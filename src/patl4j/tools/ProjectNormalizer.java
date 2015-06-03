package patl4j.tools;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.handlers.PatlOption;
import patl4j.java.JavaFile;
import patl4j.java.JavaPackage;
import patl4j.java.JavaProject;
import patl4j.util.ErrorManager;

public class ProjectNormalizer {
	
	// normalize a project
	public void normalize(JavaProject project, PatlOption option) {
		System.out.println("[[Normalize Start]] Project:" + project.getIJavaProject().getElementName());
		try {
			normalizePackage(project, option);
		} catch (JavaModelException e) {
			ErrorManager.error("ProjectNormalizer@line19","Failed to normalize the project");
			e.printStackTrace();
		}
	}

	private void normalizePackage(JavaProject javaProject, PatlOption option)
			throws JavaModelException {
		for (JavaPackage mypackage : javaProject.getPackages()) {
			
			// Only deal with the programs in the source library
			// Other packages are in the build path
			
			// First normalize all files in the package
			for (JavaFile f : mypackage.getFiles()) {
				f.normalizeAST();
			}
			
			// Then generate normalized files in the package
			if (option.packageIgnored(mypackage.getIPackageFrag().getElementName()))
				continue;
			if (mypackage.getIPackageFrag().getKind() == IPackageFragmentRoot.K_SOURCE) {
				mypackage.createNormalizedFiles(option);
			}
		}
	}
	
}
