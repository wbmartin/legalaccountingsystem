
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
import com.martinanalytics.legaltime.client.model.ExpenseRegisterService;
import com.martinanalytics.legaltime.client.model.ExpenseRegisterServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.martinanalytics.legaltime.client.view.ExpenseRegisterView;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;
import com.extjs.gxt.ui.client.store.Record;
import java.util.List;



/**
 * ExpenseRegisterController owns the ExpenseRegister View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class ExpenseRegisterController implements AppEventListener, ClickHandler, ChangeHandler{
  private static ExpenseRegisterController instance = null; //Singleton Instance
  private ExpenseRegisterView expenseRegisterView; 	//The UI container
  private final ExpenseRegisterServiceAsync expenseRegisterService = 
	GWT.create(ExpenseRegisterService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  ExpenseRegisterController(MasterController masterController_){
	masterController =masterController_;
	expenseRegisterView = new ExpenseRegisterView();	
	expenseRegisterView.addAppEventListener(this);
	//expenseRegisterView.getExpenseRegisterTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static ExpenseRegisterController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new ExpenseRegisterController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public ExpenseRegisterView getExpenseRegisterView() {
	return expenseRegisterView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("ExpenseRegisterViewOnAttach")){
		
	}else if(e_.getName().equals("ExpenseRegisterViewOnDetach")){
	}else if(e_.getName().equals("ExpenseRegisterTableOnAttach")){		
	}else if(e_.getName().equals("ExpenseRegisterTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("ExpenseRegisterController on click called");
	
	
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
 private void selectExpenseRegisterBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		expenseRegisterService.selectExpenseRegister(userProfile, whereClause_, orderByClause_, 
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
 private void retrieveExpenseRegisterBeanByPrKey( Integer expenseRegisterId_ , Integer customerId_ ){
	final java.util.Date startTime = new java.util.Date();
		expenseRegisterService.getExpenseRegisterByPrKey(userProfile,  expenseRegisterId_ , customerId_ , 
				new AsyncCallback<ExpenseRegisterBean>(){
					public void onFailure(Throwable caught) {
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						masterController.getAppContainer().setTransactionResults(
							"Retrieving ExpenseRegister by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));

					}
		
					public void onSuccess(ExpenseRegisterBean expenseRegisterResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve ExpenseRegister by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved ExpenseRegister by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param expenseRegisterBean_ the bean to insert into the database
 */
  private void insertExpenseRegisterBean(final ExpenseRegisterBean expenseRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	expenseRegisterService.insertExpenseRegisterBean(userProfile,expenseRegisterBean_,
			new AsyncCallback<ExpenseRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("expenseRegisterService.insertExpenseRegister Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Adding ExpenseRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + expenseRegisterBean_);

				}
	
				public void onSuccess(ExpenseRegisterBean result) {
					Log.debug("expenseRegisterService.insertExpenseRegister onSuccess: " + result);
					if (  result.getExpenseRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted ExpenseRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "expenseRegister.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param expenseRegisterBean_ the bean to update the database
 */
   private void updateExpenseRegisterBean(final ExpenseRegisterBean expenseRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	expenseRegisterService.updateExpenseRegisterBean(userProfile,expenseRegisterBean_,
			new AsyncCallback<ExpenseRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("expenseRegisterService.updateExpenseRegister Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Updating ExpenseRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + expenseRegisterBean_);

				}
	
				public void onSuccess(ExpenseRegisterBean result) {
					Log.debug("expenseRegisterService.updateExpenseRegister onSuccess: " + result);
					if (  result.getExpenseRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated ExpenseRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "expenseRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param expenseRegisterBean_ the bean to delete the database
 */
 private void deleteExpenseRegisterBean(final ExpenseRegisterBean expenseRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	expenseRegisterService.deleteExpenseRegisterBean(userProfile,expenseRegisterBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Deleting ExpenseRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + expenseRegisterBean_);

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("expenseRegisterService.deleteExpenseRegister onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted ExpenseRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  expenseRegisterBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "expenseRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param expenseRegisterBean_ the bean to save to the database
 */
  private void saveExpenseRegisterBean(final ExpenseRegisterBean expenseRegisterBean_){
	final java.util.Date startTime = new java.util.Date();
	expenseRegisterService.saveExpenseRegisterBean(userProfile,expenseRegisterBean_,
			new AsyncCallback<ExpenseRegisterBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("expenseRegisterService.saveExpenseRegister Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving ExpenseRegister Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + expenseRegisterBean_);

				}
	
				public void onSuccess(ExpenseRegisterBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("expenseRegisterService.saveExpenseRegister onSuccess: " + result);
					if (  result.getExpenseRegisterId() !=null && result.getClientId() !=null && result.getCustomerId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved ExpenseRegister record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  expenseRegisterBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "expenseRegister record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param expenseRegisterBean_ the bean to save to the database
 */
  private void saveExpenseRegisterBeanBatch(ArrayList<ExpenseRegisterBean> expenseRegisterBeanList_){
	final java.util.Date startTime = new java.util.Date();
	expenseRegisterService.saveExpenseRegisterBeanBatch(userProfile, expenseRegisterBeanList_, 
			new AsyncCallback<ArrayList<ExpenseRegisterBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("expenseRegisterService.saveExpenseRegisterBeanBatch Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving ExpenseRegister Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<ExpenseRegisterBean> expenseRegisterResult) {
					Log.debug("expenseRegisterService.saveExpenseRegisterBeanBatch onSuccess: " + expenseRegisterResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved ExpenseRegister Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }

		


public void saveAllChanges(){

	  List<Record> modified =  expenseRegisterView.getStore().getModifiedRecords();
	  ExpenseRegisterBean expenseRegisterBean;// = new ExpenseRegisterBean();
	  ArrayList<ExpenseRegisterBean> batchSave = new ArrayList<ExpenseRegisterBean>();
	  for (Record r : modified) {
		 // Log.debug("Identified Modified Record");
		  expenseRegisterBean = new ExpenseRegisterBean();
		  expenseRegisterBean.setProperties(r.getModel().getProperties());
		  batchSave.add(expenseRegisterBean);
		  
		  
	  }
		 expenseRegisterView.getStore().commitChanges();
		saveExpenseRegisterBeanBatch( batchSave );
		
		
	  
  }



}


