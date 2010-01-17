
package com.martinanalytics.legaltime.client.view.table;

import java.util.ArrayList;
import java.util.List;



import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreSorter;
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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerAccountRegisterBean;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;


public class CustomerAccountRegisterTable extends LayoutContainer {
	final ListStore<CustomerAccountRegisterBean> store = new ListStore<CustomerAccountRegisterBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	ArrayList<Double> runningTotal = new ArrayList<Double>();
	//Be sure to distinguish Grid from Editor Grid
	Grid<CustomerAccountRegisterBean> grid= new Grid<CustomerAccountRegisterBean>(store, cm);
	


	public CustomerAccountRegisterTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin EffectiveDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("effectiveDt");  
	    column.setHeader("Date");  
	    column.setWidth(100);  
	    column.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yy"));
	    column.setSortable(false);

	    //column.setEditor(new CellEditor( ttxtEffectiveDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End EffectiveDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Description Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  	    column = new ColumnConfig(); 
	  	    column.setId("description");  
	  	    column.setHeader("Description");  
	  	    column.setWidth(100);  
	  	  column.setSortable(false);
	  	    configs.add(column);  
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End Description Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin TranType Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("tranType");  
//	    column.setHeader("TranType");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtTranType = new TextField<String>();  
//	    ttxtTranType.setAllowBlank(false);  
//	    ttxtTranType.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtTranType.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtTranType ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End TranType Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin TranAmt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("tranAmt");  
//	    column.setHeader("TranAmt");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtTranAmt = new TextField<String>();  
//	    ttxtTranAmt.setAllowBlank(false);  
//	    ttxtTranAmt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtTranAmt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtTranAmt ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End TranAmt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig();
	    column.setId("increase");
	    column.setHeader("Increase");
	    column.setWidth(100);
	    column.setSortable(false);
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    column.setRenderer(new GridCellRenderer(){

			@Override
			public Object render(ModelData model_, String property_,
					ColumnData config_, int rowIndex_, int colIndex_,
					ListStore store_, Grid grid_) {
				Double result;
				if(model_.get("tranAmt")!= null && ((Double)model_.get("tranAmt"))>0){
					result = (Double)model_.get("tranAmt");
					return NumberFormat.getFormat("$#,##0.00").format(result);
				}else{
					return null;
				}
//				try{
//					return runningTotal.get(rowIndex_);
//				}catch(Exception ex){
//					//Log.debug("runningTotal" + runningTotal.toString());
//					return null;
//				}
				
			}
	    	
	    });
	    configs.add(column);

	    column = new ColumnConfig();
	    column.setId("decrease");
	    column.setHeader("Decrease");
	    column.setWidth(100);
	    column.setSortable(false);
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    column.setRenderer(new GridCellRenderer(){

			@Override
			public Object render(ModelData model_, String property_,
					ColumnData config_, int rowIndex_, int colIndex_,
					ListStore store_, Grid grid_) {
				Double result;
				if(model_.get("tranAmt")!= null && ((Double)model_.get("tranAmt"))<0){
					result = (Double)model_.get("tranAmt");
					return NumberFormat.getFormat("$#,##0.00").format(result*-1);
				}else{
					return null;
				}
				
			}
	    	
	    });
	    configs.add(column);
	    
	    

