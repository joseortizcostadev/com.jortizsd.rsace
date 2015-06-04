package com.jeeddev.rsace.appTree;
// init javadoc
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class TreeBuilder 
{
	public static TreeBuilder instance = new TreeBuilder();
	private IFolder root;
	private static final String VERSION = "1.0";
	private static final String VENDOR = "com.jocdev.rsace";
	private static final String CONFIG_DIR = "Rsace/RsaceConfigFiles";
	private static final String RESOURCES_DIR = "Rsace/rscDir";
	private static final String MANIFEST_FILE_CONFIG = "rsace_manifest.xml";
	private static final String MEMBERS_FILE_CONFIG = "rsace_members.xml";
	private static final String RESOURCES_FILE_CONFIG = "rsace_resources.xml";
	
	private IProject proj;
	
    private TreeBuilder ()
    {
    	this.proj = getWorkingProject();
    	this.root = getFolder("Rsace");
    	buildConfigFiles(VENDOR,VERSION);
    }
    
    public static TreeBuilder getRsaceTreeInstance ()
    {
    	
    	return instance;
    }
    
    private void buildConfigFiles (String vendor, String version)
    {
    	IFolder configFolder = getFolder(CONFIG_DIR);
    	String headerConfigFiles = "<?xml version=\"" + version + "\" encoding=\"UTF-8\"?>\n" + 
    	                           "<?rsace version=\"" + version + "\"?>";
    	getFile(configFolder, MANIFEST_FILE_CONFIG, headerConfigFiles);
    	getFile(configFolder, MEMBERS_FILE_CONFIG, headerConfigFiles);
    	getFile(configFolder, RESOURCES_FILE_CONFIG, headerConfigFiles);
    }
    
    public void buildResourcesDir ()
    {
    	IFolder configFolder = getFolder(RESOURCES_DIR);
    	String headerJavaTmp = "/**\n" + 
    	                       "    File: usrFile_syn.java \n" + 
    	                       "    Author: com.joeddev.rsace \n" + 
    			               "    Date: 00/00/0000\n" +
    	                       "*/";
    	String headerRsaceFile = "##### Keeps Stages Changes #######";
    	 
    	
    	getFile(configFolder, "usrFile_syn.java", headerJavaTmp);
    	getFile(configFolder, "usrFile.rsace", headerRsaceFile);
    	
    }
    private IProject getWorkingProject ()
    {
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
    	IWorkspaceRoot root = workspace.getRoot();
    	return  root.getProject("Rtest");
    }
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
    
    private  IFile getFile (IFolder folder, String fileName, String content)
    {
    	IFile file = folder.getFile(fileName);
    	setFileContents(file, content);
    	return file;
    }  
    
    
    
    public void setFileContents (IFile file, String contents)
    {
    	try
    	{
    	    if (!file.exists()) 
    	    {
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
    
    public IResource extractSelection(ISelection sel) {
        if (!(sel instanceof IStructuredSelection))
           return null;
        IStructuredSelection ss = (IStructuredSelection) sel;
        Object element = ss.getFirstElement();
        if (element instanceof IResource)
           return (IResource) element;
        if (!(element instanceof IAdaptable))
           return null;
        IAdaptable adaptable = (IAdaptable)element;
        Object adapter = adaptable.getAdapter(IResource.class);
        System.out.println(adapter.toString());
        return (IResource) adapter;
     }
    
    
    
}
