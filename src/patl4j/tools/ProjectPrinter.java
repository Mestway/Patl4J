package patl4j.tools;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

import patl4j.java.JavaFile;
import patl4j.java.JavaPackage;
import patl4j.java.JavaProject;
import patl4j.util.ErrorManager;

/**
 * Print the Java project informations
**/
public class ProjectPrinter {
	
	public void printJavaProjectInfo(JavaProject project) {
		System.out.println("Working in project " + project.getIJavaProject().getElementName());
		try {
			printJavaPackageInfo(project);
		} catch (JavaModelException e) {
			ErrorManager.error("Failed to print java package info");
			e.printStackTrace();
		}
	}

	private void printJavaPackageInfo(JavaProject javaProject)
			throws JavaModelException {
		for (JavaPackage mypackage : javaProject.getPackages()) {
			// Only print the program in the source library
			// Other packages are in the build path
			if (mypackage.getIPackageFrag().getKind() == IPackageFragmentRoot.K_SOURCE) {
				System.out.println("Package " + mypackage.getIPackageFrag().getElementName());
				printJavaFileInfo(mypackage);
				mypackage.createNormalizedFiles();
			}
		}
	}

	private void printJavaFileInfo(JavaPackage mypackage)
			throws JavaModelException {
		for (JavaFile file : mypackage.getFiles()) {
			printFileDetails(file);
		}
	}

	private void printFileDetails(JavaFile file) throws JavaModelException {
		System.out.println("Source file " + file.getCU().getElementName());
		Document doc = new Document(file.getCU().getSource());
		System.out.println("Has number of lines: " + doc.getNumberOfLines());
		printIMethods(file.getCU());
		System.out.println("--------------------------------------------");
		System.out.println(file.getAST());
		System.out.println("--------------------------------------------");
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println(file.getNormlizedAST());
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::");
	}
	
	private void printIMethods(ICompilationUnit unit) throws JavaModelException {
		IType[] allTypes = unit.getAllTypes();
		for (IType type : allTypes) {
			printIMethodDetails(type);
		}
	}

	private void printIMethodDetails(IType type) throws JavaModelException {
		IMethod[] methods = type.getMethods();
		for (IMethod method : methods) {
			System.out.println("Method name " + method.getElementName());
			System.out.println("Signature " + method.getSignature());
			System.out.println("Return Type " + method.getReturnType());
		}
	}
}
