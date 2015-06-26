package com.jortizsd.rsace.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

public class LoginDialog extends TitleAreaDialog {
	private Text text;
	private Listener listener;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
		syncListener();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Rsace Synchronization Credentials");
		setTitle("Welcome to Rsace");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 116, 14);
		lblNewLabel.setText("Developer ID");
		
		text = new Text(container, SWT.BORDER);
		text.setBounds(10, 30, 116, 19);
		
		Label lblTeams = new Label(container, SWT.NONE);
		lblTeams.setBounds(147, 10, 60, 14);
		lblTeams.setText("Teams");
		
		Combo combo = new Combo(container, SWT.NONE);
		combo.setBounds(146, 30, 261, 22);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) 
	{
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		 Button sync = createButton(parent,IDialogConstants.OK_ID,"Create New", true);
		 createButton(parent, IDialogConstants.OK_ID, "Synchronize",
				true);
		 sync.addListener(SWT.Selection, listener);
		 
	    
	}
	
	public void syncListener ()
	{
	          listener = new Listener() {
		      public void handleEvent(Event event) {
		        
		    	  AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(getShell());
       	          askPreferencesDialog.open();	       
		      }
		    };
	}
    
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(417, 203);
	}

}
