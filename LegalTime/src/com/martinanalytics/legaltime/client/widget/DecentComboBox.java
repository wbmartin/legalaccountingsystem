package com.martinanalytics.legaltime.client.widget;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import com.google.gwt.user.client.ui.ListBox;


public class DecentComboBox<D extends ModelData> extends Composite{
	FlowPanel formItem;

	public HTML lblCbo;
	public ListBox cbo;
	private String displayField;
	private String valueField;
	private ArrayList<D> list;
	private Integer LabelWidth =150;
	private String fieldName;
	
	public DecentComboBox(){
		list = new ArrayList();
		formItem = new FlowPanel();
		formItem.setStyleName("x-form-item");
		formItem.addStyleName("LEFT");
		

		formItem.addStyleName("combo");
		
		lblCbo = new HTML("<label  style='width:150px;' class='x-form-item-label'></label>");
		formItem.add(lblCbo);
		cbo = new ListBox(false);
		cbo.setWidth("210px");
		cbo.addStyleName("LEFT");
		formItem.add(cbo);
		initWidget(formItem);
		
	}
	public void setLabelWidth(Integer width_){
		LabelWidth = width_;
	}
	public void setFieldLabel(String text_){
		lblCbo.setHTML("<label  style='width:" + (LabelWidth +1) + "px;' class='x-form-item-label'>" + text_+"</label>");
	}
	public void setList(String displayField_, String valueField_, ArrayList<D> list_){
		displayField = displayField_;
		valueField = valueField_;
		cbo.clear();
		for(int ndx =0; ndx< list_.size();ndx++){
			cbo.addItem(list_.get(ndx).get(displayField).toString(),list_.get(ndx).get(valueField).toString());
		}
	}
	
	public void setSelected(Object toMatch_){
		for(int ndx =0; ndx< cbo.getItemCount();ndx++){
			if(cbo.getValue(ndx).equals(toMatch_.toString())){
				cbo.setSelectedIndex(ndx);
				break;
			}
			
		}
	}
	public String getSelectedValue(){
		
		return cbo.getValue(cbo.getSelectedIndex());
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the fieldName
	 */
	public String getName() {
		return fieldName;
	}
	

}
