
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
		setUsrDisplay(vwCustomerFollowupBean_.getUsrDisplay());
		setDisplayName(vwCustomerFollowupBean_.getDisplayName());
		setLastName(vwCustomerFollowupBean_.getLastName());
		setFirstName(vwCustomerFollowupBean_.getFirstName());
		setAssignedUserId(vwCustomerFollowupBean_.getAssignedUserId());
		setFollowupDescription(vwCustomerFollowupBean_.getFollowupDescription());
		setCloseDt(vwCustomerFollowupBean_.getCloseDt());
		setOpenDt(vwCustomerFollowupBean_.getOpenDt());
		setDueDt(vwCustomerFollowupBean_.getDueDt());
		setLastUpdate(vwCustomerFollowupBean_.getLastUpdate());
		setCustomerId(vwCustomerFollowupBean_.getCustomerId());
		setClientId(vwCustomerFollowupBean_.getClientId());
		setFollowupId(vwCustomerFollowupBean_.getFollowupId());

	}
	
	public VwCustomerFollowupBean getStandardVwCustomerFollowupBean(){
		VwCustomerFollowupBean vwCustomerFollowupBean = new VwCustomerFollowupBean();
		vwCustomerFollowupBean.setUsrDisplay(getUsrDisplay());
		vwCustomerFollowupBean.setDisplayName(getDisplayName());
		vwCustomerFollowupBean.setLastName(getLastName());
		vwCustomerFollowupBean.setFirstName(getFirstName());
		vwCustomerFollowupBean.setAssignedUserId(getAssignedUserId());
		vwCustomerFollowupBean.setFollowupDescription(getFollowupDescription());
		vwCustomerFollowupBean.setCloseDt(getCloseDt());
		vwCustomerFollowupBean.setOpenDt(getOpenDt());
		vwCustomerFollowupBean.setDueDt(getDueDt());
		vwCustomerFollowupBean.setLastUpdate(getLastUpdate());
		vwCustomerFollowupBean.setCustomerId(getCustomerId());
		vwCustomerFollowupBean.setClientId(getClientId());
		vwCustomerFollowupBean.setFollowupId(getFollowupId());
		return vwCustomerFollowupBean;
	}
	
	public FollowupBean getFollowupBean(){
		 FollowupBean followupBean = new FollowupBean();
			followupBean.setAssignedUserId(getAssignedUserId());
			followupBean.setFollowupDescription(getFollowupDescription());
			followupBean.setCloseDt(getCloseDt());
			followupBean.setOpenDt(getOpenDt());
			followupBean.setDueDt(getDueDt());
			followupBean.setLastUpdate(getLastUpdate());
			followupBean.setCustomerId(getCustomerId());
			followupBean.setClientId(getClientId());
			followupBean.setFollowupId(getFollowupId());
		 
		 return followupBean;
	}
	public String getUsrDisplay(){
		return get("usrDisplay");
	}
	public void setUsrDisplay( String new_){
		set("usrDisplay", new_);
	}

	public String getDisplayName(){
		return get("displayName");
	}
	public void setDisplayName( String new_){
		set("displayName", new_);
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

	public String getAssignedUserId(){
		return get("assignedUserId");
	}
	public void setAssignedUserId( String new_){
		set("assignedUserId", new_);
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

	public Integer getFollowupId(){
		return get("followupId");
	}
	public void setFollowupId( Integer new_){
		set("followupId", new_);
	}


}

