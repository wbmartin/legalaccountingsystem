

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("expenseRegister")
public interface ExpenseRegisterService extends RemoteService{
	 ExpenseRegisterBean insertExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_) throws GWTCustomException;
	 ExpenseRegisterBean updateExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_) throws GWTCustomException;
	 ExpenseRegisterBean saveExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_) throws GWTCustomException;
	 Boolean deleteExpenseRegisterBean(UserProfile userProfile_, ExpenseRegisterBean expenseRegisterBean_) throws GWTCustomException;
	 ArrayList<ExpenseRegisterBean> selectExpenseRegister(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<ExpenseRegisterBean> saveExpenseRegisterBeanBatch(UserProfile userProfile_, ArrayList<ExpenseRegisterBean> expenseRegisterBeanList_) throws GWTCustomException;
	 ExpenseRegisterBean getExpenseRegisterByPrKey(UserProfile userProfile_ , Integer expenseRegisterId_ , Integer customerId_ ) throws GWTCustomException;

}
