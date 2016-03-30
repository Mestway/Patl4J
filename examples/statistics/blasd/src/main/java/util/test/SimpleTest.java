package util.test;

import util.*;
import java.io.*;
import java.util.Properties;

public class SimpleTest implements FileChangeListener {
    private static final String testa = "testa";
    private static final String testb = "testb";
    private static final String testc = "testc";

    public SimpleTest() {
    }

    public void fileChanged(String fileName) {
        System.out.println("File Changed: " + fileName);
    }

    public Properties getProps() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) throws Exception {
	File a = new File(testa);
	a.createNewFile();
	File b = new File(testb);
	b.createNewFile();
	File c = new File(testc);
	c.createNewFile();

        SimpleTest t = new SimpleTest();
	FileMonitor m = FileMonitor.getInstance();
	m.addFileChangeListener(t, testa, (long) 3000);
	m.addFileChangeListener(t, testb, (long) 5000);
	m.addFileChangeListener(t, testc, (long) 7000);

	for (int i = 0; i < 16; i++) {
	    a.setLastModified(System.currentTimeMillis());
	    b.setLastModified(System.currentTimeMillis());
	    c.setLastModified(System.currentTimeMillis());
	    Thread.sleep(1000);
	}

	System.out.println();

	m.removeFileChangeListener(t, testa);
	for (int i = 0; i < 16; i++) {
	    a.setLastModified(System.currentTimeMillis());
	    b.setLastModified(System.currentTimeMillis());
	    c.setLastModified(System.currentTimeMillis());
	    Thread.sleep(1000);
	}

	System.out.println();
	m.removeFileChangeListener(t, testb);
	for (int i = 0; i < 16; i++) {
	    a.setLastModified(System.currentTimeMillis());
	    b.setLastModified(System.currentTimeMillis());
	    c.setLastModified(System.currentTimeMillis());
	    Thread.sleep(1000);
	}

	a.delete();
	b.delete();
	c.delete();
    }
}
