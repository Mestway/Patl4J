package husaccttest.validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import husacct.validate.ValidateServiceImpl;
import husacct.validate.domain.configuration.ActiveRuleType;
import husacct.validate.domain.configuration.ActiveViolationType;
import husacct.validate.domain.validation.Severity;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.AssertionFailedError;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ImportExportTest {
	private ValidateServiceImpl validate;

	@Before
	public void setup() {
		validate = new ValidateServiceImpl();
	}

	public void testImporting() throws URISyntaxException, ParserConfigurationException, SAXException, IOException, DatatypeConfigurationException {
		ClassLoader.getSystemResource("husaccttest/validate/Testfile_ImportExportTest.xml").toURI();
		DocumentBuilderFactory domfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder = domfactory.newDocumentBuilder();
		File file = new File(ClassLoader.getSystemResource("husaccttest/validate/Testfile_ImportExportTest.xml").toURI());
		DOMReader domBuilder = new DOMReader();
		Document document = domBuilder.read(dombuilder.parse(file));
		validate.loadWorkspaceData(document.getRootElement());

		checkSeveritiesTheSameAsSeveritiesElement(validate.getConfiguration().getAllSeverities(),document.getRootElement().element("severities"));
		checkSeveritiesPerTypesPerProgrammingLanguagesTheSameAsSeveritiesPerTypesPerProgrammingLanguagesElement(
				validate.getConfiguration().getAllSeveritiesPerTypesPerProgrammingLanguages(), 
				document.getRootElement().element("severitiesPerTypesPerProgrammingLanguages"));

		checkActiveViolationTypesTheSameAsActiveViolationTypesElement(validate
				.getConfiguration().getActiveViolationTypes(), document.getRootElement().element("activeViolationTypes"));
	}

	private void checkSeveritiesTheSameAsSeveritiesElement(List<Severity> severities, Element severitiesElement) {
		assertEquals(severitiesElement.elements().size(), severities.size());
		
		for(int i = 0; i < severitiesElement.elements().size(); i++) {
			Element severityElement = severitiesElement.elements().get(i);
			Severity severity = severities.get(i);
			checkSeverityTheSameAsSeverityElement(severity, severityElement);
		}
	}

	private void checkSeveritiesPerTypesPerProgrammingLanguagesTheSameAsSeveritiesPerTypesPerProgrammingLanguagesElement(
			HashMap<String, HashMap<String, Severity>> severitiesPerTypesPerProgrammingLanguages,
			Element severitiesPerTypesPerProgrammingLanguagesElement) {
		assertEquals(severitiesPerTypesPerProgrammingLanguages.size(), severitiesPerTypesPerProgrammingLanguagesElement.elements().size());
		for(Entry<String, HashMap<String, Severity>> severityPerTypePerProgrammingLanguage : severitiesPerTypesPerProgrammingLanguages.entrySet()) {
			for(Element severityPerTypePerProgrammingLanguageElement : severitiesPerTypesPerProgrammingLanguagesElement.elements()) {
				if(severityPerTypePerProgrammingLanguageElement.attribute("language").getValue().equals(severityPerTypePerProgrammingLanguage.getKey())) {
					checkSeverityPerTypePerProgrammingLanguageTheSameAsSeverityPerTypePerProgrammingLanguageElement(
							severityPerTypePerProgrammingLanguage, severityPerTypePerProgrammingLanguageElement);
				}
			}
		}
	}

	private void checkActiveViolationTypesTheSameAsActiveViolationTypesElement(Map<String, List<ActiveRuleType>> activeViolationTypes, Element child) {
		int i = 0;
		for(Entry<String, List<ActiveRuleType>> activeViolationType : activeViolationTypes.entrySet()) {
			Element activeViolationTypeElement = child.elements().get(i);
			assertEquals(activeViolationType.getKey(), activeViolationTypeElement.attributeValue("language"));

			for(int ruleTypeIndex = 0; ruleTypeIndex < activeViolationTypeElement.elements().size(); ruleTypeIndex++) {
				Element activeRuleTypeElement = activeViolationTypeElement.elements().get(ruleTypeIndex);

				assertNotNull(containsActiveRuleType(activeRuleTypeElement.attributeValue("type"), activeViolationType.getValue()));

				ActiveRuleType activeRuleType = containsActiveRuleType(activeRuleTypeElement.attributeValue("type"), activeViolationType.getValue());
				assertEquals(activeRuleType.getRuleType(), activeRuleTypeElement.attributeValue("type"));

				for(Element violationTypesElement : activeRuleTypeElement
						.elements("violationTypes")) {
					for(int violationTypesRootIndex = 0; violationTypesRootIndex < violationTypesElement
							.elements().size(); violationTypesRootIndex++) {

						assertNotNull(containsActiveViolationType(
								violationTypesElement.elements()
								.get(violationTypesRootIndex)
								.elementText("violationKey"),
								activeRuleType.getViolationTypes()));
						ActiveViolationType violationType = containsActiveViolationType(
								violationTypesElement.elements()
								.get(violationTypesRootIndex)
								.elementText("violationKey"),
								activeRuleType.getViolationTypes());
						assertEquals(
								violationType.getType(),
								violationTypesElement.elements()
								.get(violationTypesRootIndex)
								.elementText("violationKey"));
						assertEquals(
								violationType.isEnabled(),
								Boolean.parseBoolean(violationTypesElement
										.elements()
										.get(violationTypesRootIndex)
										.elementText("enabled")));
					}
				}
			}
			i++;
		}
	}

	private ActiveRuleType containsActiveRuleType(String key, List<ActiveRuleType> activeViolationTypes) {
		for(ActiveRuleType activeRuleType : activeViolationTypes) {
			if(activeRuleType.getRuleType().equals(key)) {
				return activeRuleType;
			}
		}
		return null;
	}

	private ActiveViolationType containsActiveViolationType(String key, List<ActiveViolationType> activeViolationTypes) {
		for(ActiveViolationType activeViolationType : activeViolationTypes) {
			if(activeViolationType.getType().equals(key)) {
				return activeViolationType;
			}
		}
		return null;
	}

	private void checkSeverityTheSameAsSeverityElement(Severity severity, Element severityElement) {
		assertEquals(severity.getSeverityKey(),	severityElement.elementText("severityKey"));
		assertEquals(severity.getColor(), new Color(Integer.parseInt(severityElement.elementText("color"))));
	}

	private void checkSeverityPerTypePerProgrammingLanguageTheSameAsSeverityPerTypePerProgrammingLanguageElement(
			Entry<String, HashMap<String, Severity>> severityPerTypePerProgrammingLanguage, Element severityPerTypePerProgrammingLanguageElement) {
		assertEquals(severityPerTypePerProgrammingLanguageElement.elements().size(), severityPerTypePerProgrammingLanguage.getValue().size());
		
		for(Entry<String, Severity> severityPerType : severityPerTypePerProgrammingLanguage.getValue().entrySet()) {
			String severityId = findSeverityPerTypeElement(severityPerTypePerProgrammingLanguageElement, severityPerType);
			assertEquals(severityId, severityPerType.getValue().getId().toString());
		}
	}

	private String findSeverityPerTypeElement(Element severityPerTypePerProgrammingLanguageElement,	Entry<String, Severity> severityPerType) {
		for(Element severityPerTypeElement : severityPerTypePerProgrammingLanguageElement.elements()) {
			if(severityPerTypeElement.elementText("typeKey").equals(severityPerType.getKey())) {
				return severityPerTypeElement.elementText("severityId");
			}
		}
		throw new AssertionFailedError("There was an error finding a type by the key: "	+ severityPerType.getKey());
	}

	@Test
	public void testExportingAndImporting() throws URISyntaxException, ParserConfigurationException, SAXException, IOException, DatatypeConfigurationException {
		testImporting();
		
		checkSeveritiesTheSameAsSeveritiesElement(validate.getConfiguration()
				.getAllSeverities(), validate.getWorkspaceData().element("severities"));
		checkSeveritiesPerTypesPerProgrammingLanguagesTheSameAsSeveritiesPerTypesPerProgrammingLanguagesElement(
				validate.getConfiguration().getAllSeveritiesPerTypesPerProgrammingLanguages(),
				validate.getWorkspaceData().element("severitiesPerTypesPerProgrammingLanguages"));
		
		checkActiveViolationTypesTheSameAsActiveViolationTypesElement(validate
				.getConfiguration().getActiveViolationTypes(), validate.getWorkspaceData().element("activeViolationTypes"));
	}
}
