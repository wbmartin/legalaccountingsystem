

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.ExpenseRegisterService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the ExpenseRegister Beans.
 */
public class ExpenseRegisterServiceImpl extends RemoteServiceServlet
		implements ExpenseRegisterService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public ExpenseRegisterServiceImpl() {
		super();
		
	}
	/**
	 * Add a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseRegisterBean_ the bean to add
         * @return the updated bean
	 */
	public ExpenseRegisterBean insertExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseRegisterBean> resultList  = new ArrayList<ExpenseRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id , client_id , expense_register_id  from expense_register_iq('CHECK_AUTH',?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDate(++ndx, new java.sql.Date(expenseRegisterBean_.getExpenseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		ps.setBoolean(++ndx,expenseRegisterBean_.getInvoiceable() );
  		try{
  			ps.setInt(++ndx,expenseRegisterBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, expenseRegisterBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,expenseRegisterBean_.getDescription() );
   		try{
  			ps.setInt(++ndx,expenseRegisterBean_.getCustomerId());
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
		throw new GWTServerException("Inserting ExpenseRegister Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * Update a record the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseRegisterBean_ the bean to update, new values come through this bean
         * @return the updated bean
	 */
	public ExpenseRegisterBean updateExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseRegisterBean> resultList  = new ArrayList<ExpenseRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id , client_id , expense_register_id  from expense_register_uq('CHECK_AUTH',?,?,?,?,?,?,?,?,?,?,?);");
		ps.setInt(++ndx,  userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		try{
  			ps.setDate(++ndx, new java.sql.Date(expenseRegisterBean_.getExpenseDt().getTime()));
  		}catch(NullPointerException nex){
  			ps.setDate(ndx, new java.sql.Date(0));
  		}
  		try{
  			ps.setBoolean(++ndx, expenseRegisterBean_.getInvoiceable());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.BOOLEAN);
  		}
  		try{
  			ps.setInt(++ndx,expenseRegisterBean_.getInvoiceId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
  		try{
  			ps.setDouble(++ndx, expenseRegisterBean_.getAmount());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.DOUBLE);
  		}
  		ps.setString(++ndx,expenseRegisterBean_.getDescription() );
  		try{
  			ps.setTimestamp(++ndx, new java.sql.Timestamp(expenseRegisterBean_.getLastUpdate().getTime()));
  		}catch(NullPointerException nex){
  			ps.setTimestamp(ndx, new java.sql.Timestamp(0));
  		}
  		try{
  			ps.setInt(++ndx,expenseRegisterBean_.getCustomerId());
  		}catch(NullPointerException nex){
  			ps.setNull(ndx, java.sql.Types.INTEGER);
  		}
   		try{
  			ps.setInt(++ndx,expenseRegisterBean_.getExpenseRegisterId());
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
		throw new GWTServerException("Updating ExpenseRegister Record Failed", e);
	  }
	  return resultList.get(0);
	}


	/**
	 * delete a record from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseRegisterBean_ the bean to delete, only primary keys value
         * @return true if the delete was successful
	 */
	public Boolean deleteExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  Boolean result = false;
	  //ArrayList<ExpenseRegisterBean> resultList  = new ArrayList<ExpenseRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select * from expense_register_dq('CHECK_AUTH',?,?,?, ?, ?, ?);");
		ps.setInt(ndx++,  userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		ps.setInt(ndx++,expenseRegisterBean_.getExpenseRegisterId() );
    		ps.setInt(ndx++,expenseRegisterBean_.getCustomerId() );
   		ps.setTimestamp(ndx++, new java.sql.Timestamp(expenseRegisterBean_.getLastUpdate().getTime()));
		rs =  ps.executeQuery();
		
		while(rs.next()){
		  result = rs.getBoolean(1);
		}
	  }catch (Exception e) {	
		e.printStackTrace();
		result = false;
		throw new GWTServerException("Deleting ExpenseRegister Record Failed", e);
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
	public ArrayList< ExpenseRegisterBean> selectExpenseRegister(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<ExpenseRegisterBean> resultList  = new ArrayList<ExpenseRegisterBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id , client_id , expense_register_id  from expense_register_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving ExpenseRegister Records Failed", e);
	  }
	  return resultList;
	}






	/**
	 * Retrieve the  the bean from the database by Primary Key
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseRegisterId_ 
	 * @param clientId_ 
	 * @param customerId_ 
         * @return an arraylist of the beans
	 */
	public  ExpenseRegisterBean getExpenseRegisterByPrKey(UserProfile userProfile_ , Integer expenseRegisterId_ , Integer customerId_ ){
	  int ndx =0;
	  PreparedStatement ps;
	  ResultSet rs;
	  ExpenseRegisterBean result  = new ExpenseRegisterBean();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  expense_dt , invoiceable , invoice_id , amount , description , last_update , customer_id , client_id , expense_register_id  from expense_register_bypk('CHECK_AUTH',?,?,?, ?, ? );");
		ps.setInt(++ndx, userProfile_.getClientId());
		ps.setString(++ndx,  userProfile_.getUserId());
		ps.setString(++ndx, userProfile_.getSessionId());
		ps.setInt(++ndx, expenseRegisterId_);
		ps.setInt(++ndx, customerId_);
		rs =  ps.executeQuery();
		if(rs.next()){
		  result =(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving ExpenseRegister Record Failed", e);
	
	  }
	  return result;
	}

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the ExpenseRegisterBean that was converted
         */
 	public ExpenseRegisterBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          ExpenseRegisterBean bean = new ExpenseRegisterBean();
          bean.setExpenseDt(rs.getDate(1));
            if(rs.wasNull()){bean.setExpenseDt(null);}
          bean.setInvoiceable(rs.getBoolean(2));
            if(rs.wasNull()){bean.setInvoiceable(null);}
          bean.setInvoiceId(rs.getInt(3));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          bean.setAmount(rs.getDouble(4));
            if(rs.wasNull()){bean.setAmount(null);}
          bean.setDescription(rs.getString(5));
            if(rs.wasNull()){bean.setDescription(null);}
          bean.setLastUpdate(rs.getTimestamp(6));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(7));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(8));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setExpenseRegisterId(rs.getInt(9));
            if(rs.wasNull()){bean.setExpenseRegisterId(null);}
          return bean;
        }

	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the ExpenseRegisterBean that was converted
         */
	public ExpenseRegisterBean saveExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_){
		if (  expenseRegisterBean_.getExpenseRegisterId() ==null ||  expenseRegisterBean_.getExpenseRegisterId() ==0   || expenseRegisterBean_.getClientId() ==null ||  expenseRegisterBean_.getClientId() ==0   || expenseRegisterBean_.getCustomerId() ==null ||  expenseRegisterBean_.getCustomerId() ==0  ){
			return insertExpenseRegisterBean( userProfile_,  expenseRegisterBean_);
		}else{
			return updateExpenseRegisterBean( userProfile_,  expenseRegisterBean_);
		}

	}
	/**
	 * Save a record to the database.  If the primary keys are null, an insert will occur, otherwise an update will occur
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param expenseRegisterBeanList_ the list of beans to save
         * @return an arraylist of the beans, updated with primary keys and last updates.
	 */
	public ArrayList<ExpenseRegisterBean> saveExpenseRegisterBeanBatch(UserProfile userProfile_, ArrayList<ExpenseRegisterBean> expenseRegisterBeanList_){
		for(int ndx =0; ndx< expenseRegisterBeanList_.size(); ndx++){
			expenseRegisterBeanList_.set(ndx, saveExpenseRegisterBean(userProfile_,expenseRegisterBeanList_.get(ndx)));
		}
		return  expenseRegisterBeanList_;
	}

}
