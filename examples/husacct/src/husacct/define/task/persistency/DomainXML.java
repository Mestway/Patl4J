package husacct.define.task.persistency;

import java.util.ArrayList;

import husacct.define.domain.Application;
import husacct.define.domain.Project;
import husacct.define.domain.SoftwareArchitecture;
import husacct.define.domain.appliedrule.AppliedRuleStrategy;
import husacct.define.domain.module.ModuleStrategy;
import husacct.define.domain.module.modules.Layer;
import husacct.define.domain.services.AppliedRuleDomainService;
import husacct.define.domain.softwareunit.SoftwareUnitDefinition;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class DomainXML {
    private SoftwareArchitecture softwareArchitecture;
	private AppliedRuleDomainService ruleService = new AppliedRuleDomainService();
    private Boolean parseLogical = true;

    public DomainXML(SoftwareArchitecture sa) {
    	softwareArchitecture = sa;
    }

    public Element getApplicationInXML(Application App) { 
		Element XMLApplication = DocumentHelper.createElement("Application"); 
	
		Element applicationName = DocumentHelper.createElement("name"); 
	
		//applicationName.add(App.getName());
		applicationName.addText(App.getName()); 
		XMLApplication.add(applicationName); 
	
		Element applicationVersion = DocumentHelper.createElement("version"); 
	
		applicationVersion.addText(App.getVersion()); 
	
		XMLApplication.add(applicationVersion); 
	
		Element applicationProjects = DocumentHelper.createElement("projects"); 
		for (Project project : App.getProjects()) {
		    applicationProjects.add(getProjectInXML(project)); 
		}
		XMLApplication.add(applicationProjects); 
	
		XMLApplication.add(getSoftwareArchitectureInXML()); 
	
		return XMLApplication;
    }

    public Element getAppliedRuleInXML(AppliedRuleStrategy AR) { 
		Element XMLAppliedRule = DocumentHelper.createElement("AppliedRule"); 
		
		Element ruleId = DocumentHelper.createElement("id"); 
		ruleId.addText(Long.toString(AR.getId())); 
		XMLAppliedRule.add(ruleId); 
	
		Element ruleType = DocumentHelper.createElement("type"); 
		ruleType.addText(AR.getRuleTypeKey()); 
		XMLAppliedRule.add(ruleType); 
	
		if (AR.getModuleFrom() instanceof ModuleStrategy) {
		    Element moduleFrom = DocumentHelper.createElement("moduleFrom"); 
		    long fromId = AR.getModuleFrom().getId();
		    moduleFrom.addText(Long.toString(fromId)); 
		    XMLAppliedRule.add(moduleFrom); 
		}
	
		if (AR.getModuleTo() instanceof ModuleStrategy) {
		    Element moduleTo = DocumentHelper.createElement("moduleTo"); 
		    long toId = AR.getModuleTo().getId();
		    moduleTo.addText(Long.toString(toId)); 
		    XMLAppliedRule.add(moduleTo); 
		}
	
		Element ruleEnabled = DocumentHelper.createElement("enabled"); 
		if (AR.isEnabled()) {
		    ruleEnabled.addText("true"); 
		} else {
		    ruleEnabled.addText("false"); } 
		XMLAppliedRule.add(ruleEnabled); 
	
		Element ruleDescription = DocumentHelper.createElement("description"); 
		ruleDescription.addText(AR.getDescription()); 
		XMLAppliedRule.add(ruleDescription); 
	
		Element ruleRegex = DocumentHelper.createElement("regex"); 
		ruleRegex.addText(AR.getRegex()); 
		XMLAppliedRule.add(ruleRegex); 
	
		Element dependencies = DocumentHelper.createElement("dependencies"); 
		if (AR.getDependencyTypes().length > 0) {
		    for (int i = 0; i < AR.getDependencyTypes().length; i++) {
			Element dependency = DocumentHelper.createElement("dependency"); 
			dependency.addText(AR.getDependencyTypes()[i].toString()); 
			dependencies.add(dependency); 
		    }
		}
		XMLAppliedRule.add(dependencies); 
		
		Element isException = DocumentHelper.createElement("isException"); 
		if (AR.isException()) {
			isException.addText("true"); 
		} else {
			isException.addText("false"); } 
		XMLAppliedRule.add(isException); 
		
		if (AR.getParentAppliedRule() instanceof AppliedRuleStrategy) {
		    Element parentAppliedRuleId = DocumentHelper.createElement("parentAppliedRuleId"); 
		    long parentId = AR.getParentAppliedRule().getId(); 
		    parentAppliedRuleId.addText(Long.toString(parentId)); 
		    XMLAppliedRule.add(parentAppliedRuleId); 
		}
	
		return XMLAppliedRule;
    }

    public Element getModuleInXML(ModuleStrategy module) { 
    	Element xmlModule = DocumentHelper.createElement("ModuleStrategy");

		Element moduleType = DocumentHelper.createElement("type"); 
		moduleType.addText(module.getClass().getSimpleName()); 
		xmlModule.add(moduleType); 
	
		Element moduleDescription = DocumentHelper.createElement("description"); 
		moduleDescription.addText(module.getDescription()); 
		xmlModule.add(moduleDescription); 
	
		Element moduleId = DocumentHelper.createElement("id"); 
		moduleId.addText(Long.toString(module.getId())); 
		xmlModule.add(moduleId); 
	
		Element moduleName = DocumentHelper.createElement("name"); 
		moduleName.addText(module.getName()); 
		xmlModule.add(moduleName); 
	
		/**
		 * build extra elements based on type (ModuleStrategy is generic)
		 */
		if (module.getClass().getSimpleName().toLowerCase().equals("layer")) {
		    Element moduleLevel = DocumentHelper.createElement("HierarchicalLevel"); 
		    moduleLevel.addText("" + ((Layer) module).getHierarchicalLevel()); 
		    xmlModule.add(moduleLevel); 
		}
	
		/**
		 * Check for units and add them to the XML root of this element
		 */
		if (module.getUnits().size() > 0) {
		    Element units = DocumentHelper.createElement("SoftwareUnitDefinitions"); 
		    Element physicalPaths = DocumentHelper.createElement("PhysicalPaths"); 
		    for (SoftwareUnitDefinition SUD : module.getUnits()) {
		    	units.add(getSoftwareUnitDefinitionInXML(SUD)); 
		    	physicalPaths.add(getPhysicalPathInXML(SUD.getName())); 
		    }
		    xmlModule.add(units); 
		    xmlModule.add(physicalPaths); 
		}
	
		/**
		 * Check for submodules and add them to the root of the parent
		 * (recursive call)
		 */
		if (module.getSubModules().size() > 0) {
		    Element subModule = DocumentHelper.createElement("SubModules"); 
		    for (ModuleStrategy m : module.getSubModules()) {
		    	subModule.add(getModuleInXML(m)); 
		    }
		    xmlModule.add(subModule); 
		}
	
		return xmlModule;
    }

    public Boolean getParseLogical() {
    	return parseLogical;
    }

    public Element getPhysicalPathInXML(String path) { 
		Element XMLPath = DocumentHelper.createElement("path"); 
		XMLPath.addText(path); 
		return XMLPath;
    }

    public Element getProjectInXML(Project project) { 
		Element XMLProject = DocumentHelper.createElement("Project"); 
	
		Element projectName = DocumentHelper.createElement("name"); 
		projectName.addText(project.getName()); 
		XMLProject.add(projectName); 
	
		Element projectPaths = DocumentHelper.createElement("paths"); 
		for (String path : project.getPaths()) {
		    Element projectPath = DocumentHelper.createElement("path"); 
		    projectPath.addText(path); 
		    projectPaths.add(projectPath); 
		}
		XMLProject.add(projectPaths); 
	
		Element projectPrLanguage = DocumentHelper.createElement("programmingLanguage"); 
		projectPrLanguage.addText(project.getProgrammingLanguage()); 
		XMLProject.add(projectPrLanguage); 
	
		Element projectVersion = DocumentHelper.createElement("version"); 
		projectVersion.addText(project.getVersion()); 
		XMLProject.add(projectVersion); 
	
		Element projectDescription = DocumentHelper.createElement("description"); 
		projectDescription.addText(project.getDescription()); 
		XMLProject.add(projectDescription); 
	
		return XMLProject;
    }

    public Element getSoftwareArchitectureInXML() { 
    	Element XMLArchitecture = DocumentHelper.createElement("Architecture"); 
		try{
			Element SAName = DocumentHelper.createElement("name"); 
			SAName.addText(softwareArchitecture.getName()); 
			XMLArchitecture.add(SAName); 
		
			Element SADescription = DocumentHelper.createElement("description"); 
			SADescription.addText(softwareArchitecture.getDescription()); 
			XMLArchitecture.add(SADescription); 
		
			
			ArrayList<ModuleStrategy> modules = softwareArchitecture.getModules();
			if (modules.size() > 0) {
			    Element SAModules = DocumentHelper.createElement("modules"); 
			    for (ModuleStrategy m : modules) {
			    	SAModules.add(getModuleInXML(m)); 
			    }
			    XMLArchitecture.add(SAModules); 
			}
		
			ArrayList<AppliedRuleStrategy> appliedRules = ruleService.getAllAppliedRules();
			if (appliedRules.size() > 0) {
			    Element SARules = DocumentHelper.createElement("rules"); 
			    for (AppliedRuleStrategy appliedRule : appliedRules) {
			    	SARules.add(getAppliedRuleInXML(appliedRule)); 
			    }
			    XMLArchitecture.add(SARules); 
			}
		
		}catch(Exception er){
			er.getStackTrace();
		}
		return XMLArchitecture;
    }

    public Element getSoftwareUnitDefinitionInXML(SoftwareUnitDefinition SUD) { 
		Element XMLSoftwareUnitDefinition = DocumentHelper.createElement("SoftwareUnitDefinition"); 
	
		Element SudName = DocumentHelper.createElement("name"); 
		SudName.addText(SUD.getName()); 
		XMLSoftwareUnitDefinition.add(SudName); 
	
		Element SudType = DocumentHelper.createElement("type"); 
		SudType.addText(SUD.getType().toString()); 
		XMLSoftwareUnitDefinition.add(SudType); 
	
		return XMLSoftwareUnitDefinition;
    }

    public void setParseLogical(Boolean doParse) {
    	parseLogical = doParse;
    }
}
