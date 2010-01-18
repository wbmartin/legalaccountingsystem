

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("followup")
public interface FollowupService extends RemoteService{
	 FollowupBean insertFollowupBean(UserProfile userProfile_, FollowupBean followupBean_) throws GWTCustomException;
	 FollowupBean updateFollowupBean(UserProfile userProfile_, FollowupBean followupBean_) throws GWTCustomException;
	 FollowupBean saveFollowupBean(UserProfile userProfile_, FollowupBean followupBean_) throws GWTCustomException;
	 Boolean deleteFollowupBean(UserProfile userProfile_, FollowupBean followupBean_) throws GWTCustomException;
	 ArrayList<FollowupBean> selectFollowup(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<FollowupBean> saveFollowupBeanBatch(UserProfile userProfile_, ArrayList<FollowupBean> followupBeanList_) throws GWTCustomException;
	 FollowupBean getFollowupByPrKey(UserProfile userProfile_ , Integer followupId_ ) throws GWTCustomException;

}
