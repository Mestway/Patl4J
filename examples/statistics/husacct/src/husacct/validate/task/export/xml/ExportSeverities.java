package husacct.validate.task.export.xml;

import husacct.validate.domain.validation.Severity;

import java.util.List;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class ExportSeverities {

	public Element exportSeverities(List<Severity> severities) { 
		Element severitiesElement = DocumentHelper.createElement("severities"); 
		for (Severity severity : severities) {
			Element severityElement = DocumentHelper.createElement("severity"); 
			createElementWithContent("severityKey", severity.getSeverityKey(), severityElement);
			createElementWithContent("id", "" + severity.getId().toString(), severityElement);
			createElementWithContent("color", "" + severity.getColor().getRGB(), severityElement);
			severitiesElement.add(severityElement); 
		}
		return severitiesElement;
	}

	private void createElementWithContent(String name, String content, Element destination) { 
		Element element = DocumentHelper.createElement(name); 
		element.setText(content); 
		destination.add(element); 
	}
}
