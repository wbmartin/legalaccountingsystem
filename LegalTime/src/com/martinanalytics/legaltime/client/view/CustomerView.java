

package com.martinanalytics.legaltime.client.view;


import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.CustomerBean;

import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.martinanalytics.legaltime.client.view.table.CustomerTable;
import com.martinanalytics.legaltime.client.view.table.VwCustomerHourlyBillRateTable;
import com.martinanalytics.legaltime.client.widget.AppContainer;
import com.martinanalytics.legaltime.client.widget.GXTValidator;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;


import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreFilter;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.SimpleComboBoxFieldBinding;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.layout.RowData;  
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.event.Events;  
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style;

public class CustomerView extends AppEventProducer{
	
	UserProfile userProfile;
	private TextField<String> txtActiveYn = new TextField<String>();
	private NumberField txtMonthlyBillRate = new NumberField();
	private SimpleComboBox<String> cboBillType = new SimpleComboBox<String>();
	private TextField<String> txtNote = new TextField<String>();
	private DateField dtpClientSinceDt = new DateField();
	private TextField<String> txtEmail = new TextField<String>();
	private TextField<String> txtFax = new TextField<String>();
	private TextField<String> txtHomePhone = new TextField<String>();
	private TextField<String> txtWorkPhone = new TextField<String>();
	private TextField<String> txtZip = new TextField<String>();
	private TextField<String> txtState = new TextField<String>();
	private TextField<String> txtCity = new TextField<String>();
	private TextField<String> txtAddress = new TextField<String>();
	private TextField<String> txtLastName = new TextField<String>();
	private TextField<String> txtFirstName = new TextField<String>();
	private TextField<java.util.Date> txtLastUpdate = new TextField<java.util.Date>();
	private TextField<Integer> txtClientId = new TextField<Integer>();
	private TextField<Integer> txtCustomerId = new TextField<Integer>();
	//private ListField<CustomerBean> lstCustomerChooser = new ListField<CustomerBean>();
	private final ListStore<CustomerBean> store = new ListStore<CustomerBean>();
	private FormBinding formBindings;    
	private FormPanel customerFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  
	private Grid<CustomerBean> grid ;
	private	List<ColumnConfig> configs = new ArrayList<ColumnConfig>(); 
	private ColumnModel cm;
	private VwCustomerHourlyBillRateTable vwCustomerHourlyBillRateTable = new VwCustomerHourlyBillRateTable();

	private final int LABEL_WIDTH =75;

