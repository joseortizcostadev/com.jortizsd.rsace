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
package com.jeeddev.rsace.appTree;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class TreeBuilder 
{
	// Instance variables and constants
	public static TreeBuilder instance = new TreeBuilder();
	private String version = "1.0";
	private String vendor = "com.jocdev.rsace";
	public static final String ROOT_FOLDER = "Rsace";
	public static final String CONFIG_DIR = "Rsace/RsaceConfigFiles";
	public static final String RESOURCES_DIR = "Rsace/rscDir";
	public static final String MANIFEST_FILE_CONFIG = "rsace_manifest.xml";
	public static final String MEMBERS_FILE_CONFIG = "rsace_members.xml";
	public static final String RESOURCES_FILE_CONFIG = "rsace_resources.xml";
	private IProject proj; // User's working project
	
	/**
	 * @category     Private Constructor
	 * @description  Initializes base components
	*/
	private TreeBuilder () 
    {
    	this.proj = getWorkingProject();
    	getFolder(ROOT_FOLDER); // Creates root folder
    	buildConfigFiles(vendor,version);
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
    private void buildConfigFiles (String vendor, String version)
    {
    	IFolder configFolder = getFolder(CONFIG_DIR);
    	String headerConfigFiles = "<?xml version=\"" + version + "\" encoding=\"UTF-8\"?>\n" + 
    	                           "<?rsace version=\"" + version + "\"?>";
    	// Creates the configuration files.
    	getFile(configFolder, MANIFEST_FILE_CONFIG, headerConfigFiles);
    	getFile(configFolder, MEMBERS_FILE_CONFIG, headerConfigFiles);
    	getFile(configFolder, RESOURCES_FILE_CONFIG, headerConfigFiles);
    }
    
    /**
     * @category      Public Class Method
     * @description   Builds the resources files for this application
     *                with a base content
     * 
    */
    public void buildResourcesFiles ()
    {
    	IFolder configFolder = getFolder(RESOURCES_DIR);
    	String headerJavaTmp = "/**\n" + 
    	                       "    File: usrFile_syn.java \n" + 
    	                       "    Author: com.joeddev.rsace \n" + 
    			               "    Date: 00/00/0000\n" +
    	                       "*/";
    	String headerRsaceFile = "##### Keeps Stages Changes #######";
    	// Create resources files
    	getFile(configFolder, "usrFile_syn.java", headerJavaTmp);
    	getFile(configFolder, "usrFile.rsace", headerRsaceFile);
    	
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
    	proj = getActiveProject("eclipse");
    	return  root.getProject(proj);
    }
    
    /**
     * @category            Private Method Class
     * @description         Creates a folder in the user's working project
     * @param folderName    String representing the folderName to be created
     * @return              IFolder object representing the folder created
     */
    private IFolder getFolder (String folderName)
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
     * @category        Private Class Method
     * @description     Creates a file in the user's working project     
     * @param folder    IFolder object that represents the file's folder
     * @param fileName  String object that represents the name of the file
     * @param content   String object that represents the header content of the file
     * @return          IFile object that represents the file created
     */
    private  IFile getFile (IFolder folder, String fileName, String header)
    {
    	IFile file = folder.getFile(fileName);
    	setFileContents(file, header); // Creates headers
    	return file;
    }
    
    /**
     * @category        Public Class Method     
     * @description     Gets the file given as argument   
     * @param filename  String object representing the file's name
     * @return          IFile object representing the file
    */
    public IFile getFile (String filename)
    {
    	return getFolder(CONFIG_DIR).getFile(filename);
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
     * @description                    Gets the current active user's working project name
     * @param platform                 String object that represents the Platform or IDE 
     *                                 that users are using to develop their project 
     * @return                         String object representing the name of the active project
     * 
    */
    private String getActiveProject (String platform) 
    {
    	try
    	{
    		String runtimePlatform = "";
    	    IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(); 
    	    IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    	    String path = file.getRawLocation().toOSString();
    	    String [] parts = path.split("\\/");
    	    // Checks IDE Platform
    	    if (platform.equalsIgnoreCase("eclipse"))
    	    	runtimePlatform = "runtime-EclipseApplication";
    	    else if (platform.equalsIgnoreCase("netbeans"))
    	    	runtimePlatform = "runtime-NetbeansApplication";
    	    // Gets the correct root working project
    	    for (int i = 0; i<parts.length; i++)
      		    if (parts[i].equalsIgnoreCase(runtimePlatform))
      			      return parts[i+1];
    	}
    	catch (Exception e) 
    	{ 
    		System.out.println(e.getMessage()); 
    	}
    	return "";
    }
}
