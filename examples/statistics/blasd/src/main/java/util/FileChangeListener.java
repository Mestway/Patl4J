package util;

import java.util.Properties;

public interface FileChangeListener {
    /** Invoked when a file changes.   
     * @param fileName name of changed file.
     */
    public void fileChanged(String fileName);

    public Properties getProps();
}
