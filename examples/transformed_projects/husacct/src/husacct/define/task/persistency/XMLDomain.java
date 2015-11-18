package husacct.define.task.persistency;

import husacct.define.domain.Application;
import husacct.define.domain.Project;
import husacct.define.domain.SoftwareArchitecture;
import husacct.define.domain.appliedrule.AppliedRuleStrategy;
import husacct.define.domain.module.ModuleFactory;
import husacct.define.domain.module.ModuleStrategy;
import husacct.define.domain.module.modules.Layer;
import husacct.define.domain.services.AppliedRuleDomainService;
import husacct.define.domain.services.ModuleDomainService;
import husacct.define.domain.softwareunit.SoftwareUnitDefinition;
import husacct.define.domain.softwareunit.SoftwareUnitDefinition.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import org.dom4j.Element;

public class XMLDomain {

	private Element workspace; 
	private Application application;
	private SoftwareArchitecture softwareArchitecture;
	private AppliedRuleDomainService ruleService = new AppliedRuleDomainService();
	private ModuleDomainService moduleService = new ModuleDomainService();
	private final Logger logger = Logger.getLogger(XMLDomain.class);
    private long highestModuleId;
    private long highestAppliedRuleId;


	public XMLDomain(Element workspaceData) { 
		workspace = workspaceData;
		softwareArchitecture = SoftwareArchitecture.getInstance();
		highestModuleId = 0;
		highestAppliedRuleId = 0;
	}

	public Application createApplication() {
		try {
			logger.info(new Date().toString() + " Loading application from storage");
			List<Element> applicationProperties = workspace.elements();  

			Element name = applicationProperties.get(0); 
			Element version = applicationProperties.get(1); 
			Element projects = applicationProperties.get(2); 
			Element architecture = applicationProperties.get(3); 
			ArrayList<Project> projectsList = getProjectsFromElement(projects);

			application = new Application(name.getStringValue(), projectsList, version.getStringValue());
			application.setArchitecture(createArchitectureFromElement(architecture));
		} catch (Exception exe) {
			logger.warn("Loaded Application incomplete! Software Architecture not specified?");
		}
		return application;
	}

	private ArrayList<Project> getProjectsFromElement(Element XMLElement) { 
		ArrayList<Project> projects = new ArrayList<Project>();
		for (Element project : XMLElement.elements("Project")) { 
			projects.add(getProjectFromElement(project));
		}
		return projects;
	}

	private Project getProjectFromElement(Element XMLElement) { 
		Project project = new Project();
		project.setName(XMLElement.element("name").getText());  
		project.setProgrammingLanguage(XMLElement.element("programmingLanguage").getText()); 
		project.setVersion(XMLElement.element("version").getText()); 
		project.setDescription(XMLElement.element("description").getText()); 

		ArrayList<String> projectPaths = new ArrayList<String>();
		List<Element> pathElements = XMLElement.element("paths").elements("path"); 
		for (Element path : pathElements) { 
			projectPaths.add(path.getText());
		}
		project.setPaths(projectPaths);

		return project;
	}

	private SoftwareArchitecture createArchitectureFromElement(Element XMLElement) { 
		softwareArchitecture.setName(XMLElement.element("name").getStringValue()); 
		softwareArchitecture.setDescription(XMLElement.element("description").getStringValue()); 
		// Check if there are modules in the XML
		if (XMLElement.element("modules").elements().size() > 0) { 
			createModulesFromXML((long) 0, XMLElement.element("modules")); 
		}
		// Check if there are rules in the XML
		if (XMLElement.element("rules").elements().size() > 0) { 
			createAppliedRulesFromXML(XMLElement.element("rules")); 
		}
		// Set highestId for ModuleStrategy and AppliedRuleStrategy
		ModuleStrategy.setStaticId(highestModuleId);
		AppliedRuleStrategy.setStaticId(highestAppliedRuleId);

		return softwareArchitecture;
	}

