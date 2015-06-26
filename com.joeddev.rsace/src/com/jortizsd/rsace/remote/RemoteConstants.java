package com.jortizsd.rsace.remote;

public class RemoteConstants 
{
	public static final String REMOTE_SERVER = "http://www.jortizsd.com";
	public static final String REMOTE_APP_CONFIG_FILE_URL = "http://www.jortizsd.com/projects_config/com.jortizsd.rsace/config.xml";
	public static final String REMOTE_APP_CONFIG_FILE_LOCAL = "rsace_config.xml";
	public static final String REMOTE_URL_DATABASE_HOST = "jortizsdcom.ipagemysql.com";
	public static final String REMOTE_DATABASE = "rsace_db";
	public static final String REMOTE_TABLE = "developer";
	public static final String REMOTE_DB_FIELD_ID = "id";
	public static final String REMOTE_DB_FIELD_DEVNAME = "dev_name";
	public static final String REMOTE_DB_FIELD_DEVID = "dev_id";
	public static final String REMOTE_DB_FIELD_DEVEMAIL = "dev_email";
	public static final String REMOTE_DB_FIELD_DEVTEAMNAME = "team_name";
	public static final String REMOTE_DB_FIELD_DEVTEAMID = "team_id";
	public static final String REMOTE_DB_FIELD_ISTEAMOWNER = "isTeamOwner";
    public static final String REMOTE_PROPERTY_DB_QUALIFIER = "database.qualifier";
    public static final String REMOTE_PROPERTY_DB_HOST = "database.host";
	public static final String REMOTE_PROPERTY_DB = "database.name";
	public static final String REMOTE_PROPERTY_DB_USERNAME = "database.username";
	public static final String REMOTE_PROPERTY_DB_PASSWORD = "database.password";
	public static final String REMOTE_PROPERTY_DB_DRIVER = "database.driver";
	public static final String REMOTE_PROPERTY_DB_MODE = "database.mode";
	public static final String REMOTE_DB_INSERT_QUERY = "INSERT INTO developers (team_id, dev_id, team_name, dev_name, dev_email, isTeamOwner)"
        + " VALUES (?, ?, ?, ?, ?, ?)";
	public static final String REMOTE_DB_UPDATE_QUERY = "UPDATE developers set team_name = ?, dev_name = ?, dev_email = ?, isTeamOwner =? WHERE dev_id = ?";
	public static final String REMOTE_DB_SELECT_QUERY = "SELECT * FROM developers";
	
    
    
}
