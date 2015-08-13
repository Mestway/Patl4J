package husacct.validate.task.fetch.xml;

import husacct.validate.domain.validation.Message;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.ViolationHistory;
import husacct.validate.domain.validation.logicalmodule.LogicalModules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dom4j.Element;

public class ImportViolationsHistory extends XmlImportUtils {

	private Logger logger = Logger.getLogger(ImportViolationsHistory.class);

	public List<ViolationHistory> importViolationsHistory(Element violationHistoriesElement) { 
		List<ViolationHistory> violationHistories = new ArrayList<ViolationHistory>();
		for (Element violationHistoryElement : violationHistoriesElement.elements("violationHistory")) { 
			List<Severity> severities = new ArrayList<Severity>();
			List<Violation> violations = new ArrayList<Violation>();
			
			// severities
			for (Element severityElement : violationHistoryElement.element("severities").elements()) { 
				String stringUUID = severityElement.elementText("id"); 
				if (isValidUUID(stringUUID)) {
					Severity severity = new Severity(UUID.fromString(severityElement.elementText("id")), severityElement.elementText("severityKey"), new Color(Integer.parseInt(severityElement.elementText("color")))); 
					severities.add(severity); 
				} else {
					logger.error(String.format("%s is not a valid UUID severity will be ignored", stringUUID));
				}
			}

			// date
			final String validationDateString = violationHistoryElement.attributeValue("date"); 
			Calendar validationDate = getCalendar(validationDateString);

			// description
			final String description = violationHistoryElement.elementText("description"); 

			// violations
			for (Element violationElement : violationHistoryElement.element("violations").elements()) { 
				final int lineNumber = Integer.parseInt(violationElement.elementText("lineNumber")); 
				final String ruleTypeKey = violationElement.elementText("ruletypeKey"); 
				final String violationTypeKey = violationElement.elementText("violationtypeKey"); 
				final String classPathFrom = violationElement.elementText("classPathFrom"); 
				final String classPathTo = violationElement.elementText("classPathTo"); 
				final LogicalModules logicalModules = getLogicalModules(violationElement.element("logicalModules")); 
				final Message message = getMessage(violationElement.element("message")); 
				final boolean isIndirect = Boolean.parseBoolean(violationElement.elementText("isIndirect")); 
				final String stringCalendar = violationElement.elementText("occured"); 
				final Calendar date = getCalendar(stringCalendar);

				// search the appropiate severity of the violation by the uuid.
				final String stringUUID = violationElement.elementText("severityId"); 
				boolean found = false;
				for (Severity severity : severities) {
					if (isValidUUID(stringUUID)) {
						UUID id = UUID.fromString(stringUUID);
						if (id.equals(severity.getId())) {
							Violation violation = new Violation()
								.setOccured(date)
								.setLineNumber(lineNumber)
								.setSeverity(severity.clone())
								.setRuletypeKey(ruleTypeKey)
								.setViolationTypeKey(violationTypeKey)
								.setClassPathFrom(classPathFrom)
								.setClassPathTo(classPathTo)
								.setInDirect(isIndirect)
								.setMessage(message)
								.setLogicalModules(logicalModules);
							violations.add(violation);
							found = true;
							break;
						}
					} else {
						logger.error(String.format("%s is not a valid severity UUID, violation will not be added", stringUUID));
						break;
					}

				}
				if (!found) {
					logger.error("Severity for the violation was not found (UUID: " + stringUUID);
				}

			}
			violationHistories.add(new ViolationHistory(violations, severities, validationDate, description));
		}
		return violationHistories;
	}

	private boolean isValidUUID(String stringUUID) {
		try {
			UUID.fromString(stringUUID);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
