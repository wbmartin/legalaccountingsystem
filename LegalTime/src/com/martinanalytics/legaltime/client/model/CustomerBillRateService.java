

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("customerBillRate")
public interface CustomerBillRateService extends RemoteService{
	 CustomerBillRateBean insertCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_);
	 CustomerBillRateBean updateCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_);
	 CustomerBillRateBean saveCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_);
	 Boolean deleteCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_);
	 ArrayList<CustomerBillRateBean> selectCustomerBillRate(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<CustomerBillRateBean> saveCustomerBillRateBeanBatch(UserProfile userProfile_, ArrayList<CustomerBillRateBean> customerBillRateBeanList_);
	 CustomerBillRateBean getCustomerBillRateByPrKey(UserProfile userProfile_ , Integer customerBillRateId_ );

}
