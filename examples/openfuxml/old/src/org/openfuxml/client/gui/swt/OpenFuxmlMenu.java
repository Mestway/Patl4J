package org.openfuxml.client.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class OpenFuxmlMenu
{
	Menu menu;
	
	public OpenFuxmlMenu(Shell shell)
	{
		menu = new Menu(shell, SWT.BAR);
		menuClient();
		menuExtras();
		menuHelp();
		shell.setMenuBar(menu);
	}
	
	private void menuClient()
	{
		MenuItem miClient = new MenuItem(menu, SWT.CASCADE);
		miClient.setText("Client");

		Menu mClient = new Menu(miClient);

		MenuItem miClientClose = new MenuItem(mClient, SWT.CASCADE);
		miClientClose.setText("Beenden");
		miClientClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
		//		ClientBeenden();
			}
			});
		
		miClient.setMenu(mClient);
	}
			
	private void menuExtras()
	{
		MenuItem miExtras = new MenuItem(menu, SWT.CASCADE);
		miExtras.setText("Extras");
		
		Menu menuExtras = new Menu(miExtras);
	
		MenuItem menuItemExtrasEinstellungen = new MenuItem(menuExtras, SWT.CASCADE);
		menuItemExtrasEinstellungen.setText("Einstellungen ...");
		menuItemExtrasEinstellungen.addSelectionListener(new SelectionAdapter() {

		public void widgetSelected(SelectionEvent evt) {
		//		Einstellungen();
				
/*				if (myProperties.getProperty("Beenden") == null)
				{
					if (myProperties.getProperty("Login") != null)
					{
						// Nur wenn das Login-Flag gesetzt ist, wird "login()" aufgerufen!
										login();
					}
				}
				myProperties.remove("Beenden");
				myProperties.remove("Login");
*/			}
		});
		
/* TODO Andy: später wieder in Betrieb nehmen und testen					
					{
						menuItemExtrasNeuesProjekt = new MenuItem(menuExtras, SWT.CASCADE);
						menuItemExtrasNeuesProjekt.setText("Neues Projekt ...");
						menuItemExtrasNeuesProjekt.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								ExtrasNeuesProjekt();
							}
						});
					}
*/					
			miExtras.setMenu(menuExtras);
	}
	
	private void menuHelp()
	{
		MenuItem menuItemHilfe = new MenuItem(menu, SWT.CASCADE);
		menuItemHilfe.setText("Hilfe");
		
		Menu menuHilfe = new Menu(menuItemHilfe);

		MenuItem menuItemHilfeInfoUeber = new MenuItem(menuHilfe, SWT.CASCADE);
		menuItemHilfeInfoUeber.setText("Info über ...");
		menuItemHilfeInfoUeber.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
	//				HilfeInfoUeber();
				}
				});
		menuItemHilfe.setMenu(menuHilfe);
	}
}

