/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          Team.java
 * @Date          04/06/2015
 * @Description   This class extends Developer class, and 
 *                handles all the remote data related to 
 *                all the developers participating in this
 *                session
 */
package com.jortizsd.rsace.remote;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jortizsd.rsace.appTree.ConfigBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;

public class Team extends Developer 
{
	private List <Developer >developers;
	private List <Team> teams;
	private String teamName;
	private String teamId;
	private String teamGuestCode;
    public static final String TEAM_CONTEXT = "team";
	public static final String ID_CONTEXT = "id";
	public static final String NAME_CONTEXT = "name";
	public static final String DB_FIELD_TEAM_ID = "team_id";
	public static final String DB_FIELD_TEAM_NAME = "team_name";
	
	/**
	 * @category Constructor
	 */
	private Team (String teamName, String teamId)
    {
		super();
        developers = new ArrayList<>();
        this.teamName = teamName;
        this.teamId = teamId;
        
    }
	
	protected Team () 
	{
		super();
		developers = new ArrayList<>();
		developers = getDevelopers();
		teams = new ArrayList <>();
		
	}
	
	
	private Team (String teamName) 
	{
		super();
		developers = new ArrayList <>();
		developers = getDevelopers();
		teams = new ArrayList <>();
		Team team = fetchTeamByName(teamName);
		this.setTeamName(teamName);
		this.setTeamId(team.getTeamId());
		
	}
	
	private Team (int teamId)
	{
		super();
		developers = new ArrayList <>();
		developers = getDevelopers();
		teams = new ArrayList <>();
		Team team = fetchTeamById(teamId);
		this.setTeamName(team.getTeamName());
		this.setTeamId(team.getTeamId());
	   
	}
	
	
	public static Team createNewTeam (String teamName, String teamId)
	{
		return new Team (teamName, teamId);
	}
	
	public static Team getDefaultTeam (int defaultIndex) 
	{
		return new Team (defaultIndex);
	}
	
	public static Team getAllTeamsInstance () 
	{
		return new Team ();
	}
	
	public static Team getTeamByName (String teamName) 
	{
		return new Team(teamName);
	}
	
	public static Team getTeamByID (int teamId)
	{
		return null;
		
	}
	
    public void setTeamName (String teamName)
    {
    	this.teamName = teamName;
    }
    
    public void setTeamId (String teamId)
    {
    	this.teamId = teamId;
    }
    
    public void setTeamGuestCode (String teamGuestCode)
    {
    	this.teamGuestCode = teamGuestCode;
    }
   
    public String getTeamName ()
    {
    	return this.teamName;
    }
    
    public String getTeamId()
    {
    	return this.teamId;
    }
    
    public String getTeamGuestCode()
    {
    	return this.teamGuestCode;
    }
    
    /**
     * @category    Public Class Method
     * @description Gets all the developers that pertain to the team 
     *              that is working in the current session
     * @return      List object containing all the developers working at this session 
     * @see         com.jortizsd.rsace.remote.Developer
     */
    public List <Developer> getDevelopers ()
    {
        try
        { 
           String id, name, email;
           boolean isActive, isSender;
           Document document = getDocumentToParse(file);
           document.getDocumentElement().normalize();
           NodeList  teams = document.getElementsByTagName(Team.TEAM_CONTEXT);
           for (int i = 0; i<teams.getLength(); i++)
           {
           	   Element tmpTeam = (Element) teams.item(i);
           	   if (tmpTeam.getAttribute("id").equalsIgnoreCase(getTeamId()))
           	   {
           		    NodeList developersList = teams.item(i).getChildNodes();
           		    for (int j = 0; j<developersList.getLength(); j++ )
           		    {
           		    	if (developersList.item(j).getNodeType() == Node.ELEMENT_NODE) 
                        {
                     	   
                            Element developer = (Element) developersList.item(j);
                            id = developer.getElementsByTagName("id").item(0).getTextContent();
                            name = developer.getElementsByTagName("name").item(0).getTextContent();
                            email = developer.getElementsByTagName("email").item(0).getTextContent();
                            isActive = Boolean.valueOf(developer.getElementsByTagName("session_active").item(0).getTextContent());
                            isSender = Boolean.valueOf(developer.getElementsByTagName("session_owner").item(0).getTextContent());
                            developers.add(new Developer(this,id,name,email,isActive,isSender));
                            
                        }
           		    }
           		    
               }
           }
           
           return developers;
           
        }
        catch (Exception e)
        {
            
        }
        return null;
    }
    
   
    
    /**
     * @category    Public Class Method
     * @description Gets a developer by a given name
     * @param name  String object representing the developer's name
     * @return      Developer object representing the developer found. Otherwise, returns null
     * @see         com.jortizsd.rsace.remote.Developer
     */
    public Developer getDeveloperFromTeamByName (String name)
    {
        
        for (Developer dev : getDevelopers())
            if (dev.getName().equalsIgnoreCase(name))
                return dev;
        return null;
    }
    
