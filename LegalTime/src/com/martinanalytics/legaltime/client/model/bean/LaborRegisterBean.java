
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;

public class LaborRegisterBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public LaborRegisterBean(){
		
	}
	public LaborRegisterBean(LaborRegisterBean laborRegisterBean_){
		this();
		setBean(laborRegisterBean_);
	}
	public void setBean(LaborRegisterBean laborRegisterBean_){
		setUserId(laborRegisterBean_.getUserId());
		setInvoiceId(laborRegisterBean_.getInvoiceId());
		setBillRate(laborRegisterBean_.getBillRate());
		setInvoiceable(laborRegisterBean_.getInvoiceable());
		setActivityDate(laborRegisterBean_.getActivityDate());
		setEndTime(laborRegisterBean_.getEndTime());
		setStartTime(laborRegisterBean_.getStartTime());
		setMinuteCount(laborRegisterBean_.getMinuteCount());
		setDescription(laborRegisterBean_.getDescription());
		setLastUpdate(laborRegisterBean_.getLastUpdate());
		setCustomerId(laborRegisterBean_.getCustomerId());
		setClientId(laborRegisterBean_.getClientId());
		setLaborRegisterId(laborRegisterBean_.getLaborRegisterId());

	}
	
	public LaborRegisterBean getStandardLaborRegisterBean(){
		LaborRegisterBean laborRegisterBean = new LaborRegisterBean();
		laborRegisterBean.setUserId(getUserId());
		laborRegisterBean.setInvoiceId(getInvoiceId());
		laborRegisterBean.setBillRate(getBillRate());
		laborRegisterBean.setInvoiceable(getInvoiceable());
		laborRegisterBean.setActivityDate(getActivityDate());
		laborRegisterBean.setEndTime(getEndTime());
		laborRegisterBean.setStartTime(getStartTime());
		laborRegisterBean.setMinuteCount(getMinuteCount());
		laborRegisterBean.setDescription(getDescription());
		laborRegisterBean.setLastUpdate(getLastUpdate());
		laborRegisterBean.setCustomerId(getCustomerId());
		laborRegisterBean.setClientId(getClientId());
		laborRegisterBean.setLaborRegisterId(getLaborRegisterId());
		return laborRegisterBean;
	}
	public String getUserId(){
		return get("userId");
	}
	public void setUserId( String new_){
		set("userId", new_);
	}

	public Integer getInvoiceId(){
		return get("invoiceId");
	}
	public void setInvoiceId( Integer new_){
		set("invoiceId", new_);
	}

	public Double getBillRate(){
		return get("billRate");
	}
	public void setBillRate( Double new_){
		set("billRate", new_);
	}

	public Boolean getInvoiceable(){
		return get("invoiceable");
	}
	public void setInvoiceable( Boolean new_){
		set("invoiceable", new_);
	}

	public java.util.Date getActivityDate(){
		return get("activityDate");
	}
	public void setActivityDate( java.util.Date new_){
		set("activityDate", new_);
	}

	public java.util.Date getEndTime(){
		return get("endTime");
	}
	public void setEndTime( java.util.Date new_){
		set("endTime", new_);
	}

	public java.util.Date getStartTime(){
		return get("startTime");
	}
	public void setStartTime( java.util.Date new_){
		set("startTime", new_);
	}

	public Integer getMinuteCount(){
		return get("minuteCount");
	}
	public void setMinuteCount( Integer new_){
		set("minuteCount", new_);
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

	public Integer getLaborRegisterId(){
		return get("laborRegisterId");
	}
	public void setLaborRegisterId( Integer new_){
		set("laborRegisterId", new_);
	}


}

