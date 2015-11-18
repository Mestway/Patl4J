package husacct.validate.task.export;

import husacct.validate.domain.configuration.ActiveRuleType;
import husacct.validate.domain.configuration.ConfigurationServiceImpl;
import husacct.validate.domain.validation.Severity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class ExportController {

	private final ExportFactory exportFactory;

	public ExportController() {
		this.exportFactory = new ExportFactory();
	}

	public Element exportAllData(ConfigurationServiceImpl configuration) { 
		Element rootValidateElement = DocumentHelper.createElement("validate"); 
		rootValidateElement.add(exportSeveritiesXML(configuration.getAllSeverities())); 
		rootValidateElement.add(exportSeveritiesPerTypesPerProgrammingLanguagesXML(configuration.getAllSeveritiesPerTypesPerProgrammingLanguages())); 
		rootValidateElement.add(exportActiveViolationTypesPerRuleTypes(configuration.getActiveViolationTypes())); 

		return rootValidateElement;
	}

	private Element exportSeveritiesXML(List<Severity> severities) { 
		return exportFactory.exportSeverities(severities);
	}

	private Element exportSeveritiesPerTypesPerProgrammingLanguagesXML(HashMap<String, HashMap<String, Severity>> allSeveritiesPerTypesPerProgrammingLanguages) { 
		return exportFactory.exportSeveritiesPerTypesPerProgrammingLanguages(allSeveritiesPerTypesPerProgrammingLanguages);
	}

	private Element exportActiveViolationTypesPerRuleTypes(Map<String, List<ActiveRuleType>> activeViolationTypes) { 
		return exportFactory.exportActiveViolationTypes(activeViolationTypes);
	}
}
