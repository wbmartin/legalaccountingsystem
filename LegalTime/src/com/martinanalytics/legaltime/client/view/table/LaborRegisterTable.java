
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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
//import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.UserInfoCache;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.LaborRegisterBean;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.widget.AlternateComboBox;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;


public class LaborRegisterTable extends LayoutContainer {
	final ListStore<LaborRegisterBean> store = new ListStore<LaborRegisterBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	//Be sure to distinguish Grid from Editor Grid
	EditorGrid<LaborRegisterBean> grid= new EditorGrid<LaborRegisterBean>(store, cm);
	private AlternateComboBox<UserInfoBean> cboUserId = new AlternateComboBox<UserInfoBean>("Billing Person", "userId","userId","displayName");



	public LaborRegisterTable(){
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

	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin ActivityDate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	  	    column = new ColumnConfig(); 
	  	    column.setId("activityDate");  
	  	    column.setHeader("Date");  
	  	    column.setWidth(85);  
	  	    final DateField ttxtActivityDate = new DateField();  
	  	    ttxtActivityDate.setAllowBlank(false);  
	  	    ttxtActivityDate.getPropertyEditor().setFormat(DateTimeFormat.getFormat("MM/dd/yy")); 
	  	    ttxtActivityDate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	  	        public void handleEvent(ComponentEvent be) {
	  	        	ttxtActivityDate.selectAll();
	  	          }});

	  	    column.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yy"));
	  	    column.setEditor(new CellEditor( ttxtActivityDate ));  
	  	    configs.add(column);  
	  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End ActivityDate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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
			  //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin MinuteCount Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		    column = new ColumnConfig(); 
		    column.setId("minuteCount");  
		    column.setHeader("Hours");  
		    column.setWidth(50); 
		    column.setAlignment(HorizontalAlignment.RIGHT);
		    column.setRenderer(new GridCellRenderer() {

				@Override
				public Object render(ModelData model_, String property_,
						ColumnData config_, int rowIndex_, int colIndex_,
						ListStore store_, Grid grid_) {
					try{
						return ((Integer)model_.get("minuteCount"))/60D;
					}catch(NullPointerException nex){
						return 0D;
						
					}
				}
		    	
			});
		    
		    
		    
		    final NumberField ttxtMinuteCount = new NumberField();  
		    ttxtMinuteCount.setAllowBlank(false);  
		    ttxtMinuteCount.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
		        public void handleEvent(ComponentEvent be) {
		        	ttxtMinuteCount.selectAll();
		          }});
		    CellEditor minuteCountEditor = new CellEditor(ttxtMinuteCount) {  
			      @Override  
			      public Object preProcessValue(Object value) {  
			        if (value == null) {  
			          return value;  
			        }  
			        return ((Integer)value)/60D;  
			      }  
			  
			      @Override  
			      public Object postProcessValue(Object value) {  
			        if (value == null) {  
			          return value;  
			        }  
			        return ((Double)(((Double)value)*60)).intValue();  
			      }  
			    };  
			    
			    
			    
		  


		    column.setEditor(minuteCountEditor );  
		    configs.add(column);  
	//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End MinuteCount Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("userId");  
	    column.setHeader("Associate");  
	    column.setWidth(100);  
		//cboUserId.setWidth(99); 
		CellEditor cboUserIdEditor = new CellEditor(cboUserId) {  
		      @Override  
		      public Object preProcessValue(Object value) {  
		        if (value == null) {  
		          return value;  
		        }  
		        return cboUserId.findModel(value.toString());  
		      }  
		  
		      @Override  
		      public Object postProcessValue(Object value) {  
		        if (value == null) {  
		          return value;  
		        }  
		        return ((ModelData) value).get("value");  
		      }  
		    };  
		    column.setRenderer(new GridCellRenderer(){

				@Override
				public Object render(ModelData model_, String property_,
						ColumnData config_, int rowIndex_, int colIndex_,
						ListStore store_, Grid grid_) {
					
					return UserInfoCache.getDisplayNameForUserId((String)model_.get("userId"));
				}});
		    	

		 column.setEditor(cboUserIdEditor);
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End UserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

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

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("billRate");  
	    column.setHeader("Rate");  
	    column.setWidth(65);
	    column.setNumberFormat(NumberFormat.getFormat("$0.00"));
	    column.setAlignment(HorizontalAlignment.RIGHT);
	    final NumberField ttxtBillRate = new NumberField();  
	    ttxtBillRate.setAllowBlank(false);  
	    ttxtBillRate.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtBillRate.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtBillRate ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End BillRate Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	    
	    
