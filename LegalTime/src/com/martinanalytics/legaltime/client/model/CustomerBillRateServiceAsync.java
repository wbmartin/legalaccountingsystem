

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final CustomerBillRateServiceAsync customerBillRateService = 
//	GWT.create(CustomerBillRateService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomerBillRateServiceAsync{
	void  insertCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_, AsyncCallback<CustomerBillRateBean> callback);
	void  saveCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_, AsyncCallback<CustomerBillRateBean> callback);
	void  updateCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_, AsyncCallback<CustomerBillRateBean> callback);
	void  deleteCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_, AsyncCallback<Boolean> result);
	void  selectCustomerBillRate(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<CustomerBillRateBean>> callback);
	void  saveCustomerBillRateBeanBatch(UserProfile userProfile_, ArrayList<CustomerBillRateBean> customerBillRateBeanList_, AsyncCallback<ArrayList<CustomerBillRateBean>> callback);

	void  getCustomerBillRateByPrKey(UserProfile userProfile_ , Integer customerBillRateId_ , AsyncCallback<CustomerBillRateBean> callback);


}
