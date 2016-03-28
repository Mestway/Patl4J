package goofs.fs.photos;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.GoofsProperties;
import goofs.fs.Dir;
import goofs.fs.SimpleDir;
import goofs.photos.IPicasa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gdata.data.photos.AlbumEntry;

public class PrivateAlbumDir extends SimpleDir implements EntryContainer {

	protected Set<String> entryIds = new HashSet<String>();

	public PrivateAlbumDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.photos.private"));

		List<AlbumEntry> albums = getPicasa().getAlbums();

		for (AlbumEntry album : albums) {

			if ("private".equals(album.getAccess())) {

				add(new AlbumDir(this, album));

				getEntryIds().add(album.getId());

			}

		}

	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	public void addNewEntryById(String entryId) throws Exception {

		AlbumEntry album = getPicasa().getAlbumById(entryId);

		add(new AlbumDir(this, album));

		getEntryIds().add(album.getId());

	}

	public Set<String> getCurrentEntryIds() throws Exception {
		Set<String> ids = new HashSet<String>();

		List<AlbumEntry> albums = getPicasa().getAlbums();

		for (AlbumEntry album : albums) {

			if ("private".equals(album.getAccess())) {

				ids.add(album.getId());
			}

		}
		return ids;
	}

	protected IPicasa getPicasa() {

		return ((PhotosDir) getParent()).getPicasa();
	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (!isDir)
			return Errno.EROFS;

		try {

			AlbumEntry album = getPicasa().createAlbum(name, name, false);

			add(new AlbumDir(this, album));

			return 0;
		} catch (Exception e) {
			return Errno.EROFS;

		}
	}
}
