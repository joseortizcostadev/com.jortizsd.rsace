package com.joeddev.rsace.configResources;

import com.joeddev.rsace.preferences.DVTPreferencesGetter;

public class AppManifestBuild extends DVTPreferencesGetter
{
    int launch = 0;
    boolean hasSessionPermision;
    String entryPoint;
   
    public AppManifestBuild ()
    {
        
    }
    
    public void setEntryPoint (String entryPointClassName)
    {
        this.entryPoint = entryPointClassName;
    }
    
    public String getEntryPointClassName ()
    {
        return this.entryPoint;
    }
    
   
    
    
    
}
