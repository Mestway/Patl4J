package org.openfuxml.client.util;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

/**
 * ImgCanvas erzeugt ein Canvas, in dem ein Image angezeigt wird.
 *  
 * @author andy
 */
public class ImgCanvas extends Canvas
{
	final static Logger logger = LoggerFactory.getLogger(ImgCanvas.class);
	
	public final static int BEGIN  = 0;
	public final static int CENTER = 1;
	public final static int END    = 2;
	
	private Image img;
	
	private int x;
	private int y;
	
	private int horizontalAlignment;
	private int verticalAlignment;

	/**
	 * Konstruktor für die Klasse ImgCanvas.
	 * @param Parent
	 * 	- Das aufrufende Element
	 * @param Dateiname
	 * 	- Name der Grafik, die angezeigt werden soll.
	 * @param ha
	 * 	- Angabe für die horizontale Ausrichtung
	 * @param va
	 * 	- Angabe für die vertikale Ausrichtung
	 */
	public ImgCanvas(Composite Parent, String Dateiname, int ha, int va)
	{
		super(Parent, SWT.NONE);
		
		x = -1;
		y = -1;
		
		horizontalAlignment = ha;
		verticalAlignment = va;
		
		setImage(Dateiname);
     		
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent evt) {
				// Die Methode ImgCanvas.paint wird aufgerufen.
				paint(evt);
			}
		});
	}
	
	/**
	 * Konstruktor f�r die Klasse ImgCanvas.
	 * Die Ausrichtung der Grafik wird auf einen Defaultwert gesetzt.
	 * @param Parent
	 * 	- Das aufrufende Element
	 * @param Dateiname
	 * 	- Name der Grafik, die angezeigt werden soll.
	 */
	public ImgCanvas(Composite Parent, String Dateiname)
	{
		this (Parent, Dateiname, BEGIN, BEGIN);
	}
	
	private void paint(PaintEvent evt)
	{
		if ( (x==-1) || (y==-1) )
		{
			berechneKoordinaten();
		}
		// Ausgabe des Bildes
		evt.gc.drawImage(img, x, y);
	}
	
	private void berechneKoordinaten()
	{
		// berechnen der Koordinaten f�r die Ausgabe des Bildes
		switch (horizontalAlignment)
		{
			case BEGIN:
				x = 0;
				break;
			case CENTER:
				x = (getSize().x - img.getImageData().width)/2; 
				break;
			case END:
				x = getSize().x - img.getImageData().width; 
				break;
			default:
				x = 0;
				break;
		}

		switch (verticalAlignment)
		{
			case BEGIN:
				y = 0;
				break;
			case CENTER:
				y = (getSize().y - img.getImageData().height)/2; 
				break;
			case END:
				y = getSize().y - img.getImageData().height; 
				break;
			default:
				y = 0;
				break;
		}
	}
	
	public void setImage(String Dateiname)
	{
		try
		{
			ImageResourceLoader irl = new ImageResourceLoader();
			img = irl.search(this.getClass().getClassLoader(), Dateiname, getDisplay());
		}
		catch (FileNotFoundException e){logger.warn(e);}
        berechneKoordinaten();
		
		redraw();
	}
}
