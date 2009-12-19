

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("userInfo")
public interface UserInfoService extends RemoteService{
	 UserInfoBean insertUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_);
	 UserInfoBean updateUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_);
	 UserInfoBean saveUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_);
	 Boolean deleteUserInfoBean(UserProfile userProfile_, UserInfoBean userInfoBean_);
	 ArrayList<UserInfoBean> selectUserInfo(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<UserInfoBean> saveUserInfoBeanBatch(UserProfile userProfile_, ArrayList<UserInfoBean> userInfoBeanList_);
	 UserInfoBean getUserInfoByPrKey(UserProfile userProfile_ , String userId_ );

}
