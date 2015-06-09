package com.jeeddev.rsace.appTree;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Developer extends AbstractTreeWriter
{
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    private String fileTarget;
    public static final String ROOT_DEVELOPERS = "Developers";
    private Element root;
    private Element subType;
    public Developer () throws ParserConfigurationException
    {
        super();
        root = super.createRoot(ROOT_DEVELOPERS);
    }
    public Developer (String id, String name, String email, boolean isTheSender, boolean isActive) throws ParserConfigurationException
    {
        super();
        root = super.createRoot(ROOT_DEVELOPERS);
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheSender;
        
        
    }
    public void syncWithRsace ()
    {
        try
        {
            InputStream is;
            IFile file = getFile(TreeBuilder.CONFIG_DIR, getFileTarget() );
            
            
                prepareToWrite();
                is = writeInEmptyFile(file, super.doc);
                file.setContents(is, IResource.NONE, null);
           
           
           
                
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    private void prepareToWrite()
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
