package com.martinanalytics.legaltime.server.utility;

import javax.mail.*;
import javax.mail.internet.*;

import com.allen_sauer.gwt.log.client.Log;
import com.martinanalytics.legaltime.client.model.bean.EmailMsg;
import com.martinanalytics.legaltime.server.ServerProps;



import java.util.ArrayList;
import java.util.Properties;

public class MailController {
	Session session ;
	Transport transport ;
	MimeMessage message;
	Properties props ;
	ServerProps serverProps;
	static private MailController instance = null;
	protected MailController(){
		serverProps = ServerProps.getInstance();
		props = new Properties();
	}
	public static MailController getInstance(){
		if (instance == null){
			instance = new MailController();
		}
		return instance;
		
	}
	
	
	
	private void initSession(){
		session = getSession();
		
		try {
			transport = session.getTransport();
			transport.connect();
		} catch (NoSuchProviderException e) {
			System.err.println("Trying to Connect to Mail Server, but NoSuchProviderException");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.err.println("Trying to Connect to Mail Server, but MessagingException");
			
			e.printStackTrace();
		}
		
	}
	private void closeSession(){
		try {
			transport.close();
		} catch (MessagingException e) {
			System.err.println("Trying to disconnect from Mail Server, but " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean sendMessage(EmailMsg emailMsg_){
		int ndx;
		initSession();
		boolean success = false;
		message = new MimeMessage(session);
		try {
			message.setSubject(emailMsg_.getSubject());
			message.setContent(emailMsg_.getMsgBody(),"text/html; charset=ISO-8859-1");
			for (ndx = 0 ; ndx < emailMsg_.getSenders().size(); ndx ++){
				message.addFrom(new InternetAddress[] { 
				         new InternetAddress(emailMsg_.getSenders().get(ndx))});
			}
			
			for (ndx = 0 ; ndx < emailMsg_.getToAddresses().size(); ndx ++){
				message.addRecipient(Message.RecipientType.TO,
				         new InternetAddress(emailMsg_.getToAddresses().get(ndx)));
			}
			for (ndx = 0 ; ndx < emailMsg_.getCCAddresses().size(); ndx ++){
				message.addRecipient(Message.RecipientType.CC,
				         new InternetAddress(emailMsg_.getToAddresses().get(ndx)));
			}
			for (ndx = 0 ; ndx < emailMsg_.getBCCAddresses().size(); ndx ++){
				message.addRecipient(Message.RecipientType.BCC,
				         new InternetAddress(emailMsg_.getBCCAddresses().get(ndx)));
			}
			transport.sendMessage(message,
			         message.getRecipients(Message.RecipientType.TO));
			success = true;
			
		} catch (MessagingException e) {
			success = false;
			System.err.println("Trying to Send Message but, " + e.getMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		closeSession();
		//System.err.println("Successful Send in Mail Controller");
		return success;
	}

	public int sendMessages(ArrayList<EmailMsg> msgs_){
		int ndx;
		int successCount =0;
		for (ndx = 0; ndx < msgs_.size(); ndx ++){
			if( sendMessage(msgs_.get(ndx))){
				successCount++;
			}
		}
		return successCount;
	}
	
	
	private  Session getSession() {
		//System.err.println("MailController getSession Called");
    	Authenticator auth = new SMTPAuthenticator();
    	props.setProperty("mail.smtp.auth", "true");
    	
		props.setProperty("mail.host", serverProps.getProperty(ServerProps.MAIL_HOST));
	    props.put("mail.smtp.port", serverProps.getProperty(ServerProps.MAIL_PORT));
	    props.setProperty("mail.transport.protocol", serverProps.getProperty(ServerProps.MAIL_PROTOCOL));
	    return Session.getInstance(props, auth);	
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = serverProps.getProperty(ServerProps.MAIL_USERNAME);
           String password = serverProps.getProperty(ServerProps.MAIL_PASSWORD);
           return new PasswordAuthentication(username, password);
        }
    }
}
