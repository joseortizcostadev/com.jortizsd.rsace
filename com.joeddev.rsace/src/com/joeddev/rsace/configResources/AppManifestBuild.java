package com.joeddev.rsace.configResources;

import com.joeddev.rsace.preferences.DVTPreferencesGetter;

public class AppManifestBuild extends DVTPreferencesGetter
{
    int launch;
    boolean hasSessionPermission;
    String entryPoint;
    String appName;
    String appVersion;
    String appProduct;
    
    
    public AppManifestBuild ()
    {
        
    }
    
    public void setAppName (String appName)
    {
        this.appName = appName;
    }
    public void setAppVersion (String appVersion)
    {
        this.appVersion = appVersion;
    }
    public void setAppProduct (String appProduct)
    {
        this.appProduct = appProduct;
    }
    public void setEntryPoint (String entryPointClassName)
    {
        this.entryPoint = entryPointClassName;
    }
    
    public String getAppName ()
    {
        return this.appName;
    }
    public String getEntryPointClassName ()
    {
        return this.entryPoint;
    }
    
    public String getAppVersion ()
    {
        return this.appVersion;
    }
    
    public String getAppProduct ()
    {
        return this.appProduct;
    }
    
    
    
}
