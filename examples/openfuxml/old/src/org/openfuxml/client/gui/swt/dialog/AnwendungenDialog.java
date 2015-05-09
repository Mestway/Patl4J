package org.openfuxml.client.gui.swt.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.openfuxml.client.gui.swt.OpenFuxmlClient;

/**
 * @author Andrea Frank
 */
public class AnwendungenDialog extends Dialog
{
	private Shell shell;

	private Text textDateityp;
	private Label labelAnwendung;
	private Button BtnAnwendung;
	private Button BtnDownload;

	private Button BtnOK;
	private Button BtnCancel;
	
	private String Dateityp; 
	private String Anwendung;
	private boolean checked;
	
	private String sErg[];
	
	public AnwendungenDialog(Shell parent, boolean checked, String Dateityp, String Anwendung)
	{
		super(parent, 0);
		
		this.Dateityp = Dateityp;
		this.Anwendung = Anwendung;
		this.checked = checked;
		sErg = new String[] {"0", "", ""};
	}
	
	public String[] open()
	{
		final Shell parent = this.getParent();
		
		shell = new Shell(parent, (SWT.DIALOG_TRIM) | SWT.APPLICATION_MODAL);
		
		shell.setText("Anwendung einrichten");
		
		initGUI();

		shell.pack();
		
		textDateityp.setText(Dateityp);
		labelAnwendung.setText(Anwendung);
		BtnDownload.setSelection(checked);

		shell.open();
		
		final Display display = parent.getDisplay();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		
		return sErg;
	}

	/**
	* Initializes the GUI.
	*/
	private void initGUI()
	{
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 20;
			shell.setLayout(layout);
		}
		{
			Label label = new Label(shell, SWT.NONE);
			label.setText("Dateityp:");
		}
		{
			textDateityp = new Text(shell, SWT.BORDER);
			textDateityp.setText("");

			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			textDateityp.setLayoutData(data);
		}
		{
			Label labelDummy = new Label(shell, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Label label = new Label(shell, SWT.NONE);
			label.setText("Anwendung:");
		}
		{
			labelAnwendung = new Label(shell, SWT.NONE);

			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.widthHint = 500;
			labelAnwendung.setLayoutData(data);
		}
		{
			BtnAnwendung = new Button(shell, SWT.PUSH | SWT.CENTER);
			BtnAnwendung.setText("...");
			
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			BtnAnwendung.setLayoutData(data);

			BtnAnwendung.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					FileDialog dialog = new FileDialog(shell);
					dialog.setText(OpenFuxmlClient.Title);
					dialog.setFilterExtensions(new String[] {"*.exe"});
					dialog.setFilterPath(labelAnwendung.getText());
					String dirname = dialog.open();
					if (dirname!=null)
					{
						labelAnwendung.setText(dirname);
					}
				}
			});
		}
		{
			Label labelDummy = new Label(shell, SWT.NONE);
			labelDummy.setText("");
		}
		{
			BtnDownload = new Button(shell, SWT.CHECK);
			BtnDownload.setText("Download");
		}
		{
			Label labelDummy = new Label(shell, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Composite cButtons = new Composite(shell, SWT.NONE);
	        GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.horizontalSpacing = 20;
			layout.makeColumnsEqualWidth = true;
			cButtons.setLayout(layout);

			GridData data1 = new GridData();
			data1.horizontalSpan = 3;
			data1.horizontalAlignment = GridData.CENTER;
			cButtons.setLayoutData(data1);
			{
				BtnOK = new Button(cButtons, SWT.PUSH | SWT.CENTER);
				BtnOK.setText("   OK   ");
				BtnOK.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						btnOK();
					}
				});

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				BtnOK.setLayoutData(data);
			}
			{
				BtnCancel = new Button(cButtons, SWT.PUSH | SWT.CENTER);
				BtnCancel.setText("   Abbrechen   ");
				BtnCancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						btnCancel();
					}
				});

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				BtnCancel.setLayoutData(data);
			}
		}
	}
	
	public void btnOK()
	{
		// Plausiprüfung für den Dateityp
		if (textDateityp.getText().length() <= 1)
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Geben Sie bitte einen Dateityp an (z. B.: \".pdf\")."); 
			messageBox.open();
			textDateityp.setFocus();
		}
		else if (textDateityp.getText().charAt(0) != '.')
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Geben Sie beim Dateityp als erstes bitte einen Punkt an (z. B.: \".pdf\")."); 
			messageBox.open();
			textDateityp.setFocus();
		}
		else if (textDateityp.getText().lastIndexOf('.') != 0)
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Geben Sie beim Dateityp bitte nur einen Punkt an (z. B.: \".pdf\")."); 
			messageBox.open();
			textDateityp.setFocus();
		}
		else if (labelAnwendung.getText().equals(""))
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("W�hlen Sie bitte eine Anwendung aus."); 
			messageBox.open();
			BtnAnwendung.setFocus();
		}
		else
		{
			if (BtnDownload.getSelection())
			{
				sErg[0] = "1";
			}
			sErg[1] = textDateityp.getText();
			sErg[2] = labelAnwendung.getText();
			shell.close();
		}
	}

	public void btnCancel()
	{
		shell.close();
	}
}
