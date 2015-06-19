package com.jortizsd.rsace.dialogs;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

import com.jortizsd.rsace.team.Team;

public class NewTeamDeveloperDialog extends TitleAreaDialog {
	private Text devNameText;
	private Text devIdText;
	private Text devEmailText;
	private Combo teamCombo;
	private Team myTeam;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public NewTeamDeveloperDialog(Shell parentShell) {
		super(parentShell);
		
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		teamCombo = new Combo(container, SWT.NONE);
		teamCombo.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.NORMAL));
		teamCombo.setBounds(10, 10, 430, 24);
		// Adds existing teams to combo 
		fillTeamComboBox("Select a team from this list");
	   
		
		
		
		
		Label lblDevelopersUserName = new Label(container, SWT.NONE);
		lblDevelopersUserName.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblDevelopersUserName.setBounds(10, 38, 142, 22);
		lblDevelopersUserName.setText("Developer's User Name");
		
		devNameText = new Text(container, SWT.BORDER);
		devNameText.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.NORMAL));
		devNameText.setBounds(10, 57, 282, 19);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel.setBounds(298, 38, 126, 14);
		lblNewLabel.setText("Developer's ID");
		
		devIdText = new Text(container, SWT.BORDER);
		devIdText.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.NORMAL));
		devIdText.setBounds(298, 57, 142, 19);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(10, 82, 110, 19);
		lblNewLabel_1.setText("Developer's email");
		
		devEmailText = new Text(container, SWT.BORDER);
		devEmailText.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.NORMAL));
		devEmailText.setBounds(10, 101, 430, 19);
		
		Button devFavoritesCheckBox = new Button(container, SWT.CHECK);
		devFavoritesCheckBox.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		devFavoritesCheckBox.setBounds(10, 132, 142, 18);
		devFavoritesCheckBox.setText("Add to Favorites");

		return area;
	}
	
	private void fillTeamComboBox (String title)
	{
		// Adds existing teams to combo 
		try 
		{
			
		    Team team = Team.getAllTeamsInstance();
		    List <Team> teams = team.fetchAllTeams();
		    if (teams.size() == 0)
		    	title = "No Teams Found";
		    teamCombo.setText(title);
			for (Team t : teams)
			    teamCombo.add(t.getTeamName());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());		
		}
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.setText("Add to Team");
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        setTitle("Developers Team");
        setMessage("Add New Developer to an Exixting Team");
        return contents;
    }
	
	@Override
	protected void okPressed() 
	{
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
