
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
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

public class ExpenseRegisterView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel expenseRegisterFormPanel ;
	private ContentPanel cp = new ContentPanel();  

	private TextField<Boolean> txtInvoiceable = new TextField<Boolean>();
 	private NumberField nbrInvoiceId = new NumberField();
 	private NumberField nbrAmount = new NumberField();
 	private TextField<String> txtDescription = new TextField<String>();
 	private DateField dtfLastUpdate = new DateField();
 	private NumberField nbrCustomerId = new NumberField();
 	private NumberField nbrClientId = new NumberField();
 	private NumberField nbrExpenseRegisterId = new NumberField();
  	private ExpenseRegisterComposite expenseRegisterComposite;
	private FormBinding formBindings;
	private final ListStore<ExpenseRegisterBean> store = new ListStore<ExpenseRegisterBean>();
	public ExpenseRegisterView(){
		userProfile = UserProfile.getInstance();
		expenseRegisterComposite =new ExpenseRegisterComposite();
		 expenseRegisterFormPanel = new FormPanel();
	}
	/**
	 * @return the txtInvoiceable
	 */
	public TextField<Boolean> getTxtInvoiceable() {
		return txtInvoiceable;
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
	 * @return the nbrExpenseRegisterId
	 */
	public NumberField getNbrExpenseRegisterId() {
		return nbrExpenseRegisterId;
	}

	/**
	 * @return the expenseRegisterComposite
	 */
	public ExpenseRegisterComposite getExpenseRegisterComposite() {
		return expenseRegisterComposite;
	}


class ExpenseRegisterComposite extends Composite{
	public ExpenseRegisterComposite(){
		
		
		expenseRegisterFormPanel.setFrame(true);
		expenseRegisterFormPanel.setHeading("expense_register Information");
		expenseRegisterFormPanel.setWidth(600);
		createControls();
		
		VerticalPanel vp = new VerticalPanel();
		  vp.add(expenseRegisterFormPanel);
	      	initWidget(vp);
		

	}
	public void createControls(){

//---------------------------------------------------------------
		txtInvoiceable.setFieldLabel("Invoiceable");
		txtInvoiceable.setName("invoiceable");
		txtInvoiceable.setFireChangeEventOnSetValue(true);
		//txtInvoiceable.setRegex("");
		//txtInvoiceable.setAutoValidate(true);
		//txtInvoiceable.setAllowBlank(false);
		//txtInvoiceable.setVisible(false);
		expenseRegisterFormPanel.add(txtInvoiceable);
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
		expenseRegisterFormPanel.add(nbrInvoiceId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrAmount.setFieldLabel("Amount");
		nbrAmount.setName("amount");
		nbrAmount.setRegex(GXTValidator.DOUBLE);
		//nbrAmount.setPropertyEditorType(Integer.class);
		nbrAmount.setFireChangeEventOnSetValue(true);
		//nbrAmount.setAllowBlank(false);
		nbrAmount.setAutoValidate(true);
		//nbrAmount.setVisible(false);
		expenseRegisterFormPanel.add(nbrAmount);
//---------------------------------------------------------------



//---------------------------------------------------------------
		txtDescription.setFieldLabel("Description");
		txtDescription.setName("description");
		txtDescription.setFireChangeEventOnSetValue(true);
		//txtDescription.setRegex("");
		//txtDescription.setAutoValidate(true);
		//txtDescription.setAllowBlank(false);
		//txtDescription.setVisible(false);
		expenseRegisterFormPanel.add(txtDescription);
//---------------------------------------------------------------



//---------------------------------------------------------------
		dtfLastUpdate.setFieldLabel("LastUpdate");
		dtfLastUpdate.setName("lastUpdate");
		//dtfLastUpdate.setAllowBlank(false);
		dtfLastUpdate.setFireChangeEventOnSetValue(true);
		//dtfLastUpdate.setRegex("");	
		//dtfLastUpdate.setAutoValidate(true);
		//dtfLastUpdate.setVisible(false);
		expenseRegisterFormPanel.add(dtfLastUpdate);
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
		expenseRegisterFormPanel.add(nbrCustomerId);
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
		expenseRegisterFormPanel.add(nbrClientId);
//---------------------------------------------------------------



//---------------------------------------------------------------
		nbrExpenseRegisterId.setFieldLabel("ExpenseRegisterId");
		nbrExpenseRegisterId.setName("expenseRegisterId");
		nbrExpenseRegisterId.setRegex(GXTValidator.DOUBLE);
		//nbrExpenseRegisterId.setPropertyEditorType(Integer.class);
		nbrExpenseRegisterId.setFireChangeEventOnSetValue(true);
		//nbrExpenseRegisterId.setAllowBlank(false);
		nbrExpenseRegisterId.setAutoValidate(true);
		//nbrExpenseRegisterId.setVisible(false);
		expenseRegisterFormPanel.add(nbrExpenseRegisterId);
//---------------------------------------------------------------


	}
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "ExpenseRegisterViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "ExpenseRegisterViewOnDetach");
	}

}

/**
 * @return the expenseRegisterFormPanel
 */
public FormPanel getExpenseRegisterFormPanel() {
	return expenseRegisterFormPanel;
}


//	/**
//	 * @param expenseRegisterTable the expenseRegisterTable to set
//	 */
//	public void setExpenseRegisterTable(ExpenseRegisterTable expenseRegisterTable_) {
//		this.expenseRegisterTable = expenseRegisterTable_;
//	}
//	/**
//	 * @return the expenseRegisterTable
//	 */
//	public ExpenseRegisterTable getExpenseRegisterTable() {
//		return expenseRegisterTable;
//	}



/**
 * @return the store
 */
public ListStore<ExpenseRegisterBean> getStore() {
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

