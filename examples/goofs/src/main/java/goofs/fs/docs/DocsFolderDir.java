package goofs.fs.docs;

import goofs.EntryContainer;
import goofs.Identifiable;
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

public class DocsFolderDir extends Dir implements EntryContainer, Identifiable {

	protected String folderId;

	protected Set<String> entryIds = new HashSet<String>();

	public DocsFolderDir(Dir parent, FolderEntry folder) throws Exception {

		super(parent, folder.getTitle().getPlainText(), 0777);

		setFolderId(folder.getSelfLink().getHref());

		List<DocumentListEntry> docs = getDocuments().getDocumentsInFolder(
				folder.getId());

		for (DocumentListEntry doc : docs) {
			add(new DocsFile(this, doc));
			entryIds.add(doc.getSelfLink().getHref());

		}

		List<FolderEntry> folders = getDocuments().getChildFolders(folder);

		for (FolderEntry next : folders) {
			add(new DocsFolderDir(this, next));
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
		List<FolderEntry> folders = getDocuments().getChildFolders(
				getFolderId());
		for (FolderEntry next : folders) {
			current.add(next.getSelfLink().getHref());
		}
		return current;
	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	protected IDocuments getDocuments() {
		Dir parent = getParent();
		boolean isDocsDir = (parent instanceof DocsDir);
		while (!isDocsDir) {
			parent = parent.getParent();
			isDocsDir = (parent instanceof DocsDir);
		}
		return ((DocsDir) parent).getDocuments();
	}

	public String getId() {
		return getFolderId();
	}

	protected String getFolderId() {
		return folderId;
	}

	protected void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	@Override
	public int createChild(String name, boolean isDir) {

		try {
			if (isDir) {

				FolderEntry folder = getDocuments().creatFolder(name);

				getDocuments()
						.moveFolderToFolder(getFolderId(), folder.getId());

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

		try {
			getDocuments().deleteFolder(getFolderId());

			remove();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int rename(Dir newParent, String name) {

		try {
			if (getParent() == newParent) {

				getDocuments().renameFolder(getFolderId(), name);

				setName(name);

			}

			else if (newParent instanceof DocsFolderDir) {

				getDocuments().renameFolder(getFolderId(), name);

				getDocuments().moveFolderToFolder(
						((DocsFolderDir) newParent).getFolderId(),
						getFolderId());

				((DocsFolderDir) newParent).add(new DocsFolderDir(newParent,
						getDocuments().getFolderById(getFolderId())));

				remove();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
