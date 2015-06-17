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
package com.jocdev.rsace.handlers;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.jocdev.rsace.appTree.AppManifestBuild;
import com.jocdev.rsace.appTree.ResourcesBuilder;
import com.jocdev.rsace.appTree.TreeBuilder;
import com.jocdev.rsace.appTree.UsrResourcesBuilder;
import com.jocdev.rsace.dialogs.AskSetPreferencesDialog;
import com.jocdev.rsace.dialogs.SnycProgress;
import com.jocdev.rsace.preferences.DVTPreferencesGetter;
import com.jocdev.rsace.team.Developer;
import com.jocdev.rsace.team.Team;

/**
 * Our handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class InitHandler extends AbstractHandler 
{
	public static final String MENU_NEW_SYNC = "Synchronize";
	public static final String MENU_NEW_SESSION = "Open Remote Session";
	public static final String MENU_NEW_TEAM = "New Developer Team";
	public static final String MENU_NEW_DEVELOPER = "New Developer";
	private TreeBuilder treeBuilder;
	
	/**
	 * The constructor.
	 */
	
	private final String INITIAL_PREFERENCES_MARKER = "your_name_or_username_here";
	public InitHandler() 
	{
		treeBuilder = TreeBuilder.getRsaceTreeInstance();
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
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SYNC))
		   {
			   
			   if (!treeBuilder.isPluginSynchronizedLocally())
	           {
				   
				   initApp(window);
	           }
			   else
			   {
				   
				   MessageDialog.openInformation(window.getShell(),
						                         "Rsace Information",
						                         "Your file is already synchronized with Rsace's resources in local mode. " + 
						                         "Open a new remote session in order to synchronize this file remotely");
			   }
		   }
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SESSION))
		   {
			  // Do team adding work here and add it to preferences
			  //before opening section
			   
			  
		   }
		   else if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_TEAM))
			  System.out.println("New Developer Team");
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
	           
               
           
               DVTPreferencesGetter serverPreferences = new DVTPreferencesGetter ();
               String author = (String) serverPreferences.getUsername();
               String email = null, id = null, teamName, teamId;
               Team team;
               if (serverPreferences.getUsername().toString().equalsIgnoreCase(INITIAL_PREFERENCES_MARKER))
               {
        	       AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(window.getShell());
        	       askPreferencesDialog.open();
               }
               author = (String) serverPreferences.getUsername();
               email = (String) serverPreferences.getEmail();
               id = (String) serverPreferences.getId();
               teamName = (String) serverPreferences.getTeamName();
               teamId = (String) serverPreferences.getTeamId();
               treeBuilder.buildAppTree(author, email);
               team = Team.createNewTeam(teamName,teamId);
               Developer developer = new Developer(team, id, author, email, false, true);
               developer.setAsSessionOwner(team);
               AppManifestBuild manifest = AppManifestBuild.getInstance();
               manifest.makeManifestFile();
               // Open session 
		       UsrResourcesBuilder usrResourcesBuilder = new UsrResourcesBuilder();
		       InputStream headerStream = usrResourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, author, ResourcesBuilder.SYNC_FILE, email, false, "No team", null);
	           usrResourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
	           treeBuilder.refreshAppTree();
	           new SnycProgress(window.getShell(), SWT.TITLE | SWT.PRIMARY_MODAL | SWT.CENTER).open();
           
	    }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	
}
