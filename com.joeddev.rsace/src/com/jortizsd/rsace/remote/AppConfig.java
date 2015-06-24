package com.jortizsd.rsace.remote;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jortizsd.rsace.appTree.TreeWriter;

public class AppConfig extends TreeWriter
{
    Remote remote;
    Document doc;
    
	public AppConfig (Remote remote)
	{
		this.remote = remote;
		doc = remote.getRemoteDocument();
	}
	
	public HashMap <String, String> getDatabaseCredentials (String databaseName)
	{
	    HashMap <String, String> databaseInfo = new HashMap <> ();
	    String driver = null,
	    	   host = null, 
	    	   username = null, 
	    	   password = null,
	           qualifier = null;
	    NodeList databases = doc.getElementsByTagName("database");
	    for (int i=0; i<databases.getLength(); i++)
	    {
	    	 Element databaseData = (Element) databases.item(i);
	    	 if (databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB).equalsIgnoreCase(databaseName))
	    	 {
	    	      driver = databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB_DRIVER);
	    	      qualifier = databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB_QUALIFIER);
	    	      host = databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB_HOST);
	    	      username = databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME);
	    	      password = databaseData.getAttribute(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD);
	    	      break;
	    	 }
	    }
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB, databaseName);
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB_QUALIFIER, qualifier);
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB_HOST, host);
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB_DRIVER, driver);
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME, username);
	    databaseInfo.put(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD, password);
	    return databaseInfo;
	}
    
}
