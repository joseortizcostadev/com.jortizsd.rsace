package com.jortizsd.rsace.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.TreeWriter;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.preferences.RsacePreferencesPage;
import com.jortizsd.rsace.views.LogConstants;
import com.jortizsd.rsace.views.RsaceLog;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class AppConfig extends TreeWriter
{
    Remote remote;
    Document doc;
    Properties properties;
    String databaseHost;
    String databaseMode;
    String databaseQualifier;
    String database;
    String databaseUsername;
    String databasePassword;
    String databaseDriver;
    String emailSMTPServer;
    String smtpUsername;
    String smtpPassword;
    private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;
    
    
	public AppConfig (Remote remote) throws IOException 
	{
		this.remote = remote;
		properties = new Properties();
		setAppConfigProperties();
	}
	
	public AppConfig()
	{
		properties = new Properties();
	}
	
	private void  setAppConfigProperties ()
	{
		
		try
		{
	        InputStream fileInput = remote.getInputStream();
			properties.loadFromXML(fileInput);
			fileInput.close();
		    setDatabaseHost(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_HOST));
		    setDatabaseMode(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_MODE));
		    setDatabaseQualifier(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_QUALIFIER));
		    setDatabase(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB));
		    setDatabaseUsername(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_USERNAME));
		    setDatabasePassword(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_PASSWORD));
		    setDatabaseDriver(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_DB_DRIVER));
		    setSMTPServer(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_EMAIL_HOST));
			setSMTPUsername(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_EMAIL_USERNAME));
			setSMTPPassword(properties.getProperty(RemoteConstants.REMOTE_PROPERTY_EMAIL_PASSWORD));
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	public void setDatabaseHost (String databaseHost)
	{
		this.databaseHost = databaseHost;
	}
	
	public void setDatabaseMode (String databaseMode)
	{
		this.databaseMode = databaseMode;
	}
	
	public void setDatabaseQualifier (String databaseQualifier)
	{
		this.databaseQualifier = databaseQualifier;
	}
	
	public void setDatabaseDriver (String databaseDriver)
	{
		this.databaseDriver = databaseDriver;
	}
	
	public void setDatabase (String database)
	{
		this.database = database;
	}
	
	public void setDatabaseUsername (String databaseUsername)
	{
		this.databaseUsername = databaseUsername;
	}
	
	public void setDatabasePassword (String databasePassword)
	{
		this.databasePassword = databasePassword;
	}
	
	public void setSMTPServer (String smtpHost)
	{
		this.emailSMTPServer = smtpHost;
	}
	
	public void setSMTPUsername (String smtpUsername)
	{
		this.smtpUsername = smtpUsername;
	}
	
	public void setSMTPPassword(String smtpPassword)
	{
		this.smtpPassword = smtpPassword;
	}
	public String getDatabaseHost ()
	{
		return this.databaseHost;
	}
	
	public String getDatabaseMode ()
	{
		return this.databaseMode;
	}
	
	public String getDatabaseQualifier ()
	{
		return this.databaseQualifier;
	}
	
	public String getDatabaseDriver ()
	{
		return this.databaseDriver;
	}
	
	public String getDatabase ()
	{
		return this.database;
	}
	
	public String getDatabaseUsername ()
	{
		return this.databaseUsername;
	}
	
	public String getDatabasePassword ()
	{
		return this.databasePassword;
	}
	
	public String getSMTPServer ()
	{
		return this.emailSMTPServer;
	}
	
	public String getSMTPUsername ()
	{
		return this.smtpUsername;
	}
	
	public String getSMTPPassword ()
	{
		return this.smtpPassword;
	}
	
	
	
	static Connection getConnectionFromRemoteConfigFile (URL configFileURL ) throws SQLException, IOException
    {
    	
    	Remote remote = new Remote (configFileURL);
    	AppConfig dbConfig = new AppConfig (remote);
    	
    	try 
    	{
    		MysqlDataSource dataSource = new MysqlDataSource();
    		dataSource.setUser(dbConfig.getDatabaseUsername()); 
    		dataSource.setPassword(dbConfig.getDatabasePassword()); 
    		dataSource.setDatabaseName(dbConfig.getDatabase());
    		dataSource.setServerName(dbConfig.getDatabaseHost());
    	    return dataSource.getConnection();
    	}
    	catch (SQLException ex) 
    	{
    	    RsaceLog.writeLog("Rsace Internal Server Error", "An internal error has ocurred. " + ex.getMessage(), LogConstants.LOG_ERROR_CONTEXT);
    	}
        return null;
        
    }
	
	public static void checkLogStatus () throws PartInitException
	{
		RsacePreferencesPage generalSettings = new RsacePreferencesPage();
    	if (generalSettings.isLogActive())
    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(RsaceLog.ID);
    	
	}
	
	public void sendEmail (Developer developerRecipient, String subject, String message) 
	{
		try
		{
		  
		   mailServerProperties = System.getProperties();
		   mailServerProperties.put("mail.smtp.port", "587");
		   mailServerProperties.put("mail.smtp.auth", "true");
		   mailServerProperties.put("mail.smtp.starttls.enable", "true");
		   getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		   generateMailMessage = new MimeMessage(getMailSession);
		   generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(developerRecipient.getEmail()));
		   // Uncomment to send emails to the other members of the group
		   // generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("rsace.remote.notification@gmail.com"));
		   generateMailMessage.setSubject(subject);
		   generateMailMessage.setContent(message, "text/html");
		   Transport transport = getMailSession.getTransport("smtp");
		   System.out.println("Server: " + getSMTPServer() + " Username: " + getSMTPUsername() + " Password: " + getSMTPPassword());
		   transport.connect(getSMTPServer(), getSMTPUsername(), getSMTPPassword());
		   transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		   transport.close();
		   RsaceLog.writeLog("Rsace Information", "Your request has been successfully sent to " + developerRecipient.getEmail(), LogConstants.LOG_INFO_CONTEXT);
		   
		}
		catch (Exception e)
		{
			RsaceLog.writeLog("Rsace Email Error", "Couldn't connect to smtp server. " + e.getMessage(), LogConstants.LOG_ERROR_CONTEXT);
		}
	}
	
	
    
}
