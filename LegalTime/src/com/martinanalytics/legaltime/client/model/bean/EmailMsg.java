package com.martinanalytics.legaltime.client.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class EmailMsg implements Serializable{
 private ArrayList<String> senders;
 private ArrayList<String> ToAddresses;
 private ArrayList<String> CCAddresses;
 private ArrayList<String> BCCAddresses;
 private String subject;
 private String msgBody;
 
public EmailMsg() {
	super();
	senders = new ArrayList<String>();
	ToAddresses = new ArrayList<String>();
	CCAddresses = new ArrayList<String>();
	BCCAddresses = new ArrayList<String>();
	subject = "";
	msgBody = "";
	
}
///**
// * @param senders the senders to set
// */
//public void setSenders(ArrayList<String> senders_) {
//	this.senders = senders_;
//}
/**
 * @return the senders
 */
public ArrayList<String> getSenders() {
	return senders;
}
///**
// * @param toAddresses the toAddresses to set
// */
//public void setToAddresses(ArrayList<String> toAddresses_) {
//	ToAddresses = toAddresses_;
//}
/**
 * @return the toAddresses
 */
public ArrayList<String> getToAddresses() {
	return ToAddresses;
}
///**
// * @param cCAddresses the cCAddresses to set
// */
//public void setCCAddresses(ArrayList<String> CCAddresses_) {
//	this.CCAddresses = CCAddresses_;
//}
/**
 * @return the cCAddresses
 */
public ArrayList<String> getCCAddresses() {
	return CCAddresses;
}
///**
// * @param bCCAddresses the bCCAddresses to set
// */
//public void setBCCAddresses(ArrayList<String> BCCAddresses_) {
//	this.BCCAddresses = BCCAddresses_;
//}
/**
 * @return the bCCAddresses
 */
public ArrayList<String> getBCCAddresses() {
	return BCCAddresses;
}
/**
 * @param subject the subject to set
 */
public void setSubject(String subject_) {
	this.subject = subject_;
}
/**
 * @return the subject
 */
public String getSubject() {
	return subject;
}
/**
 * @param msgBody the msgBody to set
 */
public void setMsgBody(String msgBody) {
	this.msgBody = msgBody;
}
/**
 * @return the msgBody
 */
public String getMsgBody() {
	return msgBody;
}

}
