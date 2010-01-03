
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.ExpenseInvoiceItemBean;

public class ExpenseInvoiceItemBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public ExpenseInvoiceItemBean(){
		
	}
	public ExpenseInvoiceItemBean(ExpenseInvoiceItemBean expenseInvoiceItemBean_){
		this();
		setBean(expenseInvoiceItemBean_);
	}
	public void setBean(ExpenseInvoiceItemBean expenseInvoiceItemBean_){
		setInvoiceId(expenseInvoiceItemBean_.getInvoiceId());
		setAmount(expenseInvoiceItemBean_.getAmount());
		setExpenseDescription(expenseInvoiceItemBean_.getExpenseDescription());
		setExpenseDt(expenseInvoiceItemBean_.getExpenseDt());
		setLastUpdate(expenseInvoiceItemBean_.getLastUpdate());
		setCustomerId(expenseInvoiceItemBean_.getCustomerId());
		setClientId(expenseInvoiceItemBean_.getClientId());
		setExpenseInvoiceItemId(expenseInvoiceItemBean_.getExpenseInvoiceItemId());

	}
	
	public ExpenseInvoiceItemBean getStandardExpenseInvoiceItemBean(){
		ExpenseInvoiceItemBean expenseInvoiceItemBean = new ExpenseInvoiceItemBean();
		expenseInvoiceItemBean.setInvoiceId(getInvoiceId());
		expenseInvoiceItemBean.setAmount(getAmount());
		expenseInvoiceItemBean.setExpenseDescription(getExpenseDescription());
		expenseInvoiceItemBean.setExpenseDt(getExpenseDt());
		expenseInvoiceItemBean.setLastUpdate(getLastUpdate());
		expenseInvoiceItemBean.setCustomerId(getCustomerId());
		expenseInvoiceItemBean.setClientId(getClientId());
		expenseInvoiceItemBean.setExpenseInvoiceItemId(getExpenseInvoiceItemId());
		return expenseInvoiceItemBean;
	}
	public Integer getInvoiceId(){
		return get("invoiceId");
	}
	public void setInvoiceId( Integer new_){
		set("invoiceId", new_);
	}

	public Double getAmount(){
		return get("amount");
	}
	public void setAmount( Double new_){
		set("amount", new_);
	}

	public String getExpenseDescription(){
		return get("expenseDescription");
	}
	public void setExpenseDescription( String new_){
		set("expenseDescription", new_);
	}

	public java.util.Date getExpenseDt(){
		return get("expenseDt");
	}
	public void setExpenseDt( java.util.Date new_){
		set("expenseDt", new_);
	}

	public java.util.Date getLastUpdate(){
		return get("lastUpdate");
	}
	public void setLastUpdate( java.util.Date new_){
		set("lastUpdate", new_);
	}

	public Integer getCustomerId(){
		return get("customerId");
	}
	public void setCustomerId( Integer new_){
		set("customerId", new_);
	}

	public Integer getClientId(){
		return get("clientId");
	}
	public void setClientId( Integer new_){
		set("clientId", new_);
	}

	public Integer getExpenseInvoiceItemId(){
		return get("expenseInvoiceItemId");
	}
	public void setExpenseInvoiceItemId( Integer new_){
		set("expenseInvoiceItemId", new_);
	}


}

