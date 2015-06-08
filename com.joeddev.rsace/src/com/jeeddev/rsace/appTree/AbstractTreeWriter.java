package com.jeeddev.rsace.appTree;



import java.io.File;
import java.nio.file.Path;

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
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
    
    
    public void saveInConfigFile (String folderName, String fileName)
    {
     // write the content into xml file
        try
        {
        IFile file = getFile(folderName, fileName);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        File f = null;
        DOMSource source = new DOMSource(doc);
        IPath location = file.getLocation();
        if (location != null)
          f = location.toFile();
        StreamResult result = new StreamResult(f);
 
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
 
        transformer.transform(source, result);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    

}
