

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
/**
 * Exposes CRUD and business logic fucntionality for the VwCustomerHourlyBillRate Beans.
 */
public class VwCustomerHourlyBillRateServiceImpl extends RemoteServiceServlet
		implements VwCustomerHourlyBillRateService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public VwCustomerHourlyBillRateServiceImpl() {
		super();
		
	}

	


	/**
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 */
	public ArrayList< VwCustomerHourlyBillRateBean> selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  bill_type , last_name , first_name , last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id , client_id  from vw_customer_hourly_bill_rate_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the VwCustomerHourlyBillRateBean that was converted
         */
 	public VwCustomerHourlyBillRateBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwCustomerHourlyBillRateBean bean = new VwCustomerHourlyBillRateBean();
          bean.setBillType(rs.getString(1));
            if(rs.wasNull()){bean.setBillType(null);}
          bean.setLastName(rs.getString(2));
            if(rs.wasNull()){bean.setLastName(null);}
          bean.setFirstName(rs.getString(3));
            if(rs.wasNull()){bean.setFirstName(null);}
          bean.setLastUpdate(rs.getTimestamp(4));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(5));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setBillRate(rs.getDouble(6));
            if(rs.wasNull()){bean.setBillRate(null);}
          bean.setDisplayName(rs.getString(7));
            if(rs.wasNull()){bean.setDisplayName(null);}
          bean.setUserId(rs.getString(8));
            if(rs.wasNull()){bean.setUserId(null);}
          bean.setCustomerBillRateId(rs.getInt(9));
            if(rs.wasNull()){bean.setCustomerBillRateId(null);}
          bean.setClientId(rs.getInt(10));
            if(rs.wasNull()){bean.setClientId(null);}
          return bean;
        }


}



