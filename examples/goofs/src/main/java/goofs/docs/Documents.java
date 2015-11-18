package goofs.docs;

import goofs.GoofsProperties;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.Category;
import com.google.gdata.data.Link;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.FolderEntry;
import com.google.gdata.data.docs.PresentationEntry;
import com.google.gdata.data.docs.SpreadsheetEntry;
import com.google.gdata.data.docs.DocumentListEntry.MediaType;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Documents implements IDocuments {

	private static final String DOCS_GOOGLE_COM_FEEDS_DOCS = "http://docs.google.com/feeds/download/documents/Export?docID=";
	private static final String DOCS_GOOGLE_COM_FEEDS_PRES = "http://docs.google.com/feeds/download/presentations/Export?docID=";

	protected DocsService realService;

	protected SpreadsheetService spreadsheetsService;

	protected static final String SPREADSHEETS_SERVICE_NAME = "wise";

	public static final String DOC_DEFAULT_EXT = GoofsProperties.INSTANCE
			.getProperty("goofs.docs.wp.ext");

	public static final String SPREAD_DEFAULT_EXT = GoofsProperties.INSTANCE
			.getProperty("goofs.docs.spreadsheet.ext");

	public static final String PRESENT_DEFAULT_EXT = GoofsProperties.INSTANCE
			.getProperty("goofs.docs.presentation.ext");

	public static final String PDF_DEFAULT_EXT = "pdf";

	private static final Map<String, String> DOWNLOAD_DOCUMENT_FORMATS;
	static {
		DOWNLOAD_DOCUMENT_FORMATS = new HashMap<String, String>();
		DOWNLOAD_DOCUMENT_FORMATS.put("doc", "doc");
		DOWNLOAD_DOCUMENT_FORMATS.put("txt", "txt");
		DOWNLOAD_DOCUMENT_FORMATS.put("odt", "odt");
		DOWNLOAD_DOCUMENT_FORMATS.put("pdf", "pdf");
		DOWNLOAD_DOCUMENT_FORMATS.put("png", "png");
		DOWNLOAD_DOCUMENT_FORMATS.put("rtf", "rtf");
		DOWNLOAD_DOCUMENT_FORMATS.put("html", "html");
	}

	private static final Map<String, String> DOWNLOAD_PRESENTATION_FORMATS;
	static {
		DOWNLOAD_PRESENTATION_FORMATS = new HashMap<String, String>();
		DOWNLOAD_PRESENTATION_FORMATS.put("pdf", "pdf");
		DOWNLOAD_PRESENTATION_FORMATS.put("ppt", "ppt");
		DOWNLOAD_PRESENTATION_FORMATS.put("swf", "swf");
	}

	private static final Map<String, String> DOWNLOAD_SPREADSHEET_FORMATS;
	static {
		DOWNLOAD_SPREADSHEET_FORMATS = new HashMap<String, String>();
		DOWNLOAD_SPREADSHEET_FORMATS.put("xls", "xls");
		DOWNLOAD_SPREADSHEET_FORMATS.put("ods", "ods");
		DOWNLOAD_SPREADSHEET_FORMATS.put("pdf", "pdf");
		DOWNLOAD_SPREADSHEET_FORMATS.put("csv", "csv");
		DOWNLOAD_SPREADSHEET_FORMATS.put("tsv", "tsv");
		DOWNLOAD_SPREADSHEET_FORMATS.put("html", "html");
	}

	public Documents(String username, String password)
			throws AuthenticationException {

		realService = new DocsService(APP_NAME);
		realService.setUserCredentials(username, password);
		spreadsheetsService = new SpreadsheetService(APP_NAME);
		spreadsheetsService.setUserCredentials(username, password);
	}

	public DocsService getRealService() {
		return realService;
	}

	public GoogleService getSpreadsheetsService() {
		return spreadsheetsService;
	}

	public void acquireSessionTokens(String username, String password)
			throws AuthenticationException {

		getRealService().setUserCredentials(username, password);
		getSpreadsheetsService().setUserCredentials(username, password);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getDocuments()
	 */
	public List<DocumentListEntry> getDocuments() throws Exception {

		DocumentListFeed feed = getRealService().getFeed(
				new URL("http://docs.google.com/feeds/default/private/full"),
				DocumentListFeed.class);

		return feed.getEntries();

	}

	public List<DocumentListEntry> getDocumentsInFolder(String folderId)
			throws Exception {

		String folderIdShort = folderId.split("%3A")[1];

		DocumentListFeed feed = getRealService().getFeed(
				new URL(
						"http://docs.google.com/feeds/default/private/full/folder%3A"
								+ folderIdShort + "/contents"),
				DocumentListFeed.class);

		return feed.getEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getFolders()
	 */
	public List<FolderEntry> getFolders() throws Exception {

		DocumentListFeed feed = getRealService()
				.getFeed(
						new URL(
								"http://docs.google.com/feeds/default/private/full/-/folder"),
						DocumentListFeed.class);

		return feed.getEntries(FolderEntry.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getWpDocuments()
	 */
	public List<DocumentListEntry> getWpDocuments() throws Exception {

		DocumentListFeed feed = getRealService()
				.getFeed(
						new URL(
								"http://docs.google.com/feeds/default/private/full/-/document"),
						DocumentListFeed.class);

		return feed.getEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getSpreadSheets()
	 */
	public List<DocumentListEntry> getSpreadSheets() throws Exception {

		DocumentListFeed feed = getRealService()
				.getFeed(
						new URL(
								"http://docs.google.com/feeds/default/private/full/-/spreadsheet"),
						DocumentListFeed.class);

		return feed.getEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getPresentations()
	 */
	public List<DocumentListEntry> getPresentations() throws Exception {

		DocumentListFeed feed = getRealService()
				.getFeed(
						new URL(
								"http://docs.google.com/feeds/default/private/full/-/presentation"),
						DocumentListFeed.class);

		return feed.getEntries();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getDocumentById(java.lang.String)
	 */
	public DocumentListEntry getDocumentById(String id) throws Exception {

		return getRealService().getEntry(new URL(id), DocumentListEntry.class);

	}

	protected String getDocumentIdSuffix(String objectId) {

		return objectId.substring(objectId.lastIndexOf("%3A") + 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#getFolderById(java.lang.String)
	 */
	public FolderEntry getFolderById(String id) throws Exception {

		return getRealService().getEntry(new URL(id), FolderEntry.class);
	}

	public List<Link> getFolderParentLinks(String folderId) throws Exception {

		return getFolderById(folderId).getParentLinks();

	}

	public List<DocumentListEntry> getRootDocuments() throws Exception {

		List<DocumentListEntry> roots = new ArrayList<DocumentListEntry>();
		for (DocumentListEntry next : getDocuments()) {

			if (next.getParentLinks().isEmpty()) {
				roots.add(next);
			}
		}
		return roots;
	}

	public List<FolderEntry> getRootFolders() throws Exception {

		List<FolderEntry> roots = new ArrayList<FolderEntry>();
		for (FolderEntry next : getFolders()) {

			if (next.getParentLinks().isEmpty()) {
				roots.add(next);
			}
		}

		return roots;

	}

	public List<FolderEntry> getChildFolders(String parentFolderId)
			throws Exception {

		return getChildFolders(getFolderById(parentFolderId));

	}

	public List<FolderEntry> getChildFolders(FolderEntry parent)
			throws Exception {

		List<FolderEntry> childs = new ArrayList<FolderEntry>();
		for (FolderEntry next : getFolders()) {
			for (Link link : next.getParentLinks()) {
				if (link.getHref().equals(parent.getSelfLink().getHref())) {
					childs.add(next);
				}
			}
		}
		return childs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#deleteDocument(java.lang.String)
	 */
	public void deleteDocument(String id) throws Exception {

		DocumentListEntry doc = getDocumentById(id);

		doc.delete();
	}

	public void deleteFolder(String id) throws Exception {

		FolderEntry folder = getFolderById(id);

		folder.delete();
	}

	public void renameDocument(String id, String name) throws Exception {

		DocumentListEntry doc = getDocumentById(id);
		int dindex = name.lastIndexOf(".");
		if (dindex != -1) {

			doc.setTitle(new PlainTextConstruct(name.substring(0, dindex)));

		} else {
			doc.setTitle(new PlainTextConstruct(name));
		}

		doc.update();

	}

	public void renameFolder(String id, String name) throws Exception {

		FolderEntry folder = getFolderById(id);

		folder.setTitle(new PlainTextConstruct(name));

		folder.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#removeDocumentFromFolder(java.lang.String,
	 * java.lang.String)
	 */
	public void removeDocumentFromFolder(String folderId, String docId)
			throws Exception {

		FolderEntry folder = getFolderById(folderId);

		DocumentListEntry doc = getDocumentById(docId);

		String folderContentUri = ((MediaContent) folder.getContent()).getUri();

		getRealService().delete(
				new URL(folderContentUri + "/" + doc.getResourceId()),
				doc.getEtag());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#addDocumentToFolder(java.lang.String,
	 * java.lang.String)
	 */
	public void addDocumentToFolder(String folderId, String docId)
			throws Exception {

		DocumentListEntry doc = getDocumentById(docId);

		addDocumentToFolder(folderId, doc);

	}

	protected void addDocumentToFolder(String folderId, DocumentListEntry doc)
			throws Exception {

		FolderEntry folder = getFolderById(folderId);

		String destFolderUri = ((MediaContent) folder.getContent()).getUri();

		getRealService().insert(new URL(destFolderUri), doc);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#moveFolderToFolder(java.lang.String,
	 * java.lang.String)
	 */
	public void moveFolderToFolder(String toFolderId, String fromFolderId)
			throws Exception {

		FolderEntry destFolder = getFolderById(toFolderId);

		FolderEntry sourceFolder = getFolderById(fromFolderId);

		String destFolderUri = ((MediaContent) destFolder.getContent())
				.getUri();

		getRealService().insert(new URL(destFolderUri), sourceFolder);

	}

	public FolderEntry creatFolder(String name) throws Exception {

		FolderEntry folder = new FolderEntry();
		folder.setTitle(new PlainTextConstruct(name));

		return getRealService().insert(
				new URL("http://docs.google.com/feeds/default/private/full"),
				folder);

	}

	protected MediaType getMediaType(String fileName) {
		MediaType m = null;
		fileName = fileName.toUpperCase();
		int dindex = fileName.lastIndexOf(".");
		try {
			if (dindex != -1) {
				m = MediaType.fromFileName(fileName.substring(dindex + 1));
			} else {
				m = MediaType.fromFileName(fileName);
			}
		} catch (Throwable e) {
		}
		return m;
	}

	protected <T extends DocumentListEntry> T createDocument(String name,
			File contents, String folderId, T newDocument) throws Exception {
		MediaType mediaType = getMediaType(name);
		setDocumentFile(name, contents, newDocument, mediaType);
		setDocumentTitle(name, newDocument);
		URL url = buildCreateUrl(newDocument, mediaType);
		T created = getRealService().insert(url, newDocument);
		if (folderId != null) {
			addDocumentToFolder(folderId, created);
		}
		optionallyTranslateDocument(name, contents, url, created);
		return created;
	}

	private <T extends DocumentListEntry> void optionallyTranslateDocument(String name, File contents,
			URL url, T created) throws Exception {
		boolean translating = url.toString().contains("targetLanguage=");
		if (translating) {
			int dindex = name.lastIndexOf(".");
			InputStream translatedInputStream = getDocumentContents(created,
					name.substring(dindex + 1));
			updateLocalContent(contents, translatedInputStream);

		}
	}

	private <T extends DocumentListEntry> void setDocumentTitle(String name, T newDocument) {
		int dindex = name.lastIndexOf(".");
		if (dindex != -1) {
			newDocument.setTitle(new PlainTextConstruct(name.substring(0,
					dindex)));
		} else {
			newDocument.setTitle(new PlainTextConstruct(name));
		}
	}

	private <T extends DocumentListEntry> void setDocumentFile(String name, File contents, T newDocument,
			MediaType mediaType) {
		if (mediaType != null) {
			newDocument.setFile(contents, mediaType.getMimeType());
		} else {
			newDocument.setFile(contents, detectMimeType(name));
		}
	}

	private <T extends DocumentListEntry> URL buildCreateUrl(T newDocument, MediaType mediaType)
			throws MalformedURLException {
		StringBuilder uri = new StringBuilder(
				"http://docs.google.com/feeds/default/private/full");
		if (mediaType == null) {
			uri.append("/?convert=false");
		} else {
			int undindex = newDocument.getTitle().getPlainText().lastIndexOf(
					'_');
			if (undindex != -1) {
				String lang = newDocument.getTitle().getPlainText().substring(
						undindex + 1);
				if (GoofsProperties.INSTANCE.getLanguages().contains(lang)) {
					uri.append("/?targetLanguage=").append(lang);
				}
			}
		}
		URL url = new URL(uri.toString());
		return url;
	}

	private String detectMimeType(String name) {

		return "application/zip";
	}

	protected void updateLocalContent(File file, InputStream is)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		try {
			byte[] buff = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = is.read(buff)) != -1) {
				fos.write(buff, 0, bytesRead);

			}
		} finally {
			fos.close();
			is.close();
		}
	}

	public boolean isWPDocument(String fileName) {

		MediaType m = getMediaType(fileName);

		return (m != null
				&& (m.equals(MediaType.DOC) || m.equals(MediaType.DOCX)
						|| m.equals(MediaType.RTF) || m.equals(MediaType.ODT)
						|| m.equals(MediaType.TXT) || m.equals(MediaType.HTM) || m
						.equals(MediaType.HTML)) || m.equals(MediaType.PDF));
	}

	public boolean isSpreadSheet(String fileName) {

		MediaType m = getMediaType(fileName);

		return (m != null && (m.equals(MediaType.XLS)
				|| m.equals(MediaType.XLSX) || m.equals(MediaType.CSV)
				|| m.equals(MediaType.ODS) || m.equals(MediaType.TAB) || m
				.equals(MediaType.TSV)));

	}

	public boolean isPresentation(String fileName) {

		MediaType m = getMediaType(fileName);

		return (m != null && (m.equals(MediaType.PPT) || m
				.equals(MediaType.PPS)));

	}

	public void updateDocumentContent(String id, String fileName, File contents)
			throws Exception {

		DocumentListEntry doc = getDocumentById(id);

		doc.setFile(contents, getMediaType(fileName).getMimeType());

		doc.updateMedia(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#createWPDocument(java.lang.String,
	 * java.io.File, java.lang.String)
	 */
	public DocumentEntry createWPDocument(String name, File contents,
			String folderName) throws Exception {

		DocumentEntry newDocument = new DocumentEntry();

		return createDocument(name, contents, folderName, newDocument);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#createSpreadsheet(java.lang.String,
	 * java.io.File, java.lang.String)
	 */
	public SpreadsheetEntry createSpreadsheet(String name, File contents,
			String folderName) throws Exception {

		SpreadsheetEntry newDocument = new SpreadsheetEntry();

		return createDocument(name, contents, folderName, newDocument);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see goofs.docs.IDocuments#createPresentation(java.lang.String,
	 * java.io.File, java.lang.String)
	 */
	public PresentationEntry createPresentation(String name, File contents,
			String folderName) throws Exception {

		PresentationEntry newDocument = new PresentationEntry();

		return createDocument(name, contents, folderName, newDocument);

	}

	public DocumentListEntry createAny(String name, File contents,
			String folderName) throws Exception {

		DocumentListEntry newDocument = new DocumentListEntry();

		return createDocument(name, contents, folderName, newDocument);
	}

	public InputStream getDocumentContents(String docId, String fileExtension)
			throws Exception {

		return getDocumentContents(getDocumentById(docId), fileExtension);
	}

	public InputStream getDocumentContents(DocumentListEntry e, String ext)
			throws Exception {
		Link link = getLinkFromEntry(e, ext);
		if (link.getHref() != null) {
			if (isSpreadSheet(e)) {
				return getSpreadsheetInputStream(link);
			} else {
				return getDocumentInputStream(link);
			}
		} else {
			return new ByteArrayInputStream("".getBytes());
		}
	}

	private Link getLinkFromEntry(DocumentListEntry e, String ext) {
		Link link = new Link();
		if (isWPDocument(e)
				&& DOWNLOAD_DOCUMENT_FORMATS.containsKey(ext.toLowerCase())) {
			link.setHref(DOCS_GOOGLE_COM_FEEDS_DOCS
					+ getDocumentIdSuffix(e.getId()) + "&exportFormat="
					+ DOWNLOAD_DOCUMENT_FORMATS.get(ext));
		} else if (isSpreadSheet(e)
				&& DOWNLOAD_SPREADSHEET_FORMATS.containsKey(ext.toLowerCase())) {
			link.setHref(((MediaContent) e.getContent()).getUri()
					+ "&exportFormat=" + DOWNLOAD_SPREADSHEET_FORMATS.get(ext));
		} else if (isPresentation(e)
				&& DOWNLOAD_PRESENTATION_FORMATS.containsKey(ext.toLowerCase())) {
			link.setHref(DOCS_GOOGLE_COM_FEEDS_PRES
					+ getDocumentIdSuffix(e.getId()) + "&exportFormat="
					+ DOWNLOAD_PRESENTATION_FORMATS.get(ext));

		} else if (isPdf(e) && PDF_DEFAULT_EXT.equals(ext)) {
			link.setHref(((MediaContent) e.getContent()).getUri());
		} else {
			link.setHref(((MediaContent) e.getContent()).getUri());
		}
		return link;
	}

	private InputStream getDocumentInputStream(Link link) throws IOException,
			ServiceException {
		GDataRequest gdataRequest = getRealService().createLinkQueryRequest(
				link);
		gdataRequest.execute();
		return gdataRequest.getResponseStream();
	}

	private InputStream getSpreadsheetInputStream(Link link)
			throws IOException, ServiceException {
		GDataRequest gdataRequest = getSpreadsheetsService()
				.createLinkQueryRequest(link);
		gdataRequest.execute();
		return gdataRequest.getResponseStream();
	}

	public String getDefaultExtension(DocumentListEntry e) {
		String ext = null;
		Category cat = getKind(e);
		if (cat.getTerm().endsWith("document")) {
			ext = Documents.DOC_DEFAULT_EXT;
		} else if (cat.getTerm().endsWith("spreadsheet")) {
			ext = Documents.SPREAD_DEFAULT_EXT;
		} else if (cat.getTerm().endsWith("presentation")) {
			ext = Documents.PRESENT_DEFAULT_EXT;
		} else if (cat.getTerm().endsWith("pdf")) {
			ext = Documents.PDF_DEFAULT_EXT;
		}
		return ext;
	}

	protected Category getKind(DocumentListEntry e) {
		for (Category cat : e.getCategories()) {
			if ("http://schemas.google.com/g/2005#kind".equals(cat.getScheme())) {
				return cat;
			}
		}
		return null;
	}

	protected boolean isWPDocument(DocumentListEntry e) {
		Category cat = getKind(e);
		return (cat != null && cat.getTerm().endsWith("document"));
	}

	protected boolean isSpreadSheet(DocumentListEntry e) {
		Category cat = getKind(e);
		return (cat != null && cat.getTerm().endsWith("spreadsheet"));
	}

	protected boolean isPresentation(DocumentListEntry e) {
		Category cat = getKind(e);
		return (cat != null && cat.getTerm().endsWith("presentation"));
	}

	protected boolean isPdf(DocumentListEntry e) {
		Category cat = getKind(e);
		return (cat != null && cat.getTerm().endsWith("pdf"));
	}

}
