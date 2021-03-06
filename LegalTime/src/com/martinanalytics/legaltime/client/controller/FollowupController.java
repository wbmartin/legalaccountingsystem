
package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.ServerExcpetionHandler;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.FollowupService;
import com.martinanalytics.legaltime.client.model.FollowupServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.view.FollowupView;
import com.martinanalytics.legaltime.client.view.table.FollowupTableCustomerPerspective;

import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * FollowupController owns the Followup View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class FollowupController implements AppEventListener, ClickHandler, ChangeHandler{
  private static FollowupController instance = null; //Singleton Instance
  private FollowupView followupView; 	//The UI container
  private final FollowupServiceAsync followupService = 
	GWT.create(FollowupService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
  private FollowupTableCustomerPerspective followupTableCustomerPerspective;
	final Dialog followupEditorDialog = new Dialog();
	private final AppNotifyObject notifier = new AppNotifyObject();
	private String owner;
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  FollowupController(MasterController masterController_){
	masterController =masterController_;
	followupView = new FollowupView();	
	followupView.addAppEventListener(this);
	//followupView.getFollowupTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
	followupTableCustomerPerspective = new FollowupTableCustomerPerspective();
	notifier.addAppEventListener(followupTableCustomerPerspective);
	followupTableCustomerPerspective.getNotifier().addAppEventListener(this);
	followupTableCustomerPerspective.setFollowupView(followupView);
	createFollowupEditorDialog();
  }

private void createFollowupEditorDialog() {
	//
	  BorderLayout layout = new BorderLayout(); 
	  followupEditorDialog.setLayout(layout);
	  followupEditorDialog.add(followupView.getFollowupFormPanel());
  
	  followupEditorDialog.setWidth(500);
	  followupEditorDialog.setModal(true);
	  followupEditorDialog.setButtons(Dialog.OKCANCEL);
	  followupEditorDialog.setHideOnButtonClick(true);
	  followupEditorDialog.setClosable(false);
	  followupEditorDialog.getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() { 

		  @Override  
		  public void componentSelected(ButtonEvent ce) { 
			  notifier.notifyAppEvent(this, AppMsg.FOLLOWUP_EDITOR_CLOSING, owner);
			  notifier.notifyAppEvent(this, AppMsg.FOLLOWUP_EDITOR_CANCELED,owner);
			 
		  }
	  }

	  );
	  followupEditorDialog.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() { 

		  @Override  
		  public void componentSelected(ButtonEvent ce) { 
			  //grid.getSelectionModel().getSelectedItem().setAssignedUserId(followupView.getCboAssignedUserId().getRawValue());
			  
			  //Record record = store.getRecord(grid.getSelectionModel().getSelectedItem());
		      //  record.set("assignedUserId",followupView.getCboAssignedUser().getSelectedValue());
			  notifier.notifyAppEvent(this, AppMsg.FOLLOWUP_EDITOR_CLOSING, owner);
		  }
	  }

	  );
	
}

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static FollowupController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new FollowupController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public FollowupView getFollowupView() {
	return followupView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("FollowupViewOnAttach")){
		
	}else if(e_.getName().equals("FollowupViewOnDetach")){
	}else if(e_.getName().equals(AppMsg.SHOW_FOLLOWUP_EDITOR)){
		
		Log.debug("FollowupController: " + owner);
		showFollowupViewDialog((String)e_.getPayLoad());
	}else if(e_.getName().equals("FollowupTableOnAttach")){		
	}else if(e_.getName().equals("FollowupTableOnDetach")){
	}else if(e_.getName().equals("NoCustomerSelected")){	
		masterController.notifyUserOfSystemError("No Customer Selected", "Please select a customer");
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

public void showFollowupViewDialog(String owner_) {
	owner = owner_;
	if (owner.equals("CUSTOMER")){
		followupView.getCboCustomerId().disable();
	}else{
		followupView.getCboCustomerId().enable();
	}
	followupEditorDialog.show();
	
}

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("FollowupController on click called");
	
	
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
 private void selectFollowupBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		followupService.selectFollowup(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<FollowupBean>>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController.getAppContainer().setTransactionResults(
							"Retrieving Followup Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

					}
		
					public void onSuccess(ArrayList<FollowupBean> followupResult) {
						masterController.getAppContainer().addSysLogMessage("Select Followup received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved Followup listing"
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
 private void retrieveFollowupBeanByPrKey( Integer followupId_ ){
	final java.util.Date startTime = new java.util.Date();
		followupService.getFollowupByPrKey(userProfile,  followupId_ , 
				new AsyncCallback<FollowupBean>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController.getAppContainer().setTransactionResults(
							"Retrieving Followup by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));

					}
		
					public void onSuccess(FollowupBean followupResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve Followup by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved Followup by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param followupBean_ the bean to insert into the database
 */
  private void insertFollowupBean(final FollowupBean followupBean_){
	final java.util.Date startTime = new java.util.Date();
	followupService.insertFollowupBean(userProfile,followupBean_,
			new AsyncCallback<FollowupBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("followupService.insertFollowup Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Adding Followup Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + followupBean_);

				}
	
				public void onSuccess(FollowupBean result) {
					Log.debug("followupService.insertFollowup onSuccess: " + result);
					if (  result.getFollowupId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted Followup record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "followup.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param followupBean_ the bean to update the database
 */
   private void updateFollowupBean(final FollowupBean followupBean_){
	final java.util.Date startTime = new java.util.Date();
	followupService.updateFollowupBean(userProfile,followupBean_,
			new AsyncCallback<FollowupBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("followupService.updateFollowup Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Updating Followup Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + followupBean_);

				}
	
				public void onSuccess(FollowupBean result) {
					Log.debug("followupService.updateFollowup onSuccess: " + result);
					if (  result.getFollowupId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated Followup record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "followup record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param followupBean_ the bean to delete the database
 */
 private void deleteFollowupBean(final FollowupBean followupBean_){
	final java.util.Date startTime = new java.util.Date();
	followupService.deleteFollowupBean(userProfile,followupBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Deleting Followup Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + followupBean_);

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("followupService.deleteFollowup onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted Followup record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  followupBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "followup record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param followupBean_ the bean to save to the database
 */
  private void saveFollowupBean(final FollowupBean followupBean_){
	final java.util.Date startTime = new java.util.Date();
	followupService.saveFollowupBean(userProfile,followupBean_,
			new AsyncCallback<FollowupBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("followupService.saveFollowup Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving Followup Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + followupBean_);

				}
	
				public void onSuccess(FollowupBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("followupService.saveFollowup onSuccess: " + result);
					if (  result.getFollowupId() !=null && result.getClientId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved Followup record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  followupBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "followup record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param followupBean_ the bean to save to the database
 */
  private void saveFollowupBeanBatch(ArrayList<FollowupBean> followupBeanList_){
	final java.util.Date startTime = new java.util.Date();
	followupService.saveFollowupBeanBatch(userProfile, followupBeanList_, 
			new AsyncCallback<ArrayList<FollowupBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("followupService.saveFollowupBeanBatch Failed: " + caught);
					if(!ServerExcpetionHandler.getInstance().handle(caught)){

					}
					masterController.getAppContainer().setTransactionResults(
						"Saving Followup Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<FollowupBean> followupResult) {
					Log.debug("followupService.saveFollowupBeanBatch onSuccess: " + followupResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved Followup Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }


/**
 * @param followupTableCustomerPerspective the followupTableCustomerPerspective to set
 */
public void setFollowupTableCustomerPerspective(
		FollowupTableCustomerPerspective followupTableCustomerPerspective) {
	this.followupTableCustomerPerspective = followupTableCustomerPerspective;
}

/**
 * @return the followupTableCustomerPerspective
 */
public FollowupTableCustomerPerspective getFollowupTableCustomerPerspective() {
	return followupTableCustomerPerspective;
}

/**
 * @return the notifier
 */
public AppNotifyObject getNotifier() {
	return notifier;
}

/**
 * @param owner the owner to set
 */
public void setOwner(String owner) {
	this.owner = owner;
}

/**
 * @return the owner
 */
public String getOwner() {
	return owner;
}
}


