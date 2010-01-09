

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;
@RemoteServiceRelativePath("paymentLog")
public interface PaymentLogService extends RemoteService{
	 PaymentLogBean insertPaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException;
	 PaymentLogBean updatePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException;
	 PaymentLogBean savePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException;
	 Boolean deletePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_) throws GWTCustomException;
	 ArrayList<PaymentLogBean> selectPaymentLog(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<PaymentLogBean> savePaymentLogBeanBatch(UserProfile userProfile_, ArrayList<PaymentLogBean> paymentLogBeanList_) throws GWTCustomException;
	 PaymentLogBean getPaymentLogByPrKey(UserProfile userProfile_ , Integer paymentLogId_ , Integer customerId_ ) throws GWTCustomException;

}
