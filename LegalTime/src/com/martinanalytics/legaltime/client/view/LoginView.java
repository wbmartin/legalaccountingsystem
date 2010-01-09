package com.martinanalytics.legaltime.client.view;




import com.allen_sauer.gwt.log.client.Log;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
//import com.extjs.gxt.ui.client.widget.Window;

//import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;



import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.widget.ReportUtil;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;

import com.extjs.gxt.ui.client.event.ComponentEvent;

public class LoginView extends AppEventProducer{

	
	private final Label textToServerLabel = new Label();
	private final HTML serverResponseLabel = new HTML();
	private AppNotifyObject notifier;
	//private MasterFileBar masterFileBar = MasterFileBar.getInstance();
	
	private LoginViewComposite loginViewComposite;
	private final Dialog loginDialog = new Dialog();
	private final LoginFormPanel initialLoginFormPanel;
	private final LoginFormPanel subLoginFormPanel;

	
	public LoginView(){
		initialLoginFormPanel = new LoginFormPanel("INITIAL");
		subLoginFormPanel = new LoginFormPanel("SUB"); 
		
		establishInitialListeners();
		//subLoginFormPanel.getSendButton().setVisible(false);
		notifier = new AppNotifyObject();
		loginViewComposite = new LoginViewComposite();
		loginViewComposite.createView();
		loginDialog.setWidth(405);
		loginDialog.setButtons(Dialog.OK);
		loginDialog.add(subLoginFormPanel );
		//loginDialog.add(loginFormPanel);
		loginDialog.getButtonById(Dialog.OK).addListener(Events.Select, new Listener<ComponentEvent>() {
		      public void handleEvent(ComponentEvent be) {
		          notifier.notifyAppEvent(this, "AttemptSubsequentLogin");
		        }
		      });
		loginDialog.setClosable(false);
		loginDialog.setHideOnButtonClick(false);
		loginDialog.setModal(true);
		
		
		
		
		
		
	}
	private void establishInitialListeners(){
		initialLoginFormPanel.getSendButton().addListener(Events.Select, new Listener<ComponentEvent>() {
		      public void handleEvent(ComponentEvent be) {
		          notifier.notifyAppEvent(this, "AttemptLogin");
		        }
		      });
		initialLoginFormPanel.getTxtUserId().addKeyListener(new KeyListener(){		
			 public void componentKeyUp(ComponentEvent event) {
				 Log.debug("keyCode: " + event.getKeyCode());
			        if(event.getKeyCode() == 13){
			        	initialLoginFormPanel.getTxtPassword().setCursorPos(0);	        	
			        }
			      }
		});
		
		initialLoginFormPanel.getTxtPassword().addKeyListener(new KeyListener(){		
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
		
		VerticalPanel vpPrimary = new VerticalPanel();
		vpPrimary.setStyleName("LoginPanel");
		vpPrimary.setStyleName("CENTER");
		vpPrimary.add(new HTML("<h1 id ='ApplicationTitle'>" + AppPref.APPLICATION_NAME +" </h1>"));

		ContentPanel cp = new ContentPanel(new CenterLayout());
		cp.setBorders(false);
		cp.setBodyBorder(false);
		cp.setHeaderVisible(false);
		vpPrimary.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpPrimary.setWidth("100%");
		cp.add(initialLoginFormPanel);
		vpPrimary.add(cp);
		initWidget(vpPrimary);
		//loginDialog.show();
	}
	public void onAttach(){
		Log.debug(AppPages.LOGIN_PAGE +"OnAttach");
		super.onAttach();
		notifyAppEvent(this, AppPages.LOGIN_PAGE +"OnAttach");
	}
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
/**
 * @return the loginDialog
 */
public Dialog getLoginDialog() {
	
	return loginDialog;
}




/**
 * @return the initialLoginFormPanel
 */
public LoginFormPanel getInitialLoginFormPanel() {
	return initialLoginFormPanel;
}




/**
 * @return the subLoginFormPanel
 */
public LoginFormPanel getSubLoginFormPanel() {
	return subLoginFormPanel;
}

}

