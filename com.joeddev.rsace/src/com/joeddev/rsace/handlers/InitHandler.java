package com.joeddev.rsace.handlers;

import java.awt.Dialog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.preferences.Activator;
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
import com.jeeddev.rsace.appTree.UsrResourcesBuilder;
import com.joeddev.rsace.dialogs.AskSetPreferencesDialog;
import com.joeddev.rsace.preferences.DVTPreferencesGetter;
import com.joeddev.rsace.preferences.DVTPreferencesPage;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class InitHandler extends AbstractHandler {
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
           String email = null, id = null;
           if (serverPreferences.getAuthor().toString().equalsIgnoreCase(INITIAL_PREFERENCES_MARKER))
           {
        	   AskSetPreferencesDialog askPreferencesDialog = new AskSetPreferencesDialog(window.getShell());
        	   askPreferencesDialog.open();
           }
           author = (String) serverPreferences.getAuthor();
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
