/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeWriter.java
 * @Date          04/06/2015
 * @Description   This class extends TreeBuilder class, and 
 *                provides methods to write data 
 *                in the configurations and resources 
 *                files of this applications  
 */
package com.jocdev.rsace.appTree;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
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
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TreeWriter extends TreeBuilder
{
   
    protected TreeBuilder configBuilder;
    protected IFile configF; // will save the file to write in
    
    /**
     * @category Constructor
     */
    protected TreeWriter()  
    {
        
         super();
    }
    
    /**
     * @category             Public Class Method
     * @description          Creates the root element for a configuration file  
     * @param doc            Document object representing the document builder 
     *                       of a configuration file
     * @param rootName       String object representing the name of the root element 
     * @param elementNS      String object representing the NS element 
     * @param headerComment  String object representing the header of a configuration file
     * @return               Element object representing the new root element
     */
    public Element createRoot (Document doc, String rootName, String elementNS, String headerComment )
    {
        Element rootElement =  doc.createElementNS(elementNS, rootName);
        rootElement.appendChild(doc.createComment(headerComment));
        doc.appendChild(rootElement);
        return rootElement;
        
    }
    
    /**
     * @category           Public Class Method
     * @description        Creates a new element
     * @param doc          Document object representing the document builder
     * @param root         Element object representing the parent element
     * @param elementName  String object representing the name of the new element 
     * @return             Element object representing the new element created
     */
    public Element createElement (Document doc,Element root, String elementName)
    {
        Element element = doc.createElement(elementName);
        root.appendChild(element);
        return element;
    }
    
    /**
     * @category            Public Class Method
     * @description         Sets the attributes for a existing element
     * @param doc           Document object representing the document builder
     * @param element       Element object representing the element that will 
     *                      will store the new attributes
     * @param attributeName String object representing the new attribute's key 
     * @param attValue      String object representing the new atributes's value 
     */
    public void setAtrribute (Document doc , Element element, String attributeName, String attValue)
    {
     // set attribute to staff element
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attValue);
        element.setAttributeNode(attr);
    }
    
    /**
     * @category           Public Class Method
     * @description        Creates a child Node
     * @param doc          Document object representing the document builder
     * @param parent       Element object representing the parent of the new child node
     * @param elementName  String object representing the parent's node name
     * @param value        String object representing the value that will hold the new child node
     */
    public void setChildNode (Document doc, Element parent, String elementName, String value)
    {
     
        Element firstname = doc.createElement(elementName);
        firstname.appendChild(doc.createTextNode(value));
        parent.appendChild(firstname);
    }
    
    /**
     * @category     Public Class Method
     * @description  Creates and gets a new Document object   
     * @param file   IFile object representing the file where the 
     *               new document will build all the elements
     * @return       Document object representing the new Document object
     * @throws       ParserConfigurationException
     */
    protected Document getNewDocument (IFile file) throws ParserConfigurationException
    {
        DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
        return docBuilder.newDocument();
        
    }
    
    /**
     * @category     Public Class Method
     * @description  Creates and gets a parsed Document object     
     * @param file   IFile object representing the file where the 
     * @return       Document object representing the parsed Document
     * @throws       SAXException
     * @throws       IOException
     * @throws       CoreException
     * @throws       ParserConfigurationException
     */
    public Document getDocumentToParse (IFile file) throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        
        DocumentBuilderFactory docBuilF = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder =  docBuilF.newDocumentBuilder();
        return docBuilder.parse(file.getContents());
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets and creates a new InputStream object from the
     *               contents of a given resource file ready to be inserted 
     *               in a configuration file
     * @param file   IFile object representing the resource file 
     * @param doc    Document object representing the Document builder object
     * @return       InputStream object representing the contents of the resource file given
     */
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
}
