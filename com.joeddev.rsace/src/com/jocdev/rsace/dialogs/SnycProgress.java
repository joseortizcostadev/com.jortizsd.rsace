package com.jocdev.rsace.dialogs;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class SnycProgress extends Dialog {

	protected Object result;
	protected Shell shell;
	protected ProgressBar progressBar;
	final Thread countThread = null;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SnycProgress(Shell parent, int style) {
		super(parent, style);
	    setText("Rsace Resources Synchronization");
	    
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		
		final int maximum = progressBar.getMaximum();
		new Thread() {
			@Override
			
			public void run() {
				for (final int[] i = new int[1]; i[0] <= maximum; i[0]++) {
				try {Thread.sleep (100);} catch (Throwable th) {}
				
					if (display.isDisposed()) return;
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
						
						if (progressBar.isDisposed ()) return;
							progressBar.setSelection(i[0]);
					    if (progressBar.getSelection() == 100)
					    	shell.dispose();
						}
						
					});
				}
			}
		}.start();
        
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}
		return result;
	}
	
	         

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(391, 105);
		shell.setLocation(575, 300);
		
		shell.setText(getText());
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(20, 32, 355, 16);
		
		Label lblSynchronizingResources = new Label(shell, SWT.NONE);
		lblSynchronizingResources.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.NORMAL));
		lblSynchronizingResources.setBounds(20, 10, 163, 16);
		lblSynchronizingResources.setText("Synchronizing Resources...");
		
		Label lblProcesses = new Label(shell, SWT.NONE);
		lblProcesses.setBounds(20, 54, 60, 14);
		lblProcesses.setText("Processes");
		

	}
	 
}
