
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;

public class UserInfoBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public UserInfoBean(){
		
	}
	public UserInfoBean(UserInfoBean userInfoBean_){
		this();
		setBean(userInfoBean_);
	}
	public void setBean(UserInfoBean userInfoBean_){
		setEmailAddr(userInfoBean_.getEmailAddr());
		setDisplayName(userInfoBean_.getDisplayName());
		setDefaultBillRate(userInfoBean_.getDefaultBillRate());
		setLastUpdate(userInfoBean_.getLastUpdate());
		setClientId(userInfoBean_.getClientId());
		setUserId(userInfoBean_.getUserId());

	}
	
	public UserInfoBean getStandardUserInfoBean(){
		UserInfoBean userInfoBean = new UserInfoBean();
		userInfoBean.setEmailAddr(getEmailAddr());
		userInfoBean.setDisplayName(getDisplayName());
		userInfoBean.setDefaultBillRate(getDefaultBillRate());
		userInfoBean.setLastUpdate(getLastUpdate());
		userInfoBean.setClientId(getClientId());
		userInfoBean.setUserId(getUserId());
		return userInfoBean;
	}
	public String getEmailAddr(){
		return get("emailAddr");
	}
	public void setEmailAddr( String new_){
		set("emailAddr", new_);
	}

	public String getDisplayName(){
		return get("displayName");
	}
	public void setDisplayName( String new_){
		set("displayName", new_);
	}

	public Double getDefaultBillRate(){
		return get("defaultBillRate");
	}
	public void setDefaultBillRate( Double new_){
		set("defaultBillRate", new_);
	}

	public java.util.Date getLastUpdate(){
		return get("lastUpdate");
	}
	public void setLastUpdate( java.util.Date new_){
		set("lastUpdate", new_);
	}

	public Integer getClientId(){
		return get("clientId");
	}
	public void setClientId( Integer new_){
		set("clientId", new_);
	}

	public String getUserId(){
		return get("userId");
	}
	public void setUserId( String new_){
		set("userId", new_);
	}


}

