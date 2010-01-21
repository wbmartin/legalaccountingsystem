

package com.martinanalytics.legaltime.server.model;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.CustomerService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
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
	 * @throws GWTCustomException 
	 */
	public CustomerBean insertCustomerBean(UserProfile userProfile_, CustomerBean customerBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  suspend_invoice ,contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,customerBean_.getSuspendInvoice() );	
		try{
  			ps.setDouble(++ndx, customerBean_.getContingencyRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, customerBean_.getMortgageAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,customerBean_.getActiveYn() );
  		ps.setDouble(++ndx,customerBean_.getMonthlyBillRate() );
  		ps.setString(++ndx,customerBean_.getBillType() );
  		ps.setString(++ndx,customerBean_.getNote() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(customerBean_.getClientSinceDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		ps.setString(++ndx,customerBean_.getEmail() );
  		ps.setString(++ndx,customerBean_.getFax() );
  		ps.setString(++ndx,customerBean_.getHomePhone() );
  		ps.setString(++ndx,customerBean_.getWorkPhone() );
  		ps.setString(++ndx,customerBean_.getZip() );
  		ps.setString(++ndx,customerBean_.getState() );
  		ps.setString(++ndx,customerBean_.getCity() );
  		ps.setString(++ndx,customerBean_.getAddress() );
  		ps.setString(++ndx,customerBean_.getLastName() );
  		ps.setString(++ndx,customerBean_.getFirstName() );
      		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Inserting Customer Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public CustomerBean updateCustomerBean(UserProfile userProfile_, CustomerBean customerBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  suspend_invoice , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,customerBean_.getSuspendInvoice() );
		try{
  			ps.setDouble(++ndx, customerBean_.getContingencyRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, customerBean_.getMortgageAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,customerBean_.getActiveYn() );
  		ps.setDouble(++ndx,customerBean_.getMonthlyBillRate() );
  		ps.setString(++ndx,customerBean_.getBillType() );
  		ps.setString(++ndx,customerBean_.getNote() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(customerBean_.getClientSinceDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		ps.setString(++ndx,customerBean_.getEmail() );
  		ps.setString(++ndx,customerBean_.getFax() );
  		ps.setString(++ndx,customerBean_.getHomePhone() );
  		ps.setString(++ndx,customerBean_.getWorkPhone() );
  		ps.setString(++ndx,customerBean_.getZip() );
  		ps.setString(++ndx,customerBean_.getState() );
  		ps.setString(++ndx,customerBean_.getCity() );
  		ps.setString(++ndx,customerBean_.getAddress() );
  		ps.setString(++ndx,customerBean_.getLastName() );
  		ps.setString(++ndx,customerBean_.getFirstName() );
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(customerBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}

	
   		ps.setInt(++ndx,customerBean_.getCustomerId() );
   		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Updating Customer Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 * @throws GWTCustomException 
	 */
	public Boolean deleteCustomerBean(UserProfile userProfile_, CustomerBean customerBean_) throws GWTCustomException{
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
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Deleting Customer Record Failed", e);
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
	 * @throws GWTCustomException 
	 */
	public ArrayList< CustomerBean> selectCustomer(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select   suspend_invoice , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving Customer Records Failed", e);
		}
	  }
	  return resultList;
	}
	
	
	/**
	 * Retrieve the entire list of beans from the with an invoice generated on a given date
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public ArrayList< CustomerBean> selectCustomerWithInvoice(UserProfile userProfile_,java.util.Date invoiceDt_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<CustomerBean> resultList  = new ArrayList<CustomerBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  suspend_invoice , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_invoiced_sq('CHECK_AUTH',?,?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setDate(ndx++, new java.sql.Date( invoiceDt_.getTime()));
		System.err.println("DateRequested" + invoiceDt_);
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving Invoiced Customer Records Failed", e);
		}
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param customerId_ 
	 * @param clientId_ 
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public  CustomerBean getCustomerByPrKey(UserProfile userProfile_ , Integer customerId_ ) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  CustomerBean result  = new CustomerBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  suspend_invoice , contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , note , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , last_name , first_name , last_update , client_id , customer_id  from customer_bypk('CHECK_AUTH',?,?,?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving Customer Record Failed", e);
		}
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBean that was converted
         */
     	public CustomerBean decodeRow(ResultSet rs) throws SQLException{
     		  java.util.Date nullDate = new java.util.Date(0);
     	          CustomerBean bean = new CustomerBean();
     	          bean.setSuspendInvoice(rs.getString(1));
     	            if(rs.wasNull()){bean.setSuspendInvoice(null);}
     	          bean.setContingencyRate(rs.getDouble(2));
     	            if(rs.wasNull()){bean.setContingencyRate(null);}
     	          bean.setMortgageAmount(rs.getDouble(3));
     	            if(rs.wasNull()){bean.setMortgageAmount(null);}
     	          bean.setActiveYn(rs.getString(4));
     	            if(rs.wasNull()){bean.setActiveYn(null);}
     	          bean.setMonthlyBillRate(rs.getDouble(5));
     	            if(rs.wasNull()){bean.setMonthlyBillRate(null);}
     	          bean.setBillType(rs.getString(6));
     	            if(rs.wasNull()){bean.setBillType(null);}
     	          bean.setNote(rs.getString(7));
     	            if(rs.wasNull()){bean.setNote(null);}
     	          bean.setClientSinceDt(rs.getDate(8));
     	            if(rs.wasNull()){bean.setClientSinceDt(null);}
     	          bean.setEmail(rs.getString(9));
     	            if(rs.wasNull()){bean.setEmail(null);}
     	          bean.setFax(rs.getString(10));
     	            if(rs.wasNull()){bean.setFax(null);}
     	          bean.setHomePhone(rs.getString(11));
     	            if(rs.wasNull()){bean.setHomePhone(null);}
     	          bean.setWorkPhone(rs.getString(12));
     	            if(rs.wasNull()){bean.setWorkPhone(null);}
     	          bean.setZip(rs.getString(13));
     	            if(rs.wasNull()){bean.setZip(null);}
     	          bean.setState(rs.getString(14));
     	            if(rs.wasNull()){bean.setState(null);}
     	          bean.setCity(rs.getString(15));
     	            if(rs.wasNull()){bean.setCity(null);}
     	          bean.setAddress(rs.getString(16));
     	            if(rs.wasNull()){bean.setAddress(null);}
     	          bean.setLastName(rs.getString(17));
     	            if(rs.wasNull()){bean.setLastName(null);}
     	          bean.setFirstName(rs.getString(18));
     	            if(rs.wasNull()){bean.setFirstName(null);}
     	          bean.setLastUpdate(rs.getTimestamp(19));
     	            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
     	          bean.setClientId(rs.getInt(20));
     	            if(rs.wasNull()){bean.setClientId(null);}
     	          bean.setCustomerId(rs.getInt(21));
     	            if(rs.wasNull()){bean.setCustomerId(null);}
     	           bean.setDisplayName(bean.getLastName() + ", " + bean.getFirstName());
     	          return bean;
     	        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the CustomerBean that was converted
	 * @throws GWTCustomException 
         */
	public CustomerBean saveCustomerBean(UserProfile userProfile_, CustomerBean customerBean_) throws GWTCustomException{
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
	 * @throws GWTCustomException 
	 */
	public ArrayList<CustomerBean> saveCustomerBeanBatch(UserProfile userProfile_, ArrayList<CustomerBean> customerBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< customerBeanList_.size(); ndx++){
			customerBeanList_.set(ndx, saveCustomerBean(userProfile_,customerBeanList_.get(ndx)));
		}
		return  customerBeanList_;
	}

}
