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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Remote extends Team implements RemoteInterface
{
	
    private URL url;
    public Remote (String url) throws MalformedURLException 
    {
    	 super();
    	 setURL(url);
    }
   
    public void setURL (String url) throws MalformedURLException
    {
    	this.url = new URL(url);
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
		try
		{
			Document doc = getRemoteDocument();
			Element root = doc.getDocumentElement();
		    Element teamElement = createElement(doc, root, Team.TEAM_CONTEXT);
		    setAtrribute(doc, teamElement, Team.ID_CONTEXT, team.getTeamId());
		    setAtrribute(doc, teamElement, Team.NAME_CONTEXT, team.getTeamName());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
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
           return docBuilder.parse(is);
           
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
}
