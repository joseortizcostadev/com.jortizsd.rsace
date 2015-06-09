package com.jeeddev.rsace.appTree;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract class AbstractTreeWriter extends TreeBuilder
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    TreeBuilder configBuilder;
    IFile configF;
    
    public AbstractTreeWriter() throws ParserConfigurationException 
    {
        
        super();
        docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilder =  docBuilderFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        // TODO Auto-generated constructor stub
    }
    
    public Element createRoot (String rootName )
    {
        Element rootElement = doc.createElement(rootName);
        doc.appendChild(rootElement);
        return rootElement;
        
    }
    
    public Element createElement (Element root, String elementName)
    {
        Element element = doc.createElement(elementName);
        root.appendChild(element);
        return element;
        
    }
    
    public void setAtrribute (Element element, String attributeName, String attValue)
    {
     // set attribute to staff element
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attValue);
        element.setAttributeNode(attr);
    }
    
    public void setChildNode (Element parent, String elementName, String value)
    {
     
        Element firstname = doc.createElement(elementName);
        firstname.appendChild(doc.createTextNode(value));
        parent.appendChild(firstname);
    }
    
    
    // Needs
   
    
    protected InputStream getStream (IFile file, Document doc)
    {
        try
        {
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);
            String xmlString=result.getWriter().toString();
            byte[] bytes = xmlString.getBytes();
            return new ByteArrayInputStream(bytes);
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
     
    public ArrayList <Developer> getDevelopers (IFile file)
    {
        try
        { 
           String id, name, email;
           boolean isActive, isSender;
           DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
           Document doc = documentBuilder.parse(file.getContents());
           doc.getDocumentElement().normalize();
           NodeList nList = doc.getElementsByTagName("developer");
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
                   isActive = Boolean.valueOf(eElement.getElementsByTagName("active").item(0).getTextContent());
                   isSender = Boolean.valueOf(eElement.getElementsByTagName("sender").item(0).getTextContent());
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
    
    public Developer getDeveloperByName (String name, boolean isRequester)
    {
        String fileContext;
        
        if (isRequester)
            fileContext = ConfigBuilder.SERVER_FILE_CONFIG;
        else
            fileContext = ConfigBuilder.CLIENT_FILE_CONFIG;
        getFile(TreeBuilder.CONFIG_DIR, fileContext );
        for (Developer dev : getDevelopers(getFile(TreeBuilder.CONFIG_DIR, fileContext )))
            if (dev.getName().equalsIgnoreCase(name))
                return dev;
        return null;
    }
    
    public Developer getDeveloperById (String id, boolean isRequester)
    {
        String fileContext;
        
        if (isRequester)
            fileContext = ConfigBuilder.SERVER_FILE_CONFIG;
        else
            fileContext = ConfigBuilder.CLIENT_FILE_CONFIG;
        getFile(TreeBuilder.CONFIG_DIR, fileContext );
        for (Developer dev : getDevelopers(getFile(TreeBuilder.CONFIG_DIR, fileContext )))
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
    
    public Developer addToDeveloperTeam (String id, String name, String email, boolean isActive, Boolean isSender)
    {
        try
        {
           Developer dev = new Developer (id, name, email, isActive, isSender);
           String fileTarget;
        
           if (isSender)
               fileTarget = ConfigBuilder.SERVER_FILE_CONFIG;
           else
               fileTarget = ConfigBuilder.CLIENT_FILE_CONFIG;
        
           InputStream is;
           IFile file = getFile(TreeBuilder.CONFIG_DIR, fileTarget );
           System.out.println("fileEmpty");
           dev.prepareToJoinDeveloperTeam();
           is = getStream(file, this.doc);
           file.setContents(is, IResource.NONE, null);
        
           if (file.getContents() == null)
           {
               
           }
           else
           {
               
           }
           return dev;
        }
        catch (Exception e)
        {
            
        }
    
        return null;
    }

}
