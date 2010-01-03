package com.martinanalytics.legaltime.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.extjs.gxt.ui.client.js.JsArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.martinanalytics.legaltime.client.model.bean.UserProfile;

public class ReportUtil {

    private ReportUtil() {
    }
    
    public static void showReport(String action,UserProfile userProfile_, HashMap parameters) {

    	JsArray names = new JsArray();
    	JsArray values = new JsArray();
    	names.add("userId");
    	values.add(userProfile_.getUserId());
    	names.add("sessionId");
    	values.add(userProfile_.getSessionId());
    	names.add("clientId");
    	values.add(userProfile_.getClientId());
    	if (parameters != null){
    	    for (Object key: parameters.keySet()) {
	    		names.add(key.toString());
	        	values.add(parameters.get(key));
    		 
    		}
    	}
    	
    	try{
        openReportWindow(action, names.getJsObject(), values.getJsObject() );//JavaScriptObjectHelper.convertToJavaScriptArray(values)
    	}catch(Exception e){
    		//This always throws an exception in hosted mode, ignoring
    	}
    }
    
    private static native void openReportWindow(String action, JavaScriptObject names, JavaScriptObject values) /*-{
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", action);

        // setting form target to a window named 'formresult'
        form.setAttribute("target", "_blank");
        for (var i=0; i<values.length; i++) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("name", names[i]);
            hiddenField.setAttribute("value", values[i]);
            form.appendChild(hiddenField);
        }

        document.body.appendChild(form);

        form.submit();
        document.body.removeChild("form");
    }-*/;

}