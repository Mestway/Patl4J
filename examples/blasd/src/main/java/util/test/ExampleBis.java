package util.test;

import util.FileChangeListener;
import util.FileMonitor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExampleBis implements FileChangeListener {
    private static final String PROPERTIES_FILE = "cabeceras.properties";

    private boolean useClasspath;
    private long checkPeriod;

    public ExampleBis(boolean b) {
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




      Properties props;
    private void reloadProperties() {
         props = new Properties();
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



    }


    public Properties getProps() {
        return props;
    }

    public void fileChanged(String fileName) {
        reloadProperties();
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

        ExampleBis ex = new ExampleBis(useClasspath);

        Thread.sleep(60000);
    }
}
