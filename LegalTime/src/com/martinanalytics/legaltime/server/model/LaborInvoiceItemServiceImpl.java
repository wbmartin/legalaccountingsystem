

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.LaborInvoiceItemService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the LaborInvoiceItem Beans.
 */
public class LaborInvoiceItemServiceImpl extends RemoteServiceServlet
		implements LaborInvoiceItemService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public LaborInvoiceItemServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborInvoiceItemBean_ the bean to add
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public LaborInvoiceItemBean insertLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborInvoiceItemBean> resultList  = new ArrayList<LaborInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id , client_id , labor_invoice_item_id  from labor_invoice_item_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDouble(++ndx, laborInvoiceItemBean_.getBillRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, laborInvoiceItemBean_.getHoursBilled());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setInt(++ndx,laborInvoiceItemBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		ps.setString(++ndx,laborInvoiceItemBean_.getUserId() );
  		ps.setString(++ndx,laborInvoiceItemBean_.getActivityDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(laborInvoiceItemBean_.getActivityDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,laborInvoiceItemBean_.getCustomerId());
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
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Inserting LaborInvoiceItem Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborInvoiceItemBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public LaborInvoiceItemBean updateLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborInvoiceItemBean> resultList  = new ArrayList<LaborInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id , client_id , labor_invoice_item_id  from labor_invoice_item_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDouble(++ndx, laborInvoiceItemBean_.getBillRate());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, laborInvoiceItemBean_.getHoursBilled());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setInt(++ndx,laborInvoiceItemBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		ps.setString(++ndx,laborInvoiceItemBean_.getUserId() );
  		ps.setString(++ndx,laborInvoiceItemBean_.getActivityDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(laborInvoiceItemBean_.getActivityDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(laborInvoiceItemBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,laborInvoiceItemBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,laborInvoiceItemBean_.getLaborInvoiceItemId());
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
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Updating LaborInvoiceItem Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborInvoiceItemBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 * @throws GWTCustomException 
	 */
	public Boolean deleteLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<LaborInvoiceItemBean> resultList  = new ArrayList<LaborInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from labor_invoice_item_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,laborInvoiceItemBean_.getLaborInvoiceItemId() );
    		ps.setInt(ndx++,laborInvoiceItemBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(laborInvoiceItemBean_.getLastUpdate().getTime()));
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
			throw new GWTServerException("Deleting LaborInvoiceItem Record Failed", e);
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
	public ArrayList< LaborInvoiceItemBean> selectLaborInvoiceItem(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<LaborInvoiceItemBean> resultList  = new ArrayList<LaborInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id , client_id , labor_invoice_item_id  from labor_invoice_item_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTServerException("Retrieving LaborInvoiceItem Records Failed", e);
		}
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborInvoiceItemId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public  LaborInvoiceItemBean getLaborInvoiceItemByPrKey(UserProfile userProfile_ , Integer laborInvoiceItemId_ , Integer customerId_ ) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  LaborInvoiceItemBean result  = new LaborInvoiceItemBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_rate , hours_billed , invoice_id , user_id , activity_description , activity_dt , last_update , customer_id , client_id , labor_invoice_item_id  from labor_invoice_item_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, laborInvoiceItemId_);
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
			throw new GWTServerException("Retrieving LaborInvoiceItem Record Failed", e);
		}
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the LaborInvoiceItemBean that was converted
         */
 	public LaborInvoiceItemBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          LaborInvoiceItemBean bean = new LaborInvoiceItemBean();
          bean.setBillRate(rs.getDouble(1));
            if(rs.wasNull()){bean.setBillRate(null);}
          bean.setHoursBilled(rs.getDouble(2));
            if(rs.wasNull()){bean.setHoursBilled(null);}
          bean.setInvoiceId(rs.getInt(3));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          bean.setUserId(rs.getString(4));
            if(rs.wasNull()){bean.setUserId(null);}
          bean.setActivityDescription(rs.getString(5));
            if(rs.wasNull()){bean.setActivityDescription(null);}
          bean.setActivityDt(rs.getDate(6));
            if(rs.wasNull()){bean.setActivityDt(null);}
          bean.setLastUpdate(rs.getTimestamp(7));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(8));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(9));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setLaborInvoiceItemId(rs.getInt(10));
            if(rs.wasNull()){bean.setLaborInvoiceItemId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the LaborInvoiceItemBean that was converted
	 * @throws GWTCustomException 
         */
	public LaborInvoiceItemBean saveLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException{
		if (  laborInvoiceItemBean_.getLaborInvoiceItemId() ==null ||  laborInvoiceItemBean_.getLaborInvoiceItemId() ==0   || laborInvoiceItemBean_.getClientId() ==null ||  laborInvoiceItemBean_.getClientId() ==0   || laborInvoiceItemBean_.getCustomerId() ==null ||  laborInvoiceItemBean_.getCustomerId() ==0  ){
			return insertLaborInvoiceItemBean( userProfile_,  laborInvoiceItemBean_);
		}else{
			return updateLaborInvoiceItemBean( userProfile_,  laborInvoiceItemBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param laborInvoiceItemBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 * @throws GWTCustomException 
	 */
	public ArrayList<LaborInvoiceItemBean> saveLaborInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<LaborInvoiceItemBean> laborInvoiceItemBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< laborInvoiceItemBeanList_.size(); ndx++){
			laborInvoiceItemBeanList_.set(ndx, saveLaborInvoiceItemBean(userProfile_,laborInvoiceItemBeanList_.get(ndx)));
		}
		return  laborInvoiceItemBeanList_;
	}

}
