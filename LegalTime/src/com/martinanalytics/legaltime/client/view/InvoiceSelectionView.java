package com.martinanalytics.legaltime.client.view;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.view.table.InvoiceTable;


public class InvoiceSelectionView {
	private InvoiceSelectionComposite invoiceSelectionComposite;
	private InvoiceTable invoiceTable;
	Button cmdReprintInvoices = new Button("Reprint Invoice");
	Button cmdReverseInvoices = new Button("Reverse Invoice");
	private AppNotifyObject notifier = new AppNotifyObject();
	
	public InvoiceSelectionView(){
		
		invoiceTable = new InvoiceTable();
		
		invoiceSelectionComposite = new InvoiceSelectionComposite();
		establishListeners();
	}
	
	private void establishListeners() {
		cmdReprintInvoices.addSelectionListener(new SelectionListener<ButtonEvent>() {  		  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  
		    	 notifier.notifyAppEvent(this, "ReprintInvoice") ;
		      }
		});
		cmdReverseInvoices.addSelectionListener(new SelectionListener<ButtonEvent>() {  		  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	    	 notifier.notifyAppEvent(this, "UnwindInvoice") ;
	      }
	});
		
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

	class InvoiceSelectionComposite extends Composite{
		public InvoiceSelectionComposite(){
			ContentPanel cp = new ContentPanel();
			VerticalPanel vp = new VerticalPanel();
			ToolBar toolbar = new ToolBar();
			
			cmdReprintInvoices.setBorders(true);
			toolbar.add( cmdReprintInvoices);

			cmdReverseInvoices.setBorders(true);
			toolbar.add( cmdReverseInvoices);
			cp.setTopComponent(toolbar);
			cp.setWidth(700);
			cp.setHeight(500);
			cp.setHeading("Invoices");
			cp.add(getInvoiceTable());
			vp.add(cp);
			initWidget(vp);
		}
	}
	

}
