package goofs.fs.contacts;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.File;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;

public class ContactNotesFile extends File {

	public ContactNotesFile(Dir parent, ContactEntry contact) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.contacts.notes"), 0777, (contact
				.getContent() == null) ? "" : contact.getTextContent()
				.getContent().getPlainText());

	}

	public ContactNotesFile(Dir parent, String name) throws Exception {

		super(parent, name, 0777, "");

	}

	protected IContacts getContacts() {

		return ((ContactsDir) getParent().getParent()).getContacts();
	}

	public ContactEntry getContact() throws Exception {
		return ((ContactDir) getParent()).getContact();
	}

	@Override
	public int save() {

		try {

			ContactEntry contact = getContact();

			contact
					.setContent(new PlainTextConstruct(new String(getContent())));

			getContacts().updateContact(contact);

			return 0;

		} catch (Exception e) {

			e.printStackTrace();

			return Errno.EROFS;
		}

	}

	@Override
	public int delete() {
		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {

		if (getParent() == newParent) {

			setName(name);
		}

		return 0;

	}

}
