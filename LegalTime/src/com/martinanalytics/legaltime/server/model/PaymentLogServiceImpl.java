

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.model.PaymentLogService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
/**
 * Exposes CRUD and business logic fucntionality for the PaymentLog Beans.
 */
public class PaymentLogServiceImpl extends RemoteServiceServlet
		implements PaymentLogService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public PaymentLogServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param paymentLogBean_ the bean to add
         * @return the updated bean
	 */
	public PaymentLogBean insertPaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<PaymentLogBean> resultList  = new ArrayList<PaymentLogBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id , client_id , payment_log_id  from payment_log_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setInt(++ndx,paymentLogBean_.getCustomerAccountRegisterId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setInt(++ndx,paymentLogBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, paymentLogBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,paymentLogBean_.getDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(paymentLogBean_.getEffectiveDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,paymentLogBean_.getCustomerId());
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
	 * @param paymentLogBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public PaymentLogBean updatePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<PaymentLogBean> resultList  = new ArrayList<PaymentLogBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id , client_id , payment_log_id  from payment_log_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setInt(++ndx,paymentLogBean_.getCustomerAccountRegisterId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setInt(++ndx,paymentLogBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, paymentLogBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,paymentLogBean_.getDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(paymentLogBean_.getEffectiveDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(paymentLogBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,paymentLogBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,paymentLogBean_.getPaymentLogId());
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
	 * @param paymentLogBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deletePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<PaymentLogBean> resultList  = new ArrayList<PaymentLogBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from payment_log_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,paymentLogBean_.getPaymentLogId() );
    		ps.setInt(ndx++,paymentLogBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(paymentLogBean_.getLastUpdate().getTime()));
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
	public ArrayList< PaymentLogBean> selectPaymentLog(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<PaymentLogBean> resultList  = new ArrayList<PaymentLogBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id , client_id , payment_log_id  from payment_log_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
	 * @param paymentLogId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  PaymentLogBean getPaymentLogByPrKey(UserProfile userProfile_ , Integer paymentLogId_ , Integer customerId_ ) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  PaymentLogBean result  = new PaymentLogBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  customer_account_register_id , invoice_id , amount , description , effective_dt , last_update , customer_id , client_id , payment_log_id  from payment_log_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, paymentLogId_);
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
	 * @return the PaymentLogBean that was converted
         */
 	public PaymentLogBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          PaymentLogBean bean = new PaymentLogBean();
          bean.setCustomerAccountRegisterId(rs.getInt(1));
            if(rs.wasNull()){bean.setCustomerAccountRegisterId(null);}
          bean.setInvoiceId(rs.getInt(2));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          bean.setAmount(rs.getDouble(3));
            if(rs.wasNull()){bean.setAmount(null);}
          bean.setDescription(rs.getString(4));
            if(rs.wasNull()){bean.setDescription(null);}
          bean.setEffectiveDt(rs.getDate(5));
            if(rs.wasNull()){bean.setEffectiveDt(null);}
          bean.setLastUpdate(rs.getTimestamp(6));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(7));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(8));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setPaymentLogId(rs.getInt(9));
            if(rs.wasNull()){bean.setPaymentLogId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the PaymentLogBean that was converted
         */
	public PaymentLogBean savePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException{
		if (  paymentLogBean_.getPaymentLogId() ==null ||  paymentLogBean_.getPaymentLogId() ==0   || paymentLogBean_.getClientId() ==null ||  paymentLogBean_.getClientId() ==0   || paymentLogBean_.getCustomerId() ==null ||  paymentLogBean_.getCustomerId() ==0  ){
			return insertPaymentLogBean( userProfile_,  paymentLogBean_);
		}else{
			return updatePaymentLogBean( userProfile_,  paymentLogBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param paymentLogBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<PaymentLogBean> savePaymentLogBeanBatch(UserProfile userProfile_, ArrayList<PaymentLogBean> paymentLogBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< paymentLogBeanList_.size(); ndx++){
			paymentLogBeanList_.set(ndx, savePaymentLogBean(userProfile_,paymentLogBeanList_.get(ndx)));
		}
		return  paymentLogBeanList_;
	}

}
