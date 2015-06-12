package com.jeeddev.rsace.appTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class UsrResourcesBuilder extends TreeBuilder
{
    public static final String SYNC_FILE = "_sync.java";
    public static final String REMOTE_MODE = "remote";
    public static final String LOCAL_MODE = "local";
	public UsrResourcesBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

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
