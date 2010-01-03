

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final VwLaborInvoiceItemDisplayServiceAsync vwLaborInvoiceItemDisplayService = 
//	GWT.create(VwLaborInvoiceItemDisplayService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VwLaborInvoiceItemDisplayServiceAsync{
	void  selectVwLaborInvoiceItemDisplay(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<VwLaborInvoiceItemDisplayBean>> callback);

}
