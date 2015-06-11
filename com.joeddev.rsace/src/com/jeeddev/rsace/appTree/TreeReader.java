package com.jeeddev.rsace.appTree;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.joeddev.rsace.configResources.AppManifestBuild;

public class TreeReader 
{
    TreeWriter  treeWriter;
    public TreeReader ()
    {
        treeWriter = TreeWriter.getInstance();
    }
    
    public AppManifestBuild getManifestData () throws SAXException, IOException, CoreException, ParserConfigurationException
    {
        IFile file = treeWriter.getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.MANIFEST_FILE_CONFIG);
        AppManifestBuild manifest = new AppManifestBuild();
        Document document = treeWriter.getDocumentToParse(file);
        getAppAttributes (document, manifest);
        return manifest;
        
    }
    
    private void getAppAttributes (Document document, AppManifestBuild manifest)
    {
        Node application = document.getElementsByTagName("application").item(0);
        NamedNodeMap appAttributes = application.getAttributes();
        manifest.setAppName(appAttributes.item(0).getNodeValue());
        manifest.setAppVersion(appAttributes.item(1).getNodeValue());
        manifest.setAppProduct(appAttributes.item(2).getNodeValue());
    }
}