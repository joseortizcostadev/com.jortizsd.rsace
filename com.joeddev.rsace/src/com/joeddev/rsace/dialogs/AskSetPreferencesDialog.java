package com.joeddev.rsace.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

import com.joeddev.rsace.preferences.DVTPreferencesPage;

public class AskSetPreferencesDialog extends TitleAreaDialog {
	private Text developerIdText;
	private Text developerNameText;
	private Text developerEmailText;
	private DVTPreferencesPage dvtPreferences;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AskSetPreferencesDialog(Shell parentShell) {
		super(parentShell);
		dvtPreferences = new DVTPreferencesPage();
		setTitle("Remote Synchronization and Code Editor");
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.heightHint = 171;
		container.setLayoutData(gd_container);
		
		Label lblSetUpYour = new Label(container, SWT.NONE);
		lblSetUpYour.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.BOLD));
		lblSetUpYour.setBounds(10, 5, 269, 19);
		lblSetUpYour.setText("Set up your developer team preferences:");
		
		developerIdText = new Text(container, SWT.BORDER);
		developerIdText.setBounds(10, 50, 430, 19);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel.setBounds(10, 30, 87, 14);
		lblNewLabel.setText("Developer's Id");
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(10, 75, 112, 14);
		lblNewLabel_1.setText("Developer's Name");
		
		developerNameText = new Text(container, SWT.BORDER);
		developerNameText.setBounds(10, 96, 430, 19);
		
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(10, 121, 112, 14);
		lblNewLabel_2.setText("Developer's Email");
		
		developerEmailText = new Text(container, SWT.BORDER);
		developerEmailText.setBounds(10, 141, 430, 19);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected void okPressed() 
	{
		dvtPreferences.updateId(developerIdText.getText());
	    dvtPreferences.updateAuthor(developerNameText.getText());
	    dvtPreferences.updateEmail(developerEmailText.getText());
	    super.okPressed();
	}
	
	
	

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
