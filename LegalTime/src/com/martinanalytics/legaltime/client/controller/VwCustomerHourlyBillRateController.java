
package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.ServerExcpetionHandler;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateService;
import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.view.VwCustomerHourlyBillRateView;
import java.util.ArrayList;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * VwCustomerHourlyBillRateController owns the VwCustomerHourlyBillRate View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class VwCustomerHourlyBillRateController implements AppEventListener, ClickHandler, ChangeHandler{
  private static VwCustomerHourlyBillRateController instance = null; //Singleton Instance
  private VwCustomerHourlyBillRateView vwCustomerHourlyBillRateView; 	//The UI container
  private final VwCustomerHourlyBillRateServiceAsync vwCustomerHourlyBillRateService = 
	GWT.create(VwCustomerHourlyBillRateService.class); 		// primary GWT remote Service
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  VwCustomerHourlyBillRateController(MasterController masterController_){
	masterController =masterController_;
	vwCustomerHourlyBillRateView = new VwCustomerHourlyBillRateView();	
	vwCustomerHourlyBillRateView.addAppEventListener(this);
	//vwCustomerHourlyBillRateView.getVwCustomerHourlyBillRateTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static VwCustomerHourlyBillRateController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new VwCustomerHourlyBillRateController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public VwCustomerHourlyBillRateView getVwCustomerHourlyBillRateView() {
	return vwCustomerHourlyBillRateView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
         if (e_.getName().equals("VwCustomerHourlyBillRateViewOnAttach")){
		
	}else if(e_.getName().equals("VwCustomerHourlyBillRateViewOnDetach")){
	}else if(e_.getName().equals("VwCustomerHourlyBillRateTableOnAttach")){		
	}else if(e_.getName().equals("VwCustomerHourlyBillRateTableOnDetach")){		
	
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("VwCustomerHourlyBillRateController on click called");
	
	
  }

/**
 * Handles any Select Box changes.
 */
@Override
  public void onChange(ChangeEvent event_) {
		
		
 }

/**
 * Provides a standard template to retrieve beans from the server.  
 * The results are handled through the onSuccess method in the AsynchCallback.
 * this function also uses the userProfile Singleton to send authorization credentials.
 * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
 * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
 */
 private void selectVwCustomerHourlyBillRateBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		vwCustomerHourlyBillRateService.selectVwCustomerHourlyBillRate(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<VwCustomerHourlyBillRateBean>>(){
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController.getAppContainer().setTransactionResults(
							"Retrieving VwCustomerHourlyBillRate Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

					}
		
					public void onSuccess(ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateResult) {
						masterController.getAppContainer().addSysLogMessage("Select VwCustomerHourlyBillRate received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved VwCustomerHourlyBillRate listing"
							, (new java.util.Date().getTime() - startTime.getTime()));
						
					}
		});
	  }





/**
 * Provides a standard template to retrieve a bean from the server by primary keys.  
 * The results are handled through the onSuccess method in the AsynchCallback.
 * this function also uses the userProfile Singleton to send authorization credentials.
 * @param whereClause_  a string beginning with "where" using standard sql syntax appropriate for the table to filter the beans
 * @param orderByClause a string beginning with "order by" using standard sql syntax appropriate alter the order of the beans
 */
// private void retrieveVwCustomerHourlyBillRateBeanByPrKey(){
//	final java.util.Date startTime = new java.util.Date();
//		vwCustomerHourlyBillRateService.getVwCustomerHourlyBillRateByPrKey(userProfile, , 
//				new AsyncCallback<VwCustomerHourlyBillRateBean>(){
//					public void onFailure(Throwable caught) {
//						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
//								AppPref.SERVER_ERROR + caught.getMessage());
//						masterController.getAppContainer().setTransactionResults(
//							"Retrieving VwCustomerHourlyBillRate by Identifier Failed"
//							, (new java.util.Date().getTime() -startTime.getTime()));
//
//					}
//		
//					public void onSuccess(VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateResult) {
//						masterController.getAppContainer().addSysLogMessage("Retrieve VwCustomerHourlyBillRate by identifier received ok");
//						masterController.getAppContainer().setTransactionResults(
//							"Successfully Retrieved VwCustomerHourlyBillRate by Primary Key"
//							, (new java.util.Date().getTime() - startTime.getTime()));
//						
//					}
//		});
//	  }



/**
 * updates the UI with the bean parameter
 */
  public void synchBeanToDisplay(VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){


    try{
	vwCustomerHourlyBillRateBean_.setLastUpdate(vwCustomerHourlyBillRateView.getDtfLastUpdate().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	vwCustomerHourlyBillRateBean_.setCustomerId((Integer)vwCustomerHourlyBillRateView.getNbrCustomerId().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	vwCustomerHourlyBillRateBean_.setBillRate((Double)vwCustomerHourlyBillRateView.getNbrBillRate().getValue());
    }catch(Exception e){
		
    }	
 




   


    try{
	vwCustomerHourlyBillRateBean_.setDisplayName(vwCustomerHourlyBillRateView.getTxtDisplayName().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	vwCustomerHourlyBillRateBean_.setUserId(vwCustomerHourlyBillRateView.getTxtUserId().getValue());
    }catch(Exception e){
		
    }
 




   


    try{
	vwCustomerHourlyBillRateBean_.setCustomerBillRateId((Integer)vwCustomerHourlyBillRateView.getNbrCustomerBillRateId().getValue());
    }catch(Exception e){
		
    }	
 




   
 

  }
/**
 * updates the bean parameter with values in the UI
 */
  public void synchDisplayToBean(VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean_){
	vwCustomerHourlyBillRateView.getDtfLastUpdate().setValue(vwCustomerHourlyBillRateBean_.getLastUpdate());
 


	vwCustomerHourlyBillRateView.getNbrCustomerId().setValue(vwCustomerHourlyBillRateBean_.getCustomerId());
 


	vwCustomerHourlyBillRateView.getNbrBillRate().setValue(vwCustomerHourlyBillRateBean_.getBillRate());	
 


 	vwCustomerHourlyBillRateView.getTxtDisplayName().setValue(vwCustomerHourlyBillRateBean_.getDisplayName());
 


 	vwCustomerHourlyBillRateView.getTxtUserId().setValue(vwCustomerHourlyBillRateBean_.getUserId());
 


	vwCustomerHourlyBillRateView.getNbrCustomerBillRateId().setValue(vwCustomerHourlyBillRateBean_.getCustomerBillRateId());
 


  }


}


