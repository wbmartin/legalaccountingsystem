package com.martinanalytics.legaltime.client.view;




import com.allen_sauer.gwt.log.client.Log;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;


import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;

import com.extjs.gxt.ui.client.event.ComponentEvent;

public class LoginView extends AppEventProducer{

	private final Button sendButton = new Button("Login");
	private final TextField<String> txtUserId = new TextField<String>();
	private final TextField<String> txtPassword = new TextField<String>();
	private final Label textToServerLabel = new Label();
	private final HTML serverResponseLabel = new HTML();
	private AppNotifyObject notifier;
	//private MasterFileBar masterFileBar = MasterFileBar.getInstance();
	
	private LoginViewComposite loginViewComposite;
	
	public LoginView(){
		
		notifier = new AppNotifyObject();
		loginViewComposite = new LoginViewComposite();
		loginViewComposite.createView();
		
		sendButton.addListener(Events.Select, new Listener<ComponentEvent>() {
		      public void handleEvent(ComponentEvent be) {
		          notifier.notifyAppEvent(this, "AttemptLogin");
		        }
		      });
		
		
	}
	
	public LoginViewComposite getLoginViewComposite(){
		return  loginViewComposite;
	}
	


private class LoginViewComposite extends Composite{
	public void createView(){
		txtPassword.setPassword(true);
		VerticalPanel vpPrimary = new VerticalPanel();
		vpPrimary.setStyleName("LoginPanel");
		sendButton.setStyleName("sendButton");

		String textFieldWidth = "200px"; 
		String labelWidth = "110px"; 
		vpPrimary.add(new HTML("<h1 id ='ApplicationTitle'>" + AppPref.APPLICATION_NAME +" </h1>"));
		HorizontalPanel hpUserName = new HorizontalPanel();
		Label lblUserName = new Label("Username: ");
		lblUserName.setWidth(labelWidth );
		hpUserName.add(lblUserName);
		txtUserId.setWidth(textFieldWidth);
		hpUserName.add(txtUserId);
		vpPrimary.add(hpUserName);
		HorizontalPanel hpPassword = new HorizontalPanel();
		Label lblPassword = new Label("Password: ");
		lblPassword.setWidth(labelWidth );
		hpPassword.add(lblPassword);
		hpPassword.add(txtPassword);
		txtPassword.setWidth(textFieldWidth);
		vpPrimary.add(hpPassword);
		HorizontalPanel hpButtons = new HorizontalPanel();
		Label lblButtonSpacer = new Label("");
		lblButtonSpacer.setWidth("152px");
		hpButtons.add(lblButtonSpacer);
		hpButtons.add(sendButton);
		vpPrimary.add(hpButtons);
		initWidget(vpPrimary);
		 
		//txtUserId.setFocus(true);

	}
	public void onAttach(){
		Log.debug(AppPages.LOGIN_PAGE +"OnAttach");
		super.onAttach();
		notifyAppEvent(this, AppPages.LOGIN_PAGE +"OnAttach");
		
	}
}


/**
 * @return the sendButton
 */
public Button getSendButton() {
	return sendButton;
}




/**
 * @return the txtUserId
 */
public TextField<String> getTxtUserId() {
	return txtUserId;
}




/**
 * @return the txtPassword
 */
public TextField<String> getTxtPassword() {
	return txtPassword;
}




/**
 * @return the textToServerLabel
 */
public Label getTextToServerLabel() {
	return textToServerLabel;
}




/**
 * @return the serverResponseLabel
 */
public HTML getServerResponseLabel() {
	return serverResponseLabel;
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




///**
// * @return the closeButton
// */
//public Button getCloseButton() {
//	return closeButton;
//}










}

