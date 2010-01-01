

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.InvoiceService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the Invoice Beans.
 */
public class InvoiceServiceImpl extends RemoteServiceServlet
		implements InvoiceService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public InvoiceServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param invoiceBean_ the bean to add
         * @return the updated bean
	 */
	public InvoiceBean insertInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<InvoiceBean> resultList  = new ArrayList<InvoiceBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from invoice_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDouble(++ndx, invoiceBean_.getPrevBalanceDue());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, invoiceBean_.getInvoiceTotalAmt());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(invoiceBean_.getGeneratedDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(invoiceBean_.getInvoiceDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,invoiceBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,invoiceBean_.getInvoiceId());
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
		throw new GWTServerException("Inserting Invoice Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param invoiceBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public InvoiceBean updateInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<InvoiceBean> resultList  = new ArrayList<InvoiceBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from invoice_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDouble(++ndx, invoiceBean_.getPrevBalanceDue());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDouble(++ndx, invoiceBean_.getInvoiceTotalAmt());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(invoiceBean_.getGeneratedDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setDate(++ndx, new java.sql.Date(invoiceBean_.getInvoiceDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(invoiceBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,invoiceBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,invoiceBean_.getInvoiceId());
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
		throw new GWTServerException("Updating Invoice Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param invoiceBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<InvoiceBean> resultList  = new ArrayList<InvoiceBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from invoice_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,invoiceBean_.getInvoiceId() );
    		ps.setInt(ndx++,invoiceBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(invoiceBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		throw new GWTServerException("Deleting Invoice Record Failed", e);
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
	public ArrayList< InvoiceBean> selectInvoice(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<InvoiceBean> resultList  = new ArrayList<InvoiceBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from invoice_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving Invoice Records Failed", e);
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param invoiceId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  InvoiceBean getInvoiceByPrKey(UserProfile userProfile_ , Integer invoiceId_ , Integer customerId_ ){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  InvoiceBean result  = new InvoiceBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from invoice_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, invoiceId_);
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving Invoice Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the InvoiceBean that was converted
         */
 	public InvoiceBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          InvoiceBean bean = new InvoiceBean();
          bean.setPrevBalanceDue(rs.getDouble(1));
            if(rs.wasNull()){bean.setPrevBalanceDue(null);}
          bean.setInvoiceTotalAmt(rs.getDouble(2));
            if(rs.wasNull()){bean.setInvoiceTotalAmt(null);}
          bean.setGeneratedDt(rs.getDate(3));
            if(rs.wasNull()){bean.setGeneratedDt(null);}
          bean.setInvoiceDt(rs.getDate(4));
            if(rs.wasNull()){bean.setInvoiceDt(null);}
          bean.setLastUpdate(rs.getTimestamp(5));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(6));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(7));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setInvoiceId(rs.getInt(8));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the InvoiceBean that was converted
         */
	public InvoiceBean saveInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_){
		if (  invoiceBean_.getInvoiceId() ==null ||  invoiceBean_.getInvoiceId() ==0   || invoiceBean_.getClientId() ==null ||  invoiceBean_.getClientId() ==0   || invoiceBean_.getCustomerId() ==null ||  invoiceBean_.getCustomerId() ==0  ){
			return insertInvoiceBean( userProfile_,  invoiceBean_);
		}else{
			return updateInvoiceBean( userProfile_,  invoiceBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param invoiceBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<InvoiceBean> saveInvoiceBeanBatch(UserProfile userProfile_, ArrayList<InvoiceBean> invoiceBeanList_){
		for(int ndx =0; ndx< invoiceBeanList_.size(); ndx++){
			invoiceBeanList_.set(ndx, saveInvoiceBean(userProfile_,invoiceBeanList_.get(ndx)));
		}
		return  invoiceBeanList_;
	}

}
