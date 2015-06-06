package com.jeeddev.rsace.appTree;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
public class ConfigBuilder extends InternalFileManagement
{
	public static final String MANIFEST_FILE_CONFIG = "rsace_manifest.xml";
	public static final String MEMBERS_FILE_CONFIG = "rsace_members.xml";
	public static final String RESOURCES_FILE_CONFIG = "rsace_resources.xml";
	
    public ConfigBuilder (IFolder rootFolder)
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
	String getHeaderContent(String vendor, String version, String context) {
		String header = "<?xml version=" + version + " encoding=\"UTF-8\"?>\n" + 
	                    "<!-- \n" +
				        "  Author: " + vendor + "\n" + 
	                    "  File:   " + context + "\n" + 
	                    "-->\n";
				        
		return header;
	}


	
}
