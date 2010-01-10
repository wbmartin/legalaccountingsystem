
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.martinanalytics.legaltime.client.view.table.CustomerAccountRegisterTable;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;
import com.martinanalytics.legaltime.client.widget.GXTValidator;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;

public class CustomerAccountRegisterView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel customerAccountRegisterFormPanel ;
	private ContentPanel cp = new ContentPanel();  
	private FormBinding formBindings; 
	private final ListStore<CustomerAccountRegisterBean> store = new ListStore<CustomerAccountRegisterBean>();


	private DateField dtfEffectiveDt = new DateField();
 	private TextField<String> txtTranType = new TextField<String>();
 	private NumberField nbrTranAmt = new NumberField();
 	private TextField<String> txtDescription = new TextField<String>();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrClientAccountRegisterId = new NumberField();
  	private CustomerAccountRegisterComposite customerAccountRegisterComposite;
  	private CustomerAccountRegisterTable customerAccountRegisterTable = new CustomerAccountRegisterTable();
  	private AlternateComboBox<CustomerBean> cboCustomerId = new AlternateComboBox<CustomerBean>("Customer","customerId","customerId","displayName");
  	private AppNotifyObject notifier = new AppNotifyObject ();
	public CustomerAccountRegisterView(){
		cp.setHeading("Customer Account Register");
		userProfile = UserProfile.getInstance();
		 customerAccountRegisterFormPanel = new FormPanel();
		customerAccountRegisterComposite =new CustomerAccountRegisterComposite();
		setListeners();
	}
	@SuppressWarnings("unchecked")
	private void setListeners(){
		
		cboCustomerId.addSelectionChangedListener(new SelectionChangedListener<CustomerBean>() {

			@Override
			public void selectionChanged(final SelectionChangedEvent<CustomerBean> se_) {
				
				notifier.notifyAppEvent(this, "AccountRegisterCustomerChanged", se_.getSelectedItem().getCustomerId());
				
			}
		});
	}
	/**
	 * @return the dtfEffectiveDt
	 */
	public DateField getDtfEffectiveDt() {
		return dtfEffectiveDt;
	}

	/**
	 * @return the txtTranType
	 */
	public TextField<String> getTxtTranType() {
		return txtTranType;
	}

	/**
	 * @return the nbrTranAmt
	 */
	public NumberField getNbrTranAmt() {
		return nbrTranAmt;
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
	 * @return the nbrClientAccountRegisterId
	 */
	public NumberField getNbrClientAccountRegisterId() {
		return nbrClientAccountRegisterId;
	}

	/**
	 * @return the customerAccountRegisterComposite
	 */
	public CustomerAccountRegisterComposite getCustomerAccountRegisterComposite() {
		return customerAccountRegisterComposite;
	}


class CustomerAccountRegisterComposite extends Composite{
	public CustomerAccountRegisterComposite(){
		
		
//		customerAccountRegisterFormPanel.setFrame(true);
//		customerAccountRegisterFormPanel.setHeading("customer_account_register Information");
		customerAccountRegisterFormPanel.setWidth(800);
		customerAccountRegisterFormPanel.setHeading("Customer Account Information");
//		createControls();
		
		VerticalPanel vp = new VerticalPanel();
//		  vp.add(customerAccountRegisterFormPanel);
		customerAccountRegisterFormPanel.add(cboCustomerId);
		cboCustomerId.setFieldLabel("Customer");
		customerAccountRegisterFormPanel.add(getCustomerAccountRegisterTable());
		ToolBar toolbar = new ToolBar();
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=	
		Button cmdPostPayment = new Button("Post Payment", new SelectionListener<ButtonEvent>() {  
		  	  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  

		        notifier.notifyAppEvent(this, "PostPaymentRequest", cboCustomerId.getKeyValue()) ;
		      }  
		    });
		cmdPostPayment.setBorders(true);
		toolbar.add(cmdPostPayment);
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=		
		Button cmdReverse = new Button("Reverse", new SelectionListener<ButtonEvent>() {  
		  	  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  

		        notifier.notifyAppEvent(this, "ReverseRequest") ;
		      }  
		    });
		cmdReverse.setBorders(true);
		toolbar.add(cmdReverse);
		
		
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=			
		customerAccountRegisterFormPanel.setBottomComponent(toolbar);
		vp.add(customerAccountRegisterFormPanel);
		
	      	initWidget(vp);
		

	}
	public void createControls(){

//---------------------------------------------------------------
		dtfEffectiveDt.setFieldLabel("EffectiveDt");
		dtfEffectiveDt.setName("effectiveDt");
		//dtfEffectiveDt.setAllowBlank(false);
		dtfEffectiveDt.setFireChangeEventOnSetValue(true);
		//dtfEffectiveDt.setRegex("");	
		//dtfEffectiveDt.setAutoValidate(true);
		//dtfEffectiveDt.setVisible(false);
		customerAccountRegisterFormPanel.add(dtfEffectiveDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtTranType.setFieldLabel("TranType");
		txtTranType.setName("tranType");
		txtTranType.setFireChangeEventOnSetValue(true);
		//txtTranType.setRegex("");
		//txtTranType.setAutoValidate(true);
		//txtTranType.setAllowBlank(false);
		//txtTranType.setVisible(false);
		customerAccountRegisterFormPanel.add(txtTranType);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrTranAmt.setFieldLabel("TranAmt");
		nbrTranAmt.setName("tranAmt");
		nbrTranAmt.setRegex(GXTValidator.DOUBLE);
		//nbrTranAmt.setPropertyEditorType(Integer.class);
		nbrTranAmt.setFireChangeEventOnSetValue(true);
		//nbrTranAmt.setAllowBlank(false);
		nbrTranAmt.setAutoValidate(true);
		//nbrTranAmt.setVisible(false);
		customerAccountRegisterFormPanel.add(nbrTranAmt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtDescription.setFieldLabel("Description");
		txtDescription.setName("description");
		txtDescription.setFireChangeEventOnSetValue(true);
		//txtDescription.setRegex("");
		//txtDescription.setAutoValidate(true);
		//txtDescription.setAllowBlank(false);
		//txtDescription.setVisible(false);
		customerAccountRegisterFormPanel.add(txtDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		dtfLastUpdate.setFireChangeEventOnSetValue(true);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		customerAccountRegisterFormPanel.add(dtfLastUpdate);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrCustomerId.setFieldLabel("CustomerId");
		nbrCustomerId.setName("customerId");
		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
		//nbrCustomerId.setPropertyEditorType(Integer.class);
		nbrCustomerId.setFireChangeEventOnSetValue(true);
		//nbrCustomerId.setAllowBlank(false);
		nbrCustomerId.setAutoValidate(true);
		//nbrCustomerId.setVisible(false);
		customerAccountRegisterFormPanel.add(nbrCustomerId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrClientId.setFieldLabel("ClientId");
		nbrClientId.setName("clientId");
		nbrClientId.setRegex(GXTValidator.DOUBLE);
		//nbrClientId.setPropertyEditorType(Integer.class);
		nbrClientId.setFireChangeEventOnSetValue(true);
		//nbrClientId.setAllowBlank(false);
		nbrClientId.setAutoValidate(true);
		//nbrClientId.setVisible(false);
		customerAccountRegisterFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrClientAccountRegisterId.setFieldLabel("ClientAccountRegisterId");
		nbrClientAccountRegisterId.setName("clientAccountRegisterId");
		nbrClientAccountRegisterId.setRegex(GXTValidator.DOUBLE);
		//nbrClientAccountRegisterId.setPropertyEditorType(Integer.class);
		nbrClientAccountRegisterId.setFireChangeEventOnSetValue(true);
		//nbrClientAccountRegisterId.setAllowBlank(false);
		nbrClientAccountRegisterId.setAutoValidate(true);
		//nbrClientAccountRegisterId.setVisible(false);
		customerAccountRegisterFormPanel.add(nbrClientAccountRegisterId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "CustomerAccountRegisterViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "CustomerAccountRegisterViewOnDetach");
	}

}

/**
 * @return the customerAccountRegisterFormPanel
 */
public FormPanel getCustomerAccountRegisterFormPanel() {
	return customerAccountRegisterFormPanel;
}


//	/**
//	 * @param customerAccountRegisterTable the customerAccountRegisterTable to set
//	 */
//	public void setCustomerAccountRegisterTable(CustomerAccountRegisterTable customerAccountRegisterTable_) {
//		this.customerAccountRegisterTable = customerAccountRegisterTable_;
//	}
//	/**
//	 * @return the customerAccountRegisterTable
//	 */
//	public CustomerAccountRegisterTable getCustomerAccountRegisterTable() {
//		return customerAccountRegisterTable;
//	}



/**
 * @return the store
 */
public ListStore<CustomerAccountRegisterBean> getStore() {
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
/**
 * @param cboCustomerId the cboCustomerId to set
 */
public void setCboCustomerId(AlternateComboBox<CustomerBean> cboCustomerId) {
	this.cboCustomerId = cboCustomerId;
}
/**
 * @return the cboCustomerId
 */
public AlternateComboBox<CustomerBean> getCboCustomerId() {
	return cboCustomerId;
}

/**
 * @param notifier the notifier to set
 */
public void setNotifier(AppNotifyObject notifier) {
	this.notifier = notifier;
}

/**
 * @return the notifier
 */
public AppNotifyObject getNotifier() {
	return notifier;
}

/**
 * @param customerAccountRegisterTable the customerAccountRegisterTable to set
 */
public void setCustomerAccountRegisterTable(CustomerAccountRegisterTable customerAccountRegisterTable) {
	this.customerAccountRegisterTable = customerAccountRegisterTable;
}

/**
 * @return the customerAccountRegisterTable
 */
public CustomerAccountRegisterTable getCustomerAccountRegisterTable() {
	return customerAccountRegisterTable;
}

}

