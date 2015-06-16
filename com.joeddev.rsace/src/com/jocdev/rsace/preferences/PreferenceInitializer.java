package com.jocdev.rsace.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.jocdev.rsace.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	IPreferenceStore store;
	public void initializeDefaultPreferences() 
	{
		store = Activator.getDefault().getPreferenceStore();
		initRsaceGeneralSettings();
		initDeveloperTeamSettings();
		initRemoteSessionSettings();
    }
	
	private void initRsaceGeneralSettings ()
	{
		
	}
	
    private void initDeveloperTeamSettings ()
    {
    	store.setDefault(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS, true);
	    store.setDefault(PreferenceConstants.P_STRING_AUTHOR_SERVER,
				"your_name_or_username_here");
		store.setDefault(PreferenceConstants.P_STRING_EMAIL_SERVER,
                "youremail@email.com");
		store.setDefault(PreferenceConstants.P_STRING_ID,
                "your_id_here");
		store.setDefault(PreferenceConstants.P_TEAM_NAME, "No team found");
		store.setDefault(PreferenceConstants.P_TEAM_ID, "No id found");
    }
    
    private void initRemoteSessionSettings ()
    {
    	store.setDefault(PreferenceConstants.P_REMOTE_HOST_URL, "http://localhost");
		store.setDefault(PreferenceConstants.P_REMOTE_HOST_ALIAS, "Sets your alias for your host");
		store.setDefault(PreferenceConstants.P_REMOTE_HOST_PORT, "11109");
		
    }
	
	
	
	

}
