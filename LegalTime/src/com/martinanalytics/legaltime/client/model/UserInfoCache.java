package com.martinanalytics.legaltime.client.model;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.controller.UserInfoController;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;

public class UserInfoCache {
	static ArrayList<UserInfoBean> cache = new ArrayList<UserInfoBean>();
	static java.util.Date lastUpdate;
	private static AppNotifyObject notifier = new AppNotifyObject();
	
	public static ArrayList<UserInfoBean> getCache(){
		if(lastUpdate == null){
			  refreshCache();
			  lastUpdate = new java.util.Date();
		}
		
		return cache;
		
	}
	
public static void refreshCache(){	
	final java.util.Date startTime = new java.util.Date();
	final UserInfoServiceAsync userInfoService = 
		GWT.create(UserInfoService.class); 		// primary GWT remote Service
	userInfoService.selectUserInfo(UserProfile.getInstance(), "", "order by display_name", 
			new AsyncCallback<ArrayList<UserInfoBean>>(){
				public void onFailure(Throwable caught) {
						Log.debug("USER INFO CACHE RETRIEVE FAILED");
				}
	
				public void onSuccess(ArrayList<UserInfoBean> userInfoResult) {
						cache = userInfoResult;
						lastUpdate = new java.util.Date();
						notifier.notifyAppEvent(this, "USER_INFO_CACHE_REFRESHED");
					
				}
	});
}

/**
 * @param notifier the notifier to set
 */
public static void setNotifier(AppNotifyObject notifier) {
	UserInfoCache.notifier = notifier;
}

/**
 * @return the notifier
 */
public static AppNotifyObject getNotifier() {
	return notifier;
}

}
