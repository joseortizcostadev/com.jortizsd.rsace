/**
 * @author        Jose Ortiz Costa
 * @application   com.jortizsd.rsace
 * @File          NewTeamDeveloperDialog.java
 * @Date          04/06/2015
 * @Description   This class creates a dialog for adding
 *                a new developer to an existing team of developers
 *                
 */

package com.jortizsd.rsace.dialogs;
import java.util.Date;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jortizsd.rsace.remote.AppConfig;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.EmailInterface;
import com.jortizsd.rsace.remote.Team;
import com.jortizsd.rsace.views.LogConstants;
import com.jortizsd.rsace.views.RsaceLog;

public class NewTeamDeveloperDialog extends TitleAreaDialog implements EmailInterface{
	private Text devIdText;
	private Combo teamCombo;
	private Developer newDeveloperMember;
	private Team team;
	private Button devFavoritesCheckBox;
	public static final String TEAM_COMBO_TITLE = "Select a team from this list";
	public static final String NO_TEAMS = "No Teams Found";
	private String [] teamsId;
	
	

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
		teamCombo.setBounds(10, 10, 290, 22);
		// Adds existing teams to combo 
		fillTeamComboBox();
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel.setBounds(10, 38, 126, 14);
		lblNewLabel.setText("Developer's ID");
		
		devIdText = new Text(container, SWT.BORDER);
		devIdText.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.NORMAL));
		devIdText.setBounds(10, 58, 290, 19);
		
		devFavoritesCheckBox = new Button(container, SWT.CHECK);
		devFavoritesCheckBox.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		devFavoritesCheckBox.setBounds(10, 87, 142, 18);
		devFavoritesCheckBox.setText("Add to Favorites");

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
			
		    Team team = Team.getAllTeamsInstance();
		    
		    List <Team> teams = team.fetchAllTeams();
		    teamsId = new String[teams.size()];
		    int teamCounter = 0;
		    String comboTeamTitle = TEAM_COMBO_TITLE;
		    if (teams.isEmpty())
		    	comboTeamTitle = NO_TEAMS;
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
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.setText("Add to Team");
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	/**
	 * Creates contents
	 */
	@Override
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        setTitle("Developers Team");
        setMessage("Add a RSACE'S developer to your team");
        return contents;
    }
	
	/**
	 * Creates an action when the ok button is clicked
	 */
	@Override
	protected void okPressed() 
	{
		addNewTeamMember();
		
    }
	/**
	 * @category        Private Class Method
	 * @description     Validates form's data before sending it to preferences
	 * @param teamName  String object representing the team's name
	 * @param devName   String object representing the developer's name
	 * @param devId     String object representing the developer's id
	 * @param devEmail  String object representing the developer's email
	 * @return          True if the data was correctly validated. Otherwise returns false.
	 */
	private boolean isDataValidated (String teamName, String devId)
	{
		
		if (teamName.equalsIgnoreCase(NO_TEAMS) || teamName.equalsIgnoreCase(TEAM_COMBO_TITLE))
			return false;
		else if ( devId.equalsIgnoreCase("") )
			return false;
		return true;
	}
	
	public void addNewTeamMember ()
	{
		String myTeam = teamCombo.getText();
		String devId = devIdText.getText();
		boolean isInFavorites = devFavoritesCheckBox.getSelection();
		if (isDataValidated(myTeam,  devId))
		{
			// The data has been validated
			try 
			{
				
				// Saves new developer info in XML configuration, and preferences files.
				team = Team.getTeamByName(myTeam);
				newDeveloperMember = Team.getDeveloperFromDB(devId);
			    if (newDeveloperMember != null)
				{
			    	newDeveloperMember.setTeam(team);
					newDeveloperMember.setAsOwner(false);
			        newDeveloperMember.addToTeam();
				    newDeveloperMember.addDeveloperToDB();
				    newDeveloperMember.setAsFavorite(isInFavorites);
				    
				    EmailInterface.super.sendEmail(newDeveloperMember, getSubject(), getBodyMessage());
				    RsaceLog.writeLog("Rsace Information","The developer " + newDeveloperMember.getName() + " was temporaly added to the team waiting list " + 
				                      "until he/she accept the requested invitation to join this team" +  " at " + new Date().toString() + 
				                      " In order to get more information about this new developer, you can check the file rsace_team.xml ", LogConstants.LOG_INFO_CONTEXT);
				    super.okPressed();
				}
				else
				{
					MessageDialog.openInformation(getShell(),
                            "Rsace Information",
                            "The developer with id: " + devId + 
                            " does not match with any Rsace's developer account " + 
                            "Please enter a valid developer's id");
					RsaceLog.writeLog("Rsace Error", "Credentials provided dont correspond to any Rsace's developer member", LogConstants.LOG_ERROR_CONTEXT);
				}
				
	
			} 
			catch (Exception e) 
			{
				RsaceLog.writeLog("Internal Error", e.getMessage(), LogConstants.LOG_ERROR_CONTEXT);
			}
			
		}
		else
		{
			
	       
			// The data has not been validated
			MessageDialog.openInformation(getShell(),
					                      "Rsace Information",
					                      "All fields are required");
			RsaceLog.writeLog("Rsace Warning","All fields are required" , LogConstants.LOG_WARNING_CONTEXT);
			
		
		}
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(310, 261);
	}

	@Override
	public String getSubject() 
	{
		return "A Rsace's developer sent you a request to join his/her developer's team. Check it out!!!";
	}

	@Override
	public String getBodyMessage() 
	{
		return "<html><body><p>Hello " + newDeveloperMember.getName() + ", </p>" + 
	           "The leader of the Rsace developers team " + team.getTeamName() +
	           " with team id " + team.getTeamId() + 
	           " has invited you to join to his/her developer team. </br>" + 
	           "In the case you want to join this team, you just need to open " + 
	           "a remote session in your Rsace plug-in, and insert the team id provided above. " + 
	           "If everything goes as expected, you will join to the " + 
	           "work flow of this team of developers. Otherwise, no action is required, and your Rsace developer " + 
	           "information will be automatically removed from the team configuration file. </br>" + 
	           "For more detailed information about this process or other questions, please visit www.jortizsd.com/rsace, " + 
	           "or send an email to rsace@jortizsd.com.</br></br>" + 
	           "Regards, </br>" + "Rsace Administration Team </body></html>" ;
	           
	}
}