//	    =-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin Total Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		    column = new ColumnConfig(); 
		    column.setId("Total");  
		    column.setHeader("Total");
		    column.setAlignment(HorizontalAlignment.RIGHT);
		    //column.setNumberFormat(NumberFormat.getFormat("$0.00"));
		    column.setRenderer(new GridCellRenderer(){

				@Override
				public Object render(ModelData model_, String property_,
						ColumnData config_, int rowIndex_, int colIndex_,
						ListStore store_, Grid grid_) {
					if (model_.get("minuteCount") == null){
						return null;
					}else{
					Double total = ((Integer)model_.get("minuteCount"))* ((Double)model_.get("billRate"))/60;
					
					return NumberFormat.getFormat("$0.00").format(total);
					}
				}
		    	
		    });
		    column.setWidth(75);  
		    configs.add(column);
//		    =-=-=-=-=-=-=-=-=-=-=-=-=-=-End Total Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	    

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin EndTime Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("endTime");  
//	    column.setHeader("EndTime");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtEndTime = new TextField<String>();  
//	    ttxtEndTime.setAllowBlank(false);  
//	    ttxtEndTime.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtEndTime.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtEndTime ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End EndTime Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin StartTime Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("startTime");  
//	    column.setHeader("StartTime");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtStartTime = new TextField<String>();  
//	    ttxtStartTime.setAllowBlank(false);  
//	    ttxtStartTime.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtStartTime.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtStartTime ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End StartTime Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



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

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LaborRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("laborRegisterId");  
//	    column.setHeader("LaborRegisterId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtLaborRegisterId = new TextField<String>();  
//	    ttxtLaborRegisterId.setAllowBlank(false);  
//	    ttxtLaborRegisterId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtLaborRegisterId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtLaborRegisterId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LaborRegisterId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Billable Hours");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(775, 200);  
	    cp.setLayout(new FitLayout());  
	  
	    //grid.setAutoExpandColumn(""); 

	    grid.setBorders(true);  
	    grid.setAutoExpandColumn("description");
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    ToolBar toolBar = new ToolBar();  
//	    Button add = new Button("Add LaborRegister");  
//	    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	        
//	        LaborRegisterBean laborRegisterBean  = new LaborRegisterBean();
//
//		laborRegisterBean.setUserId("");
// 		laborRegisterBean.setInvoiceId(0);
// 		laborRegisterBean.setBillRate(0D);
// 		laborRegisterBean.setInvoiceable("");
// 		laborRegisterBean.setActivityDate(new java.util.Date());
// 		laborRegisterBean.setEndTime(new java.util.Date());
// 		laborRegisterBean.setStartTime(new java.util.Date());
// 		laborRegisterBean.setMinuteCount(0);
// 		laborRegisterBean.setDescription("");
// 		laborRegisterBean.setLastUpdate(new java.util.Date());
// 		laborRegisterBean.setCustomerId(0);
// 		laborRegisterBean.setClientId(0);
// 		laborRegisterBean.setLaborRegisterId(0);
//  	        grid.stopEditing();  
//		//store.sort("", SortDir.ASC);
//	        store.insert(laborRegisterBean, 0);
//	        grid.startEditing(0, 0);  
//	      }  
//	  
//	    });  
//	    toolBar.add(add);  
//	    cp.setTopComponent(toolBar);  
//	    cp.setButtonAlign(HorizontalAlignment.CENTER);  
	    
	    
	  
//	    cp.addButton(new Button("Save", new SelectionListener<ButtonEvent>() {  
//	  
//	      @Override  
//	      public void componentSelected(ButtonEvent ce) {  
//	    	saveChanges();
//	         
//	      }  
//	    }));  
//	  
	    add(cp);  
	  }  
	  
	  public void setList(ArrayList<LaborRegisterBean> laborRegisterBeans_ ){
//		  List <LaborRegisterBean> laborRegisterTableModelDataList = new ArrayList <LaborRegisterBean>();
//		  for(int ndx = 0; ndx<laborRegisterBeans_.size(); ndx++){
//			  laborRegisterTableModelDataList.add(laborRegisterBeans_.get(ndx));
//		  }
		  //Log.debug("setlist laborregistertable: " + laborRegisterBeans_.get(0).getInvoiceId());
		  store.setFiresEvents(false);
		  store.removeAll();
		  //store.add(laborRegisterTableModelDataList); 
		  store.add((List <LaborRegisterBean>)laborRegisterBeans_);
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);

		  
	  }
	  public ArrayList<LaborRegisterBean> getList(){		  
		  List<Record> modified = store.getModifiedRecords();
		  LaborRegisterBean laborRegisterBean;// = new CustomerBean();
		  ArrayList<LaborRegisterBean> arrayList = new ArrayList<LaborRegisterBean>();
		  for (Record r : modified) {
			  //Log.debug("Identified Modified Record");
			  laborRegisterBean = new LaborRegisterBean();
			  laborRegisterBean.setProperties(r.getModel().getProperties());
			  //Log.debug("LaborRegisterTable Modified Record: " + laborRegisterBean.getInvoiceId());
			  arrayList.add(laborRegisterBean);
		  }
		  return arrayList;
	  }
	  
