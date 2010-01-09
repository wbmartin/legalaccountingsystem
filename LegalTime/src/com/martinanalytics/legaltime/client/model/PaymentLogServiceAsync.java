

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final PaymentLogServiceAsync paymentLogService = 
//	GWT.create(PaymentLogService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PaymentLogServiceAsync{
	void  insertPaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_, AsyncCallback<PaymentLogBean> callback);
	void  savePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_, AsyncCallback<PaymentLogBean> callback);
	void  updatePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_, AsyncCallback<PaymentLogBean> callback);
	void  deletePaymentLogBean(UserProfile userProfile_, PaymentLogBean paymentLogBean_, AsyncCallback<Boolean> result);
	void  selectPaymentLog(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<PaymentLogBean>> callback);
	void  savePaymentLogBeanBatch(UserProfile userProfile_, ArrayList<PaymentLogBean> paymentLogBeanList_, AsyncCallback<ArrayList<PaymentLogBean>> callback);

	void  getPaymentLogByPrKey(UserProfile userProfile_ , Integer paymentLogId_ , Integer customerId_ , AsyncCallback<PaymentLogBean> callback);


}
