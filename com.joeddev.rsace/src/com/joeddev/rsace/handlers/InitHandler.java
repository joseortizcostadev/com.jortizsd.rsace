package com.joeddev.rsace.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import com.jeeddev.rsace.appTree.TreeBuilder;

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
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		TreeBuilder treeBuilder = TreeBuilder.getRsaceTreeInstance();
		treeBuilder.buildResourcesDir();
		MessageDialog.openInformation(
				window.getShell(),
				"Remote Synchrnization and Code Editor",
				"Welcome to Rsace. Now, you can synchronize and edit your file remotely from the Rsace's view and editor");
		return null;
	}
}
