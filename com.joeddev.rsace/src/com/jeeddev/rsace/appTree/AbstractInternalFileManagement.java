/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          InternalFileManagement.java
 * @Date          04/06/2015
 * @Description   This abstract class implements methods to
 *                handle Resources in the app's tree project
 *                Also, this class contains abstract signature
 *                methods to be implemented by other classes
 *                that need to handle resources located inside
 *                of the application's tree root
 */
package com.jeeddev.rsace.appTree;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

abstract class AbstractInternalFileManagement 
{
	public IFolder root;
	public static final String VENDOR = "com.joeddev.rsace";
    public static final String VERSION = "1.0";
    /**
     * @category     Abstract constructor cannot be instantiated directly
     * @description  Sets the root folder of the application
     * @param        rootAppFolder IFolder object representing the application's root folder
     */
	public AbstractInternalFileManagement (IFolder rootAppFolder)
	{
		root = rootAppFolder;
	}
	
	/**
	 * @category     Public Class Method   
	 * @description  Gets the root folder of the application
	 * @return       An IFolder object
	 */
	public  IFolder getRootFolder ()
	{
		return root;
	}
	
	/**
	 * @category             Public Class Method
	 * @description          Makes a sub folder into the root folder of the application
	 * @param subfolderName  String object representing the new subfolder's name
	 * @return               An IFolder object representing the new subfolder created
	 */
	public IFolder makeSubFolder(String subfolderName) 
	{
		try
    	{
    	    IFolder folder = root.getFolder(subfolderName);
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
	 * @category               Public Class Method
	 * @description            Makes a new resources file in the application tree
	 * @param folder           IFolder object representing the folder that contains 
	 * @param filename         String object representing the name of the new file
	 * @param header           String object representing the header of the new file
	 * @return                 An IFile object representing the new file resource created
	 * @throws CoreException   An exception about the file
	 */
	public IFile makeFile (IFolder folder, String filename, String header) throws CoreException
	{
		IFile file = folder.getFile(filename);
		if (!file.exists()) {
		    byte[] bytes = header.getBytes();
		    InputStream source = new ByteArrayInputStream(bytes);
		    file.create(source, IResource.NONE, null);
		}
		return file;
		
	}
	
	/**
	 * @category                Public Class Method
	 * @description             Deletes the application's tree
	 * 
	 */
	public void deleteRoot () 
	{
		// Empty method.
		// No configuration files nor resources files should be deleted
	}
	
	/**
	 * 
	 * @param file
	 * @param contents
	 */
	abstract void appendContents (IFile file, String contents);
	
	/**
	 * @category       Signature Class Method
	 * @param vendor   String representing the file's vendor
	 * @param version  String representing the file's version
	 * @param context  String representing the context of the header 
	 *                 e.g: The name of the file
	 * @return         A String object representing the header content of the file
	 * 
	 */
    abstract String getHeaderContent(String vendor, String version, String context);
    
    /**
     * @category      Signature Class Method
     * @param file    IFile object representing the file containing the contents 
     * @return        List object representing a list which contains line by line the 
     *                contents of the file
     */
    abstract List <String> getFileContents (IFile file);
    abstract IFile getFile (String filename);
}
