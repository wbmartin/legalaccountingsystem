

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.model.CustomerBillRateService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the CustomerBillRate Beans.
 */
public class CustomerBillRateServiceImpl extends RemoteServiceServlet
		implements CustomerBillRateService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public CustomerBillRateServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBillRateBean_ the bean to add
         * @return the updated bean
	 */
	public CustomerBillRateBean insertCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBillRateBean> resultList  = new ArrayList<CustomerBillRateBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , last_update , user_id , customer_id , client_id , customer_bill_rate_id  from customer_bill_rate_iq('CHECK_AUTH',?,?,?,?,?,?);");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setDouble(ndx++,customerBillRateBean_.getBillRate() );
   		ps.setString(ndx++,customerBillRateBean_.getUserId() );
  		ps.setInt(ndx++,customerBillRateBean_.getCustomerId() );
     		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Inserting CustomerBillRate Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBillRateBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public CustomerBillRateBean updateCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBillRateBean> resultList  = new ArrayList<CustomerBillRateBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , last_update , user_id , customer_id , client_id , customer_bill_rate_id  from customer_bill_rate_uq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setDouble(ndx++,customerBillRateBean_.getBillRate() );
  		ps.setTimestamp(ndx++, new java.sql.Timestamp(customerBillRateBean_.getLastUpdate().getTime()));
  		ps.setString(ndx++,customerBillRateBean_.getUserId() );
  		ps.setInt(ndx++,customerBillRateBean_.getCustomerId() );
   		ps.setInt(ndx++,customerBillRateBean_.getCustomerBillRateId() );
   		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Updating CustomerBillRate Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBillRateBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<CustomerBillRateBean> resultList  = new ArrayList<CustomerBillRateBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from customer_bill_rate_dq('CHECK_AUTH',?,?,?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,customerBillRateBean_.getCustomerBillRateId() );
     		ps.setTimestamp(ndx++, new java.sql.Timestamp(customerBillRateBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		throw new GWTServerException("Deleting CustomerBillRate Record Failed", e);
	  }


	  return result;

	
	}


	/**
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 */
	public ArrayList< CustomerBillRateBean> selectCustomerBillRate(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBillRateBean> resultList  = new ArrayList<CustomerBillRateBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , last_update , user_id , customer_id , client_id , customer_bill_rate_id  from customer_bill_rate_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving CustomerBillRate Records Failed", e);
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBillRateId_ 
	 * @param clientId_ 
         * @return an arraylist of the beans
	 */
	public  CustomerBillRateBean getCustomerBillRateByPrKey(UserProfile userProfile_ , Integer customerBillRateId_ ){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  CustomerBillRateBean result  = new CustomerBillRateBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , last_update , user_id , customer_id , client_id , customer_bill_rate_id  from customer_bill_rate_bypk('CHECK_AUTH',?,?,?, ? );");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++, customerBillRateId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving CustomerBillRate Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBillRateBean that was converted
         */
 	public CustomerBillRateBean decodeRow(ResultSet rs) throws SQLException{
          CustomerBillRateBean bean = new CustomerBillRateBean();
          bean.setBillRate(rs.getDouble(1));
          bean.setLastUpdate(rs.getTimestamp(2)); 
          bean.setUserId(rs.getString(3));
          bean.setCustomerId(rs.getInt(4));
          bean.setClientId(rs.getInt(5));
          bean.setCustomerBillRateId(rs.getInt(6));
          bean.isNew(false);
          bean.resetIsModified();
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBillRateBean that was converted
         */
	public CustomerBillRateBean saveCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_){
		if (  customerBillRateBean_.getCustomerBillRateId() ==null ||  customerBillRateBean_.getCustomerBillRateId() ==0   || customerBillRateBean_.getClientId() ==null ||  customerBillRateBean_.getClientId() ==0  ){
			return insertCustomerBillRateBean( userProfile_,  customerBillRateBean_);
		}else{
			return updateCustomerBillRateBean( userProfile_,  customerBillRateBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBillRateBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<CustomerBillRateBean> saveCustomerBillRateBeanBatch(UserProfile userProfile_, ArrayList<CustomerBillRateBean> customerBillRateBeanList_){
		for(int ndx =0; ndx< customerBillRateBeanList_.size(); ndx++){
			customerBillRateBeanList_.set(ndx, saveCustomerBillRateBean(userProfile_,customerBillRateBeanList_.get(ndx)));
		}
		return  customerBillRateBeanList_;
	}

}
