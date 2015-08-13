package goofs.fs;

public class AddDirThread extends Thread {

	private Dir parent;

	private Class<? extends Dir> child;

	public AddDirThread(Dir parent, Class<? extends Dir> child) {

		this.parent = parent;
		this.child = child;

		setDaemon(true);
	}

	@Override
	public void run() {

		try {

			Dir childDir = (Dir) child.getConstructors()[0].newInstance(parent);

			parent.add(childDir);

		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}

}
