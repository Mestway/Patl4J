package goofs.fs.photos;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.fs.Dir;
import goofs.fs.Node;
import goofs.fs.SimpleFile;
import goofs.photos.IPicasa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;

public class AlbumDir extends Dir implements Identifiable, Fetchable,
		EntryContainer {

	protected String albumId;

	protected Set<String> entryIds = new HashSet<String>();

	public AlbumDir(Dir parent, AlbumEntry album) throws Exception {

		super(parent, album.getTitle().getPlainText(), 0777);

		setAlbumId(album.getSelfLink().getHref());

		List<PhotoEntry> photos = getPicasa().getPhotos(album);

		for (PhotoEntry photo : photos) {

			PhotoFile photoFile = new PhotoFile(this, photo);

			add(photoFile);

			getEntryIds().add(photo.getId());
		}
	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	public void addNewEntryById(String entryId) throws Exception {

		PhotoEntry photo = getPicasa().getPhotoById(entryId);

		PhotoFile photoFile = new PhotoFile(this, photo);

		add(photoFile);

		getEntryIds().add(photo.getId());

	}

	public Set<String> getCurrentEntryIds() throws Exception {

		Set<String> ids = new HashSet<String>();

		List<PhotoEntry> photos = getPicasa().getPhotos(getAlbum());

		for (PhotoEntry photo : photos) {

			ids.add(photo.getId());

		}
		return ids;
	}

	public String getId() {
		return getAlbumId();
	}

	protected String getAlbumId() {
		return albumId;
	}

	protected void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	protected IPicasa getPicasa() {

		return ((PhotosDir) getParent().getParent()).getPicasa();
	}

	public AlbumEntry getAlbum() throws Exception {

		return getPicasa().getAlbumById(getAlbumId());

	}

	public Object fetch() throws Exception {

		return getAlbum();

	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (isDir)
			return Errno.EROFS;
		try {

			PhotoFile photoFile = new PhotoFile(this, name);

			add(photoFile);

			return 0;

		} catch (Exception e) {

			e.printStackTrace();

			return Errno.EROFS;
		}
	}

	@Override
	public int createTempChild(String name) {
		try {
			SimpleFile f = new SimpleFile(this, name);

			add(f);

			return 0;

		} catch (Exception e) {

			e.printStackTrace();

			return Errno.EROFS;
		}
	}

	@Override
	public int delete() {

		try {
			getPicasa().deleteAlbum(getAlbum());

			remove();

			return 0;
		} catch (Exception e) {

			return Errno.EROFS;
		}
	}

	@Override
	public int rename(Dir newParent, String name) {

		if (newParent == getParent()) {
			try {
				getPicasa().updateAlbum(getAlbum(), name, name);

				setName(name);

				return 0;
			}

			catch (Exception e) {

				e.printStackTrace();

				return Errno.EROFS;
			}
		} else {
			return Errno.EACCES;
		}
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		try {
			if (child instanceof PhotoFile) {

				PhotoFile photoFile = (PhotoFile) child;

				PhotoEntry newPhoto = getPicasa().createPhoto(getAlbum(), name,
						name, photoFile.getDisk());

				PhotoFile newChild = new PhotoFile(this, newPhoto);

				add(newChild);

				return 0;
			} else {
				return Errno.EACCES;
			}
		} catch (Exception e) {
			return Errno.EROFS;
		}
	}

}
