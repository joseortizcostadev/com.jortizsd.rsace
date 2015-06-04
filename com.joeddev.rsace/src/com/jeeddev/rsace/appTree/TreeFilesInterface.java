/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeFilesInterface.java
 * @Date          04/06/2015
 * @Description   Creates signature methods to be implemented
 *                by others classes that need access to the files
 *                from the rsace's tree structure 
 *                Note: The classes implementing this interface
 *                need to have a IFile as a parameter in
 *                their constructor 
 */
package com.jeeddev.rsace.appTree;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;

public interface TreeFilesInterface 
{
    public void insertData (Object [] data);
    public void removeData (Object [] data);
    public InputStream getFileContents () throws CoreException;
}
