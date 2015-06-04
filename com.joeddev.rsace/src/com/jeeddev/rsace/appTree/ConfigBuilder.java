package com.jeeddev.rsace.appTree;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ConfigBuilder implements TreeFilesInterface
{
	private IFile file;
	private TreeBuilder treeBuilder;
    public ConfigBuilder (String filename)
    {
    	treeBuilder = TreeBuilder.getRsaceTreeInstance();
    	this.file = treeBuilder.getFile(filename); 
    }
	@Override
	public void insertData(String filename, Object[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeData(String filename, Object[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream getFileContents(String filename)  
	{
		try
		{
		   return file.getContents();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
		
	}
    
}
