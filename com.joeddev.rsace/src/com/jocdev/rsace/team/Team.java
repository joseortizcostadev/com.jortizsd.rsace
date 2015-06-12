package com.jocdev.rsace.team;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Team extends Developer
{
    public Team ()
    {
    	
    }
    
    /**
     * @category    Public Class Method
     * @description Gets all the developers that pertain to the team 
     *              that is working in the current session
     * @return      List object containing all the developers working at this session 
     * @see         com.jocdev.rsace.team.Developer
     */
    public List <Developer> getDevelopersInTeam ()
    {
        try
        { 
           String id, name, email;
           boolean isActive, isSender;
           Document document = getDocumentToParse(file);
           document.getDocumentElement().normalize();
           NodeList nList = document.getElementsByTagName("developer");
           ArrayList <Developer> developers = new ArrayList<>();
           
           for (int i = 0; i<nList.getLength(); i++)
           {
        	   
               Node nNode = nList.item(i);
               if (nNode.getNodeType() == Node.ELEMENT_NODE) 
               {
            	   
                   Element eElement = (Element) nNode;
                   id = eElement.getAttribute("id");
                   name = eElement.getElementsByTagName("name").item(0).getTextContent();
                   email = eElement.getElementsByTagName("email").item(0).getTextContent();
                   isActive = Boolean.valueOf(eElement.getElementsByTagName("session_active").item(0).getTextContent());
                   isSender = Boolean.valueOf(eElement.getElementsByTagName("session_owner").item(0).getTextContent());
                   developers.add(new Developer(id,name,email,isActive,isSender));
                   
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
        
        for (Developer dev : getDevelopersInTeam())
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
       for (Developer dev : getDevelopersInTeam())
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
}