//	  public void saveChanges(){
//		  List<Record> modified = store.getModifiedRecords();
//		  LaborRegisterBean laborRegisterBean = new LaborRegisterBean();
//		  ArrayList<LaborRegisterBean> batchSave = new ArrayList<LaborRegisterBean>();
//		  for (Record r : modified) {
//			  laborRegisterBean.setProperties(r.getModel().getProperties());
//			  batchSave.add(laborRegisterBean);
//			  
//			  
//		  }
// 		store.commitChanges();
//		notifier.notifyAppEvent(this, "SaveLaborRegisterBatch", batchSave );
//		  
//	  }
	
	public void onDetach(){
		super.onDetach();
		
			notifier.notifyAppEvent(this, "LaborRegisterTableOnDetach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "LaborRegisterTableOnAttach");
		
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
	public ListStore<LaborRegisterBean> getStore() {
		return store;
	}
	/**
	 * @param cboUserId the cboUserId to set
	 */
	public void setCboUserId(AlternateComboBox<UserInfoBean> cboUserId) {
		this.cboUserId = cboUserId;
	}
	/**
	 * @return the cboUserId
	 */
	public AlternateComboBox<UserInfoBean> getCboUserId() {
		return cboUserId;
	}





//     public void updateSelectedBeansinStore(ArrayList<LaborRegisterBean> laborRegisterResult_) {
//    	int ndxStore;
//    	try{
//    		for(int ndx =0;ndx<laborRegisterResult_.size();ndx++){
//    			
//    			for(ndxStore =0;ndx < store.getCount();ndxStore++){
//    				if( store.getAt(ndxStore).get("laborRegisterId").equals(laborRegisterResult_.get(ndx).getLaborRegisterId())  &&  store.getAt(ndxStore).get("clientId").equals(laborRegisterResult_.get(ndx).getClientId())  &&  store.getAt(ndxStore).get("customerId").equals(laborRegisterResult_.get(ndx).getCustomerId()) ){
//    					store.getAt(ndxStore).setBean( laborRegisterResult_.get(ndx));
//    					//Log.debug("match found: "+ laborRegisterResult_.get(ndx).getPK());
//    					break;
//    					
//    				}else if( store.getAt(ndxStore).get("laborRegisterId").equals(0)  &&  store.getAt(ndxStore).get("customerId").equals(0) 						//&& store.getAt(ndxStore).get("userId").equals(laborRegisterResult_.get(ndx).getUserId())
//						//&& store.getAt(ndxStore).get("invoiceId").equals(laborRegisterResult_.get(ndx).getInvoiceId())
//						//&& store.getAt(ndxStore).get("billRate").equals(laborRegisterResult_.get(ndx).getBillRate())
//						//&& store.getAt(ndxStore).get("invoiceable").equals(laborRegisterResult_.get(ndx).getInvoiceable())
//						//&& store.getAt(ndxStore).get("activityDate").equals(laborRegisterResult_.get(ndx).getActivityDate())
//						//&& store.getAt(ndxStore).get("endTime").equals(laborRegisterResult_.get(ndx).getEndTime())
//						//&& store.getAt(ndxStore).get("startTime").equals(laborRegisterResult_.get(ndx).getStartTime())
//						//&& store.getAt(ndxStore).get("minuteCount").equals(laborRegisterResult_.get(ndx).getMinuteCount())
//						//&& store.getAt(ndxStore).get("description").equals(laborRegisterResult_.get(ndx).getDescription())
//						//&& store.getAt(ndxStore).get("lastUpdate").equals(laborRegisterResult_.get(ndx).getLastUpdate())
//						//&& store.getAt(ndxStore).get("customerId").equals(laborRegisterResult_.get(ndx).getCustomerId())
//						//&& store.getAt(ndxStore).get("clientId").equals(laborRegisterResult_.get(ndx).getClientId())
//						//&& store.getAt(ndxStore).get("laborRegisterId").equals(laborRegisterResult_.get(ndx).getLaborRegisterId())
//					){
//    					store.getAt(ndxStore).setBean( laborRegisterResult_.get(ndx));
//    					//Log.debug("match found: "+ laborRegisterResult_.get(ndx).getField());
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


}


