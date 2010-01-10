
package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.CustomerAccountRegisterService;
import com.martinanalytics.legaltime.client.model.CustomerAccountRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.martinanalytics.legaltime.client.view.CustomerAccountRegisterView;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;
import com.extjs.gxt.ui.client.store.Record;
import java.util.List;



/**
 * CustomerAccountRegisterController owns the CustomerAccountRegister View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class CustomerAccountRegisterController implements AppEventListener, ClickHandler, ChangeHandler{
  private static CustomerAccountRegisterController instance = null; //Singleton Instance
  private CustomerAccountRegisterView customerAccountRegisterView; 	//The UI container
  private final CustomerAccountRegisterServiceAsync customerAccountRegisterService = 
	GWT.create(CustomerAccountRegisterService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
  private AppNotifyObject notifier = new AppNotifyObject();
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  CustomerAccountRegisterController(MasterController masterController_){
	masterController =masterController_;
	customerAccountRegisterView = new CustomerAccountRegisterView();
	customerAccountRegisterView.getNotifier().addAppEventListener(this);
	customerAccountRegisterView.addAppEventListener(this);
	//customerAccountRegisterView.getCustomerAccountRegisterTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
	
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static CustomerAccountRegisterController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new CustomerAccountRegisterController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public CustomerAccountRegisterView getCustomerAccountRegisterView() {
	return customerAccountRegisterView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("CustomerAccountRegisterViewOnAttach")){
		
	}else if(e_.getName().equals("CustomerAccountRegisterViewOnDetach")){
	}else if(e_.getName().equals("CustomerAccountRegisterTableOnAttach")){		
	}else if(e_.getName().equals("CustomerAccountRegisterTableOnDetach")){		
	}else if(e_.getName().equals("AccountRegisterCustomerChanged")){
		Integer custid = (Integer)e_.getPayLoad();
		selectCustomerAccountRegisterBeans("where customer_id=" + custid, "order by effective_dt");
	}else if(e_.getName().equals("PostPaymentRequest")){	
		notifier.notifyAppEvent(this, "PostPaymentRequest", e_.getPayLoad());
	}else if(e_.getName().equals(	"ReverseRequest")){
		reverseTransaction();
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

private void reverseTransaction() {
	Integer selectedId =0;
	String tranType ="";
	try{
	CustomerAccountRegisterBean customerAccountRegisterBean = customerAccountRegisterView.getCustomerAccountRegisterTable().getGrid().getSelectionModel().getSelection().get(0);
	Log.debug("selected ref" + customerAccountRegisterBean.getRefId() );
	Log.debug("selected type" + customerAccountRegisterBean.getTranType() );
	}catch(Exception e){
		Log.debug("Exception Caught in CustomerAcount Register");
	}
	
}

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("CustomerAccountRegisterController on click called");
	
	
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
 private void selectCustomerAccountRegisterBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		customerAccountRegisterService.selectCustomerAccountRegister(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<CustomerAccountRegisterBean>>(){
					public void onFailure(Throwable caught) {
						masterController.getAppContainer().setTransactionResults(
							"Retrieving CustomerAccountRegister Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );
							if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
								masterController.promptUserForLogin();
							}else{
								masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
										AppPref.SERVER_ERROR + caught.getMessage());
							}


					}
		
					public void onSuccess(ArrayList<CustomerAccountRegisterBean> customerAccountRegisterResult) {
						masterController.getAppContainer().addSysLogMessage("Select CustomerAccountRegister received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved CustomerAccountRegister listing"
							, (new java.util.Date().getTime() - startTime.getTime()));
						customerAccountRegisterView.getCustomerAccountRegisterTable().setList(customerAccountRegisterResult);
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
 private void retrieveCustomerAccountRegisterBeanByPrKey( Integer clientAccountRegisterId_ , Integer customerId_ ){
	final java.util.Date startTime = new java.util.Date();
		customerAccountRegisterService.getCustomerAccountRegisterByPrKey(userProfile,  clientAccountRegisterId_ , customerId_ , 
				new AsyncCallback<CustomerAccountRegisterBean>(){
					public void onFailure(Throwable caught) {
						masterController.getAppContainer().setTransactionResults(
							"Retrieving CustomerAccountRegister by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
							masterController.promptUserForLogin();
						}else{
							masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
									AppPref.SERVER_ERROR + caught.getMessage());
						}

					}
		
					public void onSuccess(CustomerAccountRegisterBean customerAccountRegisterResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve CustomerAccountRegister by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved CustomerAccountRegister by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerAccountRegisterBean_ the bean to insert into the database
 */
  private void insertCustomerAccountRegisterBean(final CustomerAccountRegisterBean customerAccountRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	customerAccountRegisterService.insertCustomerAccountRegisterBean(userProfile,customerAccountRegisterBean_,
			new AsyncCallback<CustomerAccountRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerAccountRegisterService.insertCustomerAccountRegister Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Adding CustomerAccountRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + customerAccountRegisterBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					}
				}
	
				public void onSuccess(CustomerAccountRegisterBean result) {
					Log.debug("customerAccountRegisterService.insertCustomerAccountRegister onSuccess: " + result);
					if (  result.getCustomerAccountRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted CustomerAccountRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "customerAccountRegister.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerAccountRegisterBean_ the bean to update the database
 */
   private void updateCustomerAccountRegisterBean(final CustomerAccountRegisterBean customerAccountRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	customerAccountRegisterService.updateCustomerAccountRegisterBean(userProfile,customerAccountRegisterBean_,
			new AsyncCallback<CustomerAccountRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerAccountRegisterService.updateCustomerAccountRegister Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Updating CustomerAccountRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + customerAccountRegisterBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}
				}
	
				public void onSuccess(CustomerAccountRegisterBean result) {
					Log.debug("customerAccountRegisterService.updateCustomerAccountRegister onSuccess: " + result);
					if (  result.getCustomerAccountRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated CustomerAccountRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "customerAccountRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerAccountRegisterBean_ the bean to delete the database
 */
 private void deleteCustomerAccountRegisterBean(final CustomerAccountRegisterBean customerAccountRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	customerAccountRegisterService.deleteCustomerAccountRegisterBean(userProfile,customerAccountRegisterBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					masterController.getAppContainer().setTransactionResults(
						"Deleting CustomerAccountRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + customerAccountRegisterBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("customerAccountRegisterService.deleteCustomerAccountRegister onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted CustomerAccountRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  customerAccountRegisterBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "customerAccountRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerAccountRegisterBean_ the bean to save to the database
 */
  private void saveCustomerAccountRegisterBean(final CustomerAccountRegisterBean customerAccountRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	customerAccountRegisterService.saveCustomerAccountRegisterBean(userProfile,customerAccountRegisterBean_,
			new AsyncCallback<CustomerAccountRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerAccountRegisterService.saveCustomerAccountRegister Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Saving CustomerAccountRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + customerAccountRegisterBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
	
				public void onSuccess(CustomerAccountRegisterBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("customerAccountRegisterService.saveCustomerAccountRegister onSuccess: " + result);
					if (  result.getCustomerAccountRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved CustomerAccountRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  customerAccountRegisterBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "customerAccountRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerAccountRegisterBean_ the bean to save to the database
 */
  private void saveCustomerAccountRegisterBeanBatch(ArrayList<CustomerAccountRegisterBean> customerAccountRegisterBeanList_){
	final java.util.Date startTime = new java.util.Date();
	customerAccountRegisterService.saveCustomerAccountRegisterBeanBatch(userProfile, customerAccountRegisterBeanList_, 
			new AsyncCallback<ArrayList<CustomerAccountRegisterBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerAccountRegisterService.saveCustomerAccountRegisterBeanBatch Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Saving CustomerAccountRegister Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
		
				public void onSuccess(ArrayList<CustomerAccountRegisterBean> customerAccountRegisterResult) {
					Log.debug("customerAccountRegisterService.saveCustomerAccountRegisterBeanBatch onSuccess: " + customerAccountRegisterResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved CustomerAccountRegister Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }

		


public void saveAllChanges(){

	  List<Record> modified =  customerAccountRegisterView.getStore().getModifiedRecords();
	  CustomerAccountRegisterBean customerAccountRegisterBean;// = new CustomerAccountRegisterBean();
	  ArrayList<CustomerAccountRegisterBean> batchSave = new ArrayList<CustomerAccountRegisterBean>();
	  for (Record r : modified) {
		 // Log.debug("Identified Modified Record");
		  customerAccountRegisterBean = new CustomerAccountRegisterBean();
		  customerAccountRegisterBean.setProperties(r.getModel().getProperties());
		  batchSave.add(customerAccountRegisterBean);
		  
		  
	  }
		 customerAccountRegisterView.getStore().commitChanges();
		saveCustomerAccountRegisterBeanBatch( batchSave );
		
		
	  
  }

/**
 * @return the notifier
 */
public AppNotifyObject getNotifier() {
	return notifier;
}

public void refreshList() {
	Integer custid = (Integer)customerAccountRegisterView.getCboCustomerId().getKeyValue();
	selectCustomerAccountRegisterBeans("where customer_id=" + custid, "order by effective_dt");
	
}



}


