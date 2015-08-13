package goofs.fs.contacts;

import fuse.Errno;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.File;

import java.util.Iterator;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.PostalAddress;

public class ContactAddressFile extends File {

	protected PostalAddress postal;

	public ContactAddressFile(Dir parent, PostalAddress postal)
			throws Exception {

		super(parent, postal.getRel().split("#")[1], 0777,
				postal.getValue() != null ? postal.getValue() : "");

		this.postal = postal;
	}

	public ContactEntry getContact() throws Exception {
		return ((ContactAddressDir) getParent()).getContact();
	}

	protected IContacts getContacts() {

		return ((ContactAddressDir) getParent()).getContacts();
	}

	protected PostalAddress getPostal() {
		return postal;
	}

	protected void setPostal(PostalAddress postal) {
		this.postal = postal;
	}

	@Override
	public int save() {

		try {

			ContactEntry contact = getContact();

			List<PostalAddress> postals = contact.getPostalAddresses();

			boolean found = false;

			for (PostalAddress p : postals) {

				if (p.getRel().equals(getPostal().getRel())) {

					p.setValue(new String(getContent()));

					found = true;

					break;
				}

			}

			if (!found) {

				getPostal().setValue(new String(getContent()));

				postals.add(getPostal());
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

			List<PostalAddress> postals = contact.getPostalAddresses();

			Iterator<PostalAddress> i = postals.iterator();

			while (i.hasNext()) {

				PostalAddress p = i.next();

				if (p.getRel().equals(getPostal().getRel())) {

					i.remove();

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
