package com.martinanalytics.legaltime.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.martinanalytics.legaltime.client.controller.MasterController;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LegalTime implements EntryPoint {
	public static String CUR_THEME = AppPref.STYLE_THEMES[0];
	MasterController masterController;
	UserProfile userProfile;
	public LegalTime() {
		masterController = new MasterController();
		userProfile = UserProfile.getInstance();
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Log.setUncaughtExceptionHandler();
		Log.debug("This is a 'DEBUG' test message");
		String initToken = History.getToken();
		if (initToken.length() == 0) {
			History.newItem(AppPages.LOGIN_PAGE);
		}
		final ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				Log.debug("History Change: " + event.getValue());
				if( event.getValue().equals(AppPages.LOGIN_PAGE)){
					
					Log.debug("Recieved SessionID: "+userProfile.getSessionId()+ " for event" + event.getValue() );
					RootPanel.get().clear();
					RootPanel.get().add(masterController.getPage(AppPages.LOGIN_PAGE));
				} else	if ((event == null ||!userProfile.isValidSession()) ) {
					Log.debug("invalid Session.  event:"+ event.getValue() + " sessionid: " + userProfile.getSessionId());
					History.newItem(AppPages.LOGIN_PAGE);
					
				}  else{
					
					Log.debug("Non Login Page Recieved: "+ event.getValue());
					if (!masterController.getAppContainer().isAttached()){
							Log.debug("Attaching Filebar");
							RootPanel.get().clear();
							RootPanel.get().add(masterController.getAppContainer());
					}
					masterController.getAppContainer().getMainPanel().clear();
					
					masterController.getAppContainer().getMainPanel().add(masterController.getPage(event.getValue()));
					//RootPanel.get().add(masterController.getAppContainer());								
				}

				

			}
		};
		History.addValueChangeHandler(historyHandler);
		History.fireCurrentHistoryState();
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("LOADER"));
	}

}
