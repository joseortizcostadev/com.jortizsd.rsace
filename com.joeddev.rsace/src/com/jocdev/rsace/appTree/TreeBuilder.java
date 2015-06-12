/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeBuilder.java
 * @Date          04/06/2015
 * @Description   This class builds the base application's tree structure
 *                in the user's project package explorer view. 
 *                First of all, this class gets the user's working project 
 *                directory, and creates the root folder ( Rsace ) for this 
 *                application. In Addition, this class creates two sub folders 
 *                which contain the base for rsace's configuration files, and 
 *                resources files respectively. 
 */
package com.jocdev.rsace.appTree;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class TreeBuilder 
{
	// Instance variables and constants
	public static TreeBuilder instance = new TreeBuilder();
	public static final String ACTIVE_ELEMENT_PROJECT = "project";
	public static final String ACTIVE_ELEMENT_FOLDER = "folder";
	public static final String ACTIVE_ELEMENT_FILE = "file";
	public static final String ECLIPSE_PLATFORM = "Eclipse";
	public static final String NETBEANS_PLATFORM = "Netbeans";
	public static final String ROOT_FOLDER = "Rsace";
	public static final String CONFIG_DIR = "RsaceConfigFiles";
	public static final String RESOURCES_DIR = "rscDir";
	private IProject proj; // User's working project
	private AbstractInternalFileManagement configBuilder;
	private AbstractInternalFileManagement resourcesBuilder;
	private IFolder root;
	
	
	/**
	 * @category     Private Constructor
	 * @description  Initializes base components
	*/
	protected TreeBuilder () 
    {
    	this.proj = getWorkingProject();
    	root = makeRoot(ROOT_FOLDER); // Creates root folder
    	
    	
    }
	
	public void buildAppTree (String serverMaker, String email)
	{
		buildConfigFiles(ConfigBuilder.VENDOR, ConfigBuilder.VERSION);
		buildResourcesFiles(serverMaker, email);
	}
	
	
	/**
	 * @category     Singleton method
	 * @description  Creates a single instance of this class
	 * @return       An instance of this class 
	*/
    public static TreeBuilder getRsaceTreeInstance ()
    {
    	return instance;
    }
    
    /**
     * @category       Private Class Method
     * @description    Build all the configuration files with
     *                 a base content
     * @param vendor   String that represents the vendor's file
     * @param version  String that represents the version's file 
    */
    public void buildConfigFiles (String vendor, String version)
    {
    	configBuilder = new ConfigBuilder(root);
    	IFolder configFolder = configBuilder.makeSubFolder(CONFIG_DIR);
    	try
    	{
    	    configBuilder.makeFile(configFolder, ConfigBuilder.MANIFEST_FILE_CONFIG, configBuilder.getHeaderContent(vendor, version, ConfigBuilder.MANIFEST_FILE_CONFIG));
		    configBuilder.makeFile(configFolder, ConfigBuilder.TEAM_FILE_CONFIG, "");
		    configBuilder.makeFile(configFolder, ConfigBuilder.RESOURCES_FILE_CONFIG, configBuilder.getHeaderContent(vendor, version, ConfigBuilder.RESOURCES_FILE_CONFIG));
    	}
    	catch (CoreException e)
    	{
    		System.out.println(e.getMessage());
    	}
	}
    
    /**
     * @category      Public Class Method
     * @description   Builds the resources files for this application
     *                with a base content
     * 
    */
    public void buildResourcesFiles (String serverMaker, String email)
    {
    	resourcesBuilder = new ResourcesBuilder(root);
    	IFolder configFolder = resourcesBuilder.makeSubFolder(RESOURCES_DIR);
    	try
    	{
    	    resourcesBuilder.makeFile(configFolder, ResourcesBuilder.SYNC_FILE, resourcesBuilder.getHeaderContent(serverMaker, email, ResourcesBuilder.SYNC_FILE));
		    resourcesBuilder.makeFile(configFolder, ResourcesBuilder.SYNC_TRAKER, resourcesBuilder.getHeaderContent(serverMaker, email, ResourcesBuilder.SYNC_TRAKER));
    	}
    	catch (CoreException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    }
    
    /**
     * @category     Private Class Method
     * @description  Gets the working project directory where this
     *               application was launched   
     * @return       IProject object representing the working 
     *               root directory
     */
    private IProject getWorkingProject () 
    {
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
    	IWorkspaceRoot root = workspace.getRoot();
    	String proj = null;
    	proj = getActiveElement("eclipse","project");
    	return  root.getProject(proj);
    }
    
    /**
     * @category            Private Method Class
     * @description         Creates a folder in the user's working project
     * @param folderName    String representing the folderName to be created
     * @return              IFolder object representing the folder created
     */
    private IFolder makeRoot (String folderName)
    {
    	try
    	{
    	    IFolder folder = proj.getFolder(folderName);
    	    if (!folder.exists()) 
        	    folder.create(IResource.NONE, true, null);
    	    return folder;
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return null;
    }
    
   /**
     * @category        Public Class Method     
     * @description     Gets the file given as argument   
     * @param filename  String object representing the file's name
     * @return          IFile object representing the file
    */
    public IFile getFile (String dirName, String filename)
    {
    	
    	return root.getFolder(dirName).getFile(filename);
    }
    
    /**
     * @category        Public Class Method
     * @description     Sets the base contents on a file     
     * @param file      IFile object that represents the file 
     * @param contents  String object representing the new file's contents 
    */
    public void setFileContents (IFile file, String contents)
    {
    	try
    	{
    	    if (!file.exists()) 
    	    {
    	    	// Inserts contents using binary approach 
    	        byte[] bytes = contents.getBytes();
    	        InputStream source = new ByteArrayInputStream(bytes);
    	        file.create(source, IResource.NONE, null);
    	    }
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
    
    /**
     * @category                       Private Class Method
     * @description                    Gets the current active user's working element name
     * @param platform                 String object that represents the Platform or IDE 
     *                                 that users are using to develop their project 
     * @param element                  String representing the name of the element's resource 
     *                                 Options available: Project, folder, file                                
     * @return                         String object representing the name of the active project
     * 
    */
    private String getActiveElement (String platform, String element) 
    {
    	try
    	{
    		String runtimePlatform = "";
    	    IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(); 
    	    IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    	    String path = file.getRawLocation().toOSString();
    	    String [] parts = path.split("\\/");
    	    // Checks IDE Platform
    	    if (platform.equalsIgnoreCase(ECLIPSE_PLATFORM))
    	    	runtimePlatform = "runtime-EclipseApplication";
    	    else if (platform.equalsIgnoreCase(NETBEANS_PLATFORM))
    	    	runtimePlatform = "runtime-NetbeansApplication";
    	    // Gets the correct root working project
    	    if (element.equalsIgnoreCase(ACTIVE_ELEMENT_FOLDER))
    	    	return parts[parts.length - 2];
    	    else if (element.equalsIgnoreCase(ACTIVE_ELEMENT_FILE))
    	    	return parts[parts.length-1];
    	    else if (element.equalsIgnoreCase(ACTIVE_ELEMENT_PROJECT))
    	    {
    	        for (int i = 0; i<parts.length; i++)
    	            if (parts[i].equalsIgnoreCase(runtimePlatform) && element.equalsIgnoreCase("project"))
      		    	    return parts[i+1];
    	    }
        }
    	catch (Exception e) 
    	{ 
    		System.out.println(e.getMessage()); 
    	}
    	return "";
    }
    
    /**
     * @category      Public Class Method
     * @description   Gets the file active in the user's project
     * @return        IFile object representing the active file
     */
    public IFile getActiveFile ()
    {
    	IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(); 
	    IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
	    return file;
    }
    
    
}
