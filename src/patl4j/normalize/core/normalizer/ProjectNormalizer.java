package patl4j.normalize.core.normalizer;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.common.config.PatlOption;
import patl4j.common.java.JavaPackage;
import patl4j.common.java.JavaProject;
import patl4j.common.tools.ErrorManager;

public class ProjectNormalizer {
	
	// normalize a project
	public void normalize(JavaProject project) {
		PatlOption option = project.getOption();
		option.setAlreadyNormalized(false);
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
			
//			System.out.println("ProjectNormalizer >> method >> normalizePackage @29 start nomalize package:"+mypackage);
			
			// Only deal with the programs in the source library
			// Other packages are in the build path
			if (option.packageIgnored(mypackage.getIPackageFrag().getElementName()))
				continue;
			if (mypackage.getIPackageFrag().getKind() == IPackageFragmentRoot.K_SOURCE) {
				mypackage.createNormalizedFiles(option);
			}
		}
	}
	
}
