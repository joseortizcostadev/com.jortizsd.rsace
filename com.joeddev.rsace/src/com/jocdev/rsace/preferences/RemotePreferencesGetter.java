package com.jocdev.rsace.preferences;

public class RemotePreferencesGetter extends RemotePreferencesPage
{
    public RemotePreferencesGetter ()
    {
    	super();
    }
    
    public String getHost ()
    {
    	return super.getPreferenceStore().getString(PreferenceConstants.P_REMOTE_HOST_URL);
    }
    
    public String getHostAlias ()
    {
    	return super.getPreferenceStore().getString(PreferenceConstants.P_REMOTE_HOST_ALIAS);
    }
    
    public int getHostPort ()
    {
    	return Integer.valueOf(super.getPreferenceStore().getString(PreferenceConstants.P_REMOTE_HOST_PORT));
    }
    
    public boolean isEmailOptionEnabled ()
    {
    	return super.getPreferenceStore().getBoolean(PreferenceConstants.P_CHOICE);
    }
    
    public boolean isReconnectingOptionEnabled ()
    {
    	return super.getPreferenceStore().getBoolean(PreferenceConstants.P_CHOICE);
    }
}
