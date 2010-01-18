


package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("customer")
public interface CustomerService extends RemoteService{
	 CustomerBean insertCustomerBean(UserProfile userProfile_, CustomerBean customerBean_)throws GWTCustomException;
	 CustomerBean updateCustomerBean(UserProfile userProfile_, CustomerBean customerBean_)throws GWTCustomException;
	 CustomerBean saveCustomerBean(UserProfile userProfile_, CustomerBean customerBean_)throws GWTCustomException;
	 Boolean deleteCustomerBean(UserProfile userProfile_, CustomerBean customerBean_)throws GWTCustomException;
	 ArrayList<CustomerBean> selectCustomer(UserProfile userProfile_, String whereClause_, String OrderByClause_)throws GWTCustomException;
	 ArrayList<CustomerBean> saveCustomerBeanBatch(UserProfile userProfile_, ArrayList<CustomerBean> customerBeanList_)throws GWTCustomException;
	 CustomerBean getCustomerByPrKey(UserProfile userProfile_ , Integer customerId_ )throws GWTCustomException;
}
