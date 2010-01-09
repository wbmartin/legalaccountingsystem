
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;

public class PaymentLogBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public PaymentLogBean(){
		
	}
	public PaymentLogBean(PaymentLogBean paymentLogBean_){
		this();
		setBean(paymentLogBean_);
	}
	public void setBean(PaymentLogBean paymentLogBean_){
		setCustomerAccountRegisterId(paymentLogBean_.getCustomerAccountRegisterId());
		setInvoiceId(paymentLogBean_.getInvoiceId());
		setAmount(paymentLogBean_.getAmount());
		setDescription(paymentLogBean_.getDescription());
		setEffectiveDt(paymentLogBean_.getEffectiveDt());
		setLastUpdate(paymentLogBean_.getLastUpdate());
		setCustomerId(paymentLogBean_.getCustomerId());
		setClientId(paymentLogBean_.getClientId());
		setPaymentLogId(paymentLogBean_.getPaymentLogId());

	}
	
	public PaymentLogBean getStandardPaymentLogBean(){
		PaymentLogBean paymentLogBean = new PaymentLogBean();
		paymentLogBean.setCustomerAccountRegisterId(getCustomerAccountRegisterId());
		paymentLogBean.setInvoiceId(getInvoiceId());
		paymentLogBean.setAmount(getAmount());
		paymentLogBean.setDescription(getDescription());
		paymentLogBean.setEffectiveDt(getEffectiveDt());
		paymentLogBean.setLastUpdate(getLastUpdate());
		paymentLogBean.setCustomerId(getCustomerId());
		paymentLogBean.setClientId(getClientId());
		paymentLogBean.setPaymentLogId(getPaymentLogId());
		return paymentLogBean;
	}
	public Integer getCustomerAccountRegisterId(){
		return get("customerAccountRegisterId");
	}
	public void setCustomerAccountRegisterId( Integer new_){
		set("customerAccountRegisterId", new_);
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

	public java.util.Date getEffectiveDt(){
		return get("effectiveDt");
	}
	public void setEffectiveDt( java.util.Date new_){
		set("effectiveDt", new_);
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

	public Integer getPaymentLogId(){
		return get("paymentLogId");
	}
	public void setPaymentLogId( Integer new_){
		set("paymentLogId", new_);
	}


}

