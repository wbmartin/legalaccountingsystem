

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.UserInfoService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the UserInfo Beans.
 */
public class UserInfoServiceImpl extends RemoteServiceServlet
		implements UserInfoService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public UserInfoServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param userInfoBean_ the bean to add
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public UserInfoBean insertUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<UserInfoBean> resultList  = new ArrayList<UserInfoBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  email_addr , display_name , default_bill_rate , last_update , client_id , user_id  from user_info_iq('CHECK_AUTH',?,?,?,?,?,?,?);");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++,userInfoBean_.getEmailAddr() );
  		ps.setString(ndx++,userInfoBean_.getDisplayName() );
  		ps.setDouble(ndx++,userInfoBean_.getDefaultBillRate() );
    		ps.setString(ndx++,userInfoBean_.getUserId() );
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
			throw new GWTServerException("Inserting UserInfo Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param userInfoBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public UserInfoBean updateUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<UserInfoBean> resultList  = new ArrayList<UserInfoBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  email_addr , display_name , default_bill_rate , last_update , client_id , user_id  from user_info_uq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++,userInfoBean_.getEmailAddr() );
  		ps.setString(ndx++,userInfoBean_.getDisplayName() );
  		ps.setDouble(ndx++,userInfoBean_.getDefaultBillRate() );
  		ps.setTimestamp(ndx++, new java.sql.Timestamp(userInfoBean_.getLastUpdate().getTime()));
   		ps.setString(ndx++,userInfoBean_.getUserId() );
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
			throw new GWTServerException("Updating UserInfo Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param userInfoBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 * @throws GWTCustomException 
	 */
	public Boolean deleteUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<UserInfoBean> resultList  = new ArrayList<UserInfoBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from user_info_dq('CHECK_AUTH',?,?,?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++,userInfoBean_.getUserId() );
     		ps.setTimestamp(ndx++, new java.sql.Timestamp(userInfoBean_.getLastUpdate().getTime()));
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
			throw new GWTServerException("Deleting UserInfo Record Failed", e);
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
	public ArrayList< UserInfoBean> selectUserInfo(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<UserInfoBean> resultList  = new ArrayList<UserInfoBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  email_addr , display_name , default_bill_rate , last_update , client_id , user_id  from user_info_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTServerException("Retrieving UserInfo Records Failed", e);
		}
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param userId_ 
	 * @param clientId_ 
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public  UserInfoBean getUserInfoByPrKey(UserProfile userProfile_ , String userId_ ) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  UserInfoBean result  = new UserInfoBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  email_addr , display_name , default_bill_rate , last_update , client_id , user_id  from user_info_bypk('CHECK_AUTH',?,?,?, ? );");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setString(ndx++, userId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		if(e.getMessage().equals("ERROR: Invalid Session -- Access Denied")){
			throw new GWTCustomException("ERROR: Invalid Session -- Access Denied");
		}else{
			throw new GWTServerException("Retrieving UserInfo Record Failed", e);
		}
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the UserInfoBean that was converted
         */
 	public UserInfoBean decodeRow(ResultSet rs) throws SQLException{
          UserInfoBean bean = new UserInfoBean();
          bean.setEmailAddr(rs.getString(1));
          bean.setDisplayName(rs.getString(2));
          bean.setDefaultBillRate(rs.getDouble(3));
          bean.setLastUpdate(rs.getTimestamp(4)); 
          bean.setClientId(rs.getInt(5));
          bean.setUserId(rs.getString(6));
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the UserInfoBean that was converted
	 * @throws GWTCustomException 
         */
	public UserInfoBean saveUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException{
		if (  userInfoBean_.getUserId() ==null ||  userInfoBean_.getUserId() ==""   || userInfoBean_.getClientId() ==null ||  userInfoBean_.getClientId() ==0  ){
			return insertUserInfoBean( userProfile_,  userInfoBean_);
		}else{
			return updateUserInfoBean( userProfile_,  userInfoBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param userInfoBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 * @throws GWTCustomException 
	 */
	public ArrayList<UserInfoBean> saveUserInfoBeanBatch(UserProfile userProfile_, ArrayList<UserInfoBean> userInfoBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< userInfoBeanList_.size(); ndx++){
			userInfoBeanList_.set(ndx, saveUserInfoBean(userProfile_,userInfoBeanList_.get(ndx)));
		}
		return  userInfoBeanList_;
	}

}
