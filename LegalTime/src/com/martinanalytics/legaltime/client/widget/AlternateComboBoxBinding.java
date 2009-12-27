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
	  public AlternateComboBoxBinding(FormBinding formBindings_, AlternateComboBox field) {
	    super(field, field.getName());
	    this.simpleComboBox = field;
	    formBindings_.removeFieldBinding(formBindings_.getBinding(field));
		formBindings_.addFieldBinding(this);
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

	
	}



