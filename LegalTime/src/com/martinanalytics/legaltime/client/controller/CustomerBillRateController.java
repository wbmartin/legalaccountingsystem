
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
import com.martinanalytics.legaltime.client.ServerExcpetionHandler;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.CustomerBillRateService;
import com.martinanalytics.legaltime.client.model.CustomerBillRateServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.view.CustomerBillRateView;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * CustomerBillRateController owns the CustomerBillRate View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class CustomerBillRateController implements AppEventListener, ClickHandler, ChangeHandler{
  private static CustomerBillRateController instance = null; //Singleton Instance
  private CustomerBillRateView customerBillRateView; 	//The UI container
  private final CustomerBillRateServiceAsync customerBillRateService = 
	GWT.create(CustomerBillRateService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  CustomerBillRateController(MasterController masterController_){
	masterController =masterController_;
	customerBillRateView = new CustomerBillRateView();	
	customerBillRateView.addAppEventListener(this);
	//customerBillRateView.getCustomerBillRateTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static CustomerBillRateController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new CustomerBillRateController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public CustomerBillRateView getCustomerBillRateView() {
	return customerBillRateView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("CustomerBillRateViewOnAttach")){
		
	}else if(e_.getName().equals("CustomerBillRateViewOnDetach")){
	}else if(e_.getName().equals("CustomerBillRateTableOnAttach")){		
	}else if(e_.getName().equals("CustomerBillRateTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("CustomerBillRateController on click called");
	
	
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
 private void selectCustomerBillRateBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		customerBillRateService.selectCustomerBillRate(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<CustomerBillRateBean>>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController.getAppContainer().setTransactionResults(
							"Retrieving CustomerBillRate Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
					}
		
					public void onSuccess(ArrayList<CustomerBillRateBean> customerBillRateResult) {
						masterController.getAppContainer().addSysLogMessage("Select CustomerBillRate received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved CustomerBillRate listing"
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
 private void retrieveCustomerBillRateBeanByPrKey( Integer customerBillRateId_ , Integer customerId_ , String userId_ ){
//	final java.util.Date startTime = new java.util.Date();
//		customerBillRateService.getCustomerBillRateByPrKey(userProfile,  customerBillRateId_ , customerId_ , userId_ , 
//				new AsyncCallback<CustomerBillRateBean>(){
//					public void onFailure(Throwable caught) {
//						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//								AppPref.SERVER_ERROR + caught.getMessage());
//						masterController.getAppContainer().setTransactionResults(
//							"Retrieving CustomerBillRate by Identifier Failed"
//							, (new java.util.Date().getTime() -startTime.getTime()));
//
//					}
//		
//					public void onSuccess(CustomerBillRateBean customerBillRateResult) {
//						masterController.getAppContainer().addSysLogMessage("Retrieve CustomerBillRate by identifier received ok");
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully Retrieved CustomerBillRate by Primary Key"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						
//					}
//		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBillRateBean_ the bean to insert into the database
 */
  private void insertCustomerBillRateBean(final CustomerBillRateBean customerBillRateBean_){
	final java.util.Date startTime = new java.util.Date();
	customerBillRateService.insertCustomerBillRateBean(userProfile,customerBillRateBean_,
			new AsyncCallback<CustomerBillRateBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerBillRateService.insertCustomerBillRate Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Adding CustomerBillRate Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + customerBillRateBean_);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
				}
	
				public void onSuccess(CustomerBillRateBean result) {
					Log.debug("customerBillRateService.insertCustomerBillRate onSuccess: " + result);
					if (  result.getCustomerBillRateId() !=null && result.getClientId() !=null && result.getCustomerId() !=null && result.getUserId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted CustomerBillRate record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "customerBillRate.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBillRateBean_ the bean to update the database
 */
   private void updateCustomerBillRateBean(final CustomerBillRateBean customerBillRateBean_){
	final java.util.Date startTime = new java.util.Date();
	customerBillRateService.updateCustomerBillRateBean(userProfile,customerBillRateBean_,
			new AsyncCallback<CustomerBillRateBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerBillRateService.updateCustomerBillRate Failed: " + caught);
					
					masterController.getAppContainer().setTransactionResults(
						"Updating CustomerBillRate Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + customerBillRateBean_);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
				}
	
				public void onSuccess(CustomerBillRateBean result) {
					Log.debug("customerBillRateService.updateCustomerBillRate onSuccess: " + result);
					if (  result.getCustomerBillRateId() !=null && result.getClientId() !=null && result.getCustomerId() !=null && result.getUserId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated CustomerBillRate record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "customerBillRate record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBillRateBean_ the bean to delete the database
 */
 private void deleteCustomerBillRateBean(final CustomerBillRateBean customerBillRateBean_){
	final java.util.Date startTime = new java.util.Date();
	customerBillRateService.deleteCustomerBillRateBean(userProfile,customerBillRateBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					
					
					masterController.getAppContainer().setTransactionResults(
						"Deleting CustomerBillRate Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + customerBillRateBean_);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
				}
	

				public void onSuccess(Boolean result) {
					Log.debug("customerBillRateService.deleteCustomerBillRate onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted CustomerBillRate record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  customerBillRateBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "customerBillRate record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBillRateBean_ the bean to save to the database
 */
  private void saveCustomerBillRateBean(final CustomerBillRateBean customerBillRateBean_){
	final java.util.Date startTime = new java.util.Date();
	customerBillRateService.saveCustomerBillRateBean(userProfile,customerBillRateBean_,
			new AsyncCallback<CustomerBillRateBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerBillRateService.saveCustomerBillRate Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving CustomerBillRate Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + customerBillRateBean_);

				}
	
				public void onSuccess(CustomerBillRateBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("customerBillRateService.saveCustomerBillRate onSuccess: " + result);
					if (  result.getCustomerBillRateId() !=null && result.getClientId() !=null && result.getCustomerId() !=null && result.getUserId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved CustomerBillRate record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  customerBillRateBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "customerBillRate record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBillRateBean_ the bean to save to the database
 */
  private void saveCustomerBillRateBeanBatch(ArrayList<CustomerBillRateBean> customerBillRateBeanList_){
	final java.util.Date startTime = new java.util.Date();
	customerBillRateService.saveCustomerBillRateBeanBatch(userProfile, customerBillRateBeanList_, 
			new AsyncCallback<ArrayList<CustomerBillRateBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerBillRateService.saveCustomerBillRateBeanBatch Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving CustomerBillRate Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<CustomerBillRateBean> customerBillRateResult) {
					Log.debug("customerBillRateService.saveCustomerBillRateBeanBatch onSuccess: " + customerBillRateResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved CustomerBillRate Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }

/**
 * updates the UI with the bean parameter
 */
  public void synchBeanToDisplay(CustomerBillRateBean customerBillRateBean_){


    try{
	customerBillRateBean_.setBillRate((Double)customerBillRateView.getNbrBillRate().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	customerBillRateBean_.setLastUpdate(customerBillRateView.getDtfLastUpdate().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	customerBillRateBean_.setUserId(customerBillRateView.getTxtUserId().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	customerBillRateBean_.setCustomerId((Integer)customerBillRateView.getNbrCustomerId().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	customerBillRateBean_.setClientId((Integer)customerBillRateView.getNbrClientId().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	customerBillRateBean_.setCustomerBillRateId((Integer)customerBillRateView.getNbrCustomerBillRateId().getValue());
    }catch(Exception e){
		
    }	
 




   
 

  }
/**
 * updates the bean parameter with values in the UI
 */
  public void synchDisplayToBean(CustomerBillRateBean customerBillRateBean_){
	customerBillRateView.getNbrBillRate().setValue(customerBillRateBean_.getBillRate());	
 


	customerBillRateView.getDtfLastUpdate().setValue(customerBillRateBean_.getLastUpdate());
 


 	customerBillRateView.getTxtUserId().setValue(customerBillRateBean_.getUserId());
 


	customerBillRateView.getNbrCustomerId().setValue(customerBillRateBean_.getCustomerId());
 


	customerBillRateView.getNbrClientId().setValue(customerBillRateBean_.getClientId());
 


	customerBillRateView.getNbrCustomerBillRateId().setValue(customerBillRateBean_.getCustomerBillRateId());
 


  }


}


