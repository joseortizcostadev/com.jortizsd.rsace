package com.joeddev.rsace.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
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

import com.jeeddev.rsace.appTree.ConfigBuilder;
import com.jeeddev.rsace.appTree.ServerConfigWriter;
import com.jeeddev.rsace.appTree.TreeBuilder;
import com.joeddev.rsace.preferences.ServerPreferences;

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
	    try
	    {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		TreeBuilder treeBuilder = TreeBuilder.getRsaceTreeInstance();
		ServerPreferences serverPreferences = new ServerPreferences ();
		String author = (String) serverPreferences.getAuthor();
		String email = (String) serverPreferences.getEmail();
		treeBuilder.buildAppTree(author, email);
		ServerConfigWriter scw = new ServerConfigWriter ("members");
		
		
		 MessageDialog.openInformation(
				window.getShell(),
				"Remote Synchrnization and Code Editor",
				"Welcome to Rsace. Now, you can synchronize and edit your file remotely from the Rsace's view and editor");
	    }
	    catch (Exception e)
	    {
	        System.out.println(e.getMessage());
	    }
		
		return null;
	}
}
