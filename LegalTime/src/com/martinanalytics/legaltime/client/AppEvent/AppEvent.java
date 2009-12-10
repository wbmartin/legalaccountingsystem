package com.martinanalytics.legaltime.client.AppEvent;



import java.util.*;

/**
 *
 * @author bmartin
 */
public class AppEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private Object payLoad;
	private String note;


    public AppEvent(Object source, String name_, Object payLoad_, String note_ ){
        super(source);
        this.name = name_;
        this.payLoad = payLoad_;
         this.note = note_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name_) {
        this.name = name_;
    }

    public Object getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(Object payLoad_) {
        this.payLoad = payLoad_;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
