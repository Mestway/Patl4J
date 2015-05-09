package husacct.control.task;

import husacct.ServiceProvider;
import husacct.common.OSDetector;
import husacct.common.dto.ProjectDTO;
import husacct.control.domain.Workspace;
import husacct.control.presentation.log.ApplicationAnalysisHistoryOverviewFrame;
import husacct.control.task.configuration.ConfigurationManager;
import husacct.control.task.resources.IResource;
import husacct.control.task.resources.ResourceFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.swing.JOptionPane;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.dialogs.MessageDialog;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

public class ApplicationAnalysisHistoryLogController{

	private Logger logger = Logger.getLogger(ApplicationAnalysisHistoryLogController.class);
	private Workspace currentWorkspace;

	private MainController mainController;
	
	private File logFile = new File(OSDetector.getAppFolder() + ConfigurationManager.getProperty("ApplicationHistoryXMLFilename"));

	public ApplicationAnalysisHistoryLogController(MainController mainController){
		this.mainController = mainController;
		currentWorkspace = null;
	}
	
	public boolean logFileExists(){
		return logFile.exists();
	}
	
	public HashMap<String, HashMap<String, String>> getApplicationHistoryFromFile(String workspace, String application, ArrayList<ProjectDTO> projects){
		HashMap<String, HashMap<String, String>> output = new HashMap<String, HashMap<String, String>>();
		
		HashMap<String, Object> resourceData = new HashMap<String, Object>();
		resourceData.put("file", logFile);
		IResource xmlResource = ResourceFactory.get("xml");
		
		try {
			Document doc = xmlResource.load(resourceData);	
			Element xmlFileRootElement = doc.getRootElement(); 
			
			//Workspace
			for(Element workspaceElement : xmlFileRootElement.elements()){ 
				
				if(workspaceElement.attributeValue("name").equals(workspace)){ 
					
					//Application
					for(Element applicationElement : workspaceElement.elements()){ 
						if(applicationElement.attributeValue("name").equals(application)){ 
							
							//Given projects
							for(ProjectDTO project : projects){
								
								//Project
								for(Element projectElement : applicationElement.elements()){ 
									if(projectElement.attributeValue("name").equals(project.name)){ 
										
										//Analysis
										for(Element analysisElement : projectElement.elements()){ 
											
											//Analysis info
											HashMap<String, String> analysisInfo = new HashMap<String, String>();
											analysisInfo.put("application", applicationElement.attributeValue("name")); 
											analysisInfo.put("project", projectElement.attributeValue("name")); 
											for(Element analysisInfoElement : analysisElement.elements()){ 
												analysisInfo.put(analysisInfoElement.getName(), analysisInfoElement.getText()); 
											}
											
											//Add every analysis to hashmap
											output.put(analysisElement.attributeValue("timestamp"), analysisInfo); 
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug("Unable load application analysis history file: " + e.getMessage());
		}
		
		return output;
	}
	
	public int getNumberOfAnalyses(String workspace, String application, ArrayList<ProjectDTO> projects){
		HashMap<String, Object> resourceData = new HashMap<String, Object>();
		resourceData.put("file", logFile);
		IResource xmlResource = ResourceFactory.get("xml");
		
		int output = 0;
		
		try {
			Document doc = xmlResource.load(resourceData);	
			Element xmlFileRootElement = doc.getRootElement(); 
			
			//Workspace
			for(Element workspaceElement : xmlFileRootElement.elements()){ 
				
				if(workspaceElement.attributeValue("name").equals(workspace)){ 
					
					//Application
					for(Element applicationElement : workspaceElement.elements()){ 
						if(applicationElement.attributeValue("name").equals(application)){ 
							
							//Given projects
							for(ProjectDTO project : projects){
								
								//XML projects
								for(Element projectElement : applicationElement.elements()){ 
									if(projectElement.attributeValue("name").equals(project.name)){ 
										output += projectElement.elements().size(); 
									}
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.debug("Unable load application analysis history file: " + e.getMessage());
		}
		
		return output;
	}

	public void showApplicationAnalysisHistoryOverview() {
		if(logFileExists()){
			String workspace = "";
			String application = "";
			ArrayList<ProjectDTO> projects = new ArrayList<ProjectDTO>();
			
            Display display = new Display();
            Shell shell = new Shell(display);

			try{
				workspace = mainController.getWorkspaceController().getCurrentWorkspace().getName();
				application = ServiceProvider.getInstance().getDefineService().getApplicationDetails().name;
				projects = ServiceProvider.getInstance().getDefineService().getApplicationDetails().projects;
			}catch(Exception e){
				//JOptionPane.showMessageDialog(null, ServiceProvider.getInstance().getLocaleService().getTranslatedString("NoWorkspaceApplicationProjectIsOpen"), ServiceProvider.getInstance().getLocaleService().getTranslatedString("NoWorkspaceApplicationProjectIsOpenTitle"), JOptionPane.ERROR_MESSAGE);
                MessageDialog.openError(shell, "NoWorkspaceApplicationProjectIsOpen", "NoWorkspaceApplicationProjectIsOpenTitle");
				return;
			}
			
			if(getNumberOfAnalyses(workspace, application, projects)<1){
				//JOptionPane.showMessageDialog(null, ServiceProvider.getInstance().getLocaleService().getTranslatedString("NoApplicationAnalysisHistory"), ServiceProvider.getInstance().getLocaleService().getTranslatedString("NoApplicationAnalysisHistoryTitle"), JOptionPane.ERROR_MESSAGE);
                MessageDialog.openError(shell, "NoApplicationAnalysisHistory", "NoApplicationAnalysisHistoryTitle");

			}else{
				//TODO: Remove this demonstration code after 24-06-2013
				mainController.getActionLogController().addAction("Viewed Analysis History File: " + logFile.getAbsolutePath());
				new ApplicationAnalysisHistoryOverviewFrame(mainController);
			}
		}else{
			//JOptionPane.showMessageDialog(null, ServiceProvider.getInstance().getLocaleService().getTranslatedString("ApplicationAnalysisHistoryFileDoesntExist"), ServiceProvider.getInstance().getLocaleService().getTranslatedString("ApplicationAnalysisHistoryFileDoesntExistTitle"), JOptionPane.ERROR_MESSAGE);
            MessageDialog.openError(shell, "ApplicationAnalysisHistoryFileDoesntExist", "ApplicationAnalysisHistoryFileDoesntExistTitle");
		}
	}
	
	public Workspace getCurrentWorkspace(){
		return currentWorkspace;
	}
}
