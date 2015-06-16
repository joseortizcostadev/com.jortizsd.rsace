package com.jocdev.rsace.preferences;


import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;

public class TeamsConboEditor extends ComboFieldEditor
{

	public TeamsConboEditor(String name, String labelText,
			                String[][] entryNamesAndValues, Composite parent) 
	{
		super(name, labelText, entryNamesAndValues, parent);
		// TODO Auto-generated constructor stub
	}
	
	public String getSelectedValue()
    {
        doStore();
        return getPreferenceStore().getString(getPreferenceName());
    }

    public void setSelectedValue(String newValue)
    {
        getPreferenceStore().setValue(getPreferenceName(), newValue);
        doLoad();
        
    }
    
    
	
	
	
	

}
