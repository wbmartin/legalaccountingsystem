
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.martinanalytics.legaltime.client.widget.GXTValidator;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;

public class InvoiceView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel invoiceFormPanel ;
	private ContentPanel cp = new ContentPanel();  
	private FormBinding formBindings; 
	private final ListStore<InvoiceBean> store = new ListStore<InvoiceBean>();

	private NumberField nbrPrevBalanceDue = new NumberField();
 	private NumberField nbrInvoiceTotalAmt = new NumberField();
 	private DateField dtfGeneratedDt = new DateField();
 	private DateField dtfInvoiceDt = new DateField();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrInvoiceId = new NumberField();
  	private InvoiceComposite invoiceComposite;
	public InvoiceView(){
		userProfile = UserProfile.getInstance();
		invoiceComposite =new InvoiceComposite();
		 invoiceFormPanel = new FormPanel();
	}
	/**
	 * @return the nbrPrevBalanceDue
	 */
	public NumberField getNbrPrevBalanceDue() {
		return nbrPrevBalanceDue;
	}

	/**
	 * @return the nbrInvoiceTotalAmt
	 */
	public NumberField getNbrInvoiceTotalAmt() {
		return nbrInvoiceTotalAmt;
	}

	/**
	 * @return the dtfGeneratedDt
	 */
	public DateField getDtfGeneratedDt() {
		return dtfGeneratedDt;
	}

	/**
	 * @return the dtfInvoiceDt
	 */
	public DateField getDtfInvoiceDt() {
		return dtfInvoiceDt;
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
	 * @return the nbrInvoiceId
	 */
	public NumberField getNbrInvoiceId() {
		return nbrInvoiceId;
	}

	/**
	 * @return the invoiceComposite
	 */
	public InvoiceComposite getInvoiceComposite() {
		return invoiceComposite;
	}


class InvoiceComposite extends Composite{
	public InvoiceComposite(){
		
		
		invoiceFormPanel.setFrame(true);
		invoiceFormPanel.setHeading("invoice Information");
		invoiceFormPanel.setWidth(600);
		createControls();
		
		VerticalPanel vp = new VerticalPanel();
		  vp.add(invoiceFormPanel);
	      	initWidget(vp);
		

	}
	public void createControls(){

//---------------------------------------------------------------
		nbrPrevBalanceDue.setFieldLabel("PrevBalanceDue");
		nbrPrevBalanceDue.setName("prevBalanceDue");
		nbrPrevBalanceDue.setRegex(GXTValidator.DOUBLE);
		//nbrPrevBalanceDue.setPropertyEditorType(Integer.class);
		nbrPrevBalanceDue.setFireChangeEventOnSetValue(true);
		//nbrPrevBalanceDue.setAllowBlank(false);
		nbrPrevBalanceDue.setAutoValidate(true);
		//nbrPrevBalanceDue.setVisible(false);
		invoiceFormPanel.add(nbrPrevBalanceDue);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrInvoiceTotalAmt.setFieldLabel("InvoiceTotalAmt");
		nbrInvoiceTotalAmt.setName("invoiceTotalAmt");
		nbrInvoiceTotalAmt.setRegex(GXTValidator.DOUBLE);
		//nbrInvoiceTotalAmt.setPropertyEditorType(Integer.class);
		nbrInvoiceTotalAmt.setFireChangeEventOnSetValue(true);
		//nbrInvoiceTotalAmt.setAllowBlank(false);
		nbrInvoiceTotalAmt.setAutoValidate(true);
		//nbrInvoiceTotalAmt.setVisible(false);
		invoiceFormPanel.add(nbrInvoiceTotalAmt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfGeneratedDt.setFieldLabel("GeneratedDt");
		dtfGeneratedDt.setName("generatedDt");
		//dtfGeneratedDt.setAllowBlank(false);
		dtfGeneratedDt.setFireChangeEventOnSetValue(true);
		//dtfGeneratedDt.setRegex("");	
		//dtfGeneratedDt.setAutoValidate(true);
		//dtfGeneratedDt.setVisible(false);
		invoiceFormPanel.add(dtfGeneratedDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfInvoiceDt.setFieldLabel("InvoiceDt");
		dtfInvoiceDt.setName("invoiceDt");
		//dtfInvoiceDt.setAllowBlank(false);
		dtfInvoiceDt.setFireChangeEventOnSetValue(true);
		//dtfInvoiceDt.setRegex("");	
		//dtfInvoiceDt.setAutoValidate(true);
		//dtfInvoiceDt.setVisible(false);
		invoiceFormPanel.add(dtfInvoiceDt);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		dtfLastUpdate.setFireChangeEventOnSetValue(true);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		invoiceFormPanel.add(dtfLastUpdate);
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
		invoiceFormPanel.add(nbrCustomerId);
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
		invoiceFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrInvoiceId.setFieldLabel("InvoiceId");
		nbrInvoiceId.setName("invoiceId");
		nbrInvoiceId.setRegex(GXTValidator.DOUBLE);
		//nbrInvoiceId.setPropertyEditorType(Integer.class);
		nbrInvoiceId.setFireChangeEventOnSetValue(true);
		//nbrInvoiceId.setAllowBlank(false);
		nbrInvoiceId.setAutoValidate(true);
		//nbrInvoiceId.setVisible(false);
		invoiceFormPanel.add(nbrInvoiceId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "InvoiceViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "InvoiceViewOnDetach");
	}

}

/**
 * @return the invoiceFormPanel
 */
public FormPanel getInvoiceFormPanel() {
	return invoiceFormPanel;
}


//	/**
//	 * @param invoiceTable the invoiceTable to set
//	 */
//	public void setInvoiceTable(InvoiceTable invoiceTable_) {
//		this.invoiceTable = invoiceTable_;
//	}
//	/**
//	 * @return the invoiceTable
//	 */
//	public InvoiceTable getInvoiceTable() {
//		return invoiceTable;
//	}



/**
 * @return the store
 */
public ListStore<InvoiceBean> getStore() {
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

