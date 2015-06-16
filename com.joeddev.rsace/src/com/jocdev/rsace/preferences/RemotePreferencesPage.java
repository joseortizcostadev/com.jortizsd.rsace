package com.jocdev.rsace.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
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
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_URL, "&Host Url or Ip Adress: ", getFieldEditorParent()));
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_ALIAS, "&Host Alias : ", getFieldEditorParent()));
    	addField(new StringFieldEditor(PreferenceConstants.P_REMOTE_HOST_PORT, "&Port : ", getFieldEditorParent()));
	}

    @Override
    protected void createFieldEditors() 
    {
	    // TODO Auto-generated method stub
	}
}
