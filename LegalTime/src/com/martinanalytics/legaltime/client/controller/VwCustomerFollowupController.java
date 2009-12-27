
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
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.FollowupService;
import com.martinanalytics.legaltime.client.model.FollowupServiceAsync;
import com.martinanalytics.legaltime.client.model.VwCustomerFollowupService;
import com.martinanalytics.legaltime.client.model.VwCustomerFollowupServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.view.VwCustomerFollowupView;
import com.martinanalytics.legaltime.client.view.table.VwCustomerFollowupTable;

import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * VwCustomerFollowupController owns the VwCustomerFollowup View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class VwCustomerFollowupController implements AppEventListener, ClickHandler, ChangeHandler{
  private static VwCustomerFollowupController instance = null; //Singleton Instance
  private VwCustomerFollowupView vwCustomerFollowupView; 	//The UI container
  private final VwCustomerFollowupServiceAsync vwCustomerFollowupService = 
	GWT.create(VwCustomerFollowupService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private FollowupController followupController;
  private VwCustomerFollowupTable vwCustomerFollowupTable;
  private final FollowupServiceAsync followupService = 
		GWT.create(FollowupService.class);

/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  VwCustomerFollowupController(MasterController masterController_){
	masterController =masterController_;
	vwCustomerFollowupTable = new VwCustomerFollowupTable();
	vwCustomerFollowupTable.getNotifier().addAppEventListener(this);
	vwCustomerFollowupView = new VwCustomerFollowupView(vwCustomerFollowupTable);	
	vwCustomerFollowupView.addAppEventListener(this);
	
	//vwCustomerFollowupView.getVwCustomerFollowupTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
	followupController = FollowupController.getInstance(masterController_);
	vwCustomerFollowupTable.setFollowupView(followupController.getFollowupView());
	followupController.getNotifier().addAppEventListener(vwCustomerFollowupTable);
	
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static VwCustomerFollowupController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new VwCustomerFollowupController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public VwCustomerFollowupView getVwCustomerFollowupView() {
	return vwCustomerFollowupView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
    if (e_.getName().equals("VwCustomerFollowupViewOnAttach")){
        	 selectVwCustomerFollowupBeans("", "");
    }else if(e_.getName().equals(AppMsg.SHOW_FOLLOWUP_EDITOR)){
    		followupController.showFollowupViewDialog("MANAGER");
    		
    }else if(e_.getName().equals("SaveChangesToVWCustomerFollowupTable")){
    	
	}else if(e_.getName().equals("VwCustomerFollowupViewOnDetach")){
	}else if(e_.getName().equals("VwCustomerFollowupTableOnAttach")){		
	}else if(e_.getName().equals("VwCustomerFollowupTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("VwCustomerFollowupController on click called");
	
	
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
 private void selectVwCustomerFollowupBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		vwCustomerFollowupService.selectVwCustomerFollowup(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<VwCustomerFollowupBean>>(){
					public void onFailure(Throwable caught) {
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						masterController.getAppContainer().setTransactionResults(
							"Retrieving VwCustomerFollowup Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

					}
		
					public void onSuccess(ArrayList<VwCustomerFollowupBean> vwCustomerFollowupResult) {
						masterController.getAppContainer().addSysLogMessage("Select VwCustomerFollowup received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved VwCustomerFollowup listing"
							, (new java.util.Date().getTime() - startTime.getTime()));
						vwCustomerFollowupTable.setList(vwCustomerFollowupResult);
						
					}
		});
	  }




//
///**
// * Provides a standard template to retrieve a bean from the server by primary keys.  
// * The results are handled through the onSuccess method in the AsynchCallback.
// * this function also uses the userProfile Singleton to send authorization credentials.
// * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
// * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
// */
// private void retrieveVwCustomerFollowupBeanByPrKey(){
//	final java.util.Date startTime = new java.util.Date();
//		vwCustomerFollowupService.getVwCustomerFollowupByPrKey(userProfile, , 
//				new AsyncCallback<VwCustomerFollowupBean>(){
//					public void onFailure(Throwable caught) {
//						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//								AppPref.SERVER_ERROR + caught.getMessage());
//						masterController.getAppContainer().setTransactionResults(
//							"Retrieving VwCustomerFollowup by Identifier Failed"
//							, (new java.util.Date().getTime() -startTime.getTime()));
//
//					}
//		
//					public void onSuccess(VwCustomerFollowupBean vwCustomerFollowupResult) {
//						masterController.getAppContainer().addSysLogMessage("Retrieve VwCustomerFollowup by identifier received ok");
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully Retrieved VwCustomerFollowup by Primary Key"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						
//					}
//		});
//	  }
//
//
//
//
///**
// * Provides standard mechanism to add a bean to the database
// * The results are handled through the onSuccess method in the AsynchCallback.
// * @param vwCustomerFollowupBean_ the bean to insert into the database
// */
//  private void insertVwCustomerFollowupBean(final VwCustomerFollowupBean vwCustomerFollowupBean_){
//	final java.util.Date startTime = new java.util.Date();
//	vwCustomerFollowupService.insertVwCustomerFollowupBean(userProfile,vwCustomerFollowupBean_,
//			new AsyncCallback<VwCustomerFollowupBean>(){
//				public void onFailure(Throwable caught) {
//					Log.debug("vwCustomerFollowupService.insertVwCustomerFollowup Failed: " + caught);
//					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//							AppPref.SERVER_ERROR + caught.getMessage());
//					masterController.getAppContainer().setTransactionResults(
//						"Adding VwCustomerFollowup Failed"
//						, (new java.util.Date().getTime() -startTime.getTime()));
//					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + vwCustomerFollowupBean_);
//
//				}
//	
//				public void onSuccess(VwCustomerFollowupBean result) {
//					Log.debug("vwCustomerFollowupService.insertVwCustomerFollowup onSuccess: " + result);
//					if (){
//						userProfile.incrementSessionTimeOut();
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully inserted VwCustomerFollowup record"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
//			
//					}else{
//						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
//						+ "vwCustomerFollowup.  This is an unexpected error, please go to Help > View Log and send "
//						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
//					}
//				}
//	});
//  }
// 
///**
// * Provides standard mechanism to update a bean in the database
// * The results are handled through the onSuccess method in the AsynchCallback.
// * @param vwCustomerFollowupBean_ the bean to update the database
// */
//   private void updateVwCustomerFollowupBean(final VwCustomerFollowupBean vwCustomerFollowupBean_){
//	final java.util.Date startTime = new java.util.Date();
//	vwCustomerFollowupService.updateVwCustomerFollowupBean(userProfile,vwCustomerFollowupBean_,
//			new AsyncCallback<VwCustomerFollowupBean>(){
//				public void onFailure(Throwable caught) {
//					Log.debug("vwCustomerFollowupService.updateVwCustomerFollowup Failed: " + caught);
//					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//							AppPref.SERVER_ERROR + caught.getMessage());
//					masterController.getAppContainer().setTransactionResults(
//						"Updating VwCustomerFollowup Failed"
//						, (new java.util.Date().getTime() -startTime.getTime()));
//					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + vwCustomerFollowupBean_);
//
//				}
//	
//				public void onSuccess(VwCustomerFollowupBean result) {
//					Log.debug("vwCustomerFollowupService.updateVwCustomerFollowup onSuccess: " + result);
//					if (){
//						userProfile.incrementSessionTimeOut();
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully updated VwCustomerFollowup record"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
//					}else{
//						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
//						+ "vwCustomerFollowup record.  This can be caused by someone else changing the record.  Please try"
//						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
//						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
//					}
//				}
//	});
//  }
//
///**
// * Provides standard mechanism to delete a bean in the database
// * The results are handled through the onSuccess method in the AsynchCallback.
// * @param vwCustomerFollowupBean_ the bean to delete the database
// */
// private void deleteVwCustomerFollowupBean(final VwCustomerFollowupBean vwCustomerFollowupBean_){
//	final java.util.Date startTime = new java.util.Date();
//	vwCustomerFollowupService.deleteVwCustomerFollowupBean(userProfile,vwCustomerFollowupBean_,
//			new AsyncCallback<Boolean>(){
//				public void onFailure(Throwable caught) {
//					
//					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//							AppPref.SERVER_ERROR + caught.getMessage());
//					masterController.getAppContainer().setTransactionResults(
//						"Deleting VwCustomerFollowup Failed"
//						, (new java.util.Date().getTime() -startTime.getTime()));
//					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + vwCustomerFollowupBean_);
//
//				}
//	
//
//				public void onSuccess(Boolean result) {
//					Log.debug("vwCustomerFollowupService.deleteVwCustomerFollowup onSuccess: " + result);
//					if (result){
//						userProfile.incrementSessionTimeOut();
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully deleted VwCustomerFollowup record"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  vwCustomerFollowupBean_.toString());
//					}else{
//						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
//						+ "vwCustomerFollowup record.  This can be caused by someone else changing the record.  Please try"
//						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
//						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
//					}
//				}
//	});
//  }
//
///**
// * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
// * The results are handled through the onSuccess method in the AsynchCallback.
// * @param vwCustomerFollowupBean_ the bean to save to the database
// */
//  private void saveVwCustomerFollowupBean(final VwCustomerFollowupBean vwCustomerFollowupBean_){
//	final java.util.Date startTime = new java.util.Date();
//	vwCustomerFollowupService.saveVwCustomerFollowupBean(userProfile,vwCustomerFollowupBean_,
//			new AsyncCallback<VwCustomerFollowupBean>(){
//				public void onFailure(Throwable caught) {
//					Log.debug("vwCustomerFollowupService.saveVwCustomerFollowup Failed: " + caught);
//					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//							AppPref.SERVER_ERROR + caught.getMessage());
//					masterController.getAppContainer().setTransactionResults(
//						"Saving VwCustomerFollowup Failed"
//						, (new java.util.Date().getTime() -startTime.getTime()));
//					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + vwCustomerFollowupBean_);
//
//				}
//	
//				public void onSuccess(VwCustomerFollowupBean result) {
//					userProfile.incrementSessionTimeOut();
//					Log.debug("vwCustomerFollowupService.saveVwCustomerFollowup onSuccess: " + result);
//					if (){
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully saved VwCustomerFollowup record"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  vwCustomerFollowupBean_.toString());
//					}else{
//						
//						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
//						+ "vwCustomerFollowup record.  This can be caused by someone else changing the record.  Please try"
//						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
//						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
//					}
//				}
//	});
//  }
//
/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param vwCustomerFollowupBean_ the bean to save to the database
 */
  private void saveVwCustomerFollowupBeanBatch(ArrayList<VwCustomerFollowupBean> vwCustomerFollowupBeanList_){
	final java.util.Date startTime = new java.util.Date();
	ArrayList<FollowupBean> followupBeanList = new ArrayList<FollowupBean>();
	for(int ndx =0; ndx<vwCustomerFollowupBeanList_.size();ndx++){
		followupBeanList.add(vwCustomerFollowupBeanList_.get(ndx).getFollowupBean());
	}
		
	
	followupService.saveFollowupBeanBatch(userProfile, followupBeanList, 
			new AsyncCallback<ArrayList<FollowupBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("vwCustomerFollowupService.saveVwCustomerFollowupBeanBatch Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving VwCustomerFollowup Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<FollowupBean> followupResult) {
					//Log.debug("vwCustomerFollowupService.saveVwCustomerFollowupBeanBatch onSuccess: " + followupResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved VwCustomerFollowup Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }



}


