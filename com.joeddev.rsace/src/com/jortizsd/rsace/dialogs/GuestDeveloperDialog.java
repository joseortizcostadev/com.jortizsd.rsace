package com.jortizsd.rsace.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GuestDeveloperDialog extends TitleAreaDialog {
	private Text text;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public GuestDeveloperDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		setTitle("Rsace Guest Developer");
		setMessage("Add a guest developer to your team");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Combo combo = new Combo(container, SWT.NONE);
		combo.setBounds(10, 10, 261, 22);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 40, 152, 14);
		lblNewLabel.setText("Guest Developer Email");
		
		text = new Text(container, SWT.BORDER);
		text.setBounds(10, 59, 259, 19);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(279, 243);
	}
}
