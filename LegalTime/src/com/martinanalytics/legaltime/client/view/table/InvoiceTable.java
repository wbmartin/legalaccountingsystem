
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
import com.extjs.gxt.ui.client.widget.grid.Grid;
//import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
//import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.VwInvoiceDisplayBean;
//import com.martinanalytics.legaltime.client.model.bean.InvoiceBean;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;


public class InvoiceTable extends LayoutContainer {
	final ListStore<VwInvoiceDisplayBean> store = new ListStore<VwInvoiceDisplayBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	//Be sure to distinguish Grid from Editor Grid
	Grid<VwInvoiceDisplayBean> grid= new Grid<VwInvoiceDisplayBean>(store, cm);


	public InvoiceTable(){
		notifier = new AppNotifyObject();
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	  
	    ColumnConfig column; 
	    
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Select Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=	    
	    CheckColumnConfig checkColumn = new CheckColumnConfig("selected", "?", 20);  
	    CellEditor checkBoxEditor = new CellEditor(new CheckBox());  
	    checkColumn.setEditor(checkBoxEditor);  
	    configs.add(checkColumn);
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Select Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin InvoiceId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  	    column = new ColumnConfig(); 
	  	    column.setId("invoiceId");  
	  	    column.setHeader("Invoice #");  
	  	    column.setWidth(75);  
	  	    final TextField<String> ttxtInvoiceId = new TextField<String>();  
	  	    ttxtInvoiceId.setAllowBlank(false);  
	  	    ttxtInvoiceId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	  	        public void handleEvent(ComponentEvent be) {
	  	        	ttxtInvoiceId.selectAll();
	  	          }});


	  	    column.setEditor(new CellEditor( ttxtInvoiceId ));  
	  	    configs.add(column);  
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End InvoiceId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

  	    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin InvoiceDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
  	    column = new ColumnConfig(); 
  	    column.setId("invoiceDt");  
  	    column.setHeader("Date");  
  	    column.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yy"));
  	    column.setWidth(100);  
  	    //	  		    final TextField<String> ttxtInvoiceDt = new TextField<String>();  
  	    //	  		    ttxtInvoiceDt.setAllowBlank(false);  
  	    //	  		    ttxtInvoiceDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
  	    //	  		        public void handleEvent(ComponentEvent be) {
  	    //	  		        	ttxtInvoiceDt.selectAll();
  	    //	  		          }});
  	    //
  	    //
  	    //	  		    column.setEditor(new CellEditor( ttxtInvoiceDt ));  
  	    configs.add(column);  
  	    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End InvoiceDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			column = new ColumnConfig(); 
			column.setId("displayName");  
			column.setHeader("Customer");  
			column.setWidth(100);  
			//	  	  	    final TextField<String> ttxtCustomerId = new TextField<String>();  
			//	  	  	    ttxtCustomerId.setAllowBlank(false);  
			//	  	  	    ttxtCustomerId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
			//	  	  	        public void handleEvent(ComponentEvent be) {
			//	  	  	        	ttxtCustomerId.selectAll();
			//	  	  	          }});
			//
			//
			//	  	  	    column.setEditor(new CellEditor( ttxtCustomerId ));  
			configs.add(column);  
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CustomerId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	  	  	    
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin InvoiceTotalAmt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("invoiceTotalAmt");  
	    column.setHeader("Total");  
	    column.setWidth(75);  
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    column.setNumberFormat(NumberFormat.getFormat("$0.00"));
//	    final TextField<String> ttxtInvoiceTotalAmt = new TextField<String>();  
//	    ttxtInvoiceTotalAmt.setAllowBlank(false);  
//	    ttxtInvoiceTotalAmt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtInvoiceTotalAmt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtInvoiceTotalAmt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End InvoiceTotalAmt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

