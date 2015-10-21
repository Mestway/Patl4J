package goofs.photos;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;

public class Picasa implements IPicasa {

	protected PicasawebService realService;

	protected static Map<String, String> mediaTypeMap = new HashMap<String, String>();

	static {

		mediaTypeMap.put("jpg", "image/jpeg");
		mediaTypeMap.put("jpeg", "image/jpeg");
		mediaTypeMap.put("bmp", "image/bmp");
		mediaTypeMap.put("gif", "image/gif");
		mediaTypeMap.put("png", "image/png");

	}

	public Picasa(String userName, String password)
			throws AuthenticationException {

		realService = new PicasawebService(APP_NAME);
		realService.setUserCredentials(userName, password);
		
	}

	public void acquireSessionTokens(String username, String password)
			throws AuthenticationException {

		getRealService().setUserCredentials(username, password);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#getRealService()
	 */
	public PicasawebService getRealService() {
		return realService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#getAlbums()
	 */
	public List<AlbumEntry> getAlbums() throws Exception {

		URL feedUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/default?kind=album");

		UserFeed myUserFeed = getRealService().getFeed(feedUrl, UserFeed.class);

		return myUserFeed.getAlbumEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#getAlbumById(java.lang.String)
	 */
	public AlbumEntry getAlbumById(String id) throws Exception {

		return (AlbumEntry) getRealService().getEntry(new URL(id),
				AlbumEntry.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#getPhotoById(java.lang.String)
	 */
	public PhotoEntry getPhotoById(String id) throws Exception {

		return (PhotoEntry) getRealService().getEntry(new URL(id),
				PhotoEntry.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#createAlbum(java.lang.String, java.lang.String)
	 */
	public AlbumEntry createAlbum(String title, String description)
			throws Exception {
		return createAlbum(title, description, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#createAlbum(java.lang.String, java.lang.String,
	 * boolean)
	 */
	public AlbumEntry createAlbum(String title, String description,
			boolean isPublic) throws Exception {

		AlbumEntry myAlbum = new AlbumEntry();

		myAlbum.setTitle(new PlainTextConstruct(title));
		myAlbum.setDescription(new PlainTextConstruct(description));
		myAlbum.setAccess(isPublic ? "public" : "private");

		return getRealService()
				.insert(
						new URL(
								"http://picasaweb.google.com/data/feed/api/user/default"),
						myAlbum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#updateAlbum(com.google.gdata.data.photos.AlbumEntry,
	 * java.lang.String, java.lang.String)
	 */
	public AlbumEntry updateAlbum(AlbumEntry album, String title,
			String description) throws Exception {
		album.setTitle(new PlainTextConstruct(title));
		album.setDescription(new PlainTextConstruct(description));

		return album.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#deleteAlbum(com.google.gdata.data.photos.AlbumEntry)
	 */
	public void deleteAlbum(AlbumEntry album) throws Exception {

		album.delete();
	}

	protected String getAlbumId(AlbumEntry album) {
		String[] parts = album.getId().split("/");
		return parts[parts.length - 1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#getPhotos(com.google.gdata.data.photos.AlbumEntry)
	 */
	public List<PhotoEntry> getPhotos(AlbumEntry album) throws Exception {

		URL feedUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/default/albumid/"
						+ getAlbumId(album));

		AlbumFeed feed = getRealService().getFeed(feedUrl, AlbumFeed.class);

		return feed.getPhotoEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.photos.IPicasa#getPhotosByTag(java.lang.String)
	 */
	public List<PhotoEntry> getPhotosByTag(String tag) throws Exception {

		URL feedUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/default");

		Query myQuery = new Query(feedUrl);
		myQuery.setStringCustomParameter("kind", "photo");
		myQuery.setStringCustomParameter("tag", tag);

		return getRealService().query(myQuery, AlbumFeed.class)
				.getPhotoEntries();

	}

	protected String getPhotoExtensionByName(String name) {
		String[] parts = name.split("\\.");
		return parts[parts.length - 1];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#createPhoto(com.google.gdata.data.photos.AlbumEntry,
	 * java.lang.String, java.lang.String, byte[])
	 */
	public PhotoEntry createPhoto(AlbumEntry album, String title,
			String description, java.io.File contents) throws Exception {

		URL albumPostUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/default/albumid/"
						+ getAlbumId(album));
		PhotoEntry myPhoto = new PhotoEntry();
		myPhoto.setTitle(new PlainTextConstruct(title));
		myPhoto.setDescription(new PlainTextConstruct(description));
		myPhoto.setClient(APP_NAME);

		String mediaType = mediaTypeMap.get(getPhotoExtensionByName(title));
		if (mediaType == null) {
			throw new IllegalArgumentException("unsupported photo name "
					+ title);
		}

		MediaSource myMedia = new MediaFileSource(contents, mediaType);
		myPhoto.setMediaSource(myMedia);

		return getRealService().insert(albumPostUrl, myPhoto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#updatePhoto(com.google.gdata.data.photos.PhotoEntry,
	 * java.lang.String, java.lang.String)
	 */
	public PhotoEntry updatePhoto(PhotoEntry photo, String title,
			String description) throws Exception {

		photo.setTitle(new PlainTextConstruct(title));
		photo.setDescription(new PlainTextConstruct(description));

		return photo.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#updatePhotoContent(com.google.gdata.data.photos.
	 * PhotoEntry, byte[])
	 */
	public PhotoEntry updatePhotoContent(PhotoEntry photo, java.io.File contents)
			throws Exception {
		MediaSource myMedia = new MediaFileSource(contents, "image/jpeg");

		photo.setMediaSource(myMedia);

		return photo.updateMedia(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#getPhotoContent(com.google.gdata.data.photos.PhotoEntry
	 * )
	 */
	public byte[] getPhotoContent(PhotoEntry photo) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		URL contentUrl = new URL(photo.getMediaContents().get(0).getUrl());
		InputStream in = contentUrl.openStream();
		BufferedOutputStream out = new BufferedOutputStream(baos);
		try {

			byte[] buff = new byte[256];
			int bytesRead = 0;

			while ((bytesRead = in.read(buff)) != -1) {
				out.write(buff, 0, bytesRead);

			}
		} finally {

			out.close();
			in.close();
		}

		return baos.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.photos.IPicasa#getPhotoInputStream(com.google.gdata.data.photos
	 * .PhotoEntry)
	 */
	public InputStream getPhotoInputStream(PhotoEntry photo) throws Exception {

		URL contentUrl = new URL(photo.getMediaContents().get(0).getUrl());
		InputStream in = contentUrl.openStream();

		return in;

	}

}
