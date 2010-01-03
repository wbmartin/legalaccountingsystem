

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;
import com.martinanalytics.legaltime.client.model.VwLaborInvoiceItemDisplayService;
import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
/**
 * Exposes CRUD and business logic fucntionality for the VwLaborInvoiceItemDisplay Beans.
 */
public class VwLaborInvoiceItemDisplayServiceImpl extends RemoteServiceServlet
		implements VwLaborInvoiceItemDisplayService{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public VwLaborInvoiceItemDisplayServiceImpl() {
		super();
		
	}

	/**
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 */
	public ArrayList< VwLaborInvoiceItemDisplayBean> selectVwLaborInvoiceItemDisplay(UserProfile userProfile_, String whereClause_, String orderByClause_){
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwLaborInvoiceItemDisplayBean> resultList  = new ArrayList<VwLaborInvoiceItemDisplayBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  display_name , user_id , bill_rate , hours_billed , invoice_id , activity_description , activity_dt , last_update , customer_id , client_id , labor_invoice_item_id  from vw_labor_invoice_item_display_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
		ps.setInt(ndx++, userProfile_.getClientId());
		ps.setString(ndx++,  userProfile_.getUserId());
		ps.setString(ndx++, userProfile_.getSessionId());
		rs =  ps.executeQuery();
		while(rs.next()){
		  resultList.add(decodeRow(rs));
		}
	  }catch (Exception e) {
		e.printStackTrace();
		throw new GWTServerException("Retrieving VwLaborInvoiceItemDisplay Records Failed", e);
	  }
	  return resultList;
	}







	/**
	 * Convert a result set a bean
         * @param rs the result set to be converted
	 * @return the VwLaborInvoiceItemDisplayBean that was converted
         */
 	public VwLaborInvoiceItemDisplayBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwLaborInvoiceItemDisplayBean bean = new VwLaborInvoiceItemDisplayBean();
          bean.setDisplayName(rs.getString(1));
            if(rs.wasNull()){bean.setDisplayName(null);}
          bean.setUserId(rs.getString(2));
            if(rs.wasNull()){bean.setUserId(null);}
          bean.setBillRate(rs.getDouble(3));
            if(rs.wasNull()){bean.setBillRate(null);}
          bean.setHoursBilled(rs.getDouble(4));
            if(rs.wasNull()){bean.setHoursBilled(null);}
          bean.setInvoiceId(rs.getInt(5));
            if(rs.wasNull()){bean.setInvoiceId(null);}
          bean.setActivityDescription(rs.getString(6));
            if(rs.wasNull()){bean.setActivityDescription(null);}
          bean.setActivityDt(rs.getDate(7));
            if(rs.wasNull()){bean.setActivityDt(null);}
          bean.setLastUpdate(rs.getTimestamp(8));
            if(bean.getLastUpdate().equals(nullDate)){bean.setLastUpdate(null);} 
          bean.setCustomerId(rs.getInt(9));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(10));
            if(rs.wasNull()){bean.setClientId(null);}
          bean.setLaborInvoiceItemId(rs.getInt(11));
            if(rs.wasNull()){bean.setLaborInvoiceItemId(null);}
          return bean;
        }

	

}
