package org.openfuxml.client.gui.swt.composites;

import java.io.FileNotFoundException;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.openfuxml.client.util.ImgCanvas;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

public class BrowserComposite extends Composite
{
	final static Logger logger = LoggerFactory.getLogger(ProjektComposite.class);
	
	private Image imgBtnBack,imgBtnForward,imgBtnRefresh,imgBtnStop;		
	
	private Button btnBack,btnForward,btnRefresh,btnStop;
	private Text textURL;
	
	private ImgCanvas imgIconRotation;
	
	private Browser browser;
	
	private Label labelStatus;
	
	private ProgressBar progressBar;
	private final String ofxNoRotate,ofxRotate;

	private Composite parent;
	
	public BrowserComposite(Composite parent, Configuration config)
	{
		super(parent, SWT.NONE);
		this.parent=parent;
		String browserIconDir = config.getString("icons/browser/@dir");
		ofxNoRotate = browserIconDir+"/"+config.getString("icons/browser/icon[@type='ofx' and @rotating='false']");
		ofxRotate = browserIconDir+"/"+config.getString("icons/browser/icon[@type='ofx' and @rotating='true']");
		
		ImageResourceLoader irl = new ImageResourceLoader();
		
		try
		{
			String res = browserIconDir+"/"+config.getString("icons/browser/icon[@type='go-previous' and @enabled='true']");
			Image img = irl.search(this.getClass().getClassLoader(), res, getDisplay());
			imgBtnBack = new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			
			res = browserIconDir+"/"+config.getString("icons/browser/icon[@type='go-next' and @enabled='true']");
			img = irl.search(this.getClass().getClassLoader(), res, getDisplay());
			imgBtnForward = new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			
			res = browserIconDir+"/"+config.getString("icons/browser/icon[@type='refresh' and @enabled='true']");
			img = irl.search(this.getClass().getClassLoader(), res, getDisplay());
			imgBtnRefresh = new Image(null, img.getImageData(), img.getImageData().getTransparencyMask());
			
			res = browserIconDir+"/"+config.getString("icons/browser/icon[@type='stop' and @enabled='true']");
			img = irl.search(this.getClass().getClassLoader(), res, getDisplay());
			imgBtnStop = new Image(null, img.getImageData(), img.getImageData().getTransparencyMask()); 
			
		}
		catch (FileNotFoundException e) {logger.error("",e);}
			
		{
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.verticalSpacing = 10;
			layout.horizontalSpacing = 10;
			layout.numColumns = 6;
			this.setLayout(layout);
		}
		
		{
			btnBack = new Button(this, SWT.NONE);
			btnBack.setToolTipText("Back");
			btnBack.setImage(imgBtnBack);
			btnBack.setBackground(this.getBackground());

			btnBack.setEnabled(false);
			
			btnBack.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.back();
					}
				});
		}
		{
			btnForward = new Button(this, SWT.NONE);
			btnForward.setToolTipText("Forward");
			btnForward.setImage(imgBtnForward);
			btnForward.setBackground(this.getBackground());

			btnForward.setEnabled(false);

			btnForward.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.forward();
					}
				});
		}
		{
			btnRefresh = new Button(this, SWT.NONE);
			btnRefresh.setToolTipText("Refresh");
			btnRefresh.setImage(imgBtnRefresh);
			btnRefresh.setBackground(this.getBackground());
			
			btnRefresh.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.refresh();
					}
				});
		}
		{
			btnStop = new Button(this, SWT.NONE);
			btnStop.setToolTipText("Stop");
			btnStop.setImage(imgBtnStop);
			btnStop.setBackground(this.getBackground());

			btnStop.setEnabled(false);
			
			btnStop.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					browser.stop();
					}
				});
		}
		{
			textURL = new Text(this, SWT.SINGLE | SWT.BORDER);
			textURL.setText("URL");
			
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				textURL.setLayoutData(data);
			}
			
			textURL.addSelectionListener(new SelectionAdapter()
				{
					public void widgetDefaultSelected(SelectionEvent event)
					{
						System.out.println("ENTER");
						browser.setUrl(textURL.getText());
					}
				});
		}
		{
			imgIconRotation = new ImgCanvas(this, ofxNoRotate);
			GridData data = new GridData();
			data.widthHint = 40;
			data.heightHint = 40;
			imgIconRotation.setLayoutData(data);
			imgIconRotation.setBackground(this.getBackground());
		}
		{
			browser = new Browser(this, SWT.NONE);
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.grabExcessVerticalSpace   = true;
				data.horizontalAlignment = GridData.FILL;
				data.verticalAlignment   = GridData.FILL;
				data.horizontalSpan = 6;
				browser.setLayoutData(data);
			}
						
			browser.addLocationListener(new LocationListener(){
				public void changing(LocationEvent event)
				{
System.out.println("LocationListener---changing: "+ event.location);
					if (event.location.startsWith("xmetal://"))
					{
						System.out.println("TREFFER: xmetal://");
						System.out.println("starte Applikation mit "+event.location);

						try
						{
							Runtime.getRuntime().exec("C:/Programme/SoftQuad/XMetaL 3/xmetal31.exe");
						}
						catch (Exception e) {e.printStackTrace();}
					}
					else
					{
						textURL.setText(browser.getUrl());
						
						imgIconRotation.setImage(ofxRotate);
						
						btnBack.setEnabled(browser.isBackEnabled());
						btnForward.setEnabled(browser.isForwardEnabled());
					}
				}
				public void changed(LocationEvent event)
				{
System.out.println("LocationListener---changed: "+ event.location);
					textURL.setText(browser.getUrl());
					
					imgIconRotation.setImage(ofxNoRotate);
					
					btnBack.setEnabled(browser.isBackEnabled());
					btnForward.setEnabled(browser.isForwardEnabled());
				}
			});

			browser.addProgressListener(new ProgressListener(){
				public void completed(ProgressEvent event)
				{
System.out.println("ProgressListener---completed: "+ event.current + " von " + event.total + " --- "+ event.toString());
					progressBar.setMaximum(event.total);
					progressBar.setSelection(event.current);
				}
				public void changed(ProgressEvent event)
				{
System.out.println("ProgressListener---changed: "+ event.current + " von " + event.total + " --- "+ event.toString());					
					progressBar.setMaximum(event.total);
					progressBar.setSelection(event.current);
				}
			});
			
			browser.addStatusTextListener(new StatusTextListener(){
				public void changed(StatusTextEvent event)
				{
					labelStatus.setText(event.text);		
				}
			});
			
			browser.addOpenWindowListener(new OpenWindowListener(){
				public void open(WindowEvent event) {
					// Der folgende Code ist notwendig, damit ein neues Browser-Fenster
					// geöffnet wird.
					// Allerdings wird die Debug-Meldung nicht ausgegeben.
					// WARUM???
					System.out.println("EventBrowser: " +  event.browser.getUrl());					
				}
			});
		}

		{
			labelStatus = new Label(this, SWT.BORDER);
			labelStatus.setBackground(this.getBackground());
			labelStatus.setText("Status");		
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				data.horizontalSpan = 5;
				labelStatus.setLayoutData(data);
			}
		}
		{
			progressBar = new ProgressBar(this, SWT.BORDER);
			progressBar.setMinimum(0);
			progressBar.setMaximum(1000);
			progressBar.setSelection(300);
			
		}
	}
	
	public void open(final URL url)
	{
		this.getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				if (!parent.isDisposed())
				{				
					browser.setUrl(url.toString());
					browser.refresh();
				} 
			}
		});
	}
	
	public void setText(String text)
	{
		browser.setText(text);
	}
}
