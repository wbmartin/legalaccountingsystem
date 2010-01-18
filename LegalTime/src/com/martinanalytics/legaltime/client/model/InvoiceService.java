

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("invoice")
public interface InvoiceService extends RemoteService{
	 InvoiceBean insertInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_) throws GWTCustomException;
	 InvoiceBean updateInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_) throws GWTCustomException;
	 InvoiceBean saveInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_) throws GWTCustomException;
	 Boolean deleteInvoiceBean(UserProfile userProfile_, InvoiceBean invoiceBean_) throws GWTCustomException;
	 ArrayList<InvoiceBean> selectInvoice(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<InvoiceBean> saveInvoiceBeanBatch(UserProfile userProfile_, ArrayList<InvoiceBean> invoiceBeanList_) throws GWTCustomException;
	 InvoiceBean getInvoiceByPrKey(UserProfile userProfile_ , Integer invoiceId_ , Integer customerId_ ) throws GWTCustomException;
	  Integer createInvoiceFromEligibleTrans(UserProfile userProfile_, Integer customerId_, java.util.Date invoiceDt_) throws GWTCustomException;
	  Boolean unwindInvoice(UserProfile userProfile_, Integer invoiceId_)throws GWTCustomException;
	  ArrayList<Integer> invoiceAllHourlyClients(UserProfile userProfile_, java.util.Date invoiceDt_) throws GWTCustomException;
}
