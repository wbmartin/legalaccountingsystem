
package com.martinanalytics.legaltime.client.view.table;

import java.util.ArrayList;
import java.util.List;




import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.SimpleComboBoxFieldBinding;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
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
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.martinanalytics.legaltime.client.AppEvent.AppNotifyObject;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;
import com.martinanalytics.legaltime.client.model.bean.FollowupBean;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;
import com.martinanalytics.legaltime.client.view.FollowupView;
import com.extjs.gxt.ui.client.event.ButtonEvent; 
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.binding.FormBinding;



public class FollowupTableCustomerPerspective extends LayoutContainer {
	final ListStore<FollowupBean> store = new ListStore<FollowupBean>(); 
	private AppNotifyObject notifier;
	FormBinding formBindings;
	FollowupView followupView ;
	final Dialog followupEditorDialog ;
	List<ColumnConfig> configs= new ArrayList<ColumnConfig>();
	ColumnModel cm =new ColumnModel(configs);
	Grid<FollowupBean> grid= new Grid<FollowupBean>(store, cm);
	
	
	public FollowupTableCustomerPerspective(){
		notifier = new AppNotifyObject();
		followupView = new FollowupView();
		
		followupEditorDialog = new Dialog();
	
		
		
		formBindings = new FormBinding(followupView.getFollowupFormPanel(), true);
		formBindings.removeFieldBinding(formBindings.getBinding(followupView.getCboAssignedUserId()));
		
		//formBindings.
	     grid.getView().setEmptyText("No Open Tasks");
		    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
		    
			grid.getSelectionModel().addListener(Events.SelectionChange,  
					new Listener<SelectionChangedEvent<FollowupBean>>() {  
						public void handleEvent(SelectionChangedEvent<FollowupBean> be) { 
							Log.debug("Followupcostomertable selection detected");
							if (be.getSelection().size() > 0) {  
								formBindings.bind((ModelData) be.getSelection().get(0));
								followupView.populateAssignedUserCBO(true);
//								for(int ndx =0; ndx < followupView.getLbtest().getItemCount();ndx++){
//									if(followupView.getLbtest().getValue(ndx).equals(be.getSelection().get(0).getAssignedUserId())){
//										followupView.getLbtest().setSelectedIndex(ndx);
//										break;
//									}
//								}
								followupView.getLbtest().setSelected(be.getSelection().get(0).getAssignedUserId());
								
								followupView.getCboAssignedUserId().setRawValue(be.getSelection().get(0).getAssignedUserId());
								//followupView.getCboAssignedUserId().setRawValue("bmartin");
								//followupView.getCboAssignedUserId().repaint();
								followupEditorDialog.show();
								
								
								//followupView.getCboAssignedUserId().setValueField(be.getSelection().get(0).getAssignedUserId());
//								for(int ndx =0; ndx < followupView.getUserInfoBeanStore().getCount();ndx++){
//									if(followupView.getUserInfoBeanStore().getAt(ndx).getUserId().equals(be.getSelection().get(0).getAssignedUserId())){
//										followupView.getCboAssignedUserId().select
//										
//										Log.debug("testing match" +ndx);
//										break;
//									}
//								}
							} else {  
								formBindings.unbind();  
							}  
						}  
			});  
		    grid.setBorders(true);
	     
	     formBindings.setStore( grid.getStore());
		
		  BorderLayout layout = new BorderLayout(); 
    	  followupEditorDialog.setLayout(layout);
    	  followupEditorDialog.add(followupView.getFollowupFormPanel());
        
    	  followupEditorDialog.setWidth(500);
    	  followupEditorDialog.setModal(true);
    	  followupEditorDialog.setButtons(Dialog.OKCANCEL);
    	  followupEditorDialog.setHideOnButtonClick(true);
    	  followupEditorDialog.setClosable(false);
    	  followupEditorDialog.getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() { 

    		  @Override  
    		  public void componentSelected(ButtonEvent ce) { 
    			  store.rejectChanges();
    			  formBindings.unbind();
    		  }
    	  }

    	  );
    	  followupEditorDialog.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() { 

    		  @Override  
    		  public void componentSelected(ButtonEvent ce) { 
    			  //grid.getSelectionModel().getSelectedItem().setAssignedUserId(followupView.getCboAssignedUserId().getRawValue());
    			  
    			  Record record = store.getRecord(grid.getSelectionModel().getSelectedItem());
    		        record.set("assignedUserId",followupView.getCboAssignedUserId().getRawValue());
    			  formBindings.unbind();
    		  }
    	  }

    	  );
    	  
	}
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	  
	    setLayout(new FlowLayout(10));  
	    
	  
	    ColumnConfig column; 
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin AssignedUserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("assignedUserId");  
	    column.setHeader("Owner");  
	    column.setWidth(100);  
