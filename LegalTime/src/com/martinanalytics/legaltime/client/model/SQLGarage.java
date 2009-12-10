package com.martinanalytics.legaltime.client.model;


public class SQLGarage {
	
	public static String getLoginAuthenticationSQL(){
		String sqlResult ="select * from  initSession(?,?);";
		// 1 - username
		// 2 - password
		
		return sqlResult;
	}
	/**
	 * Creates Prepared Statement String for parameters
	 * <ul>
	 * 	<li>1 client</li>
	 * 	<li>2 username</li>
	 *	<li>3 sessionId</li>
	 *  <li>4 subject</li>
	 *  <li>5 MsgBody</li>
	 *  </ul>
	 * @return String
	 */
	public static String getSaveMsgSQL(){
		//									 1 2 3 4 5
		String sqlResult ="select saveEmail	(?,?,?,?,?);";
		return sqlResult;
	}

}