//
//
//package com.martinanalytics.legaltime.server.model;
//
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import java.util.ArrayList;
//import com.martinanalytics.legaltime.client.model.SQLGarage;
//import com.martinanalytics.legaltime.client.model.bean.UserProfile;
//import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
//import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateService;
//import com.martinanalytics.legaltime.server.model.DatabaseManager;
//import com.martinanalytics.legaltime.server.GWTServerException;
///**
// * Exposes CRUD and business logic fucntionality for the VwCustomerHourlyBillRate Beans.
// */
//public class VwCustomerHourlyBillRateServiceImpl extends RemoteServiceServlet
//		implements VwCustomerHourlyBillRateService{
//	private DatabaseManager databaseManager = DatabaseManager.getInstance();
//	private static final long serialVersionUID = 1L;
//	public VwCustomerHourlyBillRateServiceImpl() {
//		super();
//		
//	}
//	/**
//	 * Add a record the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwCustomerHourlyBillRateBean_ the bean to add
//         * @return the updated bean
//	 */
//	public VwCustomerHourlyBillRateBean insertVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  String result;
//	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id , client_id  from vw_customer_hourly_bill_rate_iq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
//		ps.setInt(ndx++, userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
// 		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerId() );
//  		ps.setDouble(ndx++,vwCustomerHourlyBillRateBean_.getBillRate() );
//  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getDisplayName() );
//  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getUserId() );
//  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerBillRateId() );
//    		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  resultList.add(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		result = "FAIL";
//		throw new GWTServerException("Inserting VwCustomerHourlyBillRate Record Failed", e);
//	  }
//	  return resultList.get(0);
//	}
//
//
//	/**
//	 * Update a record the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwCustomerHourlyBillRateBean_ the bean to update, new values come through this bean
//         * @return the updated bean
//	 */
//	public VwCustomerHourlyBillRateBean updateVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  String result;
//	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id , client_id  from vw_customer_hourly_bill_rate_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
//		ps.setInt(ndx++,  userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
//		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwCustomerHourlyBillRateBean_.getLastUpdate().getTime()));
//  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerId() );
//  		ps.setDouble(ndx++,vwCustomerHourlyBillRateBean_.getBillRate() );
//  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getDisplayName() );
//  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getUserId() );
//  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerBillRateId() );
//    		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  resultList.add(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		result = "FAIL";
//		throw new GWTServerException("Updating VwCustomerHourlyBillRate Record Failed", e);
//	  }
//	  return resultList.get(0);
//	}
//
//
//	/**
//	 * delete a record from the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwCustomerHourlyBillRateBean_ the bean to delete, only primary keys value
//         * @return true if the delete was successful
//	 */
//	public Boolean deleteVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  Boolean result = false;
//	  //ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select * from vw_customer_hourly_bill_rate_dq('CHECK_AUTH',?,?,?, ?);");
//		ps.setInt(ndx++,  userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
// 		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwCustomerHourlyBillRateBean_.getLastUpdate().getTime()));
//		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  result = rs.getBoolean(1);
//		}
//	  }catch (Exception e) {	
//		e.printStackTrace();
//		result = false;
//		throw new GWTServerException("Deleting VwCustomerHourlyBillRate Record Failed", e);
//	  }
//
//
//	  return result;
//
//	
//	}
//
//
//	/**
//	 * Retrieve the entire list of beans from the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param whereClause_ the filter to apply to the list, should begin with "where"
//	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
//         * @return an arraylist of the beans
//	 */
//	public ArrayList< VwCustomerHourlyBillRateBean> selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereClause_, String orderByClause_){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  String result;
//	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id , client_id  from vw_customer_hourly_bill_rate_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
//		ps.setInt(ndx++, userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
//		rs =  ps.executeQuery();
//		while(rs.next()){
//			//System.err.println("ServiceImpl clientID:" + rs.getInt("client_id"));
//		  resultList.add(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		throw new GWTServerException("Retrieving VwCustomerHourlyBillRate Records Failed", e);
//	  }
//	  return resultList;
//	}
//
//
//
//
//
//
//	/**
//	 * Retrieve the  the bean from the database by Primary Key
//	 * @param userProfile_ the credentials to use for authentication and authorization
//         * @return an arraylist of the beans
//	 */
//	public  VwCustomerHourlyBillRateBean getVwCustomerHourlyBillRateByPrKey(UserProfile userProfile_ ){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  VwCustomerHourlyBillRateBean result  = new VwCustomerHourlyBillRateBean();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id , client_id  from vw_customer_hourly_bill_rate_bypk('CHECK_AUTH',?,?,? );");
//		ps.setInt(ndx++, userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
//		rs =  ps.executeQuery();
//		if(rs.next()){
//		  result =(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		throw new GWTServerException("Retrieving VwCustomerHourlyBillRate Record Failed", e);
//	
//	  }
//	  return result;
//	}
//
//	/**
//	 * Convert a result set a bean
//         * @param rs the result set to be converted
//	 * @return the VwCustomerHourlyBillRateBean that was converted
//         */
// 	public VwCustomerHourlyBillRateBean decodeRow(ResultSet rs) throws SQLException{
//          VwCustomerHourlyBillRateBean bean = new VwCustomerHourlyBillRateBean();
//          bean.setLastUpdate(rs.getTimestamp(1)); 
//          bean.setCustomerId(rs.getInt(2));
//          bean.setBillRate(rs.getDouble(3));
//          bean.setDisplayName(rs.getString(4));
//          bean.setUserId(rs.getString(5));
//          bean.setCustomerBillRateId(rs.getInt(6));
//          bean.setClientId(rs.getInt(7));
//          return bean;
//        }
//
//	/**
//	 * Convert a result set a bean
//         * @param rs the result set to be converted
//	 * @return the VwCustomerHourlyBillRateBean that was converted
//         */
//	public VwCustomerHourlyBillRateBean saveVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
////		if (){
////			return insertVwCustomerHourlyBillRateBean( userProfile_,  vwCustomerHourlyBillRateBean_);
////		}else{
////			return updateVwCustomerHourlyBillRateBean( userProfile_,  vwCustomerHourlyBillRateBean_);
////		}
//		return null;
//
//	}
//	/**
//	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwCustomerHourlyBillRateBeanList_ the list of beans to save
//         * @return an arraylist of the beans, updated with primary keys and last updates.
//	 */
//	public ArrayList<VwCustomerHourlyBillRateBean> saveVwCustomerHourlyBillRateBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateBeanList_){
//		for(int ndx =0; ndx< vwCustomerHourlyBillRateBeanList_.size(); ndx++){
//			vwCustomerHourlyBillRateBeanList_.set(ndx, saveVwCustomerHourlyBillRateBean(userProfile_,vwCustomerHourlyBillRateBeanList_.get(ndx)));
//		}
//		return  vwCustomerHourlyBillRateBeanList_;
//	}
//
//}
//
//
////
////
////package com.martinanalytics.legaltime.server.model;
////
////
////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import com.google.gwt.user.server.rpc.RemoteServiceServlet;
////import java.util.ArrayList;
////import com.martinanalytics.legaltime.client.model.SQLGarage;
////import com.martinanalytics.legaltime.client.model.bean.UserProfile;
////import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
////import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateService;
////import com.martinanalytics.legaltime.server.model.DatabaseManager;
////import com.martinanalytics.legaltime.server.GWTServerException;
/////**
//// * Exposes CRUD and business logic fucntionality for the VwCustomerHourlyBillRate Beans.
//// */
////public class VwCustomerHourlyBillRateServiceImpl extends RemoteServiceServlet
////		implements VwCustomerHourlyBillRateService{
////	private DatabaseManager databaseManager = DatabaseManager.getInstance();
////	private static final long serialVersionUID = 1L;
////	public VwCustomerHourlyBillRateServiceImpl() {
////		super();
////		
////	}
////	/**
////	 * Add a record the database
////	 * @param userProfile_ the credentials to use for authentication and authorization
////	 * @param vwCustomerHourlyBillRateBean_ the bean to add
////         * @return the updated bean
////	 */
////	public VwCustomerHourlyBillRateBean insertVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
////	  int ndx =1;
////	  PreparedStatement ps;
////	  ResultSet rs;
////	  String result;
////	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
////	  try {
////		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id  from vw_customer_hourly_bill_rate_iq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
////		ps.setInt(ndx++, userProfile_.getClientId());
////		ps.setString(ndx++,  userProfile_.getUserId());
////		ps.setString(ndx++, userProfile_.getSessionId());
//// 		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerId() );
////  		ps.setDouble(ndx++,vwCustomerHourlyBillRateBean_.getBillRate() );
////  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getDisplayName() );
////  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getUserId() );
////  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerBillRateId() );
////   		rs =  ps.executeQuery();
////		
////		while(rs.next()){
////		  resultList.add(decodeRow(rs));
////		}
////	  }catch (Exception e) {
////		e.printStackTrace();
////		result = "FAIL";
////		throw new GWTServerException("Inserting VwCustomerHourlyBillRate Record Failed", e);
////	  }
////	  return resultList.get(0);
////	}
////
////
////	/**
////	 * Update a record the database
////	 * @param userProfile_ the credentials to use for authentication and authorization
////	 * @param vwCustomerHourlyBillRateBean_ the bean to update, new values come through this bean
////         * @return the updated bean
////	 */
////	public VwCustomerHourlyBillRateBean updateVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
////	  int ndx =1;
////	  PreparedStatement ps;
////	  ResultSet rs;
////	  String result;
////	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
////	  try {
////		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id  from vw_customer_hourly_bill_rate_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
////		ps.setInt(ndx++,  userProfile_.getClientId());
////		ps.setString(ndx++,  userProfile_.getUserId());
////		ps.setString(ndx++, userProfile_.getSessionId());
////		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwCustomerHourlyBillRateBean_.getLastUpdate().getTime()));
////  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerId() );
////  		ps.setDouble(ndx++,vwCustomerHourlyBillRateBean_.getBillRate() );
////  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getDisplayName() );
////  		ps.setString(ndx++,vwCustomerHourlyBillRateBean_.getUserId() );
////  		ps.setInt(ndx++,vwCustomerHourlyBillRateBean_.getCustomerBillRateId() );
////   		rs =  ps.executeQuery();
////		
////		while(rs.next()){
////		  resultList.add(decodeRow(rs));
////		}
////	  }catch (Exception e) {
////		e.printStackTrace();
////		result = "FAIL";
////		throw new GWTServerException("Updating VwCustomerHourlyBillRate Record Failed", e);
////	  }
////	  return resultList.get(0);
////	}
////
////
////	/**
////	 * delete a record from the database
////	 * @param userProfile_ the credentials to use for authentication and authorization
////	 * @param vwCustomerHourlyBillRateBean_ the bean to delete, only primary keys value
////         * @return true if the delete was successful
////	 */
////	public Boolean deleteVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
////	  int ndx =1;
////	  PreparedStatement ps;
////	  ResultSet rs;
////	  Boolean result = false;
////	  //ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
////	  try {
////		ps = databaseManager.getConnection().prepareStatement("select * from vw_customer_hourly_bill_rate_dq('CHECK_AUTH',?,?,?, ?);");
////		ps.setInt(ndx++,  userProfile_.getClientId());
////		ps.setString(ndx++,  userProfile_.getUserId());
////		ps.setString(ndx++, userProfile_.getSessionId());
//// 		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwCustomerHourlyBillRateBean_.getLastUpdate().getTime()));
////		rs =  ps.executeQuery();
////		
////		while(rs.next()){
////		  result = rs.getBoolean(1);
////		}
////	  }catch (Exception e) {	
////		e.printStackTrace();
////		result = false;
////		throw new GWTServerException("Deleting VwCustomerHourlyBillRate Record Failed", e);
////	  }
////
////
////	  return result;
////
////	
////	}
////
////
////	/**
////	 * Retrieve the entire list of beans from the database
////	 * @param userProfile_ the credentials to use for authentication and authorization
////	 * @param whereClause_ the filter to apply to the list, should begin with "where"
////	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
////         * @return an arraylist of the beans
////	 */
////	public ArrayList< VwCustomerHourlyBillRateBean> selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereClause_, String orderByClause_){
////	  int ndx =1;
////	  PreparedStatement ps;
////	  ResultSet rs;
////	  String result;
////	  ArrayList<VwCustomerHourlyBillRateBean> resultList  = new ArrayList<VwCustomerHourlyBillRateBean>();
////	  try {
////		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id  from vw_customer_hourly_bill_rate_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
////		ps.setInt(ndx++, userProfile_.getClientId());
////		ps.setString(ndx++,  userProfile_.getUserId());
////		ps.setString(ndx++, userProfile_.getSessionId());
////		rs =  ps.executeQuery();
////		while(rs.next()){
////		  resultList.add(decodeRow(rs));
////		}
////	  }catch (Exception e) {
////		e.printStackTrace();
////		throw new GWTServerException("Retrieving VwCustomerHourlyBillRate Records Failed", e);
////	  }
////	  return resultList;
////	}
////
////
////
////
////
////
////	/**
////	 * Retrieve the  the bean from the database by Primary Key
////	 * @param userProfile_ the credentials to use for authentication and authorization
////         * @return an arraylist of the beans
////	 */
////	public  VwCustomerHourlyBillRateBean getVwCustomerHourlyBillRateByPrKey(UserProfile userProfile_ ){
////	  int ndx =1;
////	  PreparedStatement ps;
////	  ResultSet rs;
////	  VwCustomerHourlyBillRateBean result  = new VwCustomerHourlyBillRateBean();
////	  try {
////		ps = databaseManager.getConnection().prepareStatement("select  last_update , customer_id , bill_rate , display_name , user_id , customer_bill_rate_id,  from vw_customer_hourly_bill_rate_bypk('CHECK_AUTH',?,?,? );");
////		ps.setInt(ndx++, userProfile_.getClientId());
////		ps.setString(ndx++,  userProfile_.getUserId());
////		ps.setString(ndx++, userProfile_.getSessionId());
////		rs =  ps.executeQuery();
////		if(rs.next()){
////		  result =(decodeRow(rs));
////		}
////	  }catch (Exception e) {
////		e.printStackTrace();
////		throw new GWTServerException("Retrieving VwCustomerHourlyBillRate Record Failed", e);
////	
////	  }
////	  return result;
////	}
////
////	/**
////	 * Convert a result set a bean
////         * @param rs the result set to be converted
////	 * @return the VwCustomerHourlyBillRateBean that was converted
////         */
//// 	public VwCustomerHourlyBillRateBean decodeRow(ResultSet rs) throws SQLException{
////          VwCustomerHourlyBillRateBean bean = new VwCustomerHourlyBillRateBean();
////          bean.setLastUpdate(rs.getTimestamp(1)); 
////          bean.setCustomerId(rs.getInt(2));
////          bean.setBillRate(rs.getDouble(3));
////          bean.setDisplayName(rs.getString(4));
////          bean.setUserId(rs.getString(5));
////          bean.setCustomerBillRateId(rs.getInt(6));
////          bean.setClientId(newVal);
////          bean.isNew(false);
////          bean.resetIsModified();
////          return bean;
////        }
////
////	/**
////	 * Convert a result set a bean
////         * @param rs the result set to be converted
////	 * @return the VwCustomerHourlyBillRateBean that was converted
////         */
////	public VwCustomerHourlyBillRateBean saveVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
//////		if (){
//////			return insertVwCustomerHourlyBillRateBean( userProfile_,  vwCustomerHourlyBillRateBean_);
//////		}else{
//////			return updateVwCustomerHourlyBillRateBean( userProfile_,  vwCustomerHourlyBillRateBean_);
//////		}
////return null;
////	}
////	/**
////	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
////	 * @param userProfile_ the credentials to use for authentication and authorization
////	 * @param vwCustomerHourlyBillRateBeanList_ the list of beans to save
////         * @return an arraylist of the beans, updated with primary keys and last updates.
////	 */
////	public ArrayList<VwCustomerHourlyBillRateBean> saveVwCustomerHourlyBillRateBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateBeanList_){
////		for(int ndx =0; ndx< vwCustomerHourlyBillRateBeanList_.size(); ndx++){
////			vwCustomerHourlyBillRateBeanList_.set(ndx, saveVwCustomerHourlyBillRateBean(userProfile_,vwCustomerHourlyBillRateBeanList_.get(ndx)));
////		}
////		return  vwCustomerHourlyBillRateBeanList_;
////	}
////
////}
