package goofs.docs;

import goofs.GoofsService;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.google.gdata.data.Link;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.FolderEntry;
import com.google.gdata.data.docs.PresentationEntry;
import com.google.gdata.data.docs.SpreadsheetEntry;

public interface IDocuments extends GoofsService {

	public abstract List<DocumentListEntry> getDocuments() throws Exception;

	public abstract List<DocumentListEntry> getDocumentsInFolder(String folderId)
			throws Exception;

	public abstract List<FolderEntry> getFolders() throws Exception;

	public abstract List<DocumentListEntry> getWpDocuments() throws Exception;

	public abstract List<DocumentListEntry> getSpreadSheets() throws Exception;

	public abstract List<DocumentListEntry> getPresentations() throws Exception;

	public abstract DocumentListEntry getDocumentById(String id)
			throws Exception;

	public abstract InputStream getDocumentContents(DocumentListEntry e,
			String fileExtension) throws Exception;

	public abstract InputStream getDocumentContents(String docId,
			String fileExtension) throws Exception;

	public abstract FolderEntry getFolderById(String id) throws Exception;

	public abstract List<Link> getFolderParentLinks(String folderId)
			throws Exception;

	public abstract List<DocumentListEntry> getRootDocuments() throws Exception;

	public abstract List<FolderEntry> getRootFolders() throws Exception;

	public abstract List<FolderEntry> getChildFolders(String parentFolderId)
			throws Exception;

	public abstract List<FolderEntry> getChildFolders(FolderEntry parent)
			throws Exception;

	public abstract void deleteDocument(String id) throws Exception;

	public abstract void deleteFolder(String id) throws Exception;

	public abstract void renameDocument(String id, String name)
			throws Exception;

	public abstract void renameFolder(String id, String name) throws Exception;

	public abstract void removeDocumentFromFolder(String folderId, String docId)
			throws Exception;

	public abstract void addDocumentToFolder(String folderId, String docId)
			throws Exception;

	public abstract void moveFolderToFolder(String toFolderId,
			String fromFolderId) throws Exception;

	public abstract FolderEntry creatFolder(String name) throws Exception;

	public abstract DocumentEntry createWPDocument(String name, File contents,
			String folderName) throws Exception;

	public abstract SpreadsheetEntry createSpreadsheet(String name,
			File contents, String folderName) throws Exception;

	public abstract PresentationEntry createPresentation(String name,
			File contents, String folderName) throws Exception;
	
	public abstract DocumentListEntry createAny(String name, File contents,
			String folderName) throws Exception;

	public abstract void updateDocumentContent(String id, String fileName,
			File contents) throws Exception;

	public abstract boolean isWPDocument(String fileName);

	public abstract boolean isSpreadSheet(String fileName);

	public abstract boolean isPresentation(String fileName);

	public abstract String getDefaultExtension(DocumentListEntry e);

}