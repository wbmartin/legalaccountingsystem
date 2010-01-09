

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final CustomerAccountRegisterServiceAsync customerAccountRegisterService = 
//	GWT.create(CustomerAccountRegisterService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomerAccountRegisterServiceAsync{
	void  insertCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_, AsyncCallback<CustomerAccountRegisterBean> callback);
	void  saveCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_, AsyncCallback<CustomerAccountRegisterBean> callback);
	void  updateCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_, AsyncCallback<CustomerAccountRegisterBean> callback);
	void  deleteCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_, AsyncCallback<Boolean> result);
	void  selectCustomerAccountRegister(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<CustomerAccountRegisterBean>> callback);
	void  saveCustomerAccountRegisterBeanBatch(UserProfile userProfile_, ArrayList<CustomerAccountRegisterBean> customerAccountRegisterBeanList_, AsyncCallback<ArrayList<CustomerAccountRegisterBean>> callback);

	void  getCustomerAccountRegisterByPrKey(UserProfile userProfile_ , Integer clientAccountRegisterId_ , Integer customerId_ , AsyncCallback<CustomerAccountRegisterBean> callback);


}
