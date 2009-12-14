package com.martinanalytics.legaltime.client.view;




import com.allen_sauer.gwt.log.client.Log;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
//import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
		
		
		
		establishListeners();
		
	}
	private void establishListeners(){
		sendButton.addListener(Events.Select, new Listener<ComponentEvent>() {
		      public void handleEvent(ComponentEvent be) {
		          notifier.notifyAppEvent(this, "AttemptLogin");
		        }
		      });
		txtUserId.addKeyListener(new KeyListener(){		
			 public void componentKeyUp(ComponentEvent event) {
				 Log.debug("keyCode: " + event.getKeyCode());
			        if(event.getKeyCode() == 13){
			        	txtPassword.setCursorPos(0);	        	
			        }
			      }
		});
		
		txtPassword.addKeyListener(new KeyListener(){		
			 public void componentKeyUp(ComponentEvent event) {
				 Log.debug("keyCode: " + event.getKeyCode());
			        if(event.getKeyCode() == 13){
			        	notifier.notifyAppEvent(this, "AttemptLogin");	        	
			        }
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
		hpUserName.addStyleName("CENTER");
		Label lblUserName = new Label("Username: ");
		lblUserName.setWidth(labelWidth );
		hpUserName.add(lblUserName);
		txtUserId.setWidth(textFieldWidth);
		hpUserName.add(txtUserId);
		vpPrimary.add(hpUserName);
		HorizontalPanel hpPassword = new HorizontalPanel();
		hpPassword.addStyleName("CENTER");
		Label lblPassword = new Label("Password: ");
		lblPassword.setWidth(labelWidth );
		hpPassword.add(lblPassword);
		hpPassword.add(txtPassword);
		txtPassword.setWidth(textFieldWidth);
		vpPrimary.add(hpPassword);
		Label lblVertSpacer1 = new Label();
		lblVertSpacer1.setHeight("20px");
		vpPrimary.add(lblVertSpacer1);
		HorizontalPanel hpButtons = new HorizontalPanel();
		hpButtons.addStyleName("CENTER");
		hpButtons.setHorizontalAlign(HorizontalAlignment.CENTER);
		hpButtons.add(sendButton);
		sendButton.addStyleName("CENTER");
		vpPrimary.add(hpButtons);
		vpPrimary.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(vpPrimary);
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

}

