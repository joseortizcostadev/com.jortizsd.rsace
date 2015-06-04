/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          TreeFilesInterface.java
 * @Date          04/06/2015
 * @Description   Creates signature methods to be implemented
 *                by others classes  
 */
package com.jeeddev.rsace.appTree;
public interface TreeFilesInterface 
{
    public void insertData (String filename, Object [] data);
    public void removeData (String filename, Object [] data);
    public void getFileContents (String filename);
}
