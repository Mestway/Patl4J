package utility;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class Utilities {
	
	public static PaintListener normalBorder = new PaintListener() {
		
		@Override
		public void paintControl(PaintEvent arg0) {
			GC gc = arg0.gc;
			gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(arg0.x, arg0.y, arg0.width -1, arg0.height-1);
			gc = null;
		}
	};
	
	public static PaintListener highlightBorder = new PaintListener() {
		
		@Override
		public void paintControl(PaintEvent arg0) {
			GC gc = arg0.gc;
			gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
			gc.drawRectangle(arg0.x, arg0.y, arg0.width -1, arg0.height-1);
			gc = null;
		}
	};
	
	
	public static FocusListener focusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent arg0) {
			((Text)arg0.getSource()).addPaintListener(highlightBorder);
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			((Text)arg0.getSource()).addPaintListener(normalBorder);
			
		}
	};
	
}
