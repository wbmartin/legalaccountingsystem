
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.LaborInvoiceItemBean;

public class LaborInvoiceItemBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public LaborInvoiceItemBean(){
		
	}
	public LaborInvoiceItemBean(LaborInvoiceItemBean laborInvoiceItemBean_){
		this();
		setBean(laborInvoiceItemBean_);
	}
	public void setBean(LaborInvoiceItemBean laborInvoiceItemBean_){
		setBillRate(laborInvoiceItemBean_.getBillRate());
		setHoursBilled(laborInvoiceItemBean_.getHoursBilled());
		setInvoiceId(laborInvoiceItemBean_.getInvoiceId());
		setUserId(laborInvoiceItemBean_.getUserId());
		setActivityDescription(laborInvoiceItemBean_.getActivityDescription());
		setActivityDt(laborInvoiceItemBean_.getActivityDt());
		setLastUpdate(laborInvoiceItemBean_.getLastUpdate());
		setCustomerId(laborInvoiceItemBean_.getCustomerId());
		setClientId(laborInvoiceItemBean_.getClientId());
		setLaborInvoiceItemId(laborInvoiceItemBean_.getLaborInvoiceItemId());

	}
	
	public LaborInvoiceItemBean getStandardLaborInvoiceItemBean(){
		LaborInvoiceItemBean laborInvoiceItemBean = new LaborInvoiceItemBean();
		laborInvoiceItemBean.setBillRate(getBillRate());
		laborInvoiceItemBean.setHoursBilled(getHoursBilled());
		laborInvoiceItemBean.setInvoiceId(getInvoiceId());
		laborInvoiceItemBean.setUserId(getUserId());
		laborInvoiceItemBean.setActivityDescription(getActivityDescription());
		laborInvoiceItemBean.setActivityDt(getActivityDt());
		laborInvoiceItemBean.setLastUpdate(getLastUpdate());
		laborInvoiceItemBean.setCustomerId(getCustomerId());
		laborInvoiceItemBean.setClientId(getClientId());
		laborInvoiceItemBean.setLaborInvoiceItemId(getLaborInvoiceItemId());
		return laborInvoiceItemBean;
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

	public String getUserId(){
		return get("userId");
	}
	public void setUserId( String new_){
		set("userId", new_);
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

