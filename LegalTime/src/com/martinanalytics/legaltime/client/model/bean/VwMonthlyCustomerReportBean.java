
package com.martinanalytics.legaltime.client.model.bean;



import com.extjs.gxt.ui.client.data.BaseModelData;
import com.martinanalytics.legaltime.client.model.bean.VwMonthlyCustomerReportBean;

public class VwMonthlyCustomerReportBean extends BaseModelData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846879214506300276L;
	public VwMonthlyCustomerReportBean(){
		
	}
	public VwMonthlyCustomerReportBean(VwMonthlyCustomerReportBean vwMonthlyCustomerReportBean_){
		this();
		setBean(vwMonthlyCustomerReportBean_);
	}
	public void setBean(VwMonthlyCustomerReportBean vwMonthlyCustomerReportBean_){
		setEscrow(vwMonthlyCustomerReportBean_.getEscrow());
		setMonthsUnpaid(vwMonthlyCustomerReportBean_.getMonthsUnpaid());
		setFirstName(vwMonthlyCustomerReportBean_.getFirstName());
		setLastName(vwMonthlyCustomerReportBean_.getLastName());
		setCustomerId(vwMonthlyCustomerReportBean_.getCustomerId());
		setClientId(vwMonthlyCustomerReportBean_.getClientId());

	}
	
	public VwMonthlyCustomerReportBean getStandardVwMonthlyCustomerReportBean(){
		VwMonthlyCustomerReportBean vwMonthlyCustomerReportBean = new VwMonthlyCustomerReportBean();
		vwMonthlyCustomerReportBean.setEscrow(getEscrow());
		vwMonthlyCustomerReportBean.setMonthsUnpaid(getMonthsUnpaid());
		vwMonthlyCustomerReportBean.setFirstName(getFirstName());
		vwMonthlyCustomerReportBean.setLastName(getLastName());
		vwMonthlyCustomerReportBean.setCustomerId(getCustomerId());
		vwMonthlyCustomerReportBean.setClientId(getClientId());
		return vwMonthlyCustomerReportBean;
	}
	public String getEscrow(){
		return get("escrow");
	}
	public void setEscrow( String new_){
		set("escrow", new_);
	}

	public Double getMonthsUnpaid(){
		return get("monthsUnpaid");
	}
	public void setMonthsUnpaid( Double new_){
		set("monthsUnpaid", new_);
	}

	public String getFirstName(){
		return get("firstName");
	}
	public void setFirstName( String new_){
		set("firstName", new_);
	}

	public String getLastName(){
		return get("lastName");
	}
	public void setLastName( String new_){
		set("lastName", new_);
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


}

