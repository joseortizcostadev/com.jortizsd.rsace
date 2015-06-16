package com.jocdev.rsace.preferences;

import org.eclipse.core.internal.resources.Marker;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.jocdev.rsace.Activator;
import com.jocdev.rsace.team.Team;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class DVTPreferencesPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	IPreferenceStore store;
	
	
	public DVTPreferencesPage() {
		super(GRID);
		
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Developers Teams Preferences");
		store = Activator.getDefault().getPreferenceStore();
		
		
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		//addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, "&Directory preference:", getFieldEditorParent()));
		
       
	   
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_AUTHOR_SERVER, "&Developer session owner:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_EMAIL_SERVER, "&Email:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_ID, "&Id:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS,"&Allow Remote Session Sharing",getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_TEAM_NAME, "&Team:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_TEAM_ID, "&Team's id:", getFieldEditorParent()));
		
	}
	
	public void updateId(String id)
	{
		
		store.setValue(PreferenceConstants.P_STRING_ID, id);
	}
	public void updateAuthor(String author)
	{
		
		store.setValue(PreferenceConstants.P_STRING_AUTHOR_SERVER, author);
	}
	
	public void updateEmail(String email)
	{
		
		store.setValue(PreferenceConstants.P_STRING_EMAIL_SERVER, email);
	}
	
	public void updatePermissions (boolean hasPermissions)
	{
		store.setValue(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS, hasPermissions);
	}
	
	public void updateTeamName (String teamName)
	{
		store.setValue(PreferenceConstants.P_TEAM_NAME, teamName);
	}
	
	public void updateTeamId (String teamId)
	{
		store.setValue(PreferenceConstants.P_TEAM_ID, teamId);
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}