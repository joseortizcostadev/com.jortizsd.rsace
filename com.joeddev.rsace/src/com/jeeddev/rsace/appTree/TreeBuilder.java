package com.jeeddev.rsace.appTree;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

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
import org.eclipse.swt.internal.Platform;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class TreeBuilder 
{
	public static TreeBuilder instance = new TreeBuilder();
	private IFolder root;
	private String version = "1.0";
	private String vendor = "com.jocdev.rsace";
	public static final String ROOT_FOLDER = "Rsace";
	public static final String CONFIG_DIR = "Rsace/RsaceConfigFiles";
	public static final String RESOURCES_DIR = "Rsace/rscDir";
	public static final String MANIFEST_FILE_CONFIG = "rsace_manifest.xml";
	public static final String MEMBERS_FILE_CONFIG = "rsace_members.xml";
	public static final String RESOURCES_FILE_CONFIG = "rsace_resources.xml";
	
	private IProject proj;
	
    private TreeBuilder () 
    {
    	this.proj = getWorkingProject();
    	this.root = getFolder(ROOT_FOLDER);
    	buildConfigFiles(vendor,version);
    	
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
    	String proj = null;
    	try {
			proj = getActiveProject("eclipse");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return  root.getProject(proj);
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
    
    public IFile getConfigFile (String filename)
    {
    	
    	return getFolder(CONFIG_DIR).getFile(filename);
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
    
     private String getActiveProject (String platform) throws FileNotFoundException
     {
    	 try
    	 {
    	 IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(); 
    	 IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    	  String path = file.getRawLocation().toOSString();
    	 String [] parts = path.split("\\/");
    	 if (platform.equalsIgnoreCase("eclipse"))
    	 {
    		 for (int i = 0; i<parts.length; i++)
    		 {
    			 if (parts[i].equalsIgnoreCase("runtime-EclipseApplication"))
    			 {
    				 System.out.println(parts[i+1]);
    				 return parts[i+1]; 
    			 }
    				 
    		 }
    	 }
    	 }
    	 catch (Exception e) { System.out.println(e.getMessage()); }
    	 return "";
     }
    
    
    
}
