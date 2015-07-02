/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          Developer.java
 * @Date          04/06/2015
 * @Description   This singleton class extends TreeWritter, and 
 *                handle all the data related to the developers
 *                using this application
 */

package com.jortizsd.rsace.remote;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
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
import com.jortizsd.rsace.appTree.TreeWriter;

public class Developer extends TreeWriter implements DatabaseInterface
{
    protected IFile file;
    private Team team;
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    private boolean isFavorite;
    private String guestCode;
    private Connection conn;
    public static final String DEVELOPER_CONTEXT = "developer";
    public static final String DB_FIELD_DEV_ID = "dev_id";
    public static final String DB_FIELD_DEV_NAME = "dev_name";
    public static final String DB_FIELD_DEV_EMAIL = "dev_email";
    public static final String DB_FIELD_DEV_ISTEAMOWNER = "isTeamOwner";
    private boolean isRegistered;
    
    
    
   
    
    /**
     * @throws IOException 
     * @throws SQLException 
     * @category Constructor
     */
    public Developer ()  
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        setRegistrationState(false);
        
    }
    
    public Developer (String developerId)
    {
    	super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        setId(developerId);
        setRegistrationState(false);
    }
    
    /**
     * @category           Constructor
     * @param team         String representing the developer's team
     * @param id           String object representing the developer's id
     * @param name         String object representing the developer's name or userName
     * @param email        String object representing the developer's email
     * @param isActive     boolean representing true if the session for this developer is 
     *                     active.
     * @param isTheOwner   boolean representing true if the developer is the owner of this
     *                     session
     */
    public Developer (Team team, String id, String name, String email, boolean isActive, boolean isTheOwner) 
    {
        super();
        file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheOwner;
        this.team = team;
        setRegistrationState(false);
       
    }
    
    
    
   
    
    /**
     * @category     Public Class Method
     * @description  Sets the new developers's team
     * @param team   Team object representing new team of the developer
    */
    public void setTeam (Team team)
    {
    	this.team = team;
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
    public void setAsOwner (boolean isSender)
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
     * @category           Public Class Method
     * @description        Sets this developer as favorite
     * @param isFavorite   boolean object. When true, this developer is added to favorites
    */
    public void setAsFavorite (boolean isFavorite)
    {
    	this.isFavorite = isFavorite;
    }
    
    /**
     * @category           Public Class Method
     * @description        Sets a guest code for this developer
     * @param guestCode    String object representing a guest code
    */
    public void setGuestCode (String guestCode)
    {
    	this.guestCode = guestCode;
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets this developer's team
     * @return       Team object representing the developer's team
    */
    public Team getTeam()
    {
    	return team;
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
     * @see          com.jortizsd.rsace.appTree.AppManifestBuilder
    */
    public boolean isSessionOwner ()
    {
        return isTheSender;
    }
    
    /**
     * @category     Public Class Method
     * @description  Determines the developer's session is active
     * @return       True if the developer's session is active. Otherwise, returns false.
     * @see          com.jortizsd.rsace.appTree.AppManifestBuilder
    */
    public boolean isSessionActive ()
    {
        return active;
    }
    
    /**
     * @category     Public Class Method
     * @description  Determines if the developer is in favorites
     * @return       True if the developer is in favorites. Otherwise, returns false.
     * @see          com.jortizsd.rsace.appTree.AppManifestBuilder
    */
    public boolean isFavorite ()
    {
    	return isFavorite;
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets a guest code
     * @return       A String object representing the guest code for this developer
     */
    public String getGuestCode ()
    {
    	return this.guestCode;
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
        setChildNode(document, developer, "id", getId());
        setChildNode(document, developer, "name", getName());
        setChildNode(document, developer, "email", getEmail());
        setChildNode(document, developer, "session_active", String.valueOf(isSessionActive()));
        setChildNode(document, developer, "session_owner", String.valueOf(isSessionOwner()));
        setChildNode(document, developer, "is_in_favorites", String.valueOf(isFavorite()));
        
    }
    
 
    
    
    
   
    
    /**
     * @category    Public Class Method
     * @description This method creates a developer as the session owner, and subscribes
     *              it to the configuration file
     * @throws      ParserConfigurationException
     * @throws      CoreException
     */
    public void setAsSessionOwner () 
    {
    	try 
    	{
    	   setTeam(team);
           Document document = getNewDocument(file);
           Element root = createRoot(document, "rsace_developer_teams", 
                                  "http://jocdev.com/Rsace/ConfigFiles/rsace_developers_team",
                                  "DO NOT EDIT THIS FILE MANUALLY. You can safely edit this file from your rsace's preferences");
           Element teamElement = createElement(document,root,"team");
           setAtrribute(document, teamElement, "id", team.getTeamId());
           setAtrribute(document, teamElement, "team_name", team.getTeamName());
           prepareDeveloperInfo( teamElement, document);
           InputStream is = getStream(file, document);
           file.setContents(is, IResource.NONE, null);
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
        
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
    	try
    	{
    	   setTeam(team);
           Document document = getDocumentToParse(file);
           document.getDocumentElement().normalize();
           NodeList  teams = document.getElementsByTagName(Team.TEAM_CONTEXT);
           for (int i = 0; i<teams.getLength(); i++)
           {
        	   Element tmpTeam = (Element) teams.item(i);
        	   if (tmpTeam.getAttribute("id").equalsIgnoreCase(team.getTeamId()))
        	   {
        		   prepareDeveloperInfo( tmpTeam, document);
                   InputStream is = getStream(file, document);
                   file.setContents(is, IResource.NONE, null);
        	   }
           }
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
        
    }
    
    /**
     * @category      Public Class Method
     * @description   Removes this developer from its team
     * @return        True if the developer was successfully removed from its team 
     *                Otherwise, returns false.
     */
    public boolean removeFromTeam ()
    {
    	boolean removed = false;
    	try 
    	{
    		String id;
			Document document = getDocumentToParse(file);
			NodeList developersList = document.getElementsByTagName(DEVELOPER_CONTEXT);
			for (int i = 0; i<developersList.getLength(); i++)
			{
				if (developersList.item(i).getNodeType() == Node.ELEMENT_NODE) 
                {
             	    Element developer = (Element) developersList.item(i);
                    id = developer.getElementsByTagName("id").item(0).getTextContent();
                    if (id.equalsIgnoreCase(getId()))
                    {
                    	developer.getParentNode().removeChild(developer);
                    	document.getDocumentElement().normalize();
                    	InputStream is = getStream(file, document);
                        file.setContents(is, IResource.NONE, null);
                    	removed = true;
                    	break;
                    }
                    	
                    
                 }
			 }
			 return removed;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
    	return removed;
    }
    
    /**
     * @category       Public Class Method
     * @description    Updates an existing data from this developer
     * @param tag      String object representing the name of the tag that holds the value
     * @param value    String object representing the name of the tag's value
     * @return         True if this developer was successfully updated. Otherwise, returns false. 
     */
    public boolean update (String tag, String value) 
    {
    	boolean removed = false;
    	try 
    	{
    		String id;
			Document document = getDocumentToParse(file);
			NodeList developersList = document.getElementsByTagName(DEVELOPER_CONTEXT);
			for (int i = 0; i<developersList.getLength(); i++)
			{
				if (developersList.item(i).getNodeType() == Node.ELEMENT_NODE) 
                {
             	    Element developer = (Element) developersList.item(i);
                    id = developer.getElementsByTagName("id").item(0).getTextContent();
                    if (id.equalsIgnoreCase(getId()))
                    {
                    	
                        Node node = developer.getElementsByTagName(tag).item(0).getFirstChild();
                        node.setTextContent(value);
                    	document.getDocumentElement().normalize();
                    	InputStream is = getStream(file, document);
                        file.setContents(is, IResource.NONE, null);
                    	removed = true;
                    	break;
                    }
                    	
                    
                 }
			 }
			 return removed;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
    	return removed;
    }
    
    @Override
	public void addDeveloperToDB() 
    {
    	try
    	{
    	   String query = RemoteConstants.REMOTE_DB_INSERT_QUERY;
    	   URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
           conn = AppConfig.getConnectionFromRemoteConfigFile(url);
    	   PreparedStatement preparedStmt = conn.prepareStatement(query);
    	   preparedStmt.setString(1, getTeam().getTeamId());
    	   preparedStmt.setString(2, getId());
    	   preparedStmt.setString(3, getTeam().getTeamName());
    	   preparedStmt.setString(4, getName());
    	   preparedStmt.setString (5, getEmail());
    	   preparedStmt.setBoolean (6, isSessionOwner());
    	   // execute the preparedstatement
    	   preparedStmt.execute();
    	   conn.close();
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
	}
    
   

	@Override
	public void updateDeveloperFromDB() 
	{
		try
		{
		    String query = RemoteConstants.REMOTE_DB_UPDATE_QUERY;
		    URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            conn = AppConfig.getConnectionFromRemoteConfigFile(url);
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setString(1, getTeam().getTeamName());
	    	preparedStmt.setString(2, getName());
	    	preparedStmt.setString (3, getEmail());
	    	preparedStmt.setBoolean (4, isSessionOwner());
	    	preparedStmt.setString (5, getId());
	        // execute the java preparedstatement
	        preparedStmt.executeUpdate();
	        conn.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
    public List <Team> getAllMyTeams ()
	{
		List <Team> myTeams = new ArrayList <>();
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
              if (getId().equalsIgnoreCase(rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVID)))
              {
            	  String teamId = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMID);
            	  String teamName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMNAME);
            	  myTeams.add(Team.createNewTeam(teamName, teamId));
              }
            }
            st.close();
           return myTeams;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
		return myTeams;
		
	}
	
	public List <Developer> getMyTeamMembers ()
	{
		List <Developer> teamMembers = new ArrayList <> ();
		String query = "SELECT * FROM developers WHERE team_id = " + getTeam().getTeamId();
		try
    	{
    	    URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            Connection conn = AppConfig.getConnectionFromRemoteConfigFile(url);
            // create the java statement
            Statement st = conn.createStatement();
             
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
             
            // iterate through the java resultset
            while (rs.next())
            {
            	String teamId = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMID);
            	String teamName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVTEAMNAME);
          	    String devId = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVID);
          	    String devName = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVNAME);
                String devEmail = rs.getString(RemoteConstants.REMOTE_DB_FIELD_DEVEMAIL);
                boolean isOwner = rs.getBoolean(RemoteConstants.REMOTE_DB_FIELD_ISTEAMOWNER);
                team = Team.createNewTeam(teamName, teamId);
                teamMembers.add(new Developer(team,devId,devName,devEmail,false,isOwner));
            }
            st.close();
           return teamMembers;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
		return teamMembers;
		
	}
	
	public void setRegistrationState (boolean isRegistered)
	{
		this.isRegistered = isRegistered;
	}
	
	public boolean isRegisteredInTeam ()
	{
		String query = "SELECT * FROM developers WHERE " + RemoteConstants.REMOTE_DB_FIELD_DEVID + "=" + getId() + 
				       " OR " + RemoteConstants.REMOTE_DB_FIELD_DEVTEAMID + "=" + getTeam().getTeamId();
		try
    	{
    	    URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            Connection conn = AppConfig.getConnectionFromRemoteConfigFile(url);
            // create the java statement
            Statement st = conn.createStatement();
             
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
             
            if (rs.next())
            	return true;
            st.close();
            return false;
          
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
		
		return false;
	}
    
	public boolean isRegistered ()
	{
		String query = "SELECT * FROM developers WHERE " + RemoteConstants.REMOTE_DB_FIELD_DEVID + "=" + getId();
	    try
	    {
	        URL url = new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL);
            Connection conn = AppConfig.getConnectionFromRemoteConfigFile(url);
            // create the java statement
            Statement st = conn.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            if (rs.next())
     	       return true;
            st.close();
            return false;
        }
	    catch (Exception e)
	    {
		    System.out.println(e.getMessage());
	    }
        return false;
	}
	
	
    /**
     * @category     Public Class Method
     * @description  Overrides the method toString from the superClass to return 
     *               a description for this object
     */
    @Override
    public String toString ()
    {
    	super.toString();
    	String developerContext;
    	developerContext = "**************************Developer Member********************************\n" + 
    			           "Developer's user name: " + getName() + "\n" + 
    	                   "Developer's id: " + getId() + "\n" + 
    			           "Developer's email: " + getEmail() + "\n" + 
    	                   "Is this developer the owner of this session? " + isSessionOwner() + "\n" +
    	                   "Is this developer in Favorites? " + isFavorite() + "\n" +
    			           "**************************************************************************\n";
    	                   
    	return developerContext;
    			           
    }

	@Override
	public void deleteDeveloperFromDB() {
		// TODO Auto-generated method stub
		
	}
}
