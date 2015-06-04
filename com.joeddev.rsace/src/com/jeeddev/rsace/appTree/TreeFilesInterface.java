package com.jeeddev.rsace.appTree;

public interface TreeFilesInterface 
{
    public void insertData (String filename, Object [] data);
    public void removeData (String filename, Object [] data);
    public void getFileContents (String filename);
}