	    column = new ColumnConfig();
	    column.setId("total");
	    column.setHeader("Total");
	    column.setWidth(100);
	    column.setSortable(false);
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    column.setRenderer(new GridCellRenderer(){

			@Override
			public Object render(ModelData model_, String property_,
					ColumnData config_, int rowIndex_, int colIndex_,
					ListStore store_, Grid grid_) {
				Double result = 0D;
//				for(int ndx =0; ndx<=rowIndex_;ndx++){
//					if(store.getAt(ndx).getTranAmt() != null){
//						result += store.getAt(ndx).getTranAmt();
//					}
//				}
				try{
				result = runningTotal.get(rowIndex_);
				return NumberFormat.getFormat("$#,##0.00").format(result);
				}catch(Exception ex){
					ex.printStackTrace();
					return null;
				}
			}
	    	
	    });
	    configs.add(column);

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

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ClientAccountRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("clientAccountRegisterId");  
//	    column.setHeader("ClientAccountRegisterId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtClientAccountRegisterId = new TextField<String>();  
//	    ttxtClientAccountRegisterId.setAllowBlank(false);  
//	    ttxtClientAccountRegisterId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtClientAccountRegisterId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtClientAccountRegisterId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ClientAccountRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	  
	    ContentPanel cp = new ContentPanel();  
	    //cp.setHeading("Edit CustomerAccountRegister");  
	    cp.setHeaderVisible(false);
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(775, 300);  
	    cp.setLayout(new FitLayout());  
	  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true); 
	    grid.setAutoExpandColumn("description");
	    grid.setStripeRows(true);
	    
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    //ToolBar toolBar = new ToolBar();  
//	    Button cmdAdd = new Button("Add CustomerAccountRegister");  
//	    cmdAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        
//	        CustomerAccountRegisterBean customerAccountRegisterBean  = new CustomerAccountRegisterBean();
//
//		customerAccountRegisterBean.setEffectiveDt(new java.util.Date());
// 		customerAccountRegisterBean.setTranType("");
// 		customerAccountRegisterBean.setTranAmt(0D);
// 		customerAccountRegisterBean.setDescription("");
// 		customerAccountRegisterBean.setLastUpdate(new java.util.Date());
// 		customerAccountRegisterBean.setCustomerId(0);
// 		customerAccountRegisterBean.setClientId(0);
// 		customerAccountRegisterBean.setClientAccountRegisterId(0);
//  	        //grid.stopEditing();  
//		//store.sort("", SortDir.ASC);
//	        store.insert(customerAccountRegisterBean, 0);
//	       // grid.startEditing(0, 0);  
//	      }  
//	  
//	    });  
//	    toolBar.add(cmdAdd);  
//	    cp.setTopComponent(toolBar);  
//	    cp.setButtonAlign(HorizontalAlignment.CENTER); 
//	    Button cmdReset =new Button("Reset", new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        store.rejectChanges();  
//	      }  
//	    });  
//	    cmdReset.setBorders(true);
//	    cp.addButton(cmdReset);  
//	    Button cmdSave= new Button("Save", new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	    	saveChanges();
//	         
//	      }  
//	    })
//	    cmdSave.setBorders(true);
//	    cp.addButton(cmdSave);  
	  
	    add(cp);  
	  }  
	  
	  public void setList(ArrayList<CustomerAccountRegisterBean> customerAccountRegisterBeans_ ){
		  //List <CustomerAccountRegisterBean> customerAccountRegisterTableModelDataList = new ArrayList <CustomerAccountRegisterBean>();
		  //for(int ndx = 0; ndx<customerAccountRegisterBeans_.size(); ndx++){
		//	  customerAccountRegisterTableModelDataList.add(customerAccountRegisterBeans_.get(ndx));
		 // }
		  Double previousValue =0D;
		  runningTotal.clear();
		  for(int ndx =0; ndx <customerAccountRegisterBeans_.size();ndx++ ){
			  
			  
				  runningTotal.add(previousValue + customerAccountRegisterBeans_.get(ndx).getTranAmt());  
				  previousValue = previousValue + customerAccountRegisterBeans_.get(ndx).getTranAmt();
			  
		  }
		  store.setFiresEvents(false);
		  store.removeAll();
		  //store.add(customerAccountRegisterTableModelDataList);
		  store.add((List <CustomerAccountRegisterBean>)customerAccountRegisterBeans_); 
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);
		  
		  
		  

		  
	  }
	  
