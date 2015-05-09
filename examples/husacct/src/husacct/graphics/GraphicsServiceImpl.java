package husacct.graphics;

import husacct.common.savechain.ISaveable;
import husacct.common.services.ObservableService;
import husacct.graphics.task.AnalysedController;
import husacct.graphics.task.DefinedController;
import husacct.graphics.task.DrawingController;
import husacct.graphics.util.DrawingDetail;
import husacct.graphics.util.DrawingLayoutStrategy;

import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class GraphicsServiceImpl extends ObservableService implements
		IGraphicsService, ISaveable {
	
	private AnalysedController	analysedController;
	private DefinedController	definedController;
	protected Logger			logger							= Logger.getLogger(GraphicsServiceImpl.class);
	
	public static final String	workspaceServiceName			= "ArchitecureGraphicsService";
	
	public static final String	workspaceAnalysedControllerName	= "analysedController";
	
	public static final String	workspaceDefinedControllerName	= "definedController";
	
	public static final String	workspaceShowDependencies		= "showDependencies";
	
	public static final String	workspaceShowViolations			= "showViolations";
	
	public static final String	workspaceSmartLines				= "smartLines";
	
	public static final String	workspaceLayoutStrategy			= "layoutStrategy";
	
	public GraphicsServiceImpl() {
		
	}
	
	private void createControllers() {
		if (analysedController == null) analysedController = new AnalysedController();
		if (definedController == null) definedController = new DefinedController();
	}
	
	@Override
	public void drawAnalysedArchitecture() {
		createControllers();
		analysedController.drawArchitecture(DrawingDetail.WITHOUT_VIOLATIONS);
	}
	
	@Override
	public void drawAnalysedArchitectureWithViolations() {
		createControllers();
		analysedController.drawArchitecture(DrawingDetail.WITH_VIOLATIONS);
	}
	
	@Override
	public void drawDefinedArchitecture() {
		createControllers();
		definedController.drawArchitecture(DrawingDetail.WITHOUT_VIOLATIONS);
	}
	
	@Override
	public void drawDefinedArchitectureWithViolations() {
		createControllers();
		definedController.drawArchitecture(DrawingDetail.WITH_VIOLATIONS);
	}
	
	@Override
	public JInternalFrame getAnalysedArchitectureGUI() {
		createControllers();
		return analysedController.getGUI();
	}
	
	@Override
	public JInternalFrame getDefinedArchitectureGUI() {
		createControllers();
		return definedController.getGUI();
	}
	
	@Override
	public Element getWorkspaceData() { 
		createControllers();
		Element data = DocumentHelper.createElement(workspaceServiceName); 
		
		data.add(getWorkspaceDataForController( 
				workspaceAnalysedControllerName, analysedController));
		data.add(getWorkspaceDataForController( 
				workspaceDefinedControllerName, definedController));
		
		return data;
	}
	
	private Element getWorkspaceDataForController(String controllerName, 
			DrawingController controller) {
		Element controllerElement = DocumentHelper.createElement(controllerName); 
		controllerElement.addAttribute(workspaceShowDependencies, "" 
				+ controller.areDependenciesShown());
		controllerElement.addAttribute(workspaceShowViolations, 
				"" + controller.areViolationsShown());
		controllerElement.addAttribute(workspaceSmartLines, 
				"" + controller.areSmartLinesOn());
		controllerElement.addAttribute(workspaceSmartLines, 
				"" + controller.areSmartLinesOn());
		controllerElement.addAttribute(workspaceLayoutStrategy, controller 
				.getLayoutStrategy().toString());
		return controllerElement;
	}
	
	private boolean isActive(Element controllerElement, String attribute) { 
		return Boolean.parseBoolean(controllerElement.attribute(attribute) 
				.getValue());
	}
	
	@Override
	public void loadWorkspaceData(Element workspaceData) { 
		createControllers();
		try {
			Element analysedControllerElement = workspaceData 
					.element(workspaceAnalysedControllerName);
			loadWorkspaceDataForController(analysedController,
					analysedControllerElement);
		} catch (Exception e) {
			logger.error("Error importing the workspace for analyse.", e);
		}
		try {
			Element definedControllerElement = workspaceData 
					.element(workspaceDefinedControllerName);
			loadWorkspaceDataForController(definedController,
					definedControllerElement);
		} catch (Exception e) {
			logger.error("Error importing the workspace for define.", e);
		}
	}
	
	private void loadWorkspaceDataForController(DrawingController controller, Element data) { 
		if (isActive(data, workspaceShowDependencies)) controller.showDependencies();
		else
			controller.hideDependencies();
		
		if (isActive(data, workspaceShowViolations)) controller.showViolations();
		else
			controller.hideViolations();
		
		if (isActive(data, workspaceSmartLines)) controller.showSmartLines();
		else
			controller.hideSmartLines();
		
		DrawingLayoutStrategy selectedStrategy = null;
		for (DrawingLayoutStrategy strategy : DrawingLayoutStrategy.values())
			if (strategy.toString().equals( data.attribute(workspaceLayoutStrategy).getValue())) 
				selectedStrategy = strategy;
		if (null != selectedStrategy) 
			controller.changeLayoutStrategy(selectedStrategy);
	}
}
