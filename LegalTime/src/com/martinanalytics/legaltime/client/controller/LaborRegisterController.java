/*This file has been modified
 *   added retrieve last date function
 *   added assessMonthlyCharges Functions
 */

package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.ServerExcpetionHandler;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.LaborRegisterService;
import com.martinanalytics.legaltime.client.model.LaborRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.view.LaborRegisterView;
import java.util.ArrayList;
import java.util.List;



import com.extjs.gxt.ui.client.store.Record;
import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * LaborRegisterController owns the LaborRegister View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class LaborRegisterController implements AppEventListener, ClickHandler, ChangeHandler{
  private static LaborRegisterController instance = null; //Singleton Instance
  private LaborRegisterView laborRegisterView; 	//The UI container
  private final LaborRegisterServiceAsync laborRegisterService = 
	GWT.create(LaborRegisterService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date endTimeHolder;  //Holder variables for timestamps  private java.util.Date startTimeHolder;  //Holder variables for timestamps  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
  private AppNotifyObject notifier = new AppNotifyObject();
  final Dialog laborRegisterDialog =new Dialog();
 
  LaborRegisterBean bean = new LaborRegisterBean();
  /**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  LaborRegisterController(MasterController masterController_){
	masterController =masterController_;
	laborRegisterView = new LaborRegisterView();	
	laborRegisterView.addAppEventListener(this);
	//laborRegisterView.getLaborRegisterTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
	laborRegisterView.getFormBindings().bind(bean);
	createLaborRegisterDialog();
	
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static LaborRegisterController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new LaborRegisterController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public LaborRegisterView getLaborRegisterView() {
	return laborRegisterView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("LaborRegisterViewOnAttach")){
		
	}else if(e_.getName().equals("LaborRegisterViewOnDetach")){
	}else if(e_.getName().equals("LaborRegisterTableOnAttach")){		
	}else if(e_.getName().equals("LaborRegisterTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("LaborRegisterController on click called");
	
	
  }

/**
 * Handles any Select Box changes.
 */
@Override
  public void onChange(ChangeEvent event_) {
		
		
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
		laborRegisterService.selectLaborRegister(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<LaborRegisterBean>>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
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
						
					}
		});
	  }





