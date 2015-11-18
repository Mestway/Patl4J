package goofs.fs.contacts;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.SimpleFile;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;

public class ContactNotesTempFile extends SimpleFile {

	public ContactNotesTempFile(Dir parent, String name) throws Exception {
		super(parent, name);

	}

	@Override
	public int rename(Dir newParent, String name) {

		int rt = super.rename(newParent, name);

		if (rt == 0) {

			if (GoofsProperties.INSTANCE.getProperty("goofs.contacts.notes")
					.equals(getName())) {

				try {

					IContacts contacts = ((ContactsDir) getParent().getParent())
							.getContacts();

					ContactEntry contact = ((ContactDir) getParent())
							.getContact();

					contact.setContent(new PlainTextConstruct(new String(
							getContent())));

					contacts.updateContact(contact);

					Dir parent = getParent();

					remove();

					parent.add(new ContactNotesFile(parent, contact));

					return 0;

				} catch (Exception e) {

					e.printStackTrace();

					return Errno.EROFS;
				}
			}

		}

		return rt;

	}

}
