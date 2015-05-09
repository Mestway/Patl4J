package goofs.contacts;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.Query;
import com.google.gdata.client.Service;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;

public class Contacts implements IContacts {

	protected ContactsService realService;

	public Contacts(String username, String password)
			throws AuthenticationException {

		realService = new ContactsService(APP_NAME);
		realService.setUserCredentials(username, password);

	}

	public ContactsService getRealService() {
		return realService;
	}

	public void acquireSessionTokens(String username, String password)
			throws AuthenticationException {

		getRealService().setUserCredentials(username, password);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.contacts.IContacts#getContacts()
	 */
	public List<ContactEntry> getContacts() throws Exception {

		URL feedUrl = new URL(
				"http://www.google.com/m8/feeds/contacts/default/full");

		Query allContacts = new Query(feedUrl);
		allContacts.setMaxResults(1000);

		ContactFeed resultFeed = getRealService().getFeed(allContacts,
				ContactFeed.class);

		return resultFeed.getEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.contacts.IContacts#getContactById(java.lang.String)
	 */
	public ContactEntry getContactById(String id) throws Exception {

		return getRealService().getEntry(
				new URL(id.replace("/base/", "/full/")), ContactEntry.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.contacts.IContacts#createContact(java.lang.String)
	 */
	public ContactEntry createContact(String name) throws ServiceException,
			IOException {
		// Create the entry to insert
		ContactEntry contact = new ContactEntry();
		contact.setTitle(new PlainTextConstruct(name));
		// Ask the service to insert the new entry
		URL postUrl = new URL(
				"http://www.google.com/m8/feeds/contacts/default/full");
		return getRealService().insert(postUrl, contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#deleteContact(com.google.gdata.data.contacts
	 * .ContactEntry)
	 */
	public void deleteContact(ContactEntry entry) throws Exception {

		URL deleteUrl = new URL(entry.getEditLink().getHref());
		getRealService().delete(deleteUrl);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#updateContact(com.google.gdata.data.contacts
	 * .ContactEntry)
	 */
	public ContactEntry updateContact(ContactEntry entry) throws Exception {

		URL editUrl = new URL(entry.getEditLink().getHref());
		return getRealService().update(editUrl, entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#addContactPhoto(com.google.gdata.data.contacts
	 * .ContactEntry, byte[])
	 */
	public void addContactPhoto(ContactEntry entry, byte[] photoData)
			throws ServiceException, IOException {

		Link editPhotoLink = entry.getContactEditPhotoLink();
		URL editUrl = new URL(editPhotoLink.getHref());

		Service.GDataRequest request = getRealService().createRequest(
				Service.GDataRequest.RequestType.UPDATE, editUrl,
				new ContentType("image/jpg"));

		OutputStream requestStream = request.getRequestStream();
		requestStream.write(photoData);

		request.execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#hasPhotoContent(com.google.gdata.data.contacts
	 * .ContactEntry)
	 */
	public boolean hasPhotoContent(ContactEntry entry) {

		return entry.getContactPhotoLink() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#getContactPhotoContent(com.google.gdata.data
	 * .contacts.ContactEntry)
	 */
	public byte[] getContactPhotoContent(ContactEntry entry) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Link photoLink = entry.getContactPhotoLink();
		if (photoLink != null) {
			InputStream in = getRealService().getStreamFromLink(photoLink);
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
		}
		return baos.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * goofs.contacts.IContacts#getContactPhotoInputStream(com.google.gdata.
	 * data.contacts.ContactEntry)
	 */
	public InputStream getContactPhotoInputStream(ContactEntry entry)
			throws Exception {
		Link photoLink = entry.getContactPhotoLink();
		if (photoLink != null) {
			return getRealService().getStreamFromLink(photoLink);

		} else {
			return null;
		}
	}
}
