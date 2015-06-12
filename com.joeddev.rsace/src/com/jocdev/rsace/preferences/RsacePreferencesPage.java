/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          RsacePreferencesPage.java
 * @Date          04/06/2015
 * @Description   This class creates the general preferences page for this application
 */
package com.jocdev.rsace.preferences;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import com.jocdev.rsace.Activator;

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
        // TODO Auto-generated method stub
        
    }

}
