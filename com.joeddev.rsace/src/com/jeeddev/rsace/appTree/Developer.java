package com.jeeddev.rsace.appTree;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jeeddev.rsace.appTree.*;
public class Developer extends TreeWriter
{
    private IFile file;
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    
    public Developer () 
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
    }
    public Developer (String id, String name, String email, boolean isActive, boolean isTheSender) 
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheSender;
    }
    
    public void setId (String id)
    {
       this.id = id;
    }
    
    public void setName (String name)
    {
       this.name = name;
    }
    
    public void setEmail (String email)
    {
       this.email = email;
    }
    
    public void setAsSender (boolean isSender)
    {
        this.isTheSender = isSender;
    }
    
    public void setActive (boolean isActive)
    {
        this.active = isActive;
    }
    
    public String getId ()
    {
        return id;
    }
    public String getName ()
    {
        return name;
    }
    
    public String getEmail ()
    {
        return email;
    }
    
    public boolean isSender ()
    {
        return isTheSender;
    }
    
    public boolean isActive ()
    {
        return active;
    }
    
   
    
    private void prepareDeveloperInfo (Element root, Document document)
    {
       
        Element developer =  createElement (document, root, "developer");
        setAtrribute(document, developer, "id", getId());
        setChildNode(document, developer, "name", getName());
        setChildNode(document, developer, "email", getEmail());
        setChildNode(document, developer, "session_active", String.valueOf(isActive()));
        setChildNode(document, developer, "session_owner", String.valueOf(isSender()));
        
    }
    
    public void setAsSessionOwner () throws ParserConfigurationException, CoreException
    {
        Document document = getNewDocument(file);
        Element root = createRoot(document, "developers_team", 
                                  "http://jocdev.com/Rsace/ConfigFiles/developers",
                                  "DO NOT EDIT THIS FILE MANUALLY. You can safely edit this file from your rsace's preferences");
        prepareDeveloperInfo( root, document);
        InputStream is = getStream(file, document);
        file.setContents(is, IResource.NONE, null);
        
    }
    
    public void addToTeam () throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        Document document = getDocumentToParse(file);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        prepareDeveloperInfo( root, document);
        InputStream is = getStream(file, document);
        file.setContents(is, IResource.NONE, null);
    }
    
    // Get it to work
    public ArrayList <Developer> getDevelopersInTeam ()
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
    
    public Developer getDeveloperFromTeamByName (String name)
    {
        
        for (Developer dev : getDevelopersInTeam())
            if (dev.getName().equalsIgnoreCase(name))
                return dev;
        return null;
    }
    
    public Developer getDeveloperFromTeamById (String id)
    {
       for (Developer dev : getDevelopersInTeam())
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
   
}
