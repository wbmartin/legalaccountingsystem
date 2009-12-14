


package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.CustomerService;
import com.martinanalytics.legaltime.server.GWTServerException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
/**
 * Exposes CRUD and business logic fucntionality for the Customer Beans.
 */
public class CustomerServiceImpl extends RemoteServiceServlet
		implements CustomerService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	
	public CustomerServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBean_ the bean to add
         * @return the updated bean
	 */
	public CustomerBean insertCustomerBean(UserProfile userProfile_, CustomerBean customerBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++,customerBean_.getActiveYn() );
  		ps.setDouble(ndx++,customerBean_.getMonthlyBillRate() );
  		ps.setString(ndx++,customerBean_.getBillType() );
  		ps.setString(ndx++,customerBean_.getNote() );
  		ps.setDate(ndx++, new java.sql.Date(customerBean_.getClientSinceDt().getTime()));
  		ps.setString(ndx++,customerBean_.getEmail() );
  		ps.setString(ndx++,customerBean_.getFax() );
  		ps.setString(ndx++,customerBean_.getHomePhone() );
  		ps.setString(ndx++,customerBean_.getWorkPhone() );
  		ps.setString(ndx++,customerBean_.getZip() );
  		ps.setString(ndx++,customerBean_.getState() );
  		ps.setString(ndx++,customerBean_.getCity() );
  		ps.setString(ndx++,customerBean_.getAddress() );
  		ps.setString(ndx++,customerBean_.getLastName() );
  		ps.setString(ndx++,customerBean_.getFirstName() );
      		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public CustomerBean updateCustomerBean(UserProfile userProfile_, CustomerBean customerBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++,customerBean_.getActiveYn() );
 		ps.setDouble(ndx++,customerBean_.getMonthlyBillRate() );
 		ps.setString(ndx++,customerBean_.getBillType() );
 		ps.setString(ndx++,customerBean_.getNote() );
 		ps.setDate(ndx++, new java.sql.Date(customerBean_.getClientSinceDt().getTime()));
 		ps.setString(ndx++,customerBean_.getEmail() );
 		ps.setString(ndx++,customerBean_.getFax() );
 		ps.setString(ndx++,customerBean_.getHomePhone() );
 		ps.setString(ndx++,customerBean_.getWorkPhone() );
 		ps.setString(ndx++,customerBean_.getZip() );
 		ps.setString(ndx++,customerBean_.getState() );
 		ps.setString(ndx++,customerBean_.getCity() );
 		ps.setString(ndx++,customerBean_.getAddress() );
 		ps.setString(ndx++,customerBean_.getLastName() );
 		ps.setString(ndx++,customerBean_.getFirstName() );
 		ps.setTimestamp(ndx++, new java.sql.Timestamp(customerBean_.getLastUpdate().getTime()));
 		
 		ps.setInt(ndx++,customerBean_.getCustomerId() );
  		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteCustomerBean(UserProfile userProfile_, CustomerBean customerBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from customer_dq('CHECK_AUTH',?,?,?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,customerBean_.getCustomerId() );
     		ps.setTimestamp(ndx++, new java.sql.Timestamp(customerBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
	  }


	  return result;

	
	}


	

	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerId_ 
	 * @param clientId_ 
         * @return an arraylist of the beans
	 */
	public  CustomerBean getCustomerByPrKey(UserProfile userProfile_ , Integer customerId_ ){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  CustomerBean result  = new CustomerBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_bypk('CHECK_AUTH',?,?,?, ? );");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving Customer Record Failed", e);
	
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
	public ArrayList< CustomerBean> selectCustomer(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Retrieving Customer List Failed", e);
	  }
	  return resultList;
	}


	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBean that was converted
         */
 	public CustomerBean decodeRow(ResultSet rs) throws SQLException{
          CustomerBean bean = new CustomerBean();
          bean.setActiveYn(rs.getString(1));
          bean.setMonthlyBillRate(rs.getDouble(2));
          bean.setBillType(rs.getString(3));
          bean.setNote(rs.getString(4));
          bean.setClientSinceDt(rs.getDate(5));
          bean.setEmail(rs.getString(6));
          bean.setFax(rs.getString(7));
          bean.setHomePhone(rs.getString(8));
          bean.setWorkPhone(rs.getString(9));
          bean.setZip(rs.getString(10));
          bean.setState(rs.getString(11));
          bean.setCity(rs.getString(12));
          bean.setAddress(rs.getString(13));
          bean.setLastName(rs.getString(14));
          bean.setFirstName(rs.getString(15));
          bean.setLastUpdate(rs.getTimestamp(16)); 
          bean.setClientId(rs.getInt(17));
          bean.setCustomerId(rs.getInt(18));
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBean that was converted
         */
	public CustomerBean saveCustomerBean(UserProfile userProfile_, CustomerBean customerBean_){
		if (  customerBean_.getCustomerId() ==null ||  customerBean_.getCustomerId() ==0   || customerBean_.getClientId() ==null ||  customerBean_.getClientId() ==0  ){
			return insertCustomerBean( userProfile_,  customerBean_);
		}else{
			return updateCustomerBean( userProfile_,  customerBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<CustomerBean> saveCustomerBeanBatch(UserProfile userProfile_, ArrayList<CustomerBean> customerBeanList_){
		for(int ndx =0; ndx< customerBeanList_.size(); ndx++){
			customerBeanList_.set(ndx, saveCustomerBean(userProfile_,customerBeanList_.get(ndx)));
		}
		return  customerBeanList_;
	}

}
