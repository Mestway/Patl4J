package chat;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import jFrame.UI;


public class WindowDestroyer implements DisposeListener
{
	UI r;
	RoomList rl;
	
	public WindowDestroyer(UI r, RoomList rl){

		this.rl = rl;
		this.r = r;
	}

	@Override
	public void widgetDisposed(DisposeEvent arg0) {
		r.setVisible(false);
		rl.sendMsg("exitroom/"+rl.roomtitle+"/"+rl.id);
		rl.setVisible(true);
	}
}

