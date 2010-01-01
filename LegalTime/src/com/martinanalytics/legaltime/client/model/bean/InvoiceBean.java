
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;

public class InvoiceBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public InvoiceBean(){
		
	}
	public InvoiceBean(InvoiceBean invoiceBean_){
		this();
		setBean(invoiceBean_);
	}
	public void setBean(InvoiceBean invoiceBean_){
		setPrevBalanceDue(invoiceBean_.getPrevBalanceDue());
		setInvoiceTotalAmt(invoiceBean_.getInvoiceTotalAmt());
		setGeneratedDt(invoiceBean_.getGeneratedDt());
		setInvoiceDt(invoiceBean_.getInvoiceDt());
		setLastUpdate(invoiceBean_.getLastUpdate());
		setCustomerId(invoiceBean_.getCustomerId());
		setClientId(invoiceBean_.getClientId());
		setInvoiceId(invoiceBean_.getInvoiceId());

	}
	
	public InvoiceBean getStandardInvoiceBean(){
		InvoiceBean invoiceBean = new InvoiceBean();
		invoiceBean.setPrevBalanceDue(getPrevBalanceDue());
		invoiceBean.setInvoiceTotalAmt(getInvoiceTotalAmt());
		invoiceBean.setGeneratedDt(getGeneratedDt());
		invoiceBean.setInvoiceDt(getInvoiceDt());
		invoiceBean.setLastUpdate(getLastUpdate());
		invoiceBean.setCustomerId(getCustomerId());
		invoiceBean.setClientId(getClientId());
		invoiceBean.setInvoiceId(getInvoiceId());
		return invoiceBean;
	}
	public Double getPrevBalanceDue(){
		return get("prevBalanceDue");
	}
	public void setPrevBalanceDue( Double new_){
		set("prevBalanceDue", new_);
	}

	public Double getInvoiceTotalAmt(){
		return get("invoiceTotalAmt");
	}
	public void setInvoiceTotalAmt( Double new_){
		set("invoiceTotalAmt", new_);
	}

	public java.util.Date getGeneratedDt(){
		return get("generatedDt");
	}
	public void setGeneratedDt( java.util.Date new_){
		set("generatedDt", new_);
	}

	public java.util.Date getInvoiceDt(){
		return get("invoiceDt");
	}
	public void setInvoiceDt( java.util.Date new_){
		set("invoiceDt", new_);
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

	public Integer getInvoiceId(){
		return get("invoiceId");
	}
	public void setInvoiceId( Integer new_){
		set("invoiceId", new_);
	}


}

