package com.martinanalytics.legaltime.client.controller;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.LaborRegisterService;
import com.martinanalytics.legaltime.client.model.LaborRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.view.InvoiceManagerView;
import com.martinanalytics.legaltime.client.view.LaborRegisterView;

public class InvoiceManagerController implements AppEventListener {
	private static InvoiceManagerController instance=null;
	private InvoiceManagerView invoiceManagerView;
	MasterController masterController;
	  private final LaborRegisterServiceAsync laborRegisterService = 
			GWT.create(LaborRegisterService.class); 
	protected InvoiceManagerController (MasterController masterController_){
		masterController = masterController_;
		invoiceManagerView = new InvoiceManagerView();
		invoiceManagerView.getNotifier().addAppEventListener(this);
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
			selectLaborRegisterBeans("where customer_id = "+ e_.getPayLoad(), "order by activity_date");
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

}
