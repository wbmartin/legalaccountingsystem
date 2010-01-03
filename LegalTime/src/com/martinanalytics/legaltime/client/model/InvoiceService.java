

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("invoice")
public interface InvoiceService extends RemoteService{
	 InvoiceBean insertInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_);
	 InvoiceBean updateInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_);
	 InvoiceBean saveInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_);
	 Boolean deleteInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_);
	 ArrayList<InvoiceBean> selectInvoice(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<InvoiceBean> saveInvoiceBeanBatch(UserProfile userProfile_, ArrayList<InvoiceBean> invoiceBeanList_);
	 InvoiceBean getInvoiceByPrKey(UserProfile userProfile_ , Integer invoiceId_ , Integer customerId_ );
	  Integer createInvoiceFromEligibleTrans(UserProfile userProfile_, Integer customerId_, java.util.Date invoiceDt_);

}
