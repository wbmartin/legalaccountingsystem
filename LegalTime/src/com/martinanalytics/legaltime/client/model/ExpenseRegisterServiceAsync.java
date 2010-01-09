

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
//private final ExpenseRegisterServiceAsync expenseRegisterService = 
//	GWT.create(ExpenseRegisterService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExpenseRegisterServiceAsync{
	void  insertExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_, AsyncCallback<ExpenseRegisterBean> callback);
	void  saveExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_, AsyncCallback<ExpenseRegisterBean> callback);
	void  updateExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_, AsyncCallback<ExpenseRegisterBean> callback);
	void  deleteExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_, AsyncCallback<Boolean> result);
	void  selectExpenseRegister(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<ExpenseRegisterBean>> callback) ;
	void  saveExpenseRegisterBeanBatch(UserProfile userProfile_, ArrayList<ExpenseRegisterBean> expenseRegisterBeanList_, AsyncCallback<ArrayList<ExpenseRegisterBean>> callback);

	void  getExpenseRegisterByPrKey(UserProfile userProfile_ , Integer expenseRegisterId_ , Integer customerId_ , AsyncCallback<ExpenseRegisterBean> callback);


}
