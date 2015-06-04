/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeFilesInterface.java
 * @Date          04/06/2015
 * @Description   Creates signature methods to be implemented
 *                by others classes that need access to the files
 *                from the rsace's tree structure 
 */
package com.jeeddev.rsace.appTree;

import java.io.InputStream;

public interface TreeFilesInterface 
{
    public void insertData (String filename, Object [] data);
    public void removeData (String filename, Object [] data);
    public InputStream getFileContents (String filename);
}
