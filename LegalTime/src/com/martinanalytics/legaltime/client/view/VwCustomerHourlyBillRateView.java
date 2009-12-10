
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




public class VwCustomerHourlyBillRateView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel vwCustomerHourlyBillRateFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrBillRate = new NumberField();
 	private TextField<String> txtDisplayName = new TextField<String>();
 	private TextField<String> txtUserId = new TextField<String>();
 	private NumberField nbrCustomerBillRateId = new NumberField();
  	private VwCustomerHourlyBillRateComposite vwCustomerHourlyBillRateComposite;
	public VwCustomerHourlyBillRateView(){
		userProfile = UserProfile.getInstance();
		vwCustomerHourlyBillRateComposite =new VwCustomerHourlyBillRateComposite();
	}
	/**
	 * @return the dtfLastUpdate
	 */
	public DateField getDtfLastUpdate() {
		return dtfLastUpdate;
	}

	/**
	 * @return the nbrCustomerId
	 */
	public NumberField getNbrCustomerId() {
		return nbrCustomerId;
	}

	/**
	 * @return the nbrBillRate
	 */
	public NumberField getNbrBillRate() {
		return nbrBillRate;
	}

	/**
	 * @return the txtDisplayName
	 */
	public TextField<String> getTxtDisplayName() {
		return txtDisplayName;
	}

	/**
	 * @return the txtUserId
	 */
	public TextField<String> getTxtUserId() {
		return txtUserId;
	}

	/**
	 * @return the nbrCustomerBillRateId
	 */
	public NumberField getNbrCustomerBillRateId() {
		return nbrCustomerBillRateId;
	}

	/**
	 * @return the vwCustomerHourlyBillRateComposite
	 */
	public VwCustomerHourlyBillRateComposite getVwCustomerHourlyBillRateComposite() {
		return vwCustomerHourlyBillRateComposite;
	}


class VwCustomerHourlyBillRateComposite extends Composite{
	public VwCustomerHourlyBillRateComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		FormPanel vwCustomerHourlyBillRateFormPanel = new FormPanel();
		vwCustomerHourlyBillRateFormPanel.setFrame(true);
		vwCustomerHourlyBillRateFormPanel.setHeading("vw_customer_hourly_bill_rate Information");
		vwCustomerHourlyBillRateFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(vwCustomerHourlyBillRateFormPanel);
	}
	public void createControls(){

//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerId.setFieldLabel("CustomerId");
		nbrCustomerId.setName("customerId");
		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
		//nbrCustomerId.setAllowBlank(false);
		nbrCustomerId.setAutoValidate(true);
		//nbrCustomerId.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(nbrCustomerId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrBillRate.setFieldLabel("BillRate");
		nbrBillRate.setName("billRate");
		nbrBillRate.setRegex(GXTValidator.DOUBLE);
		//nbrBillRate.setAllowBlank(false);
		nbrBillRate.setAutoValidate(true);
		//nbrBillRate.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(nbrBillRate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtDisplayName.setFieldLabel("DisplayName");
		txtDisplayName.setName("displayName");
		//txtDisplayName.setRegex("");
		//txtDisplayName.setAutoValidate(true);
		//txtDisplayName.setAllowBlank(false);
		//txtDisplayName.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(txtDisplayName);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtUserId.setFieldLabel("UserId");
		txtUserId.setName("userId");
		//txtUserId.setRegex("");
		//txtUserId.setAutoValidate(true);
		//txtUserId.setAllowBlank(false);
		//txtUserId.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(txtUserId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerBillRateId.setFieldLabel("CustomerBillRateId");
		nbrCustomerBillRateId.setName("customerBillRateId");
		nbrCustomerBillRateId.setRegex(GXTValidator.DOUBLE);
		//nbrCustomerBillRateId.setAllowBlank(false);
		nbrCustomerBillRateId.setAutoValidate(true);
		//nbrCustomerBillRateId.setVisible(false);
		vwCustomerHourlyBillRateFormPanel.add(nbrCustomerBillRateId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "VwCustomerHourlyBillRateViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "VwCustomerHourlyBillRateViewOnDetach");
	}

}

/**
 * @return the vwCustomerHourlyBillRateFormPanel
 */
public FormPanel getVwCustomerHourlyBillRateFormPanel() {
	return vwCustomerHourlyBillRateFormPanel;
}


//	/**
//	 * @param vwCustomerHourlyBillRateTable the vwCustomerHourlyBillRateTable to set
//	 */
//	public void setVwCustomerHourlyBillRateTable(VwCustomerHourlyBillRateTable vwCustomerHourlyBillRateTable_) {
//		this.vwCustomerHourlyBillRateTable = vwCustomerHourlyBillRateTable_;
//	}
//	/**
//	 * @return the vwCustomerHourlyBillRateTable
//	 */
//	public VwCustomerHourlyBillRateTable getVwCustomerHourlyBillRateTable() {
//		return vwCustomerHourlyBillRateTable;
//	}


}

