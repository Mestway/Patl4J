package goofs.fs.contacts;

import fuse.Errno;
import goofs.GoofsProperties;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.PostalAddress;

public class ContactAddressDir extends Dir {

	public ContactAddressDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.contacts.address"), 0777);

		boolean homeCreated = false;
		boolean workCreated = false;
		boolean otherCreated = false;

		List<PostalAddress> postals = getContact().getPostalAddresses();

		for (PostalAddress postal : postals) {

			add(new ContactAddressFile(this, postal));

			homeCreated = homeCreated
					|| PostalAddress.Rel.HOME.equals(postal.getRel());
			workCreated = workCreated
					|| PostalAddress.Rel.WORK.equals(postal.getRel());
			otherCreated = otherCreated
					|| PostalAddress.Rel.OTHER.equals(postal.getRel());

		}

		PostalAddress postal = null;
		if (!homeCreated) {
			postal = new PostalAddress();
			postal.setValue("");
			postal.setRel(PostalAddress.Rel.HOME);
			add(new ContactAddressFile(this, postal));
		}
		if (!workCreated) {
			postal = new PostalAddress();
			postal.setValue("");
			postal.setRel(PostalAddress.Rel.WORK);
			add(new ContactAddressFile(this, postal));
		}
		if (!otherCreated) {
			postal = new PostalAddress();
			postal.setValue("");
			postal.setRel(PostalAddress.Rel.OTHER);
			postal.setLabel("other");
			add(new ContactAddressFile(this, postal));
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
				PostalAddress postal = new PostalAddress();
				if (PostalAddress.Rel.WORK.split("#")[1].equals(name)) {
					postal.setRel(PostalAddress.Rel.WORK);

				} else if (PostalAddress.Rel.HOME.split("#")[1].equals(name)) {
					postal.setRel(PostalAddress.Rel.HOME);
				} else {

					postal.setRel(PostalAddress.Rel.OTHER);
					//postal.setLabel(name);
				}

				add(new ContactAddressFile(this, postal));

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
			ContactAddressTempFile f = new ContactAddressTempFile(this, name);

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
