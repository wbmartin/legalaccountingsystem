
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




public class FollowupView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel followupFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private TextField<String> txtAssignedUserId = new TextField<String>();
 	private TextField<String> txtFollowupDescription = new TextField<String>();
 	private DateField dtfCloseDt = new DateField();
 	private DateField dtfOpenDt = new DateField();
 	private DateField dtfDueDt = new DateField();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrFollowupId = new NumberField();
  	private FollowupComposite followupComposite;
	public FollowupView(){
		userProfile = UserProfile.getInstance();
		followupComposite =new FollowupComposite();
	}
	/**
	 * @return the txtAssignedUserId
	 */
	public TextField<String> getTxtAssignedUserId() {
		return txtAssignedUserId;
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
	 * @return the nbrClientId
	 */
	public NumberField getNbrClientId() {
		return nbrClientId;
	}

	/**
	 * @return the nbrFollowupId
	 */
	public NumberField getNbrFollowupId() {
		return nbrFollowupId;
	}

	/**
	 * @return the followupComposite
	 */
	public FollowupComposite getFollowupComposite() {
		return followupComposite;
	}


class FollowupComposite extends Composite{
	public FollowupComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		FormPanel followupFormPanel = new FormPanel();
		followupFormPanel.setFrame(true);
		followupFormPanel.setHeading("followup Information");
		followupFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(followupFormPanel);
	}
	public void createControls(){

//---------------------------------------------------------------
		txtAssignedUserId.setFieldLabel("AssignedUserId");
		txtAssignedUserId.setName("assignedUserId");
		txtAssignedUserId.setFireChangeEventOnSetValue(true);
		//txtAssignedUserId.setRegex("");
		//txtAssignedUserId.setAutoValidate(true);
		//txtAssignedUserId.setAllowBlank(false);
		//txtAssignedUserId.setVisible(false);
		followupFormPanel.add(txtAssignedUserId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtFollowupDescription.setFieldLabel("FollowupDescription");
		txtFollowupDescription.setName("followupDescription");
		txtFollowupDescription.setFireChangeEventOnSetValue(true);
		//txtFollowupDescription.setRegex("");
		//txtFollowupDescription.setAutoValidate(true);
		//txtFollowupDescription.setAllowBlank(false);
		//txtFollowupDescription.setVisible(false);
		followupFormPanel.add(txtFollowupDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfCloseDt.setFieldLabel("CloseDt");
		dtfCloseDt.setName("closeDt");
		//dtfCloseDt.setAllowBlank(false);
		dtfCloseDt.setFireChangeEventOnSetValue(true);
		//dtfCloseDt.setRegex("");	
		//dtfCloseDt.setAutoValidate(true);
		//dtfCloseDt.setVisible(false);
		followupFormPanel.add(dtfCloseDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfOpenDt.setFieldLabel("OpenDt");
		dtfOpenDt.setName("openDt");
		//dtfOpenDt.setAllowBlank(false);
		dtfOpenDt.setFireChangeEventOnSetValue(true);
		//dtfOpenDt.setRegex("");	
		//dtfOpenDt.setAutoValidate(true);
		//dtfOpenDt.setVisible(false);
		followupFormPanel.add(dtfOpenDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfDueDt.setFieldLabel("DueDt");
		dtfDueDt.setName("dueDt");
		//dtfDueDt.setAllowBlank(false);
		dtfDueDt.setFireChangeEventOnSetValue(true);
		//dtfDueDt.setRegex("");	
		//dtfDueDt.setAutoValidate(true);
		//dtfDueDt.setVisible(false);
		followupFormPanel.add(dtfDueDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		dtfLastUpdate.setFireChangeEventOnSetValue(true);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		followupFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerId.setFieldLabel("CustomerId");
		nbrCustomerId.setName("customerId");
		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
		nbrCustomerId.setFireChangeEventOnSetValue(true);
		//nbrCustomerId.setAllowBlank(false);
		nbrCustomerId.setAutoValidate(true);
		//nbrCustomerId.setVisible(false);
		followupFormPanel.add(nbrCustomerId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrClientId.setFieldLabel("ClientId");
		nbrClientId.setName("clientId");
		nbrClientId.setRegex(GXTValidator.DOUBLE);
		nbrClientId.setFireChangeEventOnSetValue(true);
		//nbrClientId.setAllowBlank(false);
		nbrClientId.setAutoValidate(true);
		//nbrClientId.setVisible(false);
		followupFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrFollowupId.setFieldLabel("FollowupId");
		nbrFollowupId.setName("followupId");
		nbrFollowupId.setRegex(GXTValidator.DOUBLE);
		nbrFollowupId.setFireChangeEventOnSetValue(true);
		//nbrFollowupId.setAllowBlank(false);
		nbrFollowupId.setAutoValidate(true);
		//nbrFollowupId.setVisible(false);
		followupFormPanel.add(nbrFollowupId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "FollowupViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "FollowupViewOnDetach");
	}

}

/**
 * @return the followupFormPanel
 */
public FormPanel getFollowupFormPanel() {
	return followupFormPanel;
}


//	/**
//	 * @param followupTable the followupTable to set
//	 */
//	public void setFollowupTable(FollowupTable followupTable_) {
//		this.followupTable = followupTable_;
//	}
//	/**
//	 * @return the followupTable
//	 */
//	public FollowupTable getFollowupTable() {
//		return followupTable;
//	}


}

