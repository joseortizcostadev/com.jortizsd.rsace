package com.joeddev.rsace.preferences;

import org.eclipse.core.internal.resources.Marker;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.joeddev.rsace.Activator;

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
		setDescription("Developer Team Settings");
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
		
       
	    // addField(new RadioGroupFieldEditor(PreferenceConstants.P_CHOICE, "An example of a multiple-choice preference",1,new String[][] { { "&Choice 1", "choice1" }, {"C&hoice 2", "choice2" }}, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_AUTHOR_SERVER, "&Developer session owner:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_EMAIL_SERVER, "&Email:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_STRING_ID, "&Id:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS,"&Allow Remote Session Sharing",getFieldEditorParent()));
		
        
		
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
	
	private void setSectionHeader (String header)
	{
	    setDescription(header);
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}