package com.martinanalytics.legaltime.client.view;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.view.table.InvoiceTable;


public class InvoiceSelectionView {
	private InvoiceSelectionComposite invoiceSelectionComposite;
	private InvoiceTable invoiceTable;
	
	public InvoiceSelectionView(){
		
		invoiceTable = new InvoiceTable();
		
		invoiceSelectionComposite = new InvoiceSelectionComposite();
	}
	
	/**
	 * @param invoiceSelectionComposite the invoiceSelectionComposite to set
	 */
	public void setInvoiceSelectionComposite(InvoiceSelectionComposite invoiceSelectionComposite) {
		this.invoiceSelectionComposite = invoiceSelectionComposite;
	}

	/**
	 * @return the invoiceSelectionComposite
	 */
	public InvoiceSelectionComposite getInvoiceSelectionComposite() {
		return invoiceSelectionComposite;
	}

	/**
	 * @param invoiceTable the invoiceTable to set
	 */
	public void setInvoiceTable(InvoiceTable invoiceTable) {
		this.invoiceTable = invoiceTable;
	}

	/**
	 * @return the invoiceTable
	 */
	public InvoiceTable getInvoiceTable() {
		return invoiceTable;
	}

	class InvoiceSelectionComposite extends Composite{
		public InvoiceSelectionComposite(){
			ContentPanel cp = new ContentPanel();
			VerticalPanel vp = new VerticalPanel();
			cp.setWidth(700);
			cp.setHeight(500);
			cp.setHeading("Invoices");
			cp.add(getInvoiceTable());
			vp.add(cp);
			initWidget(vp);
		}
	}
	

}
