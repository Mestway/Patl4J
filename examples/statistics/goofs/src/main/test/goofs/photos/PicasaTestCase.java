package goofs.photos;

import goofs.BaseTestCase;

import java.util.List;

import com.google.gdata.data.photos.AlbumEntry;

public class PicasaTestCase extends BaseTestCase {
	
	protected IPicasa picasa;
	

	protected void setUp() throws Exception {
		super.setUp();
		
		picasa = new Picasa(getUsername(), getPassword());
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetAlbums() throws Exception {
		
		List<AlbumEntry> albums = picasa.getAlbums();
		
		assert(albums.size() >= 1);
		
		
	}

	public void testCreateAlbum() throws Exception {
		
		List<AlbumEntry> albums = picasa.getAlbums();
		
		assert(albums.size() >= 1);
		
		int numAlbums = albums.size();
		
		picasa.createAlbum("Test", "Test");
		
		albums = picasa.getAlbums();
		
		assert(albums.size() == numAlbums + 1);
	}

	public void testUpdateAlbum() {
		fail("Not yet implemented");
	}

	public void testDeleteAlbum() {
		fail("Not yet implemented");
	}

	public void testGetPhotos() {
		fail("Not yet implemented");
	}

	public void testGetPhotosByTag() {
		fail("Not yet implemented");
	}

	public void testCreatePhoto() {
		fail("Not yet implemented");
	}

	public void testUpdatePhoto() {
		fail("Not yet implemented");
	}

	public void testUpdatePhotoContent() {
		fail("Not yet implemented");
	}

}
