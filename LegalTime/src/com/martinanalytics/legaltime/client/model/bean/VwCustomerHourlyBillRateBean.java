
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;

public class VwCustomerHourlyBillRateBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public VwCustomerHourlyBillRateBean(){
		
	}
	public VwCustomerHourlyBillRateBean(VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
		this();
		setBean(vwCustomerHourlyBillRateBean_);
	}
	public void setBean(VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
		setBillType(vwCustomerHourlyBillRateBean_.getBillType());
		setLastName(vwCustomerHourlyBillRateBean_.getLastName());
		setFirstName(vwCustomerHourlyBillRateBean_.getFirstName());
		setLastUpdate(vwCustomerHourlyBillRateBean_.getLastUpdate());
		setCustomerId(vwCustomerHourlyBillRateBean_.getCustomerId());
		setBillRate(vwCustomerHourlyBillRateBean_.getBillRate());
		setDisplayName(vwCustomerHourlyBillRateBean_.getDisplayName());
		setUserId(vwCustomerHourlyBillRateBean_.getUserId());
		setCustomerBillRateId(vwCustomerHourlyBillRateBean_.getCustomerBillRateId());
		setClientId(vwCustomerHourlyBillRateBean_.getClientId());

	}
	
	public VwCustomerHourlyBillRateBean getStandardVwCustomerHourlyBillRateBean(){
		VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean = new VwCustomerHourlyBillRateBean();
		vwCustomerHourlyBillRateBean.setBillType(getBillType());
		vwCustomerHourlyBillRateBean.setLastName(getLastName());
		vwCustomerHourlyBillRateBean.setFirstName(getFirstName());
		vwCustomerHourlyBillRateBean.setLastUpdate(getLastUpdate());
		vwCustomerHourlyBillRateBean.setCustomerId(getCustomerId());
		vwCustomerHourlyBillRateBean.setBillRate(getBillRate());
		vwCustomerHourlyBillRateBean.setDisplayName(getDisplayName());
		vwCustomerHourlyBillRateBean.setUserId(getUserId());
		vwCustomerHourlyBillRateBean.setCustomerBillRateId(getCustomerBillRateId());
		vwCustomerHourlyBillRateBean.setClientId(getClientId());
		return vwCustomerHourlyBillRateBean;
	}
	public String getBillType(){
		return get("billType");
	}
	public void setBillType( String new_){
		set("billType", new_);
	}

	public String getLastName(){
		return get("lastName");
	}
	public void setLastName( String new_){
		set("lastName", new_);
	}

	public String getFirstName(){
		return get("firstName");
	}
	public void setFirstName( String new_){
		set("firstName", new_);
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

	public Double getBillRate(){
		return get("billRate");
	}
	public void setBillRate( Double new_){
		set("billRate", new_);
	}

	public String getDisplayName(){
		return get("displayName");
	}
	public void setDisplayName( String new_){
		set("displayName", new_);
	}

	public String getUserId(){
		return get("userId");
	}
	public void setUserId( String new_){
		set("userId", new_);
	}

	public Integer getCustomerBillRateId(){
		return get("customerBillRateId");
	}
	public void setCustomerBillRateId( Integer new_){
		set("customerBillRateId", new_);
	}

	public Integer getClientId(){
		return get("clientId");
	}
	public void setClientId( Integer new_){
		set("clientId", new_);
	}


}