	private CustomerComposite customerComposite;
	public CustomerView(){
		userProfile = UserProfile.getInstance();
		customerComposite =new CustomerComposite();
	}
	/**
	 * @return the txtActiveYn
	 */
	public TextField<String> getTxtActiveYn() {
		return txtActiveYn;
	}
	/**
	 * @return the txtMonthlyBillRate
	 */
	public NumberField getTxtMonthlyBillRate() {
		return txtMonthlyBillRate;
	}
	/**
	 * @return the txtBillType
	 */
	public SimpleComboBox<String> getCboBillType() {
		return cboBillType;
	}
	/**
	 * @return the txtNote
	 */
	public TextField<String> getTxtNote() {
		return txtNote;
	}
	/**
	 * @return the txtClientSinceDt
	 */
	public DateField getDtpClientSinceDt() {
		return dtpClientSinceDt;
	}
	/**
	 * @return the txtEmail
	 */
	public TextField<String> getTxtEmail() {
		return txtEmail;
	}
	/**
	 * @return the txtFax
	 */
	public TextField<String> getTxtFax() {
		return txtFax;
	}
	/**
	 * @return the txtHomePhone
	 */
	public TextField<String> getTxtHomePhone() {
		return txtHomePhone;
	}
	/**
	 * @return the txtWorkPhone
	 */
	public TextField<String> getTxtWorkPhone() {
		return txtWorkPhone;
	}
	/**
	 * @return the txtZip
	 */
	public TextField<String> getTxtZip() {
		return txtZip;
	}
	/**
	 * @return the txtState
	 */
	public TextField<String> getTxtState() {
		return txtState;
	}
	/**
	 * @return the txtCity
	 */
	public TextField<String> getTxtCity() {
		return txtCity;
	}
	/**
	 * @return the txtAddress
	 */
	public TextField<String> getTxtAddress() {
		return txtAddress;
	}
	/**
	 * @return the txtLastName
	 */
	public TextField<String> getTxtLastName() {
		return txtLastName;
	}
	/**
	 * @return the txtFirstName
	 */
	public TextField<String> getTxtFirstName() {
		return txtFirstName;
	}
	/**
	 * @return the txtLastUpdate
	 */
	public TextField<java.util.Date> getTxtLastUpdate() {
		return txtLastUpdate;
	}
	/**
	 * @return the txtClientId
	 */
	public TextField<Integer> getTxtClientId() {
		return txtClientId;
	}
	/**
	 * @return the txtCustomerId
	 */
	public TextField<Integer> getTxtCustomerId() {
		return txtCustomerId;
	}
	/**
	 * @return the customerComposite
	 */
	public CustomerComposite getCustomerComposite() {
		return customerComposite;
	}


//	/**
//	 * @param customerTable the customerTable to set
//	 */
//	public void setCustomerTable(CustomerTable customerTable) {
//		this.customerTable = customerTable;
//	}
//	/**
//	 * @return the customerTable
//	 */
//	public CustomerTable getCustomerTable() {
//		return customerTable;
//	}


class CustomerComposite extends Composite{
	public CustomerComposite(){
		customerFormPanel.setFrame(true);
		customerFormPanel.setHeaderVisible(false);
		customerFormPanel.setWidth(600);
		customerFormPanel.setLabelWidth(LABEL_WIDTH);
		createFields();   
		cp.setHeading("Customer Editor");  
		cp.setFrame(true);
		cp.setSize(800, 1000);  
		cp.setLayout(new RowLayout(Orientation.HORIZONTAL)); 
		cp.setScrollMode(Scroll.NONE);
		cm = createColumnModel();
		grid = new Grid<CustomerBean>(getStore(), cm);
		
		customerFormPanel.setScrollMode(Scroll.NONE);
		formBindings = new FormBinding(getCustomerFormPanel(), true);
		formBindings.setStore((Store<CustomerBean>) getGrid().getStore()); 
		formBindings.removeFieldBinding((formBindings.getBinding(cboBillType)));
		formBindings.addFieldBinding(new SimpleComboBoxFieldBinding(cboBillType, "billType"));
//-----------------------
		getGrid().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
		getGrid().getSelectionModel().addListener(Events.SelectionChange,  
				new Listener<SelectionChangedEvent<CustomerBean>>() {  
					public void handleEvent(SelectionChangedEvent<CustomerBean> be) {  
						if (be.getSelection().size() > 0) {  
							if(txtLastName.getValue()==null || customerFormPanel.isValid()){
								formBindings.bind((ModelData) be.getSelection().get(0)); 
								notifyAppEvent(this,"CustomerChange", be.getSelection().get(0).getCustomerId());
							}else{
								notifyAppEvent(this, "UserMessage", "Please Correct Validation Errors");
							}
						} else {  
							formBindings.unbind();  
						}  
					}  
		});  
		//------------------
	      
		ToolBar toolBar = new ToolBar();  
		Button addCustomer = new Button("Add Customer");  
		addCustomer.setBorders(true);
		addCustomer.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				notifyAppEvent(this, "AddCustomer"); 
			}  
		    }); 
		    
		    toolBar.add(addCustomer); 
		    Button saveCustomers = new Button("Save"); 
		    saveCustomers.setBorders(true);
		    saveCustomers.addListener(Events.Select, new Listener<ComponentEvent>() {
			      public void handleEvent(ComponentEvent be) {
		    	  notifyAppEvent(this, "UserRequestedSave");
		      }
		    });
		    toolBar.add(saveCustomers);
		    //--------------------
		    Button cancelChanges =new Button("Cancel Changes");
		    cancelChanges.setBorders(true);
		    cancelChanges.addListener(Events.Select, new Listener<ComponentEvent>() {
			      public void handleEvent(ComponentEvent be) {
		    	  store.rejectChanges();
		      }
		    });
		    toolBar.add(cancelChanges);
		    //-----------------------
		    Button deleteCustomer = new Button("Delete");
		    deleteCustomer.setBorders(true);
		    deleteCustomer.addListener(Events.Select, new Listener<ComponentEvent>() {
			      public void handleEvent(ComponentEvent be) {
  
			    	  txtActiveYn.setValue("N");
			    	  store.filter("activeYn","Y");
			    	  getGrid().getSelectionModel().select(0, false);
			    	  
			      }
			    });
		    toolBar.add(deleteCustomer);
		    //-----------------------
		    
		    //-----------------
		    cp.setTopComponent(toolBar);  
		    
		    
		    //------------------------
		  grid.getView().setEmptyText("No Customers Loaded");  
		  grid.setBorders(true);  
		  grid.setWidth(300);
		  grid.setHeight(200);
		  customerFormPanel.setWidth(400);
		  customerFormPanel.setHeight(350);
		  customerFormPanel.setScrollMode(Scroll.AUTO);
		  cp.add(grid, new RowData(325	, 350));
		  cp.add(customerFormPanel, new RowData(400, 350));
	      initWidget(cp);


	}
	
	public void createFields(){


		txtFirstName.setFieldLabel("FirstName");
		txtFirstName.setName("firstName");
		txtFirstName.setFireChangeEventOnSetValue(true);
		//txtFirstName.setRegex("[0-9]+");
		txtFirstName.setAutoValidate(true);
		//txtFirstName.setMinLength(1);
		txtFirstName.setAllowBlank(false);
//		Validator vTxtFirstName = new Validator(){
//
//			@Override
//			public String validate(Field<?> field_, String value_) {
//				if(field_.getValue().equals("ok")){
//					return "invalid String";
//				}else{
//					return null;
//				}
//			}
//		};
//		txtFirstName.setValidator(vTxtFirstName);
		getCustomerFormPanel().add(txtFirstName);	
		

		txtLastName.setFieldLabel("LastName");
		txtLastName.setName("lastName");
		getCustomerFormPanel().add(txtLastName);



		txtAddress.setFieldLabel("Address");
		txtAddress.setName("address");
		getCustomerFormPanel().add(txtAddress);


		

		txtCity.setFieldLabel("City");
		txtCity.setName("city");
		getCustomerFormPanel().add(txtCity);
		

		txtState.setFieldLabel("State");
		txtState.setName("state");
		getCustomerFormPanel().add(txtState);
		txtState.setMaxLength(2);

		txtZip.setFieldLabel("Zip");
		txtZip.setName("zip");
		getCustomerFormPanel().add(txtZip);

		txtEmail.setFieldLabel("Email");
		txtEmail.setName("email");
		getCustomerFormPanel().add(txtEmail);

		txtWorkPhone.setFieldLabel("WorkPhone");
		txtWorkPhone.setName("workPhone");
		getCustomerFormPanel().add(txtWorkPhone);

		txtHomePhone.setFieldLabel("HomePhone");
		txtHomePhone.setName("homePhone");
		getCustomerFormPanel().add(txtHomePhone);

		txtFax.setFieldLabel("Fax");
		txtFax.setName("fax");
		getCustomerFormPanel().add(txtFax);

		dtpClientSinceDt.setFieldLabel("Client Since");
		dtpClientSinceDt.setName("clientSinceDt");
		getCustomerFormPanel().add(dtpClientSinceDt);

		txtNote.setFieldLabel("Note");
		txtNote.setName("note");
		txtNote.setWidth(300);
		txtNote.setHeight(200);
		getCustomerFormPanel().add(txtNote);

		cboBillType.setFieldLabel("Bill Type");
		cboBillType.setName("billType");
		cboBillType.add("MONTHLY");
		cboBillType.add("HOURLY");
		cboBillType.addStyleName("LEFT");
		//cboBillType.set
		cboBillType.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

		      @Override
		      public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
		        if(se.getSelectedItem() == null || se.getSelectedItem().get("value").equals("HOURLY")){
		        	txtMonthlyBillRate.setVisible(false);
		        	getVwCustomerHourlyBillRateTable().setVisible(true);
		        }else{
		        		txtMonthlyBillRate.setVisible(true);
		        		getVwCustomerHourlyBillRateTable().setVisible(false);
		        
		        }
		      
		    }});
		cboBillType.setTriggerAction(TriggerAction.ALL);

		customerFormPanel.add(cboBillType);
		
		txtMonthlyBillRate.setFieldLabel("MonthlyBillRate");
		txtMonthlyBillRate.setName("monthlyBillRate");
		txtMonthlyBillRate.setRegex(GXTValidator.DOUBLE);
		txtMonthlyBillRate.setAutoValidate(true);
		getCustomerFormPanel().add(txtMonthlyBillRate);

		customerFormPanel.add(getVwCustomerHourlyBillRateTable());
