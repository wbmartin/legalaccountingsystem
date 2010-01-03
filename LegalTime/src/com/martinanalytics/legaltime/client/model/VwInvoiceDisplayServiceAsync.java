

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final VwInvoiceDisplayServiceAsync vwInvoiceDisplayService = 
//	GWT.create(VwInvoiceDisplayService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VwInvoiceDisplayServiceAsync{
//	void  insertVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_, AsyncCallback<VwInvoiceDisplayBean> callback);
//	void  saveVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_, AsyncCallback<VwInvoiceDisplayBean> callback);
//	void  updateVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_, AsyncCallback<VwInvoiceDisplayBean> callback);
//	void  deleteVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_, AsyncCallback<Boolean> result);
	void  selectVwInvoiceDisplay(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<VwInvoiceDisplayBean>> callback);
//	void  saveVwInvoiceDisplayBeanBatch(UserProfile userProfile_, ArrayList<VwInvoiceDisplayBean> vwInvoiceDisplayBeanList_, AsyncCallback<ArrayList<VwInvoiceDisplayBean>> callback);
//
//	void  getVwInvoiceDisplayByPrKey(UserProfile userProfile_ , AsyncCallback<VwInvoiceDisplayBean> callback);


}
