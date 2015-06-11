package com.jeeddev.rsace.appTree;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
    
    
    protected TreeWriter()  
    {
        
         super();
    }
    
   
    
    public Element createRoot (Document doc, String rootName, String elementNS, String headerComment )
    {
        Element rootElement =  doc.createElementNS(elementNS, rootName);
        rootElement.appendChild(doc.createComment(headerComment));
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
    
    
   
    protected Document getNewDocument (IFile file) throws ParserConfigurationException
    {
        DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
        return docBuilder.newDocument();
        
    }
    
    public Document getDocumentToParse (IFile file) throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        
        DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
        return docBuilder.parse(file.getContents());
    }

    protected InputStream getStream (IFile file, Document doc)
    {
        try
        {
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
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
     
    public static String createRandomId ()
    {
        
        int id = 0;
        for (int i = 0; i<5; i++)
        {
            id += randInt(1, 500);
        }
        return String.valueOf(id);
        
        
    }
    
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
