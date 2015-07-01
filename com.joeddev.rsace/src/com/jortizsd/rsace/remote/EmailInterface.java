package com.jortizsd.rsace.remote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface EmailInterface 
{
    default void sendEmail (Developer developerRecipient, String subject, String message) throws MalformedURLException, IOException
    {
    	
    	Remote remote = new Remote (new URL(RemoteConstants.REMOTE_APP_CONFIG_FILE_URL));
    	AppConfig appConfig = new AppConfig(remote);
    	appConfig.sendEmail(developerRecipient, subject, message);
    }
    
    public String getSubject();
    public String getBodyMessage();
}
