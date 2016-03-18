package main;


import ui.MainWindow;

public class Main {

	
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				MainWindow mainWindow=new MainWindow();
			}
		});
		t.start();

		
	}

}
