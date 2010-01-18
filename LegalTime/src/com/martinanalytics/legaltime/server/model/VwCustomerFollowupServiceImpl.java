

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.VwCustomerFollowupService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the VwCustomerFollowup Beans.
 */
public class VwCustomerFollowupServiceImpl extends RemoteServiceServlet
		implements VwCustomerFollowupService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public VwCustomerFollowupServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param vwCustomerFollowupBean_ the bean to add
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public VwCustomerFollowupBean insertVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwCustomerFollowupBean> resultList  = new ArrayList<VwCustomerFollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  usr_display , display_name , last_name , first_name , assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from vw_customer_followup_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,vwCustomerFollowupBean_.getUsrDisplay() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getDisplayName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getLastName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getFirstName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getAssignedUserId() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getFollowupDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getCloseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getOpenDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getDueDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,vwCustomerFollowupBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,vwCustomerFollowupBean_.getFollowupId());
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
			throw new GWTServerException("Inserting VwCustomerFollowup Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param vwCustomerFollowupBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public VwCustomerFollowupBean updateVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwCustomerFollowupBean> resultList  = new ArrayList<VwCustomerFollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  usr_display , display_name , last_name , first_name , assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from vw_customer_followup_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,vwCustomerFollowupBean_.getUsrDisplay() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getDisplayName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getLastName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getFirstName() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getAssignedUserId() );
  		ps.setString(++ndx,vwCustomerFollowupBean_.getFollowupDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getCloseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getOpenDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(vwCustomerFollowupBean_.getDueDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(vwCustomerFollowupBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,vwCustomerFollowupBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,vwCustomerFollowupBean_.getFollowupId());
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
			throw new GWTServerException("Updating VwCustomerFollowup Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param vwCustomerFollowupBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 * @throws GWTCustomException 
	 */
	public Boolean deleteVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<VwCustomerFollowupBean> resultList  = new ArrayList<VwCustomerFollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from vw_customer_followup_dq('CHECK_AUTH',?,?,?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
 		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwCustomerFollowupBean_.getLastUpdate().getTime()));
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
			throw new GWTServerException("Deleting VwCustomerFollowup Record Failed", e);
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
	public ArrayList< VwCustomerFollowupBean> selectVwCustomerFollowup(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwCustomerFollowupBean> resultList  = new ArrayList<VwCustomerFollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  usr_display , display_name , last_name , first_name , assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from vw_customer_followup_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTServerException("Retrieving VwCustomerFollowup Records Failed", e);
		}
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public  VwCustomerFollowupBean getVwCustomerFollowupByPrKey(UserProfile userProfile_ ) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  VwCustomerFollowupBean result  = new VwCustomerFollowupBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  usr_display , display_name , last_name , first_name , assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from vw_customer_followup_bypk('CHECK_AUTH',?,?,? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving VwCustomerFollowup Record Failed", e);
		}
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the VwCustomerFollowupBean that was converted
         */
 	public VwCustomerFollowupBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwCustomerFollowupBean bean = new VwCustomerFollowupBean();
          bean.setUsrDisplay(rs.getString(1));
          bean.setDisplayName(rs.getString(2));
          bean.setLastName(rs.getString(3));
          bean.setFirstName(rs.getString(4));
          bean.setAssignedUserId(rs.getString(5));
          bean.setFollowupDescription(rs.getString(6));
          bean.setCloseDt(rs.getDate(7));
          bean.setOpenDt(rs.getDate(8));
          bean.setDueDt(rs.getDate(9));
          bean.setLastUpdate(rs.getTimestamp(10));
          if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(11));
          bean.setClientId(rs.getInt(12));
          bean.setFollowupId(rs.getInt(13));
          return bean;
        }

	
	

}

