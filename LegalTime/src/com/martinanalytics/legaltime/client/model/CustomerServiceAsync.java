


package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final CustomerServiceAsync customerService = 
//	GWT.create(CustomerService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomerServiceAsync{
	void  insertCustomerBean(UserProfile userProfile_, CustomerBean customerBean_, AsyncCallback<CustomerBean> callback);
	void  saveCustomerBean(UserProfile userProfile_, CustomerBean customerBean_, AsyncCallback<CustomerBean> callback);
	void  updateCustomerBean(UserProfile userProfile_, CustomerBean customerBean_, AsyncCallback<CustomerBean> callback);
	void  deleteCustomerBean(UserProfile userProfile_, CustomerBean customerBean_, AsyncCallback<Boolean> result);
	void  selectCustomer(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<CustomerBean>> callback);
	void  saveCustomerBeanBatch(UserProfile userProfile_, ArrayList<CustomerBean> customerBeanList_, AsyncCallback<ArrayList<CustomerBean>> callback);
	void  getCustomerByPrKey(UserProfile userProfile_ , Integer customerId_ , AsyncCallback<CustomerBean> callback);


}
