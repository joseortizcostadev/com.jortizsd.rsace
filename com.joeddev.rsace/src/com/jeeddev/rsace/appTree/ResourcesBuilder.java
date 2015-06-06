package com.jeeddev.rsace.appTree;

import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

public class ResourcesBuilder extends InternalFileManagement
{
    public static final String SYNC_FILE = "_sync.java";
    public static final String SYNC_TRAKER = "sync_rsc.rsace";
    
    
    public ResourcesBuilder (IFolder rootFolder)
    {
    	super(rootFolder);
    }

	@Override
	void appendContents(IFile file, String contents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	List<String> getFileContents(IFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getHeaderContent(String author, String email, String context) {
		String header = "/* \n" + 
				        "  // Author: " + author + "\n" + 
				        "  // File:   " + context + "\n" + 
				        "  // Email:  " + email + "\n" + 
				        "*/\n";
	    return header;
	}
	
	
	

}
