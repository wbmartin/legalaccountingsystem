
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.UserInfoCache;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.SimpleComboBoxFieldBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;
import com.martinanalytics.legaltime.client.widget.AlternateComboBoxBinding;
import com.martinanalytics.legaltime.client.widget.DecentComboBox;
import com.martinanalytics.legaltime.client.widget.GXTValidator;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ButtonEvent; 


public class LaborRegisterView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel laborRegisterFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel(); 
	//-----------------------
	private FormBinding formBindings; 
	private final ListStore<LaborRegisterBean> store = new ListStore<LaborRegisterBean>();
	//-----------------------
	
	//private DecentComboBox<UserInfoBean> cboUserId = new DecentComboBox<UserInfoBean>();
	//private TextField<String> txtUserId = new TextField<String>();
 	private NumberField nbrInvoiceId = new NumberField();
 	private NumberField nbrBillRate = new NumberField();
 	private TextField<Boolean> txtInvoiceable = new TextField<Boolean>();
 	private DateField dtfActivityDate = new DateField();
 	private DateField dtfEndTime = new DateField();
 	private DateField dtfStartTime = new DateField();
 	private NumberField nbrMinuteCount = new NumberField();
 	private TextField<String> txtDescription = new TextField<String>();
 	private DateField dtfLastUpdate = new DateField();
 	//private NumberField nbrCustomerId = new NumberField();
 	private AlternateComboBox<CustomerBean> cboCustomerId = new AlternateComboBox<CustomerBean>("Customer","customerId", "CustomerId", "displayName");
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrLaborRegisterId = new NumberField();
  	private LaborRegisterComposite laborRegisterComposite;
	 AlternateComboBox<UserInfoBean> cboUserId = new AlternateComboBox<UserInfoBean>("Billing Person", "userId","userId","displayName");
	public LaborRegisterView(){
		userProfile = UserProfile.getInstance();
		laborRegisterComposite =new LaborRegisterComposite();
//-----------------------		
		formBindings = new FormBinding(laborRegisterFormPanel, true);
		AlternateComboBoxBinding.swapBinding(formBindings, cboCustomerId);
		AlternateComboBoxBinding.swapBinding(formBindings, cboUserId);
		formBindings.setStore(store); 
//		formBindings.removeFieldBinding((formBindings.getBinding(cboBillType)));
//		formBindings.addFieldBinding(new SimpleComboBoxFieldBinding(cboBillType, "billType"));
		//formBindings.addFieldBinding(new f)
		
//-----------------------
	}
	/**
	 * @return the txtUserId
	 */
	public AlternateComboBox<UserInfoBean> getCboUserId() {
		return cboUserId;
	}

	/**
	 * @return the nbrInvoiceId
	 */
	public NumberField getNbrInvoiceId() {
		return nbrInvoiceId;
	}

	/**
	 * @return the nbrBillRate
	 */
	public NumberField getNbrBillRate() {
		return nbrBillRate;
	}

	/**
	 * @return the txtInvoiceable
	 */
	public TextField<Boolean> getTxtInvoiceable() {
		return txtInvoiceable;
	}

	/**
	 * @return the dtfActivityDate
	 */
	public DateField getDtfActivityDate() {
		return dtfActivityDate;
	}

	/**
	 * @return the dtfEndTime
	 */
	public DateField getDtfEndTime() {
		return dtfEndTime;
	}

	/**
	 * @return the dtfStartTime
	 */
	public DateField getDtfStartTime() {
		return dtfStartTime;
	}

	/**
	 * @return the nbrMinuteCount
	 */
	public NumberField getNbrMinuteCount() {
		return nbrMinuteCount;
	}

	/**
	 * @return the txtDescription
	 */
	public TextField<String> getTxtDescription() {
		return txtDescription;
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
	public AlternateComboBox<CustomerBean> getCboCustomerId() {
		return cboCustomerId;
	}

	/**
	 * @return the nbrClientId
	 */
	public NumberField getNbrClientId() {
		return nbrClientId;
	}

	/**
	 * @return the nbrLaborRegisterId
	 */
	public NumberField getNbrLaborRegisterId() {
		return nbrLaborRegisterId;
	}

	/**
	 * @return the laborRegisterComposite
	 */
	public LaborRegisterComposite getLaborRegisterComposite() {
		return laborRegisterComposite;
	}


class LaborRegisterComposite extends Composite{


	public LaborRegisterComposite(){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

		 laborRegisterFormPanel = new FormPanel();
		laborRegisterFormPanel.setFrame(true);
		laborRegisterFormPanel.setHeading("Billing Hours");
		laborRegisterFormPanel.setWidth(650);
	
		createControls();
		

	//	initWidget(vpPrimary);
		VerticalPanel vp = new VerticalPanel();

		  vp.add(laborRegisterFormPanel);
	      initWidget(vp);
//		initWidget(laborRegisterFormPanel);
	}
	public void createControls(){
		

//---------------------------------------------------------------
		dtfActivityDate.setFieldLabel("Date");
		dtfActivityDate.addStyleName("LEFT");
		dtfActivityDate.setName("activityDate");
		dtfActivityDate.setAllowBlank(false);
		dtfActivityDate.setFireChangeEventOnSetValue(true);
		//dtfActivityDate.setRegex("");	
		//dtfActivityDate.setAutoValidate(true);
		//dtfActivityDate.setVisible(false);
		laborRegisterFormPanel.add(dtfActivityDate);
//---------------------------------------------------------------


//---------------------------------------------------------------
//				nbrCustomerId.setFieldLabel("CustomerId");
//				nbrCustomerId.setName("customerId");
//				nbrCustomerId.setRegex(GXTValidator.DOUBLE);
//				nbrCustomerId.setFireChangeEventOnSetValue(true);
//				//nbrCustomerId.setAllowBlank(false);
//				nbrCustomerId.setAutoValidate(true);
//				nbrCustomerId.setVisible(false);
		cboCustomerId.setAllowBlank(false);
		laborRegisterFormPanel.add(cboCustomerId);
//---------------------------------------------------------------
		final RadioGroup rgHours = new RadioGroup();
		rgHours.setFieldLabel("Hours");
		rgHours.setSelectionRequired(true);
		laborRegisterFormPanel.add(rgHours);
		laborRegisterFormPanel.setFieldWidth(400);
		
		Radio rHour0 = new Radio();
		rHour0.setBoxLabel("0");
		rgHours.add(rHour0);

		rHour0.setValue(true);
		
		Radio rHour1 = new Radio();
		rHour1.setBoxLabel("1");
		rgHours.add(rHour1);
		
		Radio rHour2 = new Radio();
		rHour2.setBoxLabel("2");
		rgHours.add(rHour2);
		
		Radio rHour3 = new Radio();
		rHour3.setBoxLabel("3");
		rgHours.add(rHour3);
		
		Radio rHour4 = new Radio();
		rHour4.setBoxLabel("4");
		rgHours.add(rHour4);
		
		
		Radio rHour5 = new Radio();
		rHour5.setBoxLabel("5");
		rgHours.add(rHour5);
		
		Radio rHour6 = new Radio();
		rHour6.setBoxLabel("6");
		rgHours.add(rHour6);		
		
		Radio rHour7 = new Radio();
		rHour7.setBoxLabel("7");
		rgHours.add(rHour7);
		
		Radio rHour8 = new Radio();
		rHour8.setBoxLabel("8");
		rgHours.add(rHour8);
		
		Radio rHour9 = new Radio();
		rHour9.setBoxLabel("9");
		rgHours.add(rHour9);
		
		Radio rHour10 = new Radio();
		rHour10.setBoxLabel("10");
		rgHours.add(rHour10);
		
//ADD Minute Chooser Here		


		final RadioGroup rgMinutes = new RadioGroup();
		rgMinutes.setSelectionRequired(true);
		rgMinutes.setFieldLabel("Minutes");
		
		Radio rMin0 = new Radio();
		rMin0.setBoxLabel("0");
		rgMinutes.add(rMin0);

		rMin0.setValue(true);
		
		Radio rMin6 = new Radio();
		rMin6.setBoxLabel("6");
		rgMinutes.add(rMin6);
		
		Radio rMin12 = new Radio();
		rMin12.setBoxLabel("12");
		rgMinutes.add(rMin12);
		
		Radio rMin18 = new Radio();
		rMin18.setBoxLabel("18");
		rgMinutes.add(rMin18);
		
		Radio rMin24 = new Radio();
		rMin24.setBoxLabel("24");
		rgMinutes.add(rMin24);
		
		Radio rMin30 = new Radio();
		rMin30.setBoxLabel("30");
		rgMinutes.add(rMin30);
		
		Radio rMin36 = new Radio();
		rMin36.setBoxLabel("36");
		rgMinutes.add(rMin36);
		
		Radio rMin42 = new Radio();
		rMin42.setBoxLabel("42");
		rgMinutes.add(rMin42);
		laborRegisterFormPanel.add(rgMinutes);
		
		Radio rMin48 = new Radio();
		rMin48.setBoxLabel("48");
		rgMinutes.add(rMin48);
		laborRegisterFormPanel.add(rgMinutes);
		
		Radio rMin54 = new Radio();
		rMin54.setBoxLabel("54");
		rgMinutes.add(rMin54);
		laborRegisterFormPanel.add(rgMinutes);
//---------------------------------------------------------------	
		cboUserId.setAllowBlank(false);
		  laborRegisterFormPanel.add(cboUserId);
//---------------------------------------------------------------		
		  
		  
		  
		//---------------------------------------------------------------
//			nbrMinuteCount.setFieldLabel("MinuteCount");
//			nbrMinuteCount.setName("minuteCount");
//			nbrMinuteCount.setRegex(GXTValidator.DOUBLE);
//			nbrMinuteCount.setFireChangeEventOnSetValue(true);
//			//nbrMinuteCount.setAllowBlank(false);
//			nbrMinuteCount.setAutoValidate(true);
//			nbrMinuteCount.setVisible(false);
//			laborRegisterFormPanel.add(nbrMinuteCount);
	//---------------------------------------------------------------



	//---------------------------------------------------------------
			txtDescription.setFieldLabel("Description");
			txtDescription.setName("description");
			txtDescription.setFireChangeEventOnSetValue(true);
			txtDescription.setHeight(200);
			//txtDescription.setRegex("");
			txtDescription.setAutoValidate(true);
			txtDescription.setAllowBlank(false);
			//txtDescription.setVisible(false);
			laborRegisterFormPanel.add(txtDescription);
			
	//---------------------------------------------------------------

		final HiddenField<Integer> hfMinuteCount = new HiddenField<Integer>();
		hfMinuteCount.setName("minuteCount");
		laborRegisterFormPanel.add(hfMinuteCount);	
//---------------------------------------------------------------
			Button cmdRecordHours = new Button("Record");
			cmdRecordHours.addSelectionListener(new SelectionListener<ButtonEvent>() {  
				  
			      @Override  
			      public void componentSelected(ButtonEvent ce) { 
			    	  Integer minuteCount = Integer.parseInt(rgHours.getValue().getBoxLabel())* 60 + Integer.parseInt(rgMinutes.getValue().getBoxLabel()) ;
			    	  hfMinuteCount.setValue( minuteCount);

			    	 
			      //Log.debug("storecoutn:" + store.getCount());
			      }
			});
			//laborRegisterFormPanel.add(cmdRecordHours);


//---------------------------------------------------------------
		
//		cboUserId.setFieldLabel("UserId");
//		cboUserId.setName("userId");
		//txtUserId.setFireChangeEventOnSetValue(true);
		//txtUserId.setRegex("");
		//txtUserId.setAutoValidate(true);
		//txtUserId.setAllowBlank(false);
		//txtUserId.setVisible(false);
		
//		laborRegisterFormPanel.add(cboUserId);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		nbrInvoiceId.setFieldLabel("InvoiceId");
//		nbrInvoiceId.setName("invoiceId");
//		nbrInvoiceId.setRegex(GXTValidator.DOUBLE);
//		nbrInvoiceId.setFireChangeEventOnSetValue(true);
//		//nbrInvoiceId.setAllowBlank(false);
//		nbrInvoiceId.setAutoValidate(true);
//		//nbrInvoiceId.setVisible(false);
//		laborRegisterFormPanel.add(nbrInvoiceId);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		nbrBillRate.setFieldLabel("BillRate");
//		nbrBillRate.setName("billRate");
//		nbrBillRate.setRegex(GXTValidator.DOUBLE);
//		nbrBillRate.setFireChangeEventOnSetValue(true);
//		//nbrBillRate.setAllowBlank(false);
//		nbrBillRate.setAutoValidate(true);
//		//nbrBillRate.setVisible(false);
//		laborRegisterFormPanel.add(nbrBillRate);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		txtInvoiceable.setFieldLabel("Invoiceable");
//		txtInvoiceable.setName("invoiceable");
//		txtInvoiceable.setFireChangeEventOnSetValue(true);
//		//txtInvoiceable.setRegex("");
//		//txtInvoiceable.setAutoValidate(true);
//		//txtInvoiceable.setAllowBlank(false);
//		//txtInvoiceable.setVisible(false);
//		laborRegisterFormPanel.add(txtInvoiceable);
//---------------------------------------------------------------




//---------------------------------------------------------------
//		dtfEndTime.setFieldLabel("EndTime");
//		dtfEndTime.setName("endTime");
//		//dtfEndTime.setAllowBlank(false);
//		dtfEndTime.setFireChangeEventOnSetValue(true);
//		//dtfEndTime.setRegex("");	
//		//dtfEndTime.setAutoValidate(true);
//		//dtfEndTime.setVisible(false);
//		laborRegisterFormPanel.add(dtfEndTime);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		dtfStartTime.setFieldLabel("StartTime");
//		dtfStartTime.setName("startTime");
//		//dtfStartTime.setAllowBlank(false);
//		dtfStartTime.setFireChangeEventOnSetValue(true);
//		//dtfStartTime.setRegex("");	
//		//dtfStartTime.setAutoValidate(true);
//		//dtfStartTime.setVisible(false);
//		laborRegisterFormPanel.add(dtfStartTime);
//---------------------------------------------------------------





//---------------------------------------------------------------
//		dtfLastUpdate.setFieldLabel("LastUpdate");
//		dtfLastUpdate.setName("lastUpdate");
//		//dtfLastUpdate.setAllowBlank(false);
//		dtfLastUpdate.setFireChangeEventOnSetValue(true);
//		//dtfLastUpdate.setRegex("");	
//		//dtfLastUpdate.setAutoValidate(true);
//		//dtfLastUpdate.setVisible(false);
//		laborRegisterFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		nbrClientId.setFieldLabel("ClientId");
//		nbrClientId.setName("clientId");
//		nbrClientId.setRegex(GXTValidator.DOUBLE);
//		nbrClientId.setFireChangeEventOnSetValue(true);
//		//nbrClientId.setAllowBlank(false);
//		nbrClientId.setAutoValidate(true);
//		//nbrClientId.setVisible(false);
//		laborRegisterFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
//		nbrLaborRegisterId.setFieldLabel("LaborRegisterId");
//		nbrLaborRegisterId.setName("laborRegisterId");
//		nbrLaborRegisterId.setRegex(GXTValidator.DOUBLE);
//		nbrLaborRegisterId.setFireChangeEventOnSetValue(true);
//		//nbrLaborRegisterId.setAllowBlank(false);
//		nbrLaborRegisterId.setAutoValidate(true);
//		//nbrLaborRegisterId.setVisible(false);
//		laborRegisterFormPanel.add(nbrLaborRegisterId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "LaborRegisterViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "LaborRegisterViewOnDetach");
	}

}

/**
 * @return the laborRegisterFormPanel
 */
public FormPanel getLaborRegisterFormPanel() {
	return laborRegisterFormPanel;
}


//	/**
//	 * @param laborRegisterTable the laborRegisterTable to set
//	 */
//	public void setLaborRegisterTable(LaborRegisterTable laborRegisterTable_) {
//		this.laborRegisterTable = laborRegisterTable_;
//	}
//	/**
//	 * @return the laborRegisterTable
//	 */
//	public LaborRegisterTable getLaborRegisterTable() {
//		return laborRegisterTable;
//	}


public void populateUserInfoList(){
	Log.debug("LaborRegisterView: " +UserInfoCache.getCache().size() );
	cboUserId.add( UserInfoCache.getCache());//"displayName", "userId",
}
/**
 * @return the store
 */
public ListStore<LaborRegisterBean> getStore() {
	return store;
}
/**
 * @param formBindings the formBindings to set
 */
public void setFormBindings(FormBinding formBindings) {
	this.formBindings = formBindings;
}
/**
 * @return the formBindings
 */
public FormBinding getFormBindings() {
	return formBindings;
}

}

