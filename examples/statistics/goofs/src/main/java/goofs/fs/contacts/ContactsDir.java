package goofs.fs.contacts;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;

public class ContactsDir extends Dir {

	protected IContacts contacts;

	public ContactsDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.contacts.contacts"), 0777);

		contacts = (IContacts) ServiceFactory.getService(IContacts.class);

		List<ContactEntry> entries = contacts.getContacts();

		for (ContactEntry entry : entries) {

			ContactDir contactDir = new ContactDir(this, entry);

			add(contactDir);
		}

	}

	public IContacts getContacts() {
		return contacts;
	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (isDir) {

			try {

				ContactEntry entry = getContacts().createContact(name);

				ContactDir contactDir = new ContactDir(this, entry);

				add(contactDir);

				return 0;
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
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
