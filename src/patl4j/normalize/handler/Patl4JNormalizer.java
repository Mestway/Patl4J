package patl4j.normalize.handler;

import java.io.PrintStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import patl4j.common.java.JavaProject;
import patl4j.common.java.JavaWorkspace;
import patl4j.common.tools.Console;
import patl4j.normalize.core.normalizer.ProjectNormalizer;

public class Patl4JNormalizer extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		
		// Set output to log files
		MessageConsole console = Console.findConsole("Patl4J:console");
		MessageConsoleStream stream = console.newMessageStream();
		System.setErr(new PrintStream(stream));
		System.setOut(new PrintStream(stream));
		
		System.out.println("=============Excuting Patl4JNormalizer===============");
		
		// Read in all projects with all packages and java files
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over the projects
		for (JavaProject project : workspace.getProjects()) {
			
			System.out.println("[Normalization ["+project.getIJavaProject().getElementName()+"] Begin]");
			/* second step: normalize the client program */
			new ProjectNormalizer().normalize(project);
			
			System.out.println("[[Normlaization ["+project.getIJavaProject().getElementName()+"] End]]");
			
		}
		
		System.out.println("=============Finish Patl4JNormalizer===============");
		
		return null;
	}

}