//------------------------------------

		txtLastUpdate.setFieldLabel("LastUpdate");
		txtLastUpdate.setName("lastUpdate");
		getCustomerFormPanel().add(txtLastUpdate);
		txtLastUpdate.setVisible(false);


		txtClientId.setFieldLabel("ClientId");
		txtClientId.setName("clientId");
		getCustomerFormPanel().add(txtClientId);
		txtClientId.setVisible(false);


		txtCustomerId.setFieldLabel("CustomerId");
		txtCustomerId.setName("customerId");
		getCustomerFormPanel().add(txtCustomerId);
		txtCustomerId.setVisible(false);
	
		txtActiveYn.setFieldLabel("ActiveYn");
		txtActiveYn.setName("activeYn");
		txtActiveYn.setFireChangeEventOnSetValue(true);
		getCustomerFormPanel().add(txtActiveYn);
		txtActiveYn.setVisible(false);
		

	}
	public void onAttach(){
		if(!isAttached()){
			super.onAttach();
			notifyAppEvent(this, "CustomerViewOnAttach");
		}
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "CustomerViewOnDetach");
	}
	
	

}

///**
// * @param lstCustomerChooser the lstCustomerChooser to set
// */
//public void setLstCustomerChooser(ListField<CustomerBean> lstCustomerChooser) {
//	this.lstCustomerChooser = lstCustomerChooser;
//}
///**
// * @return the lstCustomerChooser
// */
//public ListField<CustomerBean> getLstCustomerChooser() {
//	return lstCustomerChooser;
//}

