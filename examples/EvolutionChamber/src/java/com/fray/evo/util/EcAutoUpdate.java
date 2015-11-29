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
/** 
 * Automatically downloads the newest version of the application and runs it.
 */
public class EcAutoUpdate extends SwingWorker<Void,Void> {
  private static final Logger logger=Logger.getLogger(EcAutoUpdate.class.getName());
  /** 
 * The format of the URL that points to the JAR file.
 */
  private static final String downloadUrlFormat="http://evolutionchamber.googlecode.com/files/evolutionchamber-version-%s.jar";
  /** 
 * The format of the filename that is given to the downloaded JAR file.
 */
  private static final String downloadedFileNameFormat="evolutionchamber-version-%s.jar";
  /** 
 * The format of the URL that contains the JAR file's checksum.
 */
  private static final String checksumUrlFormat="http://code.google.com/p/evolutionchamber/downloads/detail?name=evolutionchamber-version-%s.jar";
  /** 
 * The regular expression used to scrape the checksum from the webpage of the JAR file.
 */
  private static final Pattern checksumPattern=Pattern.compile("SHA1 Checksum: </th><td style=\"white-space:nowrap\"> ([0-9a-f]{40})",Pattern.CASE_INSENSITIVE);
  /** 
 * The URL of the project's download page.
 */
  private static final String downloadsPageUrl="http://code.google.com/p/evolutionchamber/downloads/list";
  /** 
 * A regular expression that matches against URLs to the various versions of the application.
 */
  private static final Pattern jarUrlRegex=Pattern.compile("evolutionchamber\\.googlecode\\.com/files/evolutionchamber-version-(\\d+)\\.jar");
  /** 
 * Whether or not there is a newer version available.
 */
  private boolean updateAvailable=false;
  /** 
 * Whether or not it is performing an update.
 */
  private boolean updating=false;
  /** 
 * The version number of the latest version. For example, "0017".
 */
  private String latestVersion="";
  /** 
 * The name of the downloaded JAR file.
 */
  private String jarFile="";
  /** 
 * Whether or not the checksum of the downloaded file matches the expected checksum.
 */
  private boolean checksumMatches=true;
  /** 
 * Allows the code calling this class to handle certain auto-updater events.
 */
  protected final Callback callback;
  /** 
 * Constructor.
 * @param ecVersion the version of the currently running application. For example, "0017".
 */
  public EcAutoUpdate(  String ecVersion,  Callback callback){
    EcAutoUpdate genVar4489;
    genVar4489=this;
    genVar4489.configureProxy();
    com.fray.evo.util.EcAutoUpdate genVar4490;
    genVar4490=this;
    this.callback=callback;
    com.fray.evo.util.EcAutoUpdate genVar4491;
    genVar4491=this;
    EcAutoUpdate genVar4492;
    genVar4492=this;
    genVar4491.latestVersion=genVar4492.findLatestVersion(ecVersion);
    com.fray.evo.util.EcAutoUpdate genVar4493;
    genVar4493=this;
    java.lang.String genVar4494;
    genVar4494=genVar4493.latestVersion;
    boolean genVar4495;
    genVar4495=genVar4494.equals(ecVersion);
    boolean genVar4496;
    genVar4496=!genVar4495;
    if (genVar4496) {
      com.fray.evo.util.EcAutoUpdate genVar4497;
      genVar4497=this;
      genVar4497.updateAvailable=true;
    }
 else {
      ;
    }
  }
  /** 
 * Allows the auto-update process to work if the user is behind a proxy.
 * @see http://stackoverflow.com/questions/376101/setting-jvm-jre-to-use-windows-proxy-automatically
 */
  private void configureProxy(){
    java.lang.String genVar4498;
    genVar4498="java.net.useSystemProxies";
    java.lang.String genVar4499;
    genVar4499="true";
    System.setProperty(genVar4498,genVar4499);
    try {
      java.net.ProxySelector genVar4500;
      genVar4500=ProxySelector.getDefault();
      java.net.URI genVar4501;
      genVar4501=new URI(downloadsPageUrl);
      List<Proxy> proxies;
      proxies=genVar4500.select(genVar4501);
      for (      Proxy proxy : proxies) {
        java.net.SocketAddress genVar4502;
        genVar4502=proxy.address();
        InetSocketAddress addr;
        addr=(InetSocketAddress)genVar4502;
        boolean genVar4503;
        genVar4503=addr == null;
        if (genVar4503) {
        }
 else {
          java.lang.String genVar4504;
          genVar4504="http.proxyHost";
          java.lang.String genVar4505;
          genVar4505=addr.getHostName();
          System.setProperty(genVar4504,genVar4505);
          java.lang.String genVar4506;
          genVar4506="http.proxyPort";
          int genVar4507;
          genVar4507=addr.getPort();
          java.lang.String genVar4508;
          genVar4508=Integer.toString(genVar4507);
          System.setProperty(genVar4506,genVar4508);
        }
      }
    }
 catch (    URISyntaxException e) {
      java.lang.String genVar4509;
      genVar4509="Download list URL is invalid.";
      logger.log(Level.SEVERE,genVar4509,e);
    }
  }
  /** 
 * Whether or not there is a newer version available.
 * @return true if a newer version is available, false if not
 */
  public boolean isUpdateAvailable(){
    return updateAvailable;
  }
  /** 
 * Whether or not it is performing an update.
 * @return true if an update is being performed, false if not.
 */
  public boolean isUpdating(){
    return updating;
  }
  /** 
 * Sets whether or not it is performing an update.
 * @param updating true if an update is being performed, false if not.
 */
  public void setUpdating(  boolean updating){
    com.fray.evo.util.EcAutoUpdate genVar4510;
    genVar4510=this;
    genVar4510.updating=updating;
  }
  /** 
 * Gets the version number of the latest version. For example, "0017".
 * @return the latest version number
 */
  public String getLatestVersion(){
    return latestVersion;
  }
  /** 
 * Whether or not the checksum of the downloaded file matches the expected checksum.
 * @return true if the file checksum matches the expected checksum
 */
  public boolean isChecksumMatches(){
    return checksumMatches;
  }
  /** 
 * Determines what the latest version of the application is.
 * @param ecVersion the version of the currently running application. For example, "0017".
 * @return the latest version number
 */
  private String findLatestVersion(  String ecVersion){
    String latestVersion;
    latestVersion=ecVersion;
    try {
      String html;
      html=null;
{
        URL url;
        url=new URL(downloadsPageUrl);
        java.net.URLConnection genVar4511;
        genVar4511=url.openConnection();
        HttpURLConnection conn;
        conn=(HttpURLConnection)genVar4511;
        java.io.InputStream genVar4512;
        genVar4512=conn.getInputStream();
        BufferedInputStream in;
        in=new BufferedInputStream(genVar4512);
        ByteArrayOutputStream out;
        out=new ByteArrayOutputStream();
        int read;
        while ((read=in.read()) != -1) {
          out.write(read);
        }
        in.close();
        byte[] genVar4513;
        genVar4513=out.toByteArray();
        html=new String(genVar4513);
      }
      int latest;
      latest=Integer.parseInt(ecVersion);
      Matcher m;
      m=jarUrlRegex.matcher(html);
      while (m.find()) {
        int genVar4514;
        genVar4514=1;
        java.lang.String genVar4515;
        genVar4515=m.group(genVar4514);
        int cur;
        cur=Integer.parseInt(genVar4515);
        boolean genVar4516;
        genVar4516=cur > latest;
        if (genVar4516) {
          latest=cur;
          int genVar4517;
          genVar4517=1;
          latestVersion=m.group(genVar4517);
        }
 else {
          ;
        }
      }
    }
 catch (    MalformedURLException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar4518;
      genVar4518=new PrintWriter(sw);
      e.printStackTrace(genVar4518);
      java.lang.String genVar4519;
      genVar4519=sw.toString();
      logger.severe(genVar4519);
    }
catch (    UnknownHostException e) {
      callback.updateCheckFailed();
    }
catch (    IOException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar4520;
      genVar4520=new PrintWriter(sw);
      e.printStackTrace(genVar4520);
      java.lang.String genVar4521;
      genVar4521=sw.toString();
      logger.severe(genVar4521);
    }
    return latestVersion;
  }
  /** 
 * Gets an inputstream to the file and the length of the file.
 * @param version the version to retrieve
 * @return an inputstream to the file and the length of the file
 * @throws IOException
 */
  protected FileInfo getFileInputStreamAndLength(  String version) throws IOException {
    java.lang.String genVar4522;
    genVar4522=String.format(downloadUrlFormat,version);
    URL downloadUrl;
    downloadUrl=new URL(genVar4522);
    URLConnection uc;
    uc=downloadUrl.openConnection();
    String contentType;
    contentType=uc.getContentType();
    int contentLength;
    contentLength=uc.getContentLength();
    java.lang.String genVar4523;
    genVar4523="text/";
    boolean genVar4524;
    genVar4524=contentType.startsWith(genVar4523);
    int genVar4525;
    genVar4525=1;
    int genVar4526;
    genVar4526=-genVar4525;
    boolean genVar4527;
    genVar4527=contentLength == genVar4526;
    boolean genVar4528;
    genVar4528=genVar4524 || genVar4527;
    if (genVar4528) {
      java.lang.String genVar4529;
      genVar4529="This is not a binary file.";
      java.io.IOException genVar4530;
      genVar4530=new IOException(genVar4529);
      throw genVar4530;
    }
 else {
      ;
    }
    java.io.InputStream genVar4531;
    genVar4531=uc.getInputStream();
    com.fray.evo.util.EcAutoUpdate.FileInfo genVar4532;
    genVar4532=new FileInfo(genVar4531,contentLength);
    return genVar4532;
  }
  @Override public Void doInBackground(){
    int progress;
    progress=0;
    EcAutoUpdate genVar4533;
    genVar4533=this;
    int genVar4534;
    genVar4534=0;
    genVar4533.setProgress(genVar4534);
    com.fray.evo.util.EcAutoUpdate genVar4535;
    genVar4535=this;
    genVar4535.updating=true;
    try {
      EcAutoUpdate genVar4536;
      genVar4536=this;
      String expectedChecksum;
      expectedChecksum=genVar4536.getExpectedChecksum(latestVersion);
      boolean download;
      download=true;
      com.fray.evo.util.EcAutoUpdate genVar4537;
      genVar4537=this;
      java.lang.String genVar4538;
      genVar4538=genVar4537.latestVersion;
      java.lang.String genVar4539;
      genVar4539=String.format(downloadedFileNameFormat,genVar4538);
      File file;
      file=new File(genVar4539);
      java.lang.String genVar4540;
      genVar4540="SHA-1";
      MessageDigest md;
      md=MessageDigest.getInstance(genVar4540);
      boolean genVar4541;
      genVar4541=file.exists();
      if (genVar4541) {
        FileInputStream in;
        in=new FileInputStream(file);
        byte[] buf;
        buf=new byte[1024];
        int read;
        while ((read=in.read(buf)) != -1) {
          int genVar4542;
          genVar4542=0;
          md.update(buf,genVar4542,read);
        }
        in.close();
        EcAutoUpdate genVar4543;
        genVar4543=this;
        byte[] genVar4544;
        genVar4544=md.digest();
        String fileChecksum;
        fileChecksum=genVar4543.convertToHex(genVar4544);
        boolean genVar4545;
        genVar4545=expectedChecksum != null && expectedChecksum.equalsIgnoreCase(fileChecksum);
        if (genVar4545) {
          download=false;
          checksumMatches=true;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
      if (download) {
        EcAutoUpdate genVar4546;
        genVar4546=this;
        FileInfo info;
        info=genVar4546.getFileInputStreamAndLength(latestVersion);
        String fileChecksum;
{
          FileOutputStream out;
          out=new FileOutputStream(file);
          InputStream raw;
          raw=info.inputStream;
          InputStream in;
          in=new BufferedInputStream(raw);
          byte[] buf;
          buf=new byte[1024];
          int bytesRead;
          bytesRead=0;
          int offset;
          offset=0;
          while ((bytesRead=in.read(buf)) != -1) {
            int genVar4547;
            genVar4547=0;
            md.update(buf,genVar4547,bytesRead);
            int genVar4548;
            genVar4548=0;
            out.write(buf,genVar4548,bytesRead);
            offset+=bytesRead;
            float genVar4549;
            genVar4549=(float)info.length;
            float genVar4550;
            genVar4550=offset / genVar4549;
            float genVar4551;
            genVar4551=(genVar4550);
            int genVar4552;
            genVar4552=100;
            float genVar4553;
            genVar4553=genVar4551 * genVar4552;
            float genVar4554;
            genVar4554=(genVar4553);
            progress=(int)genVar4554;
            EcAutoUpdate genVar4555;
            genVar4555=this;
            genVar4555.setProgress(progress);
          }
          EcAutoUpdate genVar4556;
          genVar4556=this;
          byte[] genVar4557;
          genVar4557=md.digest();
          fileChecksum=genVar4556.convertToHex(genVar4557);
          in.close();
          out.close();
          boolean genVar4558;
          genVar4558=offset != info.length;
          if (genVar4558) {
            java.lang.String genVar4559;
            genVar4559="Only read ";
            java.lang.String genVar4560;
            genVar4560=" bytes; Expected ";
            java.lang.String genVar4561;
            genVar4561=" bytes";
            java.lang.String genVar4562;
            genVar4562=genVar4559 + offset + genVar4560+ info.length+ genVar4561;
            java.io.IOException genVar4563;
            genVar4563=new IOException(genVar4562);
            throw genVar4563;
          }
 else {
            ;
          }
        }
        boolean genVar4564;
        genVar4564=expectedChecksum == null;
        boolean genVar4565;
        genVar4565=expectedChecksum.equalsIgnoreCase(fileChecksum);
        checksumMatches=genVar4564 || genVar4565;
      }
 else {
        ;
      }
      com.fray.evo.util.EcAutoUpdate genVar4566;
      genVar4566=this;
      genVar4566.jarFile=file.getName();
    }
 catch (    MalformedURLException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar4567;
      genVar4567=new PrintWriter(sw);
      e.printStackTrace(genVar4567);
      java.lang.String genVar4568;
      genVar4568=sw.toString();
      logger.severe(genVar4568);
    }
catch (    IOException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar4569;
      genVar4569=new PrintWriter(sw);
      e.printStackTrace(genVar4569);
      java.lang.String genVar4570;
      genVar4570=sw.toString();
      logger.severe(genVar4570);
    }
catch (    NoSuchAlgorithmException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar4571;
      genVar4571=new PrintWriter(sw);
      e.printStackTrace(genVar4571);
      java.lang.String genVar4572;
      genVar4572=sw.toString();
      logger.severe(genVar4572);
    }
    return null;
  }
  /** 
 * Scrapes the checksum for the JAR file of the given version from the website.
 * @param version the version of the application to get the checksum for
 * @return the checksum or null if it couldn't find the checksum
 * @throws IOException if there's a problem loading the page
 */
  public static String getExpectedChecksum(  String version) throws IOException {
    String pageChecksum;
    pageChecksum=null;
    java.lang.String genVar4573;
    genVar4573=String.format(checksumUrlFormat,version);
    URL checksumUrl;
    checksumUrl=new URL(genVar4573);
    java.net.URLConnection genVar4574;
    genVar4574=checksumUrl.openConnection();
    HttpURLConnection conn;
    conn=(HttpURLConnection)genVar4574;
    java.io.InputStream genVar4575;
    genVar4575=conn.getInputStream();
    BufferedInputStream in;
    in=new BufferedInputStream(genVar4575);
    ByteArrayOutputStream out;
    out=new ByteArrayOutputStream();
    int read;
    while ((read=in.read()) != -1) {
      out.write(read);
    }
    byte[] genVar4576;
    genVar4576=out.toByteArray();
    String html;
    html=new String(genVar4576);
    in.close();
    out.close();
    Matcher m;
    m=checksumPattern.matcher(html);
    boolean genVar4577;
    genVar4577=m.find();
    if (genVar4577) {
      int genVar4578;
      genVar4578=1;
      pageChecksum=m.group(genVar4578);
    }
 else {
      ;
    }
    return pageChecksum;
  }
  @Override public void done(){
    EcAutoUpdate genVar4579;
    genVar4579=this;
    int genVar4580;
    genVar4580=100;
    genVar4579.setProgress(genVar4580);
    if (checksumMatches) {
      java.lang.String genVar4581;
      genVar4581="java.home";
      java.lang.String genVar4582;
      genVar4582=System.getProperty(genVar4581);
      java.lang.String genVar4583;
      genVar4583="bin";
      java.lang.String genVar4584;
      genVar4584="java";
      String javaBin;
      javaBin=genVar4582 + File.separator + genVar4583+ File.separator+ genVar4584;
      java.lang.String genVar4585;
      genVar4585="-jar";
      com.fray.evo.util.EcAutoUpdate genVar4586;
      genVar4586=this;
      java.lang.String genVar4587;
      genVar4587=genVar4586.jarFile;
      String[] restartCmd;
      restartCmd=new String[]{javaBin,genVar4585,genVar4587};
      try {
        java.lang.Runtime genVar4588;
        genVar4588=Runtime.getRuntime();
        genVar4588.exec(restartCmd);
      }
 catch (      IOException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar4589;
        genVar4589=new PrintWriter(sw);
        e.printStackTrace(genVar4589);
        java.lang.String genVar4590;
        genVar4590=sw.toString();
        logger.severe(genVar4590);
      }
    }
 else {
      callback.checksumFailed();
    }
    int genVar4591;
    genVar4591=0;
    System.exit(genVar4591);
  }
  /** 
 * Converts an array of bytes to a hex string.
 * @see http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
 * @param data the data to convert
 * @return the hex string
 */
  private static String convertToHex(  byte[] data){
    StringBuilder buf;
    buf=new StringBuilder();
    int i=0;
    for (; i < data.length; i++) {
      byte genVar4592;
      genVar4592=data[i];
      int genVar4593;
      genVar4593=4;
      int genVar4594;
      genVar4594=genVar4592 >>> genVar4593;
      int genVar4595;
      genVar4595=(genVar4594);
      int genVar4596;
      genVar4596=0x0F;
      int halfbyte;
      halfbyte=genVar4595 & genVar4596;
      int two_halfs;
      two_halfs=0;
      do {
        boolean genVar4600;
        genVar4600=(0 <= halfbyte) && (halfbyte <= 9);
        if (genVar4600) {
          char genVar4601;
          genVar4601='0';
          int genVar4602;
          genVar4602=genVar4601 + halfbyte;
          int genVar4603;
          genVar4603=(genVar4602);
          char genVar4604;
          genVar4604=(char)genVar4603;
          buf.append(genVar4604);
        }
 else {
          char genVar4605;
          genVar4605='a';
          int genVar4606;
          genVar4606=10;
          int genVar4607;
          genVar4607=halfbyte - genVar4606;
          int genVar4608;
          genVar4608=(genVar4607);
          int genVar4609;
          genVar4609=genVar4605 + genVar4608;
          int genVar4610;
          genVar4610=(genVar4609);
          char genVar4611;
          genVar4611=(char)genVar4610;
          buf.append(genVar4611);
        }
        byte genVar4612;
        genVar4612=data[i];
        int genVar4613;
        genVar4613=0x0F;
        halfbyte=genVar4612 & genVar4613;
      }
 while (two_halfs++ < 1);
    }
    java.lang.String genVar4614;
    genVar4614=buf.toString();
    return genVar4614;
  }
  /** 
 * Allows the code calling this class to handle certain auto-updater events.
 * @author mike.angstadt
 */
public interface Callback {
    /** 
 * Called when the checksum of the downloaded JAR file did not match its expected checksum.
 */
    void checksumFailed();
    /** 
 * Called if there is a problem determining what the latest version is.
 */
    void updateCheckFailed();
  }
protected static class FileInfo {
    public InputStream inputStream;
    public long length;
    public FileInfo(    InputStream in,    long length){
      com.fray.evo.util.EcAutoUpdate.FileInfo genVar4615;
      genVar4615=this;
      genVar4615.inputStream=in;
      com.fray.evo.util.EcAutoUpdate.FileInfo genVar4616;
      genVar4616=this;
      genVar4616.length=length;
    }
  }
}
