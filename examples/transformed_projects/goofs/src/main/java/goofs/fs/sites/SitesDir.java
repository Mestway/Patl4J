package goofs.fs.sites;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.fs.Dir;
import goofs.fs.Node;
import goofs.sites.ISites;

public class SitesDir extends Dir {

	protected ISites sites;

	public SitesDir(Dir parent) throws Exception {
		super(parent,
				GoofsProperties.INSTANCE.getProperty("goofs.sites.sites"), 0777);

		sites = (ISites) ServiceFactory.getService(ISites.class);

	}
	
	public ISites getSites() {
		return sites;
	}

	public void setSites(ISites sites) {
		this.sites = sites;
	}

	@Override
	public int createChild(String name, boolean isDir) {
		return Errno.EROFS;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
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

}
