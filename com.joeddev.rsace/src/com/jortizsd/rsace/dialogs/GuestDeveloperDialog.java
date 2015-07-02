package com.jortizsd.rsace.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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
import com.jortizsd.rsace.remote.RemoteConstants;
import com.jortizsd.rsace.remote.Team;
import com.jortizsd.rsace.views.LogConstants;
import com.jortizsd.rsace.views.RsaceLog;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.EmailInterface;

public class GuestDeveloperDialog extends TitleAreaDialog implements EmailInterface
{
	private Text emailText;
	private Combo teamCombo;
	private Team team;
	private String [] teamsId;
	private String teamId;

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
		
		teamCombo = new Combo(container, SWT.NONE);
		teamCombo.setBounds(10, 10, 261, 22);
		fillTeamComboBox();
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 40, 152, 14);
		lblNewLabel.setText("Guest Developer Email");
		
		emailText = new Text(container, SWT.BORDER);
		emailText.setBounds(10, 59, 259, 19);

		return area;
	}
    
	/**
	 *  Populates comboBox with all the existing team's names
	*/
	private void fillTeamComboBox ()
	{
		// Adds existing teams to the teamComboBox 
		try 
		{
			
		    team = Team.getAllTeamsInstance();
		    List <Team> teams = team.fetchAllTeams();
		    teamsId = new String[teams.size()];
		    int teamCounter = 0;
		    String comboTeamTitle = "Select your team from this list";
		    if (teams.isEmpty())
		    	comboTeamTitle = "No teams found";
		    teamCombo.setText(comboTeamTitle);
		    for (Team t : teams)
			{
			    teamCombo.add(t.getTeamName());
			    teamsId[teamCounter] = t.getTeamId();
			    teamCounter++;
			    
			}
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
	
	@Override
	public String getSubject() 
	{
		return "You have a request to join to a Rsace's developers team";
	}

    public void sendRequest ()
    {
    	try
    	{
    		teamId = teamsId[teamCombo.getSelectionIndex()];
    		Team team = Team.createNewTeam(teamCombo.getText(), teamId);
    		Developer dev = new Developer ("guest");
    		dev.setEmail(emailText.getText());
    		dev.setTeam(team);
    		EmailInterface.super.sendEmail(dev, getSubject(), getBodyMessage());
    		String messageInfo = "Your request was succesfully sent to " + emailText.getText();
    		String messageHeader = "Rsace Guest Request Information";
    		MessageDialog.openInformation(getShell(),
                    messageHeader,
                    messageInfo);
			RsaceLog.writeLog(messageHeader,messageInfo , LogConstants.LOG_INFO_CONTEXT);
			super.okPressed();
    	}
    	catch (Exception e)
    	{
    		String messageInfo = "Your request couldn't be sent";
    		String messageHeader = "Rsace Guest Request Error";
    		MessageDialog.openInformation(getShell(),
                    messageHeader,
                    messageInfo + " " + e.getMessage());
			RsaceLog.writeLog(messageHeader,messageInfo + " " + e.getMessage(), LogConstants.LOG_ERROR_CONTEXT);
    	}
    }
    
    @Override
    protected void okPressed ()
    {
    	sendRequest();
    }

	@Override
	public String getBodyMessage() 
	{
		return "<p>Hi, </p> " +
			   "A Rsace's developer member has sent you a request to join his/her " + 
			   "team of developers. </br>" +
			   "If you already are a Rsace's developer member, you just need to start a remote session in Rsace plug-in, " +
			   "and insert your developer id, and the following </strong> team id " + teamId + "</strong> " + 
			   ". Otherwise, download Rsace plug-in platform for Eclipse from " + RemoteConstants.REMOTE_WEB_RSACE_PAGE +
			   ". Once Rsace is installed in eclipse, create a new free Rsace developer account, and follow the steps described above. " + 
			   "You will find more detailed information about this proccess and many more in "  + RemoteConstants.REMOTE_WEB_RSACE_TUTORIAL + 
			   "</br></br> Regards, </br> The Rsace Team" ;
	}
}