//	  public void saveChanges(){
//		  List<Record> modified = store.getModifiedRecords();
//		  CustomerAccountRegisterBean customerAccountRegisterBean = new CustomerAccountRegisterBean();
//		  ArrayList<CustomerAccountRegisterBean> batchSave = new ArrayList<CustomerAccountRegisterBean>();
//		  for (Record r : modified) {
//			  customerAccountRegisterBean.setProperties(r.getModel().getProperties());
//			  batchSave.add(customerAccountRegisterBean);
//			  
//			  
//		  }
//		store.commitChanges();
//		notifier.notifyAppEvent(this, "SaveCustomerAccountRegisterBatch", batchSave );
//		  
//	  }

	  public ArrayList<CustomerAccountRegisterBean> getModifiedList(){		  
		  List<Record> modified = store.getModifiedRecords();
		  CustomerAccountRegisterBean customerAccountRegisterBean;
		  ArrayList<CustomerAccountRegisterBean> arrayList = new ArrayList<CustomerAccountRegisterBean>();
		  for (Record r : modified) {
			  //Log.debug("Identified Modified Record");
			  customerAccountRegisterBean = new CustomerAccountRegisterBean();
			  customerAccountRegisterBean.setProperties(r.getModel().getProperties());
			  //Log.debug("LaborRegisterTable Modified Record: " + customerAccountRegisterBean.get());
			  arrayList.add(customerAccountRegisterBean);
		  }
		  return arrayList;
	  }

	
	public void onDetach(){
		super.onDetach();
		//saveChanges();
		notifier.notifyAppEvent(this, "CustomerAccountRegisterTableOnDetach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "CustomerAccountRegisterTableOnAttach");
		
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
	public ListStore<CustomerAccountRegisterBean> getStore() {
		return store;
	}





     public void updateSelectedBeansinStore(ArrayList<CustomerAccountRegisterBean> customerAccountRegisterResult_) {
    	int ndxStore;
    	try{
    		for(int ndx =0;ndx<customerAccountRegisterResult_.size();ndx++){
    			
    			for(ndxStore =0;ndx < store.getCount();ndxStore++){
    				if( store.getAt(ndxStore).get("clientAccountRegisterId").equals(customerAccountRegisterResult_.get(ndx).getCustomerAccountRegisterId())  &&  store.getAt(ndxStore).get("clientId").equals(customerAccountRegisterResult_.get(ndx).getClientId())  &&  store.getAt(ndxStore).get("customerId").equals(customerAccountRegisterResult_.get(ndx).getCustomerId()) ){
    					store.getAt(ndxStore).setBean( customerAccountRegisterResult_.get(ndx));
    					//Log.debug("match found: "+ customerAccountRegisterResult_.get(ndx).getPK());
    					break;
    					
    				}else if( store.getAt(ndxStore).get("clientAccountRegisterId").equals(0)  &&  store.getAt(ndxStore).get("customerId").equals(0) 						//&& store.getAt(ndxStore).get("effectiveDt").equals(customerAccountRegisterResult_.get(ndx).getEffectiveDt())
						//&& store.getAt(ndxStore).get("tranType").equals(customerAccountRegisterResult_.get(ndx).getTranType())
						//&& store.getAt(ndxStore).get("tranAmt").equals(customerAccountRegisterResult_.get(ndx).getTranAmt())
						//&& store.getAt(ndxStore).get("description").equals(customerAccountRegisterResult_.get(ndx).getDescription())
						//&& store.getAt(ndxStore).get("lastUpdate").equals(customerAccountRegisterResult_.get(ndx).getLastUpdate())
						//&& store.getAt(ndxStore).get("customerId").equals(customerAccountRegisterResult_.get(ndx).getCustomerId())
						//&& store.getAt(ndxStore).get("clientId").equals(customerAccountRegisterResult_.get(ndx).getClientId())
						//&& store.getAt(ndxStore).get("clientAccountRegisterId").equals(customerAccountRegisterResult_.get(ndx).getClientAccountRegisterId())
					){
    					store.getAt(ndxStore).setBean( customerAccountRegisterResult_.get(ndx));
    					//Log.debug("match found: "+ customerAccountRegisterResult_.get(ndx).getField());
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
	public Grid<CustomerAccountRegisterBean> getGrid() {
		return grid;
	}


}


