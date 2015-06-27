package com.jortizsd.rsace.dialogs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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

import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.preferences.DVTPreferencesPage;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.Team;

public class LoginDialog extends TitleAreaDialog {
	private Text devIdText;
	private Listener newCredentialsListener;
	private Text teamText;
	private UsrResourcesBuilder resourcesBuilder;
	private TreeBuilder treeBuilder;
	private AppManifestBuild manifest;
	private SnycProgress syncProgress;
	

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
		
		resourcesBuilder = new UsrResourcesBuilder();
		treeBuilder = TreeBuilder.getRsaceTreeInstance();
	    manifest = AppManifestBuild.getInstance();
	    resourcesBuilder = new UsrResourcesBuilder();
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
		
		devIdText = new Text(container, SWT.BORDER);
		devIdText.setBounds(10, 30, 116, 19);
		devIdText.setToolTipText("Enter your developer ID");
		
		
		Label lblTeams = new Label(container, SWT.NONE);
		lblTeams.setBounds(147, 10, 60, 14);
		lblTeams.setText("Team ID");
		
		teamText = new Text(container, SWT.BORDER);
		teamText.setBounds(147, 30, 268, 19);

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
		 Button sync = createButton(parent,IDialogConstants.CANCEL_ID,"New Credentials", true);
		 createButton(parent, IDialogConstants.OK_ID, "Synchronize",
				true);
		 sync.addListener(SWT.Selection, newCredentialsListener);
    }
	
	
	
    
	// New credentials button clicked
	public void syncListener ()
	{
	    newCredentialsListener = new Listener() 
	    {
		    public void handleEvent(Event event) 
		    {
		        
		        AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(getShell());
	       	    askPreferencesDialog.open();
		        
		    }
		};
	}
	// Synchronize button clicked
	@Override
	protected void okPressed()
	{
		
		syncProgress = new SnycProgress(getShell(), SWT.TITLE | SWT.PRIMARY_MODAL);
	    try
		{
			Developer developer = Team.getDeveloperFromDB(devIdText.getText(), teamText.getText());
			if (developer == null)
			{	
        	    MessageDialog.openInformation(getShell(),
			                              "Rsace Information",
			                              "Sorry, we couldn't find your credentials in our servers. Where do I go from here?\n" +
			                              "1. Check if your credentials are in the correct form. Remember, developer's id and team's id " +
			                              "can be in any form you want but only 5 characters maximum\n" + 
			                              "2. If this is your first time using Rsace, you'll need to create new credentials");
        	    treeBuilder.deleteRoot();
        	    
			}
			else if (developer.isRegistered() == true)
            {
				System.out.println("I am here 1");
				treeBuilder.buildAppTree(developer.getName(), developer.getEmail());
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
		    System.out.println(e.getMessage());	
		}
		super.okPressed();
	}
	
	
    
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(436, 203);
	}
}
