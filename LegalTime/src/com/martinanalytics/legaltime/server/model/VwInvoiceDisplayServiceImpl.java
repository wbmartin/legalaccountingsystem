

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
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
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
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 * @throws GWTCustomException 
	 */
	public ArrayList< VwInvoiceDisplayBean> selectVwInvoiceDisplay(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwInvoiceDisplayBean> resultList  = new ArrayList<VwInvoiceDisplayBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  contingency_rate , mortgage_amount , active_yn , monthly_bill_rate , bill_type , client_since_dt , email , fax , home_phone , work_phone , zip , state , city , address , display_name , last_name , first_name , prev_balance_due , invoice_total_amt , generated_dt , invoice_dt , last_update , customer_id , client_id , invoice_id  from vw_invoice_display_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
			throw new GWTServerException("Retrieving VwInvoiceDisplay Records Failed", e);
		}
	  }
	  return resultList;
	}





	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the VwInvoiceDisplayBean that was converted
         */
 	public VwInvoiceDisplayBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwInvoiceDisplayBean bean = new VwInvoiceDisplayBean();
          bean.setContingencyRate(rs.getDouble(1));
            if(rs.wasNull()){bean.setContingencyRate(null);}
          bean.setMortgageAmount(rs.getDouble(2));
            if(rs.wasNull()){bean.setMortgageAmount(null);}
          bean.setActiveYn(rs.getString(3));
            if(rs.wasNull()){bean.setActiveYn(null);}
          bean.setMonthlyBillRate(rs.getDouble(4));
            if(rs.wasNull()){bean.setMonthlyBillRate(null);}
          bean.setBillType(rs.getString(5));
            if(rs.wasNull()){bean.setBillType(null);}
          bean.setClientSinceDt(rs.getDate(6));
            if(rs.wasNull()){bean.setClientSinceDt(null);}
          bean.setEmail(rs.getString(7));
            if(rs.wasNull()){bean.setEmail(null);}
          bean.setFax(rs.getString(8));
            if(rs.wasNull()){bean.setFax(null);}
          bean.setHomePhone(rs.getString(9));
            if(rs.wasNull()){bean.setHomePhone(null);}
          bean.setWorkPhone(rs.getString(10));
            if(rs.wasNull()){bean.setWorkPhone(null);}
          bean.setZip(rs.getString(11));
            if(rs.wasNull()){bean.setZip(null);}
          bean.setState(rs.getString(12));
            if(rs.wasNull()){bean.setState(null);}
          bean.setCity(rs.getString(13));
            if(rs.wasNull()){bean.setCity(null);}
          bean.setAddress(rs.getString(14));
            if(rs.wasNull()){bean.setAddress(null);}
          bean.setDisplayName(rs.getString(15));
            if(rs.wasNull()){bean.setDisplayName(null);}
          bean.setLastName(rs.getString(16));
            if(rs.wasNull()){bean.setLastName(null);}
          bean.setFirstName(rs.getString(17));
            if(rs.wasNull()){bean.setFirstName(null);}
          bean.setPrevBalanceDue(rs.getDouble(18));
            if(rs.wasNull()){bean.setPrevBalanceDue(null);}
          bean.setInvoiceTotalAmt(rs.getDouble(19));
            if(rs.wasNull()){bean.setInvoiceTotalAmt(null);}
          bean.setGeneratedDt(rs.getDate(20));
            if(rs.wasNull()){bean.setGeneratedDt(null);}
          bean.setInvoiceDt(rs.getDate(21));
            if(rs.wasNull()){bean.setInvoiceDt(null);}
          bean.setLastUpdate(rs.getTimestamp(22));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(23));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(24));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setInvoiceId(rs.getInt(25));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          return bean;
        }

}
