

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final UserInfoServiceAsync userInfoService = 
//	GWT.create(UserInfoService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync{
	void  insertUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_, AsyncCallback<UserInfoBean> callback);
	void  saveUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_, AsyncCallback<UserInfoBean> callback);
	void  updateUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_, AsyncCallback<UserInfoBean> callback);
	void  deleteUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_, AsyncCallback<Boolean> result);
	void  selectUserInfo(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<UserInfoBean>> callback);
	void  saveUserInfoBeanBatch(UserProfile userProfile_, ArrayList<UserInfoBean> userInfoBeanList_, AsyncCallback<ArrayList<UserInfoBean>> callback);

	void  getUserInfoByPrKey(UserProfile userProfile_ , String userId_ , AsyncCallback<UserInfoBean> callback);


}
