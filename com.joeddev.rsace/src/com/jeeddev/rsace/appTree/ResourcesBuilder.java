/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          ResourcesBuilder.java
 * @Date          04/06/2015
 * @Description   This class extends the abstract class
 *                InternalFileManagement, and implements its
 *                signature methods.
 *                This class handles the resources
 *                files of this application.
 */
package com.jeeddev.rsace.appTree;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

public class ResourcesBuilder extends AbstractInternalFileManagement
{
	
    public static final String SYNC_FILE = "_sync.java";
    public static final String SYNC_TRAKER = "sync_rsc.rsace";
    
    /**
	 * @category          Class Constructor
	 * @description       sets the root folder in the superclass
	 * @param rootFolder  IFolder object representing the root folder of the application
	 */
    public ResourcesBuilder (IFolder rootFolder)
    {
    	super(rootFolder);
    }
    
    /**
	 * @see AbstractInternalFileManagement class
	 */
	@Override
	void appendContents(IFile file, String contents) {
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
	String getHeaderContent(String author, String email, String context) {
		String header = "/* \n" + 
				        "  // Author: " + author + "\n" + 
				        "  // File:   " + context + "\n" + 
				        "  // Email:  " + email + "\n" + 
				        "*/\n";
	    return header;
	}

    @Override
    IFile getFile(String filename) {
        // TODO Auto-generated method stub
        return null;
    }
	
	
	

}
