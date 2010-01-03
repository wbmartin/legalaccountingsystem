
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.VwLaborInvoiceItemDisplayBean;

public class VwLaborInvoiceItemDisplayBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public VwLaborInvoiceItemDisplayBean(){
		
	}
	public VwLaborInvoiceItemDisplayBean(VwLaborInvoiceItemDisplayBean vwLaborInvoiceItemDisplayBean_){
		this();
		setBean(vwLaborInvoiceItemDisplayBean_);
	}
	public void setBean(VwLaborInvoiceItemDisplayBean vwLaborInvoiceItemDisplayBean_){
		setDisplayName(vwLaborInvoiceItemDisplayBean_.getDisplayName());
		setUserId(vwLaborInvoiceItemDisplayBean_.getUserId());
		setBillRate(vwLaborInvoiceItemDisplayBean_.getBillRate());
		setHoursBilled(vwLaborInvoiceItemDisplayBean_.getHoursBilled());
		setInvoiceId(vwLaborInvoiceItemDisplayBean_.getInvoiceId());
		setActivityDescription(vwLaborInvoiceItemDisplayBean_.getActivityDescription());
		setActivityDt(vwLaborInvoiceItemDisplayBean_.getActivityDt());
		setLastUpdate(vwLaborInvoiceItemDisplayBean_.getLastUpdate());
		setCustomerId(vwLaborInvoiceItemDisplayBean_.getCustomerId());
		setClientId(vwLaborInvoiceItemDisplayBean_.getClientId());
		setLaborInvoiceItemId(vwLaborInvoiceItemDisplayBean_.getLaborInvoiceItemId());

	}
	
	public VwLaborInvoiceItemDisplayBean getStandardVwLaborInvoiceItemDisplayBean(){
		VwLaborInvoiceItemDisplayBean vwLaborInvoiceItemDisplayBean = new VwLaborInvoiceItemDisplayBean();
		vwLaborInvoiceItemDisplayBean.setDisplayName(getDisplayName());
		vwLaborInvoiceItemDisplayBean.setUserId(getUserId());
		vwLaborInvoiceItemDisplayBean.setBillRate(getBillRate());
		vwLaborInvoiceItemDisplayBean.setHoursBilled(getHoursBilled());
		vwLaborInvoiceItemDisplayBean.setInvoiceId(getInvoiceId());
		vwLaborInvoiceItemDisplayBean.setActivityDescription(getActivityDescription());
		vwLaborInvoiceItemDisplayBean.setActivityDt(getActivityDt());
		vwLaborInvoiceItemDisplayBean.setLastUpdate(getLastUpdate());
		vwLaborInvoiceItemDisplayBean.setCustomerId(getCustomerId());
		vwLaborInvoiceItemDisplayBean.setClientId(getClientId());
		vwLaborInvoiceItemDisplayBean.setLaborInvoiceItemId(getLaborInvoiceItemId());
		return vwLaborInvoiceItemDisplayBean;
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

	public Double getBillRate(){
		return get("billRate");
	}
	public void setBillRate( Double new_){
		set("billRate", new_);
	}

	public Double getHoursBilled(){
		return get("hoursBilled");
	}
	public void setHoursBilled( Double new_){
		set("hoursBilled", new_);
	}

	public Integer getInvoiceId(){
		return get("invoiceId");
	}
	public void setInvoiceId( Integer new_){
		set("invoiceId", new_);
	}

	public String getActivityDescription(){
		return get("activityDescription");
	}
	public void setActivityDescription( String new_){
		set("activityDescription", new_);
	}

	public java.util.Date getActivityDt(){
		return get("activityDt");
	}
	public void setActivityDt( java.util.Date new_){
		set("activityDt", new_);
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

	public Integer getLaborInvoiceItemId(){
		return get("laborInvoiceItemId");
	}
	public void setLaborInvoiceItemId( Integer new_){
		set("laborInvoiceItemId", new_);
	}


}

