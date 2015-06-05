package com.jeeddev.rsace.appTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

public class ConfigBuilder implements InternalFileManagement
{
    private IFolder rootConfigFolder;
	public ConfigBuilder (IFolder configFolder)
	{
		this.rootConfigFolder = configFolder;
	}
	@Override
	public void CreateFile(String filename) 
	{
		
		 rootConfigFolder.getFile(filename);
		
    	
    }

	@Override
	public void deleteFile(IFile file) throws CoreException 
	{
		file.delete(true, null);
		
	}

	@Override
	public void setContents(IFile file, String contents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appendContents(IFile file, String contents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getFileContents(IFile file) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IFolder getRootFolder() {
		// TODO Auto-generated method stub
		return null;
	}
	

    


	
    
}
