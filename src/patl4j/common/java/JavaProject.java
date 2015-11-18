package patl4j.common.java;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.common.config.PatlOption;
import patl4j.common.tools.ErrorManager;

public class JavaProject {

	protected IJavaProject ijProject;
	protected ArrayList<JavaPackage> packages = new ArrayList<JavaPackage>();
	protected PatlOption option;

	public JavaProject(IJavaProject p, PatlOption option) {
		this.ijProject = p;
		this.option = option;
		try {
			for (IPackageFragment ipf : p.getPackageFragments()) {
				if (!option.packageIgnored(ipf.getElementName()))
					packages.add(new JavaPackage(ipf, option));
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
	
	public PatlOption getOption() {
		return option;
	}
}
