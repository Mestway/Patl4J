package goofs.photos;

import goofs.GoofsService;

import java.io.InputStream;
import java.util.List;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;

public interface IPicasa extends GoofsService {

	public abstract List<AlbumEntry> getAlbums() throws Exception;

	public abstract AlbumEntry getAlbumById(String id) throws Exception;

	public abstract PhotoEntry getPhotoById(String id) throws Exception;

	public abstract AlbumEntry createAlbum(String title, String description)
			throws Exception;

	public abstract AlbumEntry createAlbum(String title, String description,
			boolean isPublic) throws Exception;

	public abstract AlbumEntry updateAlbum(AlbumEntry album, String title,
			String description) throws Exception;

	public abstract void deleteAlbum(AlbumEntry album) throws Exception;

	public abstract List<PhotoEntry> getPhotos(AlbumEntry album)
			throws Exception;

	public abstract List<PhotoEntry> getPhotosByTag(String tag)
			throws Exception;

	public abstract PhotoEntry createPhoto(AlbumEntry album, String title,
			String description, java.io.File contents) throws Exception;

	public abstract PhotoEntry updatePhoto(PhotoEntry photo, String title,
			String description) throws Exception;

	public abstract PhotoEntry updatePhotoContent(PhotoEntry photo,
			java.io.File contents) throws Exception;

	public abstract byte[] getPhotoContent(PhotoEntry photo) throws Exception;

	public abstract InputStream getPhotoInputStream(PhotoEntry photo)
			throws Exception;

}