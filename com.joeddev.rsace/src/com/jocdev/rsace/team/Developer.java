/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          Developer.java
 * @Date          04/06/2015
 * @Description   This singleton class extends TreeWritter, and 
 *                handle all the data related to the developers
 *                using this application
 */

package com.jocdev.rsace.team;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jocdev.rsace.appTree.ConfigBuilder;
import com.jocdev.rsace.appTree.TreeBuilder;
import com.jocdev.rsace.appTree.TreeWriter;
public class Developer extends TreeWriter
{
    protected IFile file;
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    
    /**
     * @category Constructor
     */
    public Developer () 
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
    }
    
    /**
     * @category           Constructor
     * @param id           String object representing the developer's id
     * @param name         String object representing the developer's name or userName
     * @param email        String object representing the developer's email
     * @param isActive     boolean representing true if the session for this developer is 
     *                     active.
     * @param isTheOwner   boolean representing true if the developer is the owner of this
     *                     session
     */
    public Developer (String id, String name, String email, boolean isActive, boolean isTheOwner) 
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheOwner;
    }
    
    /**
     * @category     Public Class Method
     * @description  Sets the developer's id
     * @param id     String object representing the developer's id
    */
    public void setId (String id)
    {
       this.id = id;
    }
    
    /**
     * @category     Public Class Method
     * @description  Sets the developer's name
     * @param name     String object representing the developer's name
    */
    public void setName (String name)
    {
       this.name = name;
    }
    
    /**
     * @category     Public Class Method
     * @description  Sets the developer's email
     * @param email    String object representing the developer's email
    */
    public void setEmail (String email)
    {
       this.email = email;
    }
    
    /**
     * @category           Public Class Method
     * @description        Sets this developer as the session's owner
     * @param isSender     boolean object. When true, it sets this developer as the session owner
    */
    public void setAsSender (boolean isSender)
    {
        this.isTheSender = isSender;
    }
    
    /**
     * @category           Public Class Method
     * @description        Sets this session as active for this developer
     * @param isActive     boolean object. When true, session is active
    */
    public void setActive (boolean isActive)
    {
        this.active = isActive;
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets this developer's id 
     * @return       String object representing this developer's id
    */
    public String getId ()
    {
        return id;
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets this developer's name 
     * @return       String object representing this developer's name
    */
    public String getName ()
    {
        return name;
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets this developer's email
     * @return       String object representing this developer's email
    */
    public String getEmail ()
    {
        return email;
    }
    
    /**
     * @category     Public Class Method
     * @description  Determines if this developer is the owner of a session 
     * @return       True if the developer is the owner of a session. Otherwise, returns false.
     * @see          com.jocdev.rsace.appTree.AppManifestBuilder
    */
    public boolean isSessionOwner ()
    {
        return isTheSender;
    }
    
    /**
     * @category     Public Class Method
     * @description  Determines the developer's session is active
     * @return       True if the developer's session is active. Otherwise, returns false.
     * @see          com.jocdev.rsace.appTree.AppManifestBuilder
    */
    public boolean isSessionActive ()
    {
        return active;
    }
    
    /**
     * @category        Private Class Method
     * @description     This method prepares the developer's information to be inserted
     *                  in the configuration file
     * @param root      Element object representing the root node
     * @param document  Document object representing the document to create the root node
     */
    private void prepareDeveloperInfo (Element root, Document document)
    {
       
        Element developer =  createElement (document, root, "developer");
        setAtrribute(document, developer, "id", getId());
        setChildNode(document, developer, "name", getName());
        setChildNode(document, developer, "email", getEmail());
        setChildNode(document, developer, "session_active", String.valueOf(isSessionActive()));
        setChildNode(document, developer, "session_owner", String.valueOf(isSessionOwner()));
        
    }
    
    /**
     * @category    Public Class Method
     * @description This method creates a developer as the session owner, and subscribes
     *              it to the configuration file
     * @throws      ParserConfigurationException
     * @throws      CoreException
     */
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
    
    /**
     * @category    Public Class Method
     * @description This method adds a developer to a team of developers, and insert the
     *              new developer's information in a configuration file
     * @throws      SAXException
     * @throws      IOException
     * @throws      CoreException
     * @throws      ParserConfigurationException
     */
    public void addToTeam () throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        Document document = getDocumentToParse(file);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        prepareDeveloperInfo( root, document);
        InputStream is = getStream(file, document);
        file.setContents(is, IResource.NONE, null);
    }
    
    
   
}
