package goofs.fs;

import fuse.Errno;

public class SimpleDir extends Dir {

	public SimpleDir(Dir parent, String name) {

		super(parent, name, 0777);
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
	public int delete() {
		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {
		return Errno.EROFS;
	}
	
	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

}
