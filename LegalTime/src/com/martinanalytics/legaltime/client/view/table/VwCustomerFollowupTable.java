
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
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppMsg;
import com.martinanalytics.legaltime.client.AppEvent.AppEvent;
import com.martinanalytics.legaltime.client.AppEvent.AppEventListener;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.VwCustomerFollowupBean;
import com.martinanalytics.legaltime.client.view.FollowupView;
import com.martinanalytics.legaltime.client.widget.AlternateComboBoxBinding;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;


public class VwCustomerFollowupTable extends LayoutContainer implements AppEventListener{
	final ListStore<VwCustomerFollowupBean> store = new ListStore<VwCustomerFollowupBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	//Be sure to distinguish Grid from Editor Grid
	Grid<VwCustomerFollowupBean> grid= new Grid<VwCustomerFollowupBean>(store, cm);
	private FollowupView followupView ;


	public VwCustomerFollowupTable(){
		notifier = new AppNotifyObject();
		store.setMonitorChanges(true);
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin UsrDisplay Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("usrDisplay");  
	    column.setHeader("UsrDisplay");  
	    column.setWidth(100);  
	    final TextField<String> ttxtUsrDisplay = new TextField<String>();  
	    ttxtUsrDisplay.setAllowBlank(false);  
	    ttxtUsrDisplay.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtUsrDisplay.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtUsrDisplay ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End UsrDisplay Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin DisplayName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("displayName");  
	    column.setHeader("Customer");  
	    column.setWidth(100);  
	    final TextField<String> ttxtDisplayName = new TextField<String>();  
	    ttxtDisplayName.setAllowBlank(false);  
	    ttxtDisplayName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtDisplayName.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtDisplayName ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End DisplayName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("lastName");  
//	    column.setHeader("LastName");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtLastName = new TextField<String>();  
//	    ttxtLastName.setAllowBlank(false);  
//	    ttxtLastName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtLastName.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtLastName ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("firstName");  
//	    column.setHeader("FirstName");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtFirstName = new TextField<String>();  
//	    ttxtFirstName.setAllowBlank(false);  
//	    ttxtFirstName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtFirstName.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtFirstName ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin AssignedUserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("assignedUserId");  
//	    column.setHeader("AssignedUserId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtAssignedUserId = new TextField<String>();  
//	    ttxtAssignedUserId.setAllowBlank(false);  
//	    ttxtAssignedUserId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtAssignedUserId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtAssignedUserId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End AssignedUserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FollowupDescription Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("followupDescription");  
	    column.setHeader("Description");  
	    column.setWidth(100);  
	    final TextField<String> ttxtFollowupDescription = new TextField<String>();  
	    ttxtFollowupDescription.setAllowBlank(false);  
	    ttxtFollowupDescription.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtFollowupDescription.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtFollowupDescription ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FollowupDescription Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CloseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("closeDt");  
	    column.setHeader("Closed");  
	    column.setWidth(100);  
	    final TextField<String> ttxtCloseDt = new TextField<String>();  
	    ttxtCloseDt.setAllowBlank(false);  
	    ttxtCloseDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtCloseDt.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtCloseDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CloseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin OpenDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("openDt");  
	    column.setHeader("OpenDt");  
	    column.setWidth(100);  
	    final TextField<String> ttxtOpenDt = new TextField<String>();  
	    ttxtOpenDt.setAllowBlank(false);  
	    ttxtOpenDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtOpenDt.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtOpenDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End OpenDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin DueDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("dueDt");  
	    column.setHeader("Due");  
	    column.setWidth(100);  
	    final TextField<String> ttxtDueDt = new TextField<String>();  
	    ttxtDueDt.setAllowBlank(false);  
	    ttxtDueDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
	        public void handleEvent(ComponentEvent be) {
	        	ttxtDueDt.selectAll();
	          }});


	    column.setEditor(new CellEditor( ttxtDueDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End DueDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

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

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FollowupId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("followupId");  
//	    column.setHeader("FollowupId");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtFollowupId = new TextField<String>();  
//	    ttxtFollowupId.setAllowBlank(false);  
//	    ttxtFollowupId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtFollowupId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtFollowupId ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FollowupId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	   
	  
	  
	    ContentPanel cp = new ContentPanel();  
	    cp.setHeading("Customer Followup Manager");  
	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(900, 500);  
	    cp.setLayout(new FitLayout());  
	  
	    //grid.setAutoExpandColumn("");  
	    grid.setBorders(true); 
	    grid.setAutoExpandColumn("followupDescription");
	    grid.getSelectionModel().addListener(Events.SelectionChange,  
				new Listener<SelectionChangedEvent<FollowupBean>>() {  
					public void handleEvent(SelectionChangedEvent<FollowupBean> be) { 
						Log.debug("Followupcostomertable selection detected");
						if (be.getSelection().size() > 0) {  
							formBindings.bind((ModelData) be.getSelection().get(0));
							
							//followupView.getCboAssignedUser().setValue(be.getSelection().get(0).getAssignedUserId());
							notifier.notifyAppEvent(this, AppMsg.SHOW_FOLLOWUP_EDITOR, "MANAGER");
						} else {  
							formBindings.unbind();  
						}  
					}  
		}); 
	    //grid.addPlugin(checkColumn);  
	    cp.add(grid);  
	  
	    ToolBar toolBar = new ToolBar();  
	    Button add = new Button("Add Followup");  
	    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
	        
	        VwCustomerFollowupBean vwCustomerFollowupBean  = new VwCustomerFollowupBean();

		vwCustomerFollowupBean.setUsrDisplay("");
 		vwCustomerFollowupBean.setDisplayName("");
 		vwCustomerFollowupBean.setLastName("");
 		vwCustomerFollowupBean.setFirstName("");
 		vwCustomerFollowupBean.setAssignedUserId("");
 		vwCustomerFollowupBean.setFollowupDescription("");
 		vwCustomerFollowupBean.setCloseDt(new java.util.Date());
 		vwCustomerFollowupBean.setOpenDt(new java.util.Date());
 		vwCustomerFollowupBean.setDueDt(new java.util.Date());
 		vwCustomerFollowupBean.setLastUpdate(new java.util.Date());
 		vwCustomerFollowupBean.setCustomerId(0);
 		vwCustomerFollowupBean.setClientId(0);
 		vwCustomerFollowupBean.setFollowupId(0);
  	        //grid.stopEditing();  
		//store.sort("", SortDir.ASC);
	        store.insert(vwCustomerFollowupBean, 0);
	        //grid.startEditing(0, 0);  
	      }  
	  
	    });  
	    toolBar.add(add);  
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
	  
	  public void setList(ArrayList<VwCustomerFollowupBean> vwCustomerFollowupBeans_ ){
		  List <VwCustomerFollowupBean> vwCustomerFollowupTableModelDataList = new ArrayList <VwCustomerFollowupBean>();
		  for(int ndx = 0; ndx<vwCustomerFollowupBeans_.size(); ndx++){
			  vwCustomerFollowupTableModelDataList.add(vwCustomerFollowupBeans_.get(ndx));
		  }
		  store.setFiresEvents(false);
		  store.removeAll();
		  store.add(vwCustomerFollowupTableModelDataList); 
		  store.setFiresEvents(true);
		  grid.getView().refresh(false);

		  
	  }
	  
	  public void saveChanges(){
		  List<Record> modified = store.getModifiedRecords();
		  VwCustomerFollowupBean vwCustomerFollowupBean = new VwCustomerFollowupBean();
		  ArrayList<VwCustomerFollowupBean> batchSave = new ArrayList<VwCustomerFollowupBean>();
		  for (Record r : modified) {
			  vwCustomerFollowupBean.setProperties(r.getModel().getProperties());
			  batchSave.add(vwCustomerFollowupBean);
			  
			  
		  }
 		store.commitChanges();
		notifier.notifyAppEvent(this, "SaveVwCustomerFollowupBatch", batchSave );
		  
	  }
	
	public void onDetach(){
		super.onDetach();
		saveChanges();
			notifier.notifyAppEvent(this, "VwCustomerFollowupTableOnAttach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "VwCustomerFollowupTableOnAttach");
		
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
	public ListStore<VwCustomerFollowupBean> getStore() {
		return store;
	}





//     public void updateSelectedBeansinStore(ArrayList<VwCustomerFollowupBean> vwCustomerFollowupResult_) {
//    	int ndxStore;
//    	try{
//    		for(int ndx =0;ndx<vwCustomerFollowupResult_.size();ndx++){
//    			
//    			for(ndxStore =0;ndx < store.getCount();ndxStore++){
//    				if(){
//    					store.getAt(ndxStore).setBean( vwCustomerFollowupResult_.get(ndx));
//    					//Log.debug("match found: "+ vwCustomerFollowupResult_.get(ndx).getPK());
//    					break;
//    					
//    				}else if(						//&& store.getAt(ndxStore).get("usrDisplay").equals(vwCustomerFollowupResult_.get(ndx).getUsrDisplay())
//						//&& store.getAt(ndxStore).get("displayName").equals(vwCustomerFollowupResult_.get(ndx).getDisplayName())
//						//&& store.getAt(ndxStore).get("lastName").equals(vwCustomerFollowupResult_.get(ndx).getLastName())
//						//&& store.getAt(ndxStore).get("firstName").equals(vwCustomerFollowupResult_.get(ndx).getFirstName())
//						//&& store.getAt(ndxStore).get("assignedUserId").equals(vwCustomerFollowupResult_.get(ndx).getAssignedUserId())
//						//&& store.getAt(ndxStore).get("followupDescription").equals(vwCustomerFollowupResult_.get(ndx).getFollowupDescription())
//						//&& store.getAt(ndxStore).get("closeDt").equals(vwCustomerFollowupResult_.get(ndx).getCloseDt())
//						//&& store.getAt(ndxStore).get("openDt").equals(vwCustomerFollowupResult_.get(ndx).getOpenDt())
//						//&& store.getAt(ndxStore).get("dueDt").equals(vwCustomerFollowupResult_.get(ndx).getDueDt())
//						//&& store.getAt(ndxStore).get("lastUpdate").equals(vwCustomerFollowupResult_.get(ndx).getLastUpdate())
//						//&& store.getAt(ndxStore).get("customerId").equals(vwCustomerFollowupResult_.get(ndx).getCustomerId())
//						//&& store.getAt(ndxStore).get("clientId").equals(vwCustomerFollowupResult_.get(ndx).getClientId())
//						//&& store.getAt(ndxStore).get("followupId").equals(vwCustomerFollowupResult_.get(ndx).getFollowupId())
//					){
//    					store.getAt(ndxStore).setBean( vwCustomerFollowupResult_.get(ndx));
//    					//Log.debug("match found: "+ vwCustomerFollowupResult_.get(ndx).getField());
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

	public void setFollowupView(FollowupView followupView_) {
		followupView = followupView_;
		formBindings = new FormBinding(followupView.getFollowupFormPanel(), true);
	     AlternateComboBoxBinding assignedUserBinding = new AlternateComboBoxBinding (formBindings, followupView.getCboAssignedUser());
	     AlternateComboBoxBinding customerBinding = new AlternateComboBoxBinding (formBindings, followupView.getCboCustomerId());
	     formBindings.setStore( grid.getStore());
	}
	
	 
	
	
	@Override
	public void onAppEventNotify(AppEvent e_) {
		if(e_.getName().equals(AppMsg.FOLLOWUP_EDITOR_CLOSING) && ((String)e_.getPayLoad()).equals("MANAGER")){
			  store.getRecord((VwCustomerFollowupBean)formBindings.getModel()).set("usrDisplay", followupView.getCboAssignedUser().getValue().get("displayName"));
			  store.getRecord((VwCustomerFollowupBean)formBindings.getModel()).set("displayName", followupView.getCboCustomerId().getValue().get("displayName"));
			  formBindings.unbind();
			  grid.getSelectionModel().deselectAll();
			 
		}else if(e_.getName().equals(AppMsg.FOLLOWUP_EDITOR_CANCELED) && ((String)e_.getPayLoad()).equals("MANAGER")){
			 store.rejectChanges();
		}
		
	}

}


