

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final VwCustomerFollowupServiceAsync vwCustomerFollowupService = 
//	GWT.create(VwCustomerFollowupService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VwCustomerFollowupServiceAsync{
	void  insertVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_, AsyncCallback<VwCustomerFollowupBean> callback);
//	void  saveVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_, AsyncCallback<VwCustomerFollowupBean> callback);
//	void  updateVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_, AsyncCallback<VwCustomerFollowupBean> callback);
//	void  deleteVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_, AsyncCallback<Boolean> result);
	void  selectVwCustomerFollowup(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<VwCustomerFollowupBean>> callback);
//	void  saveVwCustomerFollowupBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerFollowupBean> vwCustomerFollowupBeanList_, AsyncCallback<ArrayList<VwCustomerFollowupBean>> callback);

	void  getVwCustomerFollowupByPrKey(UserProfile userProfile_ , AsyncCallback<VwCustomerFollowupBean> callback);


}
