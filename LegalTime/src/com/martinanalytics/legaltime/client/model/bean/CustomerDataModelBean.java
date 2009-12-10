

package com.martinanalytics.legaltime.client.model.bean;





import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.data.BaseModelData;

public class CustomerDataModelBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public CustomerDataModelBean(){
		
	}
	public CustomerDataModelBean(CustomerBean customerBean_){
		this();
		setBean(customerBean_);
	}
	public void setBean(CustomerBean customerBean_){
		setActiveYn(customerBean_.getActiveYn());
		setMonthlyBillRate(customerBean_.getMonthlyBillRate());
		setBillType(customerBean_.getBillType());
		setNote(customerBean_.getNote());
		setClientSinceDt(customerBean_.getClientSinceDt());
		setEmail(customerBean_.getEmail());
		setFax(customerBean_.getFax());
		setHomePhone(customerBean_.getHomePhone());
		setWorkPhone(customerBean_.getWorkPhone());
		setZip(customerBean_.getZip());
		setState(customerBean_.getState());
		setCity(customerBean_.getCity());
		setAddress(customerBean_.getAddress());
		setLastName(customerBean_.getLastName());
		setFirstName(customerBean_.getFirstName());
		setLastUpdate(customerBean_.getLastUpdate());
		//Log.debug("SettingBean" + customerBean_.getClientId());
		setClientId(customerBean_.getClientId());
		setCustomerId(customerBean_.getCustomerId());

	}
	
	public CustomerBean getStandardCustomerBean(){
		CustomerBean customerBean = new CustomerBean();
		customerBean.setActiveYn(getActiveYn());
		customerBean.setMonthlyBillRate(getMonthlyBillRate());
		customerBean.setBillType(getBillType());
		customerBean.setNote(getNote());
		customerBean.setClientSinceDt(getClientSinceDt());
		customerBean.setEmail(getEmail());
		customerBean.setFax(getFax());
		customerBean.setHomePhone(getHomePhone());
		customerBean.setWorkPhone(getWorkPhone());
		customerBean.setZip(getZip());
		customerBean.setState(getState());
		customerBean.setCity(getCity());
		customerBean.setAddress(getAddress());
		customerBean.setLastName(getLastName());
		customerBean.setFirstName(getFirstName());
		customerBean.setLastUpdate(getLastUpdate());
		customerBean.setClientId(getClientId());
		customerBean.setCustomerId(getCustomerId());
		return customerBean;
	}
	public String getActiveYn(){
		return get("activeYn");
	}
	public void setActiveYn( String new_){
		set("activeYn", new_);
	}

	public Double getMonthlyBillRate(){
		return get("monthlyBillRate");
	}
	public void setMonthlyBillRate( Double new_){
		set("monthlyBillRate", new_);
	}

	public String getBillType(){
		return get("billType");
	}
	public void setBillType( String new_){
		set("billType", new_);
	}

	public String getNote(){
		return get("note");
	}
	public void setNote( String new_){
		set("note", new_);
	}

	public java.util.Date getClientSinceDt(){
		return get("clientSinceDt");
	}
	public void setClientSinceDt( java.util.Date new_){
		set("clientSinceDt", new_);
	}

	public String getEmail(){
		return get("email");
	}
	public void setEmail( String new_){
		set("email", new_);
	}

	public String getFax(){
		return get("fax");
	}
	public void setFax( String new_){
		set("fax", new_);
	}

	public String getHomePhone(){
		return get("homePhone");
	}
	public void setHomePhone( String new_){
		set("homePhone", new_);
	}

	public String getWorkPhone(){
		return get("workPhone");
	}
	public void setWorkPhone( String new_){
		set("workPhone", new_);
	}

	public String getZip(){
		return get("zip");
	}
	public void setZip( String new_){
		set("zip", new_);
	}

	public String getState(){
		return get("state");
	}
	public void setState( String new_){
		set("state", new_);
	}

	public String getCity(){
		return get("city");
	}
	public void setCity( String new_){
		set("city", new_);
	}

	public String getAddress(){
		return get("address");
	}
	public void setAddress( String new_){
		set("address", new_);
	}

	public String getLastName(){
		return get("lastName");
	}
	public void setLastName( String new_){
		set("lastName", new_);
		set("customerChooserDisplay", get("lastName") + ", " + get("firstName"));
	}

	public String getFirstName(){
		return get("firstName");
	}
	public void setFirstName( String new_){
		set("firstName", new_);
		set("customerChooserDisplay", get("lastName") + ", " + get("firstName"));
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

	public Integer getCustomerId(){
		return get("customerId");
	}
	public void setCustomerId( Integer new_){
		set("customerId", new_);
	}
	


}

