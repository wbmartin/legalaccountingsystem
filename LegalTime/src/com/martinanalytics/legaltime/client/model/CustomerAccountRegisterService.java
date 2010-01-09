

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("customerAccountRegister")
public interface CustomerAccountRegisterService extends RemoteService{
	 CustomerAccountRegisterBean insertCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException;
	 CustomerAccountRegisterBean updateCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException;
	 CustomerAccountRegisterBean saveCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException;
	 Boolean deleteCustomerAccountRegisterBean(UserProfile userProfile_, CustomerAccountRegisterBean customerAccountRegisterBean_) throws GWTCustomException;
	 ArrayList<CustomerAccountRegisterBean> selectCustomerAccountRegister(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<CustomerAccountRegisterBean> saveCustomerAccountRegisterBeanBatch(UserProfile userProfile_, ArrayList<CustomerAccountRegisterBean> customerAccountRegisterBeanList_) throws GWTCustomException;
	 CustomerAccountRegisterBean getCustomerAccountRegisterByPrKey(UserProfile userProfile_ , Integer clientAccountRegisterId_ , Integer customerId_ ) throws GWTCustomException;

}
