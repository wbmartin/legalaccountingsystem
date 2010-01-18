

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("laborInvoiceItem")
public interface LaborInvoiceItemService extends RemoteService{
	 LaborInvoiceItemBean insertLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException;
	 LaborInvoiceItemBean updateLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException;
	 LaborInvoiceItemBean saveLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException;
	 Boolean deleteLaborInvoiceItemBean(UserProfile userProfile_, LaborInvoiceItemBean laborInvoiceItemBean_) throws GWTCustomException;
	 ArrayList<LaborInvoiceItemBean> selectLaborInvoiceItem(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<LaborInvoiceItemBean> saveLaborInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<LaborInvoiceItemBean> laborInvoiceItemBeanList_) throws GWTCustomException;
	 LaborInvoiceItemBean getLaborInvoiceItemByPrKey(UserProfile userProfile_ , Integer laborInvoiceItemId_ , Integer customerId_ ) throws GWTCustomException;

}
