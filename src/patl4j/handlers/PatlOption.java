package patl4j.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.java.JavaProject;

public class PatlOption {
	
	boolean ignored = true;
	// only work for java program and patl program
	List<String> ignoredFiles = new ArrayList<String>();
	List<String> ignoredPackages = new ArrayList<String>();
	
	// The list of new APIs, which are supposed to be added to the import list
	List<String> newAPINames = new ArrayList<String>();

	List<String> classPath = new LinkedList<String>();
	
	// For soot config
	String mainClassName = "";
	
	// Either windows, mac or linux
	String platform = "mac";
	
	// Filter by exclude, 
	boolean filterByExclude = true; 
	
	boolean outputToFile = false;
	
	// Changed to be built from IJavaProject instead of JavaProject
	public PatlOption(IJavaProject project) {
		try {
			for (Object i : project.getNonJavaResources()) {
				if (i instanceof IFile) {
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
	
	// To add a library into build path, we should also add the class path into plugin.xml.
	private void extractConfig(IFile config) {
		try {
    	    SAXReader saxReader=new SAXReader();
    	    Document document = saxReader.read(config.getContents());
    	    Element root = document.getRootElement();
    	    
    	    for (Iterator i = root.elementIterator(); i.hasNext();) {
    	    	Element e = (Element) i.next();
    	    	if (e.getName().equals("classPath")) {
        	    	this.classPath.add(e.getText());
    	    	} else if (e.getName().equals("platform")) {
    	    		this.platform = e.getText();
    	    	} else if (e.getName().equals("output")) {
    	    		if (e.getText().equals("file")) {
    	    			this.outputToFile = true;
    	    		}
    	    	} else if (e.getName().equals("filterMode")) {
    	    		if (e.getText().equals("include"))
    	    			this.filterByExclude = false;
    	    	} else if (e.getName().equals("mainClassName")) {
    	    		this.mainClassName = e.getText();
    	    	}
    	    }
    	    
    	    @SuppressWarnings("unchecked")
			List<Element> ignoreList = ((Element) root.elements("ignore").get(0)).elements();
    	    for (Element i : ignoreList) {
    	    	if (i.getName().equals("file"))
    	    		ignoredFiles.add(i.getText());
    	    	if (i.getName().equals("package"))
    	    		ignoredPackages.add(i.getText());
    	    }
    	    
    	    @SuppressWarnings("unchecked")
			List<Element> newAPIs = ((Element) root.elements("libraries").get(0)).elements();
    	    for (Element i : newAPIs) {
    	    	if (i.getName().equals("lib")) {
    	    		newAPINames.add(i.getText());
    	    	}
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
			if (packagename.startsWith(i)) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getClassPath() {
		return this.classPath;
	}
	
	public String getPlatform() {
		return this.platform;
	}
	
	public boolean outputToFile() {
		return this.outputToFile;
	}
	
	public boolean projectIgnored() {
		return this.ignored;
	}
	
	public String getMainClassName() {
		return this.mainClassName;
	}
	
	@Override
	public String toString() {
		String result = "<Ignored>\n";
		for (String i : this.ignoredFiles) 
			result += "  File: " + i + "\n";
		for (String i : this.ignoredPackages)
			result += "  Package: " + i + "\n";
		result += "<ClassPath>\n";
		for (String i : this.classPath)
			result += "  t" + i;
		return result;
	}
}
