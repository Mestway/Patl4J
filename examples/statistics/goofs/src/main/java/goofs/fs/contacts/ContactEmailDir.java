package goofs.fs.contacts;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;

public class ContactEmailDir extends Dir {

	public ContactEmailDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.contacts.email"), 0777);

		boolean homeCreated = false;
		boolean workCreated = false;
		boolean otherCreated = false;

		List<Email> emails = getContact().getEmailAddresses();

		for (Email email : emails) {

			add(new ContactEmailFile(this, email));

			homeCreated = homeCreated || Email.Rel.HOME.equals(email.getRel());
			workCreated = workCreated || Email.Rel.WORK.equals(email.getRel());
			otherCreated = otherCreated
					|| Email.Rel.OTHER.equals(email.getRel());
		}

		Email email = null;
		if (!homeCreated) {
			email = new Email();
			email.setAddress("");
			email.setRel(Email.Rel.HOME);
			add(new ContactEmailFile(this, email));
		}
		if (!workCreated) {
			email = new Email();
			email.setAddress("");
			email.setRel(Email.Rel.WORK);
			add(new ContactEmailFile(this, email));
		}
		if (!otherCreated) {
			email = new Email();
			email.setAddress("");
			email.setRel(Email.Rel.OTHER);
			email.setLabel("other");
			add(new ContactEmailFile(this, email));
		}

	}

	public ContactEntry getContact() throws Exception {
		return ((ContactDir) getParent()).getContact();
	}

	protected IContacts getContacts() {

		return ((ContactDir) getParent()).getContacts();
	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (!isDir) {
			try {
				Email email = new Email();
				if (Email.Rel.WORK.split("#")[1].equals(name)) {
					email.setRel(Email.Rel.WORK);

				} else if (Email.Rel.HOME.split("#")[1].equals(name)) {
					email.setRel(Email.Rel.HOME);
				} else {

					email.setRel(Email.Rel.OTHER);
					//email.setLabel(name);
				}

				add(new ContactEmailFile(this, email));

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
		try {
			ContactEmailTempFile f = new ContactEmailTempFile(this, name);

			add(f);

			return 0;

		} catch (Exception e) {
			return Errno.EROFS;
		}
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
