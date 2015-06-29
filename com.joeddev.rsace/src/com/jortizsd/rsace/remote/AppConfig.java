package com.jortizsd.rsace.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.TreeWriter;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class AppConfig extends TreeWriter
{
    Remote remote;
    Document doc;
    Properties properties;
    String databaseHost;
    String databaseMode;
    String databaseQualifier;
    String database;
    String databaseUsername;
    String databasePassword;
    String databaseDriver;
    
    
	public AppConfig (Remote remote) throws IOException 
	{
		this.remote = remote;
		properties = new Properties();
		setAppConfigProperties();
	}
	
	public AppConfig()
	{
		properties = new Properties();
	}
	
	private void  setAppConfigProperties ()
	{
		
		try
		{
	        InputStream fileInput = remote.getInputStream();
			properties.loadFromXML(fileInput);
			fileInput.close();
		    setDatabaseHost(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_HOST));
		    setDatabaseMode(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_MODE));
		    setDatabaseQualifier(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_QUALIFIER));
		    setDatabase(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB));
		    setDatabaseUsername(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME));
		    setDatabasePassword(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD));
		    setDatabaseDriver(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_DRIVER));
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	public void setDatabaseHost (String databaseHost)
	{
		this.databaseHost = databaseHost;
	}
	
	public void setDatabaseMode (String databaseMode)
	{
		this.databaseMode = databaseMode;
	}
	
	public void setDatabaseQualifier (String databaseQualifier)
	{
		this.databaseQualifier = databaseQualifier;
	}
	
	public void setDatabaseDriver (String databaseDriver)
	{
		this.databaseDriver = databaseDriver;
	}
	
	public void setDatabase (String database)
	{
		this.database = database;
	}
	
	public void setDatabaseUsername (String databaseUsername)
	{
		this.databaseUsername = databaseUsername;
	}
	
	public void setDatabasePassword (String databasePassword)
	{
		this.databasePassword = databasePassword;
	}
	
	public String getDatabaseHost ()
	{
		return this.databaseHost;
	}
	
	public String getDatabaseMode ()
	{
		return this.databaseMode;
	}
	
	public String getDatabaseQualifier ()
	{
		return this.databaseQualifier;
	}
	
	public String getDatabaseDriver ()
	{
		return this.databaseDriver;
	}
	
	public String getDatabase ()
	{
		return this.database;
	}
	
	public String getDatabaseUsername ()
	{
		return this.databaseUsername;
	}
	
	public String getDatabasePassword ()
	{
		return this.databasePassword;
	}
	
	static Connection getConnectionFromRemoteConfigFile (URL configFileURL ) throws SQLException, IOException
    {
    	
    	Remote remote = new Remote (configFileURL);
    	AppConfig dbConfig = new AppConfig (remote);
    	
    	try 
    	{
    		MysqlDataSource dataSource = new MysqlDataSource();
    		dataSource.setUser(dbConfig.getDatabaseUsername()); 
    		dataSource.setPassword(dbConfig.getDatabasePassword()); 
    		dataSource.setDatabaseName(dbConfig.getDatabase());
    		dataSource.setServerName(dbConfig.getDatabaseHost());
    	    return dataSource.getConnection();
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
	
	
    
}
