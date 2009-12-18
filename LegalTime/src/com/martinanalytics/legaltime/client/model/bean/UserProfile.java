package com.martinanalytics.legaltime.client.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;

public class UserProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 114807130397442052L;
	private ArrayList<String> securityGrant;
	private String userId;
	private String sessionId;
	private int clientId;
	static private UserProfile instance = null;
	private java.util.Date sessionTimeOut;

	/**
	 * Warning, you should probably use Get Instance
	 */
	public UserProfile() {
		securityGrant = new ArrayList<String>();
		setSessionId("");
		setUserId("");
		sessionTimeOut = new java.util.Date();
	}

	public static UserProfile getInstance() {
		if (instance == null) {
			instance = new UserProfile();
		}
		return instance;
	}

	public void addGrant(String grant_) {
		securityGrant.add(grant_);
	}

	public void setGrantList(ArrayList<String> grantList_) {
		securityGrant = grantList_;
	}

	public boolean checkGrant(String testGrant_) {
		boolean grantFound = false;
		int itemCount = securityGrant.size();
		for (int ndx = 0; ndx < itemCount; ndx++) {
			if (securityGrant.get(ndx).equals(testGrant_)) {
				grantFound = true;
				break;
			}
		}
		return grantFound;

	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId_) {
		this.sessionId = sessionId_;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	public void incrementSessionTimeOut() {
		sessionTimeOut = new java.util.Date();
		sessionTimeOut.setTime(sessionTimeOut.getTime() + 20 * 60 * 1000);
		Log.debug("Incremeenting Session Timeout" + sessionTimeOut);
	}

	public void expireSession() {
		sessionTimeOut = new java.util.Date();
		sessionTimeOut.setTime(sessionTimeOut.getTime() - 60 * 1000);
		Log.debug("Expiring Session");
	}

	public boolean isValidSession() {
		boolean result = false;
		java.util.Date now = new java.util.Date();
		try {
			Log.debug("Session timeout during check: " + sessionTimeOut);
			if (sessionTimeOut.compareTo(now) > 0 && sessionId != null) {
				result = true;
			}

		} catch (NullPointerException nex) {
			Log.debug("SessionTimout Null Pointer Exception");

			result = false;
		}

		if (result == false) {
			setSessionId(null);
		}
		return result;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

}
