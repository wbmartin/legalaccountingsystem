package com.martinanalytics.legaltime.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;


public class LoginFormPanel extends Composite{
	FormPanel loginPanel;
	private final Button sendButton = new Button("Login");
	private final TextField<String> txtUserId = new TextField<String>();
	private final TextField<String> txtPassword = new TextField<String>();
	private AppNotifyObject notifier = new AppNotifyObject();
	public LoginFormPanel(final String type){
		
		
		loginPanel = new FormPanel();
		loginPanel.setWidth(400);
		loginPanel.setHeading("Login...");
		sendButton.setStyleName("sendButton");
		txtUserId.setFieldLabel("User Name");
		txtUserId.setFieldLabel("User Name");
		loginPanel.add(txtUserId);
		
		
		txtPassword.setFieldLabel("Password");
		txtPassword.setPassword(true);
		loginPanel.add(txtPassword);

		if(type.equals("INITIAL")){
			loginPanel.addButton(sendButton);
		}
		loginPanel.setBorders(false);
		
		loginPanel.setButtonAlign(Style.HorizontalAlignment.CENTER);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(loginPanel);
		initWidget(vp);
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
}

