

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final InvoiceServiceAsync invoiceService = 
//	GWT.create(InvoiceService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InvoiceServiceAsync{
	void  insertInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_, AsyncCallback<InvoiceBean> callback);
	void  saveInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_, AsyncCallback<InvoiceBean> callback);
	void  updateInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_, AsyncCallback<InvoiceBean> callback);
	void  deleteInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_, AsyncCallback<Boolean> result);
	void  selectInvoice(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<InvoiceBean>> callback);
	void  saveInvoiceBeanBatch(UserProfile userProfile_, ArrayList<InvoiceBean> invoiceBeanList_, AsyncCallback<ArrayList<InvoiceBean>> callback);

	void  getInvoiceByPrKey(UserProfile userProfile_ , Integer invoiceId_ , Integer customerId_ , AsyncCallback<InvoiceBean> callback);


}
