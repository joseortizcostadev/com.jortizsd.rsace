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
import java.io.InputStream;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import com.jortizsd.rsace.appTree.ResourcesBuilder;
import com.jortizsd.rsace.appTree.TreeBuilder;
import com.jortizsd.rsace.appTree.UsrResourcesBuilder;
import com.jortizsd.rsace.dialogs.LoginDialog;
import com.jortizsd.rsace.dialogs.NewTeamDeveloperDialog;
import com.jortizsd.rsace.preferences.RsacePreferencesPage;
import com.jortizsd.rsace.remote.AppConfig;
import com.jortizsd.rsace.views.RsaceLog;


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
    private UsrResourcesBuilder usrResourcesBuilder;
	private String author;
	private String email; 
	
   
   
	
	/**
	 * The constructor.
	 * @throws PartInitException 
	 */
	
	
	public InitHandler() throws PartInitException 
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
		   AppConfig.checkLogStatus();
		   
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SYNC))
		   {
			   
			   if (!treeBuilder.isPluginSynchronizedLocally())
			   {
				  
	              initApp(window);
	              
			   }
			   else
			   {
				   InputStream headerStream = usrResourcesBuilder.getHeaderStreamForSyncFile(UsrResourcesBuilder.FILE_MODE_LOCAL, author, ResourcesBuilder.SYNC_FILE, email, false, "No team", null);
		           usrResourcesBuilder.syncFile(UsrResourcesBuilder.LOCAL_MODE, headerStream);
		           
		       }
			   
			   
		   }
		   if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_SESSION))
		   {
			   
	           startRemoteConnection(window); 
			   
		   }
		   else if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_TEAM))
		   {
			  // Add a new developer to a existing team.
			   
			   new NewTeamDeveloperDialog(window.getShell()).open();
		   }
		   else if (event.getCommand().getName().equalsIgnoreCase(MENU_NEW_DEVELOPER))
			   System.out.println("I think that dos not work");
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
	          
               LoginDialog login = new LoginDialog(window.getShell());
               login.open();
              
	               
	    }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	public void startRemoteConnection (IWorkbenchWindow window) 
	{
		try
		{
		    
	    }
 		catch (Exception e)
 		{
	      
	    	 MessageDialog.openInformation(window.getShell(),
	    						          "Rsace Information",
	    						          "Connection to Rsace server failed with error: " + e.getMessage());
	    }
	       
	}
}
