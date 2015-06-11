package com.joeddev.rsace.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.joeddev.rsace.Activator;

public class RsacePreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public RsacePreferencesPage (){
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
