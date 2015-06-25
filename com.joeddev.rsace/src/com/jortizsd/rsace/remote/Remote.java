/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          Remote.java
 * @Date          04/06/2015
 * @Description   This class handle all the remote work from
 *                this application. It extends to Team class 
 *                to provides team and developer functionality.
 *                It also implements RemoteInterface to add remote
 *                functionality to the team and developer class methods
 *                inherited from this class.
 */
package com.jortizsd.rsace.remote;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jortizsd.rsace.appTree.TreeWriter;



public class Remote extends TreeWriter implements RemoteInterface
{
	
    private URL url;
    private String fileName;
    private InputStream fileStream;
    private FileInputStream fileInputStream;
    private Properties properties;
    private File file;
    
    public Remote (URL url) throws IOException 
    {
    	 super();
    	 setURL(url);
    	 properties = new Properties();
    	 fileStream = this.url.openStream();
    	 
    }
    
    
   
    public void setURL (URL url) throws MalformedURLException
    {
    	this.url = url;
    }
	
    public URL getURL ()
    {
    	return this.url;
    }
    
    public String getFileName (String file)
    {
    	return this.fileName;
    }
    
    public void setFile (String filename)
    {
    	this.file = new File (filename);
    }
    
    public File getFile ()
    {
    	return this.file;
    }
    
    public InputStream getInputStream ()
    {
    	return this.fileStream;
    }
    
    public FileInputStream getFileInputStream()
    {
    	return this.fileInputStream;
    }
    
    @Override
	public boolean isServerUp() throws IOException
	{
		try
		{
		     url.openConnection().connect() ;
		     return true;
		} 
		catch(final MalformedURLException e)
		{
		    System.out.println("Error: Bad url. " + e.getMessage() );
		    		
		} 
		catch(final IOException e)
		{
		    System.out.println(url.toString() + " is down");
		    
		}
		return false;
		
		
	}

	@Override
	public boolean existRemoteConfigFile() 
	{
		try
		{
		   
		   
		   if (isServerUp())
		   {
			   URLConnection urlConnection = this.url.openConnection();
			   InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			   in.available();
			   return true;
		   }
		   return false;
		}
		catch (Exception e)
		{
			System.out.println("Error while trying to access to the server. " + e.getMessage());
		}
		return false;
	}

	@Override
	public void createRemoteTeam(Team team) 
	{
		
	}

	@Override
	public void updateRemoteConfigFile( String tag, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRemoteConfigFileValue( String tag,
			String value) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Document getRemoteDocument() 
	{
		try
		{
		   
		   InputStream is = this.url.openStream();
		   DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
           Document document =  docBuilder.parse(is);
           document.normalizeDocument();
           return document;
           
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public InputStream getURLStream () throws IOException
	{
		return this.url.openStream();
	}
	
	 
}
