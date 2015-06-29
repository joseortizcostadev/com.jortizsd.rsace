package com.jortizsd.rsace.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class RsaceLog extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.jortizsd.rsace.views.RsaceLog";
	private Action action1;
	private Action action2;
    public static StyledText styledText;
    private static Display display;
	

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public RsaceLog() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) 
	{
		styledText = new StyledText(parent, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP | SWT.H_SCROLL);
		//styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
	}
    
	// Write Log
    public static void writeLog (String tag, String message, int contextColor)
    {
    	
    	// Font
    	Font font = new Font( Display.getDefault(), "Lucida Grande", 12, SWT.NORMAL);
    	// Text
    	int start = styledText.getCharCount();
    	String newText = "*** " + tag + ": " + message + "\n";
    	styledText.append(newText);
    	int end = styledText.getCharCount() - start;
    	// Sets error color
        StyleRange sr1 = new StyleRange();
        sr1.start = start;
        sr1.length = end;
        sr1.foreground = Display.getDefault().getSystemColor(contextColor);
        sr1.font = font;
        sr1.fontStyle = SWT.BOLD;
        styledText.setStyleRange(sr1);
        
    }
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}
}
