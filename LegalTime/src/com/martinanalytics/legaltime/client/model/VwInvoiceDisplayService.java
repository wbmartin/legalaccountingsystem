

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwInvoiceDisplay")
public interface VwInvoiceDisplayService extends RemoteService{
//	 VwInvoiceDisplayBean insertVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_) throws GWTCustomException;
//	 VwInvoiceDisplayBean updateVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_) throws GWTCustomException;
//	 VwInvoiceDisplayBean saveVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_) throws GWTCustomException;
//	 Boolean deleteVwInvoiceDisplayBean(UserProfile userProfile_, VwInvoiceDisplayBean vwInvoiceDisplayBean_) throws GWTCustomException;
	 ArrayList<VwInvoiceDisplayBean> selectVwInvoiceDisplay(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
//	 ArrayList<VwInvoiceDisplayBean> saveVwInvoiceDisplayBeanBatch(UserProfile userProfile_, ArrayList<VwInvoiceDisplayBean> vwInvoiceDisplayBeanList_) throws GWTCustomException;
//	 VwInvoiceDisplayBean getVwInvoiceDisplayByPrKey(UserProfile userProfile_ ) throws GWTCustomException;

}
