

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.widget.GWTCustomException;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("expenseInvoiceItem")
public interface ExpenseInvoiceItemService extends RemoteService{
	 ExpenseInvoiceItemBean insertExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException;
	 ExpenseInvoiceItemBean updateExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException;
	 ExpenseInvoiceItemBean saveExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException;
	 Boolean deleteExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_) throws GWTCustomException;
	 ArrayList<ExpenseInvoiceItemBean> selectExpenseInvoiceItem(UserProfile userProfile_, String whereClause_, String OrderByClause_) throws GWTCustomException;
	 ArrayList<ExpenseInvoiceItemBean> saveExpenseInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<ExpenseInvoiceItemBean> expenseInvoiceItemBeanList_) throws GWTCustomException;
	 ExpenseInvoiceItemBean getExpenseInvoiceItemByPrKey(UserProfile userProfile_ , Integer expenseInvoiceItemId_ , Integer customerId_ ) throws GWTCustomException;

}
