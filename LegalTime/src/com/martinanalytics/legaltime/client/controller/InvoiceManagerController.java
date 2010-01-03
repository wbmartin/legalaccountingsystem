package com.martinanalytics.legaltime.client.controller;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.ExpenseRegisterService;
import com.martinanalytics.legaltime.client.model.ExpenseRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.InvoiceService;
import com.martinanalytics.legaltime.client.model.InvoiceServiceAsync;
import com.martinanalytics.legaltime.client.model.LaborRegisterService;
import com.martinanalytics.legaltime.client.model.LaborRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.VwInvoiceDisplayService;
import com.martinanalytics.legaltime.client.model.VwInvoiceDisplayServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
import com.martinanalytics.legaltime.client.view.ExpenseRegisterView;
import com.martinanalytics.legaltime.client.view.InvoiceManagerView;
import com.martinanalytics.legaltime.client.view.InvoiceSelectionView;
import com.martinanalytics.legaltime.client.view.InvoiceView;
import com.martinanalytics.legaltime.client.view.LaborRegisterView;
import com.martinanalytics.legaltime.client.view.table.LaborRegisterTable;

public class InvoiceManagerController implements AppEventListener {
	private static InvoiceManagerController instance=null;
	private InvoiceManagerView invoiceManagerView;
	MasterController masterController;
	  private final LaborRegisterServiceAsync laborRegisterService = 
			GWT.create(LaborRegisterService.class); 
	  private final ExpenseRegisterServiceAsync expenseRegisterService = 
			GWT.create(ExpenseRegisterService.class); 
	  private final InvoiceServiceAsync invoiceService = 
			GWT.create(InvoiceService.class); 	
	  private final VwInvoiceDisplayServiceAsync vwInvoiceDisplayService = 
			GWT.create(VwInvoiceDisplayService.class); 
	  private boolean expensesSaved = false;
	  private boolean laborSaved = false;
	  private InvoiceSelectionView invoiceSelectionView;
	  private Dialog invoiceSelectionDialog;
	  
	protected InvoiceManagerController (MasterController masterController_){
		masterController = masterController_;
		invoiceManagerView = new InvoiceManagerView();
		invoiceManagerView.getNotifier().addAppEventListener(this);
		invoiceManagerView.getLaborRegisterTable().getNotifier().addAppEventListener(this);
		invoiceSelectionView = new InvoiceSelectionView();
		
		invoiceSelectionDialog = new Dialog();
		createInvoiceSelectionDialog();
	}
	
	public static InvoiceManagerController getInstance(MasterController masterController_){
		if(instance == null){
			instance = new InvoiceManagerController(masterController_);
		}
		return instance;
	}

//	/**
//	 * @param invoiceManagerView the invoiceManagerView to set
//	 */
//	public void setInvoiceManagerView(InvoiceManagerView invoiceManagerView) {
//		this.invoiceManagerView = invoiceManagerView;
//	}

	/**
	 * @return the invoiceManagerView
	 */
	public InvoiceManagerView getInvoiceManagerView() {
		return invoiceManagerView;
	}

	/**
	 * @param invoiceManagerView the invoiceManagerView to set
	 */
	public void setInvoiceManagerView(InvoiceManagerView invoiceManagerView) {
		this.invoiceManagerView = invoiceManagerView;
	}

