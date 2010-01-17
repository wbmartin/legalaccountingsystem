package com.martinanalytics.legaltime.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.martinanalytics.legaltime.client.widget.ReportUtil;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppPages;
import com.martinanalytics.legaltime.client.model.UserInfoCache;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;

import com.martinanalytics.legaltime.client.view.LoginView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
public class MasterController implements AppEventListener{
	 private Map<String, Composite> itemWidgets = new HashMap<String, Composite>();
	 private LoginController loginController ;
	 private UserProfile userProfile;



	 private AppContainer appContainer ;
	 private CustomerController customerController;
	 private VwCustomerHourlyBillRateController vwCustomerHourlyBillRateController;
	 private CustomerBillRateController customerBillRateController;
	 private FollowupController followupController;
	 private VwCustomerFollowupController vwCustomerFollowupController;
	 private UserInfoController userInfoController;
	 private LaborRegisterController laborRegisterController;
	 private InvoiceManagerController invoiceManagerController;
	 private InvoiceController invoiceController;
	 private CustomerAccountRegisterController customerAccountRegisterController;
	 private PaymentLogController paymentLogController;

	 
	 public MasterController(){
		 userProfile = UserProfile.getInstance();
		 UserInfoCache.getNotifier().addAppEventListener(this);
		 loginController = LoginController.getInstance(this);
		 itemWidgets.put(AppPages.LOGIN_PAGE, loginController.getLoginView().getLoginViewComposite());
		 loginController.getNotifier().addAppEventListener(this);


		 appContainer = AppContainer.getInstance();
		 appContainer.getNotifier().addAppEventListener(this);

		 customerController =  CustomerController.getInstance(this);
		 itemWidgets.put(AppPages.CUSTOMER_PAGE, customerController.getCustomerView().getCustomerComposite());
		 customerController.getNotifier().addAppEventListener(this);

		 vwCustomerHourlyBillRateController =  VwCustomerHourlyBillRateController.getInstance(this);
		 itemWidgets.put(AppPages.VW_USER_GRANT_PAGE, vwCustomerHourlyBillRateController.getVwCustomerHourlyBillRateView().getVwCustomerHourlyBillRateComposite());


		 customerBillRateController =  CustomerBillRateController.getInstance(this);
		 itemWidgets.put(AppPages.CUSTOMER_BILL_RATE_PAGE, customerBillRateController.getCustomerBillRateView().getCustomerBillRateComposite());

		 followupController =  FollowupController.getInstance(this);
		 itemWidgets.put(AppPages.FOLLOWUP_PAGE, followupController.getFollowupView().getFollowupComposite());

		 vwCustomerFollowupController =  VwCustomerFollowupController.getInstance(this);
		 itemWidgets.put(AppPages.VW_CUSTOMER_FOLLOWUP_PAGE, vwCustomerFollowupController.getVwCustomerFollowupView().getVwCustomerFollowupComposite());

		 userInfoController =  UserInfoController.getInstance(this);
		 itemWidgets.put(AppPages.USER_INFO_PAGE, userInfoController.getUserInfoView().getUserInfoComposite());

		 laborRegisterController =  LaborRegisterController.getInstance(this);
		 itemWidgets.put(AppPages.LABOR_REGISTER_PAGE, laborRegisterController.getLaborRegisterView().getLaborRegisterComposite());
		 laborRegisterController.getNotifier().addAppEventListener(this);
		 
		 invoiceManagerController =  InvoiceManagerController.getInstance(this);
		 itemWidgets.put(AppPages.INVOICE_MANAGER_PAGE,  invoiceManagerController.getInvoiceManagerView().getInvoiceManagerViewComposite());
		 invoiceManagerController.getNotifier().addAppEventListener(this);
		 
		 invoiceController =  InvoiceController.getInstance(this);
		 itemWidgets.put(AppPages.INVOICE_PAGE, invoiceController.getInvoiceView().getInvoiceComposite());

		 customerAccountRegisterController =  CustomerAccountRegisterController.getInstance(this);
		 itemWidgets.put(AppPages.CUSTOMER_ACCOUNT_REGISTER_PAGE, customerAccountRegisterController.getCustomerAccountRegisterView().getCustomerAccountRegisterComposite());
		 customerAccountRegisterController.getNotifier().addAppEventListener(this);
		
		 
			
			paymentLogController =  PaymentLogController.getInstance(this);
			itemWidgets.put(AppPages.PAYMENT_LOG_PAGE, paymentLogController.getPaymentLogView().getPaymentLogComposite());
			paymentLogController.getNotifier().addAppEventListener(this);
	 }
	 
	 public Composite getPage(String page_){
		 String finalPage =page_;
		 return itemWidgets.get(finalPage);
		 
	 }
	 public void promptUserForLogin(){
		if(!loginController.getLoginView().getLoginDialog().isVisible()){
		 loginController.getLoginView().getLoginDialog().show();
		}
	 }
	 public void secondaryLoginAccepted(){
		 loginController.getLoginView().getLoginDialog().hide();
	 }
	
