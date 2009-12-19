
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.UserInfoCache;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.extjs.gxt.ui.client.data.ModelProcessor;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ListModelPropertyEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.martinanalytics.legaltime.client.widget.DecentComboBox;
import com.martinanalytics.legaltime.client.widget.GXTValidator;





public class FollowupView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel followupFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private ComboBox<UserInfoBean> cboAssignedUserId = new ComboBox<UserInfoBean>();
	private DecentComboBox<UserInfoBean> lbtest = new DecentComboBox<UserInfoBean>();
	//private ListBox cboAssignedUserId = new ListBox();
	
 	private TextField<String> txtFollowupDescription = new TextField<String>();
 	private DateField dtfCloseDt = new DateField();
 	private DateField dtfOpenDt = new DateField();
 	private DateField dtfDueDt = new DateField();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrFollowupId = new NumberField();
  	private FollowupComposite followupComposite;
	private ListStore<UserInfoBean> userInfoBeanStore = new ListStore<UserInfoBean>();
	//private ListBox lbtest = new ListBox();
	public FollowupView(){
		userProfile = UserProfile.getInstance();
		followupComposite =new FollowupComposite();
		cboAssignedUserId.setFieldLabel("Owner");
		cboAssignedUserId.setStore(userInfoBeanStore);
		cboAssignedUserId.setDisplayField("userId");
//		 ListView<UserInfoBean> lv = new ListView<UserInfoBean>();
//         lv.setStore(cboAssignedUserId.getStore());
//         lv.setLoadingText("LOADING");
//         lv.setDisplayProperty("displayName");
		
		//cboAssignedUserId.setValueField("userId");
//		cboAssignedUserId.setPropertyEditor(new ListModelPropertyEditor<UserInfoBean>() {
//			   public String getStringValue(UserInfoBean value) {
//			     return value.getDisplayName();
//			   }
//			 });
//		cboAssignedUserId.getView().setModelProcessor(new ModelProcessor<UserInfoBean>() {
//			   public UserInfoBean prepareData(UserInfoBean model) {
//			     model.set("test", model.getDisplayName());
//			     return model;
//			   }
//			 });
		
		
		//cboAssignedUserId.setValueField("userId");
		cboAssignedUserId.addStyleName("LEFT");
		followupFormPanel.setLabelWidth(150);
		followupFormPanel.setHeading("Follow up Editor");
		followupFormPanel.addStyleName("LEFT");
		
		
	}
	/**
	 * @return the txtAssignedUserId
	 */
	
	public ComboBox<UserInfoBean> getCboAssignedUserId() {
		return cboAssignedUserId;
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
		followupFormPanel.setHeading("Followup Information");
		followupFormPanel.setHeaderVisible(true);
		followupFormPanel.setWidth(600);
		createControls();
		

	//	initWidget(vpPrimary);
		initWidget(followupFormPanel);
	}
	public void createControls(){
		lbtest.setFieldLabel("Owner");
		lbtest.setLabelWidth(150);
		followupFormPanel.add(lbtest);
//---------------------------------------------------------------
//		cboAssignedUserId.setFieldLabel("Assigned User Id");
		cboAssignedUserId.setName("assignedUserId");
//		cboAssignedUserId.setFireChangeEventOnSetValue(true);
		//txtAssignedUserId.setRegex("");
		//txtAssignedUserId.setAutoValidate(true);
		//txtAssignedUserId.setAllowBlank(false);
		//txtAssignedUserId.setVisible(false);
		followupFormPanel.add(cboAssignedUserId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtFollowupDescription.setFieldLabel("Description");
		txtFollowupDescription.setName("followupDescription");
		txtFollowupDescription.setFireChangeEventOnSetValue(true);
		txtFollowupDescription.setHeight(200);
		//txtFollowupDescription.setRegex("");
		//txtFollowupDescription.setAutoValidate(true);
		//txtFollowupDescription.setAllowBlank(false);
		//txtFollowupDescription.setVisible(false);
		followupFormPanel.add(txtFollowupDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfCloseDt.setFieldLabel("Close Date");
		dtfCloseDt.setName("closeDt");
		//dtfCloseDt.setAllowBlank(false);
		dtfCloseDt.setFireChangeEventOnSetValue(true);
		//dtfCloseDt.setRegex("");	
		//dtfCloseDt.setAutoValidate(true);
		//dtfCloseDt.setVisible(false);
		followupFormPanel.add(dtfCloseDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfOpenDt.setFieldLabel("Open Date");
		dtfOpenDt.setName("openDt");
		//dtfOpenDt.setAllowBlank(false);
		dtfOpenDt.setFireChangeEventOnSetValue(true);
		//dtfOpenDt.setRegex("");	
		//dtfOpenDt.setAutoValidate(true);
		//dtfOpenDt.setVisible(false);
		followupFormPanel.add(dtfOpenDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfDueDt.setFieldLabel("Due Date");
		dtfDueDt.setName("dueDt");
		//dtfDueDt.setAllowBlank(false);
		dtfDueDt.setFireChangeEventOnSetValue(true);
		//dtfDueDt.setRegex("");	
		//dtfDueDt.setAutoValidate(true);
		//dtfDueDt.setVisible(false);
		followupFormPanel.add(dtfDueDt);
//---------------------------------------------------------------



////---------------------------------------------------------------
//		dtfLastUpdate.setFieldLabel("LastUpdate");
//		dtfLastUpdate.setName("lastUpdate");
//		//dtfLastUpdate.setAllowBlank(false);
//		dtfLastUpdate.setFireChangeEventOnSetValue(true);
//		//dtfLastUpdate.setRegex("");	
//		//dtfLastUpdate.setAutoValidate(true);
//		//dtfLastUpdate.setVisible(false);
//		followupFormPanel.add(dtfLastUpdate);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrCustomerId.setFieldLabel("CustomerId");
//		nbrCustomerId.setName("customerId");
//		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
//		nbrCustomerId.setFireChangeEventOnSetValue(true);
//		//nbrCustomerId.setAllowBlank(false);
//		nbrCustomerId.setAutoValidate(true);
//		//nbrCustomerId.setVisible(false);
//		followupFormPanel.add(nbrCustomerId);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrClientId.setFieldLabel("ClientId");
//		nbrClientId.setName("clientId");
//		nbrClientId.setRegex(GXTValidator.DOUBLE);
//		nbrClientId.setFireChangeEventOnSetValue(true);
//		//nbrClientId.setAllowBlank(false);
//		nbrClientId.setAutoValidate(true);
//		//nbrClientId.setVisible(false);
//		followupFormPanel.add(nbrClientId);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrFollowupId.setFieldLabel("FollowupId");
//		nbrFollowupId.setName("followupId");
//		nbrFollowupId.setRegex(GXTValidator.DOUBLE);
//		nbrFollowupId.setFireChangeEventOnSetValue(true);
//		//nbrFollowupId.setAllowBlank(false);
//		nbrFollowupId.setAutoValidate(true);
//		//nbrFollowupId.setVisible(false);
//		followupFormPanel.add(nbrFollowupId);
////---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		//notifyAppEvent(this, "FollowupViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		//notifyAppEvent(this, "FollowupViewOnDetach");
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

public void populateAssignedUserCBO(boolean force){
	if(userInfoBeanStore.getCount()==0 || force)
		userInfoBeanStore.removeAll();
		userInfoBeanStore.add(UserInfoCache.getCache());
		Log.debug("userinfo size:" + UserInfoCache.getCache().size());
//		lbtest.clear();
//		for(int ndx =0;ndx < UserInfoCache.getCache().size();ndx++){
//			lbtest.addItem(UserInfoCache.getCache().get(ndx).getDisplayName(), UserInfoCache.getCache().get(ndx).getUserId());
//		}
		lbtest.setList("displayName", "userId", UserInfoCache.getCache());
		//cboAssignedUserId.addStyleName("LEFT");
    }
/**
 * @param userInfoBeanStore the userInfoBeanStore to set
 */
public void setUserInfoBeanStore(ListStore<UserInfoBean> userInfoBeanStore) {
	this.userInfoBeanStore = userInfoBeanStore;
}
/**
 * @return the userInfoBeanStore
 */
public ListStore<UserInfoBean> getUserInfoBeanStore() {
	return userInfoBeanStore;
}

/**
 * @return the lbtest
 */
public DecentComboBox<UserInfoBean> getLbtest() {
	return lbtest;
}

}

