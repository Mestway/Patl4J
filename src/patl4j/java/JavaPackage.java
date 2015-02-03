package patl4j.java;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.util.ErrorManager;

public class JavaPackage {

	IPackageFragment packageFrag;
	ArrayList<JavaFile> files = new ArrayList<JavaFile>();
	
	public JavaPackage(IPackageFragment ipf) {
		packageFrag = ipf;
		// Package fragments include all packages in the classpath
		// We will only look at the package from the source folder
		// K_BINARY would include also included JARS, e.g. rt.jar
		try {
			for (ICompilationUnit f : ipf.getCompilationUnits()) {
				files.add(new JavaFile(f));
			}
		} catch (JavaModelException e) {
			ErrorManager.error("Cannot find CompilationUnit in package [" + ipf.getElementName() + "]");
			e.printStackTrace();
		}
	}
	
	public IPackageFragment getIPackageFrag() {
		return packageFrag;
	}
	
	public ArrayList<JavaFile> getFiles() {
		return files;
	}
	
	public void createNormalizedFiles() {
		for (JavaFile i : files) {
			try {
				packageFrag.createCompilationUnit(
						"_" + i.getCU().getElementName(), 
						i.getNormlizedAST().toString(), 
						false, 
						null);
			} catch (JavaModelException e) {
				ErrorManager.error("Current Name already exists!");
				e.printStackTrace();
			}
		}
	}	
}
