package patl4j.java;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.util.ErrorManager;

public class JavaProject {

	protected IJavaProject ijProject;
	protected ArrayList<JavaPackage> packages = new ArrayList<JavaPackage>();

	public JavaProject(IJavaProject p) {
		this.ijProject = p;
		try {
			for (IPackageFragment ipf : p.getPackageFragments()) {
				packages.add(new JavaPackage(ipf));
			}
		} catch (JavaModelException e) {
			ErrorManager.error("JavaProject@line23", "Cannot find valid Java package fragments for the given project [" + p.getElementName() + "]");
			e.printStackTrace();
		}
	}
	
	public IJavaProject getIJavaProject() {
		return ijProject;
	}
	
	public ArrayList<JavaPackage> getPackages() {
		return packages;
	}
	
}
