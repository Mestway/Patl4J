package goofs.contacts;

import goofs.GoofsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ServiceException;

public interface IContacts extends GoofsService {

	public abstract List<ContactEntry> getContacts() throws Exception;

	public abstract ContactEntry getContactById(String id) throws Exception;

	public abstract ContactEntry createContact(String name)
			throws ServiceException, IOException;

	public abstract void deleteContact(ContactEntry entry) throws Exception;

	public abstract ContactEntry updateContact(ContactEntry entry)
			throws Exception;

	public abstract void addContactPhoto(ContactEntry entry, byte[] photoData)
			throws ServiceException, IOException;

	public abstract boolean hasPhotoContent(ContactEntry entry);

	public abstract byte[] getContactPhotoContent(ContactEntry entry)
			throws Exception;

	public abstract InputStream getContactPhotoInputStream(ContactEntry entry)
			throws Exception;

}