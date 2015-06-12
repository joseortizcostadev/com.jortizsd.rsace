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
	public void initializeDefaultPreferences() 
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS, true);
	    store.setDefault(PreferenceConstants.P_STRING_AUTHOR_SERVER,
				"your_name_or_username_here");
		store.setDefault(PreferenceConstants.P_STRING_EMAIL_SERVER,
                "youremail@email.com");
		store.setDefault(PreferenceConstants.P_STRING_ID,
                "your_id_here");
		
	}
	
	

}