//	    final TextField<String> ttxtAssignedUserId = new TextField<String>();  
//	    ttxtAssignedUserId.setAllowBlank(false);  
//	    ttxtAssignedUserId.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtAssignedUserId.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtAssignedUserId ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End AssignedUserId Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FollowupDescription Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("followupDescription");  
	    column.setHeader("Description");  
	    column.setWidth(450);  
//	    final TextField<String> ttxtFollowupDescription = new TextField<String>();  
//	    ttxtFollowupDescription.setAllowBlank(false);  
//	    ttxtFollowupDescription.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtFollowupDescription.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtFollowupDescription ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FollowupDescription Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin CloseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//	    column = new ColumnConfig(); 
//	    column.setId("closeDt");  
//	    column.setHeader("CloseDt");  
//	    column.setWidth(100);  
//	    final TextField<String> ttxtCloseDt = new TextField<String>();  
//	    ttxtCloseDt.setAllowBlank(false);  
//	    ttxtCloseDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtCloseDt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtCloseDt ));  
//	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End CloseDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin OpenDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("openDt");  
	    column.setHeader("Opened");  
	    column.setWidth(100);  
	    column.setDateTimeFormat(DateTimeFormat.getShortDateFormat());
//	    final TextField<String> ttxtOpenDt = new TextField<String>();  
//	    ttxtOpenDt.setAllowBlank(false);  
//	    ttxtOpenDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtOpenDt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtOpenDt ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End OpenDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin DueDt Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("dueDt");  
	    column.setHeader("Due");  
	    column.setDateTimeFormat(DateTimeFormat.getShortDateFormat());
	    column.setWidth(100);

//	    final TextField<String> ttxtDueDt = new TextField<String>();  
//	    ttxtDueDt.setAllowBlank(false);  
//	    ttxtDueDt.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtDueDt.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtDueDt ));  
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
	    cp.setHeading("Customer Followup");

	    cp.setFrame(true);  
	    //cp.setIcon(Examples.ICONS.table());  
	    cp.setSize(750, 150);  
	    cp.setLayout(new FitLayout());  
	  

	     
	   
	    cp.add(grid);  
	  
	    ToolBar toolBar = new ToolBar(); 

	    
	    Button add = new Button("Add Followup");  
	    add.addSelectionListener(new SelectionListener<ButtonEvent>() {  
	  
	      @Override  
	      public void componentSelected(ButtonEvent ce) {  
		    FollowupBean followupBean  = new FollowupBean();
	  		followupBean.setAssignedUserId("");
	   		followupBean.setFollowupDescription("!New");
	   		followupBean.setCloseDt(null);
	   		followupBean.setOpenDt(new java.util.Date());
	   		followupBean.setDueDt(new java.util.Date());
	   		followupBean.setLastUpdate(new java.util.Date());
	   		followupBean.setCustomerId(0);
	   		followupBean.setClientId(0);
	   		followupBean.setFollowupId(0);
	    	//grid.stopEditing();  
	    	store.sort("followupDescription", SortDir.ASC);
	  	    store.insert(followupBean, 0);
	  	    //grid.startEditing(0, 0); 
	  	    
	  	  
	  	    
	  	   
			
			formBindings.bind(followupBean  );
	    	  
	    	
	    	  followupEditorDialog.show();
 
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
//	  
	    add(cp);  
	  }  
	  
	  public void setList(ArrayList<FollowupBean> followupBeans_ ){
		  List <FollowupBean> followupTableModelDataList = new ArrayList <FollowupBean>();
		  for(int ndx = 0; ndx<followupBeans_.size(); ndx++){
			  followupTableModelDataList.add(followupBeans_.get(ndx));
		  }
		  store.removeAll();
		  store.add(followupTableModelDataList); 
//		  if (followupBeans_.size()>0){
//			  grid.getSelectionModel().select(0,false);
//		  }
		  
	  }
	  
	  public void saveChanges(){
		  List<Record> modified = store.getModifiedRecords();
		  FollowupBean followupBean = new FollowupBean();
		  ArrayList<FollowupBean> batchSave = new ArrayList<FollowupBean>();
		  for (Record r : modified) {
			  followupBean.setProperties(r.getModel().getProperties());
			  batchSave.add(followupBean);
			  
			  
		  }
 		store.commitChanges();
		notifier.notifyAppEvent(this, "SaveFollowupBatch", batchSave );
		  
	  }
	
	public void onDetach(){
		super.onDetach();
		saveChanges();
			notifier.notifyAppEvent(this, "FollowupTableOnAttach");
	}
	public void onAttach(){
		super.onAttach();
		notifier.notifyAppEvent(this, "FollowupTableOnAttach");
		
		
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
	public ListStore<FollowupBean> getStore() {
		return store;
	}

}


