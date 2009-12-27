
package com.martinanalytics.legaltime.client.view;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.martinanalytics.legaltime.client.AppPref;
import com.martinanalytics.legaltime.client.AppEvent.AppEventProducer;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.martinanalytics.legaltime.client.view.table.VwCustomerFollowupTable;
import com.martinanalytics.legaltime.client.widget.GXTValidator;




public class VwCustomerFollowupView extends AppEventProducer{
	
	private UserProfile userProfile;
	private FormPanel vwCustomerFollowupFormPanel = new FormPanel();
	private ContentPanel cp = new ContentPanel();  

	private TextField<String> txtLastName = new TextField<String>();
 	private TextField<String> txtFirstName = new TextField<String>();
 	private TextField<String> txtFollowupDescription = new TextField<String>();
 	private DateField dtfCloseDt = new DateField();
 	private DateField dtfOpenDt = new DateField();
 	private DateField dtfDueDt = new DateField();
 	private NumberField nbrFollowupId = new NumberField();
  	private VwCustomerFollowupComposite vwCustomerFollowupComposite;
  	//VwCustomerFollowupTable vwCustomerFollowupTable;
  	//private VwCustomerFollowupTable vwCustomerFollowupTable;
	public VwCustomerFollowupView( VwCustomerFollowupTable vwCustomerFollowupTable_){
		userProfile = UserProfile.getInstance();
		//vwCustomerFollowupTable =vwCustomerFollowupTable_;
		vwCustomerFollowupComposite = new VwCustomerFollowupComposite(vwCustomerFollowupTable_);
	}
		/**
	 * @return the vwCustomerFollowupComposite
	 */
	public VwCustomerFollowupComposite getVwCustomerFollowupComposite() {
		return vwCustomerFollowupComposite;
	}


class VwCustomerFollowupComposite extends Composite{
	public VwCustomerFollowupComposite(VwCustomerFollowupTable vwCustomerFollowupTable_){
		
		//VerticalPanel vpPrimary = new VerticalPanel();

//		FormPanel vwCustomerFollowupFormPanel = new FormPanel();
//		vwCustomerFollowupFormPanel.setFrame(true);
//		vwCustomerFollowupFormPanel.setHeading("vw_customer_followup Information");
//		vwCustomerFollowupFormPanel.setWidth(600);
		
		//createControls();
		

	//	initWidget(vpPrimary);
		//initWidget(vwCustomerFollowupFormPanel);
		//ContentPanel cp = new ContentPanel();
		//vwCustomerFollowupTable = new VwCustomerFollowupTable();
		//cp.add( vwCustomerFollowupTable);
		VerticalPanel vp = new VerticalPanel();
		vp.add(vwCustomerFollowupTable_);
		initWidget(vp);
	}
	
	public void onAttach(){
		super.onAttach();
		notifyAppEvent(this, "VwCustomerFollowupViewOnAttach");
	}

	public void onDetach(){
		super.onDetach();
		notifyAppEvent(this, "VwCustomerFollowupViewOnDetach");
	}

}

/**
 * @return the vwCustomerFollowupFormPanel
 */
public FormPanel getVwCustomerFollowupFormPanel() {
	return vwCustomerFollowupFormPanel;
}



//	/**
//	 * @param vwCustomerFollowupTable the vwCustomerFollowupTable to set
//	 */
//	public void setVwCustomerFollowupTable(VwCustomerFollowupTable vwCustomerFollowupTable_) {
//		this.vwCustomerFollowupTable = vwCustomerFollowupTable_;
//	}
//	/**
//	 * @return the vwCustomerFollowupTable
//	 */
//	public VwCustomerFollowupTable getVwCustomerFollowupTable() {
//		return vwCustomerFollowupTable;
//	}


}

