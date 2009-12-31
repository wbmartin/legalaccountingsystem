package com.martinanalytics.legaltime.client.view;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.ui.Composite;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.view.table.LaborRegisterTable;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;

public class InvoiceManagerView {
	private AlternateComboBox<CustomerBean> cboCustomerId = new AlternateComboBox<CustomerBean>("Customer","customerId","customerId","displayName");
	private InvoiceManagerViewComposite invoiceManagerViewComposite;
	final private LaborRegisterTable laborRegisterTable = new LaborRegisterTable();
	private final AppNotifyObject notifier = new AppNotifyObject();
	public InvoiceManagerView(){
		invoiceManagerViewComposite = new InvoiceManagerViewComposite();
		setListeners();
	}
	@SuppressWarnings("unchecked")
	private void setListeners(){
		cboCustomerId.addSelectionChangedListener(new SelectionChangedListener<CustomerBean>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<CustomerBean> se_) {
				notifier.notifyAppEvent(this, "InvoiceManagerCustomerChanged", se_.getSelectedItem().getCustomerId());
				
			}
		});
	}
	
	
	 /**
	 * @param invoiceManagerViewComposite the invoiceManagerViewComposite to set
	 */
	public void setInvoiceManagerViewComposite(
			InvoiceManagerViewComposite invoiceManagerViewComposite) {
		this.invoiceManagerViewComposite = invoiceManagerViewComposite;
	}


	/**
	 * @return the invoiceManagerViewComposite
	 */
	public InvoiceManagerViewComposite getInvoiceManagerViewComposite() {
		return invoiceManagerViewComposite;
	}


	/**
	 * @return the laborRegisterTable
	 */
	public LaborRegisterTable getLaborRegisterTable() {
		return laborRegisterTable;
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
	 * @return the notifier
	 */
	public AppNotifyObject getNotifier() {
		return notifier;
	}


	class InvoiceManagerViewComposite extends Composite{
		public InvoiceManagerViewComposite(){
			FormPanel cp = new FormPanel();
			cp.setHeading("Invoice Manager");
			cboCustomerId.setFieldLabel("Customer");
			cp.add(getCboCustomerId());
			cp.add(laborRegisterTable);
			
			initWidget(cp);
		}
		
	}

}
