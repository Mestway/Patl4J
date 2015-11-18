package husacct.validate.task.fetch.xml;

import husacct.validate.domain.validation.Message;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.logicalmodule.LogicalModules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.dom4j.Element;

public class ImportViolations extends XmlImportUtils {
	public List<Violation> importViolations(Element violationsElement, List<Severity> severities) throws DatatypeConfigurationException { 
		List<Violation> violations = new ArrayList<Violation>();
		for (Element violationElement : violationsElement.elements()) { 
			Severity violationSeverity = null;

			final String severityId = violationElement.elementText("severityId"); 
			for (Severity severity : severities) {
				if (severityId.equals(severity.getId().toString())) {
					violationSeverity = severity;
					break;
				}
			}

			final int lineNumber = Integer.parseInt(violationElement.elementText("lineNumber")); 
			final String ruleTypeKey = violationElement.elementText("ruletypeKey"); 
			final String violationTypeKey = violationElement.elementText("violationtypeKey"); 
			final String classPathFrom = violationElement.elementText("classPathFrom"); 
			final String classPathTo = violationElement.elementText("classPathTo"); 
			final LogicalModules logicalModules = getLogicalModules(violationElement.element("logicalModules")); 
			final Message message = getMessage(violationElement.element("message")); 
			final boolean isIndirect = Boolean.parseBoolean(violationElement.elementText("isIndirect")); 

			final String stringCalendar = violationElement.elementText("occured"); 
			final Calendar occured = getCalendar(stringCalendar);

			Violation violation = new Violation()
				.setOccured(occured)
				.setLineNumber(lineNumber)
				.setSeverity(violationSeverity)
				.setRuletypeKey(ruleTypeKey)
				.setViolationTypeKey(violationTypeKey)
				.setClassPathFrom(classPathFrom)
				.setClassPathTo(classPathTo)
				.setInDirect(isIndirect)
				.setMessage(message)
				.setLogicalModules(logicalModules);

			violations.add(violation);
		}
		return violations;
	}
}
