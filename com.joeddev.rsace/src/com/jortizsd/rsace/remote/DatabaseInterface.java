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
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jasypt.properties.EncryptableProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
public interface DatabaseInterface 
{
	/**
	 * @category             Default Interface Method
	 * @description          Creates a connection to the database
	 * @param configFileURL  String object representing the host's server URL 
	 *                       where the database is hosted
	 * @return               Connection object representing the connection to the database
	 * @throws SQLException 
	 * @throws IOException 
	 */
	
    
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
    public void addDeveloperToDB ();
    
    /**
     * @category         Public Interface Signature Method
     * @description      Updates a developer from the database
     * @param developer  Developer object representing a developer
     * @see              Developer 
     */
    public void updateDeveloperFromDB ();
    
    /**
     * @category         Public Interface Signature Method
     * @description      Deletes a developer from the database
     * @param developer  String object representing the developer's id
     * @see              Developer 
     */
    public void deleteDeveloperFromDB();
    
    /**
     * @category         Public Interface Signature Method
     * @description      Gets a developer from the database
     * @param developer  String object representing the developer's id
     * @return           Developer object representing a developer from the database
     * @see              Developer 
     */
   // public Developer getDeveloperFromDB(String developerID);
    
    
}
