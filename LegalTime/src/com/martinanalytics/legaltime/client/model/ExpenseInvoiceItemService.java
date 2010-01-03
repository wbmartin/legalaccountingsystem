

package com.martinanalytics.legaltime.client.model;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("expenseInvoiceItem")
public interface ExpenseInvoiceItemService extends RemoteService{
	 ExpenseInvoiceItemBean insertExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_);
	 ExpenseInvoiceItemBean updateExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_);
	 ExpenseInvoiceItemBean saveExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_);
	 Boolean deleteExpenseInvoiceItemBean(UserProfile userProfile_, ExpenseInvoiceItemBean expenseInvoiceItemBean_);
	 ArrayList<ExpenseInvoiceItemBean> selectExpenseInvoiceItem(UserProfile userProfile_, String whereClause_, String OrderByClause_);
	 ArrayList<ExpenseInvoiceItemBean> saveExpenseInvoiceItemBeanBatch(UserProfile userProfile_, ArrayList<ExpenseInvoiceItemBean> expenseInvoiceItemBeanList_);
	 ExpenseInvoiceItemBean getExpenseInvoiceItemByPrKey(UserProfile userProfile_ , Integer expenseInvoiceItemId_ , Integer customerId_ );

}
