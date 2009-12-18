

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwCustomerFollowup")
public interface VwCustomerFollowupService extends RemoteService{
	 VwCustomerFollowupBean insertVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_);
//	 VwCustomerFollowupBean updateVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_);
//	 VwCustomerFollowupBean saveVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_);
//	 Boolean deleteVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_);
	 ArrayList<VwCustomerFollowupBean> selectVwCustomerFollowup(UserProfile userProfile_, String whereClause_, String OrderByClause_);
//	 ArrayList<VwCustomerFollowupBean> saveVwCustomerFollowupBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerFollowupBean> vwCustomerFollowupBeanList_);
	 VwCustomerFollowupBean getVwCustomerFollowupByPrKey(UserProfile userProfile_ );

}
