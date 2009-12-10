

package com.martinanalytics.legaltime.client.view.table;

import java.util.ArrayList;
import java.util.List;



import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.CustomerDataModelBean;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;



public class CustomerTable extends LayoutContainer {
	final ListStore<CustomerDataModelBean> store = new ListStore<CustomerDataModelBean>(); 
	private AppNotifyObject notifier;
	public CustomerTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ActiveYn Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("activeYn");  
//	    column.setHeader("ActiveYn");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtActiveYn = new TextField<String>();  
//	    ttxtActiveYn.setAllowBlank(false);  
//	    ttxtActiveYn.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtActiveYn.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtActiveYn ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ActiveYn Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin MonthlyBillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("monthlyBillRate");  
//	    column.setHeader("MonthlyBillRate");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtMonthlyBillRate = new TextField<String>();  
//	    ttxtMonthlyBillRate.setAllowBlank(false);  
//	    ttxtMonthlyBillRate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtMonthlyBillRate.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtMonthlyBillRate ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End MonthlyBillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin BillType Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("billType");  
//	    column.setHeader("BillType");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtBillType = new TextField<String>();  
//	    ttxtBillType.setAllowBlank(false);  
//	    ttxtBillType.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtBillType.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtBillType ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End BillType Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Note Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("note");  
//	    column.setHeader("Note");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtNote = new TextField<String>();  
//	    ttxtNote.setAllowBlank(false);  
//	    ttxtNote.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtNote.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtNote ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Note Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ClientSinceDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("clientSinceDt");  
//	    column.setHeader("ClientSinceDt");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtClientSinceDt = new TextField<String>();  
//	    ttxtClientSinceDt.setAllowBlank(false);  
//	    ttxtClientSinceDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtClientSinceDt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtClientSinceDt ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ClientSinceDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Email Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("email");  
//	    column.setHeader("Email");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtEmail = new TextField<String>();  
//	    ttxtEmail.setAllowBlank(false);  
//	    ttxtEmail.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtEmail.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtEmail ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Email Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Fax Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("fax");  
//	    column.setHeader("Fax");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtFax = new TextField<String>();  
//	    ttxtFax.setAllowBlank(false);  
//	    ttxtFax.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtFax.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtFax ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Fax Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin HomePhone Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("homePhone");  
//	    column.setHeader("HomePhone");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtHomePhone = new TextField<String>();  
//	    ttxtHomePhone.setAllowBlank(false);  
//	    ttxtHomePhone.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtHomePhone.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtHomePhone ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End HomePhone Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin WorkPhone Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("workPhone");  
//	    column.setHeader("WorkPhone");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtWorkPhone = new TextField<String>();  
//	    ttxtWorkPhone.setAllowBlank(false);  
//	    ttxtWorkPhone.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtWorkPhone.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtWorkPhone ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End WorkPhone Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Zip Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("zip");  
//	    column.setHeader("Zip");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtZip = new TextField<String>();  
//	    ttxtZip.setAllowBlank(false);  
//	    ttxtZip.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtZip.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtZip ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Zip Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin State Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("state");  
//	    column.setHeader("State");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtState = new TextField<String>();  
//	    ttxtState.setAllowBlank(false);  
//	    ttxtState.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtState.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtState ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End State Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Address Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("address");  
//	    column.setHeader("Address");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtAddress = new TextField<String>();  
//	    ttxtAddress.setAllowBlank(false);  
//	    ttxtAddress.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtAddress.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtAddress ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Address Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("lastName");  
	    column.setHeader("LastName");  
	    column.setWidth(100);  
//	    final TextField<String> ttxtLastName = new TextField<String>();  
//	    ttxtLastName.setAllowBlank(false);  
//	    ttxtLastName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtLastName.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtLastName ));  
	    column.addListener(Events.OnClick,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	Log.debug("Click found");
	          }});
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("firstName");  
	    column.setHeader("FirstName");  
	    column.setWidth(100);  
//	    final TextField<String> ttxtFirstName = new TextField<String>();  
//	    ttxtFirstName.setAllowBlank(false);  
//	    ttxtFirstName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtFirstName.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtFirstName ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

 // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin City Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
  	    column = new ColumnConfig(); 
  	    column.setId("city");  
  	    column.setHeader("City");  
  	    column.setWidth(100);  
//  	    final TextField<String> ttxtCity = new TextField<String>();  
//  	    ttxtCity.setAllowBlank(false);  
//  	    ttxtCity.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//  	        public void handleEvent(ComponentEvent be) {
//  	        	ttxtCity.selectAll();
//  	          }});
//  
//  
//  	    column.setEditor(new CellEditor( ttxtCity ));  
  	    configs.add(column);  
  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End City Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LastUpdate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("lastUpdate");  
