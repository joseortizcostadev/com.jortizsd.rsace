/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeReader.java
 * @Date          04/06/2015
 * @Description   This class extends TreeWritter class, and 
 *                provides methods to read data 
 *                from the configurations and resources 
 *                files of this applications  
 */
package com.jortizsd.rsace.appTree;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TreeReader extends TreeWriter
{
    /**
     * @category Constructor
     */
    public TreeReader ()
    {
        super();
    }
    
    /**
     * @category    Public Class Method
     * @description Gets the manifest data
     * @return      AppManifestBuild object representing a object containing the manifest data
     * @throws      SAXException
     * @throws      IOException
     * @throws      CoreException
     * @throws      ParserConfigurationException
     * @see         com.jortizsd.rsace.appTree.AppManifestBuild
     */
    public AppManifestBuild getManifestData () throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        IFile file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.MANIFEST_FILE_CONFIG);
        AppManifestBuild manifest = AppManifestBuild.getInstance();
        Document document = getDocumentToParse(file);
        getAppAttributes (document, manifest);
        return manifest;
        
    }
    
    /**
     * @category        Private Class Method
     * @description     Gets attributes from the manifest file
     * @param document  Document object representing the document that parse the elements 
     *                  in the manifest
     * @param manifest  AppManifestBuild representing the manifest object that will store 
     *                  those attributes
     * @see             com.jortizsd.rsace.appTree.AppManifestBuild
     */
    private void getAppAttributes (Document document, AppManifestBuild manifest)
    {
        Node application = document.getElementsByTagName("application").item(0);
        NamedNodeMap appAttributes = application.getAttributes();
        manifest.setAppName(appAttributes.item(0).getNodeValue());
        manifest.setAppVersion(appAttributes.item(1).getNodeValue());
        manifest.setAppProduct(appAttributes.item(2).getNodeValue());
    }
    
    
}
