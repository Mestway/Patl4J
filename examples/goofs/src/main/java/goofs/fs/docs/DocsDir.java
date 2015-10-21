package goofs.fs.docs;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.docs.IDocuments;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.FolderEntry;

public class DocsDir extends Dir implements EntryContainer {

	protected IDocuments documents;

	protected Set<String> entryIds = new HashSet<String>();

	public DocsDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.docs.documents"), 0777);

		documents = (IDocuments) ServiceFactory.getService(IDocuments.class);

		List<DocumentListEntry> docs = documents.getRootDocuments();

		for (DocumentListEntry doc : docs) {

			add(new DocsFile(this, doc));

			entryIds.add(doc.getSelfLink().getHref());

		}

		List<FolderEntry> folders = documents.getRootFolders();

		for (FolderEntry folder : folders) {

			add(new DocsFolderDir(this, folder));

			entryIds.add(folder.getSelfLink().getHref());

		}

	}

	public void addNewEntryById(String entryId) throws Exception {
		if (entryId.indexOf("/folder") != -1) {
			add(new DocsFolderDir(this, getDocuments().getFolderById(entryId)));
		} else {
			add(new DocsFile(this, getDocuments().getDocumentById(entryId)));
		}
	}

	public Set<String> getCurrentEntryIds() throws Exception {
		Set<String> current = new HashSet<String>();
		List<FolderEntry> folders = documents.getRootFolders();
		for (FolderEntry folder : folders) {
			current.add(folder.getSelfLink().getHref());
		}
		return current;
	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	protected IDocuments getDocuments() {
		return documents;
	}

	@Override
	public int createChild(String name, boolean isDir) {

		try {
			if (isDir) {

				FolderEntry folder = getDocuments().creatFolder(name);

				add(new DocsFolderDir(this, folder));

			}

			else {

				add(new DocsFile(this, name));

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createTempChild(String name) {

		try {

			Map<String, String> docMap = new HashMap<String, String>();

			for (Node node : files.values()) {

				if (node instanceof DocsFile) {
					docMap.put(node.getName(), ((DocsFile) node).getId());
				}

			}

			add(new DocsTempFile(this, name, docMap));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
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