//	    column.setHeader("LastUpdate");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtLastUpdate = new TextField<String>();  
//	    ttxtLastUpdate.setAllowBlank(false);  
//	    ttxtLastUpdate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtLastUpdate.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtLastUpdate ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastUpdate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ClientId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("clientId");  
//	    column.setHeader("ClientId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtClientId = new TextField<String>();  
//	    ttxtClientId.setAllowBlank(false);  
//	    ttxtClientId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtClientId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtClientId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ClientId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("customerId");  
//	    column.setHeader("CustomerId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtCustomerId = new TextField<String>();  
//	    ttxtCustomerId.setAllowBlank(false);  
//	    ttxtCustomerId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtCustomerId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtCustomerId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	    ColumnModel cm = new ColumnModel(configs);  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Choose a Customer");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(600, 300);  
	    cp.setLayout(new FitLayout());  
	  
	    final EditorGrid<CustomerDataModelBean> grid = new EditorGrid<CustomerDataModelBean>(store, cm);  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true);  
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    ToolBar toolBar = new ToolBar();  
	    Button addCustomer = new Button("Add Customer");  
	    addCustomer.addListener(Events.Select, new Listener<ComponentEvent>() {
		      public void handleEvent(ComponentEvent be) { 
	        
	        CustomerDataModelBean customerTableModelBean  = new CustomerDataModelBean();

		customerTableModelBean.setActiveYn("");
 		customerTableModelBean.setMonthlyBillRate(0D);
 		customerTableModelBean.setBillType("");
 		customerTableModelBean.setNote("");
 		customerTableModelBean.setClientSinceDt(new java.util.Date());
 		customerTableModelBean.setEmail("");
 		customerTableModelBean.setFax("");
 		customerTableModelBean.setHomePhone("");
 		customerTableModelBean.setWorkPhone("");
 		customerTableModelBean.setZip("");
 		customerTableModelBean.setState("");
 		customerTableModelBean.setCity("");
 		customerTableModelBean.setAddress("");
 		customerTableModelBean.setLastName("");
 		customerTableModelBean.setFirstName("");
 		customerTableModelBean.setLastUpdate(new java.util.Date());
 		customerTableModelBean.setClientId(0);
 		customerTableModelBean.setCustomerId(0);
  	        grid.stopEditing();  
	        store.insert(customerTableModelBean, 0);
	        grid.startEditing(0, 0);  
	      }  
	  
	    });  
	     toolBar.add(addCustomer);
	    cp.setTopComponent(toolBar);  
	    cp.setButtonAlign(HorizontalAlignment.CENTER);  
//	    cp.addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        store.rejectChanges();  
//	      }  
//	    }));  
//	  
//	    cp.addButton(new Button("Save", new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	    	saveChanges();
//	         
//	      }  
//	    }));  
	   
	    add(cp);  
	  }  
	  
	  public void setList(ArrayList<CustomerBean> customerBeans_ ){
		  List <CustomerDataModelBean> customerTableModelDataList = new ArrayList <CustomerDataModelBean>();
		  for(int ndx = 0; ndx<customerBeans_.size(); ndx++){
			  Log.debug("foundbean");
			  customerTableModelDataList.add(new CustomerDataModelBean(customerBeans_.get(ndx)));
		  }
		  store.removeAll();
		  store.add(customerTableModelDataList); 
		  
	  }
	  
	  public void saveChanges(){
		  List<Record> modified = store.getModifiedRecords();
		  CustomerDataModelBean customerTableModelBean = new CustomerDataModelBean();
		  ArrayList<CustomerBean> batchSave = new ArrayList<CustomerBean>();
		  for (Record r : modified) {
			  customerTableModelBean.setProperties(r.getModel().getProperties());
			  batchSave.add(customerTableModelBean.getStandardCustomerBean());
			  
			  
		  }
 		store.commitChanges();
		notifier.notifyAppEvent(this, "SaveCustomerBatch", batchSave );
		  
	  }
	
	public void onDetach(){
		super.onDetach();
		saveChanges();
			notifier.notifyAppEvent(this, "CustomerTableOnAttach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "CustomerTableOnAttach");
		
	}



	/**
	 * @return the notifier
	 */
	public AppNotifyObject getNotifier() {
		return notifier;
	}
}


