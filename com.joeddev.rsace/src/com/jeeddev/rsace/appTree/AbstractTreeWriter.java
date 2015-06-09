package com.jeeddev.rsace.appTree;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
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
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);
        String xmlString=result.getWriter().toString();
        byte[] bytes = xmlString.getBytes();
        InputStream sources = new ByteArrayInputStream(bytes);
        file.setContents(sources, IResource.NONE, null);
        
        
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateInConfigFile (String folderName, String fileName)
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
        StreamResult result = new StreamResult(new StringWriter());
        
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
 
        transformer.transform(source, result);
        String xmlString=result.getWriter().toString();
     // Inserts contents using binary approach 
        byte[] bytes = xmlString.getBytes();
        InputStream sources = new ByteArrayInputStream(bytes);
        file.appendContents(sources, IResource.NONE, null);
        
        file.refreshLocal(IResource.DEPTH_INFINITE, null);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    /*
    public void append ()
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("server.xml");
        Element root = document.getDocumentElement();
        
        Collection<Node> servers = new ArrayList<Node>();
        servers.add(new Server());

        for (Server server : servers) {
            // server elements
            Element newServer = document.createElement("server");
            root.appendChild(newServer);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(server.getName()));
            newServer.appendChild(name);

            Element port = document.createElement("port");
            port.appendChild(document.createTextNode(Integer.toString(server.getPort())));
            newServer.appendChild(port);

            root.appendChild(newServer);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("server.xml");
        transformer.transform(source, result);
    }
    */
    
    
    
    

}
