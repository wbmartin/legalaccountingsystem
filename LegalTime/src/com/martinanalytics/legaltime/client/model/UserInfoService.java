

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("userInfo")
public interface UserInfoService extends RemoteService{
	 UserInfoBean insertUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException;
	 UserInfoBean updateUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException;
	 UserInfoBean saveUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException;
	 Boolean deleteUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_) throws GWTCustomException;
	 ArrayList<UserInfoBean> selectUserInfo(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<UserInfoBean> saveUserInfoBeanBatch(UserProfile userProfile_, ArrayList<UserInfoBean> userInfoBeanList_) throws GWTCustomException;
	 UserInfoBean getUserInfoByPrKey(UserProfile userProfile_ , String userId_ ) throws GWTCustomException;

}
