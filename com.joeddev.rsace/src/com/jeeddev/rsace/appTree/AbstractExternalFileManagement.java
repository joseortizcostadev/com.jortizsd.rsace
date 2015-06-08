package com.jeeddev.rsace.appTree;

import java.util.List;

public interface AbstractExternalFileManagement 
{
	public List <String> readFileContents ();
	public void setFileContents ();

}
