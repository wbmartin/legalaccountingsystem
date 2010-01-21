package com.martinanalytics.legaltime.client.model.bean;

import java.util.HashMap;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class KeyValuePair extends BaseModelData{
	
	

		/**
		 * 
		 */
		private static final long serialVersionUID = 1846879214506300276L;
		public KeyValuePair(){
			set("key","");
			set("value","");
		}
		public KeyValuePair(String key_, String value_){
			set("key",key_);
			set("value",value_);
			
		}
		/**
		 * @param key the key to set
		 */
		public void setKey(String key_) {
			set("key",key_);
		}
		/**
		 * @return the key
		 */
		public String getKey() {
			return get("key");
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value_) {
			set("value",value_);
		}
		/**
		 * @return the value
		 */
		public String getValue() {
			return get("value");
			
		}
	

}
