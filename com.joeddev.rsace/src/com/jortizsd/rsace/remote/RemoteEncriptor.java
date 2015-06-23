package com.jortizsd.rsace.remote;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

public class RemoteEncriptor extends Remote
{
	StandardPBEStringEncryptor encryptor;
	Properties props;
	File file;
    public RemoteEncriptor(String urlFilePath) throws IOException
    {
    	super(urlFilePath);
    	encryptor = new StandardPBEStringEncryptor();
    	props = new EncryptableProperties(encryptor);
    	props.load(getURLStream());
    	
    }
    
    public void setEncriptorPassword(String password)
    {
    	encryptor.setPassword(password); 
    }
    
    public String getDatabaseQualifierProperty (String qualifier)
    {
    	if (qualifier.equalsIgnoreCase(RemoteConstants.REMOTE_MYSQL_QUALIFIER))
    		return props.getProperty("database.driver.mysql");
    	if (qualifier.equalsIgnoreCase(RemoteConstants.REMOTE_ORACLE_QUALIFIER))
    		return props.getProperty("database.driver.mysql");
    	return null;
    }
    
    public String getDatabaseHostProperty ()
    {
    	return props.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_HOST);
    }
    
    public String getDatabaseProperty ()
    {
    	return props.getProperty(RemoteConstants.REMOTE_PROPERTY_DB);
    }
    
    public String getDatabaseUsernameProperty ()
    {
    	return props.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME);
    }
    
    public String getDatabasePasswordProperty ()
    {
    	return props.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD);
    }
    
}
