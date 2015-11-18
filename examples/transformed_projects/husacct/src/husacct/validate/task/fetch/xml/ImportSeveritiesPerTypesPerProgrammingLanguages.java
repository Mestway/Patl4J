package husacct.validate.task.fetch.xml;

import husacct.validate.domain.validation.Severity;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

public class ImportSeveritiesPerTypesPerProgrammingLanguages {

	public HashMap<String, HashMap<String, Severity>> importSeveritiesPerTypesPerProgrammingLanguages(Element element, List<Severity> severities) { 
		HashMap<String, HashMap<String, Severity>> severitiesPerTypesPerProgrammingLanguages = new HashMap<String, HashMap<String, Severity>>();
		for (Element severityPerTypePerProgrammingLanguageElement : element.elements()) { 
			String language = severityPerTypePerProgrammingLanguageElement.attributeValue("language"); 
			HashMap<String, Severity> severitiesPerTypes = new HashMap<String, Severity>();
			for (Element severityPerTypeElement : severityPerTypePerProgrammingLanguageElement.elements("severityPerType")) { 
				for (Severity severity : severities) {
					if (severity.getId().toString().equals(severityPerTypeElement.elementText("severityId"))) { 
						severitiesPerTypes.put(severityPerTypeElement.elementText("typeKey"), severity); 
					}
				}
			}
			severitiesPerTypesPerProgrammingLanguages.put(language, severitiesPerTypes);
		}
		return severitiesPerTypesPerProgrammingLanguages;
	}
}
