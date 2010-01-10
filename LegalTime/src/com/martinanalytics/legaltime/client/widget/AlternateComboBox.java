package com.martinanalytics.legaltime.client.widget;

import java.util.ArrayList;
import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;

public class AlternateComboBox<D extends ModelData> extends ComboBox{
	Integer selectedNdx;
	public AlternateComboBox(){
		//setDisplayField("value");
	    setStore(new ListStore<SimpleComboValue<D>>());
	    addStyleName("LEFT");
	    setFireChangeEventOnSetValue(true);
	}
	
	public AlternateComboBox(String labelText_, String comboValueToSynch_
			, String dataValueToSynch, String fieldToDisplay_){
		this();
		setup(labelText_, comboValueToSynch_, dataValueToSynch, fieldToDisplay_);
	}
	
	public void setup(String labelText_, String comboValueToSynch_
			, String dataValueToSynch, String fieldToDisplay_){
		setFieldLabel(labelText_);
		setValueField(comboValueToSynch_);
		setName(dataValueToSynch);
		setDisplayField(fieldToDisplay_);
	}
	
	public void setKeyValue(Object value_){
		//Log.debug("decent:" + getValueField() + " " + value_ + " store coutn: " + store.getCount() );
		if(value_ != null){
			for(int ndx =0; ndx< store.getCount();ndx++){
				if(store.getAt(ndx).get(getValueField()).equals(value_)){
					super.setValue(store.getAt(ndx));
				}
				
			}
		}else{
			clearSelections();
			setValue(null);
		}
		
	}
	
	
	
	
	
	public Object getKeyValue(){
		try{
		return getValue().get(getValueField());
		}catch(Exception e){
			return null;
		}
		
	}
	public int getSelectedIndex(){
		int ndx;
		for( ndx =0; ndx< store.getCount();ndx++){
			if(store.getAt(ndx).get(getValueField()).equals(getValue())){
				break;
			}
			
		}
		return ndx;
	}
	
	 /**
	   * Adds the values to the list.
	   * 
	   * @param values the values
	   */
	  @SuppressWarnings("unchecked")
	  public void add(ArrayList<D> values) {
	   
	    store.add(values);
	  }
	  public void setList(ArrayList<D> values) {
		  store.removeAll();
		  add(values);
	  }
	  
	  public D findModel(Object value) {
		    D val = null;
		    int ndx;
		    //Log.debug("Inside findModel getValue:" + getValue());
			for( ndx =0; ndx< store.getCount();ndx++){
				if(store.getAt(ndx).get(getValueField()).equals(value)){
					val = (D)store.getAt(ndx);
					break;
				}
				
			}
			if(ndx ==store.getCount()){clearSelections();}
		    return val;
		  }

}
