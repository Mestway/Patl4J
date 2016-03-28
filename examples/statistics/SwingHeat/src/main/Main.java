package main;

import ui.MainWindow;

public class Main {

	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				MainWindow mainWindow=new MainWindow();
				
			}
		});
		
	}

}
