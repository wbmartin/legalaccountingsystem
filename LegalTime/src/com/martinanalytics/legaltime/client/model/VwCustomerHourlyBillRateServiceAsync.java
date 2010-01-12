

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final VwCustomerHourlyBillRateServiceAsync vwCustomerHourlyBillRateService = 
//	GWT.create(VwCustomerHourlyBillRateService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VwCustomerHourlyBillRateServiceAsync{
	void  selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<VwCustomerHourlyBillRateBean>> callback);
	

}
