/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          RsacePreferencesPage.java
 * @Date          04/06/2015
 * @Description   This class creates the general preferences page for this application
 */
package com.jortizsd.rsace.preferences;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.jortizsd.rsace.Activator;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.wb.swt.DoubleFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;

public class RsacePreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage 
{

    public RsacePreferencesPage ()
    {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("Rsace General Settings");
    }

    @Override
    public void init(IWorkbench arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void createFieldEditors() {
    	
    	addField(new BooleanFieldEditor(PreferenceConstants.P_LOG_STATUS,"&Show relevant information in log",getFieldEditorParent()));
    }
    
    // Getters
    
    public boolean isLogActive ()
    {
    	return super.getPreferenceStore().getBoolean(PreferenceConstants.P_LOG_STATUS);
    }

}
