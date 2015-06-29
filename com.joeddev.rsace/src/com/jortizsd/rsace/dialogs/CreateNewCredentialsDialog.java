/**
 * @author        Jose Ortiz Costa
 * @application   com.jortizsd.rsace
 * @File          AskSetPreferencesDialog.java
 * @Date          04/06/2015
 * @Description   This class creates a dialog asking the
 *                users to enter their developer's preferences
 */
package com.jortizsd.rsace.dialogs;
import java.io.InputStream;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.preferences.DVTPreferencesPage;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.Team;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;

public class CreateNewCredentialsDialog extends TitleAreaDialog 
{
	private TreeBuilder treeBuilder;
	private AppManifestBuild manifest;
	private UsrResourcesBuilder resourcesBuilder;
	private Text developerIdText;
	private Text developerNameText;
	private Text developerEmailText;
	private Button allowSyncPermissionsBtn;
	private DVTPreferencesPage dvtPreferences;
	private Text teamText;
	private Text teamIdText;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public CreateNewCredentialsDialog(Shell parentShell) {
		super(parentShell);
		dvtPreferences = new DVTPreferencesPage();
		treeBuilder = TreeBuilder.getRsaceTreeInstance();
		manifest = AppManifestBuild.getInstance();
		resourcesBuilder = new UsrResourcesBuilder();
		
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
		
		developerIdText = new Text(container, SWT.BORDER);
		developerIdText.setBounds(353, 73, 87, 19);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel.setBounds(353, 53, 87, 14);
		lblNewLabel.setText("Developer's Id");
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(10, 55, 112, 14);
		lblNewLabel_1.setText("Developer's Name");
		
		developerNameText = new Text(container, SWT.BORDER);
		developerNameText.setBounds(10, 73, 337, 19);
		
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(10, 98, 112, 14);
		lblNewLabel_2.setText("Developer's Email");
		
		developerEmailText = new Text(container, SWT.BORDER);
		developerEmailText.setBounds(10, 118, 430, 19);
		
		allowSyncPermissionsBtn = new Button(container, SWT.CHECK);
		allowSyncPermissionsBtn.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		allowSyncPermissionsBtn.setBounds(10, 142, 430, 18);
		allowSyncPermissionsBtn.setText("Allow remote synchronization with other developers ( Recomended )");
        allowSyncPermissionsBtn.setSelection(true);
        
        teamIdText = new Text(container, SWT.BORDER);
        teamIdText.setBounds(353, 30, 87, 19);
        
        teamText = new Text(container, SWT.BORDER);
        teamText.setBounds(10, 30, 337, 19);
        
        Label lblNewLabel_3 = new Label(container, SWT.NONE);
        lblNewLabel_3.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
        lblNewLabel_3.setBounds(350, 10, 60, 14);
        lblNewLabel_3.setText("Team's Id");
        
        Label lblTeamName = new Label(container, SWT.NONE);
        lblTeamName.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
        lblTeamName.setBounds(10, 10, 129, 14);
        lblTeamName.setText("Create a new team:");
       
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
	
	/**
	 * Creates an action when the ok button is cliked
	 */
	@Override
	protected void okPressed() 
	{
		initAppWithNewCredentials();
	    super.okPressed();
	}
	
	/**
	 * Creates contents
	 */
	@Override
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);

        setTitle("Remote Synchronization and Code Editor");
        setMessage("Set up your developer's team preferences");

       

        return contents;
    }
	
	
	public void initAppWithNewCredentials ()
	{
		SnycProgress syncProgress = new SnycProgress(getShell(), SWT.TITLE | SWT.PRIMARY_MODAL);
	    try
		{
	    	Team team = Team.createNewTeam(teamText.getText(), teamIdText.getText());
	    	Developer developer = new Developer(team, developerIdText.getText(), developerNameText.getText(), 
	    			                            developerEmailText.getText(), false, true);
			if (developer.isRegisteredInTeam())
			{	
        	    MessageDialog.openInformation(getShell(),
			                              "Rsace Information",
			                              "Your credentials are being used by other developer in the system. Please enter a diferent developer ID, and/or team ID");
        	    treeBuilder.deleteRoot();
        	    
			}
			else 
            {
				
				treeBuilder.buildAppTree(developer.getName(), developer.getEmail());
				developer.addDeveloperToDB();
            	developer.setAsSessionOwner();
            	manifest.makeManifestFile();
        	    List <Developer> teamMembers= developer.getMyTeamMembers();
        	    for (Developer dev : teamMembers)
        	    {
        	    	
        	    	if (!developer.getId().equalsIgnoreCase(dev.getId()))
        		          dev.addToTeam();
        	    	
        	    }
        	    InputStream headerStream = resourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, developer.getName(), ResourcesBuilder.SYNC_FILE, 
        	    		                                                               developer.getEmail(), false, developer.getTeam().getTeamName(), teamMembers);
        	    resourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
        	    
        	    treeBuilder.refreshAppTree();
        	    treeBuilder.refreshUserRootProject();
        	    syncProgress.open();
            }
		}
	    catch (Exception e)
	    {
	    	
	    }
	 }
	

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
