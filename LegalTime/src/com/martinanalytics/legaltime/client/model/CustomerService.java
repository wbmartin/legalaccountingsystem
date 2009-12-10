


package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("customer")
public interface CustomerService extends RemoteService{
	 CustomerBean insertCustomerBean(UserProfile userProfile_, CustomerBean customerBean_);
	 CustomerBean updateCustomerBean(UserProfile userProfile_, CustomerBean customerBean_);
	 CustomerBean saveCustomerBean(UserProfile userProfile_, CustomerBean customerBean_);
	 Boolean deleteCustomerBean(UserProfile userProfile_, CustomerBean customerBean_);
	 ArrayList<CustomerBean> selectCustomer(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<CustomerBean> saveCustomerBeanBatch(UserProfile userProfile_, ArrayList<CustomerBean> customerBeanList_);
	 CustomerBean getCustomerByPrKey(UserProfile userProfile_ , Integer customerId_ );
}
