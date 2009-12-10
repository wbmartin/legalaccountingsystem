

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("vwCustomerHourlyBillRate")
public interface VwCustomerHourlyBillRateService extends RemoteService{
	 VwCustomerHourlyBillRateBean insertVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_);
	 VwCustomerHourlyBillRateBean updateVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_);
	 VwCustomerHourlyBillRateBean saveVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_);
	 Boolean deleteVwCustomerHourlyBillRateBean(UserProfile userProfile_, VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_);
	 ArrayList<VwCustomerHourlyBillRateBean> selectVwCustomerHourlyBillRate(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<VwCustomerHourlyBillRateBean> saveVwCustomerHourlyBillRateBeanBatch(UserProfile userProfile_, ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateBeanList_);
	 VwCustomerHourlyBillRateBean getVwCustomerHourlyBillRateByPrKey(UserProfile userProfile_ );

}
