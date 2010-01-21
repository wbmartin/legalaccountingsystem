
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
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
import com.extjs.gxt.ui.client.widget.Dialog;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;
import com.martinanalytics.legaltime.client.widget.AlternateComboBoxBinding;
import com.martinanalytics.legaltime.client.widget.GXTValidator;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.view.table.CustomerAccountRegisterTable;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.SimpleComboBoxFieldBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

public class PaymentLogView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel paymentLogFormPanel ;
	private ContentPanel cp = new ContentPanel();  
	private FormBinding formBindings; 
	private final ListStore<PaymentLogBean> store = new ListStore<PaymentLogBean>();


	private NumberField nbrCustomerAccountRegisterId = new NumberField();
 	private NumberField nbrInvoiceId = new NumberField();
 	private NumberField nbrAmount = new NumberField();
 	private TextField<String> txtDescription = new TextField<String>();
 	private DateField dtfEffectiveDt = new DateField();
 	private DateField dtfLastUpdate = new DateField();

 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrPaymentLogId = new NumberField();
  	private PaymentLogComposite paymentLogComposite;
  	private Dialog postPaymentDialog = new Dialog();
	private AlternateComboBox<CustomerBean> cboCustomerId = new AlternateComboBox<CustomerBean>("Customer","customerId","customerId","displayName");

  	//private PaymentLogTable paymentLogTable = new PaymentLogTable();
	public PaymentLogView(){
		userProfile = UserProfile.getInstance();
		 paymentLogFormPanel = new FormPanel();
		paymentLogComposite =new PaymentLogComposite();
		postPaymentDialog.setModal(true);
		postPaymentDialog.setWidth(500);
		postPaymentDialog.setButtonAlign(HorizontalAlignment.CENTER);
		postPaymentDialog.setButtons(Dialog.YESNOCANCEL);
		postPaymentDialog.getButtonById(Dialog.YES).setText("Post");
		postPaymentDialog.getButtonById(Dialog.NO).setText("Post and Another");
		establishListeners();
		postPaymentDialog.setClosable(false);
		postPaymentDialog.setHideOnButtonClick(false);
		postPaymentDialog.add(paymentLogComposite);
		
		formBindings = new FormBinding(paymentLogFormPanel, true);
		//formBindings.removeFieldBinding((formBindings.getBinding(cboCustomerId)));
		AlternateComboBoxBinding customerBinding = new AlternateComboBoxBinding (formBindings, cboCustomerId);
		//formBindings.setStore((Store<PaymentLogBean>) store); 
		//store.add( new PaymentLogBean());
		PaymentLogBean paymentLogBean = new PaymentLogBean();
		paymentLogBean.setDescription("Payment Received");
		paymentLogBean.setEffectiveDt(new java.util.Date());
		formBindings.bind(paymentLogBean);
		
		
	}
	
	public void establishListeners(){
		postPaymentDialog.getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() {  
			  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  
		    	  postPaymentDialog.hide();
		    	  notifyAppEvent(this, "PostPaymentDialogClosing");
		      }
		      });
		postPaymentDialog.getButtonById(Dialog.YES).addSelectionListener(new SelectionListener<ButtonEvent>() {  
			  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  
		    	  if (paymentLogFormPanel.isValid()){
		    		  notifyAppEvent(this, "AddPayment");
		    		  postPaymentDialog.hide();
		    		  notifyAppEvent(this, "PostPaymentDialogClosing");
		    	  }
		      }
		      });
		postPaymentDialog.getButtonById(Dialog.NO).addSelectionListener(new SelectionListener<ButtonEvent>() {  
			  
		      @Override  
		      public void componentSelected(ButtonEvent ce) { 
		    	  if (paymentLogFormPanel.isValid()){
		    	    notifyAppEvent(this, "AddPayment");
		    		postPaymentDialog.getButtonById(Dialog.YES).setEnabled(false);
		    		postPaymentDialog.getButtonById(Dialog.NO).setEnabled(false);
		    	  }
		    	  
		      }
		      });
		
		
		
		
		
		
	

	}
	
	public void clearForm(){
		nbrAmount.setValue(0);
		txtDescription.setValue("Payment Received");
	}
	/**
	 * @return the nbrCustomerAccountRegisterId
	 */
	public NumberField getNbrCustomerAccountRegisterId() {
		return nbrCustomerAccountRegisterId;
	}

	/**
	 * @return the nbrInvoiceId
	 */
	public NumberField getNbrInvoiceId() {
		return nbrInvoiceId;
	}

	/**
	 * @return the nbrAmount
	 */
	public NumberField getNbrAmount() {
		return nbrAmount;
	}

	/**
	 * @return the txtDescription
	 */
	public TextField<String> getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the dtfEffectiveDt
	 */
	public DateField getDtfEffectiveDt() {
		return dtfEffectiveDt;
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
	 * @return the nbrPaymentLogId
	 */
	public NumberField getNbrPaymentLogId() {
		return nbrPaymentLogId;
	}

	/**
	 * @return the paymentLogComposite
	 */
	public PaymentLogComposite getPaymentLogComposite() {
		return paymentLogComposite;
	}


class PaymentLogComposite extends Composite{
	public PaymentLogComposite(){
		
		
		paymentLogFormPanel.setFrame(true);
		paymentLogFormPanel.setHeading("Log A Payment");
		paymentLogFormPanel.setWidth(600);
		createControls();
		
		VerticalPanel vp = new VerticalPanel();
		  vp.add(paymentLogFormPanel);
	      	initWidget(vp);
		

	}
	public void createControls(){

////---------------------------------------------------------------
//		nbrCustomerAccountRegisterId.setFieldLabel("CustomerAccountRegisterId");
//		nbrCustomerAccountRegisterId.setName("customerAccountRegisterId");
//		nbrCustomerAccountRegisterId.setRegex(GXTValidator.DOUBLE);
//		//nbrCustomerAccountRegisterId.setPropertyEditorType(Integer.class);
//		nbrCustomerAccountRegisterId.setFireChangeEventOnSetValue(true);
//		//nbrCustomerAccountRegisterId.setAllowBlank(false);
//		nbrCustomerAccountRegisterId.setAutoValidate(true);
//		//nbrCustomerAccountRegisterId.setVisible(false);
//		paymentLogFormPanel.add(nbrCustomerAccountRegisterId);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrInvoiceId.setFieldLabel("InvoiceId");
//		nbrInvoiceId.setName("invoiceId");
//		nbrInvoiceId.setRegex(GXTValidator.DOUBLE);
//		//nbrInvoiceId.setPropertyEditorType(Integer.class);
//		nbrInvoiceId.setFireChangeEventOnSetValue(true);
//		//nbrInvoiceId.setAllowBlank(false);
//		nbrInvoiceId.setAutoValidate(true);
//		//nbrInvoiceId.setVisible(false);
//		paymentLogFormPanel.add(nbrInvoiceId);
////---------------------------------------------------------------
		cboCustomerId.setFieldLabel("Customer");
		paymentLogFormPanel.add(cboCustomerId);
		cboCustomerId.setAllowBlank(false);

//---------------------------------------------------------------
		nbrAmount.setFieldLabel("Amount");
		nbrAmount.setName("amount");
		nbrAmount.setRegex(GXTValidator.DOUBLE);
		nbrAmount.setAllowDecimals(true);
		
		nbrAmount.setAllowBlank(false);
		nbrAmount.addStyleName("RIGHT");
		//nbrAmount.getPropertyEditor().setFormat(NumberFormat.getFormat("$#,##0.00"));
		
		//nbrAmount.setPropertyEditorType(Integer.class);
		nbrAmount.setFireChangeEventOnSetValue(true);
		//nbrAmount.setAllowBlank(false);
		nbrAmount.setAutoValidate(false);
		//nbrAmount.setVisible(false);
		paymentLogFormPanel.add(nbrAmount);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtDescription.setFieldLabel("Description");
		txtDescription.setName("description");
		txtDescription.setFireChangeEventOnSetValue(true);
		//txtDescription.setRegex("");
		txtDescription.setAutoValidate(false);
		//txtDescription.setAllowBlank(false);
		//txtDescription.setVisible(false);
		paymentLogFormPanel.add(txtDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfEffectiveDt.setFieldLabel("Date");
		dtfEffectiveDt.setName("effectiveDt");
		//dtfEffectiveDt.setAllowBlank(false);
		dtfEffectiveDt.setFireChangeEventOnSetValue(true);
		
		//dtfEffectiveDt.setRegex("");	
		dtfEffectiveDt.setAutoValidate(false);
		//dtfEffectiveDt.setVisible(false);
		paymentLogFormPanel.add(dtfEffectiveDt);
//---------------------------------------------------------------



////---------------------------------------------------------------
//		dtfLastUpdate.setFieldLabel("LastUpdate");
//		dtfLastUpdate.setName("lastUpdate");
//		//dtfLastUpdate.setAllowBlank(false);
//		dtfLastUpdate.setFireChangeEventOnSetValue(true);
//		//dtfLastUpdate.setRegex("");	
//		//dtfLastUpdate.setAutoValidate(true);
//		//dtfLastUpdate.setVisible(false);
//		paymentLogFormPanel.add(dtfLastUpdate);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrCustomerId.setFieldLabel("CustomerId");
//		nbrCustomerId.setName("customerId");
//		nbrCustomerId.setRegex(GXTValidator.DOUBLE);
//		//nbrCustomerId.setPropertyEditorType(Integer.class);
//		nbrCustomerId.setFireChangeEventOnSetValue(true);
//		//nbrCustomerId.setAllowBlank(false);
//		nbrCustomerId.setAutoValidate(true);
//		//nbrCustomerId.setVisible(false);
//		paymentLogFormPanel.add(nbrCustomerId);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrClientId.setFieldLabel("ClientId");
//		nbrClientId.setName("clientId");
//		nbrClientId.setRegex(GXTValidator.DOUBLE);
//		//nbrClientId.setPropertyEditorType(Integer.class);
//		nbrClientId.setFireChangeEventOnSetValue(true);
//		//nbrClientId.setAllowBlank(false);
//		nbrClientId.setAutoValidate(true);
//		//nbrClientId.setVisible(false);
//		paymentLogFormPanel.add(nbrClientId);
////---------------------------------------------------------------



////---------------------------------------------------------------
//		nbrPaymentLogId.setFieldLabel("PaymentLogId");
//		nbrPaymentLogId.setName("paymentLogId");
//		nbrPaymentLogId.setRegex(GXTValidator.DOUBLE);
//		//nbrPaymentLogId.setPropertyEditorType(Integer.class);
//		nbrPaymentLogId.setFireChangeEventOnSetValue(true);
//		//nbrPaymentLogId.setAllowBlank(false);
//		nbrPaymentLogId.setAutoValidate(true);
//		//nbrPaymentLogId.setVisible(false);
//		paymentLogFormPanel.add(nbrPaymentLogId);
////---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "PaymentLogViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "PaymentLogViewOnDetach");
	}

}

/**
 * @return the paymentLogFormPanel
 */
public FormPanel getPaymentLogFormPanel() {
	return paymentLogFormPanel;
}


//	/**
//	 * @param paymentLogTable the paymentLogTable to set
//	 */
//	public void setPaymentLogTable(PaymentLogTable paymentLogTable_) {
//		this.paymentLogTable = paymentLogTable_;
//	}
//	/**
//	 * @return the paymentLogTable
//	 */
//	public PaymentLogTable getPaymentLogTable() {
//		return paymentLogTable;
//	}



/**
 * @return the store
 */
public ListStore<PaymentLogBean> getStore() {
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
 * @param postPaymentDialog the postPaymentDialog to set
 */
public void setPostPaymentDialog(Dialog postPaymentDialog) {
	this.postPaymentDialog = postPaymentDialog;
}
/**
 * @return the postPaymentDialog
 */
public Dialog getPostPaymentDialog() {
	return postPaymentDialog;
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

}

