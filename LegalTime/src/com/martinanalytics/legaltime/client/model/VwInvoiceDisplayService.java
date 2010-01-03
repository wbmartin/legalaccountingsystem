

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwInvoiceDisplay")
public interface VwInvoiceDisplayService extends RemoteService{
//	 VwInvoiceDisplayBean insertVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_);
//	 VwInvoiceDisplayBean updateVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_);
//	 VwInvoiceDisplayBean saveVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_);
//	 Boolean deleteVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_);
	 ArrayList<VwInvoiceDisplayBean> selectVwInvoiceDisplay(UserProfile userProfile_, String whereClause_, String OrderByClause_);
//	 ArrayList<VwInvoiceDisplayBean> saveVwInvoiceDisplayBeanBatch(UserProfile userProfile_, ArrayList<VwInvoiceDisplayBean> vwInvoiceDisplayBeanList_);
//	 VwInvoiceDisplayBean getVwInvoiceDisplayByPrKey(UserProfile userProfile_ );

}
