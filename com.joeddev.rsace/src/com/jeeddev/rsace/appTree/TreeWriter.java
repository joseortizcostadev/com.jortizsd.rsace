package com.jeeddev.rsace.appTree;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TreeWriter extends TreeBuilder
{
    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;
    Document doc;
    TreeBuilder configBuilder;
    IFile configF;
    public static TreeWriter instance = new TreeWriter() ;
    
    private TreeWriter()  
    {
        
         super();
    }
    
    public static TreeWriter getInstance ()
    {
        return instance;
    }
    
    public Element createRoot (Document doc, String rootName )
    {
        Element rootElement = doc.createElement(rootName);
        doc.appendChild(rootElement);
        return rootElement;
        
    }
    
    public Element createElement (Document doc,Element root, String elementName)
    {
        Element element = doc.createElement(elementName);
        root.appendChild(element);
        return element;
        
    }
    
    public void setAtrribute (Document doc , Element element, String attributeName, String attValue)
    {
     // set attribute to staff element
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attValue);
        element.setAttributeNode(attr);
    }
    
    public void setChildNode (Document doc, Element parent, String elementName, String value)
    {
     
        Element firstname = doc.createElement(elementName);
        firstname.appendChild(doc.createTextNode(value));
        parent.appendChild(firstname);
    }
    
    
    public void addDeveloperToTeam (Developer dev, boolean addFirst) throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        IFile file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.TEAM_FILE_CONFIG);
        DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
        Document document;
        Element root;
        if (!addFirst)
        {
             System.out.println("Im greater than 0");
             document = docBuilder.parse(file.getContents());
             root = document.getDocumentElement();
        }
        else
        {
            System.out.println("I am less than 0");
            document = docBuilder.newDocument();
            root = createRoot(document, "developers");
        }
        Element developer =  createElement (document, root, "developer");
        setAtrribute(document, developer, "id", dev.getId());
        setChildNode(document, developer, "name", dev.getName());
        setChildNode(document, developer, "email", dev.getEmail());
        setChildNode(document, developer, "session_active", String.valueOf(dev.isActive()));
        setChildNode(document, developer, "session_owner", String.valueOf(dev.isSender()));
        InputStream is = getStream(file, document);
        file.setContents(is, IResource.NONE, null);
        
    }

    
    
    
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
            fileContext = ConfigBuilder.TEAM_FILE_CONFIG;
        else
            fileContext = ConfigBuilder.TEAM_FILE_CONFIG;
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
            fileContext = ConfigBuilder.TEAM_FILE_CONFIG;
        else
            fileContext = ConfigBuilder.TEAM_FILE_CONFIG;
        getFile(TreeBuilder.CONFIG_DIR, fileContext );
        for (Developer dev : getDevelopers(getFile(TreeBuilder.CONFIG_DIR, fileContext )))
            if (dev.getId().equalsIgnoreCase(id))
                return dev;
        return null;
    }
    
    
    
    public Document getDocParser (IFile file) throws ParserConfigurationException, SAXException, IOException, CoreException
    {
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(file.getContents());
        doc.getDocumentElement().normalize();
        return doc;
    }

}
