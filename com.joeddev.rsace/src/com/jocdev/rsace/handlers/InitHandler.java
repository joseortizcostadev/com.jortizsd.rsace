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
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import com.jocdev.rsace.appTree.AppManifestBuild;
import com.jocdev.rsace.appTree.TreeBuilder;
import com.jocdev.rsace.appTree.UsrResourcesBuilder;
import com.jocdev.rsace.dialogs.AskSetPreferencesDialog;
import com.jocdev.rsace.preferences.DVTPreferencesGetter;
import com.jocdev.rsace.team.Developer;

/**
 * Our handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class InitHandler extends AbstractHandler 
{
	public static final String MENU_SYNC = "Sync";
	public static final String MENU_NEW_TEAM = "New Developer Team";
	public static final String MENU_NEW_DEVELOPER = "New Developer";
	/**
	 * The constructor.
	 */
	
	private final String INITIAL_PREFERENCES_MARKER = "your_name_or_username_here";
	public InitHandler() {
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
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_SYNC))
		      initApp(window);
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
	       
           TreeBuilder treeBuilder = TreeBuilder.getRsaceTreeInstance();
           DVTPreferencesGetter serverPreferences = new DVTPreferencesGetter ();
           String author = (String) serverPreferences.getUsername();
           String email = null, id = null;
           if (serverPreferences.getUsername().toString().equalsIgnoreCase(INITIAL_PREFERENCES_MARKER))
           {
        	   AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(window.getShell());
        	   askPreferencesDialog.open();
           }
           author = (String) serverPreferences.getUsername();
           email = (String) serverPreferences.getEmail();
           id = (String) serverPreferences.getId();
           treeBuilder.buildAppTree(author, email);
           Developer developer = new Developer(id, author, email, false, true);
           developer.setAsSessionOwner();
           AppManifestBuild manifest = AppManifestBuild.getInstance();
           manifest.makeManifestFile();
           UsrResourcesBuilder usrResourcesBuilder = new UsrResourcesBuilder();
           usrResourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	
}
