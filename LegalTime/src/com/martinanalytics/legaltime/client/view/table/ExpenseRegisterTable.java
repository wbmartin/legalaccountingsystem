
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
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
//import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.ExpenseRegisterBean;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;


public class ExpenseRegisterTable extends LayoutContainer {
	final ListStore<ExpenseRegisterBean> store = new ListStore<ExpenseRegisterBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	//Be sure to distinguish Grid from Editor Grid
	private EditorGrid<ExpenseRegisterBean> grid= new EditorGrid<ExpenseRegisterBean>(store, cm);


	public ExpenseRegisterTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Invoiceable Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	    CheckColumnConfig checkColumn = new CheckColumnConfig("invoiceable", "?", 20);  
	    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
	    checkColumn.setEditor(checkBoxEditor);  
	    configs.add(checkColumn);
//	    column = new ColumnConfig(); 
//	    column.setId("invoiceable");  
//	    column.setHeader("Invoiceable");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtInvoiceable = new TextField<String>();  
//	    ttxtInvoiceable.setAllowBlank(false);  
//	    ttxtInvoiceable.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtInvoiceable.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtInvoiceable ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Invoiceable Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin InvoiceId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("invoiceId");  
//	    column.setHeader("InvoiceId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtInvoiceId = new TextField<String>();  
//	    ttxtInvoiceId.setAllowBlank(false);  
//	    ttxtInvoiceId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtInvoiceId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtInvoiceId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End InvoiceId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ExpenseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("expenseDt");  
	    column.setHeader("ExpenseDt");  
	    column.setWidth(100);  
	    final DateField ttxtExpenseDt = new DateField();  
	    ttxtExpenseDt.setAllowBlank(false);  
	    ttxtExpenseDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtExpenseDt.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtExpenseDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ExpenseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Description Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("description");  
	    column.setHeader("Description");  
	    column.setWidth(100);  
	    final TextField<String> ttxtDescription = new TextField<String>();  
	    ttxtDescription.setAllowBlank(false);  
	    ttxtDescription.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtDescription.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtDescription ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Description Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	    

	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Amount Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  	    column = new ColumnConfig(); 
	  	    column.setId("amount");  
	  	    column.setHeader("Amount");  
	  	    column.setWidth(100);  
	  	    final TextField<String> ttxtAmount = new TextField<String>();  
	  	    ttxtAmount.setAllowBlank(false);  
	  	    ttxtAmount.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	  	        public void handleEvent(ComponentEvent be) {
	  	        	ttxtAmount.selectAll();
	  	          }});


	  	    column.setEditor(new CellEditor( ttxtAmount ));  
	  	    configs.add(column);  
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Amount Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	  	    
	  	    
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

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ExpenseRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("expenseRegisterId");  
//	    column.setHeader("ExpenseRegisterId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtExpenseRegisterId = new TextField<String>();  
//	    ttxtExpenseRegisterId.setAllowBlank(false);  
//	    ttxtExpenseRegisterId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtExpenseRegisterId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtExpenseRegisterId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ExpenseRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Expense");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(775, 200);  
	    cp.setLayout(new FitLayout());  
	  
	    //grid.setAutoExpandColumn("");  
	    grid.setAutoExpandColumn("description");
	    grid.setBorders(true); 

	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
//	    ToolBar toolBar = new ToolBar();  
  
//	    cp.setTopComponent(toolBar);  
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
	  
	  public void setList(ArrayList<ExpenseRegisterBean> expenseRegisterBeans_ ){
		  //List <ExpenseRegisterBean> expenseRegisterTableModelDataList = new ArrayList <ExpenseRegisterBean>();
		  //for(int ndx = 0; ndx<expenseRegisterBeans_.size(); ndx++){
		//	  expenseRegisterTableModelDataList.add(expenseRegisterBeans_.get(ndx));
		 // }
		  store.setFiresEvents(false);
		  store.removeAll();
		  //store.add(expenseRegisterTableModelDataList);
		  store.add((List <ExpenseRegisterBean>)expenseRegisterBeans_); 
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);

		  
	  }
	  
