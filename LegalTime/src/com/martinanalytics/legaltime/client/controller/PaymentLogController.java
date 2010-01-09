
package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.PaymentLogService;
import com.martinanalytics.legaltime.client.model.PaymentLogServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.PaymentLogBean;
import com.martinanalytics.legaltime.client.view.PaymentLogView;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;
import com.extjs.gxt.ui.client.store.Record;
import java.util.List;



/**
 * PaymentLogController owns the PaymentLog View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class PaymentLogController implements AppEventListener, ClickHandler, ChangeHandler{
  private static PaymentLogController instance = null; //Singleton Instance
  private PaymentLogView paymentLogView; 	//The UI container
  private final PaymentLogServiceAsync paymentLogService = 
	GWT.create(PaymentLogService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private AppNotifyObject notifier = new AppNotifyObject();
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  PaymentLogController(MasterController masterController_){
	masterController =masterController_;
	paymentLogView = new PaymentLogView();	
	paymentLogView.addAppEventListener(this);
	//paymentLogView.getPaymentLogTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static PaymentLogController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new PaymentLogController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public PaymentLogView getPaymentLogView() {
	return paymentLogView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("PaymentLogViewOnAttach")){
		
	}else if(e_.getName().equals("PaymentLogViewOnDetach")){
	}else if(e_.getName().equals("PaymentLogTableOnAttach")){		
	}else if(e_.getName().equals("PaymentLogTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("PaymentLogController on click called");
	
	
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
 private void selectPaymentLogBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		paymentLogService.selectPaymentLog(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<PaymentLogBean>>(){
					public void onFailure(Throwable caught) {
						masterController.getAppContainer().setTransactionResults(
							"Retrieving PaymentLog Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );
							if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
								masterController.promptUserForLogin();
							}else{
								masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
										AppPref.SERVER_ERROR + caught.getMessage());
							}


					}
		
					public void onSuccess(ArrayList<PaymentLogBean> paymentLogResult) {
						masterController.getAppContainer().addSysLogMessage("Select PaymentLog received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved PaymentLog listing"
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
 private void retrievePaymentLogBeanByPrKey( Integer paymentLogId_ , Integer customerId_ ){
	final java.util.Date startTime = new java.util.Date();
		paymentLogService.getPaymentLogByPrKey(userProfile,  paymentLogId_ , customerId_ , 
				new AsyncCallback<PaymentLogBean>(){
					public void onFailure(Throwable caught) {
						masterController.getAppContainer().setTransactionResults(
							"Retrieving PaymentLog by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
							masterController.promptUserForLogin();
						}else{
							masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
									AppPref.SERVER_ERROR + caught.getMessage());
						}

					}
		
					public void onSuccess(PaymentLogBean paymentLogResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve PaymentLog by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved PaymentLog by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param paymentLogBean_ the bean to insert into the database
 */
  private void insertPaymentLogBean(final PaymentLogBean paymentLogBean_){
	final java.util.Date startTime = new java.util.Date();
	paymentLogService.insertPaymentLogBean(userProfile,paymentLogBean_,
			new AsyncCallback<PaymentLogBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("paymentLogService.insertPaymentLog Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Adding PaymentLog Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + paymentLogBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					}
				}
	
				public void onSuccess(PaymentLogBean result) {
					Log.debug("paymentLogService.insertPaymentLog onSuccess: " + result);
					if (  result.getPaymentLogId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted PaymentLog record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "paymentLog.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param paymentLogBean_ the bean to update the database
 */
   private void updatePaymentLogBean(final PaymentLogBean paymentLogBean_){
	final java.util.Date startTime = new java.util.Date();
	paymentLogService.updatePaymentLogBean(userProfile,paymentLogBean_,
			new AsyncCallback<PaymentLogBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("paymentLogService.updatePaymentLog Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Updating PaymentLog Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + paymentLogBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}
				}
	
				public void onSuccess(PaymentLogBean result) {
					Log.debug("paymentLogService.updatePaymentLog onSuccess: " + result);
					if (  result.getPaymentLogId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated PaymentLog record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "paymentLog record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param paymentLogBean_ the bean to delete the database
 */
 private void deletePaymentLogBean(final PaymentLogBean paymentLogBean_){
	final java.util.Date startTime = new java.util.Date();
	paymentLogService.deletePaymentLogBean(userProfile,paymentLogBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					masterController.getAppContainer().setTransactionResults(
						"Deleting PaymentLog Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + paymentLogBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("paymentLogService.deletePaymentLog onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted PaymentLog record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  paymentLogBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "paymentLog record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param paymentLogBean_ the bean to save to the database
 */
  private void savePaymentLogBean(final PaymentLogBean paymentLogBean_){
	final java.util.Date startTime = new java.util.Date();
	paymentLogService.savePaymentLogBean(userProfile,paymentLogBean_,
			new AsyncCallback<PaymentLogBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("paymentLogService.savePaymentLog Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Saving PaymentLog Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + paymentLogBean_);
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
	
				public void onSuccess(PaymentLogBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("paymentLogService.savePaymentLog onSuccess: " + result);
					if (  result.getPaymentLogId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved PaymentLog record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  paymentLogBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "paymentLog record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param paymentLogBean_ the bean to save to the database
 */
  private void savePaymentLogBeanBatch(ArrayList<PaymentLogBean> paymentLogBeanList_){
	final java.util.Date startTime = new java.util.Date();
	paymentLogService.savePaymentLogBeanBatch(userProfile, paymentLogBeanList_, 
			new AsyncCallback<ArrayList<PaymentLogBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("paymentLogService.savePaymentLogBeanBatch Failed: " + caught);
					masterController.getAppContainer().setTransactionResults(
						"Saving PaymentLog Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					if (caught.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
						masterController.promptUserForLogin();
					}else{
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
					}

				}
		
				public void onSuccess(ArrayList<PaymentLogBean> paymentLogResult) {
					Log.debug("paymentLogService.savePaymentLogBeanBatch onSuccess: " + paymentLogResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved PaymentLog Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }

		


public void saveAllChanges(){

	  List<Record> modified =  paymentLogView.getStore().getModifiedRecords();
	  PaymentLogBean paymentLogBean;// = new PaymentLogBean();
	  ArrayList<PaymentLogBean> batchSave = new ArrayList<PaymentLogBean>();
	  for (Record r : modified) {
		 // Log.debug("Identified Modified Record");
		  paymentLogBean = new PaymentLogBean();
		  paymentLogBean.setProperties(r.getModel().getProperties());
		  batchSave.add(paymentLogBean);
		  
		  
	  }
		 paymentLogView.getStore().commitChanges();
		savePaymentLogBeanBatch( batchSave );
		
		
	  
  }


/**
 * @return the notifier
 */
public AppNotifyObject getNotifier() {
	return notifier;
}


}