////-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin GeneratedDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("generatedDt");  
//	    column.setHeader("GeneratedDt");  
//	    column.setWidth(100);  
////	    final TextField<String> ttxtGeneratedDt = new TextField<String>();  
////	    ttxtGeneratedDt.setAllowBlank(false);  
////	    ttxtGeneratedDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
////	        public void handleEvent(ComponentEvent be) {
////	        	ttxtGeneratedDt.selectAll();
////	          }});
////
////
////	    column.setEditor(new CellEditor( ttxtGeneratedDt ));  
//	    configs.add(column);  
////-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End GeneratedDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin PrevBalanceDue Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("prevBalanceDue");  
	    column.setHeader("Prev Balance");  
	    column.setWidth(100);  
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    column.setNumberFormat(NumberFormat.getFormat("$0.00"));
//	    final TextField<String> ttxtPrevBalanceDue = new TextField<String>();  
//	    ttxtPrevBalanceDue.setAllowBlank(false);  
//	    ttxtPrevBalanceDue.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtPrevBalanceDue.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtPrevBalanceDue ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End PrevBalanceDue Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

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

	   
	  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Select Invoice");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(600, 300);  
	    cp.setLayout(new FitLayout());  
	  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true);  
	    grid.setAutoExpandColumn("displayName");
	    grid.setWidth(600);
	    grid.getSelectionModel().addListener(Events.SelectionChange,  
				new Listener<SelectionChangedEvent<VwInvoiceDisplayBean>>() {  
					public void handleEvent(SelectionChangedEvent<VwInvoiceDisplayBean> be) {
						if (be.getSelection().size()>0){
						Record selected = grid.getStore().getRecord(be.getSelection().get(0));
						if( selected.get("selected")==null ||selected.get("selected").equals("false")){
							selected.set("selected",true);	
						}else{
							selected.set("selected",false);
						}
						grid.getSelectionModel().deselectAll();
						}
					}
	    });
	    
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
//	    ToolBar toolBar = new ToolBar();  
//	    Button cmdAdd = new Button("Add Invoice");  
//	    cmdAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        
//	        InvoiceBean invoiceBean  = new InvoiceBean();
//
//		invoiceBean.setPrevBalanceDue(0D);
// 		invoiceBean.setInvoiceTotalAmt(0D);
// 		invoiceBean.setGeneratedDt(new java.util.Date());
// 		invoiceBean.setInvoiceDt(new java.util.Date());
// 		invoiceBean.setLastUpdate(new java.util.Date());
// 		invoiceBean.setCustomerId(0);
// 		invoiceBean.setClientId(0);
// 		invoiceBean.setInvoiceId(0);
//  	        grid.stopEditing();  
//		//store.sort("", SortDir.ASC);
//	        store.insert(invoiceBean, 0);
//	        grid.startEditing(0, 0);  
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
	  
	  public void setList(ArrayList<VwInvoiceDisplayBean> invoiceBeans_ ){
		  //List <InvoiceBean> invoiceTableModelDataList = new ArrayList <InvoiceBean>();
		  //for(int ndx = 0; ndx<invoiceBeans_.size(); ndx++){
		//	  invoiceTableModelDataList.add(invoiceBeans_.get(ndx));
		 // }
		  store.setFiresEvents(false);
		  store.removeAll();
		  //store.add(invoiceTableModelDataList);
		  store.add((List <VwInvoiceDisplayBean>)invoiceBeans_); 
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);

		  
	  }
	  