	@Override
	public void onAppEventNotify(AppEvent e_) {
		if(e_.getName().equals("InvoiceManagerCustomerChanged")){
			saveLaborRegisterBeanBatch(invoiceManagerView.getLaborRegisterTable().getList());
			selectLaborRegisterBeans("where customer_id = "+ e_.getPayLoad() + " and invoice_id is null", "order by activity_date");
			selectExpenseRegisterBeans("where customer_id = "+ e_.getPayLoad()+ " and invoice_id is null","order by expense_dt");
		}else if(e_.getName().equals("GenerateInvoice")){
			invoiceManagerView.getCmdGenerateInvoice().setEnabled(false);
			expensesSaved = false;
			laborSaved = false;
			saveLaborRegisterBeanBatch(invoiceManagerView.getLaborRegisterTable().getList());
			saveExpenseRegisterBeanBatch(invoiceManagerView.getExpenseRegisterTable().getList());
			
		}else if(e_.getName().equals("LaborRegisterTableOnDetach")){
			saveLaborRegisterBeanBatch(invoiceManagerView.getLaborRegisterTable().getList());
		}else if(e_.getName().equals("ShowInvoiceList")){
			selectVwInvoiceDisplayBeans("","");
			invoiceSelectionDialog.show();
		}
		
	}
	
	
	
	
	/**
	 * Provides a standard template to retrieve beans from the server.  
	 * The results are handled through the onSuccess method in the AsynchCallback.
	 * this function also uses the userProfile Singleton to send authorization credentials.
	 * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
	 * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
	 */
	 private void selectLaborRegisterBeans( final String whereClause_, final String orderByClause_){
		final java.util.Date startTime = new java.util.Date();
			laborRegisterService.selectLaborRegister(UserProfile.getInstance(), whereClause_, orderByClause_, 
					new AsyncCallback<ArrayList<LaborRegisterBean>>(){
						public void onFailure(Throwable caught) {
							masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
									AppPref.SERVER_ERROR + caught.getMessage());
							masterController.getAppContainer().setTransactionResults(
								"Retrieving LaborRegister Failed"
								, (new java.util.Date().getTime() -startTime.getTime()));
							masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

						}
			
						public void onSuccess(ArrayList<LaborRegisterBean> laborRegisterResult) {
							masterController.getAppContainer().addSysLogMessage("Select LaborRegister received ok-  Where Attempted: " 
								+ whereClause_ + " | Orderby attempted " + orderByClause_ );
							masterController.getAppContainer().setTransactionResults(
								"Successfully Retrieved LaborRegister listing"
								, (new java.util.Date().getTime() - startTime.getTime()));
							invoiceManagerView.getLaborRegisterTable().setList(laborRegisterResult);
							
						}
			});
		  }


	 /**
	  * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
	  * The results are handled through the onSuccess method in the AsynchCallback.
	  * @param laborRegisterBean_ the bean to save to the database
	  */
	   private void saveLaborRegisterBeanBatch(ArrayList<LaborRegisterBean> laborRegisterBeanList_){
	 	final java.util.Date startTime = new java.util.Date();
	 	laborRegisterService.saveLaborRegisterBeanBatch(UserProfile.getInstance(), laborRegisterBeanList_, 
	 			new AsyncCallback<ArrayList<LaborRegisterBean>>(){
	 				public void onFailure(Throwable caught) {
	 					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch Failed: " + caught);
	 					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
	 							AppPref.SERVER_ERROR + caught.getMessage());
	 					masterController.getAppContainer().setTransactionResults(
	 						"Saving LaborRegister Batch Failed"
	 						, (new java.util.Date().getTime() -startTime.getTime()));
	 				}
	 		
	 				public void onSuccess(ArrayList<LaborRegisterBean> laborRegisterResult) {
	 					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch onSuccess: " + laborRegisterResult.toString());
	 					masterController.getAppContainer().setTransactionResults(
	 							"Successfully saved LaborRegister Batch"
	 							, (new java.util.Date().getTime() - startTime.getTime()));
	 					
	 					laborSaved = true;
	 					generateInvoiceAfterSaves();
	 					
	 			
	 				}
	 		});
	   }
	   
	   

	   /**
	    * Provides a standard template to retrieve beans from the server.  
	    * The results are handled through the onSuccess method in the AsynchCallback.
	    * this function also uses the userProfile Singleton to send authorization credentials.
	    * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
	    * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
	    */
	    private void selectExpenseRegisterBeans( final String whereClause_, final String orderByClause_){
	   	final java.util.Date startTime = new java.util.Date();
	   		expenseRegisterService.selectExpenseRegister(UserProfile.getInstance(), whereClause_, orderByClause_, 
	   				new AsyncCallback<ArrayList<ExpenseRegisterBean>>(){
	   					public void onFailure(Throwable caught) {
	   						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
	   								AppPref.SERVER_ERROR + caught.getMessage());
	   						masterController.getAppContainer().setTransactionResults(
	   							"Retrieving ExpenseRegister Failed"
	   							, (new java.util.Date().getTime() -startTime.getTime()));
	   						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

	   					}
	   		
	   					public void onSuccess(ArrayList<ExpenseRegisterBean> expenseRegisterResult) {
	   						masterController.getAppContainer().addSysLogMessage("Select ExpenseRegister received ok-  Where Attempted: " 
	   							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
	   						masterController.getAppContainer().setTransactionResults(
	   							"Successfully Retrieved ExpenseRegister listing"
	   							, (new java.util.Date().getTime() - startTime.getTime()));
	   						invoiceManagerView.getExpenseRegisterTable().setList(expenseRegisterResult);
	   						
	   					}
	   		});
	   	  }
	    
	    
	    
	    /**
		  * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
		  * The results are handled through the onSuccess method in the AsynchCallback.
		  * @param laborRegisterBean_ the bean to save to the database
		  */
		   public void createInvoiceFromEligibleTrans(Integer customerId_, java.util.Date invoiceDt_){
		 	final java.util.Date startTime = new java.util.Date();
		 	invoiceService.createInvoiceFromEligibleTrans(UserProfile.getInstance(), customerId_, invoiceDt_,
		 			new AsyncCallback<Integer>(){
		 				public void onFailure(Throwable caught) {
		 					Log.debug("InvoiceManagerController.createInvoiceFromEligibleTrans Failed: " + caught);
		 					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
		 							AppPref.SERVER_ERROR + caught.getMessage());
		 					masterController.getAppContainer().setTransactionResults(
		 						"Saving LaborRegister Batch Failed"
		 						, (new java.util.Date().getTime() -startTime.getTime()));
		 				}
		 		
		 				public void onSuccess(Integer newInvoiceId_) {
		 					Log.debug("InvoiceManagerController.createInvoiceFromEligibleTrans onSuccess: " + newInvoiceId_);
		 					masterController.getAppContainer().setTransactionResults(
		 							"Successfully saved LaborRegister Batch"
		 							, (new java.util.Date().getTime() - startTime.getTime()));
		 					
		 					removedInvoicedLaborAndExpenses();
		 				}
		 		});
		   }

		   

			 /**
			  * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
			  * The results are handled through the onSuccess method in the AsynchCallback.
			  * @param laborRegisterBean_ the bean to save to the database
			  */
			   private void saveExpenseRegisterBeanBatch(ArrayList<ExpenseRegisterBean> expenseBeanList_){
			 	final java.util.Date startTime = new java.util.Date();
			 	expenseRegisterService.saveExpenseRegisterBeanBatch(UserProfile.getInstance(), expenseBeanList_, 
			 			new AsyncCallback<ArrayList<ExpenseRegisterBean>>(){
			 				public void onFailure(Throwable caught) {
			 					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch Failed: " + caught);
			 					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
			 							AppPref.SERVER_ERROR + caught.getMessage());
			 					masterController.getAppContainer().setTransactionResults(
			 						"Saving LaborRegister Batch Failed"
			 						, (new java.util.Date().getTime() -startTime.getTime()));
			 				}
			 		
			 				public void onSuccess(ArrayList<ExpenseRegisterBean> expenseRegisterResult) {
			 					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch onSuccess: " + expenseRegisterResult.toString());
			 					masterController.getAppContainer().setTransactionResults(
			 							"Successfully saved LaborRegister Batch"
			 							, (new java.util.Date().getTime() - startTime.getTime()));
			 					
			 					expensesSaved = true;
			 					generateInvoiceAfterSaves();
			 				}
			 		});
			   }
			   
			   private void generateInvoiceAfterSaves(){
				   if (expensesSaved && laborSaved){
					   createInvoiceFromEligibleTrans(invoiceManagerView.getSelectedCustomerId(), new java.util.Date());
					   invoiceManagerView.getCmdGenerateInvoice().setEnabled(true);
				   }
			   }
			   private void removedInvoicedLaborAndExpenses(){
				   
				   for(ExpenseRegisterBean eBean :invoiceManagerView.getExpenseRegisterTable().getStore().getModels()){
				   	if(eBean.getInvoiceable()){
				   		invoiceManagerView.getExpenseRegisterTable().getStore().remove(eBean);
				   	}
				   }
				   for(LaborRegisterBean lBean :invoiceManagerView.getLaborRegisterTable().getStore().getModels()){
					   	if(lBean.getInvoiceable()){
					   		invoiceManagerView.getLaborRegisterTable().getStore().remove(lBean);
					   	}
					   }
				   
			   }

			   
			   
	   private void createInvoiceSelectionDialog() {
			//
			  BorderLayout layout = new BorderLayout(); 
			  invoiceSelectionDialog.setLayout(layout);
			  invoiceSelectionDialog.add(invoiceSelectionView.getInvoiceSelectionComposite());
		  
			  invoiceSelectionDialog.setWidth(700);
			  invoiceSelectionDialog.setModal(true);
			  invoiceSelectionDialog.setButtons(Dialog.OK);
			  invoiceSelectionDialog.setHideOnButtonClick(true);
			  invoiceSelectionDialog.setClosable(false);
			  
			  invoiceSelectionDialog.getButtonById(Dialog.OK).setText("Finished");

			  
			
		}
	   
	   
	   
	   /**
	    * Provides a standard template to retrieve beans from the server.  
	    * The results are handled through the onSuccess method in the AsynchCallback.
	    * this function also uses the userProfile Singleton to send authorization credentials.
	    * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
	    * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
	    */
	    private void selectVwInvoiceDisplayBeans( final String whereClause_, final String orderByClause_){
	   	final java.util.Date startTime = new java.util.Date();
	   		vwInvoiceDisplayService.selectVwInvoiceDisplay(UserProfile.getInstance(), whereClause_, orderByClause_, 
	   				new AsyncCallback<ArrayList<VwInvoiceDisplayBean>>(){
	   					public void onFailure(Throwable caught) {
	   						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
	   								AppPref.SERVER_ERROR + caught.getMessage());
	   						masterController.getAppContainer().setTransactionResults(
	   							"Retrieving VwInvoiceDisplay Failed"
	   							, (new java.util.Date().getTime() -startTime.getTime()));
	   						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

	   					}
	   		
	   					public void onSuccess(ArrayList<VwInvoiceDisplayBean> vwInvoiceDisplayResult) {
	   						masterController.getAppContainer().addSysLogMessage("Select VwInvoiceDisplay received ok-  Where Attempted: " 
	   							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
	   						masterController.getAppContainer().setTransactionResults(
	   							"Successfully Retrieved VwInvoiceDisplay listing"
	   							, (new java.util.Date().getTime() - startTime.getTime()));
	   						invoiceSelectionView.getInvoiceTable().setList(vwInvoiceDisplayResult);
	   						
	   					}
	   		});
	   	  }

}
