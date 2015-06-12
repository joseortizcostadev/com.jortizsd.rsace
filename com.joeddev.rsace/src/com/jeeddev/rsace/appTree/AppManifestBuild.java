package com.jeeddev.rsace.appTree;

import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.joeddev.rsace.preferences.DVTPreferencesGetter;



public class AppManifestBuild extends TreeWriter
{
    private static final AppManifestBuild instance = new AppManifestBuild();
    private DVTPreferencesGetter devPreferencesGetter;
    private IFile file;
    int launch;
    boolean hasSessionPermission;
    String entryPoint;
    String appName;
    String appVersion;
    String appProduct;
    
    
    private AppManifestBuild ()
    {
        super();
        this.devPreferencesGetter = new DVTPreferencesGetter();
        this.file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.MANIFEST_FILE_CONFIG);
    }
    
    public static AppManifestBuild getInstance ()
    {
        return instance;
    }
    public void setAppName (String appName)
    {
        this.appName = appName;
    }
    public void setAppVersion (String appVersion)
    {
        this.appVersion = appVersion;
    }
    public void setAppProduct (String appProduct)
    {
        this.appProduct = appProduct;
    }
    public void setEntryPoint (String entryPointClassName)
    {
        this.entryPoint = entryPointClassName;
    }
    
    public String getAppName ()
    {
        return this.appName;
    }
    public String getEntryPointClassName ()
    {
        return this.entryPoint;
    }
    
    public String getAppVersion ()
    {
        return this.appVersion;
    }
    
    public String getAppProduct ()
    {
        return this.appProduct;
    }
    
    private void prepareManifestData (Element root, Document document)
    {
        Element appName =  createElement (document, root, "application");
        setAtrribute(document, appName, "app_name", "com.joeddev.com.rsace");
        setAtrribute(document,appName,"version", "1.0");
        setAtrribute(document,appName,"product", "debugging");
        Element launcher =  createElement (document, root, "Launcher");
        setAtrribute(document, launcher, "times_executed", "1");
        Element appEntryPoint =  createElement (document, root, "AppMain");
        setAtrribute(document, appEntryPoint, "entry_point", "InitHandler.class");
        Element remotePermissions =  createElement (document, root, "Remote_Session");
        String permissions = String.valueOf(devPreferencesGetter.getRemotePermissionsState());
        setAtrribute(document, remotePermissions, "permissions", permissions);
    }
    
    public void makeManifestFile () throws ParserConfigurationException, CoreException
    {
        Document document = getNewDocument(file);
        Element root = createRoot(document, "rsace_manifest",
                                  "http://jocdev.com/Rsace/ConfigFiles/manifest",
                                  "DO NOT EDIT THIS FILE");
        prepareManifestData(root, document);
        InputStream is = getStream(this.file, document);
        file.setContents(is, IResource.NONE, null);
    }
    
}