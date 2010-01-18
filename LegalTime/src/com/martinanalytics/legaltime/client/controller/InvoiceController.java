/*
 * This file has been modified
 *  * added invoice all hourly clients
 */
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
import com.martinanalytics.legaltime.client.model.InvoiceService;
import com.martinanalytics.legaltime.client.model.InvoiceServiceAsync;
import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.martinanalytics.legaltime.client.view.InvoiceView;
import java.util.ArrayList;
import java.util.HashMap;

import com.martinanalytics.legaltime.client.widget.ReportUtil;
import com.martinanalytics.legaltime.client.widget.SimpleDateFormat;
import com.extjs.gxt.ui.client.store.Record;
import java.util.List;

/**
 * InvoiceController owns the Invoice View and responds to all events genereated
 * by the View and it's subcomponents.
 * 
 * @author bmartin
 * 
 */
public class InvoiceController implements AppEventListener, ClickHandler,
		ChangeHandler {
	private static InvoiceController instance = null; // Singleton Instance
	private InvoiceView invoiceView; // The UI container
	private final InvoiceServiceAsync invoiceService = GWT
			.create(InvoiceService.class); // primary GWT remote Service
	private UserProfile userProfile; // User Properties
	private MasterController masterController; // Overarching Controller
	private java.util.Date lastUpdateHolder; // Holder variables for timestamps

	/**
	 * Primary constructor, only called by getInstance, hence protected
	 * 
	 * @param masterController_
	 */
	protected InvoiceController(MasterController masterController_) {
		masterController = masterController_;
		invoiceView = new InvoiceView();
		invoiceView.addAppEventListener(this);
		// invoiceView.getInvoiceTable().getNotifier().addAppEventListener(this);
		userProfile = UserProfile.getInstance();
	}

	/**
	 * Singleton getInstance
	 * 
	 * @param masterController_
	 *            the overarching controller
	 * @return a Login Conroller reference
	 */
	public static InvoiceController getInstance(
			MasterController masterController_) {
		if (instance == null) {
			instance = new InvoiceController(masterController_);
		}
		return instance;
	}

	/**
	 * Used to get access to the the primary UI and subcomponents (e.g. fields
	 * and tables)
	 * 
	 * @return the primary UI
	 */
	public InvoiceView getInvoiceView() {
		return invoiceView;
	}

	/**
	 * Handles custom event system events, driven by the event's name.
	 * Type-casting of payloads should happen here.
	 */
	@Override
	public void onAppEventNotify(AppEvent e_) {
		if (e_.getName().equals("InvoiceViewOnAttach")) {

		} else if (e_.getName().equals("InvoiceViewOnDetach")) {
		} else if (e_.getName().equals("InvoiceTableOnAttach")) {
		} else if (e_.getName().equals("InvoiceTableOnDetach")) {

		} else {
			Log.debug("Unexpected AppEvent named" + e_.getName());
		}

	}

	/**
	 * Handles onClick actions from LoginView
	 */
	@Override
	public void onClick(ClickEvent event_) {
		Log.debug("InvoiceController on click called");

	}

	/**
	 * Handles any Select Box changes.
	 */
	@Override
	public void onChange(ChangeEvent event_) {

	}

	/**
	 * Provides a standard template to retrieve beans from the server. The
	 * results are handled through the onSuccess method in the AsynchCallback.
	 * this function also uses the userProfile Singleton to send authorization
	 * credentials.
	 * 
	 * @param whereClause_
	 *            a string beginning with "where" using standard sql syntax
	 *            appropriate for the table to filter the beans
	 * @param orderByClause
	 *            a string beginning with "order by" using standard sql syntax
	 *            appropriate alter the order of the beans
	 */
	private void selectInvoiceBeans(final String whereClause_,
			final String orderByClause_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.selectInvoice(userProfile, whereClause_, orderByClause_,
				new AsyncCallback<ArrayList<InvoiceBean>>() {
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Retrieving Invoice Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						masterController.getAppContainer().addSysLogMessage(
								"Where Attempted: " + whereClause_
										+ " | Orderby attempted "
										+ orderByClause_);

					}

					public void onSuccess(ArrayList<InvoiceBean> invoiceResult) {
						masterController.getAppContainer().addSysLogMessage(
								"Select Invoice received ok-  Where Attempted: "
										+ whereClause_
										+ " | Orderby attempted "
										+ orderByClause_);
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Successfully Retrieved Invoice listing",
										(new java.util.Date().getTime() - startTime
												.getTime()));

					}
				});
	}

	/**
	 * Provides a standard template to retrieve a bean from the server by
	 * primary keys. The results are handled through the onSuccess method in the
	 * AsynchCallback. this function also uses the userProfile Singleton to send
	 * authorization credentials.
	 * 
	 * @param whereClause_
	 *            a string beginning with "where" using standard sql syntax
	 *            appropriate for the table to filter the beans
	 * @param orderByClause
	 *            a string beginning with "order by" using standard sql syntax
	 *            appropriate alter the order of the beans
	 */
	private void retrieveInvoiceBeanByPrKey(Integer invoiceId_,
			Integer customerId_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.getInvoiceByPrKey(userProfile, invoiceId_, customerId_,
				new AsyncCallback<InvoiceBean>() {
					public void onFailure(Throwable caught) {
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Retrieving Invoice by Identifier Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));

					}

					public void onSuccess(InvoiceBean invoiceResult) {
						masterController.getAppContainer().addSysLogMessage(
								"Retrieve Invoice by identifier received ok");
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Successfully Retrieved Invoice by Primary Key",
										(new java.util.Date().getTime() - startTime
												.getTime()));

					}
				});
	}

	/**
	 * Provides standard mechanism to add a bean to the database The results are
	 * handled through the onSuccess method in the AsynchCallback.
	 * 
	 * @param invoiceBean_
	 *            the bean to insert into the database
	 */
	private void insertInvoiceBean(final InvoiceBean invoiceBean_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.insertInvoiceBean(userProfile, invoiceBean_,
				new AsyncCallback<InvoiceBean>() {
					public void onFailure(Throwable caught) {
						Log.debug("invoiceService.insertInvoice Failed: "
								+ caught);
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Adding Invoice Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						masterController.getAppContainer().addSysLogMessage(
								"Insert Bean Attempted: " + invoiceBean_);

					}

					public void onSuccess(InvoiceBean result) {
						Log.debug("invoiceService.insertInvoice onSuccess: "
								+ result);
						if (result.getInvoiceId() != null
								&& result.getClientId() != null
								&& result.getCustomerId() != null) {
							userProfile.incrementSessionTimeOut();
							masterController
									.getAppContainer()
									.setTransactionResults(
											"Successfully inserted Invoice record",
											(new java.util.Date().getTime() - startTime
													.getTime()));
							masterController.getAppContainer()
									.addSysLogMessage(
											"Bean Added" + result.toString());

						} else {
							masterController
									.notifyUserOfSystemError(
											"Server Error",
											"There was an error while adding the "
													+ "invoice.  This is an unexpected error, please go to Help > View Log and send "
													+ " the entire contents to the system administrator: "
													+ AppPref.SYS_ADMIN);
						}
					}
				});
	}

	/**
	 * Provides standard mechanism to update a bean in the database The results
	 * are handled through the onSuccess method in the AsynchCallback.
	 * 
	 * @param invoiceBean_
	 *            the bean to update the database
	 */
	private void updateInvoiceBean(final InvoiceBean invoiceBean_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.updateInvoiceBean(userProfile, invoiceBean_,
				new AsyncCallback<InvoiceBean>() {
					public void onFailure(Throwable caught) {
						Log.debug("invoiceService.updateInvoice Failed: "
								+ caught);
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Updating Invoice Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						masterController.getAppContainer().addSysLogMessage(
								"Update Bean Attempted: " + invoiceBean_);

					}

					public void onSuccess(InvoiceBean result) {
						Log.debug("invoiceService.updateInvoice onSuccess: "
								+ result);
						if (result.getInvoiceId() != null
								&& result.getClientId() != null
								&& result.getCustomerId() != null) {
							userProfile.incrementSessionTimeOut();
							masterController
									.getAppContainer()
									.setTransactionResults(
											"Successfully updated Invoice record",
											(new java.util.Date().getTime() - startTime
													.getTime()));
							masterController.getAppContainer()
									.addSysLogMessage(
											"Bean Updated" + result.toString());
						} else {
							masterController
									.notifyUserOfSystemError(
											"Server Error",
											"There was an error while updating a "
													+ "invoice record.  This can be caused by someone else changing the record.  Please try"
													+ " your transaction again.  If the error persists, please go to Help > View Log and send "
													+ " the entire contents to the system administrator: "
													+ AppPref.SYS_ADMIN);
						}
					}
				});
	}

	/**
	 * Provides standard mechanism to delete a bean in the database The results
	 * are handled through the onSuccess method in the AsynchCallback.
	 * 
	 * @param invoiceBean_
	 *            the bean to delete the database
	 */
	private void deleteInvoiceBean(final InvoiceBean invoiceBean_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.deleteInvoiceBean(userProfile, invoiceBean_,
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {

						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Deleting Invoice Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						masterController.getAppContainer().addSysLogMessage(
								"Delete Bean Attempted: " + invoiceBean_);

					}

					public void onSuccess(Boolean result) {
						Log.debug("invoiceService.deleteInvoice onSuccess: "
								+ result);
						if (result) {
							userProfile.incrementSessionTimeOut();
							masterController
									.getAppContainer()
									.setTransactionResults(
											"Successfully deleted Invoice record",
											(new java.util.Date().getTime() - startTime
													.getTime()));
							masterController.getAppContainer()
									.addSysLogMessage(
											"Bean Deleted"
													+ invoiceBean_.toString());
						} else {
							masterController
									.notifyUserOfSystemError(
											"Server Error",
											"There was an error while deleting a "
													+ "invoice record.  This can be caused by someone else changing the record.  Please try"
													+ " your transaction again.  If the error persists, please go to Help > View Log and send "
													+ " the entire contents to the system administrator: "
													+ AppPref.SYS_ADMIN);
						}
					}
				});
	}

	/**
	 * Sends the bean to the Server to be saved. If the primary keys are null or
	 * empty, an insert will be processed. The results are handled through the
	 * onSuccess method in the AsynchCallback.
	 * 
	 * @param invoiceBean_
	 *            the bean to save to the database
	 */
	private void saveInvoiceBean(final InvoiceBean invoiceBean_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.saveInvoiceBean(userProfile, invoiceBean_,
				new AsyncCallback<InvoiceBean>() {
					public void onFailure(Throwable caught) {
						Log.debug("invoiceService.saveInvoice Failed: "
								+ caught);
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Saving Invoice Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						masterController.getAppContainer().addSysLogMessage(
								"Save Bean Attempted: " + invoiceBean_);

					}

					public void onSuccess(InvoiceBean result) {
						userProfile.incrementSessionTimeOut();
						Log.debug("invoiceService.saveInvoice onSuccess: "
								+ result);
						if (result.getInvoiceId() != null
								&& result.getClientId() != null
								&& result.getCustomerId() != null) {
							masterController
									.getAppContainer()
									.setTransactionResults(
											"Successfully saved Invoice record",
											(new java.util.Date().getTime() - startTime
													.getTime()));
							masterController.getAppContainer()
									.addSysLogMessage(
											"Bean Saved"
													+ invoiceBean_.toString());
						} else {

							masterController
									.notifyUserOfSystemError(
											"Server Error",
											"There was an error while saving a "
													+ "invoice record.  This can be caused by someone else changing the record.  Please try"
													+ " your transaction again.  If the error persists, please go to Help > View Log and send "
													+ " the entire contents to the system administrator: "
													+ AppPref.SYS_ADMIN);
						}
					}
				});
	}

	/**
	 * Sends a batch of beans to the Server to be saved. If the primary keys are
	 * null or empty, an insert will be processed. The results are handled
	 * through the onSuccess method in the AsynchCallback.
	 * 
	 * @param invoiceBean_
	 *            the bean to save to the database
	 */
	private void saveInvoiceBeanBatch(ArrayList<InvoiceBean> invoiceBeanList_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.saveInvoiceBeanBatch(userProfile, invoiceBeanList_,
				new AsyncCallback<ArrayList<InvoiceBean>>() {
					public void onFailure(Throwable caught) {
						Log
								.debug("invoiceService.saveInvoiceBeanBatch Failed: "
										+ caught);
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Saving Invoice Batch Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
					}

					public void onSuccess(ArrayList<InvoiceBean> invoiceResult) {
						Log
								.debug("invoiceService.saveInvoiceBeanBatch onSuccess: "
										+ invoiceResult.toString());
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Successfully saved Invoice Batch",
										(new java.util.Date().getTime() - startTime
												.getTime()));

					}
				});
	}

	public void saveAllChanges() {

		List<Record> modified = invoiceView.getStore().getModifiedRecords();
		InvoiceBean invoiceBean;// = new InvoiceBean();
		ArrayList<InvoiceBean> batchSave = new ArrayList<InvoiceBean>();
		for (Record r : modified) {
			// Log.debug("Identified Modified Record");
			invoiceBean = new InvoiceBean();
			invoiceBean.setProperties(r.getModel().getProperties());
			batchSave.add(invoiceBean);

		}
		invoiceView.getStore().commitChanges();
		saveInvoiceBeanBatch(batchSave);

	}

	public void invoiceAllHourlyClients(java.util.Date invoiceDt_) {
		final java.util.Date startTime = new java.util.Date();
		invoiceService.invoiceAllHourlyClients(userProfile, invoiceDt_,
				new AsyncCallback<ArrayList<Integer>>() {
					public void onFailure(Throwable caught) {
						Log.debug("invoiceAllHourlyClients Failed: " + caught);
						if(!ServerExcpetionHandler.getInstance().handle(caught)){

						}
						masterController
								.getAppContainer()
								.setTransactionResults(
										"invoiceAllHourlyClients Failed",
										(new java.util.Date().getTime() - startTime
												.getTime()));
					}

					public void onSuccess(ArrayList<Integer> invoiceResult) {
						Log.debug("invoiceAllHourlyClients onSuccess: "
								+ invoiceResult.toString());
						masterController
								.getAppContainer()
								.setTransactionResults(
										"Successfully invoiceAllHourlyClients",
										(new java.util.Date().getTime() - startTime
												.getTime()));
						HashMap params = new HashMap();
						String list = "";
						for (int ndx = 0; ndx < invoiceResult.size(); ndx++) {
							list = list + invoiceResult.get(ndx) + ",";
						}
						list = list.substring(0, list.length() - 1);
						params.put("invoiceIdList", list);
						ReportUtil.showReport("./InvoiceReportServlet",
								UserProfile.getInstance(), params);

					}
				});
	}

}
