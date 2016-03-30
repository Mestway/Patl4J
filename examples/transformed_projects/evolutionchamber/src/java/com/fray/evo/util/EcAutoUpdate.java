package com.fray.evo.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

public class EcAutoUpdate extends SwingWorker<Void, Void> {
	private static final Logger logger = Logger.getLogger(EcAutoUpdate.class.getName());
	private static final String downloadUrlFormat = "http://evolutionchamber.googlecode.com/files/evolutionchamber-version-%s.jar";
	
	private static final String downloadedFileNameFormat = "evolutionchamber-version-%s.jar";
	
	private static final String	checksumUrlFormat = "http://code.google.com/p/evolutionchamber/downloads/detail?name=evolutionchamber-version-%s.jar";
	
	private static final Pattern checksumPattern = Pattern.compile("SHA1 Checksum: </th><td style=\"white-space:nowrap\"> ([0-9a-f]{40})", Pattern.CASE_INSENSITIVE);
	
	private static final String downloadsPageUrl = "http://code.google.com/p/evolutionchamber/downloads/list";

	private static final Pattern jarUrlRegex = Pattern.compile("evolutionchamber\\.googlecode\\.com/files/evolutionchamber-version-(\\d+)\\.jar");
	
	private boolean 			updateAvailable= false;
	
	private boolean				updating       = false;
	
	private String				latestVersion  = "";
	
	private String          	jarFile        = "";
	
	private boolean				checksumMatches = true;

	protected final Callback callback;

	public EcAutoUpdate(String ecVersion, Callback callback) {
		configureProxy();
		this.callback = callback;
		this.latestVersion	 	= findLatestVersion(ecVersion);
		
		if( !this.latestVersion.equals( ecVersion ) )
			this.updateAvailable = true;
	}
	
	private void configureProxy(){
		System.setProperty("java.net.useSystemProxies", "true");

		try {
			List<Proxy> proxies = ProxySelector.getDefault().select(new URI(downloadsPageUrl));
			for (Proxy proxy : proxies){
		        InetSocketAddress addr = (InetSocketAddress) proxy.address();
		        if (addr == null){
		        } else {
	                System.setProperty("http.proxyHost", addr.getHostName());
	                System.setProperty("http.proxyPort", Integer.toString(addr.getPort()));
		        }
		    }
		} 
		catch (URISyntaxException e) {
		    logger.log(Level.SEVERE, "Download list URL is invalid.", e);
		}
	}
	
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	
	public boolean isUpdating() {
		return updating;
	}
	
	public void setUpdating(boolean updating){
		this.updating = updating;
	}
	
	public String getLatestVersion() {
		return latestVersion;
	}
	
    public boolean isChecksumMatches() {
		return checksumMatches;
	}
	
	private String findLatestVersion(String ecVersion) {
		String latestVersion = ecVersion;
		try {
			String html = null;
			{
				URL url = new URL(downloadsPageUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int read;
				while ((read = in.read()) != -1) {
					out.write(read);
				}
				in.close();
				html = new String(out.toByteArray());
			}

			int latest = Integer.parseInt(ecVersion);
			Matcher m = jarUrlRegex.matcher(html);
			while (m.find()) {
				int cur = Integer.parseInt(m.group(1));
				if (cur > latest) {
					latest = cur;
					latestVersion = m.group(1);
				}
			}
		} catch (MalformedURLException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		} catch (UnknownHostException e) {
			callback.updateCheckFailed();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		return latestVersion;
	}
	
	protected FileInfo getFileInputStreamAndLength(String version) throws IOException
	{
		URL downloadUrl = new URL(String.format(downloadUrlFormat, version));
		URLConnection uc 	= downloadUrl.openConnection();
	    String contentType 	= uc.getContentType();
	    int contentLength 	= uc.getContentLength();
	    if (contentType.startsWith("text/") || contentLength == -1) {
	    	throw new IOException("This is not a binary file.");
	    }
	    return new FileInfo(uc.getInputStream(), contentLength);
	}
	
    @Override
    public Void doInBackground() {
        int progress = 0;
        setProgress(0);
        this.updating = true;
		try {
			String expectedChecksum = getExpectedChecksum(latestVersion);

			boolean download = true;
			File file = new File(String.format(downloadedFileNameFormat, this.latestVersion));
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			if (file.exists()){
				FileInputStream in = new FileInputStream(file);
				byte[] buf = new byte[1024];
				int read;
				while ((read = in.read(buf)) != -1){
					md.update(buf, 0, read);
				}
				in.close();
				
				String fileChecksum = convertToHex(md.digest());
				
				if (expectedChecksum != null && expectedChecksum.equalsIgnoreCase(fileChecksum)){
					download = false;
					checksumMatches = true;
				}
			}
			
			if (download){
				FileInfo info = getFileInputStreamAndLength(latestVersion);
			    
			    String fileChecksum;
			    {
					FileOutputStream out = new FileOutputStream(file);
				    InputStream raw = info.inputStream;
				    InputStream in 	= new BufferedInputStream(raw);
				    byte[] buf = new byte[1024];
				    int bytesRead = 0;
				    int offset = 0;
				    while ((bytesRead = in.read(buf)) != -1){
				    	md.update(buf, 0, bytesRead);
				    	out.write(buf, 0, bytesRead);
				    	offset += bytesRead;
				    	progress = (int)( (offset / (float)info.length) * 100 );
					    setProgress(progress);
				    }
				    fileChecksum = convertToHex(md.digest());
				    in.close();
				    out.close();
				    
				    if (offset != info.length) {
				        throw new IOException("Only read " + offset + " bytes; Expected " + info.length + " bytes");
				    }
			    }
			    
			    checksumMatches = expectedChecksum == null || expectedChecksum.equalsIgnoreCase(fileChecksum);
			}

			this.jarFile = file.getName();
		} catch (MalformedURLException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		} catch (NoSuchAlgorithmException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
        return null;
    }
    

    public static String getExpectedChecksum(String version) throws IOException
    {
    	String pageChecksum = null;
    	
		URL checksumUrl = new URL(String.format(checksumUrlFormat, version));
		HttpURLConnection conn = (HttpURLConnection) checksumUrl.openConnection();
		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int read;
		while ((read = in.read()) != -1) {
			out.write(read);
		}
		String html = new String(out.toByteArray());
		in.close();
		out.close();
		
		Matcher m = checksumPattern.matcher(html);
		if (m.find()){
			pageChecksum = m.group(1);
		}
		
		return pageChecksum;
    }

    @Override
    public void done() {
    	setProgress(100);
    	
    	if (checksumMatches){
	        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	        String restartCmd[] = new String[] { javaBin, "-jar", this.jarFile};
	        try {
				Runtime.getRuntime().exec( restartCmd );
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
    	} else {
    		callback.checksumFailed();
    	}
		
        System.exit(0);
    }
    
    private static String convertToHex(byte[] data) { 
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    }
    

    public interface Callback
    {
    	void checksumFailed();
    	
    	void updateCheckFailed();
    }
    
    protected static class FileInfo
    {
    	public InputStream inputStream;
    	public long length;
		public FileInfo(InputStream in, long length)
		{
			this.inputStream = in;
			this.length = length;
		}
    }
}
