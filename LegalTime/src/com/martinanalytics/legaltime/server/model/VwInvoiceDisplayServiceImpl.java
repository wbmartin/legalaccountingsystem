

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.VwInvoiceDisplayService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the VwInvoiceDisplay Beans.
 */
public class VwInvoiceDisplayServiceImpl extends RemoteServiceServlet
		implements VwInvoiceDisplayService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public VwInvoiceDisplayServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param vwInvoiceDisplayBean_ the bean to add
         * @return the updated bean
	 */
//	public VwInvoiceDisplayBean insertVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_){
//	  int ndx =0;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  String result;
//	  ArrayList<VwInvoiceDisplayBean> resultList  = new ArrayList<VwInvoiceDisplayBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  display_name , last_name , first_name , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from vw_invoice_display_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?);");
//		ps.setInt(++ndx, userProfile_.getClientId());
//		ps.setString(++ndx,  userProfile_.getUserId());
//		ps.setString(++ndx, userProfile_.getSessionId());
//		ps.setString(++ndx,vwInvoiceDisplayBean_.getDisplayName() );
//  		ps.setString(++ndx,vwInvoiceDisplayBean_.getLastName() );
//  		ps.setString(++ndx,vwInvoiceDisplayBean_.getFirstName() );
//  		try{
//  			ps.setDouble(++ndx, vwInvoiceDisplayBean_.getPrevBalanceDue());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.DOUBLE);
//  		}
//  		try{
//  			ps.setDouble(++ndx, vwInvoiceDisplayBean_.getInvoiceTotalAmt());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.DOUBLE);
//  		}
//  		try{
//  			ps.setDate(++ndx, new java.sql.Date(vwInvoiceDisplayBean_.getGeneratedDt().getTime()));
//  		}catch(NullPointerException nex){
//  			ps.setDate(ndx, new java.sql.Date(0));
//  		}
//  		try{
//  			ps.setDate(++ndx, new java.sql.Date(vwInvoiceDisplayBean_.getInvoiceDt().getTime()));
//  		}catch(NullPointerException nex){
//  			ps.setDate(ndx, new java.sql.Date(0));
//  		}
//   		try{
//  			ps.setInt(++ndx,vwInvoiceDisplayBean_.getCustomerId());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.INTEGER);
//  		}
//   		try{
//  			ps.setInt(++ndx,vwInvoiceDisplayBean_.getInvoiceId());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.INTEGER);
//  		}
//   		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  resultList.add(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		result = "FAIL";
//		throw new GWTServerException("Inserting VwInvoiceDisplay Record Failed", e);
//	  }
//	  return resultList.get(0);
//	}
//
//
//	/**
//	 * Update a record the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwInvoiceDisplayBean_ the bean to update, new values come through this bean
//         * @return the updated bean
//	 */
//	public VwInvoiceDisplayBean updateVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_){
//	  int ndx =0;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  String result;
//	  ArrayList<VwInvoiceDisplayBean> resultList  = new ArrayList<VwInvoiceDisplayBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  display_name , last_name , first_name , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from vw_invoice_display_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?,?,?);");
//		ps.setInt(++ndx,  userProfile_.getClientId());
//		ps.setString(++ndx,  userProfile_.getUserId());
//		ps.setString(++ndx, userProfile_.getSessionId());
//		ps.setString(++ndx,vwInvoiceDisplayBean_.getDisplayName() );
//  		ps.setString(++ndx,vwInvoiceDisplayBean_.getLastName() );
//  		ps.setString(++ndx,vwInvoiceDisplayBean_.getFirstName() );
//  		try{
//  			ps.setDouble(++ndx, vwInvoiceDisplayBean_.getPrevBalanceDue());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.DOUBLE);
//  		}
//  		try{
//  			ps.setDouble(++ndx, vwInvoiceDisplayBean_.getInvoiceTotalAmt());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.DOUBLE);
//  		}
//  		try{
//  			ps.setDate(++ndx, new java.sql.Date(vwInvoiceDisplayBean_.getGeneratedDt().getTime()));
//  		}catch(NullPointerException nex){
//  			ps.setDate(ndx, new java.sql.Date(0));
//  		}
//  		try{
//  			ps.setDate(++ndx, new java.sql.Date(vwInvoiceDisplayBean_.getInvoiceDt().getTime()));
//  		}catch(NullPointerException nex){
//  			ps.setDate(ndx, new java.sql.Date(0));
//  		}
//  		try{
//  			ps.setTimestamp(++ndx, new java.sql.Timestamp(vwInvoiceDisplayBean_.getLastUpdate().getTime()));
//  		}catch(NullPointerException nex){
//  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
//  		}
//  		try{
//  			ps.setInt(++ndx,vwInvoiceDisplayBean_.getCustomerId());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.INTEGER);
//  		}
//   		try{
//  			ps.setInt(++ndx,vwInvoiceDisplayBean_.getInvoiceId());
//  		}catch(NullPointerException nex){
//  			ps.setNull(ndx, java.sql.Types.INTEGER);
//  		}
//   		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  resultList.add(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		result = "FAIL";
//		throw new GWTServerException("Updating VwInvoiceDisplay Record Failed", e);
//	  }
//	  return resultList.get(0);
//	}
//
//
//	/**
//	 * delete a record from the database
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwInvoiceDisplayBean_ the bean to delete, only primary keys value
//         * @return true if the delete was successful
//	 */
//	public Boolean deleteVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_){
//	  int ndx =1;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  Boolean result = false;
//	  //ArrayList<VwInvoiceDisplayBean> resultList  = new ArrayList<VwInvoiceDisplayBean>();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select * from vw_invoice_display_dq('CHECK_AUTH',?,?,?, ?);");
//		ps.setInt(ndx++,  userProfile_.getClientId());
//		ps.setString(ndx++,  userProfile_.getUserId());
//		ps.setString(ndx++, userProfile_.getSessionId());
// 		ps.setTimestamp(ndx++, new java.sql.Timestamp(vwInvoiceDisplayBean_.getLastUpdate().getTime()));
//		rs =  ps.executeQuery();
//		
//		while(rs.next()){
//		  result = rs.getBoolean(1);
//		}
//	  }catch (Exception e) {	
//		e.printStackTrace();
//		result = false;
//		throw new GWTServerException("Deleting VwInvoiceDisplay Record Failed", e);
//	  }
//
//
//	  return result;
//
//	
//	}


	/**
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 */
	public ArrayList< VwInvoiceDisplayBean> selectVwInvoiceDisplay(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwInvoiceDisplayBean> resultList  = new ArrayList<VwInvoiceDisplayBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  display_name , last_name , first_name , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from vw_invoice_display_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving VwInvoiceDisplay Records Failed", e);
	  }
	  return resultList;
	}





