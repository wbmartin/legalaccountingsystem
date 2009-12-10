

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final VwCustomerHourlyBillRateServiceAsync vwCustomerHourlyBillRateService = 
//	GWT.create(VwCustomerHourlyBillRateService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VwCustomerHourlyBillRateServiceAsync{
	void  insertVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_, AsyncCallback<VwCustomerHourlyBillRateBean> callback);
	void  saveVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_, AsyncCallback<VwCustomerHourlyBillRateBean> callback);
	void  updateVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_, AsyncCallback<VwCustomerHourlyBillRateBean> callback);
	void  deleteVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_, AsyncCallback<Boolean> result);
	void  selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<VwCustomerHourlyBillRateBean>> callback);
	void  saveVwCustomerHourlyBillRateBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateBeanList_, AsyncCallback<ArrayList<VwCustomerHourlyBillRateBean>> callback);

	void  getVwCustomerHourlyBillRateByPrKey(UserProfile userProfile_ , AsyncCallback<VwCustomerHourlyBillRateBean> callback);


}
