package com.martinanalytics.legaltime.client.widget;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;


public class DecentComboBox<D extends ModelData> extends Composite{
	HorizontalPanel hp;
	public Label lblCbo;
	public ListBox cbo;
	private String displayField;
	private String valueField;
	private ArrayList<D> list;
	public DecentComboBox(){
		list = new ArrayList();
		hp = new HorizontalPanel();
		lblCbo = new Label();
		hp.add(lblCbo);
		cbo = new ListBox();
		hp.add(cbo);
		initWidget(hp);	
	}
	public void setLabelWidth(Integer width_){
		lblCbo.setWidth(width_.toString()+"px");
	}
	public void setFieldLabel(String text_){
		lblCbo.setText(text_);
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
	

}
