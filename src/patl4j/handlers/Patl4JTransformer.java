package patl4j.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import patl4j.java.JavaProject;
import patl4j.java.JavaWorkspace;
import patl4j.tools.ProjectTransformer;
import patl4j.tools.ProjectNormalizer;

public class Patl4JTransformer extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Set output to log files
		MessageConsole console = findConsole("Patl4J:console");
		MessageConsoleStream stream = console.newMessageStream();
		System.setErr(new PrintStream(stream));
		System.setOut(new PrintStream(stream));
		
		/*PrintStream ps, ps2;
		try {
			ps = new PrintStream(new FileOutputStream(".\\patllog-outlog"));
			System.setOut(ps);
			ps2 = new PrintStream(new FileOutputStream(".\\patllog-errlog"));
			System.setErr(ps2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		// Read in all projects with all packages and java files
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over the projects
		for (JavaProject project : workspace.getProjects()) {
			//new ProjectPrinter().printJavaProjectInfo(project);
			
			System.out.println("\n[[Transformation Start]] Project: " + project.getIJavaProject().getElementName()); 
			
			System.out.println("[[Normalization Begin]]");
			/* second step: normalize the client program */
			new ProjectNormalizer().normalize(project);
			System.out.println("[[Normlaization End]]");
			
			System.out.println("[[Collect Rules Begin]]");
			/* first step: collect transformation rules */
			ProjectTransformer projectTransformer = new ProjectTransformer(project);
			projectTransformer.printRules();
			System.out.println("[[Collect Rules End]]");
			
			/* third step: create matcher based on the rules */
			projectTransformer.transform();
		}
		
		System.out.println("[[Transformation End]]");
		
		return null;
	}
	
	   private MessageConsole findConsole(String name) {
		      ConsolePlugin plugin = ConsolePlugin.getDefault();
		      IConsoleManager conMan = plugin.getConsoleManager();
		      IConsole[] existing = conMan.getConsoles();
		      for (int i = 0; i < existing.length; i++)
		         if (name.equals(existing[i].getName()))
		            return (MessageConsole) existing[i];
		      //no console found, so create a new one
		      MessageConsole myConsole = new MessageConsole(name, null);
		      conMan.addConsoles(new IConsole[]{myConsole});
		      return myConsole;
		   }
	
}