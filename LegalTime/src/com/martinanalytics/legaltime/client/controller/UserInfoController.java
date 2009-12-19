
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
import com.martinanalytics.legaltime.client.model.UserInfoService;
import com.martinanalytics.legaltime.client.model.UserInfoServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.view.UserInfoView;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * UserInfoController owns the UserInfo View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class UserInfoController implements AppEventListener, ClickHandler, ChangeHandler{
  private static UserInfoController instance = null; //Singleton Instance
  private UserInfoView userInfoView; 	//The UI container
  private final UserInfoServiceAsync userInfoService = 
	GWT.create(UserInfoService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  UserInfoController(MasterController masterController_){
	 
	masterController =masterController_;
	  
	userInfoView = new UserInfoView();	
	userInfoView.addAppEventListener(this);
	//userInfoView.getUserInfoTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static UserInfoController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new UserInfoController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public UserInfoView getUserInfoView() {
	return userInfoView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("UserInfoViewOnAttach")){
		
	}else if(e_.getName().equals("UserInfoViewOnDetach")){
	}else if(e_.getName().equals("UserInfoTableOnAttach")){		
	}else if(e_.getName().equals("UserInfoTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("UserInfoController on click called");
	
	
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
 private void selectUserInfoBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		userInfoService.selectUserInfo(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<UserInfoBean>>(){
					public void onFailure(Throwable caught) {
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						masterController.getAppContainer().setTransactionResults(
							"Retrieving UserInfo Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

					}
		
					public void onSuccess(ArrayList<UserInfoBean> userInfoResult) {
						masterController.getAppContainer().addSysLogMessage("Select UserInfo received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved UserInfo listing"
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
 private void retrieveUserInfoBeanByPrKey( String userId_ ){
	final java.util.Date startTime = new java.util.Date();
		userInfoService.getUserInfoByPrKey(userProfile,  userId_ , 
				new AsyncCallback<UserInfoBean>(){
					public void onFailure(Throwable caught) {
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						masterController.getAppContainer().setTransactionResults(
							"Retrieving UserInfo by Identifier Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));

					}
		
					public void onSuccess(UserInfoBean userInfoResult) {
						masterController.getAppContainer().addSysLogMessage("Retrieve UserInfo by identifier received ok");
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved UserInfo by Primary Key"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }




/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param userInfoBean_ the bean to insert into the database
 */
  private void insertUserInfoBean(final UserInfoBean userInfoBean_){
	final java.util.Date startTime = new java.util.Date();
	userInfoService.insertUserInfoBean(userProfile,userInfoBean_,
			new AsyncCallback<UserInfoBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("userInfoService.insertUserInfo Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Adding UserInfo Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + userInfoBean_);

				}
	
				public void onSuccess(UserInfoBean result) {
					Log.debug("userInfoService.insertUserInfo onSuccess: " + result);
					if (  result.getUserId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted UserInfo record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "userInfo.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param userInfoBean_ the bean to update the database
 */
   private void updateUserInfoBean(final UserInfoBean userInfoBean_){
	final java.util.Date startTime = new java.util.Date();
	userInfoService.updateUserInfoBean(userProfile,userInfoBean_,
			new AsyncCallback<UserInfoBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("userInfoService.updateUserInfo Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Updating UserInfo Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + userInfoBean_);

				}
	
				public void onSuccess(UserInfoBean result) {
					Log.debug("userInfoService.updateUserInfo onSuccess: " + result);
					if (  result.getUserId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated UserInfo record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "userInfo record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param userInfoBean_ the bean to delete the database
 */
 private void deleteUserInfoBean(final UserInfoBean userInfoBean_){
	final java.util.Date startTime = new java.util.Date();
	userInfoService.deleteUserInfoBean(userProfile,userInfoBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Deleting UserInfo Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + userInfoBean_);

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("userInfoService.deleteUserInfo onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted UserInfo record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  userInfoBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "userInfo record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param userInfoBean_ the bean to save to the database
 */
  private void saveUserInfoBean(final UserInfoBean userInfoBean_){
	final java.util.Date startTime = new java.util.Date();
	userInfoService.saveUserInfoBean(userProfile,userInfoBean_,
			new AsyncCallback<UserInfoBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("userInfoService.saveUserInfo Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving UserInfo Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + userInfoBean_);

				}
	
				public void onSuccess(UserInfoBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("userInfoService.saveUserInfo onSuccess: " + result);
					if (  result.getUserId() !=null && result.getClientId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved UserInfo record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  userInfoBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "userInfo record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param userInfoBean_ the bean to save to the database
 */
  private void saveUserInfoBeanBatch(ArrayList<UserInfoBean> userInfoBeanList_){
	final java.util.Date startTime = new java.util.Date();
	userInfoService.saveUserInfoBeanBatch(userProfile, userInfoBeanList_, 
			new AsyncCallback<ArrayList<UserInfoBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("userInfoService.saveUserInfoBeanBatch Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving UserInfo Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<UserInfoBean> userInfoResult) {
					Log.debug("userInfoService.saveUserInfoBeanBatch onSuccess: " + userInfoResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved UserInfo Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
			
				}
		});
  }

/**
 * updates the UI with the bean parameter
 */
  public void synchBeanToDisplay(UserInfoBean userInfoBean_){


    try{
	userInfoBean_.setEmailAddr(userInfoView.getTxtEmailAddr().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	userInfoBean_.setDisplayName(userInfoView.getTxtDisplayName().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	userInfoBean_.setDefaultBillRate((Double)userInfoView.getNbrDefaultBillRate().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	userInfoBean_.setLastUpdate(userInfoView.getDtfLastUpdate().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	userInfoBean_.setClientId((Integer)userInfoView.getNbrClientId().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	userInfoBean_.setUserId(userInfoView.getTxtUserId().getValue());
    }catch(Exception e){
		
    }
 




   
 

  }
/**
 * updates the bean parameter with values in the UI
 */
  public void synchDisplayToBean(UserInfoBean userInfoBean_){
 	userInfoView.getTxtEmailAddr().setValue(userInfoBean_.getEmailAddr());
 


 	userInfoView.getTxtDisplayName().setValue(userInfoBean_.getDisplayName());
 


	userInfoView.getNbrDefaultBillRate().setValue(userInfoBean_.getDefaultBillRate());	
 


	userInfoView.getDtfLastUpdate().setValue(userInfoBean_.getLastUpdate());
 


	userInfoView.getNbrClientId().setValue(userInfoBean_.getClientId());
 


 	userInfoView.getTxtUserId().setValue(userInfoBean_.getUserId());
 


  }


}


