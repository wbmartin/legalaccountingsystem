package com.martinanalytics.legaltime.client.controller;

import java.util.HashMap;
import java.util.Map;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;

import com.martinanalytics.legaltime.client.view.LoginView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
public class MasterController implements AppEventListener{
	 private Map<String, Composite> itemWidgets = new HashMap<String, Composite>();
	 private LoginController loginController ;
	 private UserProfile userProfile;



	 private AppContainer appContainer ;
	 private CustomerController customerController;
	 private VwCustomerHourlyBillRateController vwCustomerHourlyBillRateController;
	 private CustomerBillRateController customerBillRateController;
	 private FollowupController followupController;
	
	 
	 public MasterController(){
		 userProfile = UserProfile.getInstance();
		 loginController = LoginController.getInstance(this);
		 itemWidgets.put(AppPages.LOGIN_PAGE, loginController.getLoginView().getLoginViewComposite());
	

		 
		 appContainer = AppContainer.getInstance();
		 appContainer.getNotifier().addAppEventListener(this);
		 
		 customerController =  CustomerController.getInstance(this);
		 itemWidgets.put(AppPages.CUSTOMER_PAGE, customerController.getCustomerView().getCustomerComposite());
		 vwCustomerHourlyBillRateController =  VwCustomerHourlyBillRateController.getInstance(this);
		 itemWidgets.put(AppPages.VW_USER_GRANT_PAGE, vwCustomerHourlyBillRateController.getVwCustomerHourlyBillRateView().getVwCustomerHourlyBillRateComposite());

		 customerBillRateController =  CustomerBillRateController.getInstance(this);
		 itemWidgets.put(AppPages.CUSTOMER_BILL_RATE_PAGE, customerBillRateController.getCustomerBillRateView().getCustomerBillRateComposite());

		 followupController =  FollowupController.getInstance(this);
		 itemWidgets.put(AppPages.FOLLOWUP_PAGE, followupController.getFollowupView().getFollowupComposite());
			 
	 }
	 
	 public Composite getPage(String page_){
		 String finalPage =page_;
		 return itemWidgets.get(finalPage);
		 
	 }
	
	 public void notifyUserOfSystemError(String title_, String msg_){
		 Log.debug("Calling Set Message");		
		 appContainer.displayPopupMsg(title_, msg_);
			
		}

	public void passValue(String sessionId_) {
		//page1Controller.passValue(sessionId_);
		
	}

	@Override
	public void onAppEventNotify(AppEvent e_) {
		Log.debug("Master Controller received AppEvent: " + e_.getName());
		if(e_.getName().equals(AppMsg.LOGOUT_COMMAND)){
			userProfile.setSessionId("");
			userProfile.setClientId(0);
			userProfile.expireSession();
			loginController.getLoginView().getSendButton().setEnabled(true);
			History.newItem(AppPages.LOGIN_PAGE);
			
		}else if(e_.getName().equals("REQUEST_SCROLL_TO_TOP")){
			//appContainer.getMainPanel().setScrollPosition(0);
			//Log.debug("Scroll Postion: " + appContainer.getMainPanel().getScrollPosition());
		}else{
			Log.debug("Unexpected App Message" + e_.getName());
		}
		
	}



	

	/**
	 * @return the masterFileBar2
	 */
	public AppContainer getAppContainer() {
		return appContainer;
	}

	
		
}
