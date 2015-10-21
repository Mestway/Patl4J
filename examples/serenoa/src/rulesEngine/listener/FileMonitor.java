
package rulesEngine.listener;



import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;





/**
 * Class for monitoring changes in disk files.
 * Usage:
 *
 *    1. Implement the FileListener interface.
 *    2. Create a FileMonitor instance.
 *    3. Add the file(s)/directory(ies) to listen for.
 *
 * fileChanged() will be called when a monitored file is created,
 * deleted or its modified time changes.
 *
 * @author <a href="mailto:info@geosoft.no">GeoSoft</a>
 */   
public class FileMonitor
{
  private Timer       timer_;
  private HashMap<File,Long>     files_;       // File -> Long
  private Collection<FileListener>  listeners_;   // of WeakReference(FileListener)
 

  /**
   * Create a file monitor instance with specified polling interval.
   * 
   * @param pollingInterval  Polling interval in milli seconds.
   */
  public FileMonitor (long pollingInterval)
  {
    files_     = new HashMap<File,Long>();
    listeners_ = new ArrayList<FileListener>();

    timer_ = new Timer (true);
    timer_.schedule (new FileMonitorNotifier(), 0, pollingInterval);
  }


  
  /**
   * Stop the file monitor polling.
   */
  public void stop()
  {
    timer_.cancel();
  }
  

  /**
   * Add file to listen for. File may be any java.io.File (including a
   * directory) and may well be a non-existing file in the case where the
   * creating of the file is to be trepped.
   * <p>
   * More than one file can be listened for. When the specified file is
   * created, modified or deleted, listeners are notified.
   * 
   * @param file  File to listen for.
   */
  public void addFile (File file)
  {
    if (!files_.containsKey (file)) {
      long modifiedTime = file.exists() ? file.lastModified() : -1;
      files_.put (file, new Long (modifiedTime));
    }
  }

  

  /**
   * Remove specified file for listening.
   * 
   * @param file  File to remove.
   */
  public void removeFile (File file)
  {
    files_.remove (file);
  }


  
  /**
   * Add listener to this file monitor.
   * 
   * @param fileListener  Listener to add.
   */
  public void addListener (FileListener fileListener)
  {
    // Don't add if its already there
    for (Iterator<FileListener> i = listeners_.iterator(); i.hasNext(); ) {
     FileListener listener = i.next();
      if (listener == fileListener)
        return;
    }

    // Use WeakReference to avoid memory leak if this becomes the
    // sole reference to the object.
    listeners_.add (fileListener);
  }


  
  /**
   * Remove listener from this file monitor.
   * 
   * @param fileListener  Listener to remove.
   */
  public void removeListener (FileListener fileListener)
  {
    for (Iterator<FileListener> i = listeners_.iterator(); i.hasNext(); ) {
    	
      FileListener listener = i.next();
      if (listener == fileListener) {
        i.remove();
        break;
      }
    }
  }


  
  /**
   * This is the timer thread which is executed every n milliseconds
   * according to the setting of the file monitor. It investigates the
   * file in question and notify listeners if changed.
   */
  private class FileMonitorNotifier extends TimerTask
  {
    public void run()
    {
      // Loop over the registered files and see which have changed.
      // Use a copy of the list in case listener wants to alter the
      // list within its fileChanged method.
      Collection<File> files = new ArrayList<File> (files_.keySet());
      
      
      for (Iterator<File> i = files.iterator(); i.hasNext(); ) {
        File file = (File) i.next();
        long lastModifiedTime = ((Long) files_.get (file)).longValue();
        long newModifiedTime  = file.exists() ? file.lastModified() : -1;
        
        // Chek if file has changed
        if (newModifiedTime != lastModifiedTime) {

          // Register new modified time
          files_.put (file, new Long (newModifiedTime));

          // Notify listeners
          for (Iterator<FileListener> j = listeners_.iterator(); j.hasNext(); ) {
        	 FileListener listener = j.next();
             listener.fileChanged();
          }
        }
      }
    }
  }

 
}
