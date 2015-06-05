/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          InternalFileManagement.java
 * @Date          04/06/2015
 * @Description   Creates signature methods to be implemented
 *                by others classes that need access to the files
 *                from the rsace's tree structure 
 *                Note: The classes implementing this interface
 *                need to have a IFile as a parameter in
 *                their constructor 
 */
package com.jeeddev.rsace.appTree;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

public interface InternalFileManagement 
{
	public void CreateFile (IFolder folder, IFile file);
	public void deleteFile (IFolder folder, IFile file);
    public void setContents (IFile file,String contents);
    public void appendContents (String contents);
    public List <String> getFileContents ();
}
