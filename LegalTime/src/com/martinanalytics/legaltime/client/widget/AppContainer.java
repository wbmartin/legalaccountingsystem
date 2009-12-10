package com.martinanalytics.legaltime.client.widget;

/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */



import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;


public class AppContainer extends LayoutContainer {
	Listener<ComponentEvent> masterController;
	private AppNotifyObject notifier ;
	private ScrollPanel mainPanel ;


	final Dialog standardUserMessage = new Dialog();
	final Dialog logMessagesDialog = new Dialog();
	private static AppContainer instance;
    private Status userMessage;
    private Status lastTranTime;
    private Status versionStatus;

    private ArrayList<String> messageLog;
	protected AppContainer(){
		super();
		setStyleName("AppContainer");
		mainPanel = new ScrollPanel();
		
		
		notifier = new AppNotifyObject();
		standardUserMessage.setBodyStyleName("pad-text");
		standardUserMessage.setScrollMode(Scroll.AUTO);
		standardUserMessage.setHideOnButtonClick(true);
		standardUserMessage.setHeaderVisible(true);
		standardUserMessage.addStyleName("LEFT");
		
		logMessagesDialog.setBodyStyleName("pad-text");
		logMessagesDialog.setScrollMode(Scroll.AUTO);
		logMessagesDialog.setHideOnButtonClick(true);
		logMessagesDialog.setHeaderVisible(true);
		logMessagesDialog.setWidth(500);
		
		userMessage = new Status();
		lastTranTime= new Status();
		versionStatus = new Status();
	    versionStatus.setText(AppPref.VERSION);
		messageLog = new ArrayList<String>();
	}
	public static AppContainer getInstance(){
		if (instance == null){
			instance = new AppContainer();
		}
		return instance;
	}
  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    
    Menu mnuFile = new Menu();
    MenuItem miLogout = new MenuItem("Logout");
    miLogout.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
            notifier.notifyAppEvent(this, AppMsg.LOGOUT_COMMAND);
          }}
    );
    miLogout.addStyleName("LEFT");
    mnuFile.add(miLogout);
    
    Menu mnuMessage = new Menu();
    MenuItem miCreateMessage= new MenuItem("Create");
    mnuMessage.add(miCreateMessage);
    miCreateMessage.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.EMAIL_MSG_PAGE);
          }}
    );
    MenuItem miMsgHistory= new MenuItem("Message History");
    miMsgHistory.addStyleName("LEFT");
    mnuMessage.add(miMsgHistory);
    miMsgHistory.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.VW_SENT_EMAIL_PAGE);
          }}
    );
    
    Menu mnuList = new Menu();
    
    MenuItem miListManager = new MenuItem("ListManager");
    miListManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.EMAIL_LIST_PAGE);
        	
          }}
    );
    miListManager.addStyleName("LEFT");
    mnuList.add(miListManager);
    
 
    
    MenuItem miEmailManager = new MenuItem("Email Addresses");
    miEmailManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.RECIPIENT_PAGE);
          }}
    );
    miEmailManager.addStyleName("LEFT");
    mnuList.add(miEmailManager);
    
Menu mnuHelp = new Menu();
    
    MenuItem miAbout = new MenuItem("About");
    miAbout.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	Log.debug("settingMessage");
        	displayPopupMsg("About", "Developed by MartinAnalytics copyright 2009.");
        	
          }}
    );
    miAbout.addStyleName("LEFT");
    mnuHelp.add(miAbout);
    
    MenuItem miViewLog = new MenuItem("View Log");
    miViewLog.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	displayLogMessages();

          }}
    );
    miViewLog.addStyleName("LEFT");
    mnuHelp.add(miViewLog);

    
    MenuBar bar = new MenuBar();
    bar.setBorders(true);
    bar.setStyleAttribute("borderTop", "none");
    bar.add(new MenuBarItem("File", mnuFile));
    bar.add(new MenuBarItem("Message", mnuMessage));
    bar.add(new MenuBarItem("List", mnuList));
    bar.add(new MenuBarItem("Help", mnuHelp)); 
    

    add(bar);    
    mainPanel.setWidth("100%");
    mainPanel.setHeight("88%");
    mainPanel.setStyleName("MainApplicationScrollPanel");
    add(mainPanel);
    
    //add(panel, new FlowData(10));
    ToolBar statusBar = new ToolBar();

    userMessage.setWidth(500);
    userMessage.setText("Initial Message");
    userMessage.setBorders(true);
    statusBar.add(userMessage);
    
    lastTranTime.setWidth(50);
    lastTranTime.setBorders(true);

    statusBar.add(lastTranTime);
    
    add(statusBar);

    
  }
  
  /**
   * Set the StatusBar Messages
   */
  public void setTransactionResults(String message_, Long time_){
	  userMessage.setText(message_);
	  lastTranTime.setText(time_.toString() +"ms");
	  messageLog.add(new java.util.Date() + " Result - " + message_+ "(" + time_.toString() +"ms)" );
  }
  public void setStatusMessage(String message_){
	  userMessage.setText(message_);
	  messageLog.add(new java.util.Date() + " Status - " + message_  );

  }

/**
 * @return the notifier
 */
public AppNotifyObject getNotifier() {
	return notifier;
}

/**
 * @return the mainPanel
 */
public ScrollPanel getMainPanel() {
	return mainPanel;
}


public void displayPopupMsg(String title_, String msg_) {
	standardUserMessage.removeAll();
    standardUserMessage.setButtons(Dialog.OK);
	standardUserMessage.setHeading(title_);
	standardUserMessage.addText(msg_);
	standardUserMessage.show();
	messageLog.add(new java.util.Date() + " Popup - " + msg_  );
	
}
public void addSysLogMessage(String msg_){
	messageLog.add(new java.util.Date() + " NonDisp - " + msg_  );
}

public void displayLogMessages(){
	 logMessagesDialog.setButtons(Dialog.OK);
	 logMessagesDialog.setHeading("Log Messages");
	 logMessagesDialog.addStyleName("LEFT");
	 logMessagesDialog.removeAll();
	 for(int ndx =0; ndx <messageLog.size();ndx++){
		 logMessagesDialog.addText(messageLog.get(ndx)); 
	 }
	 
	 logMessagesDialog.show();
}
/**
 * @param mainPanel the mainPanel to set
 */
public void setMainPanel(ScrollPanel mainPanel) {
	this.mainPanel = mainPanel;
}


}
