//This file has been modified
// * added assess monthly charges function

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.LaborRegisterService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the LaborRegister Beans.
 */
public class LaborRegisterServiceImpl extends RemoteServiceServlet
		implements LaborRegisterService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public LaborRegisterServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborRegisterBean_ the bean to add
         * @return the updated bean
	 */
	public LaborRegisterBean insertLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborRegisterBean> resultList  = new ArrayList<LaborRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id , client_id , labor_register_id  from labor_register_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,laborRegisterBean_.getUserId() );
  		try{
  			ps.setInt(++ndx,laborRegisterBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, laborRegisterBean_.getBillRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setBoolean(++ndx,laborRegisterBean_.getInvoiceable() );
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.BOOLEAN);
  		}
  		
  		try{
  			ps.setDate(++ndx, new java.sql.Date(laborRegisterBean_.getActivityDate().getTime()));
  			//ps.setTimestamp(++ndx, new java.sql.Timestamp(new java.util.Date().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getEndTime().getTime()));
  		}catch(NullPointerException nex){
  			//ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getStartTime().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  			//ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  			//ps.setObject(ndx, "cast(null as timestamp)");
  		}
  		try{
  			ps.setInt(++ndx,laborRegisterBean_.getMinuteCount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		ps.setString(++ndx,laborRegisterBean_.getDescription() );
   		try{
  			ps.setInt(++ndx,laborRegisterBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		
  		System.err.println("LaborRegisterService PS: " +ps.toString());

     		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Inserting LaborRegister Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborRegisterBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public LaborRegisterBean updateLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborRegisterBean> resultList  = new ArrayList<LaborRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id , client_id , labor_register_id  from labor_register_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,laborRegisterBean_.getUserId() );
  		try{
  			ps.setInt(++ndx,laborRegisterBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, laborRegisterBean_.getBillRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setBoolean(++ndx,laborRegisterBean_.getInvoiceable() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(laborRegisterBean_.getActivityDate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getEndTime().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getStartTime().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}
  		try{
  			ps.setInt(++ndx,laborRegisterBean_.getMinuteCount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		ps.setString(++ndx,laborRegisterBean_.getDescription() );
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}
  		try{
  			ps.setInt(++ndx,laborRegisterBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,laborRegisterBean_.getLaborRegisterId());
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
		throw new GWTServerException("Updating LaborRegister Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborRegisterBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<LaborRegisterBean> resultList  = new ArrayList<LaborRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from labor_register_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,laborRegisterBean_.getLaborRegisterId() );
    		ps.setInt(ndx++,laborRegisterBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(laborRegisterBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		throw new GWTServerException("Deleting LaborRegister Record Failed", e);
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
	public ArrayList< LaborRegisterBean> selectLaborRegister(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborRegisterBean> resultList  = new ArrayList<LaborRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id , client_id , labor_register_id  from labor_register_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
	 * @param laborRegisterId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  LaborRegisterBean getLaborRegisterByPrKey(UserProfile userProfile_ , Integer laborRegisterId_ , Integer customerId_ ){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  LaborRegisterBean result  = new LaborRegisterBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  user_id , invoice_id , bill_rate , invoiceable , activity_date , end_time , start_time , minute_count , description , last_update , customer_id , client_id , labor_register_id  from labor_register_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, laborRegisterId_);
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving LaborRegister Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the LaborRegisterBean that was converted
         */
	public LaborRegisterBean decodeRow(ResultSet rs) throws SQLException{
		  java.util.Date nullDate = new java.util.Date(0);
	          LaborRegisterBean bean = new LaborRegisterBean();
	          bean.setUserId(rs.getString(1));
		    if(rs.wasNull()){bean.setUserId(null);}
	          bean.setInvoiceId(rs.getInt(2));
		    if(rs.wasNull()){bean.setInvoiceId(null);}
	          bean.setBillRate(rs.getDouble(3));
		    if(rs.wasNull()){bean.setBillRate(null);}
	          bean.setInvoiceable(rs.getBoolean(4));
		    if(rs.wasNull()){bean.setInvoiceable(null);}
	          bean.setActivityDate(rs.getDate(5));
		    if(rs.wasNull()){bean.setActivityDate(null);}
	          bean.setEndTime(rs.getTimestamp(6));
	            if(bean.getEndTime().equals(nullDate)){bean.setEndTime(null);} 
	          bean.setStartTime(rs.getTimestamp(7));
	            if(bean.getStartTime().equals(nullDate)){bean.setStartTime(null);} 
	          bean.setMinuteCount(rs.getInt(8));
		    if(rs.wasNull()){bean.setMinuteCount(null);}
	          bean.setDescription(rs.getString(9));
		    if(rs.wasNull()){bean.setDescription(null);}
	          bean.setLastUpdate(rs.getTimestamp(10));
	            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
	          bean.setCustomerId(rs.getInt(11));
		    if(rs.wasNull()){bean.setCustomerId(null);}
	          bean.setClientId(rs.getInt(12));
		    if(rs.wasNull()){bean.setClientId(null);}
	          bean.setLaborRegisterId(rs.getInt(13));
		    if(rs.wasNull()){bean.setLaborRegisterId(null);}
	          return bean;
	        }


	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the LaborRegisterBean that was converted
         */
	public LaborRegisterBean saveLaborRegisterBean(UserProfile userProfile_, LaborRegisterBean laborRegisterBean_){
		if (  laborRegisterBean_.getLaborRegisterId() ==null ||  laborRegisterBean_.getLaborRegisterId() ==0   || laborRegisterBean_.getClientId() ==null ||  laborRegisterBean_.getClientId() ==0   || laborRegisterBean_.getCustomerId() ==null ||  laborRegisterBean_.getCustomerId() ==0  ){
			return insertLaborRegisterBean( userProfile_,  laborRegisterBean_);
		}else{
			return updateLaborRegisterBean( userProfile_,  laborRegisterBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborRegisterBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<LaborRegisterBean> saveLaborRegisterBeanBatch(UserProfile userProfile_, ArrayList<LaborRegisterBean> laborRegisterBeanList_){
		for(int ndx =0; ndx< laborRegisterBeanList_.size(); ndx++){
			laborRegisterBeanList_.set(ndx, saveLaborRegisterBean(userProfile_,laborRegisterBeanList_.get(ndx)));
		}
		return  laborRegisterBeanList_;
	}
	
	
	
	
	
	
	
	
	public ArrayList AssessMonthlyChargesAndInvoice(UserProfile userProfile_, java.util.Date assessDt_) throws GWTCustomException{
		  int ndx =1;
		  PreparedStatement ps;
		  ResultSet rs;
		  String result = "";
		  ArrayList<Integer> resultList  = new ArrayList<Integer>();
		  try {
			ps = databaseManager.getConnection().prepareStatement("select  * from assess_all_monthly_charges_and_invoice('CHECK_AUTH',?,?,?,?) ;");
			ps.setInt(ndx++, userProfile_.getClientId());
			ps.setString(ndx++,  userProfile_.getUserId());
			ps.setString(ndx++, userProfile_.getSessionId());
			ps.setDate(ndx++, new java.sql.Date(assessDt_.getTime()));
			rs =  ps.executeQuery();
			while(rs.next()){
			  result = rs.getString(1);
			}
			String[] resultArray = result.split(",");
			for( ndx =0;ndx<resultArray.length;ndx++){
				resultList.add(Integer.parseInt(resultArray[ndx]));
			}
		  }catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
				
				throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
			}else{
				throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
			}
		  }
		  return resultList;
		}
	
	public java.util.Date RetrieveLastMonthlycharge(UserProfile userProfile_) throws GWTCustomException{
		  int ndx =1;
		  PreparedStatement ps;
		  ResultSet rs;
		  java.util.Date result = null;
		  
		  try {
			ps = databaseManager.getConnection().prepareStatement("select  * from retrieve_last_monthly_charge('CHECK_AUTH',?,?,?) ;");
			ps.setInt(ndx++, userProfile_.getClientId());
			ps.setString(ndx++,  userProfile_.getUserId());
			ps.setString(ndx++, userProfile_.getSessionId());

			rs =  ps.executeQuery();
			while(rs.next()){
			  result = rs.getDate(1);
			}
		  }catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
				
				throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
			}else{
				throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
			}
		  }
		  return result;
		}


}
