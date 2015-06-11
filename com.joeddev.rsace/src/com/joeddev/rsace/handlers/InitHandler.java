package com.joeddev.rsace.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;

import com.jeeddev.rsace.appTree.AppManifestBuild;
import com.jeeddev.rsace.appTree.ConfigBuilder;
import com.jeeddev.rsace.appTree.Developer;
import com.jeeddev.rsace.appTree.TreeBuilder;
import com.jeeddev.rsace.appTree.TreeReader;
import com.jeeddev.rsace.appTree.TreeWriter;
import com.joeddev.rsace.preferences.DVTPreferencesGetter;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class InitHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public InitHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
	    
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		initApp(window);
		return null;
	}
	
	private void initApp (IWorkbenchWindow window)
	{
	    try
        {
           TreeBuilder treeBuilder = TreeBuilder.getRsaceTreeInstance();
           DVTPreferencesGetter serverPreferences = new DVTPreferencesGetter ();
           String author = (String) serverPreferences.getAuthor();
           String email = (String) serverPreferences.getEmail();
           String id = (String) serverPreferences.getId();
           treeBuilder.buildAppTree(author, email);
           boolean result;
           if (author.equalsIgnoreCase("your_name_or_username_here"))
           {
        	   result =   MessageDialog.openQuestion(window.getShell(), "Remote Sinchronization and Code Editor", 
        			                      "Welcome to RSACE. It is higly recomended to set up your developer preferences before starting to work with this plu-gin. " + 
        	                              "Would you like to set up your developer preferences?");
               if (result)
               {
            	   System.out.println(result);
               }
        		 
           }
           Developer developer = new Developer(id, author, email, false, true);
           AppManifestBuild manifest = AppManifestBuild.getInstance();
           manifest.makeManifestFile();
           developer.setAsSessionOwner();
           
           
          
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}
	
	
}
