package com.martinanalytics.legaltime.client.widget;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.martinanalytics.legaltime.client.model.bean.UserInfoBean;

public class AlternateComboBoxBinding extends FieldBinding {

	  protected AlternateComboBox simpleComboBox;

	  /**
	   * Creates a new simplecombobox field binding instance.
	   * 
	   * @param field the simple combo box
	   * @param property the property name
	   */
	  public AlternateComboBoxBinding(AlternateComboBox field, String bindField) {
	    super(field, bindField);
	    this.simpleComboBox = field;
	  }

	  @Override
	  protected Object onConvertFieldValue(Object value) {
		  Object ret = simpleComboBox.getKeyValue();
		 // Log.debug("Decent Binding onConvertFieldValue: " +ret.toString());
	    return ret;
	  }

	  @Override
	  protected Object onConvertModelValue(Object value) {
		  
		  Object ret = simpleComboBox.findModel(value);
		 // Log.debug("Decent Binding onConvertModelValue: " +value.toString() );
		 // Log.debug("Decent Binding onConvertModelValue found:" + ret.toString());
	    return ret;
	  }

	public static void swapBinding(FormBinding formBindings_,
			AlternateComboBox cboAssignedUser_) {
		
			formBindings_.removeFieldBinding(formBindings_.getBinding(cboAssignedUser_));
			formBindings_.addFieldBinding(new AlternateComboBoxBinding(cboAssignedUser_, cboAssignedUser_.getName()));

		
	}

	}



