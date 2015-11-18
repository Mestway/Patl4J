package husacct.validate.task.fetch.xml;

import husacct.validate.domain.validation.Message;
import husacct.validate.domain.validation.logicalmodule.LogicalModule;
import husacct.validate.domain.validation.logicalmodule.LogicalModules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
//transformed
//import org.jdom2.Element;
import org.dom4j.Element;

public abstract class XmlImportUtils {

	private Logger logger = Logger.getLogger(XmlImportUtils.class);

	protected Message getMessage(Element messageElement) {
		Element logicalModulesElement = messageElement.element("logicalModules");
		LogicalModules logicalModules = getLogicalModules(logicalModulesElement);
		String ruleKey = messageElement.elementText("ruleKey");
		String regex = messageElement.elementText("regex");
		Element violationTypeKeys = messageElement.element("violationTypeKeys");
		List<String> violationTypeKeysList = new ArrayList<String>();
		for (Element violationTypeKey : violationTypeKeys.elements()) {
			violationTypeKeysList.add(violationTypeKey.getText());
		}
		List<Message> exceptionMessages = new ArrayList<Message>();
		Element exceptionMessagesElement = messageElement.element("exceptionMessages");
		if (exceptionMessagesElement != null) {
			for (Element exceptionMessageElement : exceptionMessagesElement.elements()) {
				exceptionMessages.add(getMessage(exceptionMessageElement));
			}
		}
		Message message = new Message(logicalModules, ruleKey, violationTypeKeysList, regex, exceptionMessages);
		return message;
	}

	protected LogicalModules getLogicalModules(Element logicalModulesElement) {
		Element logicalModuleFromElement = logicalModulesElement.element("logicalModuleFrom");
		Element logicalModuleToElement = logicalModulesElement.element("logicalModuleTo");

		LogicalModule logicalModuleFrom = new LogicalModule(logicalModuleFromElement.elementText("logicalModulePath"), logicalModuleFromElement.elementText("logicalModuleType"));
		LogicalModule logicalModuleTo = new LogicalModule(logicalModuleToElement.elementText("logicalModulePath"), logicalModuleToElement.elementText("logicalModuleType"));
		LogicalModules logicalModules = new LogicalModules(logicalModuleFrom, logicalModuleTo);
		return logicalModules;
	}

	protected Calendar getCalendar(String stringCalendar) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(stringCalendar).toGregorianCalendar();
		} catch (IllegalArgumentException e) {
			logger.error(String.format("%s is not a valid datetime, switching back to current datetime", stringCalendar));
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage());
		}
		return calendar;
	}
}
