
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




public class VwCustomerFollowupView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel vwCustomerFollowupFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private TextField<String> txtLastName = new TextField<String>();
 	private TextField<String> txtFirstName = new TextField<String>();
 	private TextField<String> txtFollowupDescription = new TextField<String>();
 	private DateField dtfCloseDt = new DateField();
 	private DateField dtfOpenDt = new DateField();
 	private DateField dtfDueDt = new DateField();
 	private NumberField nbrFollowupId = new NumberField();
  	private VwCustomerFollowupComposite vwCustomerFollowupComposite;
	public VwCustomerFollowupView(){
		userProfile = UserProfile.getInstance();
		vwCustomerFollowupComposite =new VwCustomerFollowupComposite();
	}
	/**
	 * @return the txtLastName
	 */
	public TextField<String> getTxtLastName() {
		return txtLastName;
	}

	/**
	 * @return the txtFirstName
	 */
	public TextField<String> getTxtFirstName() {
		return txtFirstName;
	}

	/**
	 * @return the txtFollowupDescription
	 */
	public TextField<String> getTxtFollowupDescription() {
		return txtFollowupDescription;
	}

	/**
	 * @return the dtfCloseDt
	 */
	public DateField getDtfCloseDt() {
		return dtfCloseDt;
	}

	/**
	 * @return the dtfOpenDt
	 */
	public DateField getDtfOpenDt() {
		return dtfOpenDt;
	}

	/**
	 * @return the dtfDueDt
	 */
	public DateField getDtfDueDt() {
		return dtfDueDt;
	}

	/**
	 * @return the nbrFollowupId
	 */
	public NumberField getNbrFollowupId() {
		return nbrFollowupId;
	}

	/**
	 * @return the vwCustomerFollowupComposite
	 */
	public VwCustomerFollowupComposite getVwCustomerFollowupComposite() {
		return vwCustomerFollowupComposite;
	}


class VwCustomerFollowupComposite extends Composite{
	public VwCustomerFollowupComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		FormPanel vwCustomerFollowupFormPanel = new FormPanel();
		vwCustomerFollowupFormPanel.setFrame(true);
		vwCustomerFollowupFormPanel.setHeading("vw_customer_followup Information");
		vwCustomerFollowupFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(vwCustomerFollowupFormPanel);
	}
	public void createControls(){

//---------------------------------------------------------------
		txtLastName.setFieldLabel("LastName");
		txtLastName.setName("lastName");
		txtLastName.setFireChangeEventOnSetValue(true);
		//txtLastName.setRegex("");
		//txtLastName.setAutoValidate(true);
		//txtLastName.setAllowBlank(false);
		//txtLastName.setVisible(false);
		vwCustomerFollowupFormPanel.add(txtLastName);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtFirstName.setFieldLabel("FirstName");
		txtFirstName.setName("firstName");
		txtFirstName.setFireChangeEventOnSetValue(true);
		//txtFirstName.setRegex("");
		//txtFirstName.setAutoValidate(true);
		//txtFirstName.setAllowBlank(false);
		//txtFirstName.setVisible(false);
		vwCustomerFollowupFormPanel.add(txtFirstName);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtFollowupDescription.setFieldLabel("FollowupDescription");
		txtFollowupDescription.setName("followupDescription");
		txtFollowupDescription.setFireChangeEventOnSetValue(true);
		//txtFollowupDescription.setRegex("");
		//txtFollowupDescription.setAutoValidate(true);
		//txtFollowupDescription.setAllowBlank(false);
		//txtFollowupDescription.setVisible(false);
		vwCustomerFollowupFormPanel.add(txtFollowupDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfCloseDt.setFieldLabel("CloseDt");
		dtfCloseDt.setName("closeDt");
		//dtfCloseDt.setAllowBlank(false);
		dtfCloseDt.setFireChangeEventOnSetValue(true);
		//dtfCloseDt.setRegex("");	
		//dtfCloseDt.setAutoValidate(true);
		//dtfCloseDt.setVisible(false);
		vwCustomerFollowupFormPanel.add(dtfCloseDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfOpenDt.setFieldLabel("OpenDt");
		dtfOpenDt.setName("openDt");
		//dtfOpenDt.setAllowBlank(false);
		dtfOpenDt.setFireChangeEventOnSetValue(true);
		//dtfOpenDt.setRegex("");	
		//dtfOpenDt.setAutoValidate(true);
		//dtfOpenDt.setVisible(false);
		vwCustomerFollowupFormPanel.add(dtfOpenDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfDueDt.setFieldLabel("DueDt");
		dtfDueDt.setName("dueDt");
		//dtfDueDt.setAllowBlank(false);
		dtfDueDt.setFireChangeEventOnSetValue(true);
		//dtfDueDt.setRegex("");	
		//dtfDueDt.setAutoValidate(true);
		//dtfDueDt.setVisible(false);
		vwCustomerFollowupFormPanel.add(dtfDueDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrFollowupId.setFieldLabel("FollowupId");
		nbrFollowupId.setName("followupId");
		nbrFollowupId.setRegex(GXTValidator.DOUBLE);
		nbrFollowupId.setFireChangeEventOnSetValue(true);
		//nbrFollowupId.setAllowBlank(false);
		nbrFollowupId.setAutoValidate(true);
		//nbrFollowupId.setVisible(false);
		vwCustomerFollowupFormPanel.add(nbrFollowupId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "VwCustomerFollowupViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "VwCustomerFollowupViewOnDetach");
	}

}

/**
 * @return the vwCustomerFollowupFormPanel
 */
public FormPanel getVwCustomerFollowupFormPanel() {
	return vwCustomerFollowupFormPanel;
}


//	/**
//	 * @param vwCustomerFollowupTable the vwCustomerFollowupTable to set
//	 */
//	public void setVwCustomerFollowupTable(VwCustomerFollowupTable vwCustomerFollowupTable_) {
//		this.vwCustomerFollowupTable = vwCustomerFollowupTable_;
//	}
//	/**
//	 * @return the vwCustomerFollowupTable
//	 */
//	public VwCustomerFollowupTable getVwCustomerFollowupTable() {
//		return vwCustomerFollowupTable;
//	}


}

