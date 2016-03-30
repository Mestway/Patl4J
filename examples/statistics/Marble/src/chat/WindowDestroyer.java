package chat;
import jFrame.UI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowDestroyer extends WindowAdapter
{
	UI r;
	RoomList rl;
	
	public WindowDestroyer(UI r, RoomList rl){

		this.rl = rl;
		this.r = r;
	}
	public void windowClosing (WindowEvent e)
	{
		r.setVisible(false);//Y
		rl.sendMsg("exitroom/"+rl.roomtitle+"/"+rl.id);
		rl.setVisible(true);//Y
	}
}

