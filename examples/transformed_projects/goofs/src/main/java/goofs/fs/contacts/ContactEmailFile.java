package goofs.fs.contacts;

import fuse.Errno;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.File;

import java.util.Iterator;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;

public class ContactEmailFile extends File {

	private Email email;

	public ContactEmailFile(Dir parent, Email email) throws Exception {

		super(parent, email.getRel().split("#")[1], 0777,
				email.getAddress() != null ? email.getAddress() : "");

		this.email = email;
	}

	public ContactEntry getContact() throws Exception {
		return ((ContactEmailDir) getParent()).getContact();
	}

	protected IContacts getContacts() {

		return ((ContactEmailDir) getParent()).getContacts();
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	@Override
	public int save() {

		try {

			ContactEntry contact = getContact();

			List<Email> emails = contact.getEmailAddresses();

			boolean found = false;

			for (Email e : emails) {

				if (e.getRel().equals(getEmail().getRel())) {

					e.setAddress(new String(getContent()));

					found = true;

					break;
				}

			}

			if (!found) {

				getEmail().setAddress(new String(getContent()));

				emails.add(getEmail());
			}

			getContacts().updateContact(contact);

			return 0;
		} catch (Exception e) {

			e.printStackTrace();

			return Errno.EROFS;
		}

	}

	@Override
	public int delete() {
		try {

			ContactEntry contact = getContact();

			Iterator<Email> emails = contact.getEmailAddresses().iterator();

			while (emails.hasNext()) {

				Email next = emails.next();

				if (next.getRel().equals(getEmail().getRel())) {

					emails.remove();

					break;

				}

			}
			getContacts().updateContact(contact);

			remove();

			return 0;
		} catch (Exception e) {

			e.printStackTrace();
		}

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
