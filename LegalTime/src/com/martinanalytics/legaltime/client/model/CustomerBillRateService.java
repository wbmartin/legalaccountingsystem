

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("customerBillRate")
public interface CustomerBillRateService extends RemoteService{
	 CustomerBillRateBean insertCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_)throws GWTCustomException;
	 CustomerBillRateBean updateCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_)throws GWTCustomException;
	 CustomerBillRateBean saveCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_)throws GWTCustomException;
	 Boolean deleteCustomerBillRateBean(UserProfile userProfile_, CustomerBillRateBean customerBillRateBean_)throws GWTCustomException;
	 ArrayList<CustomerBillRateBean> selectCustomerBillRate(UserProfile userProfile_, String whereClause_, String OrderByClause_)throws GWTCustomException;
	 ArrayList<CustomerBillRateBean> saveCustomerBillRateBeanBatch(UserProfile userProfile_, ArrayList<CustomerBillRateBean> customerBillRateBeanList_)throws GWTCustomException;
	 CustomerBillRateBean getCustomerBillRateByPrKey(UserProfile userProfile_ , Integer customerBillRateId_ )throws GWTCustomException;

}
