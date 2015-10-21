package goofs.fs;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.fs.blogger.BlogsDir;
import goofs.fs.calendar.CalendarsDir;
import goofs.fs.contacts.ContactsDir;
import goofs.fs.docs.DocsDir;
import goofs.fs.photos.PhotosDir;

public class RootDir extends Dir {

	public RootDir() throws Exception {

		super(null, "", 0777, "description", "ROOT directory");

		AddDirThread t;

		if (Boolean.TRUE.toString().equals(
				GoofsProperties.INSTANCE.getProperty("goofs.blogger.enabled"))) {

			t = new AddDirThread(this, BlogsDir.class);
			t.start();
		}

		if (Boolean.TRUE.toString().equals(
				GoofsProperties.INSTANCE.getProperty("goofs.photos.enabled"))) {
			t = new AddDirThread(this, PhotosDir.class);
			t.start();
		}

		if (Boolean.TRUE.toString().equals(
				GoofsProperties.INSTANCE.getProperty("goofs.contacts.enabled"))) {
			t = new AddDirThread(this, ContactsDir.class);
			t.start();
		}

		if (Boolean.TRUE.toString().equals(
				GoofsProperties.INSTANCE.getProperty("goofs.calendar.enabled"))) {
			t = new AddDirThread(this, CalendarsDir.class);
			t.start();
		}

		if (Boolean.TRUE.toString().equals(
				GoofsProperties.INSTANCE.getProperty("goofs.docs.enabled"))) {
			t = new AddDirThread(this, DocsDir.class);
			t.start();
		}

	}

	@Override
	public int delete() {
		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {
		return Errno.EROFS;
	}

	@Override
	public void remove() {
	}

	@Override
	public int createChild(String name, boolean isDir) {
		return Errno.EROFS;
	}

	@Override
	public int createTempChild(String name) {
		return Errno.EROFS;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

}
