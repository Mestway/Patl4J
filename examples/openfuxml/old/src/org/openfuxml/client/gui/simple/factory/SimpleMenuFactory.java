package org.openfuxml.client.gui.simple.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.openfuxml.client.gui.simple.Client;

public class SimpleMenuFactory
{
	public static Menu createMenu(Shell shell, Client client)
	{
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		createMenuClient(menu,client);
		createMenuExtras(menu,client);
		createMenuHelp(menu,client);
		
		return menu;
	}

	private static void createMenuClient(Menu menu,final Client client)
	{
		MenuItem menuItemClient = new MenuItem(menu, SWT.CASCADE);
		menuItemClient.setText("Client");
		
		Menu menuClient = new Menu(menuItemClient);
		MenuItem menuItemClientBeenden = new MenuItem(menuClient, SWT.CASCADE);
		menuItemClientBeenden.setText("Beenden");
		menuItemClientBeenden.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				client.ClientBeenden();
			}
			});
	
		menuItemClient.setMenu(menuClient);
	}
	
	private static void createMenuExtras(Menu menu,final Client client)
	{
		MenuItem menuItemEinstellungen = new MenuItem(menu, SWT.CASCADE);
		menuItemEinstellungen.setText("Extras");
		{
			Menu menuEinstellungen = new Menu(menuItemEinstellungen);
			{
				MenuItem menuItemEinstellungenServer = new MenuItem(menuEinstellungen, SWT.CASCADE);
				menuItemEinstellungenServer.setText("Einstellungen ...");
				menuItemEinstellungenServer.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						client.Einstellungen();
					}
					});
			}
			menuItemEinstellungen.setMenu(menuEinstellungen);
		}
	}
	
	private static void createMenuHelp(Menu menu,final Client client)
	{
		MenuItem menuItemHilfe = new MenuItem(menu, SWT.CASCADE);
		menuItemHilfe.setText("Hilfe");
		{
			Menu menuHilfe = new Menu(menuItemHilfe);
			{
				MenuItem menuItemHilfeInfoUeber = new MenuItem(menuHilfe, SWT.CASCADE);
				menuItemHilfeInfoUeber.setText("Info über ...");
				menuItemHilfeInfoUeber.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						client.HilfeInfoUeber();
					}
					});
			}
			menuItemHilfe.setMenu(menuHilfe);
		}
	}

}