public ColumnModel createColumnModel(){
 
	  
    ColumnConfig column; 

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    column = new ColumnConfig();
    
    column.setId("lastName");  
    column.setHeader("LastName");  
    column.setWidth(100);  
//    final TextField<String> ttxtLastName = new TextField<String>();  
//    ttxtLastName.setAllowBlank(false);  
//    ttxtLastName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//        public void handleEvent(ComponentEvent be) {
//        	ttxtLastName.selectAll();
//          }});
//
//
//    column.setEditor(new CellEditor( ttxtLastName ));  

    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End LastName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    column = new ColumnConfig(); 
    column.setId("firstName");  
    column.setHeader("FirstName");  
    column.setWidth(100);  
//    final TextField<String> ttxtFirstName = new TextField<String>();  
//    ttxtFirstName.setAllowBlank(false);  
//    ttxtFirstName.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//        public void handleEvent(ComponentEvent be) {
//        	ttxtFirstName.selectAll();
//          }});
//
//
//    column.setEditor(new CellEditor( ttxtFirstName ));  
    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End FirstName Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Begin City Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    column = new ColumnConfig(); 
	    column.setId("city");  
	    column.setHeader("City");  
	    column.setWidth(100);  
//	    final TextField<String> ttxtCity = new TextField<String>();  
//	    ttxtCity.setAllowBlank(false);  
//	    ttxtCity.addListener(Events.OnFocus,new Listener<ComponentEvent>() {
//	        public void handleEvent(ComponentEvent be) {
//	        	ttxtCity.selectAll();
//	          }});
//
//
//	    column.setEditor(new CellEditor( ttxtCity ));  
	    configs.add(column);  
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-End City Column=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	    
	  // tore.setMonitorChanges(true);  

	   return new ColumnModel(configs);
}
/**
 * @return the store
 */
public ListStore<CustomerBean> getStore() {
	return store;
}


/**
 * @return the customerFormPanel
 */
public FormPanel getCustomerFormPanel() {
	return customerFormPanel;
}
/**
 * @param vwCustomerHourlyBillRateTable the vwCustomerHourlyBillRateTable to set
 */
public void setVwCustomerHourlyBillRateTable(
		VwCustomerHourlyBillRateTable vwCustomerHourlyBillRateTable) {
	this.vwCustomerHourlyBillRateTable = vwCustomerHourlyBillRateTable;
}
/**
 * @return the vwCustomerHourlyBillRateTable
 */
public VwCustomerHourlyBillRateTable getVwCustomerHourlyBillRateTable() {
	return vwCustomerHourlyBillRateTable;
}
/**
 * @param cp the cp to set
 */
public void setCp(ContentPanel cp) {
	this.cp = cp;
}
/**
 * @return the cp
 */
public ContentPanel getCp() {
	return cp;
}

/**
 * @return the grid
 */
public Grid<CustomerBean> getGrid() {
	return grid;
}


}

