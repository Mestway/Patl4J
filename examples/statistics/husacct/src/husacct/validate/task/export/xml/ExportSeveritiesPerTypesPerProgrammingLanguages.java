package husacct.validate.task.export.xml;

import husacct.validate.domain.validation.Severity;

import java.util.HashMap;
import java.util.Map.Entry;


import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class ExportSeveritiesPerTypesPerProgrammingLanguages {

	public Element exportSeveritiesPerTypesPerProgrammingLanguages(HashMap<String, HashMap<String, Severity>> allSeveritiesPerTypesPerProgrammingLanguages) { 
		Element severitiesPerTypesPerProgrammingLanguagesElement = DocumentHelper.createElement("severitiesPerTypesPerProgrammingLanguages"); 
		for (Entry<String, HashMap<String, Severity>> programminglanguageEntry : allSeveritiesPerTypesPerProgrammingLanguages.entrySet()) {
			Element severityPerTypePerProgrammingLanguageElement = createElementWithoutContent("severityPerTypePerProgrammingLanguage", severitiesPerTypesPerProgrammingLanguagesElement); 
			severityPerTypePerProgrammingLanguageElement.addAttribute("language", programminglanguageEntry.getKey()); 
			for (Entry<String, Severity> severityPerType : programminglanguageEntry.getValue().entrySet()) {
				Element severityPerTypeElement = createElementWithoutContent("severityPerType", severityPerTypePerProgrammingLanguageElement); 
				createElementWithContent("typeKey", severityPerType.getKey(), severityPerTypeElement);
				createElementWithContent("severityId", "" + severityPerType.getValue().getId().toString(), severityPerTypeElement);
			}
		}
		return severitiesPerTypesPerProgrammingLanguagesElement;
	}

	private void createElementWithContent(String name, String content, Element destination) {
		Element element = DocumentHelper.createElement(name); 
		element.addText(content); 
		destination.add(element); 
	}
	private Element createElementWithoutContent(String name, Element destination) { 
		Element element = DocumentHelper.createElement(name); 
		destination.add(element); 
		return element;
	}
}
