/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          InitHandler.java
 * @Date          04/06/2015
 * @Description   This class is the starting point
 *                of the application where this plug-in
 *                and all its components and resources
 *                are initialized for the first time 
 */
package com.jortizsd.rsace.handlers;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import com.jortizsd.rsace.appTree.AppManifestBuild;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.dialogs.AskSetPreferencesDialog;
import com.jortizsd.rsace.dialogs.NewTeamDeveloperDialog;
import com.jortizsd.rsace.dialogs.SnycProgress;
import com.jortizsd.rsace.preferences.DVTPreferencesPage;
import com.jortizsd.rsace.remote.Developer;
import com.jortizsd.rsace.remote.Remote;
import com.jortizsd.rsace.remote.RemoteConstants;
import com.jortizsd.rsace.remote.Team;


/**
 * Our handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class InitHandler extends AbstractHandler 
{
	public static final String MENU_NEW_SYNC = "Synchronize";
	public static final String MENU_NEW_SESSION = "Open Remote Session";
	public static final String MENU_NEW_TEAM = "Add Developers to Team";
	public static final String MENU_NEW_DEVELOPER = "New Developer";
	private TreeBuilder treeBuilder;
	private DVTPreferencesPage developerPreferences;
	private AppManifestBuild manifest;
	private UsrResourcesBuilder usrResourcesBuilder;
	private String author;
	private String email; 
	private String id; 
	private String teamName; 
	private String teamId;
    private Team team;
    private SnycProgress syncProgress;
   
	
	/**
	 * The constructor.
	 */
	
	private final String INITIAL_PREFERENCES_MARKER = "your_name_or_username_here";
	public InitHandler() 
	{
		treeBuilder = TreeBuilder.getRsaceTreeInstance();
		developerPreferences = new DVTPreferencesPage ();
	    manifest = AppManifestBuild.getInstance();
	    usrResourcesBuilder = new UsrResourcesBuilder();
	    author = (String) developerPreferences.getUsername();
	   
	    
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
	    try
	    {
		   IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		   syncProgress = new SnycProgress(window.getShell(), SWT.TITLE | SWT.PRIMARY_MODAL | SWT.CENTER);
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SYNC))
		   {
			   
			   if (!treeBuilder.isPluginSynchronizedLocally())
			   {
	              initApp(window);
	              syncProgress.open();
			   }
			   else
			   {
				   InputStream headerStream = usrResourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, author, ResourcesBuilder.SYNC_FILE, email, false, "No team", null);
		           usrResourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
		           syncProgress.open();
		       }
			   
			   
		   }
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SESSION))
		   {
			   Developer d = new Developer();
	    	   d.getConn();
			   
		   }
		   else if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_TEAM))
		   {
			  // Add a new developer to a existing team.
			   
			   new NewTeamDeveloperDialog(window.getShell()).open();
		   }
		   else if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_DEVELOPER))
				  System.out.println("New Developer");
		   return null;
	    }
	    catch (Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
		return null;
	}
	
	/**
	 * @category      Private Class Method
	 * @description   Initializes all the component and resources of this application
	 * @param window  IWorkbenchWindow object representing the actual window
	 */
	private void initApp (IWorkbenchWindow window)
	{
	    try
        {
	           
               if (developerPreferences.getUsername().toString().equalsIgnoreCase(INITIAL_PREFERENCES_MARKER))
               {
        	       AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(window.getShell());
        	       askPreferencesDialog.open();
               }
               author = (String) developerPreferences.getUsername();
               email = (String) developerPreferences.getEmail();
               id = (String) developerPreferences.getId();
               teamName = (String) developerPreferences.getTeamName();
               teamId = (String) developerPreferences.getTeamId();
               treeBuilder.buildAppTree(author, email);
               team = Team.createNewTeam(teamName,teamId);
               Developer developer = new Developer(team, id, author, email, false, true);
               developer.setAsSessionOwner();
               manifest.makeManifestFile();
               // Open local session 
		       InputStream headerStream = usrResourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, author, ResourcesBuilder.SYNC_FILE, email, false, "No team", null);
	           usrResourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
	           treeBuilder.refreshAppTree();
	           
	           
           
	    }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	public void startRemoteWork (IWorkbenchWindow window) 
	{
		
			
	      
	    	   // The data has not been validated
	    	   MessageDialog.openInformation(window.getShell(),
	    						          "Rsace Information",
	    						          "Connection to Rsace server failed. Please, try later " + 
	    						          "or contact the web master at jortizdev@jortizsd.com");
	       
		
		
		
	}
	
	
}