//	  public void saveChanges(){
//		  List<Record> modified = store.getModifiedRecords();
//		  ExpenseRegisterBean expenseRegisterBean = new ExpenseRegisterBean();
//		  ArrayList<ExpenseRegisterBean> batchSave = new ArrayList<ExpenseRegisterBean>();
//		  for (Record r : modified) {
//			  expenseRegisterBean.setProperties(r.getModel().getProperties());
//			  batchSave.add(expenseRegisterBean);
//			  
//			  
//		  }
//		store.commitChanges();
//		notifier.notifyAppEvent(this, "SaveExpenseRegisterBatch", batchSave );
//		  
//	  }

	  public ArrayList<ExpenseRegisterBean> getList(){		  
		  List<Record> modified = store.getModifiedRecords();
		  ExpenseRegisterBean expenseRegisterBean;
		  ArrayList<ExpenseRegisterBean> arrayList = new ArrayList<ExpenseRegisterBean>();
		  for (Record r : modified) {
			  //Log.debug("Identified Modified Record");
			  expenseRegisterBean = new ExpenseRegisterBean();
			  expenseRegisterBean.setProperties(r.getModel().getProperties());
			  //Log.debug("LaborRegisterTable Modified Record: " + expenseRegisterBean.get());
			  arrayList.add(expenseRegisterBean);
		  }
		  return arrayList;
	  }

	
	public void onDetach(){
		super.onDetach();
		//saveChanges();
			notifier.notifyAppEvent(this, "ExpenseRegisterTableOnDetach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "ExpenseRegisterTableOnAttach");
		
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
	public ListStore<ExpenseRegisterBean> getStore() {
		return store;
	}





     public void updateSelectedBeansinStore(ArrayList<ExpenseRegisterBean> expenseRegisterResult_) {
    	int ndxStore;
    	try{
    		for(int ndx =0;ndx<expenseRegisterResult_.size();ndx++){
    			
    			for(ndxStore =0;ndx < store.getCount();ndxStore++){
    				if( store.getAt(ndxStore).get("expenseRegisterId").equals(expenseRegisterResult_.get(ndx).getExpenseRegisterId())  &&  store.getAt(ndxStore).get("clientId").equals(expenseRegisterResult_.get(ndx).getClientId())  &&  store.getAt(ndxStore).get("customerId").equals(expenseRegisterResult_.get(ndx).getCustomerId()) ){
    					store.getAt(ndxStore).setBean( expenseRegisterResult_.get(ndx));
    					//Log.debug("match found: "+ expenseRegisterResult_.get(ndx).getPK());
    					break;
    					
    				}else if( store.getAt(ndxStore).get("expenseRegisterId").equals(0)  
    					&& store.getAt(ndxStore).get("invoiceable").equals(expenseRegisterResult_.get(ndx).getInvoiceable())
						&& store.getAt(ndxStore).get("invoiceId").equals(expenseRegisterResult_.get(ndx).getInvoiceId())
						&& store.getAt(ndxStore).get("amount").equals(expenseRegisterResult_.get(ndx).getAmount())
						&& store.getAt(ndxStore).get("description").equals(expenseRegisterResult_.get(ndx).getDescription())
						&& store.getAt(ndxStore).get("lastUpdate").equals(expenseRegisterResult_.get(ndx).getLastUpdate())
						&& store.getAt(ndxStore).get("customerId").equals(expenseRegisterResult_.get(ndx).getCustomerId())
						&& store.getAt(ndxStore).get("clientId").equals(expenseRegisterResult_.get(ndx).getClientId())
					){
    					store.getAt(ndxStore).setBean( expenseRegisterResult_.get(ndx));
    					//Log.debug("match found: "+ expenseRegisterResult_.get(ndx).getField());
    					break;
    				}else{
    					//Log.debug("mistmatch" + customerView.getStore().getAt(ndxStore).get("customerId") );
    				}
    				
    			}
    			
    		
    			
    		}
    	}catch(NullPointerException e){
    		
    	}
    	
    }

	/**
	 * @return the grid
	 */
	public EditorGrid<ExpenseRegisterBean> getGrid() {
		return grid;
	}


}


