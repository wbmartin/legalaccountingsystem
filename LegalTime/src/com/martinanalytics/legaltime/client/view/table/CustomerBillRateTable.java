
package com.martinanalytics.legaltime.client.view.table;

import java.util.ArrayList;
import java.util.List;

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
import com.martinanalytics.legaltime.client.model.bean.CustomerBillRateBean;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;



public class CustomerBillRateTable extends LayoutContainer {
	final ListStore<CustomerBillRateDataModelBean> store = new ListStore<CustomerBillRateDataModelBean>(); 
	private AppNotifyObject notifier;
	public CustomerBillRateTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("billRate");  
	    column.setHeader("BillRate");  
	    column.setWidth(100);  
	    final TextField<String> ttxtBillRate = new TextField<String>();  
	    ttxtBillRate.setAllowBlank(false);  
	    ttxtBillRate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtBillRate.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtBillRate ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LastUpdate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("lastUpdate");  
	    column.setHeader("LastUpdate");  
	    column.setWidth(100);  
	    final TextField<String> ttxtLastUpdate = new TextField<String>();  
	    ttxtLastUpdate.setAllowBlank(false);  
	    ttxtLastUpdate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtLastUpdate.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtLastUpdate ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastUpdate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("userId");  
	    column.setHeader("UserId");  
	    column.setWidth(100);  
	    final TextField<String> ttxtUserId = new TextField<String>();  
	    ttxtUserId.setAllowBlank(false);  
	    ttxtUserId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtUserId.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtUserId ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("customerId");  
	    column.setHeader("CustomerId");  
	    column.setWidth(100);  
	    final TextField<String> ttxtCustomerId = new TextField<String>();  
	    ttxtCustomerId.setAllowBlank(false);  
	    ttxtCustomerId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtCustomerId.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtCustomerId ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ClientId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("clientId");  
	    column.setHeader("ClientId");  
	    column.setWidth(100);  
	    final TextField<String> ttxtClientId = new TextField<String>();  
	    ttxtClientId.setAllowBlank(false);  
	    ttxtClientId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtClientId.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtClientId ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ClientId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CustomerBillRateId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("customerBillRateId");  
	    column.setHeader("CustomerBillRateId");  
	    column.setWidth(100);  
	    final TextField<String> ttxtCustomerBillRateId = new TextField<String>();  
	    ttxtCustomerBillRateId.setAllowBlank(false);  
	    ttxtCustomerBillRateId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtCustomerBillRateId.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtCustomerBillRateId ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CustomerBillRateId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	    ColumnModel cm = new ColumnModel(configs);  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Edit CustomerBillRate");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(600, 300);  
	    cp.setLayout(new FitLayout());  
	  
	    final EditorGrid<CustomerBillRateDataModelBean> grid = new EditorGrid<CustomerBillRateDataModelBean>(store, cm);  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true);  
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    ToolBar toolBar = new ToolBar();  
	    Button add = new Button("Add CustomerBillRate");  
	    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	        
	        CustomerBillRateDataModelBean customerBillRateDataModelBean  = new CustomerBillRateDataModelBean();

		customerBillRateDataModelBean.setBillRate(0D);
 		customerBillRateDataModelBean.setLastUpdate(new java.util.Date());
 		customerBillRateDataModelBean.setUserId("");
 		customerBillRateDataModelBean.setCustomerId(0);
 		customerBillRateDataModelBean.setClientId(0);
 		customerBillRateDataModelBean.setCustomerBillRateId(0);
  	        grid.stopEditing();  
	        store.insert(customerBillRateDataModelBean, 0);
	        grid.startEditing(0, 0);  
	      }  
	  
	    });  
	    toolBar.add(add);  
	    cp.setTopComponent(toolBar);  
	    cp.setButtonAlign(HorizontalAlignment.CENTER);  
	    cp.addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	        store.rejectChanges();  
	      }  
	    }));  
	  
	    cp.addButton(new Button("Save", new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	    	saveChanges();
	         
	      }  
	    }));  
	  
	    add(cp);  
	  }  
	  
	  public void setList(ArrayList<CustomerBillRateBean> customerBillRateBeans_ ){
		  List <CustomerBillRateDataModelBean> customerBillRateTableModelDataList = new ArrayList <CustomerBillRateDataModelBean>();
		  for(int ndx = 0; ndx<customerBillRateBeans_.size(); ndx++){
			  customerBillRateTableModelDataList.add(new CustomerBillRateDataModelBean(customerBillRateBeans_.get(ndx)));
		  }
		  store.removeAll();
		  store.add(customerBillRateTableModelDataList); 
		  
	  }
	  
	  public void saveChanges(){
		  List<Record> modified = store.getModifiedRecords();
		  CustomerBillRateDataModelBean customerBillRateDataModelBean = new CustomerBillRateDataModelBean();
		  ArrayList<CustomerBillRateBean> batchSave = new ArrayList<CustomerBillRateBean>();
		  for (Record r : modified) {
			  customerBillRateDataModelBean.setProperties(r.getModel().getProperties());
			  batchSave.add(customerBillRateDataModelBean.getStandardCustomerBillRateBean());
			  
			  
		  }
 		store.commitChanges();
		notifier.notifyAppEvent(this, "SaveCustomerBillRateBatch", batchSave );
		  
	  }
	
	public void onDetach(){
		super.onDetach();
		saveChanges();
			notifier.notifyAppEvent(this, "CustomerBillRateTableOnAttach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "CustomerBillRateTableOnAttach");
		
	}



	/**
	 * @return the notifier
	 */
	public AppNotifyObject getNotifier() {
		return notifier;
	}
}