	private void createModulesFromXML(long parentId, Element XMLElement) { 
		try{
			for (Element module : XMLElement.elements()) { 
				ModuleStrategy newModule;
				ModuleFactory factory = new ModuleFactory();
				String moduleType = module.elementText("type"); 
				String moduleDescription = module.elementText("description"); 
				String moduleName = module.elementText("name"); 
				Element SoftwareUnitDefinitions = module.element("SoftwareUnitDefinitions"); 
				int moduleId = Integer.parseInt(module.elementText("id")); 
				// Determine highestModuleId to make sure that new modules (after loading from XML) don't get an existing moduleId, erroneously.
				if(moduleId > highestModuleId){
					highestModuleId = moduleId;
				}
	
				switch (moduleType) {
				case "ExternalLibrary":
					newModule = moduleService.createNewModule("ExternalLibrary");
					break;
				case "Component":
					newModule = moduleService.createNewModule("Component");
					break;
				case "Facade":
					newModule = moduleService.createNewModule("Facade");
					break;
				case "SubSystem":
					newModule = moduleService.createNewModule("SubSystem");
					break;
				case "Layer":
					newModule = moduleService.createNewModule("Layer");
					int HierarchicalLevel = Integer.parseInt(module
							.elementText("HierarchicalLevel"));
					((Layer) newModule).setHierarchicalLevel(HierarchicalLevel);
					break;
				default:
					newModule = factory.createDummy("Blank");
					break;
				}
	
				boolean fromStorage = true;
				newModule.set(moduleName, moduleDescription, fromStorage);
				newModule.setId(moduleId);
	
				// Add neModule to parent
				if (parentId == 0) {
					try {
						moduleService.addModuleToRoot(newModule);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					moduleService.addModuleToParent(parentId, newModule);
				}
	
				if (SoftwareUnitDefinitions != null) {
					List<Element> SoftwareUnitDefinitionsList = SoftwareUnitDefinitions.elements("SoftwareUnitDefinition");  
					Iterator SUDIterator = SoftwareUnitDefinitionsList.iterator();
					while (SUDIterator.hasNext()) {
						Object o = SUDIterator.next();
						if (o instanceof Element) { 
							newModule.addSUDefinition(getSoftwareUnitDefinitionFromXML((Element) o)); 
						}
					}
				}
	
				if (hasSubmodules(module)) {
					createModulesFromXML(newModule.getId(),	module.element("SubModules")); 
				}
			}
			SoftwareArchitecture.getInstance().registerImportedValues();
		} catch (Exception exe) {
			logger.error("createModulesFromXML()" + exe.getMessage());
		}
	}

	public SoftwareUnitDefinition getSoftwareUnitDefinitionFromXML(Element e) {
		Element SUDName = e.element("name"); 
		Element SUDType = e.element("type"); 
		Type SoftwareUnitDefinitionType;

		if (SUDType.getStringValue().toUpperCase().equals("CLASS")) {
			SoftwareUnitDefinitionType = Type.CLASS;
		} else if (SUDType.getStringValue().toUpperCase().equals("INTERFACE")) {
			SoftwareUnitDefinitionType = Type.INTERFACE;
		} else if (SUDType.getStringValue().toUpperCase().equals("EXTERNALLIBRARY")) {
			SoftwareUnitDefinitionType = Type.EXTERNALLIBRARY;
		} else if (SUDType.getStringValue().toUpperCase().equals("LIBRARY")) {
			SoftwareUnitDefinitionType = Type.LIBRARY;
		} else if (SUDType.getStringValue().toUpperCase().equals("PACKAGE")) {
			SoftwareUnitDefinitionType = Type.PACKAGE;
		} else if (SUDType.getStringValue().toUpperCase().equals("SUBSYSTEM")) {
			SoftwareUnitDefinitionType = Type.SUBSYSTEM;
		} else {
			SoftwareUnitDefinitionType = Type.REGEX;
		}

		return new SoftwareUnitDefinition(SUDName.getText(),
				SoftwareUnitDefinitionType);
	}

	private boolean hasSubmodules(Element XMLElement) { 
		if (XMLElement.elements("SubModules").size() > 0) { 
			return true;
		}
		return false;
	}

	private void createAppliedRulesFromXML(Element XMLElement) { 
		try{
			// 1) Reload all applied rules
			for (Element appliedRule : XMLElement.elements()) { 
				AppliedRuleStrategy rule = createRuleFromXML(appliedRule);
				// Determine highestAppliedRuleId to make sure that new rules (after loading from XML) don't get an existing appliedRuleId.
				if(rule.getId() > highestAppliedRuleId){
					highestAppliedRuleId = rule.getId();
				}
			}
			// 2) Get exception rules and establish links from main rules to exceptions 
			ArrayList<AppliedRuleStrategy> exceptionsList = ruleService.getAllExceptionRules();
			for (AppliedRuleStrategy exceptionRule : exceptionsList){
				AppliedRuleStrategy parentRule = exceptionRule.getParentAppliedRule();
				parentRule.addException(exceptionRule);
			}
		} catch (Exception e) {
        	this.logger.warn(new Date().toString() + "Applied rule not reloaded" + e.getMessage());
        }
	}

	private AppliedRuleStrategy createRuleFromXML(Element appliedRule) { 
		AppliedRuleStrategy rule = null;
		try{
		long ruleId = Integer.parseInt(appliedRule.elementText("id")); 
		String ruleTypeKey = appliedRule.elementText("type"); 
		// In version 3.1, InheritanceConvention was introduced instead of SuperClassInheritanceConvention
		if ((ruleTypeKey.equals("SuperClassInheritanceConvention")) || (ruleTypeKey.equals("InterfaceInheritanceConvention"))){
			ruleTypeKey = "InheritanceConvention";
		}
		// In version 3.0, only id's were included, instead of all the data of the referred modules 
		// To convert old to 3.0, enable next to lines and disable the two lines thereafter. Save workspace and discard changes below again.
		//int moduleFromId = Integer.parseInt(appliedRule.element("moduleFrom").element("ModuleStrategy").elementText("id"));
		//int moduleToId = Integer.parseInt(appliedRule.element("moduleTo").element("ModuleStrategy").elementText("id"));
		long moduleFromId = Integer.parseInt(appliedRule.elementText("moduleFrom")); 
		long moduleToId = Integer.parseInt(appliedRule.elementText("moduleTo")); 
		boolean isEnabled = Boolean.parseBoolean(appliedRule.elementText("enabled")); 
		String description = appliedRule.elementText("description"); 
		String regex = appliedRule.elementText("regex"); 
		Element dependencies = appliedRule.element("dependencies"); 
		String[] dependencyTypes = getDependencyTypesFromXML(dependencies);
		boolean isException = Boolean.parseBoolean(appliedRule.elementText("isException")); 
		long parentRule = -1;
		if (isException)
			parentRule = Integer.parseInt(appliedRule.elementText("parentAppliedRuleId")); 
		 rule = ruleService.reloadAppliedRule(ruleId, ruleTypeKey, description, dependencyTypes, regex, moduleFromId, moduleToId, isEnabled, isException, parentRule);
		} catch (Exception e) {
        	this.logger.warn(new Date().toString() + "Applied rule not reloaded" + e.getMessage());
		}
		return rule;
	}

	private String[] getDependencyTypesFromXML(Element XMLElement) { 
		ArrayList<String> dependencies = new ArrayList<String>();
		for (Element dependency : XMLElement.elements("dependency")) { 
			dependencies.add(dependency.getStringValue());
		}
		String[] returnValue = null;
		returnValue = dependencies.toArray(new String[dependencies.size()]);
		return returnValue;
	}

}

