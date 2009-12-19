
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




public class UserInfoView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel userInfoFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private TextField<String> txtEmailAddr = new TextField<String>();
 	private TextField<String> txtDisplayName = new TextField<String>();
 	private NumberField nbrDefaultBillRate = new NumberField();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrClientId = new NumberField();
 	private TextField<String> txtUserId = new TextField<String>();
  	private UserInfoComposite userInfoComposite;
	public UserInfoView(){
		userProfile = UserProfile.getInstance();
		userInfoComposite =new UserInfoComposite();
	}
	/**
	 * @return the txtEmailAddr
	 */
	public TextField<String> getTxtEmailAddr() {
		return txtEmailAddr;
	}

	/**
	 * @return the txtDisplayName
	 */
	public TextField<String> getTxtDisplayName() {
		return txtDisplayName;
	}

	/**
	 * @return the nbrDefaultBillRate
	 */
	public NumberField getNbrDefaultBillRate() {
		return nbrDefaultBillRate;
	}

	/**
	 * @return the dtfLastUpdate
	 */
	public DateField getDtfLastUpdate() {
		return dtfLastUpdate;
	}

	/**
	 * @return the nbrClientId
	 */
	public NumberField getNbrClientId() {
		return nbrClientId;
	}

	/**
	 * @return the txtUserId
	 */
	public TextField<String> getTxtUserId() {
		return txtUserId;
	}

	/**
	 * @return the userInfoComposite
	 */
	public UserInfoComposite getUserInfoComposite() {
		return userInfoComposite;
	}


class UserInfoComposite extends Composite{
	public UserInfoComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		FormPanel userInfoFormPanel = new FormPanel();
		userInfoFormPanel.setFrame(true);
		userInfoFormPanel.setHeading("user_info Information");
		userInfoFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(userInfoFormPanel);
	}
	public void createControls(){

//---------------------------------------------------------------
		txtEmailAddr.setFieldLabel("EmailAddr");
		txtEmailAddr.setName("emailAddr");
		txtEmailAddr.setFireChangeEventOnSetValue(true);
		//txtEmailAddr.setRegex("");
		//txtEmailAddr.setAutoValidate(true);
		//txtEmailAddr.setAllowBlank(false);
		//txtEmailAddr.setVisible(false);
		userInfoFormPanel.add(txtEmailAddr);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtDisplayName.setFieldLabel("DisplayName");
		txtDisplayName.setName("displayName");
		txtDisplayName.setFireChangeEventOnSetValue(true);
		//txtDisplayName.setRegex("");
		//txtDisplayName.setAutoValidate(true);
		//txtDisplayName.setAllowBlank(false);
		//txtDisplayName.setVisible(false);
		userInfoFormPanel.add(txtDisplayName);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrDefaultBillRate.setFieldLabel("DefaultBillRate");
		nbrDefaultBillRate.setName("defaultBillRate");
		nbrDefaultBillRate.setRegex(GXTValidator.DOUBLE);
		nbrDefaultBillRate.setFireChangeEventOnSetValue(true);
		//nbrDefaultBillRate.setAllowBlank(false);
		nbrDefaultBillRate.setAutoValidate(true);
		//nbrDefaultBillRate.setVisible(false);
		userInfoFormPanel.add(nbrDefaultBillRate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		dtfLastUpdate.setFireChangeEventOnSetValue(true);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		userInfoFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrClientId.setFieldLabel("ClientId");
		nbrClientId.setName("clientId");
		nbrClientId.setRegex(GXTValidator.DOUBLE);
		nbrClientId.setFireChangeEventOnSetValue(true);
		//nbrClientId.setAllowBlank(false);
		nbrClientId.setAutoValidate(true);
		//nbrClientId.setVisible(false);
		userInfoFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtUserId.setFieldLabel("UserId");
		txtUserId.setName("userId");
		txtUserId.setFireChangeEventOnSetValue(true);
		//txtUserId.setRegex("");
		//txtUserId.setAutoValidate(true);
		//txtUserId.setAllowBlank(false);
		//txtUserId.setVisible(false);
		userInfoFormPanel.add(txtUserId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "UserInfoViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "UserInfoViewOnDetach");
	}

}

/**
 * @return the userInfoFormPanel
 */
public FormPanel getUserInfoFormPanel() {
	return userInfoFormPanel;
}


//	/**
//	 * @param userInfoTable the userInfoTable to set
//	 */
//	public void setUserInfoTable(UserInfoTable userInfoTable_) {
//		this.userInfoTable = userInfoTable_;
//	}
//	/**
//	 * @return the userInfoTable
//	 */
//	public UserInfoTable getUserInfoTable() {
//		return userInfoTable;
//	}


}

