/**
 * @author        Jose Ortiz Costa
 * @application   com.jocdev.rsace
 * @File          UsrResourcesBuilder.java
 * @Date          04/06/2015
 * @Description   This class extends TreeBuilder class, and 
 *                provides methods build and handle the necessary resources
 *                for this application 
 */
package com.jocdev.rsace.appTree;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class UsrResourcesBuilder extends TreeBuilder
{
	// final instance constants for the resources files
    public static final String SYNC_FILE = "_sync.java";
    public static final String REMOTE_MODE = "remote";
    public static final String LOCAL_MODE = "local";
    
    /**
     * @category constructor
     */
	public UsrResourcesBuilder() 
	{
		super();
		
	}
    
	/**
	 * @category    Public Class Method
	 * @description Synchronizes a file with another        
	 * @param mode  String object representing the sync modes
	 *              there are two modes available. Sync in local
	 *              mode, and in remote mode. You get this modes
	 *              calling the static modes from this class 
	 * @throws      CoreException
	 * @throws      IOException
	 */
	public void syncFile(String mode) throws CoreException, IOException
	{
		IFile usrFile = getActiveFile();
		IFile syncFile = getFile(TreeBuilder.RESOURCES_DIR, SYNC_FILE);
		InputStream fileStream = usrFile.getContents();
        if (mode.equalsIgnoreCase(LOCAL_MODE))
        {
		    syncFile.appendContents(fileStream, IResource.NONE, null);
        }
        else
        {
        	// Sync in remote mode implementation
        }
	}
	
	public void setHeaderInSyncFile ()
	{
		
	}

	

}
