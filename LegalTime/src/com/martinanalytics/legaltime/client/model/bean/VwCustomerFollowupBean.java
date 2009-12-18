
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;

public class VwCustomerFollowupBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public VwCustomerFollowupBean(){
		
	}
	public VwCustomerFollowupBean(VwCustomerFollowupBean vwCustomerFollowupBean_){
		this();
		setBean(vwCustomerFollowupBean_);
	}
	public void setBean(VwCustomerFollowupBean vwCustomerFollowupBean_){
		setClientId(vwCustomerFollowupBean_.getClientId());
		setCustomerId(vwCustomerFollowupBean_.getCustomerId());
		setLastName(vwCustomerFollowupBean_.getLastName());
		setFirstName(vwCustomerFollowupBean_.getFirstName());
		setFollowupDescription(vwCustomerFollowupBean_.getFollowupDescription());
		setCloseDt(vwCustomerFollowupBean_.getCloseDt());
		setOpenDt(vwCustomerFollowupBean_.getOpenDt());
		setDueDt(vwCustomerFollowupBean_.getDueDt());
		setFollowupId(vwCustomerFollowupBean_.getFollowupId());

	}
	
	public VwCustomerFollowupBean getStandardVwCustomerFollowupBean(){
		VwCustomerFollowupBean vwCustomerFollowupBean = new VwCustomerFollowupBean();
		vwCustomerFollowupBean.setClientId(getClientId());
		vwCustomerFollowupBean.setCustomerId(getCustomerId());
		vwCustomerFollowupBean.setLastName(getLastName());
		vwCustomerFollowupBean.setFirstName(getFirstName());
		vwCustomerFollowupBean.setFollowupDescription(getFollowupDescription());
		vwCustomerFollowupBean.setCloseDt(getCloseDt());
		vwCustomerFollowupBean.setOpenDt(getOpenDt());
		vwCustomerFollowupBean.setDueDt(getDueDt());
		vwCustomerFollowupBean.setFollowupId(getFollowupId());
		return vwCustomerFollowupBean;
	}
	public Integer getClientId(){
		return get("clientId");
	}
	public void setClientId( Integer new_){
		set("clientId", new_);
	}

	public Integer getCustomerId(){
		return get("customerId");
	}
	public void setCustomerId( Integer new_){
		set("customerId", new_);
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

	public String getFollowupDescription(){
		return get("followupDescription");
	}
	public void setFollowupDescription( String new_){
		set("followupDescription", new_);
	}

	public java.util.Date getCloseDt(){
		return get("closeDt");
	}
	public void setCloseDt( java.util.Date new_){
		set("closeDt", new_);
	}

	public java.util.Date getOpenDt(){
		return get("openDt");
	}
	public void setOpenDt( java.util.Date new_){
		set("openDt", new_);
	}

	public java.util.Date getDueDt(){
		return get("dueDt");
	}
	public void setDueDt( java.util.Date new_){
		set("dueDt", new_);
	}

	public Integer getFollowupId(){
		return get("followupId");
	}
	public void setFollowupId( Integer new_){
		set("followupId", new_);
	}


}

