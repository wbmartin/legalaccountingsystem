
package com.martinanalytics.legaltime.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.martinanalytics.legaltime.client.model.ApplicationSecurityService;
import com.martinanalytics.legaltime.client.model.SQLGarage;
import com.martinanalytics.legaltime.client.model.bean.SecurityUserBean;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
import com.martinanalytics.legaltime.server.model.DatabaseManager;

@RemoteServiceRelativePath("applicationSecurity")
public class ApplicationSecurityServiceImpl 
			extends RemoteServiceServlet 
			implements ApplicationSecurityService {

	private DatabaseManager databaseManager = DatabaseManager.getInstance();
	
	/**
	 * AppSec
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public SecurityUserBean authenticateUser(String userId_, String password_)throws GWTCustomException{
		PreparedStatement ps;
		SecurityUserBean result = new SecurityUserBean();
		try {
			ps = databaseManager.getConnection().prepareStatement(
					SQLGarage.getLoginAuthenticationSQL());
			ps.setString(1,userId_);
			ps.setString(2, password_);
			ResultSet rs =  ps.executeQuery();
			if (rs.next() ){
				//System.err.println("got next");
				result.setClientId(  rs.getInt(1));
				result.setUserId(rs.getString(2));
				result.setSessionId(rs.getString(6));
			}
			else{
				result.setClientId(0);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			result.setClientId(0);
			throw new GWTCustomException("Authentication Failure", e);
		}
		System.err.println("SessionID: " +result.getSessionId());
		return result;
	}

}
