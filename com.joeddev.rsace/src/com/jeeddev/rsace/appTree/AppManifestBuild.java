/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          AppManifestBuild.java
 * @Date          04/06/2015
 * @Description   This singleton class extends TreeWritter class, and 
 *                build the manifest file for this application
 */
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
    private DVTPreferencesGetter devPreferencesGetter; // preferences page
    private IFile file;
    int launch; // number of times the program is execited
    boolean hasSessionPermission;
    String entryPoint;
    String appName;
    String appVersion;
    String appProduct;
    
    /**
     * @category Constructor
     */
    private AppManifestBuild ()
    {
        super();
        this.devPreferencesGetter = new DVTPreferencesGetter();
        // Empty manifest file
        this.file = getFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.MANIFEST_FILE_CONFIG);
    }
    
    /**
     * @category     Public Class Method
     * @description  Gets an instance from this class  
     * @return       An instance of AppManifestBuild
     */
    public static AppManifestBuild getInstance ()
    {
        return instance;
    }
    
    /**
     * @category      Public Class Method
     * @description   Sets this application's name
     * @param appName String representing this app's name
    */
    public void setAppName (String appName)
    {
        this.appName = appName;
    }
    
    /**
     * @category      Public Class Method
     * @description   Sets this application's version
     * @param appName String representing this app's version
    */
    public void setAppVersion (String appVersion)
    {
        this.appVersion = appVersion;
    }
    
    /**
     * @category      Public Class Method
     * @description   Sets this application's product
     * @param appName String representing this app's product
    */
    public void setAppProduct (String appProduct)
    {
        this.appProduct = appProduct;
    }
    
    /**
     * @category      Public Class Method
     * @description   Sets this application's entry point 
     * @param appName String representing this app's entry point
    */
    public void setEntryPoint (String entryPointClassName)
    {
        this.entryPoint = entryPointClassName;
    }
    
    /**
     * @category      Public Class Method
     * @description   Gets this application's name
     * @return        String object representing this app's name
     */
    public String getAppName ()
    {
        return this.appName;
    }
    
    /**
     * @category      Public Class Method
     * @description   Gets this application's entry point
     * @return        String object representing this app's entry point 
     */
    public String getEntryPointClassName ()
    {
        return this.entryPoint;
    }
    
    /**
     * @category      Public Class Method
     * @description   Gets this application's version
     * @return        String object representing this app's version
     */
    public String getAppVersion ()
    {
        return this.appVersion;
    }
    
    /**
     * @category      Public Class Method
     * @description   Gets this application's product
     * @return        String object representing this app's product
     */
    public String getAppProduct ()
    {
        return this.appProduct;
    }
    
    /**
     * @category        Private Class Method
     * @description     Prepares all the data to be inserted in the manifest file
     * @param root      Element object representing the root of the manifest file
     * @param document  Document object representing the document to create 
     *                  the manifest file elements
     */
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
    
    /**
     * @category     Public Class Method
     * @description  Makes the manifest file for this application
     * @throws       ParserConfigurationException
     * @throws       CoreException
     */
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
