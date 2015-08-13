package husacct.validate.task.export.xml;

import husacct.validate.domain.configuration.ActiveRuleType;
import husacct.validate.domain.configuration.ActiveViolationType;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class ExportActiveViolationTypes extends XmlExportUtils {

	public Element exportActiveViolationTypes(Map<String, List<ActiveRuleType>> activeViolationTypes) { 
		Element activeViolationTypesElement = DocumentHelper.createElement("activeViolationTypes"); 
		for (Entry<String, List<ActiveRuleType>> activeViolationType : activeViolationTypes.entrySet()) {
			activeViolationTypesElement.add(createActiveViolationTypeElement(activeViolationType)); 
		}
		return activeViolationTypesElement;
	}

	private Element createActiveViolationTypeElement(Entry<String, List<ActiveRuleType>> activeViolationType) { 
		Element activeViolationTypeElement = DocumentHelper.createElement("activeViolationType"); 
		//activeViolationTypeElement.add(new FlyweightAttribute("language", activeViolationType.getKey()));
		activeViolationTypeElement.addAttribute("language", activeViolationType.getKey()); 
		for (ActiveRuleType activeRuleType : activeViolationType.getValue()) {
			activeViolationTypeElement.add(createActiveRuleTypeElement(activeRuleType)); 
		}
		return activeViolationTypeElement;
	}

	private Element createActiveRuleTypeElement(ActiveRuleType activeRuleType) { 
		Element ruleTypeElement = DocumentHelper.createElement("ruleType"); 
		//ruleTypeElement.setAttribute(new Attribute("type", activeRuleType.getRuleType()));
		ruleTypeElement.addAttribute("type", activeRuleType.getRuleType()); 
		Element violationTypesElement = DocumentHelper.createElement("violationTypes"); 
		ruleTypeElement.add(violationTypesElement); 
		for (ActiveViolationType violationType : activeRuleType.getViolationTypes()) {
			Element violationTypeElement = DocumentHelper.createElement("violationType"); 
			violationTypeElement.add(createElementWithContent("violationKey", violationType.getType())); 
			violationTypeElement.add(createElementWithContent("enabled", "" + violationType.isEnabled())); 
			violationTypesElement.add(violationTypeElement); 
		}
		return ruleTypeElement;
	}
}
