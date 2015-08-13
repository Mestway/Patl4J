package goofs.fs.contacts;

import fuse.Errno;
import goofs.contacts.IContacts;
import goofs.fs.Dir;
import goofs.fs.SimpleFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.PostalAddress;

public class ContactAddressTempFile extends SimpleFile {

	private static Set<String> RELS = new HashSet<String>();

	static {

		RELS.add(PostalAddress.Rel.WORK.split("#")[1]);
		RELS.add(PostalAddress.Rel.HOME.split("#")[1]);
		RELS.add(PostalAddress.Rel.OTHER.split("#")[1]);
	}

	public ContactAddressTempFile(Dir parent, String name) throws Exception {
		super(parent, name);

	}

	@Override
	public int save() {

		return super.save();
	}

	@Override
	public int rename(Dir newParent, String name) {

		int rt = super.rename(newParent, name);

		if (rt == 0) {

			if (RELS.contains(getName())) {
				try {
					IContacts contacts = ((ContactAddressDir) getParent())
							.getContacts();
					ContactEntry contact = ((ContactAddressDir) getParent())
							.getContact();

					List<PostalAddress> postals = contact.getPostalAddresses();

					PostalAddress p = findMatch(postals);

					if (p != null) {
						p.setValue(new String(getContent()));

						contacts.updateContact(contact);

						Dir parent = getParent();

						remove();

						parent.add(new ContactAddressFile(parent, p));

						return 0;

					} else {
						p = new PostalAddress();
						p.setValue(new String(getContent()));
						if (PostalAddress.Rel.WORK.endsWith(getName())) {
							p.setRel(PostalAddress.Rel.WORK);
						} else if (PostalAddress.Rel.HOME.endsWith(getName())) {
							p.setRel(PostalAddress.Rel.HOME);
						} else {
							p.setRel(PostalAddress.Rel.OTHER);
						}

						contact.getPostalAddresses().add(p);

						contacts.updateContact(contact);

						Dir parent = getParent();

						remove();

						parent.add(new ContactAddressFile(parent, p));

						return 0;
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					return Errno.EROFS;
				}
			}

		}

		return rt;

	}

	protected PostalAddress findMatch(List<PostalAddress> postals) {
		for (PostalAddress p : postals) {

			if (p.getRel().endsWith(getName())) {
				return p;
			}

		}
		return null;

	}

}