	 public void notifyUserOfSystemError(String title_, String msg_){
		 Log.debug("Calling Set Message");		
		 appContainer.displayPopupMsg(title_, msg_);
			
		}

	public void passValue(String sessionId_) {
		//page1Controller.passValue(sessionId_);
		
	}

	@Override
	public void onAppEventNotify(AppEvent e_) {
		Log.debug("Master Controller received AppEvent: " + e_.getName());
		if(e_.getName().equals(AppMsg.LOGOUT_COMMAND)){
			userProfile.setSessionId("");
			userProfile.setClientId(0);
			userProfile.expireSession();
			
			History.newItem(AppPages.LOGIN_PAGE);
			
		}else if(e_.getName().equals("REQUEST_SCROLL_TO_TOP")){
			//appContainer.getMainPanel().setScrollPosition(0);
			//Log.debug("Scroll Postion: " + appContainer.getMainPanel().getScrollPosition());
		}else if(e_.getName().equals("SuccessfulLogin")){
			UserInfoCache.refreshCache();
			
		}else if(e_.getName().equals("USER_INFO_CACHE_REFRESHED")){
			customerController.getCustomerView().getFollowupTableCustomerPerspective().getFollowupView().getCboAssignedUser().setList(UserInfoCache.getCache());
			laborRegisterController.getLaborRegisterView().getCboUserId().setList(UserInfoCache.getCache());
			invoiceManagerController.getInvoiceManagerView().getLaborRegisterTable().getCboUserId().setList(UserInfoCache.getCache());
		}else if(e_.getName().equals(AppMsg.CUSTOMER_CACHE_REFRESHED)){
			laborRegisterController.getLaborRegisterView().getCboCustomerId().setList(customerController.getCache());
			followupController.getFollowupView().getCboCustomerId().setList(customerController.getCache());
			invoiceManagerController.getInvoiceManagerView().getCboCustomerId().setList(customerController.getCache());
			customerAccountRegisterController.getCustomerAccountRegisterView().getCboCustomerId().setList(customerController.getCache());
			paymentLogController.getPaymentLogView().getCboCustomerId().setList(customerController.getCache());
		}else if(e_.getName().equals(AppMsg.SHOW_LABOR_REGISTER_DIALOG)){
			laborRegisterController.showBillableHoursDialog();
		}else if(e_.getName().equals("PostPaymentRequest")){
			if(e_.getPayLoad() !=null){
				paymentLogController.getPaymentLogView().getCboCustomerId().setKeyValue(e_.getPayLoad());
			}
			paymentLogController.getPaymentLogView().getPostPaymentDialog().show();
			
		}else if(e_.getName().equals(	"PostPaymentDialogClosing")){
			customerAccountRegisterController.refreshList();
		}else if(e_.getName().equals(	"AssessMonthlyChargesRequested")){	
			laborRegisterController.retrieveLastMonthlyChargeDate();
		}else if(e_.getName().equals(	"AssessMonthlyChargeCommit")){		
			laborRegisterController.assessMonthlyChargesAndInvoice(appContainer.getDtfAssessMonthlyCharges().getValue());
		}else if(e_.getName().equals(	"InvoiceAllHourlyClients")){
			invoiceController.invoiceAllHourlyClients(new java.util.Date());
		}else if(e_.getName().equals(	"MonthlyChargesAssesmentComplete")){
			ArrayList<Integer> invoiceResult = (ArrayList<Integer>)e_.getPayLoad();
			String list ="";
			 for(int ndx =0;ndx<invoiceResult.size();ndx++ ){
				 list = list +invoiceResult.get(ndx) +",";
			 }
			 list = list.substring(0,list.length()-1);
			HashMap params = new HashMap();
			params.put("invoiceIdList", list);
			ReportUtil.showReport("./InvoiceReportServlet",UserProfile.getInstance(),params);
		}else if(e_.getName().equals(	"UnwindInvoiceRequest")){
			Integer invoiceId = (Integer)e_.getPayLoad();
			try{
				invoiceManagerController.unwindInvoice(invoiceId);
			}catch (Exception e){
				e.printStackTrace();
			}
			
		}else if(e_.getName().equals(	"RequestCustomerAccountRegisterRefresh")){	
			customerAccountRegisterController.refreshList();
		}else if(e_.getName().equals(	"ReversePaymentRequest")){		
			paymentLogController.reversePayment((Integer)e_.getPayLoad());
		}else if(e_.getName().equals(	"PaymentReversedSuccessfully")){
			customerAccountRegisterController.refreshList();
			
		}else{
			Log.debug("Unexpected App Message" + e_.getName());
		}
		
	}



	

	/**
	 * @return the masterFileBar2
	 */
	public AppContainer getAppContainer() {
		return appContainer;
	}

	
		
}
