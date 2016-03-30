package goofs.fs.docs;

import fuse.Errno;
import goofs.docs.IDocuments;
import goofs.fs.Dir;
import goofs.fs.DiskFile;

import java.util.Map;

import com.google.gdata.data.docs.DocumentListEntry;

public class DocsTempFile extends DiskFile {

	protected Map<String, String> documentMap;

	public DocsTempFile(Dir parent, String name, Map<String, String> documentMap)
			throws Exception {

		super(parent, name, 0777);
		// TODO Auto-generated constructor stub

		setDocumentMap(documentMap);
	}

	protected Map<String, String> getDocumentMap() {
		return documentMap;
	}

	protected void setDocumentMap(Map<String, String> documentMap) {
		this.documentMap = documentMap;
	}

	public int delete() {

		remove();

		return 0;
	}

	@Override
	public int save() {
		return 0;
	}

	@Override
	public int rename(Dir newParent, String name) {

		if (getParent() == newParent) {

			setName(name);

			try {
				Dir parent = getParent();

				if (getDocuments().isWPDocument(getName())) {

					DocumentListEntry doc = null;

					if (getDocumentMap().containsKey(getName())) {

						String docId = getDocumentMap().get(getName());

						getDocuments().updateDocumentContent(docId, getName(),
								getDisk());

						doc = getDocuments().getDocumentById(docId);

					} else {

						doc = getDocuments().createWPDocument(getName(),
								getDisk(), null);

					}

					remove();

					parent.add(new DocsFile(parent, doc));

					if (parent instanceof DocsFolderDir) {

						getDocuments().addDocumentToFolder(
								((DocsFolderDir) parent).getFolderId(),
								doc.getId());

					}

				}

				else if (getDocuments().isSpreadSheet(getName())) {

					DocumentListEntry sp = null;

					if (getDocumentMap().containsKey(getName())) {

						String docId = getDocumentMap().get(getName());

						getDocuments().updateDocumentContent(docId, getName(),
								getDisk());

						sp = getDocuments().getDocumentById(docId);

					} else {

						sp = getDocuments().createSpreadsheet(getName(),
								getDisk(), null);

					}

					remove();

					parent.add(new DocsFile(parent, sp));

					if (parent instanceof DocsFolderDir) {

						getDocuments().addDocumentToFolder(
								((DocsFolderDir) parent).getFolderId(),
								sp.getId());

					}

				}

				else if (getDocuments().isPresentation(getName())) {

					DocumentListEntry pr = null;

					if (getDocumentMap().containsKey(getName())) {

						String docId = getDocumentMap().get(getName());

						getDocuments().updateDocumentContent(docId, getName(),
								getDisk());

						pr = getDocuments().getDocumentById(docId);

					} else {
						pr = getDocuments().createPresentation(getName(),
								getDisk(), null);

					}

					remove();

					parent.add(new DocsFile(parent, pr));

					if (parent instanceof DocsFolderDir) {

						getDocuments().addDocumentToFolder(
								((DocsFolderDir) parent).getFolderId(),
								pr.getId());

					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return Errno.EROFS;
			}

		}

		return 0;
	}

	protected IDocuments getDocuments() {
		Dir parent = getParent();
		if (parent instanceof DocsFolderDir) {
			return ((DocsFolderDir) parent).getDocuments();
		} else {
			return ((DocsDir) parent).getDocuments();
		}

	}

}
