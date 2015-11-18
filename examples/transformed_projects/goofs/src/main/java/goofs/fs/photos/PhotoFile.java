package goofs.fs.photos;

import fuse.Errno;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.fs.Dir;
import goofs.fs.DiskFile;
import goofs.photos.IPicasa;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;

public class PhotoFile extends DiskFile implements Identifiable, Fetchable {

	protected String photoId;

	public PhotoFile(Dir parent, PhotoEntry photo) throws Exception {

		super(parent, photo.getTitle().getPlainText(), 0777);

		try {
			setContent(getPicasa().getPhotoInputStream(photo));
		} catch (Exception e) {
			e.printStackTrace();
		}

		setPhotoId(photo.getSelfLink().getHref());

	}

	public PhotoFile(Dir parent, String name) throws Exception {

		super(parent, name, 0777);

	}

	public String getId() {
		return getPhotoId();
	}

	protected String getPhotoId() {
		return photoId;
	}

	protected void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public PhotoEntry getPhoto() throws Exception {

		return (PhotoEntry) getPicasa().getPhotoById(getPhotoId());

	}

	public Object fetch() throws Exception {

		return getPhoto();

	}

	protected IPicasa getPicasa() {

		return ((PhotosDir) getParent().getParent().getParent()).getPicasa();
	}

	protected AlbumEntry getAlbum() throws Exception {

		return ((AlbumDir) getParent()).getAlbum();
	}

	@Override
	public int save() {
		try {

			if (getPhotoId() == null) {
				PhotoEntry e = getPicasa().createPhoto(getAlbum(), name, name,
						getDisk());
				setPhotoId(e.getId());

			} else {
				getPicasa().updatePhotoContent(getPhoto(), getDisk());
			}

			return 0;

		} catch (Exception e) {
			e.printStackTrace();

			return Errno.ENOENT;

		}

	}

	@Override
	public int delete() {

		try {
			getPhoto().delete();

			remove();

			return 0;
		} catch (Exception e) {
			return Errno.ENOENT;
		}

	}

	@Override
	public int rename(Dir newParent, String name) {

		try {
			if (getParent() == newParent) {

				getPicasa().updatePhoto(getPhoto(), name, name);

				setName(name);

			}

			else {

				int result = newParent.createChildFromExisting(name, this);

				if (result == 0) {
					return delete();
				}

				else {
					return result;
				}

			}

			return 0;

		} catch (Exception e) {
			return Errno.ENOENT;
		}

	}
}
