
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.martinanalytics.legaltime.client.widget.GXTValidator;




public class CustomerBillRateView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel customerBillRateFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private NumberField nbrBillRate = new NumberField();
 	private DateField dtfLastUpdate = new DateField();
 	private TextField<String> txtUserId = new TextField<String>();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrCustomerBillRateId = new NumberField();
  	private CustomerBillRateComposite customerBillRateComposite;
	public CustomerBillRateView(){
		userProfile = UserProfile.getInstance();
		customerBillRateComposite =new CustomerBillRateComposite();
	}
	/**
	 * @return the nbrBillRate
	 */
	public NumberField getNbrBillRate() {
		return nbrBillRate;
	}

	/**
	 * @return the dtfLastUpdate
	 */
	public DateField getDtfLastUpdate() {
		return dtfLastUpdate;
	}

	/**
	 * @return the txtUserId
	 */
	public TextField<String> getTxtUserId() {
		return txtUserId;
	}

	/**
	 * @return the nbrCustomerId
	 */
	public NumberField getNbrCustomerId() {
		return nbrCustomerId;
	}

	/**
	 * @return the nbrClientId
	 */
	public NumberField getNbrClientId() {
		return nbrClientId;
	}

	/**
	 * @return the nbrCustomerBillRateId
	 */
	public NumberField getNbrCustomerBillRateId() {
		return nbrCustomerBillRateId;
	}

	/**
	 * @return the customerBillRateComposite
	 */
	public CustomerBillRateComposite getCustomerBillRateComposite() {
		return customerBillRateComposite;
	}


class CustomerBillRateComposite extends Composite{
	public CustomerBillRateComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		FormPanel customerBillRateFormPanel = new FormPanel();
		customerBillRateFormPanel.setFrame(true);
		customerBillRateFormPanel.setHeading("customer_bill_rate Information");
		customerBillRateFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(customerBillRateFormPanel);
	}
	public void createControls(){

//---------------------------------------------------------------
		nbrBillRate.setFieldLabel("BillRate");
		nbrBillRate.setName("billRate");
		nbrBillRate.setRegex(GXTValidator.DOUBLE);
		//nbrBillRate.setAllowBlank(false);
		nbrBillRate.setAutoValidate(true);
		//nbrBillRate.setVisible(false);
		customerBillRateFormPanel.add(nbrBillRate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		customerBillRateFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtUserId.setFieldLabel("UserId");
		txtUserId.setName("userId");
		//txtUserId.setRegex("");
		//txtUserId.setAutoValidate(true);
		//txtUserId.setAllowBlank(false);
		//txtUserId.setVisible(false);
		customerBillRateFormPanel.add(txtUserId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerId.setFieldLabel("CustomerId");
		nbrCustomerId.setName("customerId");
		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
		//nbrCustomerId.setAllowBlank(false);
		nbrCustomerId.setAutoValidate(true);
		//nbrCustomerId.setVisible(false);
		customerBillRateFormPanel.add(nbrCustomerId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrClientId.setFieldLabel("ClientId");
		nbrClientId.setName("clientId");
		nbrClientId.setRegex(GXTValidator.DOUBLE);
		//nbrClientId.setAllowBlank(false);
		nbrClientId.setAutoValidate(true);
		//nbrClientId.setVisible(false);
		customerBillRateFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerBillRateId.setFieldLabel("CustomerBillRateId");
		nbrCustomerBillRateId.setName("customerBillRateId");
		nbrCustomerBillRateId.setRegex(GXTValidator.DOUBLE);
		//nbrCustomerBillRateId.setAllowBlank(false);
		nbrCustomerBillRateId.setAutoValidate(true);
		//nbrCustomerBillRateId.setVisible(false);
		customerBillRateFormPanel.add(nbrCustomerBillRateId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "CustomerBillRateViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "CustomerBillRateViewOnDetach");
	}

}

/**
 * @return the customerBillRateFormPanel
 */
public FormPanel getCustomerBillRateFormPanel() {
	return customerBillRateFormPanel;
}


//	/**
//	 * @param customerBillRateTable the customerBillRateTable to set
//	 */
//	public void setCustomerBillRateTable(CustomerBillRateTable customerBillRateTable_) {
//		this.customerBillRateTable = customerBillRateTable_;
//	}
//	/**
//	 * @return the customerBillRateTable
//	 */
//	public CustomerBillRateTable getCustomerBillRateTable() {
//		return customerBillRateTable;
//	}


}

