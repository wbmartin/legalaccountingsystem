
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
import com.google.gwt.user.client.ui.Grid;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.controller.MasterController;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerHourlyBillRateBean;
import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.Style;



public class VwCustomerHourlyBillRateTable extends LayoutContainer {
	private final ListStore<VwCustomerHourlyBillRateBean> store = new ListStore<VwCustomerHourlyBillRateBean>(); 
	private AppNotifyObject notifier;
	private EditorGrid<VwCustomerHourlyBillRateBean> grid;
	public VwCustomerHourlyBillRateTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
	  
	    ColumnConfig column; 
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
//	    
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastUpdate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

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

	    
	    
	    
	    
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin DisplayName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("displayName");  
	    column.setHeader("Name");  
	    column.setWidth(175);  
//	    final TextField<String> ttxtDisplayName = new TextField<String>();  
//	    ttxtDisplayName.setAllowBlank(false);  
//	    ttxtDisplayName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtDisplayName.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtDisplayName ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End DisplayName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("billRate");  
	    column.setHeader("Hourly Rate");  
	    column.setWidth(125);  
	    final NumberField tnbrBillRate = new NumberField();  
	    tnbrBillRate.setAllowBlank(false);  
	    tnbrBillRate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	tnbrBillRate.selectAll();
	          }});


	    column.setEditor(new CellEditor( tnbrBillRate));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("userId");  
//	    column.setHeader("UserId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtUserId = new TextField<String>();  
//	    ttxtUserId.setAllowBlank(false);  
//	    ttxtUserId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtUserId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtUserId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CustomerBillRateId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("customerBillRateId");  
//	    column.setHeader("CustomerBillRateId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtCustomerBillRateId = new TextField<String>();  
//	    ttxtCustomerBillRateId.setAllowBlank(false);  
//	    ttxtCustomerBillRateId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtCustomerBillRateId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtCustomerBillRateId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CustomerBillRateId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	    ColumnModel cm = new ColumnModel(configs);  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Hourly Bill Rates");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(325, 200);  
	    cp.setLayout(new FitLayout());  
	  
	    grid = new EditorGrid<VwCustomerHourlyBillRateBean>(store, cm);  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true);  
	   // setScrollMode(Style.Scroll.NONE);
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    //ToolBar toolBar = new ToolBar();  
//	    Button add = new Button("Add VwCustomerHourlyBillRate");  
//	    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        
//	        VwCustomerHourlyBillRateBean vwCustomerHourlyBillRateBean  = new VwCustomerHourlyBillRateBean();
//
//		vwCustomerHourlyBillRateBean.setLastUpdate(new java.util.Date());
// 		vwCustomerHourlyBillRateBean.setCustomerId(0);
// 		vwCustomerHourlyBillRateBean.setBillRate(0D);
// 		vwCustomerHourlyBillRateBean.setDisplayName("");
// 		vwCustomerHourlyBillRateBean.setUserId("");
// 		vwCustomerHourlyBillRateBean.setCustomerBillRateId(0);
//  	        grid.stopEditing();  
//	        store.insert(vwCustomerHourlyBillRateBean, 0);
//	        grid.startEditing(0, 0);  
//	      }  
//	  
//	    });  
//	    toolBar.add(add);  
//	    cp.setTopComponent(toolBar);  
	    cp.setButtonAlign(HorizontalAlignment.CENTER);  
	    cp.addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	        store.rejectChanges();  
	      }  
	    }));  
	  
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
	  
	  public void setList(ArrayList<VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateBeans_ ){
		  List <VwCustomerHourlyBillRateBean> vwCustomerHourlyBillRateTableModelDataList = new ArrayList <VwCustomerHourlyBillRateBean>();
		  for(int ndx = 0; ndx<vwCustomerHourlyBillRateBeans_.size(); ndx++){
			 // Log.debug("Client Id found set list in table:" + vwCustomerHourlyBillRateBeans_.get(ndx).getClientId());
			  vwCustomerHourlyBillRateTableModelDataList.add(vwCustomerHourlyBillRateBeans_.get(ndx));
			 // Log.debug("SetList2" +vwCustomerHourlyBillRateTableModelDataList.get(ndx).getClientId()+ vwCustomerHourlyBillRateTableModelDataList.get(ndx).getDisplayName() );
		  }
		  store.setFiresEvents(false);
		  store.removeAll();
		  
		  store.add(vwCustomerHourlyBillRateTableModelDataList); 
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);
		  //notifier.notifyAppEvent(this, "REQUEST_SCROLL_TO_TOP");
		  
		  
		  
	  }
	  

	
	public void onDetach(){
		super.onDetach();
		//saveChanges();
			notifier.notifyAppEvent(this, "VwCustomerHourlyBillRateTableOnAttach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "VwCustomerHourlyBillRateTableOnAttach");
		
	}



	/**
	 * @return the notifier
	 */
	public AppNotifyObject getNotifier() {
		return notifier;
	}
	/**
	 * @return the store
	 */
	public ListStore<VwCustomerHourlyBillRateBean> getStore() {
		return store;
	}
}


