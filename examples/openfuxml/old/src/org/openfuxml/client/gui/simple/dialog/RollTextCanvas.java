package org.openfuxml.client.gui.simple.dialog;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * The class RollTextCanvas implements a canvas with text.
 * When the user clicks on this canvas, the text scrolled.
 * After clicking again, the scrolling stops.
 *   
 * @author Andrea Frank
 */
public class RollTextCanvas extends Canvas implements Runnable
{
	private String sRollText;
	
	private Shell shell;
	
	private Thread threadRollText;
	private boolean threadRollTextActive;
	private int threadRollTextCounter;
	
	public RollTextCanvas(Composite arg0, int arg1, String sRollText)
	{
		super(arg0, arg1);
		
		this.sRollText = sRollText;
		
		shell = this.getShell();

		threadRollText = null;
		threadRollTextCounter = 0;
		threadRollTextActive = false;

		this.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent evt) {
				if (threadRollTextActive)
				{
					stopThread();
				}
				else
				{
					startThread();
				}
			}
		});

		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent evt) {
				/* Test to disable the screen-jitter. 
				   But this doesn't work.   :-( 
				Display display = evt.display;
				GC gc = evt.gc;
				// Das Widget, das das Ereignis verursacht hat
				Composite source = (Composite) evt.widget;
				// Größe der nutzbaren Fläche
				Rectangle rect = source.getClientArea();
				// Puffer aufbauen
				Image buffer = new Image(display,rect.width,rect.height);
				// Neuer Grafikkontext für Puffer
				GC bufferGC = new GC(buffer);
				paint(bufferGC);
				// Nun das gepufferte Bild auf Canvas zeichnen
				gc.drawImage(buffer,0,0);
				// Den Puffer entsorgen
				buffer.dispose();
				*/
				paint(evt.gc);
			}
		});
		
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent evt) {
				// Stop the thread.
				stopThread();
			}
		});
	}
	
	/**
	 * The method paint draws the text on the canvas, depending on the scrolling value.
	 * @param gc
	 */
	private void paint(GC gc)
	{
		int HeightText = gc.textExtent(sRollText).y;
		
		if (HeightText < threadRollTextCounter)
		{
			threadRollTextCounter = -this.getSize().y;
		}
		
		gc.drawText(sRollText, 0, -threadRollTextCounter);
	}

	public void startThread()
	{
		// Start the thread ...
		threadRollText = new Thread(this);
		threadRollText.start();
		threadRollTextActive = true;
	}
	
	public void stopThread()
	{
		// Stop the thread ...
		threadRollTextActive = false;
		threadRollText = null;
	}

	public void run()
	{  
		while (threadRollTextActive)
		{
			threadRollTextCounter++;

			this.getDisplay().asyncExec(new Runnable()
			{
				public void run()
				{
					if (!shell.isDisposed())
					{
				    	redraw();
					} // if
				}
			});

			try 
			{
    			Thread.sleep(100);
    		} 
    		catch (InterruptedException e) {}
    	}
	}
}