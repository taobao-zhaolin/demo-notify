package com.taobao.mina.test.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotifyDatabase {
	
	private  final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
		
	private static Connection conn;

	public NotifyDatabase() {
		
	}
	
    public  Connection init() throws Exception
    {
       String IP = "127.0.0.1";
       String port = "3306";
       String dbname="notify";
       String user = "root";
       String password = "root";
 	   String JDriver = "com.mysql.jdbc.Driver";
 	   String conURL="jdbc:mysql://"+IP+":"+port+"/"+dbname;
 	   try {
            Class.forName(JDriver);
        }
        catch(ClassNotFoundException cnf_e) {  
     	   logger.error("Driver Not Found: ", cnf_e);
     	   throw new Exception(cnf_e);
        }

        try {
            conn = DriverManager.getConnection(conURL, user, password);  
            return conn;
        }
        catch(SQLException se)
        {
     	   logger.error("connection to target database is created failed.", se);
     	   throw new Exception(se);
        } 
    }
	
	public static Connection getConn() {
		return conn;
	}

}
