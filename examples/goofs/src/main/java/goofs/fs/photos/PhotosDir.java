package goofs.fs.photos;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.fs.Dir;
import goofs.fs.Node;
import goofs.photos.IPicasa;

public class PhotosDir extends Dir {

	protected IPicasa picasa;

	public PhotosDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.photos.photos"), 0777);

		picasa = (IPicasa) ServiceFactory.getService(IPicasa.class);

		PublicAlbumDir publicDir = new PublicAlbumDir(this);

		PrivateAlbumDir privateDir = new PrivateAlbumDir(this);

		add(publicDir);

		add(privateDir);
	}

	public IPicasa getPicasa() {
		return picasa;
	}

	public void setPicasa(IPicasa picasa) {
		this.picasa = picasa;
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
