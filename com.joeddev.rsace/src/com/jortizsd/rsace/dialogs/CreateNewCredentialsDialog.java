/**
 * @author        Jose Ortiz Costa
 * @application   com.jortizsd.rsace
 * @File          AskSetPreferencesDialog.java
 * @Date          04/06/2015
 * @Description   This class creates a dialog which asks the users
 *                to create new developer's credentials
 *                If the data is validated, then all the data is
 *                saved in the database, and in addition, it is 
 *                loaded to the app's configuration files.
 *                This class also checks for data validation
 */
package com.jortizsd.rsace.dialogs;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
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
import com.jortizsd.rsace.remote.AppConfig;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.Team;
import com.jortizsd.rsace.views.LogConstants;
import com.jortizsd.rsace.views.RsaceLog;

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
	 * Creates an action when the OK button is clicked
	 */
	@Override
	protected void okPressed() 
	{
		initAppWithNewCredentials();
	    
	}
	
	/**
	 * Creates an action when the Cancel Button is clicked
	 */
	@Override
	protected void cancelPressed()
	{
		try {
			treeBuilder.deleteRoot();
			super.cancelPressed();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.cancelPressed();
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
		try
		{
			// Checks data validation
	    	if (isDataValidated())
	    	{
	    	   Team team = Team.createNewTeam(teamText.getText(), teamIdText.getText());
	    	   Developer developer = new Developer(team, developerIdText.getText(), developerNameText.getText(), 
	    	   		                            developerEmailText.getText(), false, true);
	    	   // Checks is the info provided by the new developer is already taken. Otherwise, register the developer
	    	   if (developer.isRegisteredInTeam())
	    	   {
	    		  // A developer and/or team with those credentials already exist 
			      MessageDialog.openInformation(getShell(),
			                                    "Rsace Information",
			                                    "The developer and/or team ID that you provided are being already used " + 
			                                    "by other RSACE's develoepers and/or teams.");
	    	      RsaceLog.writeLog("Rsace Failed to Create New Credentials", "Credentials already exist", LogConstants.LOG_ERROR_CONTEXT);
	    	   }
        	   else 
               {
        		  // Register developer 
				  treeBuilder.buildAppTree(developer.getName(), developer.getEmail());
				  developer.addDeveloperToDB();
            	  developer.setAsSessionOwner();
            	  manifest.makeManifestFile();
        	      List <Developer> teamMembers= developer.getMyTeamMembers();
        	      for (Developer dev : teamMembers)
        	         if (!developer.getId().equalsIgnoreCase(dev.getId()))
        		          dev.addToTeam();
        	      InputStream headerStream = resourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, developer.getName(), ResourcesBuilder.SYNC_FILE, 
        	    		                                                               developer.getEmail(), false, developer.getTeam().getTeamName(), teamMembers);
        	      resourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
        	      treeBuilder.refreshAppTree();
        	      RsaceLog.writeLog("Rsace Synchronization Status", "Local synchronization succesfully done with developer's id: " + 
	                       developer.getId() + " and developer's team id: " + developer.getTeam().getTeamId() + 
	                       " at " + new Date().toString(), LogConstants.LOG_INFO_CONTEXT);
        	      super.okPressed();
        	    }
	    	}
	    	else
	    	{
	    		// Data was not validated
	    		MessageDialog.openInformation(getShell(),
                        "Rsace Information",
                        "All fields are required, and ID's fields must contain only numbers");
	    		RsaceLog.writeLog("New Credentials Warning", "All fields are required, and ID's fields must contain only numbers" , LogConstants.LOG_WARNING_CONTEXT);
	    	}
		}
	    catch (Exception e)
	    {
	    	// Create error
	    }
	}
	
	/**
	 * Validates data 
	 * @return True if the data was validated. Otherwise, return false
	 */
	private boolean isDataValidated ()
	{
		if (developerIdText.getText().equals("") || developerNameText.getText().equals("") || 
			developerEmailText.getText().equals("") || teamIdText.getText().equals("") || 
			teamText.getText().equals("") || !isFieldOnlyNumbers(developerIdText.getText()) ||
			!isFieldOnlyNumbers(teamIdText.getText()))
            return false;
        return true;
			
	}
	
	/**
	 * Checks if the id's fields contain only numbers
	 * @param string String object representing the string to be tested
	 * @return True if the string given contains only numbers. Otherwise, returns false.
	 */
	private static boolean isFieldOnlyNumbers (String string)
	{
		  char[] chars = new char[string.length()];
	      string.getChars(0, chars.length, chars, 0);
	      for (int i = 0; i < chars.length; i++) 
	         if (!('0' <= chars[i] && chars[i] <= '9')) 
	            return false;
	      return true;
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
