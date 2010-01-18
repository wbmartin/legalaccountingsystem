package com.martinanalytics.legaltime.client;

import com.martinanalytics.legaltime.client.controller.MasterController;

public class ServerExcpetionHandler {
  static ServerExcpetionHandler instance= null;
  private MasterController masterController;
  protected ServerExcpetionHandler(){
	  
	  
  }
  public static ServerExcpetionHandler getInstance(){
	  if(instance==null){
		  instance = new ServerExcpetionHandler();
	  }
	  return instance;
  }
  
  public boolean handle(Throwable e){
	  boolean handled = false;
	  if (e.getMessage().equals(AppMsg.SERVER_TIMEOUT_ERROR)){
			masterController.promptUserForLogin();
			handled = true;
		}else{
			handled = true;
			masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
					AppPref.SERVER_ERROR + e.getMessage());
		}
	  return handled;
  }
/**
 * @param masterController the masterController to set
 */
public void setMasterController(MasterController masterController_) {
	this.masterController = masterController_;
}

}
