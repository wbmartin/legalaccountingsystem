

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwCustomerFollowup")
public interface VwCustomerFollowupService extends RemoteService{
	 VwCustomerFollowupBean insertVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_)throws GWTCustomException;
//	 VwCustomerFollowupBean updateVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_)throws GWTCustomException;
//	 VwCustomerFollowupBean saveVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_)throws GWTCustomException;
//	 Boolean deleteVwCustomerFollowupBean(UserProfile userProfile_, VwCustomerFollowupBean vwCustomerFollowupBean_)throws GWTCustomException;
	 ArrayList<VwCustomerFollowupBean> selectVwCustomerFollowup(UserProfile userProfile_, String whereClause_, String OrderByClause_)throws GWTCustomException;
//	 ArrayList<VwCustomerFollowupBean> saveVwCustomerFollowupBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerFollowupBean> vwCustomerFollowupBeanList_)throws GWTCustomException;
	 VwCustomerFollowupBean getVwCustomerFollowupByPrKey(UserProfile userProfile_ )throws GWTCustomException;

}
