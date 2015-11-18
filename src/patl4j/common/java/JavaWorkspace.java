package patl4j.common.java;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import patl4j.common.config.PatlOption;
import patl4j.common.tools.ErrorManager;

public class JavaWorkspace {
	
	/**
	* 	The Java Project Structures as the following:
	* 	JavaWorkspace 
	* 		--> JavaProject
	* 			--> JavaPackage
	* 				--> JavaFile
	*/
	IWorkspace workspace;
	ArrayList<JavaProject> projects = new ArrayList<JavaProject>();
	
	public JavaWorkspace(IWorkspace _workspace) {
		this.workspace = _workspace;
		
		// Get the root of the workspace
		IWorkspaceRoot root = this.workspace.getRoot();
		
		// Get all projects in the workspace
		IProject[] iprojects = root.getProjects();
		
		for (IProject project : iprojects) {
			
			if (!project.isOpen()) {
				System.out.println("[JavaWorkspace@44]Project \'" + project.getName() + "\' is closed.");
				continue;
			}
			
			// Check if we have a Java project
			try {
				if (project.isNatureEnabled("org.eclipse.jdt.core.javanature") && project.isOpen()) {
					IJavaProject javaProject = JavaCore.create(project);
					PatlOption option = new PatlOption(javaProject);
					JavaProject newProject = new JavaProject(javaProject, option);
					if (option.projectIgnored() == true)
						continue;
					projects.add(newProject);
				}
			} catch (CoreException e) {
				ErrorManager.error("JavaWorkspace@line48", "Cannot create java project");
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<JavaProject> getProjects() {
		return projects;
	}
}
