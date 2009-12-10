
package com.martinanalytics.legaltime.client.view.table;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;

public class CustomerBillRateDataModelBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public CustomerBillRateDataModelBean(){
		
	}
	public CustomerBillRateDataModelBean(CustomerBillRateBean customerBillRateBean_){
		this();
		setBean(customerBillRateBean_);
	}
	public void setBean(CustomerBillRateBean customerBillRateBean_){
		setBillRate(customerBillRateBean_.getBillRate());
		setLastUpdate(customerBillRateBean_.getLastUpdate());
		setUserId(customerBillRateBean_.getUserId());
		setCustomerId(customerBillRateBean_.getCustomerId());
		setClientId(customerBillRateBean_.getClientId());
		setCustomerBillRateId(customerBillRateBean_.getCustomerBillRateId());

	}
	
	public CustomerBillRateBean getStandardCustomerBillRateBean(){
		CustomerBillRateBean customerBillRateBean = new CustomerBillRateBean();
		customerBillRateBean.setBillRate(getBillRate());
		customerBillRateBean.setLastUpdate(getLastUpdate());
		customerBillRateBean.setUserId(getUserId());
		customerBillRateBean.setCustomerId(getCustomerId());
		customerBillRateBean.setClientId(getClientId());
		customerBillRateBean.setCustomerBillRateId(getCustomerBillRateId());
		return customerBillRateBean;
	}
	public Double getBillRate(){
		return get("billRate");
	}
	public void setBillRate( Double new_){
		set("billRate", new_);
	}

	public java.util.Date getLastUpdate(){
		return get("lastUpdate");
	}
	public void setLastUpdate( java.util.Date new_){
		set("lastUpdate", new_);
	}

	public String getUserId(){
		return get("userId");
	}
	public void setUserId( String new_){
		set("userId", new_);
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

	public Integer getCustomerBillRateId(){
		return get("customerBillRateId");
	}
	public void setCustomerBillRateId( Integer new_){
		set("customerBillRateId", new_);
	}


}

