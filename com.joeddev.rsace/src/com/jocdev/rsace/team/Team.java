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
package com.jocdev.rsace.team;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jocdev.rsace.appTree.ConfigBuilder;
import com.jocdev.rsace.appTree.TreeBuilder;
public class Team extends Developer
{
	private List <Developer >developers;
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
        developers = new ArrayList<>();
        this.teamName = teamName;
        this.teamId = teamId;
       
        
        
    }
	
	private Team (String teamName)
	{
		
	}
	
	
	
	
    
    public static Team createNewTeam (String id, String teamName)
    {
    	
    	return new Team(id,teamName);
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
     * @see         com.jocdev.rsace.team.Developer
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
                            id = developer.getAttribute("id");
                            name = developer.getElementsByTagName("name").item(0).getTextContent();
                            email = developer.getElementsByTagName("email").item(0).getTextContent();
                            isActive = Boolean.valueOf(developer.getElementsByTagName("session_active").item(0).getTextContent());
                            isSender = Boolean.valueOf(developer.getElementsByTagName("session_owner").item(0).getTextContent());
                            developers.add(new Developer(id,name,email,isActive,isSender));
                            
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
     * @see         com.jocdev.rsace.team.Developer
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
     * @see         com.jocdev.rsace.team.Developer
     */
    public Developer getDeveloperFromTeamById (String id)
    {
       for (Developer dev : getDevelopers())
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
    
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
    
    
}
