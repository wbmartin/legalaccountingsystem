package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.model.ApplicationSecurityService;
import com.martinanalytics.legaltime.client.model.ApplicationSecurityServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.SecurityUserBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.view.LoginView;

/**
 * Login Controller owns the Login view and responds to login events upon a 
 * successful call to applicationSecurityService.authenticateUser the controller
 * will forward the browser to the first page defined in App Pages
 * @author bmartin
 *
 */
public class LoginController implements ClickHandler, KeyUpHandler, AppEventListener{
	private LoginView loginView; //contains the View for entering login credentials
	private static LoginController instance = null; // Singleton instance
	private final ApplicationSecurityServiceAsync applicationSecurityService = 
		GWT.create(ApplicationSecurityService.class); // Remote GWT Service
	private UserProfile userProfile; //User properties, not a direct DB bean
	private MasterController masterController; //connects all the controllers
	
	/**
	 * Singleton getInstance
	 * @param masterController_ the overarching controller
	 * @return a Login Conroller reference
	 */
	public static LoginController getInstance(MasterController masterController_){
		if (instance == null){
			instance = new LoginController(masterController_);
		}
		return instance;
	}
	/**
	 * Primary constructor, only called by getInstance, hence protected
	 * @param masterController_
	 */
	protected LoginController(MasterController masterController_){
		masterController = masterController_;
		loginView = new LoginView();
		//loginView.getSendButton().addClickHandler(this);
		loginView.getNotifier().addAppEventListener(this);
		//loginView.getTxtUserId().addKeyUpHandler(this);
		loginView.addAppEventListener(this);
		userProfile = UserProfile.getInstance();
	}
	/**
	 * Get Method for LoginView, primarily used to get members
	 * @return
	 */
	public  LoginView getLoginView(){
		return loginView;
	}
	/**
	 * Handles onClick actions from LoginView
	 */
	public void onClick(ClickEvent event) {
		//sendNameToServer();
		if (event.getSource() == loginView.getSendButton() ){
			attemptAuthorization();
		}
	}
	
	/**
	 * Handles keyboard actions from LoginView
	 */
	public void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			//sendNameToServer();
		}
	}

	/**
	 * Retrieves login credentials from LoginView and verifies access to the system
	 * Even if this is hacked, the user will have to have a valid session and a
	 * valid username for each database call.  The web browser security exposes to many
	 * gaps to completely rely on this setting.
	 */
	private void attemptAuthorization(){
		final java.util.Date startTime = new java.util.Date();
		loginView.getSendButton().setEnabled(false);
		String userId;
		String passwd;
		
		userId = loginView.getTxtUserId().getValue();
		passwd = loginView.getTxtPassword().getValue();
		if(AppPref.TEST_MODE ){
			userId ="bmartin";
			passwd ="test";
		}
		masterController.getAppContainer().setStatusMessage("Attempting Login");
		applicationSecurityService.authenticateUser( userId, passwd,
				new AsyncCallback<SecurityUserBean>(){
					public void onFailure(Throwable caught) {
						Log.debug("authenticateUser Failed: " + caught);
						
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						loginView.getSendButton().setEnabled(true);
						masterController.getAppContainer().setTransactionResults("Login Request Failed", (new java.util.Date().getTime() -startTime.getTime()));
						
					}
		
					public void onSuccess(SecurityUserBean result) {
						Log.debug("AttemptAuthorization.onSuccess received: " + result);
						if (result.getClientId() !=0 && result.getSessionId() !=null){
							userProfile.setUserId(result.getUserId());
							userProfile.incrementSessionTimeOut();
							userProfile.setSessionId(result.getSessionId());
							userProfile.setClientId(result.getClientId());
							Log.debug("Requesting First Page History Change");
							History.newItem(AppPages.FIRST_PAGE);
							masterController.getAppContainer().setTransactionResults(
									"Successful Login"
									, (new java.util.Date().getTime() -startTime.getTime()));

								
						}else{
							masterController.notifyUserOfSystemError("Sorry...","I couldn't validate your credentials.  Please try again.");
							loginView.getSendButton().setEnabled(true);
							masterController.getAppContainer().setTransactionResults(
									"Login Denied"
									, (new java.util.Date().getTime() - startTime.getTime()));
						}
					}
		});
	}
	/**
	 * Handles custom event system events,  all type casting of payloads should happen here.
	 */
	@Override
	public void onAppEventNotify(AppEvent e_) {
		Log.debug("LoginController Recieved " + e_.getName());
		if(e_.getName().equals("AttemptLogin")){
			attemptAuthorization();
		}
	
	}
	

}
