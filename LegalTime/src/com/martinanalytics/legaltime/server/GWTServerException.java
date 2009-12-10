package com.martinanalytics.legaltime.server;

public class GWTServerException extends com.google.gwt.user.client.rpc.InvocationException{
String message;
	public GWTServerException(String s_) {
		super(s_);
		message = s_;
	}
	
	public GWTServerException(String s_, Throwable t) {
		super(s_,t);
		message = s_;
		
		
	}
	@Override
	public String getMessage() {

		return message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629623221303754436L;

}
