package com.martinanalytics.legaltime.client.view;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.view.table.ExpenseRegisterTable;
import com.martinanalytics.legaltime.client.view.table.LaborRegisterTable;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;

public class InvoiceManagerView {
	private AlternateComboBox<CustomerBean> cboCustomerId = new AlternateComboBox<CustomerBean>("Customer","customerId","customerId","displayName");
	private InvoiceManagerViewComposite invoiceManagerViewComposite;
	final private LaborRegisterTable laborRegisterTable = new LaborRegisterTable();
	private final ExpenseRegisterTable expenseRegisterTable = new ExpenseRegisterTable();
	private final AppNotifyObject notifier = new AppNotifyObject();
	private Button cmdGenerateInvoice;
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


	/**
	 * @return the expenseRegisterTable
	 */
	public ExpenseRegisterTable getExpenseRegisterTable() {
		return expenseRegisterTable;
	}


	/**
	 * @param cmdGenerateInvoice the cmdGenerateInvoice to set
	 */
	public void setCmdGenerateInvoice(Button cmdGenerateInvoice) {
		this.cmdGenerateInvoice = cmdGenerateInvoice;
	}
	/**
	 * @return the cmdGenerateInvoice
	 */
	public Button getCmdGenerateInvoice() {
		return cmdGenerateInvoice;
	}


	class InvoiceManagerViewComposite extends Composite{
		public InvoiceManagerViewComposite(){
			FormPanel cp = new FormPanel();
			cp.setHeading("Invoice Manager");
			cp.setWidth(800);
			ToolBar tbUtility = new ToolBar();
			cmdGenerateInvoice = new Button("Generate Invoice", new SelectionListener<ButtonEvent>() {  
			  	  
			      @Override  
			      public void componentSelected(ButtonEvent ce) {  
			        notifier.notifyAppEvent(this, "GenerateInvoice") ;
			      }  
			    });
			getCmdGenerateInvoice().setBorders(true);
			tbUtility.add(getCmdGenerateInvoice());
		    Button cmdAdd = new Button("Add Expense");  
		    cmdAdd.setBorders(true);
		    cmdAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {  
		  
		      @Override  
		      public void componentSelected(ButtonEvent ce) {  
		        
		        ExpenseRegisterBean expenseRegisterBean  = new ExpenseRegisterBean();
				expenseRegisterBean.setInvoiceable(true);
		 		expenseRegisterBean.setInvoiceId(0);
		 		expenseRegisterBean.setAmount(0D);
		 		expenseRegisterBean.setDescription("!Enter Description Here");
		 		expenseRegisterBean.setLastUpdate(new java.util.Date());
		 		expenseRegisterBean.setCustomerId(0);
		 		expenseRegisterBean.setClientId(0);
		 		expenseRegisterBean.setExpenseRegisterId(0);
		 		expenseRegisterBean.setExpenseDt(new java.util.Date());
		  	        expenseRegisterTable.getGrid().stopEditing();  
				//store.sort("", SortDir.ASC);
		  	      expenseRegisterTable.getStore().insert(expenseRegisterBean, 0);
		  	      expenseRegisterTable.getGrid().startEditing(0, 0);  
			      }  
		  
		    });  
		    tbUtility.add(cmdAdd);
			
			Button cmdCancelChanges = new Button("Cancel Changes", new SelectionListener<ButtonEvent>() {  
				  
			      @Override  
			      public void componentSelected(ButtonEvent ce) {  
			        laborRegisterTable.getStore().rejectChanges();  
			        expenseRegisterTable.getStore().rejectChanges();
			      }  
			    });
			cmdCancelChanges.setBorders(true);
			tbUtility.add(cmdCancelChanges);
			
//-------------------------------------
			Button cmdViewInvoices = new Button("View Invoices", new SelectionListener<ButtonEvent>() {  
				  
			    @Override  
			    public void componentSelected(ButtonEvent ce) {  
			      notifier.notifyAppEvent(this, "ShowInvoiceList");
			    }  
			  });
			cmdViewInvoices.setBorders(true);
			tbUtility.add(cmdViewInvoices);

//-------------------------------------
			//tbUtility.setBorders(true);
			cp.setTopComponent(tbUtility);
			cboCustomerId.setFieldLabel("Customer");
			cp.add(getCboCustomerId());
			cp.add(laborRegisterTable);
			cp.add(getExpenseRegisterTable());
			VerticalPanel vp = new VerticalPanel();
			vp.add(cp);
			initWidget(vp);
		}
		
	}
	public Integer getSelectedCustomerId(){
		return ((CustomerBean)cboCustomerId.getSelection().get(0)).getCustomerId();
	}

}
