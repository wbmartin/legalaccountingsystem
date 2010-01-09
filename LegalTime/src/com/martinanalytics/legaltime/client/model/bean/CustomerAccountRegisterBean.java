
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;

public class CustomerAccountRegisterBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public CustomerAccountRegisterBean(){
		
	}
	public CustomerAccountRegisterBean(CustomerAccountRegisterBean customerAccountRegisterBean_){
		this();
		setBean(customerAccountRegisterBean_);
	}
	public void setBean(CustomerAccountRegisterBean customerAccountRegisterBean_){
		setTranType(customerAccountRegisterBean_.getTranType());
		setTranAmt(customerAccountRegisterBean_.getTranAmt());
		setDescription(customerAccountRegisterBean_.getDescription());
		setEffectiveDt(customerAccountRegisterBean_.getEffectiveDt());
		setLastUpdate(customerAccountRegisterBean_.getLastUpdate());
		setCustomerId(customerAccountRegisterBean_.getCustomerId());
		setClientId(customerAccountRegisterBean_.getClientId());
		setCustomerAccountRegisterId(customerAccountRegisterBean_.getCustomerAccountRegisterId());

	}
	
	public CustomerAccountRegisterBean getStandardCustomerAccountRegisterBean(){
		CustomerAccountRegisterBean customerAccountRegisterBean = new CustomerAccountRegisterBean();
		customerAccountRegisterBean.setTranType(getTranType());
		customerAccountRegisterBean.setTranAmt(getTranAmt());
		customerAccountRegisterBean.setDescription(getDescription());
		customerAccountRegisterBean.setEffectiveDt(getEffectiveDt());
		customerAccountRegisterBean.setLastUpdate(getLastUpdate());
		customerAccountRegisterBean.setCustomerId(getCustomerId());
		customerAccountRegisterBean.setClientId(getClientId());
		customerAccountRegisterBean.setCustomerAccountRegisterId(getCustomerAccountRegisterId());
		return customerAccountRegisterBean;
	}
	public String getTranType(){
		return get("tranType");
	}
	public void setTranType( String new_){
		set("tranType", new_);
	}

	public Double getTranAmt(){
		return get("tranAmt");
	}
	public void setTranAmt( Double new_){
		set("tranAmt", new_);
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

	public Integer getCustomerAccountRegisterId(){
		return get("customerAccountRegisterId");
	}
	public void setCustomerAccountRegisterId( Integer new_){
		set("customerAccountRegisterId", new_);
	}


}

