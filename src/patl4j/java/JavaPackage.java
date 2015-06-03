package patl4j.java;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.handlers.PatlOption;
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
			ErrorManager.error("JavaPackage@line27", "Cannot find CompilationUnit in package [" + ipf.getElementName() + "]");
			e.printStackTrace();
		}
	}
	
	public IPackageFragment getIPackageFrag() {
		return packageFrag;
	}
	
	public ArrayList<JavaFile> getFiles() {
		return files;
	}
	
	public void createNormalizedFiles(PatlOption option) {
		for (JavaFile i : files) {
			if (option.fileIgnored(i.getCU().getElementName()))
				continue;
			try {
				// Temporarily generate the normalized-file to regenerate an AST with type binding
				ICompilationUnit icu = packageFrag.createCompilationUnit(
						i.getCU().getElementName(), 
						i.getNormalizedAST().toString() + "/*" + i.getOriginalASTString() + "*/",
						true, 
						null);
				i.reGenNormalizedAST(icu);
				
				// Now just put the original AST back to the file
				/* packageFrag.createCompilationUnit(
						i.getCU().getElementName(), 
						i.getOriginalASTString(), 
						true, 
						null);
				*/
			} catch (JavaModelException e) {
				ErrorManager.error("JavaPackage@line69", "Current File Name already exists!");
			}
		}
	}	
	
	public void generatedTransformedFiles(String fileName, String programBody) {
		// TODO: make it work again sometime
		int x= 2;
		if (x == 3 - 1) {
			System.out.println(programBody);
			return;
		}
		try {
			// Generate the normalized AST as a new file here for debugging purpose
			packageFrag.createCompilationUnit(
					"_Transformed_" + fileName, 
					programBody, 
					true, 
					null);
		} catch (JavaModelException e) {
			ErrorManager.error("JavaPackage@line69", "Current File Name already exists!");
			//e.printStackTrace();
		}
	}
}
	

