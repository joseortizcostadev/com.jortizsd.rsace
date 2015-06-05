package com.jeeddev.rsace.appTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class ConfigBuilder implements InternalFileManagement
{
	private IFile file;
	private TreeBuilder treeBuilder;
    public ConfigBuilder (String filename)
    {
    	treeBuilder = TreeBuilder.getRsaceTreeInstance();
    	this.file = treeBuilder.getFile(filename); 
    }
	
	
	public String getContents(InputStream stream) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(stream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer buffer = new StringBuffer(1000);
		int c;
		while ((c = bufferedReader.read()) != -1) {
			buffer.append((char) c);
		}
		return buffer.toString();
	}


	@Override
	public void insertData(Object[] data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeData(Object[] data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public InputStream getFileContents() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
