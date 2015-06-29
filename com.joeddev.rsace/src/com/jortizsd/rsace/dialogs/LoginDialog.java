/**
 * @author        Jose Ortiz Costa
 * @application   com.jortizsd.rsace
 * @File          LoginDialog.java
 * @Date          04/06/2015
 * @Description   This class creates a dialog asking the
 *                users to enter their developer's credentials.
 *                If the credentials are correct, it loads all
 *                the user configuration from the database.
 *                Otherwise, its inform the users that their
 *                credentials are incorrect.
 *                This class also contains a button to launch
 *                the CreateNewCredentialsDialog which creates
 *                new credentials for this application
 */
package com.jortizsd.rsace.dialogs;
import java.io.InputStream;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.Team;

public class LoginDialog extends TitleAreaDialog 
{
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
	public LoginDialog(Shell parentShell) 
	{
		// initializes dialog componenets
		super(parentShell);
		resourcesBuilder = new UsrResourcesBuilder();
		treeBuilder = TreeBuilder.getRsaceTreeInstance();
	    manifest = AppManifestBuild.getInstance();
	    resourcesBuilder = new UsrResourcesBuilder();
	    credentialsListener(); // Listener for synchronize button
	 }

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) 
	{
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
		 Button newCredentialsButton = createButton(parent,IDialogConstants.CANCEL_ID,"New Credentials", true);
		 createButton(parent, IDialogConstants.OK_ID, "Synchronize",
				true);
		 newCredentialsButton.addListener(SWT.Selection, newCredentialsListener);
    }
	
	/**
	 * Create listener for credentials button 
	 */
	public void credentialsListener ()
	{
	    newCredentialsListener = new Listener() 
	    {
		    public void handleEvent(Event event) 
		    {
		        
		        CreateNewCredentialsDialog askPreferencesDialog = new CreateNewCredentialsDialog(getShell());
	       	    askPreferencesDialog.open();
		        
		    }
		};
	}
	/**
	 * Action when synchronized button is pressed
	 */
	@Override
	protected void okPressed()
	{
	    initAppWithCredentials();
	}
	
	/**
	 * Action when cancel button is pressed
	 */
	@Override
	protected void cancelPressed ()
	{
		try 
		{
			treeBuilder.deleteRoot();
			super.cancelPressed();
		} 
		catch (CoreException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Clear all fields 
	 */
	private void clearFields ()
	{
		devIdText.setText("");
		teamText.setText("");
	}
	
	/**
	 *  If the credentials given by the user are correct, it loads
	 *  all the configuration parameters for this user.
	 */
	public void initAppWithCredentials ()
	{
		try
		{
			Developer developer = Team.getDeveloperFromDB(devIdText.getText(), teamText.getText());
			if (developer == null)
			{	
        	    MessageDialog.openInformation(getShell(),
			                              "Rsace Information",
			                              "Ops, we are sorry. We couldn't find your credentials in our server, but " + 
			                              "the RSACE's team would be very happy to have you in our developer's family. " + 
			                              "Please, create new credentials to join our RSACE team");
        	    clearFields();
        	    
			}
			else if (developer.isSessionOwner() == false)
			{
				MessageDialog.openInformation(getShell(),
                        "Rsace Information",
                        "The developer's ID that you have provided is not the owner of " + 
                        "this team. Please, enter the developer and team ID provided at registration time");
                clearFields();
			}
			else if (developer.isRegisteredInTeam() == true )
            {
				
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
        	   // treeBuilder.refreshUserRootProject();
        	    super.okPressed();
        	    
        	    
        	}
          
		    
		}
		catch (Exception e)
		{
		    System.out.println(e.getMessage());	
		}
	}
    
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(436, 203);
	}
}
