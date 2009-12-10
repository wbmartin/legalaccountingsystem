package com.martinanalytics.legaltime.server.model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseManager {
	private static DatabaseManager instance = null;
	Connection lConnection = null;
	protected DatabaseManager(){
		
	}
	public static DatabaseManager getInstance(){
		if (instance == null ){
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	public void openConnection()  {
        
		try {
			if (lConnection == null){//) || !lConnection.isValid(15) ){
			    Context lContext = new InitialContext();
			    DataSource lDataSource = (DataSource) lContext.lookup("java:comp/env/jdbc/projectDS");
			    lConnection = lDataSource.getConnection();
			}
		} catch (SQLException e) {
			
			System.err.println("Open connection: SQLException");
			e.printStackTrace();
		} catch (NamingException e) {
			
			System.err.println("Open connection: NamingException");
			e.printStackTrace();
		}

                      
	}
	
	public Connection getConnection(){
		openConnection();
		return lConnection;
	}

}