//
//	/**
//	 * Retrieve the  the bean from the database by Primary Key
//	 * @param userProfile_ the credentials to use for authentication and authorization
//         * @return an arraylist of the beans
//	 */
//	public  VwInvoiceDisplayBean getVwInvoiceDisplayByPrKey(UserProfile userProfile_ ){
//	  int ndx =0;
//	  PreparedStatement ps;
//	  ResultSet rs;
//	  VwInvoiceDisplayBean result  = new VwInvoiceDisplayBean();
//	  try {
//		ps = databaseManager.getConnection().prepareStatement("select  display_name , last_name , first_name , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from vw_invoice_display_bypk('CHECK_AUTH',?,?,? );");
//		ps.setInt(++ndx, userProfile_.getClientId());
//		ps.setString(++ndx,  userProfile_.getUserId());
//		ps.setString(++ndx, userProfile_.getSessionId());
//		rs =  ps.executeQuery();
//		if(rs.next()){
//		  result =(decodeRow(rs));
//		}
//	  }catch (Exception e) {
//		e.printStackTrace();
//		throw new GWTServerException("Retrieving VwInvoiceDisplay Record Failed", e);
//	
//	  }
//	  return result;
//	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the VwInvoiceDisplayBean that was converted
         */
 	public VwInvoiceDisplayBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwInvoiceDisplayBean bean = new VwInvoiceDisplayBean();
          bean.setDisplayName(rs.getString(1));
            if(rs.wasNull()){bean.setDisplayName(null);}
          bean.setLastName(rs.getString(2));
            if(rs.wasNull()){bean.setLastName(null);}
          bean.setFirstName(rs.getString(3));
            if(rs.wasNull()){bean.setFirstName(null);}
          bean.setPrevBalanceDue(rs.getDouble(4));
            if(rs.wasNull()){bean.setPrevBalanceDue(null);}
          bean.setInvoiceTotalAmt(rs.getDouble(5));
            if(rs.wasNull()){bean.setInvoiceTotalAmt(null);}
          bean.setGeneratedDt(rs.getDate(6));
            if(rs.wasNull()){bean.setGeneratedDt(null);}
          bean.setInvoiceDt(rs.getDate(7));
            if(rs.wasNull()){bean.setInvoiceDt(null);}
          bean.setLastUpdate(rs.getTimestamp(8));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(9));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(10));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setInvoiceId(rs.getInt(11));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          return bean;
        }

//	/**
//	 * Convert a result set a bean
//         * @param rs the result set to be converted
//	 * @return the VwInvoiceDisplayBean that was converted
//         */
//	public VwInvoiceDisplayBean saveVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_){
//		if (){
//			return insertVwInvoiceDisplayBean( userProfile_,  vwInvoiceDisplayBean_);
//		}else{
//			return updateVwInvoiceDisplayBean( userProfile_,  vwInvoiceDisplayBean_);
//		}
//
//	}
//	/**
//	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
//	 * @param userProfile_ the credentials to use for authentication and authorization
//	 * @param vwInvoiceDisplayBeanList_ the list of beans to save
//         * @return an arraylist of the beans, updated with primary keys and last updates.
//	 */
//	public ArrayList<VwInvoiceDisplayBean> saveVwInvoiceDisplayBeanBatch(UserProfile userProfile_, ArrayList<VwInvoiceDisplayBean> vwInvoiceDisplayBeanList_){
//		for(int ndx =0; ndx< vwInvoiceDisplayBeanList_.size(); ndx++){
//			vwInvoiceDisplayBeanList_.set(ndx, saveVwInvoiceDisplayBean(userProfile_,vwInvoiceDisplayBeanList_.get(ndx)));
//		}
//		return  vwInvoiceDisplayBeanList_;
//	}

}
