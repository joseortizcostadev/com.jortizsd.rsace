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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.jocdev.rsace.team.Developer;

public class UsrResourcesBuilder extends TreeBuilder
{
	// final instance constants for the resources files
    public static final String SYNC_FILE = "_sync.java";
    public static final String REMOTE_MODE = "remote";
    public static final String LOCAL_MODE = "local";
    public static final String FILE_MODE_LOCAL = "Synchronized Local Mode";
    public static final String FILE_MODE_REMOTE = "Remote Session Mode";
    
    
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
	public void syncFile(String mode, InputStream headerInputStream) throws CoreException, IOException
	{
		IFile usrFile = getActiveFile(); // user's file to be synchronized.
		IFile syncFile = getFile(TreeBuilder.RESOURCES_DIR, SYNC_FILE); // new file
		InputStream fileStream = usrFile.getContents();
        if (mode.equalsIgnoreCase(LOCAL_MODE))
        {
        	SequenceInputStream sequence = new SequenceInputStream(headerInputStream, fileStream);
		    syncFile.setContents(sequence, IResource.NONE, null);
        }
        else
        {
        	// Sync in remote mode implementation
        }
	}
	
	public InputStream getHeaderStreamForSyncFile (String fileMode, String devSessionOwner, String fileName, String email,
			                         boolean isSessionOpened, String teamName, List <Developer> teamDevelopers)
	{
	    int counter = 0;
		String isSessionOpenedToString = "Session Closed";
		if (isSessionOpened)
			isSessionOpenedToString = "Session Open";
		String header = "/**\n" + 
		        "// @file_mode:             " + fileMode + "\n" +  
		        "// @DeveloperSessionOwner: " + devSessionOwner + "\n" + 
		        "// @File:                  " + fileName + "\n" + 
		        "// @Email:                 " + email + "\n" + 
		        "// @Session:               " + isSessionOpenedToString + "\n" +
		        "// @Team:                  " + teamName + "\n";
		if (teamDevelopers != null)
		{
	        for (Developer dev : teamDevelopers)
	        {
	    	    if (counter == 0)
	    		    header += "// @Developers:              --" + dev.getName() + "\n";
	    	    else
	    		    header += "                             --" + dev.getName() + "\n"; 
	    	    counter++;
	        }
		}
		header += "// @Note:                  This header won't be saved in your original file\n" +   
		          "*/\n";
		
		return getHeaderStream(header);
		
	}
	
	private InputStream getHeaderStream (String header)
	{
		 byte[] bytes = header.getBytes();
		 InputStream source = new ByteArrayInputStream(bytes);
		 return source;
	}

	

}
