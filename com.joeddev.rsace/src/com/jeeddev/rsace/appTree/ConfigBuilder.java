/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          ConfigBuilder.java
 * @Date          04/06/2015
 * @Description   This class extends the abstract class
 *                InternalFileManagement, and implements its
 *                signature methods.
 *                This class handles the configuration
 *                files of this application
 */
package com.jeeddev.rsace.appTree;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

public class ConfigBuilder extends AbstractInternalFileManagement
{
	// public static instance variables
	public static final String MANIFEST_FILE_CONFIG = "rsace_manifest.xml";
	public static final String TEAM_FILE_CONFIG = "rsace_team.xml";
    public static final String RESOURCES_FILE_CONFIG = "rsace_resources.xml";
	
	
	/**
	 * @category          Class Constructor
	 * @description       sets the root folder in the superclass
	 * @param rootFolder  IFolder object representing the root folder of the application
	 */
    public ConfigBuilder (IFolder rootFolder)
	{
		super(rootFolder);
	}

	/**
	 * @see AbstractInternalFileManagement class
	 */
	@Override
	void appendContents(IFile file, String contents) 
	{
		// TODO Auto-generated method stub
		
	}
    /**
     * @see AbstractInternalFileManagement class
     */
	@Override
	List<String> getFileContents(IFile file) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @see AbstractInternalFileManagement class
     */
	@Override
	String getHeaderContent(String vendor, String version, String context) {
		String header = "<?xml version=" + version + " encoding=\"UTF-8\"?>\n" + 
	                    "<!-- \n" +
				        "  Author: " + vendor + "\n" + 
	                    "  File:   " + context + "\n" + 
	                    "-->\n";
				        
		return header;
	}

    @Override
    IFile getFile(String filename) {
        // TODO Auto-generated method stub
        return null;
    }


	
}
