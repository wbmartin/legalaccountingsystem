

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
//private final ExpenseInvoiceItemServiceAsync expenseInvoiceItemService = 
//	GWT.create(ExpenseInvoiceItemService.class);
//

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExpenseInvoiceItemServiceAsync{
	void  insertExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_, AsyncCallback<ExpenseInvoiceItemBean> callback);
	void  saveExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_, AsyncCallback<ExpenseInvoiceItemBean> callback);
	void  updateExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_, AsyncCallback<ExpenseInvoiceItemBean> callback);
	void  deleteExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_, AsyncCallback<Boolean> result);
	void  selectExpenseInvoiceItem(UserProfile userProfile_, String whereByClause_, String orderByClause_, AsyncCallback<ArrayList<ExpenseInvoiceItemBean>> callback);
	void  saveExpenseInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<ExpenseInvoiceItemBean> expenseInvoiceItemBeanList_, AsyncCallback<ArrayList<ExpenseInvoiceItemBean>> callback);

	void  getExpenseInvoiceItemByPrKey(UserProfile userProfile_ , Integer expenseInvoiceItemId_ , Integer customerId_ , AsyncCallback<ExpenseInvoiceItemBean> callback);


}
