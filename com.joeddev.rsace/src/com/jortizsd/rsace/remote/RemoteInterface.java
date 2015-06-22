package com.jortizsd.rsace.remote;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;

public interface RemoteInterface 
{
    
    public boolean isServerUp() throws IOException;
    public boolean existRemoteConfigFile();
    public Document getRemoteDocument();
    public void createRemoteTeam(Team team);
    public void updateRemoteConfigFile(String tag, String value);
    public void deleteRemoteConfigFileValue(String tag, String value);
    
}
