package husacct.validate.task.export.xml;

import husacct.validate.domain.validation.Message;
import husacct.validate.domain.validation.logicalmodule.LogicalModules;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public abstract class XmlExportUtils {

	protected Element createElementWithContent(String name, String content) { 
		Element element = DocumentHelper.createElement(name); 
		element.addText(content); 
		return element;
	}

	protected Element createLogicalModulesElement(LogicalModules logicalModules) { 
		Element logicalModulesElement = DocumentHelper.createElement("logicalModules"); 
		Element logicalModuleFrom = DocumentHelper.createElement("logicalModuleFrom"); 
		Element logicalModuleTo = DocumentHelper.createElement("logicalModuleTo"); 

		logicalModulesElement.add(logicalModuleFrom); 
		logicalModulesElement.add(logicalModuleTo); 

		logicalModuleFrom.add(createElementWithContent("logicalModulePath", logicalModules.getLogicalModuleFrom().getLogicalModulePath())); 
		logicalModuleFrom.add(createElementWithContent("logicalModuleType", logicalModules.getLogicalModuleFrom().getLogicalModuleType())); 
		logicalModuleTo.add(createElementWithContent("logicalModulePath", logicalModules.getLogicalModuleTo().getLogicalModulePath())); 
		logicalModuleTo.add(createElementWithContent("logicalModuleType", logicalModules.getLogicalModuleTo().getLogicalModuleType())); 

		return logicalModulesElement;
	}

	protected Element createMessageElementFromMessageObject(Message message) { 
		Element messageElement = DocumentHelper.createElement("message"); 
		messageElement.add(createLogicalModulesElement(message.getLogicalModules())); 
		messageElement.add(createElementWithContent("ruleKey", message.getRuleKey())); 
		messageElement.add(createElementWithContent("regex", message.getRegex())); 
		Element violationTypeKeysElement = DocumentHelper.createElement("violationTypeKeys"); 
		messageElement.add(violationTypeKeysElement); 
		for (String violationTypeKey : message.getViolationTypeKeys()) {
			violationTypeKeysElement.add(createElementWithContent("violationTypeKey", violationTypeKey)); 
		}
		Element exceptionMessages = DocumentHelper.createElement("exceptionMessages"); 
		messageElement.add(exceptionMessages); 
		if (message.getExceptionMessage() != null) {
			for (Message exceptionMessage : message.getExceptionMessage()) {
				exceptionMessages.add(createMessageElementFromMessageObject(exceptionMessage)); 
			}
		}
		return messageElement;
	}
}
