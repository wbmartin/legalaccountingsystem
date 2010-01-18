

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
//private final FollowupServiceAsync followupService = 
//	GWT.create(FollowupService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FollowupServiceAsync{
	void  insertFollowupBean(UserProfile userProfile_, FollowupBean followupBean_, AsyncCallback<FollowupBean> callback) ;
	void  saveFollowupBean(UserProfile userProfile_, FollowupBean followupBean_, AsyncCallback<FollowupBean> callback) ;
	void  updateFollowupBean(UserProfile userProfile_, FollowupBean followupBean_, AsyncCallback<FollowupBean> callback) ;
	void  deleteFollowupBean(UserProfile userProfile_, FollowupBean followupBean_, AsyncCallback<Boolean> result) ;
	void  selectFollowup(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<FollowupBean>> callback) ;
	void  saveFollowupBeanBatch(UserProfile userProfile_, ArrayList<FollowupBean> followupBeanList_, AsyncCallback<ArrayList<FollowupBean>> callback) ;

	void  getFollowupByPrKey(UserProfile userProfile_ , Integer followupId_ , AsyncCallback<FollowupBean> callback) ;


}
