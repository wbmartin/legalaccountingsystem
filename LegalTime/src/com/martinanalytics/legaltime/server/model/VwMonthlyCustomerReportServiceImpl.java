

package com.martinanalytics.legaltime.server.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwMonthlyCustomerReportBean;

import com.martinanalytics.legaltime.server.model.DatabaseManager;
import com.martinanalytics.legaltime.server.GWTServerException;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
/**
 * Exposes CRUD and business logic fucntionality for the VwMonthlyCustomerReport Beans.
 */
public class VwMonthlyCustomerReportServiceImpl extends RemoteServiceServlet
		{
	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	private static final long serialVersionUID = 1L;
	public VwMonthlyCustomerReportServiceImpl() {
		super();
		
	}
	

	/**
	 * Retrieve the entire list of beans from the database
	 * @param userProfile_ the credentials to use for authentication and authorization
	 * @param whereClause_ the filter to apply to the list, should begin with "where"
	 * @param orderByClause_ the sorting order in standard SQL, should being with "order by"
         * @return an arraylist of the beans
	 */
	public ArrayList< VwMonthlyCustomerReportBean> selectVwMonthlyCustomerReport(UserProfile userProfile_, String whereClause_, String orderByClause_) throws GWTCustomException{
	  int ndx =1;
	  PreparedStatement ps;
	  ResultSet rs;
	  String result;
	  ArrayList<VwMonthlyCustomerReportBean> resultList  = new ArrayList<VwMonthlyCustomerReportBean>();
	  try {
		ps = databaseManager.getConnection().prepareStatement("select  escrow , months_unpaid , first_name , last_name , customer_id , client_id  from vw_monthly_customer_report_sq('CHECK_AUTH',?,?,?) " + whereClause_ + " " + orderByClause_+ ";");
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
	 * @return the VwMonthlyCustomerReportBean that was converted
         */
 	public VwMonthlyCustomerReportBean decodeRow(ResultSet rs) throws SQLException{
	  java.util.Date nullDate = new java.util.Date(0);
          VwMonthlyCustomerReportBean bean = new VwMonthlyCustomerReportBean();
          bean.setEscrow(rs.getString(1));
            if(rs.wasNull()){bean.setEscrow(null);}
          bean.setMonthsUnpaid(rs.getDouble(2));
            if(rs.wasNull()){bean.setMonthsUnpaid(null);}
          bean.setFirstName(rs.getString(3));
            if(rs.wasNull()){bean.setFirstName(null);}
          bean.setLastName(rs.getString(4));
            if(rs.wasNull()){bean.setLastName(null);}
          bean.setCustomerId(rs.getInt(5));
            if(rs.wasNull()){bean.setCustomerId(null);}
          bean.setClientId(rs.getInt(6));
            if(rs.wasNull()){bean.setClientId(null);}
          return bean;
        }


}
