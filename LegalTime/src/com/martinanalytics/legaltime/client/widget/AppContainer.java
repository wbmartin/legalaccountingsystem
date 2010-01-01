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
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.VerticalPanel;

import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.controller.LaborRegisterController;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.AppPref;


public class AppContainer extends LayoutContainer {
	Listener<ComponentEvent> masterController;
	private AppNotifyObject notifier ;
	private ScrollPanel mainPanel ;


	final Dialog standardUserMessage = new Dialog();
	final Dialog logMessagesDialog = new Dialog();
	private  Dialog appWindowDialog = new Dialog();

	private static AppContainer instance;
    private Status userMessage;
    private Status lastTranTime;
    private Status versionStatus;

    private ArrayList<String> messageLog;
    ContentPanel cp = new ContentPanel(new CenterLayout());
    CenterLayout centerLayout = new CenterLayout();
    VerticalPanel vp = new VerticalPanel();
	protected AppContainer(){
		super();
		setStyleName("AppContainer");
		mainPanel = new ScrollPanel();
		mainPanel.setWidget(vp);
		//cp.setHeaderVisible(false);
		//cp.setBodyBorder(false);
		//cp.setBorders(false);
		cp.setWidth(800);
		
		
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
		
		createApplicationWindow();
	}
	public void createApplicationWindow() {

		  BorderLayout layout = new BorderLayout(); 
  	  appWindowDialog.setLayout(layout);

      
  
		
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
    MenuBar bar = new MenuBar();
    bar.setBorders(true);
    bar.setStyleAttribute("borderTop", "none");

    

    add(bar);
    
    Menu mnuFile = new Menu();
    bar.add(new MenuBarItem("File", mnuFile));
    MenuItem miLogout = new MenuItem("Logout");
    miLogout.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
            notifier.notifyAppEvent(this, AppMsg.LOGOUT_COMMAND);
          }}
    );
    miLogout.addStyleName("LEFT");
    mnuFile.add(miLogout);
    
    Menu mnuCustomer = new Menu();
    bar.add(new MenuBarItem("Customer", mnuCustomer));
     
    MenuItem miCustomerManager= new MenuItem("Customer Manager");
    miCustomerManager.addStyleName("LEFT");
    mnuCustomer.add(miCustomerManager);
    miCustomerManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	History.newItem(AppPages.CUSTOMER_PAGE);
          }}
    );
    
    MenuItem miBillHours= new MenuItem("Bill Hours");
    miBillHours.addStyleName("LEFT");
    mnuCustomer.add(miBillHours);
    miBillHours.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	notifier.notifyAppEvent(this, AppMsg.SHOW_LABOR_REGISTER_DIALOG);
          }}
    );
    
    MenuItem miFollowupManager= new MenuItem("Followup Manager");
    miFollowupManager.addStyleName("LEFT");
    mnuCustomer.add(miFollowupManager);
    miFollowupManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	History.newItem(  AppPages.VW_CUSTOMER_FOLLOWUP_PAGE);
          }}
    );
    
  
    
    MenuItem miMsgHistory= new MenuItem("Message History");
    miMsgHistory.addStyleName("LEFT");
    mnuCustomer.add(miMsgHistory);
    miMsgHistory.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.VW_SENT_EMAIL_PAGE);
          }}
    );
    
    Menu mnuBilling = new Menu();
    bar.add(new MenuBarItem("Billing", mnuBilling));

    MenuItem miInvoiceManager = new MenuItem("Invoice Manager");
    miInvoiceManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	History.newItem(AppPages.INVOICE_MANAGER_PAGE);
        	
          }}
    );
    miInvoiceManager.addStyleName("LEFT");
    mnuBilling.add(miInvoiceManager);
    
 
    
    MenuItem miEmailManager = new MenuItem("Email Addresses");
    miEmailManager.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent be) {
        	//History.newItem(AppPages.RECIPIENT_PAGE);
          }}
    );
    miEmailManager.addStyleName("LEFT");
    mnuBilling.add(miEmailManager);
    
    Menu mnuHelp = new Menu();
    bar.add(new MenuBarItem("Help", mnuHelp));
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

    
    
     
    mainPanel.setWidth("100%");
    mainPanel.setHeight("88%");
    mainPanel.setStyleName("MainApplicationScrollPanel");
    
    //mainPanel.add(cp);
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
public void clearMainPanel(){
//	try{
	mainPanel.clear();
	//mainPanel.remove(mainPanel.getWidget());
//	}catch(Exception e){
//		
//	}
}

/**
 * @return the appWindowDialog
 */
public Dialog getAppWindowDialog() {
	return appWindowDialog;
}
public void setAppWindowDialog(Dialog newDialog_) {
	 appWindowDialog = newDialog_;
}
public void setView(Composite page_) {
//	cp.removeAll();
//	cp.setLayout(centerLayout);
//	cp.add(page_);
//
//	VerticalPanel vp = new VerticalPanel();
//	vp.setWidth("100%");
//	vp.setHorizontalAlign(HorizontalAlignment.CENTER);
//	vp.add(cp);
//	cp.setWidth("96%");
	
	mainPanel.setWidget(page_);
	//vp.removeAll();
	//vp.add(page_);
	

	
}

}
