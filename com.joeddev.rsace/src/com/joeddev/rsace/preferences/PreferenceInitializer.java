package com.joeddev.rsace.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.joeddev.rsace.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		//store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		//store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING_AUTHOR_SERVER,
				System.getProperty("user.home") + " ( You can change author in Rsace's preferences on server section");
		store.setDefault(PreferenceConstants.P_STRING_EMAIL_SERVER,
                "<your email here> ( You can chage email in Rsace's preferences on server section)");
		
	}

}
