
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;

public class FollowupBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public FollowupBean(){
		
	}
	public FollowupBean(FollowupBean followupBean_){
		this();
		setBean(followupBean_);
	}
	public void setBean(FollowupBean followupBean_){
		setAssignedUserId(followupBean_.getAssignedUserId());
		setFollowupDescription(followupBean_.getFollowupDescription());
		setCloseDt(followupBean_.getCloseDt());
		setOpenDt(followupBean_.getOpenDt());
		setDueDt(followupBean_.getDueDt());
		setLastUpdate(followupBean_.getLastUpdate());
		setCustomerId(followupBean_.getCustomerId());
		setClientId(followupBean_.getClientId());
		setFollowupId(followupBean_.getFollowupId());

	}
	
	public FollowupBean getStandardFollowupBean(){
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