/**
 * Provides a standard template to retrieve a bean from the server by primary keys.  
 * The results are handled through the onSuccess method in the AsynchCallback.
 * this function also uses the userProfile Singleton to send authorization credentials.
 * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
 * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
 */
 private void retrieveLaborRegisterBeanByPrKey( Integer laborRegisterId_ , Integer customerId_ ){
	final java.util.Date startTime = new java.util.Date();
		laborRegisterService.getLaborRegisterByPrKey(userProfile,  laborRegisterId_ , customerId_ , 
				new AsyncCallback<LaborRegisterBean>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController.getAppContainer().setTransactionResults(
							"Retrieving LaborRegister by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));

					}
		
					public void onSuccess(LaborRegisterBean laborRegisterResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve LaborRegister by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved LaborRegister by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param laborRegisterBean_ the bean to insert into the database
 */
  private void insertLaborRegisterBean(final LaborRegisterBean laborRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	laborRegisterService.insertLaborRegisterBean(userProfile,laborRegisterBean_,
			new AsyncCallback<LaborRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("laborRegisterService.insertLaborRegister Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Adding LaborRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + laborRegisterBean_);

				}
	
				public void onSuccess(LaborRegisterBean result) {
					Log.debug("laborRegisterService.insertLaborRegister onSuccess: " + result);
					if (  result.getLaborRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted LaborRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "laborRegister.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param laborRegisterBean_ the bean to update the database
 */
   private void updateLaborRegisterBean(final LaborRegisterBean laborRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	laborRegisterService.updateLaborRegisterBean(userProfile,laborRegisterBean_,
			new AsyncCallback<LaborRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("laborRegisterService.updateLaborRegister Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Updating LaborRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + laborRegisterBean_);

				}
	
				public void onSuccess(LaborRegisterBean result) {
					Log.debug("laborRegisterService.updateLaborRegister onSuccess: " + result);
					if (  result.getLaborRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated LaborRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "laborRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param laborRegisterBean_ the bean to delete the database
 */
 private void deleteLaborRegisterBean(final LaborRegisterBean laborRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	laborRegisterService.deleteLaborRegisterBean(userProfile,laborRegisterBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Deleting LaborRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + laborRegisterBean_);

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("laborRegisterService.deleteLaborRegister onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted LaborRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  laborRegisterBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "laborRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param laborRegisterBean_ the bean to save to the database
 */
  private void saveLaborRegisterBean(final LaborRegisterBean laborRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	laborRegisterService.saveLaborRegisterBean(userProfile,laborRegisterBean_,
			new AsyncCallback<LaborRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("laborRegisterService.saveLaborRegister Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving LaborRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + laborRegisterBean_);

				}
	
				public void onSuccess(LaborRegisterBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("laborRegisterService.saveLaborRegister onSuccess: " + result);
					if (  result.getLaborRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved LaborRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  laborRegisterBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "laborRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
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
	laborRegisterService.saveLaborRegisterBeanBatch(userProfile, laborRegisterBeanList_, 
			new AsyncCallback<ArrayList<LaborRegisterBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving LaborRegister Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<LaborRegisterBean> laborRegisterResult) {
					Log.debug("laborRegisterService.saveLaborRegisterBeanBatch onSuccess: " + laborRegisterResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved LaborRegister Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }


  
  public void createLaborRegisterDialog(){
	  
  	  laborRegisterDialog.setWidth(650);
  	  laborRegisterDialog.setModal(true);
  	  laborRegisterDialog.setButtons(Dialog.YESNOCANCEL);
  	  laborRegisterDialog.setHideOnButtonClick(false);
  	  laborRegisterDialog.setClosable(false);
  	  laborRegisterDialog.getButtonById(Dialog.CANCEL).setText("Home");
  	  laborRegisterDialog.add(laborRegisterView.getLaborRegisterFormPanel());
  	  laborRegisterView.clearAllInvalids();
  	  laborRegisterDialog.getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() { 
  		
  		  @Override  
  		  public void componentSelected(ButtonEvent ce) { 
  			laborRegisterDialog.setVisible(false);
  		  }
  	  }

  	  );
  	  laborRegisterDialog.getButtonById(Dialog.YES).setText("Record and Add Another");
  	  laborRegisterDialog.getButtonById(Dialog.YES).addSelectionListener(new SelectionListener<ButtonEvent>() { 

  		  @Override  
  		  public void componentSelected(ButtonEvent ce) { 
  			if (laborRegisterView.getLaborRegisterFormPanel().isValid()){
  			  laborRegisterView.updateCalcValues();
  			  saveAllChanges();
  			  clearValues();
  			  addLaborRegisterBean();
  			}
  		  }
  	  }

  	  );
  	  
  	  laborRegisterDialog.getButtonById(Dialog.NO).setText("Record and Return");
	  laborRegisterDialog.getButtonById(Dialog.NO).addSelectionListener(new SelectionListener<ButtonEvent>() { 

		  @Override  
		  public void componentSelected(ButtonEvent ce) { 
			  if (laborRegisterView.getLaborRegisterFormPanel().isValid()){
				  laborRegisterDialog.setVisible(false);
				  laborRegisterView.updateCalcValues();
				  saveAllChanges();
				  clearValues();
			  }
		  }

		
	  }

	  );
	  
  }
  private void clearValues() {
	  laborRegisterView.getTxtDescription().setValue("");
	  laborRegisterView.clearAllInvalids();
		
	}
  public void showBillableHoursDialog(){  
	  masterController.getAppContainer().setAppWindowDialog(laborRegisterDialog);

	  
	  addLaborRegisterBean();

	  laborRegisterDialog.show();
	  
  }
  
  public void addLaborRegisterBean(){
	  
	  bean.setDescription("");
	 
	  bean.setInvoiceable(true);
	  bean.setLaborRegisterId(null);
	  //bean.setStartTime(new java.util.Date());
	  //bean.setEndTime(new java.util.Date());
	  
	  //laborRegisterView.getCboCustomerId().clearSelections();
	  
	  
	  
	  

  }

  

  public void saveAllChanges(){
	  

  	  ArrayList<LaborRegisterBean> batchSave = new ArrayList<LaborRegisterBean>();

  	  batchSave.add(bean);
  		
  		saveLaborRegisterBeanBatch( batchSave );
  		
  		
  	  
    }
  
  
  
  
  
  
  
  public void retrieveLastMonthlyChargeDate( ){
		final java.util.Date startTime = new java.util.Date();
			laborRegisterService.RetrieveLastMonthlycharge(userProfile, 
					new AsyncCallback<java.util.Date>(){
						public void onFailure(Throwable caught) {
							if(!ServerExcpetionHandler.getInstance().handle(caught)){

							}
							masterController.getAppContainer().setTransactionResults(
								"retrieveLastMonthlyChargeDate Failed"
								, (new java.util.Date().getTime() -startTime.getTime()));
							masterController.getAppContainer().addSysLogMessage("retrieveLastMonthlyChargeDate Attempted "  );

						}
			
						public void onSuccess(java.util.Date lastAssessedChargeDate) {
							masterController.getAppContainer().addSysLogMessage("retrieveLastMonthlyChargeDate ");
							
							masterController.getAppContainer().setTransactionResults(
								"Successfully retrieveLastMonthlyChargeDate"
								, (new java.util.Date().getTime() - startTime.getTime()));
							AppContainer.getInstance().getTxtLastMonthlyChargeDate().setValue(lastAssessedChargeDate);
							
						}
			});
		  }
  
  
  public void assessMonthlyChargesAndInvoice(java.util.Date assessDt_ ){
		final java.util.Date startTime = new java.util.Date();
			laborRegisterService.AssessMonthlyChargesAndInvoice(userProfile,  assessDt_,
					new AsyncCallback<ArrayList<Integer>>(){
						public void onFailure(Throwable caught) {
							if(!ServerExcpetionHandler.getInstance().handle(caught)){

							}
							masterController.getAppContainer().setTransactionResults(
								"retrieveLastMonthlyChargeDate Failed"
								, (new java.util.Date().getTime() -startTime.getTime()));
							masterController.getAppContainer().addSysLogMessage("retrieveLastMonthlyChargeDate Attempted "  );

						}
			
						public void onSuccess(ArrayList<Integer> invoiceList) {
							masterController.getAppContainer().addSysLogMessage("retrieveLastMonthlyChargeDate ");
							
							masterController.getAppContainer().setTransactionResults(
								"Successfully retrieveLastMonthlyChargeDate"
								, (new java.util.Date().getTime() - startTime.getTime()));
							
							notifier.notifyAppEvent(this, "MonthlyChargesAssesmentComplete", invoiceList);
							
						}
			});
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
  
  

}


