// A simple example to illustrate properties reloading.
package util.test;

import util.FileChangeListener;
import util.FileMonitor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Example implements FileChangeListener {
    private static final String PROPERTIES_FILE = "example.properties";

    private boolean useClasspath;
    private long checkPeriod;
    private String emailAddr;
    private int logLevel;

    public Example(boolean b) {
        this.useClasspath = b;
        reloadProperties();
    }

    public long getCheckPeriod() {
        return checkPeriod;
    }

    public synchronized void setCheckPeriod(long period) {
        log("Setting checkPeriod: " + period);
        checkPeriod = period;
        try {
            FileMonitor.getInstance().addFileChangeListener(this,
                    PROPERTIES_FILE,
                    checkPeriod);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getEmailAddress() {
        return emailAddr;
    }

    private synchronized void setEmailAddress(String email) {
        log("Setting emailAddress: " + email);
        emailAddr = email;
    }

    public int getLogLevel() {
        return logLevel;
    }

    private synchronized void setLogLevel(int level) {
        log("Setting log level: " + level);
        logLevel = level;
    }

    private void reloadProperties() {
        Properties props = new Properties();
        InputStream in;

        try {
            if (useClasspath) {
                in = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            } else {
                in = new FileInputStream(PROPERTIES_FILE);
            }

            props.load(in);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setCheckPeriod(Long.parseLong(props.getProperty("checkPeriod")));
        setEmailAddress(props.getProperty("emailAddress"));
        setLogLevel(Integer.parseInt(props.getProperty("logLevel")));
    }

    public void fileChanged(String fileName) {
        reloadProperties();
    }

    public Properties getProps() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void log(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) throws Exception {
        boolean useClasspath = false;

        try {
            useClasspath = Boolean.valueOf(args[0]).booleanValue();
        }
        catch (Exception e) {
            System.out.println("Usage: java Example <true|false>");
            System.exit(1);
        }

        Example ex = new Example(useClasspath);

        Thread.sleep(60000);
    }
}
