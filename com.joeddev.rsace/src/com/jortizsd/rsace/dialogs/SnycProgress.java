package com.jortizsd.rsace.dialogs;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.jortizsd.rsace.Task;

public class SnycProgress extends Dialog {

	protected Object result;
	protected Shell shell;
	protected ProgressBar progressBar;
	Label lblProcesses;
	final Thread countThread = null;
	final String [] initProcessesNames;
	final int [] initProcessesTimes;
	private Display display;
	static int counter;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SnycProgress(Shell parent, int style) {
		super(parent, style);
	    setText("Rsace Resources Synchronization");
	    initProcessesNames = new String [14];
	    initProcessesTimes = new int [14];
	    
	    
	    
	}

	/**
	 * Open the dialog.
	 * @return the result
	 * @throws InterruptedException 
	 */
	public Object open() throws InterruptedException {
		
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		// Work done in background by progressBar
		
		for (int i =0; i<=100; i++)
		{
			
			try 
			{
				Thread.sleep(10);
				
				
			}
			catch (Throwable th)
			{
				
			}
			progressBar.setSelection(i);
			if (progressBar.getSelection() == 100)
				shell.dispose();
		}
		
		
	    
		
		
        
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
		
		lblProcesses = new Label(shell, SWT.NONE);
		lblProcesses.setBounds(20, 54, 355, 14);
		lblProcesses.setText("Processes");
		

	}
	 
}
