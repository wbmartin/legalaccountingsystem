

package com.martinanalytics.legaltime.client.controller;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.martinanalytics.legaltime.client.model.bean.CustomerDataModelBean;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.model.CustomerBillRateService;
import com.martinanalytics.legaltime.client.model.CustomerBillRateServiceAsync;
import com.martinanalytics.legaltime.client.model.CustomerService;
import com.martinanalytics.legaltime.client.model.CustomerServiceAsync;
import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateService;
import com.martinanalytics.legaltime.client.model.VwCustomerHourlyBillRateServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.view.CustomerView;
import com.martinanalytics.legaltime.client.view.table.VwCustomerHourlyBillRateDataModelBean;

import java.util.ArrayList;
import java.util.List;

import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;


/**
 * CustomerController owns the Customer View and responds to all events genereated
 * by the View and it's subcomponents.
 * @author bmartin
 *
 */
public class CustomerController implements AppEventListener, ClickHandler, ChangeHandler{
  private static CustomerController instance = null; //Singleton Instance
  private CustomerView customerView; 	//The UI container
  private final CustomerServiceAsync customerService = 
	GWT.create(CustomerService.class); 		// primary GWT remote Service
  private final VwCustomerHourlyBillRateServiceAsync vwCustomerHourlyBillRateService = 
		GWT.create(VwCustomerHourlyBillRateService.class); 		//aux gwt remote services
  private final CustomerBillRateServiceAsync customerBillRateService = 
		GWT.create(CustomerBillRateService.class); 		//
  private UserProfile userProfile;  			// User Properties
  private MasterController masterController;		// Overarching Controller
  private java.util.Date lastUpdateHolder;  //Holder variables for timestamps
/**
 * Primary constructor, only called by getInstance, hence protected
 * @param masterController_
 */
  protected  CustomerController(MasterController masterController_){
	masterController =masterController_;
	customerView = new CustomerView();	
	customerView.addAppEventListener(this);
	//customerView.getVwCustomerHourlyBillRateTable().getNotifier().addAppEventListener(masterController);
	//customerView.getCustomerTable().getNotifier().addAppEventListener(this);
	userProfile = UserProfile.getInstance();
	
//	customerView.getLstCustomerChooser().addListener(Events.OnClick,new Listener<ComponentEvent>() {
//        public void handleEvent(ComponentEvent be) {
//        	Log.debug("result: " + customerView.getCustomerFormPanel().isValid());
//        	if(customerView.getCustomerFormPanel().isValid()){
//	        	List <CustomerDataModelBean> beans = customerView.getLstCustomerChooser().getSelection();
//	    		//masterController.notifyUserOfSystemError("CustomerChange", beans.get(0).getCustomerId().toString() );
//	    		retrieveCustomerBeanByPrKey(beans.get(0).getCustomerId());
//        	}else{
//        		masterController.notifyUserOfSystemError("Information Error", "Please Correct Invalid Fields before Proceeding");
//        	}
//      }});
  }

/**
 * Singleton getInstance
 * @param masterController_ the overarching controller
 * @return a Login Conroller reference
 */
  public static CustomerController getInstance(MasterController masterController_){
	if(instance == null){
		instance = new CustomerController(masterController_);
	}
	return instance;
  } 

/**
 * Used to get access to the the primary UI and subcomponents (e.g. fields and tables)
 * @return the primary UI
 */
  public CustomerView getCustomerView() {
	return customerView;
  }

/**
 * Handles custom event system events, driven by the event's name.  Type-casting of payloads
 * should happen here.
 */
@Override
  public void onAppEventNotify(AppEvent e_) {
	Log.debug("Customer Controller received: " +e_.getName() + " "+ e_.getSource().toString());
    if (e_.getName().equals("CustomerViewOnAttach")){
    	selectCustomerBeans("where active_yn='Y'", "order by last_name");	
    }else if (e_.getName().equals("RefreshCustomers")){
    	selectCustomerBeans("where active_yn='Y'", "order by last_name");
	}else if(e_.getName().equals("CustomerViewOnDetach")){
		saveAllChanges();
	}else if(e_.getName().equals("SaveCustomerBatch")){
		saveAllChanges();
		
	}else if(e_.getName().equals("UserRequestedSave")){
		saveAllChanges();
		
	}else if(e_.getName().equals("UserMessage")){
		masterController.notifyUserOfSystemError("System Message", (String)e_.getPayLoad());
		
	}else if (e_.getName().equals("CustomerTableOnAttach")){
		
	}else if (e_.getName().equals("CustomerChange"	)){
		Integer custId = (Integer)e_.getPayLoad();
		saveOldChangesGetNewRates(custId);
	
	}else if (e_.getName().equals(	"RefreshBillRates")){
		Integer custId = (Integer)e_.getPayLoad();
		getCustomerRates(custId);
	}else{
		Log.debug("Unexpected AppEvent named" +e_.getName() );
	}
	
	
  }

/**
 * Handles onClick actions from LoginView
 */
@Override
  public void onClick(ClickEvent event_) {
	Log.debug("CustomerController on click called");
	
	
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
 private void selectCustomerBeans( final String whereClause_, final String orderByClause_){
	final java.util.Date startTime = new java.util.Date();
		customerService.selectCustomer(userProfile, whereClause_, orderByClause_, 
				new AsyncCallback<ArrayList<CustomerBean>>(){
					public void onFailure(Throwable caught) {
						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
								AppPref.SERVER_ERROR + caught.getMessage());
						masterController.getAppContainer().setTransactionResults(
							"Retrieving Customer Failed"
							, (new java.util.Date().getTime() -startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Where Attempted: " +whereClause_ + " | Orderby attempted " + orderByClause_ );

					}
		
					public void onSuccess(ArrayList<CustomerBean> customerResult) {
						masterController.getAppContainer().addSysLogMessage("Select Customer received ok-  Where Attempted: " 
							+ whereClause_ + " | Orderby attempted " + orderByClause_ );
						masterController.getAppContainer().setTransactionResults(
							"Successfully Retrieved Customer listing"
							, (new java.util.Date().getTime() - startTime.getTime()));
						customerView.setCustomerList(customerResult);
					}
		});
	  }

/**
 * Provides standard mechanism to add a bean to the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBean_ the bean to insert into the database
 */
  private void insertCustomerBean(final CustomerBean customerBean_){
	final java.util.Date startTime = new java.util.Date();
	customerService.insertCustomerBean(userProfile,customerBean_,
			new AsyncCallback<CustomerBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerService.insertCustomer Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Adding Customer Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Insert Bean Attempted: " + customerBean_);

				}
	
				public void onSuccess(CustomerBean result) {
					Log.debug("customerService.insertCustomer onSuccess: " + result);
					if (  result.getCustomerId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully inserted Customer record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Added" + result.toString());
			
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while adding the "
						+ "customer.  This is an unexpected error, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }
 
/**
 * Provides standard mechanism to update a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBean_ the bean to update the database
 */
   private void updateCustomerBean(final CustomerBean customerBean_){
	final java.util.Date startTime = new java.util.Date();
	customerService.updateCustomerBean(userProfile,customerBean_,
			new AsyncCallback<CustomerBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerService.updateCustomer Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Updating Customer Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Update Bean Attempted: " + customerBean_);

				}
	
				public void onSuccess(CustomerBean result) {
					Log.debug("customerService.updateCustomer onSuccess: " + result);
					if (  result.getCustomerId() !=null && result.getClientId() !=null){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully updated Customer record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Updated" + result.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while updating a "
						+ "customer record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Provides standard mechanism to delete a bean in the database
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBean_ the bean to delete the database
 */
 private void deleteCustomerBean(final CustomerBean customerBean_){
	final java.util.Date startTime = new java.util.Date();
	customerService.deleteCustomerBean(userProfile,customerBean_,
			new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Deleting Customer Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Delete Bean Attempted: " + customerBean_);

				}
	

				public void onSuccess(Boolean result) {
					Log.debug("customerService.deleteCustomer onSuccess: " + result);
					if (result){
						userProfile.incrementSessionTimeOut();
						masterController.getAppContainer().setTransactionResults(
							"Successfully deleted Customer record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Deleted" +  customerBean_.toString());
					}else{
						masterController.notifyUserOfSystemError("Server Error","There was an error while deleting a "
						+ "customer record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends the bean to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBean_ the bean to save to the database
 */
  private void saveCustomerBean(final CustomerBean customerBean_){
	final java.util.Date startTime = new java.util.Date();
	customerService.saveCustomerBean(userProfile,customerBean_,
			new AsyncCallback<CustomerBean>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerService.saveCustomer Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving Customer Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
					masterController.getAppContainer().addSysLogMessage("Save Bean Attempted: " + customerBean_);

				}
	
				public void onSuccess(CustomerBean result) {
					userProfile.incrementSessionTimeOut();
					Log.debug("customerService.saveCustomer onSuccess: " + result);
					if (  result.getCustomerId() !=null && result.getClientId() !=null){
						masterController.getAppContainer().setTransactionResults(
							"Successfully saved Customer record"
							, (new java.util.Date().getTime() - startTime.getTime()));
						masterController.getAppContainer().addSysLogMessage("Bean Saved" +  customerBean_.toString());
					}else{
						
						masterController.notifyUserOfSystemError("Server Error","There was an error while saving a "
						+ "customer record.  This can be caused by someone else changing the record.  Please try"
						+ " your transaction again.  If the error persists, please go to Help > View Log and send "
						+ " the entire contents to the system administrator: " + AppPref.SYS_ADMIN);
					}
				}
	});
  }

/**
 * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
 * The results are handled through the onSuccess method in the AsynchCallback.
 * @param customerBean_ the bean to save to the database
 */
  private void saveCustomerBeanBatch(ArrayList<CustomerBean> customerBeanList_){
	final java.util.Date startTime = new java.util.Date();
	customerService.saveCustomerBeanBatch(userProfile, customerBeanList_, 
			new AsyncCallback<ArrayList<CustomerBean>>(){
				public void onFailure(Throwable caught) {
					Log.debug("customerService.saveCustomerBeanBatch Failed: " + caught);
					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
							AppPref.SERVER_ERROR + caught.getMessage());
					masterController.getAppContainer().setTransactionResults(
						"Saving Customer Batch Failed"
						, (new java.util.Date().getTime() -startTime.getTime()));
				}
		
				public void onSuccess(ArrayList<CustomerBean> customerResult) {
					Log.debug("customerService.saveCustomerBeanBatch onSuccess: " + customerResult.toString());
					masterController.getAppContainer().setTransactionResults(
							"Successfully saved Customer Batch"
							, (new java.util.Date().getTime() - startTime.getTime()));
					customerView.updateSelectedBeansinStore(customerResult);
	
						
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
   private void retrieveCustomerBeanByPrKey( Integer customerId_ ){
  	final java.util.Date startTime = new java.util.Date();
  		customerService.getCustomerByPrKey(userProfile,  customerId_ 	,			new AsyncCallback<CustomerBean>(){
  					public void onFailure(Throwable caught) {
  						masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
  								AppPref.SERVER_ERROR + caught.getMessage());
  						masterController.getAppContainer().setTransactionResults(
  							"Retrieving Customer by Identifier Failed"
  							, (new java.util.Date().getTime() -startTime.getTime()));
  						
  					}
  		
  					public void onSuccess(CustomerBean customerResult) {
  						masterController.getAppContainer().addSysLogMessage("Retrieve Customer by identifier received ok");
  						masterController.getAppContainer().setTransactionResults(
  							"Successfully Retrieved Customer by Primary Key"
  							, (new java.util.Date().getTime() - startTime.getTime()));
  					synchDisplayToBean(customerResult);	
  					}
  		});
  	  }

/**
 * updates the UI with the bean parameter
 */
  public void synchBeanToDisplay(CustomerBean customerBean_){
    try{
	customerBean_.setActiveYn(customerView.getTxtActiveYn().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setMonthlyBillRate((Double) customerView.getTxtMonthlyBillRate().getValue());
    }catch(Exception e){
    	customerBean_.setMonthlyBillRate(0);
    }
    try{
	customerBean_.setBillType(customerView.getCboBillType().getSimpleValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setNote(customerView.getTxtNote().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setClientSinceDt(customerView.getDtpClientSinceDt().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setEmail(customerView.getTxtEmail().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setFax(customerView.getTxtFax().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setHomePhone(customerView.getTxtHomePhone().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setWorkPhone(customerView.getTxtWorkPhone().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setZip(customerView.getTxtZip().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setState(customerView.getTxtState().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setCity(customerView.getTxtCity().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setAddress(customerView.getTxtAddress().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setLastName(customerView.getTxtLastName().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setFirstName(customerView.getTxtFirstName().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setLastUpdate(customerView.getTxtLastUpdate().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setClientId(customerView.getTxtClientId().getValue());
    }catch(Exception e){
		
    }
    try{
	customerBean_.setCustomerId(customerView.getTxtCustomerId().getValue());
    }catch(Exception e){
		
    }
 

  }
/**
 * updates the bean parameter with values in the UI
 */
  public void synchDisplayToBean(CustomerBean customerBean_){
	customerView.getTxtActiveYn().setValue(customerBean_.getActiveYn());
	customerView.getTxtMonthlyBillRate().setValue(customerBean_.getMonthlyBillRate());
	customerView.getCboBillType().setSimpleValue(customerBean_.getBillType());
	customerView.getTxtNote().setValue(customerBean_.getNote());
	customerView.getDtpClientSinceDt().setValue(customerBean_.getClientSinceDt());
	customerView.getTxtEmail().setValue(customerBean_.getEmail());
	customerView.getTxtFax().setValue(customerBean_.getFax());
	customerView.getTxtHomePhone().setValue(customerBean_.getHomePhone());
	customerView.getTxtWorkPhone().setValue(customerBean_.getWorkPhone());
	customerView.getTxtZip().setValue(customerBean_.getZip());
	customerView.getTxtState().setValue(customerBean_.getState());
	customerView.getTxtCity().setValue(customerBean_.getCity());
	customerView.getTxtAddress().setValue(customerBean_.getAddress());
	customerView.getTxtLastName().setValue(customerBean_.getLastName());
	customerView.getTxtFirstName().setValue(customerBean_.getFirstName());
	customerView.getTxtLastUpdate().setValue(customerBean_.getLastUpdate());
	customerView.getTxtClientId().setValue(customerBean_.getClientId());
	customerView.getTxtCustomerId().setValue(customerBean_.getCustomerId());
  }
  
  public void saveAllChanges(){
	  ListStore<CustomerDataModelBean> store = customerView.getStore();
	  List<Record> modified = store.getModifiedRecords();
	  CustomerDataModelBean customerTableModelBean = new CustomerDataModelBean();
	  ArrayList<CustomerBean> batchSave = new ArrayList<CustomerBean>();
	  for (Record r : modified) {
		  Log.debug("Identified Modified Record");
		  customerTableModelBean.setProperties(r.getModel().getProperties());
		  batchSave.add(customerTableModelBean.getStandardCustomerBean());
		  
		  
	  }
		store.commitChanges();
		saveCustomerBeanBatch( batchSave );
	  
  }
 
  
  public void saveOldChangesGetNewRates(Integer custId_){
	  ListStore<VwCustomerHourlyBillRateDataModelBean> store = customerView.getVwCustomerHourlyBillRateTable().getStore();
	  List<Record> modified = store.getModifiedRecords();
	  VwCustomerHourlyBillRateDataModelBean vwCustomerHourlyBillRateDataModelBean = new VwCustomerHourlyBillRateDataModelBean();
	  ArrayList<CustomerBillRateBean> batchSave = new ArrayList<CustomerBillRateBean>();
	  for (Record r : modified) {
		  Log.debug("Table.saveChange1: " + r.getModel().getProperties().get("clientId"));
		  vwCustomerHourlyBillRateDataModelBean.setProperties(r.getModel().getProperties());
		  Log.debug("SaveOld CHanges"+vwCustomerHourlyBillRateDataModelBean.getProperties().toString());
		  batchSave.add(vwCustomerHourlyBillRateDataModelBean.getStandardCustomerBillRateBean());
		  
		  Log.debug("Table.saveChanges 2"+ vwCustomerHourlyBillRateDataModelBean.getClientId());
		  
	  }
		store.commitChanges();
		saveCustomerBillRateBeanBatch( batchSave );
		
		getCustomerRates(custId_);
		
  }
  
  public void getCustomerRates(Integer custId_){
	  selectVwCustomerHourlyBillRateBeans("where customer_id = "+ custId_.toString(),"");
  }
  
  
  private void selectVwCustomerHourlyBillRateBeans( final String whereClause_, final String orderByClause_){
		final java.util.Date startTime = new java.util.Date();
			vwCustomerHourlyBillRateService.selectVwCustomerHourlyBillRate(userProfile, whereClause_, orderByClause_, 
					new AsyncCallback<ArrayList<VwCustomerHourlyBillRateBean>>(){
						public void onFailure(Throwable caught) {
							masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
									AppPref.SERVER_ERROR + caught.getMessage());
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
						//	Log.debug("Controller.select Client ID:" + vwCustomerHourlyBillRateResult.get(0).getClientId());
							customerView.getVwCustomerHourlyBillRateTable().setList(vwCustomerHourlyBillRateResult)	;
//							    Timer t = new Timer() {
//							    	//This time is required to delay the scroll to the top required after an update to the Bill Rate Table
									//no longer needed, instead turn off event firing on grid, do adds/removes turn it on and then refresh.
//							        public void run() {
//							        	AppContainer.getInstance().getMainPanel().scrollToTop();
//							        }
//
//							      };
//							      t.schedule(450);
							 }
						
			});
		  }
  
  
  /**
   * Sends a batch of beans to the Server to be saved.  If the primary keys are null or empty, an insert will be processed.
   * The results are handled through the onSuccess method in the AsynchCallback.
   * @param customerBean_ the bean to save to the database
   */
    private void saveCustomerBillRateBeanBatch(ArrayList<CustomerBillRateBean> customerBillRateBeanList_){
    	Log.debug("saveCustomerBillRateBeanBatch" + customerBillRateBeanList_.toString());
  	final java.util.Date startTime = new java.util.Date();
  	customerBillRateService.saveCustomerBillRateBeanBatch(userProfile, customerBillRateBeanList_, 
  			new AsyncCallback<ArrayList<CustomerBillRateBean>>(){
  				public void onFailure(Throwable caught) {
  					Log.debug("customerBillRateService.saveCustomerBeanBatch Failed: " + caught);
  					masterController.notifyUserOfSystemError("Remote Procedure Call - Failure", 
  							AppPref.SERVER_ERROR + caught.getMessage());
  					masterController.getAppContainer().setTransactionResults(
  						"Saving Customer Batch Failed"
  						, (new java.util.Date().getTime() -startTime.getTime()));
  				}
  		
  				public void onSuccess(ArrayList<CustomerBillRateBean> customerResult) {
  					Log.debug("customerBillRateService.saveCustomerBeanBatch onSuccess: " + customerResult.toString());
  					masterController.getAppContainer().setTransactionResults(
  							"Successfully saved Customer Batch"
  							, (new java.util.Date().getTime() - startTime.getTime()));
  					//customerView.updateSelectedBeansinStore(customerResult);
  	
  						
  				}
  		});
    }
    



}


