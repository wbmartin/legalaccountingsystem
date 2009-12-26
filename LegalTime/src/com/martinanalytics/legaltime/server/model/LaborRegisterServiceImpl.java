

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
  		ps.setInt(++ndx,laborRegisterBean_.getInvoiceId() );
  		ps.setDouble(++ndx,laborRegisterBean_.getBillRate() );
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
  		ps.setInt(++ndx,laborRegisterBean_.getMinuteCount() );
  		ps.setString(++ndx,laborRegisterBean_.getDescription() );
   		ps.setInt(++ndx,laborRegisterBean_.getCustomerId() );
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
  		ps.setInt(++ndx,laborRegisterBean_.getInvoiceId() );
  		ps.setDouble(++ndx,laborRegisterBean_.getBillRate() );
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

	
  		ps.setInt(++ndx,laborRegisterBean_.getMinuteCount() );
  		ps.setString(++ndx,laborRegisterBean_.getDescription() );
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborRegisterBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}

	
  		ps.setInt(++ndx,laborRegisterBean_.getCustomerId() );
   		ps.setInt(++ndx,laborRegisterBean_.getLaborRegisterId() );
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
	 */
	public ArrayList< LaborRegisterBean> selectLaborRegister(UserProfile userProfile_, String whereClause_, String orderByClause_){
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
		throw new GWTServerException("Retrieving LaborRegister Records Failed", e);
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
          LaborRegisterBean bean = new LaborRegisterBean();
          bean.setUserId(rs.getString(1));
          bean.setInvoiceId(rs.getInt(2));
          bean.setBillRate(rs.getDouble(3));
          bean.setInvoiceable(rs.getBoolean(4));
          bean.setActivityDate(rs.getDate(5));
          bean.setEndTime(rs.getTimestamp(6)); 
          bean.setStartTime(rs.getTimestamp(7)); 
          bean.setMinuteCount(rs.getInt(8));
          bean.setDescription(rs.getString(9));
          bean.setLastUpdate(rs.getTimestamp(10)); 
          bean.setCustomerId(rs.getInt(11));
          bean.setClientId(rs.getInt(12));
          bean.setLaborRegisterId(rs.getInt(13));
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

}
