

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
//private final LaborInvoiceItemServiceAsync laborInvoiceItemService = 
//	GWT.create(LaborInvoiceItemService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LaborInvoiceItemServiceAsync{
	void  insertLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_, AsyncCallback<LaborInvoiceItemBean> callback) ;
	void  saveLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_, AsyncCallback<LaborInvoiceItemBean> callback) ;
	void  updateLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_, AsyncCallback<LaborInvoiceItemBean> callback) ;
	void  deleteLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_, AsyncCallback<Boolean> result) ;
	void  selectLaborInvoiceItem(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<LaborInvoiceItemBean>> callback) ;
	void  saveLaborInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<LaborInvoiceItemBean> laborInvoiceItemBeanList_, AsyncCallback<ArrayList<LaborInvoiceItemBean>> callback) ;

	void  getLaborInvoiceItemByPrKey(UserProfile userProfile_ , Integer laborInvoiceItemId_ , Integer customerId_ , AsyncCallback<LaborInvoiceItemBean> callback) ;


}
