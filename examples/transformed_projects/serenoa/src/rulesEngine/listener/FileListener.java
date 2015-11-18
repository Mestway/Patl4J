
package rulesEngine.listener;





/**
 * Interface for listening to disk file changes.
 * @see FileMonitor
 * 
 * @author <a href="mailto:info@geosoft.no">GeoSoft</a>
 */   
public interface FileListener
{
  /**
   * Called when one of the monitored files are created, deleted
   * or modified.
   * 
   * @param file  File which has been changed.
   */
  void fileChanged ();
}
