

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("followup")
public interface FollowupService extends RemoteService{
	 FollowupBean insertFollowupBean(UserProfile userProfile_, FollowupBean followupBean_);
	 FollowupBean updateFollowupBean(UserProfile userProfile_, FollowupBean followupBean_);
	 FollowupBean saveFollowupBean(UserProfile userProfile_, FollowupBean followupBean_);
	 Boolean deleteFollowupBean(UserProfile userProfile_, FollowupBean followupBean_);
	 ArrayList<FollowupBean> selectFollowup(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<FollowupBean> saveFollowupBeanBatch(UserProfile userProfile_, ArrayList<FollowupBean> followupBeanList_);
	 FollowupBean getFollowupByPrKey(UserProfile userProfile_ , Integer followupId_ );

}
