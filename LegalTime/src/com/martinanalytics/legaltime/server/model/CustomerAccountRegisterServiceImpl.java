

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.martinanalytics.legaltime.client.model.CustomerAccountRegisterService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
/**
 * Exposes CRUD and business logic fucntionality for the CustomerAccountRegister Beans.
 */
public class CustomerAccountRegisterServiceImpl extends RemoteServiceServlet
		implements CustomerAccountRegisterService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public CustomerAccountRegisterServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerAccountRegisterBean_ the bean to add
         * @return the updated bean
	 */
	public CustomerAccountRegisterBean insertCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerAccountRegisterBean> resultList  = new ArrayList<CustomerAccountRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  tran_type , tran_amt , description , effective_dt , last_update , customer_id , client_id , customer_account_register_id  from customer_account_register_iq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,customerAccountRegisterBean_.getTranType() );
  		try{
  			ps.setDouble(++ndx, customerAccountRegisterBean_.getTranAmt());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,customerAccountRegisterBean_.getDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(customerAccountRegisterBean_.getEffectiveDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,customerAccountRegisterBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
     		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			System.err.println("FiredCustomExceptions");
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
		}

	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerAccountRegisterBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public CustomerAccountRegisterBean updateCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerAccountRegisterBean> resultList  = new ArrayList<CustomerAccountRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  tran_type , tran_amt , description , effective_dt , last_update , customer_id , client_id , customer_account_register_id  from customer_account_register_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,customerAccountRegisterBean_.getTranType() );
  		try{
  			ps.setDouble(++ndx, customerAccountRegisterBean_.getTranAmt());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,customerAccountRegisterBean_.getDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(customerAccountRegisterBean_.getEffectiveDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(customerAccountRegisterBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,customerAccountRegisterBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,customerAccountRegisterBean_.getCustomerAccountRegisterId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			System.err.println("FiredCustomExceptions");
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
		}
	
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerAccountRegisterBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<CustomerAccountRegisterBean> resultList  = new ArrayList<CustomerAccountRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from customer_account_register_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,customerAccountRegisterBean_.getCustomerAccountRegisterId() );
    		ps.setInt(ndx++,customerAccountRegisterBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(customerAccountRegisterBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			System.err.println("FiredCustomExceptions");
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
		}
		
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
	public ArrayList< CustomerAccountRegisterBean> selectCustomerAccountRegister(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerAccountRegisterBean> resultList  = new ArrayList<CustomerAccountRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  tran_type , tran_amt , description , effective_dt , last_update , customer_id , client_id , customer_account_register_id  from customer_account_register_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			System.err.println("FiredCustomExceptions");
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
		}
		
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerAccountRegisterId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  CustomerAccountRegisterBean getCustomerAccountRegisterByPrKey(UserProfile userProfile_ , Integer customerAccountRegisterId_ , Integer customerId_ ) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  CustomerAccountRegisterBean result  = new CustomerAccountRegisterBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  tran_type , tran_amt , description , effective_dt , last_update , customer_id , client_id , customer_account_register_id  from customer_account_register_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, customerAccountRegisterId_);
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			System.err.println("FiredCustomExceptions");
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
		}
		
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerAccountRegisterBean that was converted
         */
 	public CustomerAccountRegisterBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          CustomerAccountRegisterBean bean = new CustomerAccountRegisterBean();
          bean.setTranType(rs.getString(1));
            if(rs.wasNull()){bean.setTranType(null);}
          bean.setTranAmt(rs.getDouble(2));
            if(rs.wasNull()){bean.setTranAmt(null);}
          bean.setDescription(rs.getString(3));
            if(rs.wasNull()){bean.setDescription(null);}
          bean.setEffectiveDt(rs.getDate(4));
            if(rs.wasNull()){bean.setEffectiveDt(null);}
          bean.setLastUpdate(rs.getTimestamp(5));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(6));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(7));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setCustomerAccountRegisterId(rs.getInt(8));
            if(rs.wasNull()){bean.setCustomerAccountRegisterId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerAccountRegisterBean that was converted
         */
	public CustomerAccountRegisterBean saveCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException{
		if (  customerAccountRegisterBean_.getCustomerAccountRegisterId() ==null ||  customerAccountRegisterBean_.getCustomerAccountRegisterId() ==0   || customerAccountRegisterBean_.getClientId() ==null ||  customerAccountRegisterBean_.getClientId() ==0   || customerAccountRegisterBean_.getCustomerId() ==null ||  customerAccountRegisterBean_.getCustomerId() ==0  ){
			return insertCustomerAccountRegisterBean( userProfile_,  customerAccountRegisterBean_);
		}else{
			return updateCustomerAccountRegisterBean( userProfile_,  customerAccountRegisterBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerAccountRegisterBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<CustomerAccountRegisterBean> saveCustomerAccountRegisterBeanBatch(UserProfile userProfile_, ArrayList<CustomerAccountRegisterBean> customerAccountRegisterBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< customerAccountRegisterBeanList_.size(); ndx++){
			customerAccountRegisterBeanList_.set(ndx, saveCustomerAccountRegisterBean(userProfile_,customerAccountRegisterBeanList_.get(ndx)));
		}
		return  customerAccountRegisterBeanList_;
	}

}
