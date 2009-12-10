package com.martinanalytics.legaltime.client.AppEvent;

import java.util.Vector;

public abstract class AppEventProducer {
	 private Vector AppEventListeners;
	 private AppEvent appEvent;
	public AppEventProducer() {
		super();
		AppEventListeners = new Vector();
		
	}
	 
	
	public void addAppEventListener(AppEventListener ael){
		 //add main frame to vector of listeners
		 if (AppEventListeners.contains(ael))  return;
		 AppEventListeners.addElement(ael);
		}
		public  void notifyAppEvent(Object source_, String msgName_, Object payLoad_){
		    notifyAppEvent(source_, msgName_, payLoad_, null);
		}
		public  void notifyAppEvent(Object source_, String msgName_){
		    notifyAppEvent(source_, msgName_, null, null);
		}
		public  void notifyAppEvent(Object source_, String msgName_, Object payLoad_, String note_){
		        if (!msgName_.equals("")){
		        //create the message encapsulated in an SQLEvent
		          appEvent = new AppEvent (source_, msgName_, payLoad_, note_);
		          Vector vtemp = (Vector)AppEventListeners.clone();
		              for (int x = 0; x < vtemp.size(); x++){
		                   AppEventListener target = null;
		                   target = (AppEventListener)vtemp.elementAt(x);
		                   target.onAppEventNotify(appEvent);
		              }
		         }
		    }
}
