

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwLaborInvoiceItemDisplay")
public interface VwLaborInvoiceItemDisplayService extends RemoteService{
ArrayList<VwLaborInvoiceItemDisplayBean> selectVwLaborInvoiceItemDisplay(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;

}
