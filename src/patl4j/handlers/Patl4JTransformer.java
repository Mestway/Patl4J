package patl4j.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;

import patl4j.java.JavaProject;
import patl4j.java.JavaWorkspace;
import patl4j.tools.ProjectTransformer;
import patl4j.tools.ProjectNormalizer;

public class Patl4JTransformer extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Set log path
		try {
			System.setOut(new PrintStream(new FileOutputStream("D:\\workspace\\Patl4J\\log.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Loop over the projects
		for (JavaProject project : workspace.getProjects()) {
			//new ProjectPrinter().printJavaProjectInfo(project);
			
			PatlOption option = new PatlOption(project);
			
			if (option.ignored == true)
				continue;
			
			System.out.println("\n[[Transformation Start]] Project: " + project.getIJavaProject().getElementName()); 
			
			System.out.println("[[Normalization Begin]]");
			/* second step: normalize the client program */
			new ProjectNormalizer().normalize(project, option);
			System.out.println("[[Normlaization End]]");
			
			System.out.println("[[Collect Rules Begin]]");
			/* first step: collect transformation rules */
			ProjectTransformer projectTransformer = new ProjectTransformer(project, option);
			projectTransformer.printRules();
			System.out.println("[[Collect Rules End]]");
			
			/* third step: create matcher based on the rules */
			projectTransformer.transform();
		}
		
		System.out.println("[[Transformation End]]");
		
		return null;
	}
	
}