package com.jocdev.rsace.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.jocdev.rsace.Activator;

public class RemotePreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage 
{
    IPreferenceStore store;

    public RemotePreferencesPage() 
    {
	    super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
	    setDescription("Remote Sessions Settings");
	    store = Activator.getDefault().getPreferenceStore();
	}

    @Override
    public void init(IWorkbench arg0) 
    {
    	
	}

    @Override
    protected void createFieldEditors() 
    {
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_URL, "&Host Url or Ip Adress: ", getFieldEditorParent()));
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_ALIAS, "&Host Alias : ", getFieldEditorParent()));
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_PORT, "&Port : ", getFieldEditorParent()));
    	addField(new RadioGroupFieldEditor(PreferenceConstants.P_CHOICE, "Connection Rejected Preferences",1,new String[][] { { "&Send a joining invitation for this session by email", "choice1" }, {"&Send automatic request every 5 minutes, with a maximum of three requestes", "choice2" }}, getFieldEditorParent()));
	}
    
    public void updateHostIpOrUrl (String urlOrIp)
    {
    	store.setValue(PreferenceConstants.P_REMOTE_HOST_URL, urlOrIp);
    }
    
    public void updateHostAlias (String alias)
    {
    	store.setValue(PreferenceConstants.P_REMOTE_HOST_ALIAS, alias);
    }
    
    public void updateHostPort (int port)
    {
    	
    	store.setValue(PreferenceConstants.P_REMOTE_HOST_PORT, port);
    }
}
