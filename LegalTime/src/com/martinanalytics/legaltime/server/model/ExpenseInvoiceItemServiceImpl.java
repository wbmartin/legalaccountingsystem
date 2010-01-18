

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.ExpenseInvoiceItemService;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the ExpenseInvoiceItem Beans.
 */
public class ExpenseInvoiceItemServiceImpl extends RemoteServiceServlet
		implements ExpenseInvoiceItemService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public ExpenseInvoiceItemServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseInvoiceItemBean_ the bean to add
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public ExpenseInvoiceItemBean insertExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseInvoiceItemBean> resultList  = new ArrayList<ExpenseInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  invoice_id , amount , expense_description , expense_dt , last_update , customer_id , client_id , expense_invoice_item_id  from expense_invoice_item_iq('CHECK_AUTH',?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setInt(++ndx,expenseInvoiceItemBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, expenseInvoiceItemBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,expenseInvoiceItemBean_.getExpenseDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(expenseInvoiceItemBean_.getExpenseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
   		try{
  			ps.setInt(++ndx,expenseInvoiceItemBean_.getCustomerId());
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
			throw new GWTServerException("Inserting ExpenseInvoiceItem Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseInvoiceItemBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 * @throws GWTCustomException 
	 */
	public ExpenseInvoiceItemBean updateExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException{
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseInvoiceItemBean> resultList  = new ArrayList<ExpenseInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  invoice_id , amount , expense_description , expense_dt , last_update , customer_id , client_id , expense_invoice_item_id  from expense_invoice_item_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setInt(++ndx,expenseInvoiceItemBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, expenseInvoiceItemBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,expenseInvoiceItemBean_.getExpenseDescription() );
  		try{
  			ps.setDate(++ndx, new java.sql.Date(expenseInvoiceItemBean_.getExpenseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(expenseInvoiceItemBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,expenseInvoiceItemBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,expenseInvoiceItemBean_.getExpenseInvoiceItemId());
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
			throw new GWTServerException("Updating ExpenseInvoiceItem Record Failed", e);
		}
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseInvoiceItemBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 * @throws GWTCustomException 
	 */
	public Boolean deleteExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<ExpenseInvoiceItemBean> resultList  = new ArrayList<ExpenseInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from expense_invoice_item_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,expenseInvoiceItemBean_.getExpenseInvoiceItemId() );
    		ps.setInt(ndx++,expenseInvoiceItemBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(expenseInvoiceItemBean_.getLastUpdate().getTime()));
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
			throw new GWTServerException("Deleting ExpenseInvoiceItem Record Failed", e);
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
	public ArrayList< ExpenseInvoiceItemBean> selectExpenseInvoiceItem(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseInvoiceItemBean> resultList  = new ArrayList<ExpenseInvoiceItemBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  invoice_id , amount , expense_description , expense_dt , last_update , customer_id , client_id , expense_invoice_item_id  from expense_invoice_item_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTServerException("Retrieving ExpenseInvoiceItem Records Failed", e);
		}
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseInvoiceItemId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  ExpenseInvoiceItemBean getExpenseInvoiceItemByPrKey(UserProfile userProfile_ , Integer expenseInvoiceItemId_ , Integer customerId_ ){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  ExpenseInvoiceItemBean result  = new ExpenseInvoiceItemBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  invoice_id , amount , expense_description , expense_dt , last_update , customer_id , client_id , expense_invoice_item_id  from expense_invoice_item_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, expenseInvoiceItemId_);
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving ExpenseInvoiceItem Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the ExpenseInvoiceItemBean that was converted
         */
 	public ExpenseInvoiceItemBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          ExpenseInvoiceItemBean bean = new ExpenseInvoiceItemBean();
          bean.setInvoiceId(rs.getInt(1));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          bean.setAmount(rs.getDouble(2));
            if(rs.wasNull()){bean.setAmount(null);}
          bean.setExpenseDescription(rs.getString(3));
            if(rs.wasNull()){bean.setExpenseDescription(null);}
          bean.setExpenseDt(rs.getDate(4));
            if(rs.wasNull()){bean.setExpenseDt(null);}
          bean.setLastUpdate(rs.getTimestamp(5));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(6));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(7));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setExpenseInvoiceItemId(rs.getInt(8));
            if(rs.wasNull()){bean.setExpenseInvoiceItemId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the ExpenseInvoiceItemBean that was converted
	 * @throws GWTCustomException 
         */
	public ExpenseInvoiceItemBean saveExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException{
		if (  expenseInvoiceItemBean_.getExpenseInvoiceItemId() ==null ||  expenseInvoiceItemBean_.getExpenseInvoiceItemId() ==0   || expenseInvoiceItemBean_.getClientId() ==null ||  expenseInvoiceItemBean_.getClientId() ==0   || expenseInvoiceItemBean_.getCustomerId() ==null ||  expenseInvoiceItemBean_.getCustomerId() ==0  ){
			return insertExpenseInvoiceItemBean( userProfile_,  expenseInvoiceItemBean_);
		}else{
			return updateExpenseInvoiceItemBean( userProfile_,  expenseInvoiceItemBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseInvoiceItemBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 * @throws GWTCustomException 
	 */
	public ArrayList<ExpenseInvoiceItemBean> saveExpenseInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<ExpenseInvoiceItemBean> expenseInvoiceItemBeanList_) throws GWTCustomException{
		for(int ndx =0; ndx< expenseInvoiceItemBeanList_.size(); ndx++){
			expenseInvoiceItemBeanList_.set(ndx, saveExpenseInvoiceItemBean(userProfile_,expenseInvoiceItemBeanList_.get(ndx)));
		}
		return  expenseInvoiceItemBeanList_;
	}

}
