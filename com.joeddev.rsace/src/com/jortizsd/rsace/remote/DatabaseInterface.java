/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          DatabaseInterface.java
 * @Date          06/23/2015
 * @Description   This interface provides useful default and signature 
 *                methods to handle the data from the application's database
 *                that uses MySQL Database Engine.
 *                It contains a implemented two default method one of them gets the 
 *                connection from the database, and the other one checks the validity
 *                of the database connection. Also, contains signature 
 *                methods to add, get, update and delete a developer from
 *                the database.
 *        
 */
package com.jortizsd.rsace.remote;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.jasypt.properties.EncryptableProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
public interface DatabaseInterface 
{
	/**
	 * @category         Default Interface Method
	 * @description      Creates a connection to the database
	 * @param url        String object representing the host's server URL 
	 *                   where the database is hosted
	 * @param database   String object representing the database's name
	 * @return           Connection object representing the connection to the database
	 * @throws SQLException 
	 * @throws IOException 
	 */
    default Connection getConnection (String databaseName) throws SQLException, IOException
    {
    	String username, password, host;
    	Remote remote = new Remote (RemoteConstants.REMOTE_APP_CONFIG_FILE);
    	AppConfig dbConfig = new AppConfig (remote);
    	HashMap <String,String> databaseCredentials = dbConfig.getDatabaseCredentials(databaseName);
    	username = databaseCredentials.get(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME);
    	password = databaseCredentials.get(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD);
    	host = databaseCredentials.get(RemoteConstants.REMOTE_PROPERTY_DB_HOST);
    	Connection conn = null;
    	try {
    		
    		MysqlDataSource dataSource = new MysqlDataSource();
    		dataSource.setUser(username ); 
    		dataSource.setPassword(password); //rme.getDatabasePasswordProperty());
    		dataSource.setDatabaseName(databaseName);//rme.getDatabaseProperty());
    		dataSource.setServerName(host);//rme.getDatabaseHostProperty());
    	    conn =
    	       dataSource.getConnection();
    	    if (conn.isValid(3))
    	        System.out.println("valid Connection");
    	    else
    	    	System.out.println("valid Connection");
    	    return conn;
    	}
    	catch (SQLException ex) 
    	{
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
        return null;
    }
    
    /**
     * @category             Default Interface Method
     * @description          Checks the validity of the database connection
     * @param conn           Connection object representing the connection to the database
     * @param timeout        Int representing the timeout to check the validity of the connection
     * @return               True is the connection to the database is up. Otherwise, returns false
     * @throws SQLException
     */
    default boolean isDBConnectionValid (Connection conn, int timeout) throws SQLException
    {
    	return conn.isValid(timeout);
    }
    
    /**
     * @category         Public Interface Signature Method
     * @description      Adds a developer to the database
     * @param developer  Developer object representing a developer
     * @see              Developer 
     */
    public void addDeveloperToDB (Developer developer);
    
    /**
     * @category         Public Interface Signature Method
     * @description      Updates a developer from the database
     * @param developer  Developer object representing a developer
     * @see              Developer 
     */
    public void updateDeveloperFromDB (Developer developer);
    
    /**
     * @category         Public Interface Signature Method
     * @description      Deletes a developer from the database
     * @param developer  String object representing the developer's id
     * @see              Developer 
     */
    public void deleteDeveliperFromDB(String developerID);
    
    /**
     * @category         Public Interface Signature Method
     * @description      Gets a developer from the database
     * @param developer  String object representing the developer's id
     * @return           Developer object representing a developer from the database
     * @see              Developer 
     */
    public Developer getDeveloperFromDB(String developerID);
    
    
}
