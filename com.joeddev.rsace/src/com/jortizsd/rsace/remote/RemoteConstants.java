package com.jortizsd.rsace.remote;

public class RemoteConstants 
{
	public static final String REMOTE_SERVER_DEFAULT = "http://www.jortizsd.com//projects_config/com.jortizsd.rsace/rsace_config.xml";
	public static final String REMOTE_DATABASE = "rsace_db";
	public static final String REMOTE_TABLE = "developer";
	public static final String REMOTE_FIELD_ID = "id";
	public static final String REMOTE_FIELD_DEVNAME = "devName";
	public static final String REMOTE_FIELD_DEVID = "devId";
	public static final String REMOTE_FIELD_DEVEMAIL = "devEmail";
	public static final String REMOTE_FIELD_DEVTEAMNAME = "devTeamName";
	public static final String REMOTE_FIELD_DEVTEAMID = "devTeamId";
	public static final String REMOTE_FIELD_ISTEAMOWNER = "isTeamOwner";
	
	/**
	 * Note for Edgardo: 
	 * We need to find a way to encrypt the database's password
	 * I was thinking that maybe we should use a XML file in the server to store the database
	 * information and pull it from there when it is needed, or maybe a property file. But whatever it is, it needs to be on the server.
	 * What do you think about it?
	 */
	
	
}
