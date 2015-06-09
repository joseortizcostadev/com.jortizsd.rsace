package com.jeeddev.rsace.appTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Developer extends AbstractTreeWriter
{
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder db;
    
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    private String fileTarget;
    public static final String ROOT_DEVELOPERS = "developers";
    private Element root;
    private Element subType;
    public Developer () throws ParserConfigurationException
    {
        super();
        root = super.createRoot(ROOT_DEVELOPERS);
    }
    public Developer (String id, String name, String email, boolean isActive, boolean isTheSender) throws ParserConfigurationException
    {
        super();
        root = super.createRoot(ROOT_DEVELOPERS);
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheSender;
        this.fileTarget = ConfigBuilder.SERVER_FILE_CONFIG;
        
        
    }
    
    public void addToTeam () throws CoreException, TransformerException, ParserConfigurationException, SAXException, IOException
    {
        
        IFile file = getFile(TreeBuilder.CONFIG_DIR, this.fileTarget);
        prepareToJoinDeveloperTeam();
        InputStream is = getStream(file, super.doc);
        file.setContents(is, IResource.NONE, null);
        
        
        
        
    }

    public void updateInfo (String value)
    {
        
    }
    public void prepareToJoinDeveloperTeam()
    {
        setId(this.id);
        setName(this.name);
        setEmail(this.email);
        setActive(this.isActive());
        setAsSender(this.isTheSender);
    }
    public void setId (String id)
    {
        Element subType = super.createElement(this.root, "developer");
        super.setAtrribute(subType, "id", id);
        this.subType = subType;
    }
    
    public void setName (String name)
    {
        setChildNode(this.subType, "name", name);
    }
    
    public void setEmail (String email)
    {
        setChildNode(this.subType, "email", email);
    }
    
    public void setAsSender (boolean isSender)
    {
        setChildNode(this.subType, "sender", String.valueOf(isSender));
    }
    
    public void setActive (boolean isActive)
    {
        setChildNode(this.subType, "active", String.valueOf(isActive));
    }
    public void setFileTarget (String fileTarget)
    {
        this.fileTarget = fileTarget;
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
    
    public String getFileTarget ()
    {
        return fileTarget;
    }
    
   
}