    /**
     * @category    Public Class Method
     * @description Gets a developer by a given id
     * @param name  String object representing the developer's id
     * @return      Developer object representing the developer found. Otherwise, returns null
     * @see         com.jortizsd.rsace.remote.Developer
     */
    public Developer getDeveloperFromTeamById (String id)
    {
       for (Developer dev : getDevelopers())
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
    
    /**
     * @category     Public Class Method
     * @description  Returns a string with the description of this object
     * @see          Developer#toString()
     */
    @Override
    public String toString ()
    {
    	String teamContext;
    	teamContext = "Team: " + getTeamName() + "\n" + 
    			      "Team's id: " + getTeamId() + "\n" + 
    			      "Developers members of this team: \n";
    	for (Developer dev : getDevelopers())
    		 teamContext+= dev.toString() + "\n";
    	return teamContext;
    			      
    		
    }
    
    
    /**
     * @category     Public Class Method
     * @description  Fetches all the developer teams
     * @return       List <Team> object representing a list of the existing developers teams 
     */
    public List <Team> fetchAllTeams () 
    {
    	try
    	{
    	   Document document = getDocumentToParse(file);
           document.getDocumentElement().normalize();
           NodeList  teamNodes = document.getElementsByTagName(Team.TEAM_CONTEXT);
           for (int i = 0; i<teamNodes.getLength(); i++)
           {
        	   Element teamElement = (Element) teamNodes.item(i);
        	   this.teams.add(createNewTeam(teamElement.getAttribute("team_name"), teamElement.getAttribute("id")));
           }
           return this.teams;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		return this.teams;
    }
    
    /**
     * @category         Public Class Method
     * @description      Fetches a team given a its name as a parameter
     * @param teamName   String object representing the team's name
     * @return           Team object representing the team that matches with the given name
     * @throws           SAXException
     * @throws           IOException
     * @throws           CoreException
     * @throws           ParserConfigurationException
     */
    public Team fetchTeamByName (String teamName) 
    {
    	teams = fetchAllTeams();
    	for (Team team : teams)
    	if (team.getTeamName().equalsIgnoreCase(teamName))
    	      return team;
    	return null;
    	
    }
    
    public Team fetchTeamById (int teamId) 
    {
    	teams = fetchAllTeams();
    	for (Team team : teams)
    	if (team.getTeamId().equalsIgnoreCase(String.valueOf(teamId)))
    	      return team;
    	return null;
    	
    }
    
    /**
     * @category              Public Class Method
     * @description           Determines if a developer is a member of this team
     * @param developerName   String object representing the developer's name
     * @return                True is the developer is a member of this team. Otherwise, returns false.
     */
    public boolean isMember (String developerName)
    {
    	for (Developer dev : developers)
    		if (dev.getName().equalsIgnoreCase(developerName))
    			return true;
    	return false;
    	
    }
    
    public static Developer getDeveloperFromDB (String developerID, String teamId)
    {
    	Team team;
    	Developer developer = null;
    	try
    	{
    	    String query = RemoteConstants.REMOTE_DB_SELECT_QUERY;
 	        URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            Connection conn = AppConfig.getConnectionFromRemoteConfigFile(url);
            // create the java statement
            Statement st = conn.createStatement();
             
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
             
            // iterate through the java resultset
            while (rs.next())
            {
              if (developerID.equalsIgnoreCase(rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVID)) && 
            	  teamId.equalsIgnoreCase(rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMID)))
              {
            	  String teamName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMNAME);
            	  String devId = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVID);
            	  String devName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVNAME);
                  String devEmail = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVEMAIL);
                  boolean isOwner = rs.getBoolean(RemoteConstants.REMOTE_DB_FIELD_ISTEAMOWNER);
                  team = new Team(teamName, teamId);
                  developer = new Developer(team,devId,devName,devEmail,false,isOwner);
                  developer.setRegistrationState(true);
                  break;
              }
            }
            st.close();
           return developer;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		return developer;
        
    }
    
    public static Developer getDeveloperFromDB (String developerID)
    {
    	Team team;
    	Developer developer = null;
    	try
    	{
    	    
    	    String query = "SELECT * FROM developers WHERE " + RemoteConstants.REMOTE_DB_FIELD_DEVID + 
    	    		       "=" + developerID;
 	        URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            Connection conn = AppConfig.getConnectionFromRemoteConfigFile(url);
            // create the java statement
            Statement st = conn.createStatement();
             
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
             
            // iterate through the java resultset
            while (rs.next())
            {
              
            	  String teamName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMNAME);
            	  String devId = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVID);
            	  String devName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVNAME);
                  String devEmail = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVEMAIL);
                  boolean isOwner = rs.getBoolean(RemoteConstants.REMOTE_DB_FIELD_ISTEAMOWNER);
                  team = new Team(teamName, devId);
                  developer = new Developer(team,devId,devName,devEmail,false,isOwner);
                  developer.setRegistrationState(true);
                  break;
              
            }
            st.close();
           return developer;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		return developer;
    }
    
    public Developer getTeamLeader()
    {
    	for (Developer dev : developers)
    	{
    		if (dev.isSessionOwner())
    			return dev;
    	}
    	return null;
    }
    
    public boolean isDeveloperTeamMember (Developer developer)
    {
    	for (Developer dev : developers)
    		if (dev.getId().equalsIgnoreCase(developer.getId()))
    			return true;
    	return false;
    }
    
    // Temporal method. Delete once it is not used anymore
    public void createTeamGuestCode ()
    {
    	teamGuestCode = "RSACE";
    	int rnd;
    	char rndResult;
    	for (int i = 0; i<7; i++)
    	{
    		 rnd = (int) (Math.random() * 52); 
    	     rndResult = (rnd < 26) ? 'A' : 'a';
    	     teamGuestCode += (char) (rndResult + rnd % 26);
    	}
    	
    }
    
    
}
