
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;

public class ExpenseRegisterBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public ExpenseRegisterBean(){
		
	}
	public ExpenseRegisterBean(ExpenseRegisterBean expenseRegisterBean_){
		this();
		setBean(expenseRegisterBean_);
	}
	public void setBean(ExpenseRegisterBean expenseRegisterBean_){
		setExpenseDt(expenseRegisterBean_.getExpenseDt());
		setInvoiceable(expenseRegisterBean_.getInvoiceable());
		setInvoiceId(expenseRegisterBean_.getInvoiceId());
		setAmount(expenseRegisterBean_.getAmount());
		setDescription(expenseRegisterBean_.getDescription());
		setLastUpdate(expenseRegisterBean_.getLastUpdate());
		setCustomerId(expenseRegisterBean_.getCustomerId());
		setClientId(expenseRegisterBean_.getClientId());
		setExpenseRegisterId(expenseRegisterBean_.getExpenseRegisterId());

	}
	
	public ExpenseRegisterBean getStandardExpenseRegisterBean(){
		ExpenseRegisterBean expenseRegisterBean = new ExpenseRegisterBean();
		expenseRegisterBean.setExpenseDt(getExpenseDt());
		expenseRegisterBean.setInvoiceable(getInvoiceable());
		expenseRegisterBean.setInvoiceId(getInvoiceId());
		expenseRegisterBean.setAmount(getAmount());
		expenseRegisterBean.setDescription(getDescription());
		expenseRegisterBean.setLastUpdate(getLastUpdate());
		expenseRegisterBean.setCustomerId(getCustomerId());
		expenseRegisterBean.setClientId(getClientId());
		expenseRegisterBean.setExpenseRegisterId(getExpenseRegisterId());
		return expenseRegisterBean;
	}
	public java.util.Date getExpenseDt(){
		return get("expenseDt");
	}
	public void setExpenseDt( java.util.Date new_){
		set("expenseDt", new_);
	}

	public Boolean getInvoiceable(){
		return get("invoiceable");
	}
	public void setInvoiceable( Boolean new_){
		set("invoiceable", new_);
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

	public String getDescription(){
		return get("description");
	}
	public void setDescription( String new_){
		set("description", new_);
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

	public Integer getExpenseRegisterId(){
		return get("expenseRegisterId");
	}
	public void setExpenseRegisterId( Integer new_){
		set("expenseRegisterId", new_);
	}


}

