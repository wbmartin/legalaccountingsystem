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
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.ServerExcpetionHandler;
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
public class LoginController implements   AppEventListener{
	private LoginView loginView; //contains the View for entering login credentials
	private static LoginController instance = null; // Singleton instance
	private final ApplicationSecurityServiceAsync applicationSecurityService = 
		GWT.create(ApplicationSecurityService.class); // Remote GWT Service
	private UserProfile userProfile; //User properties, not a direct DB bean
	private MasterController masterController; //connects all the controllers
	private AppNotifyObject notifier;
	
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
		notifier = new AppNotifyObject();
	}
	/**
	 * Get Method for LoginView, primarily used to get members
	 * @return
	 */
	public  LoginView getLoginView(){
		return loginView;
	}

	

	/**
	 * Retrieves login credentials from LoginView and verifies access to the system
	 * Even if this is hacked, the user will have to have a valid session and a
	 * valid username for each database call.  The web browser security exposes to many
	 * gaps to completely rely on this setting.
	 */
	private void attemptAuthorization(final String type){
		//TODO handle different subsequent Logins
		final java.util.Date startTime = new java.util.Date();
		String userId;
		String passwd;
		if(type.equals("INITIAL")){
			loginView.getInitialLoginFormPanel().getSendButton().setEnabled(false);
			userId = loginView.getInitialLoginFormPanel().getTxtUserId().getValue();
			passwd = loginView.getInitialLoginFormPanel().getTxtPassword().getValue();
		}else{
			userId = loginView.getSubLoginFormPanel().getTxtUserId().getValue();
			passwd = loginView.getSubLoginFormPanel().getTxtPassword().getValue();
		}

		
		
//		if(AppPref.TEST_MODE ){
//			userId ="testboger";
//			passwd ="test";
//		}
		masterController.getAppContainer().setStatusMessage("Attempting Login");
		applicationSecurityService.authenticateUser( userId, passwd,
				new AsyncCallback<SecurityUserBean>(){
					public void onFailure(Throwable caught) {
						Log.debug("authenticateUser Failed: " + caught);
						clearLoginFormPanel();
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						
						if(type.equals("INITIAL")){
							loginView.getInitialLoginFormPanel().getSendButton().setEnabled(true);
						}
						
						masterController.getAppContainer().setTransactionResults("Login Request Failed", (new java.util.Date().getTime() -startTime.getTime()));
						
					}
		
					public void onSuccess(SecurityUserBean result) {
						Log.debug("AttemptAuthorization.onSuccess received: " + result);
						if(type.equals("INITIAL")){
							loginView.getInitialLoginFormPanel().getSendButton().setEnabled(true);
						}
						if (result.getClientId() !=0 && result.getSessionId() !=null){
							userProfile.setUserId(result.getUserId());
							userProfile.incrementSessionTimeOut();
							userProfile.setSessionId(result.getSessionId());
							userProfile.setClientId(result.getClientId());
							
							masterController.getAppContainer().setTransactionResults(
									"Successful Login"
									, (new java.util.Date().getTime() -startTime.getTime()));
							notifier.notifyAppEvent(this, "SuccessfulLogin");
							clearLoginFormPanel();	
							if(type.equals("INITIAL"))	{
								Log.debug("Requesting First Page History Change");
								History.newItem(AppPages.FIRST_PAGE);
							}else{
								masterController.secondaryLoginAccepted();
							}
								
						}else{
							masterController.notifyUserOfSystemError("Sorry...","I couldn't validate your credentials.  Please try again.");
							if(type.equals("INITIAL")){
								loginView.getInitialLoginFormPanel().getSendButton().setEnabled(true);
							}
							masterController.getAppContainer().setTransactionResults(
									"Login Denied"
									, (new java.util.Date().getTime() - startTime.getTime()));
						}
					}
		});
	}
	
	public void clearLoginFormPanel(){
		loginView.getSubLoginFormPanel().getTxtPassword().setValue("");
		loginView.getSubLoginFormPanel().getTxtUserId().setValue("");
	}
	/**
	 * Handles custom event system events,  all type casting of payloads should happen here.
	 */
	@Override
	public void onAppEventNotify(AppEvent e_) {
		Log.debug("LoginController Recieved " + e_.getName());
		if(e_.getName().equals("AttemptLogin")){
			attemptAuthorization("INITIAL");
		}if(e_.getName().equals("AttemptSubsequentLogin")){
			attemptAuthorization("SUBSEQUENT");
		}
	
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
