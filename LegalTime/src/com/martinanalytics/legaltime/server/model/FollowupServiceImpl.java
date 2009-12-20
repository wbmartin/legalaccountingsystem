

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.FollowupService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the Followup Beans.
 */
public class FollowupServiceImpl extends RemoteServiceServlet
		implements FollowupService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public FollowupServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param followupBean_ the bean to add
         * @return the updated bean
	 */
	public FollowupBean insertFollowupBean(UserProfile userProfile_, FollowupBean followupBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<FollowupBean> resultList  = new ArrayList<FollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from followup_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,followupBean_.getAssignedUserId() );
  		ps.setString(++ndx,followupBean_.getFollowupDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getCloseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getOpenDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getDueDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
   		ps.setInt(++ndx,followupBean_.getCustomerId() );
     		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Inserting Followup Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param followupBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public FollowupBean updateFollowupBean(UserProfile userProfile_, FollowupBean followupBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<FollowupBean> resultList  = new ArrayList<FollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from followup_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setString(++ndx,followupBean_.getAssignedUserId() );
  		ps.setString(++ndx,followupBean_.getFollowupDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getCloseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getOpenDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(followupBean_.getDueDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DATE);
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(followupBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.TIMESTAMP);
  		}

	
  		ps.setInt(++ndx,followupBean_.getCustomerId() );
   		ps.setInt(++ndx,followupBean_.getFollowupId() );
   		rs =  ps.executeQuery();
		
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		result = "FAIL";
		throw new GWTServerException("Updating Followup Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param followupBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteFollowupBean(UserProfile userProfile_, FollowupBean followupBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<FollowupBean> resultList  = new ArrayList<FollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from followup_dq('CHECK_AUTH',?,?,?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,followupBean_.getFollowupId() );
     		ps.setTimestamp(ndx++, new java.sql.Timestamp(followupBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		throw new GWTServerException("Deleting Followup Record Failed", e);
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
	public ArrayList< FollowupBean> selectFollowup(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<FollowupBean> resultList  = new ArrayList<FollowupBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from followup_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving Followup Records Failed", e);
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param followupId_ 
	 * @param clientId_ 
         * @return an arraylist of the beans
	 */
	public  FollowupBean getFollowupByPrKey(UserProfile userProfile_ , Integer followupId_ ){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  FollowupBean result  = new FollowupBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  assigned_user_id , followup_description , close_dt , open_dt , due_dt , last_update , customer_id , client_id , followup_id  from followup_bypk('CHECK_AUTH',?,?,?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, followupId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving Followup Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the FollowupBean that was converted
         */
 	public FollowupBean decodeRow(ResultSet rs) throws SQLException{
          FollowupBean bean = new FollowupBean();
          bean.setAssignedUserId(rs.getString(1));
          bean.setFollowupDescription(rs.getString(2));
          bean.setCloseDt(rs.getDate(3));
          bean.setOpenDt(rs.getDate(4));
          bean.setDueDt(rs.getDate(5));
          bean.setLastUpdate(rs.getTimestamp(6)); 
          bean.setCustomerId(rs.getInt(7));
          bean.setClientId(rs.getInt(8));
          bean.setFollowupId(rs.getInt(9));
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the FollowupBean that was converted
         */
	public FollowupBean saveFollowupBean(UserProfile userProfile_, FollowupBean followupBean_){
		if (  followupBean_.getFollowupId() ==null ||  followupBean_.getFollowupId() ==0   || followupBean_.getClientId() ==null ||  followupBean_.getClientId() ==0  ){
			return insertFollowupBean( userProfile_,  followupBean_);
		}else{
			return updateFollowupBean( userProfile_,  followupBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param followupBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<FollowupBean> saveFollowupBeanBatch(UserProfile userProfile_, ArrayList<FollowupBean> followupBeanList_){
		for(int ndx =0; ndx< followupBeanList_.size(); ndx++){
			followupBeanList_.set(ndx, saveFollowupBean(userProfile_,followupBeanList_.get(ndx)));
		}
		return  followupBeanList_;
	}

}
