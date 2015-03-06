package patl4j.handlers;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.java.JavaProject;

public class PatlOption {
	
	boolean ignored = true;
	// only work for java program and patl program
	List<String> ignoredFiles = new ArrayList<String>();
	
	List<String> ignoredPackages = new ArrayList<String>();
	
	public PatlOption(JavaProject project) {
		try {
			for (Object i : project.getIJavaProject().getNonJavaResources()) {
				if (i instanceof IFile) {
					System.out.println("--The name " + ((IFile)i).getName() + "--name");
					if (((IFile)i).getName().equals("patl.option")) {
						ignored = false;
						extractConfig((IFile)i);
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	// Damn! I need to add the class path in plugin.xml to make it run!!
	private void extractConfig(IFile config) {
		try {
    	    SAXReader saxReader=new SAXReader();
    	    Document document = saxReader.read(config.getContents());
    	    Element root = document.getRootElement();
    	    
    	    @SuppressWarnings("unchecked")
			List<Element> ignoreList = ((Element) root.elements("ignore").get(0)).elements();
    	    for (Element i : ignoreList) {
    	    	if (i.getName().equals("file"))
    	    		ignoredFiles.add(i.getText());
    	    	if (i.getName().equals("package"))
    	    		ignoredPackages.add(i.getText());
    	    }
    	 }
    	 catch (Exception exception) {
    	    exception.printStackTrace();
    	 }
	}

	public boolean fileIgnored(String filename) {
		for (String i : ignoredFiles) {
			if (i.equals(filename)) {
				return true;
			}
		}
		return false;
	}

	public boolean packageIgnored(String packagename) {
		for (String i : ignoredPackages) {
			if (i.equals(packagename)) {
				return true;
			}
		}
		return false;
	}
	
}
