

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

