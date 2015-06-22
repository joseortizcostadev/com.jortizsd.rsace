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
import java.util.ArrayList;
import java.util.List;

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
    public static final String TEAM_CONTEXT = "team";
	public static final String ID_CONTEXT = "id";
	public static final String NAME_CONTEXT = "name";
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
	
	private Team (int defaultIndex) throws SAXException, IOException, CoreException, ParserConfigurationException 
	{
		super();
		developers = new ArrayList<>();
		teams = new ArrayList<>();
		developers = getDevelopers();
		fetchDefaultTeam(defaultIndex);
		
	}
	
	private Team () throws SAXException, IOException, CoreException, ParserConfigurationException
	{
		super();
		developers = new ArrayList<>();
		developers = getDevelopers();
		teams = new ArrayList <>();
	}
	
	private Team (String teamName) throws SAXException, IOException, CoreException, ParserConfigurationException
	{
		super();
		developers = new ArrayList <>();
		developers = getDevelopers();
		teams = new ArrayList <>();
		Team team = fetchTeamByName(teamName);
		System.out.println("Team: " + team.getTeamName() + "Id: " + team.getTeamId());
		this.setTeamName(teamName);
		this.setTeamId(team.getTeamId());
	}
	
	public static Team createNewTeam (String teamName, String teamId)
	{
		return new Team (teamName, teamId);
	}
	
	public static Team getDefaultTeam (int defaultIndex) throws SAXException, IOException, CoreException, ParserConfigurationException
	{
		return new Team (defaultIndex);
	}
	
	public static Team getAllTeamsInstance () throws SAXException, IOException, CoreException, ParserConfigurationException
	{
		return new Team ();
	}
	
	public static Team getTeamByName (String teamName) throws SAXException, IOException, CoreException, ParserConfigurationException
	{
		return new Team(teamName);
	}
	
    public void setTeamName (String teamName)
    {
    	this.teamName = teamName;
    }
    
    public void setTeamId (String teamId)
    {
    	this.teamId = teamId;
    }
   
    public String getTeamName ()
    {
    	return this.teamName;
    }
    
    public String getTeamId()
    {
    	return this.teamId;
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
     * @category                              Public Class Method
     * @description                           Determines and gets the default team of developers by index
     * @param index                           Integer representing the index 
     * @throws                                SAXException
     * @throws                                IOException
     * @throws                                CoreException
     * @throws                                ParserConfigurationException
     */
    private void fetchDefaultTeam (int index) throws SAXException, IOException, CoreException, ParserConfigurationException
    {
    	Document document = getDocumentToParse(file);
        document.getDocumentElement().normalize();
        NodeList  teams = document.getElementsByTagName(Team.TEAM_CONTEXT);
        Element teamElement = (Element) teams.item(index);
        setTeamName(teamElement.getAttribute("team_name"));
        setTeamId(teamElement.getAttribute("id"));
        System.out.print(getTeamName() + " " + getTeamId());
    }
    
    /**
     * @category     Public Class Method
     * @description  Fetches all the developer teams
     * @return       List <Team> object representing a list of the existing developers teams 
     */
    public List <Team> fetchAllTeams () throws SAXException, IOException, CoreException, ParserConfigurationException
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
    public Team fetchTeamByName (String teamName) throws SAXException, IOException, CoreException, ParserConfigurationException
    {
    	teams = fetchAllTeams();
    	for (Team team : teams)
    	if (team.getTeamName().equalsIgnoreCase(teamName))
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
    
    
}
