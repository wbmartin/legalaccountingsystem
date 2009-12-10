package com.martinanalytics.legaltime.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerProps extends  HttpServlet {
	
	public void init(ServletConfig servletConfig) throws ServletException {
        
        super.init(servletConfig);

	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ServerProps instance = null;
	Properties props;
	public static String MAIL_HOST = "MailServer";
	public static String MAIL_PORT = "MailPort";
	public static String MAIL_PROTOCOL ="MailProtocol";
	public static String MAIL_USERNAME ="MailUsername";
	public static String MAIL_PASSWORD ="MailPassword";
	protected 	ServerProps(){
		super();

		InputStream inputStream =this.getClass().getClassLoader().getSystemResourceAsStream("com/martinanalytics/manymail/server/server.properties");
		props = new Properties();
		//props.put(MAIL_HOST, "mail.martinanalytics.com");
		
	
		try {
			props.load(inputStream);
		} catch (IOException e) {
			System.err.println("Error Loading Properties File");
			e.printStackTrace();
		}
	}
public static ServerProps getInstance(){
	if (instance == null){
		instance = new ServerProps();
	}
	return instance;
}

public String getProperty(String propName_){
	return props.getProperty(propName_);
}
}
