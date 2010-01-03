
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;

public class VwInvoiceDisplayBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public VwInvoiceDisplayBean(){
		
	}
	public VwInvoiceDisplayBean(VwInvoiceDisplayBean vwInvoiceDisplayBean_){
		this();
		setBean(vwInvoiceDisplayBean_);
	}
	public void setBean(VwInvoiceDisplayBean vwInvoiceDisplayBean_){
		setContingencyRate(vwInvoiceDisplayBean_.getContingencyRate());
		setMortgageAmount(vwInvoiceDisplayBean_.getMortgageAmount());
		setActiveYn(vwInvoiceDisplayBean_.getActiveYn());
		setMonthlyBillRate(vwInvoiceDisplayBean_.getMonthlyBillRate());
		setBillType(vwInvoiceDisplayBean_.getBillType());
		setClientSinceDt(vwInvoiceDisplayBean_.getClientSinceDt());
		setEmail(vwInvoiceDisplayBean_.getEmail());
		setFax(vwInvoiceDisplayBean_.getFax());
		setHomePhone(vwInvoiceDisplayBean_.getHomePhone());
		setWorkPhone(vwInvoiceDisplayBean_.getWorkPhone());
		setZip(vwInvoiceDisplayBean_.getZip());
		setState(vwInvoiceDisplayBean_.getState());
		setCity(vwInvoiceDisplayBean_.getCity());
		setAddress(vwInvoiceDisplayBean_.getAddress());
		setDisplayName(vwInvoiceDisplayBean_.getDisplayName());
		setLastName(vwInvoiceDisplayBean_.getLastName());
		setFirstName(vwInvoiceDisplayBean_.getFirstName());
		setPrevBalanceDue(vwInvoiceDisplayBean_.getPrevBalanceDue());
		setInvoiceTotalAmt(vwInvoiceDisplayBean_.getInvoiceTotalAmt());
		setGeneratedDt(vwInvoiceDisplayBean_.getGeneratedDt());
		setInvoiceDt(vwInvoiceDisplayBean_.getInvoiceDt());
		setLastUpdate(vwInvoiceDisplayBean_.getLastUpdate());
		setCustomerId(vwInvoiceDisplayBean_.getCustomerId());
		setClientId(vwInvoiceDisplayBean_.getClientId());
		setInvoiceId(vwInvoiceDisplayBean_.getInvoiceId());

	}
	
	public VwInvoiceDisplayBean getStandardVwInvoiceDisplayBean(){
		VwInvoiceDisplayBean vwInvoiceDisplayBean = new VwInvoiceDisplayBean();
		vwInvoiceDisplayBean.setContingencyRate(getContingencyRate());
		vwInvoiceDisplayBean.setMortgageAmount(getMortgageAmount());
		vwInvoiceDisplayBean.setActiveYn(getActiveYn());
		vwInvoiceDisplayBean.setMonthlyBillRate(getMonthlyBillRate());
		vwInvoiceDisplayBean.setBillType(getBillType());
		vwInvoiceDisplayBean.setClientSinceDt(getClientSinceDt());
		vwInvoiceDisplayBean.setEmail(getEmail());
		vwInvoiceDisplayBean.setFax(getFax());
		vwInvoiceDisplayBean.setHomePhone(getHomePhone());
		vwInvoiceDisplayBean.setWorkPhone(getWorkPhone());
		vwInvoiceDisplayBean.setZip(getZip());
		vwInvoiceDisplayBean.setState(getState());
		vwInvoiceDisplayBean.setCity(getCity());
		vwInvoiceDisplayBean.setAddress(getAddress());
		vwInvoiceDisplayBean.setDisplayName(getDisplayName());
		vwInvoiceDisplayBean.setLastName(getLastName());
		vwInvoiceDisplayBean.setFirstName(getFirstName());
		vwInvoiceDisplayBean.setPrevBalanceDue(getPrevBalanceDue());
		vwInvoiceDisplayBean.setInvoiceTotalAmt(getInvoiceTotalAmt());
		vwInvoiceDisplayBean.setGeneratedDt(getGeneratedDt());
		vwInvoiceDisplayBean.setInvoiceDt(getInvoiceDt());
		vwInvoiceDisplayBean.setLastUpdate(getLastUpdate());
		vwInvoiceDisplayBean.setCustomerId(getCustomerId());
		vwInvoiceDisplayBean.setClientId(getClientId());
		vwInvoiceDisplayBean.setInvoiceId(getInvoiceId());
		return vwInvoiceDisplayBean;
	}
	public Double getContingencyRate(){
		return get("contingencyRate");
	}
	public void setContingencyRate( Double new_){
		set("contingencyRate", new_);
	}

	public Double getMortgageAmount(){
		return get("mortgageAmount");
	}
	public void setMortgageAmount( Double new_){
		set("mortgageAmount", new_);
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

	public Double getPrevBalanceDue(){
		return get("prevBalanceDue");
	}
	public void setPrevBalanceDue( Double new_){
		set("prevBalanceDue", new_);
	}

	public Double getInvoiceTotalAmt(){
		return get("invoiceTotalAmt");
	}
	public void setInvoiceTotalAmt( Double new_){
		set("invoiceTotalAmt", new_);
	}

	public java.util.Date getGeneratedDt(){
		return get("generatedDt");
	}
	public void setGeneratedDt( java.util.Date new_){
		set("generatedDt", new_);
	}

	public java.util.Date getInvoiceDt(){
		return get("invoiceDt");
	}
	public void setInvoiceDt( java.util.Date new_){
		set("invoiceDt", new_);
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

	public Integer getInvoiceId(){
		return get("invoiceId");
	}
	public void setInvoiceId( Integer new_){
		set("invoiceId", new_);
	}


}