//	  public void saveChanges(){
//		  List<Record> modified = store.getModifiedRecords();
//		  InvoiceBean invoiceBean = new InvoiceBean();
//		  ArrayList<InvoiceBean> batchSave = new ArrayList<InvoiceBean>();
//		  for (Record r : modified) {
//			  invoiceBean.setProperties(r.getModel().getProperties());
//			  batchSave.add(invoiceBean);
//			  
//			  
//		  }
//		store.commitChanges();
//		notifier.notifyAppEvent(this, "SaveInvoiceBatch", batchSave );
//		  
//	  }

	  public ArrayList<VwInvoiceDisplayBean> getModifiedList(){		  
		  List<Record> modified = store.getModifiedRecords();
		  VwInvoiceDisplayBean invoiceBean;
		  ArrayList<VwInvoiceDisplayBean> arrayList = new ArrayList<VwInvoiceDisplayBean>();
		  for (Record r : modified) {
			  //Log.debug("Identified Modified Record");
			  invoiceBean = new VwInvoiceDisplayBean();
			  invoiceBean.setProperties(r.getModel().getProperties());
			  //Log.debug("LaborRegisterTable Modified Record: " + invoiceBean.get());
			  arrayList.add(invoiceBean);
		  }
		  return arrayList;
	  }
	  
	  public ArrayList<VwInvoiceDisplayBean> getSelectedList(){		  
		  List<Record> modified = store.getModifiedRecords();
		  VwInvoiceDisplayBean invoiceBean;
		  ArrayList<VwInvoiceDisplayBean> arrayList = new ArrayList<VwInvoiceDisplayBean>();
		  for (Record r : modified) {
			  if(r.get("selected").equals(true)){
				  invoiceBean = new VwInvoiceDisplayBean();
				  invoiceBean.setProperties(r.getModel().getProperties());
				  arrayList.add(invoiceBean);
			  }
		  }
		  return arrayList;
	  }

	
	public void onDetach(){
		super.onDetach();
		//saveChanges();
		notifier.notifyAppEvent(this, "InvoiceTableOnDetach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "InvoiceTableOnAttach");
		
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
	public ListStore<VwInvoiceDisplayBean> getStore() {
		return store;
	}





//     public void updateSelectedBeansinStore(ArrayList<VwInvoiceDisplayBean> invoiceResult_) {
//    	int ndxStore;
//    	try{
//    		for(int ndx =0;ndx<invoiceResult_.size();ndx++){
//    			
//    			for(ndxStore =0;ndx < store.getCount();ndxStore++){
//    				if( store.getAt(ndxStore).get("invoiceId").equals(invoiceResult_.get(ndx).getInvoiceId())  &&  store.getAt(ndxStore).get("clientId").equals(invoiceResult_.get(ndx).getClientId())  &&  store.getAt(ndxStore).get("customerId").equals(invoiceResult_.get(ndx).getCustomerId()) ){
//    					store.getAt(ndxStore).setBean( invoiceResult_.get(ndx));
//    					//Log.debug("match found: "+ invoiceResult_.get(ndx).getPK());
//    					break;
//    					
//    				}else if( store.getAt(ndxStore).get("invoiceId").equals(0)  &&  store.getAt(ndxStore).get("customerId").equals(0) 						//&& store.getAt(ndxStore).get("prevBalanceDue").equals(invoiceResult_.get(ndx).getPrevBalanceDue())
//						//&& store.getAt(ndxStore).get("invoiceTotalAmt").equals(invoiceResult_.get(ndx).getInvoiceTotalAmt())
//						//&& store.getAt(ndxStore).get("generatedDt").equals(invoiceResult_.get(ndx).getGeneratedDt())
//						//&& store.getAt(ndxStore).get("invoiceDt").equals(invoiceResult_.get(ndx).getInvoiceDt())
//						//&& store.getAt(ndxStore).get("lastUpdate").equals(invoiceResult_.get(ndx).getLastUpdate())
//						//&& store.getAt(ndxStore).get("customerId").equals(invoiceResult_.get(ndx).getCustomerId())
//						//&& store.getAt(ndxStore).get("clientId").equals(invoiceResult_.get(ndx).getClientId())
//						//&& store.getAt(ndxStore).get("invoiceId").equals(invoiceResult_.get(ndx).getInvoiceId())
//					){
//    					store.getAt(ndxStore).setBean( invoiceResult_.get(ndx));
//    					//Log.debug("match found: "+ invoiceResult_.get(ndx).getField());
//    					break;
//    				}else{
//    					//Log.debug("mistmatch" + customerView.getStore().getAt(ndxStore).get("customerId") );
//    				}
//    				
//    			}
//    			
//    		
//    			
//    		}
//    	}catch(NullPointerException e){
//    		
//    	}
//    	
//    }


	/**
	 * @return the grid
	 */
	public Grid<VwInvoiceDisplayBean> getGrid() {
		return grid;
	}
	


}


