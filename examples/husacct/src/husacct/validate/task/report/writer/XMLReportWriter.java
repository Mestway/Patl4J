package husacct.validate.task.report.writer;

import husacct.validate.domain.factory.message.Messagebuilder;
import husacct.validate.domain.validation.Message;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.internaltransferobjects.ViolationsPerSeverity;
import husacct.validate.domain.validation.report.Report;
import husacct.validate.task.extensiontypes.ExtensionTypes.ExtensionType;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentHelper;

public class XMLReportWriter extends ReportWriter {

	public XMLReportWriter(Report report, String path, String fileName) {
		super(report, path, fileName, ExtensionType.XML);
	}

	@Override
	public void createReport() throws IOException {
		//Document document = new Document();
		Document document = DocumentHelper.createDocument(); 

		//Element reportElement = DocumentHelper.createElement("report");
		Element reportElement = DocumentHelper.createElement("report" 
		document.setRootElement(reportElement); 

		//Element projectName = DocumentHelper.createElement("projectName");
		Element projectName = DocumentHelper.createElement("projectName"); 
		projectName.setText(report.getProjectName()); 
		reportElement.add(projectName); 

		Element projectVersion = DocumentHelper.createElement("version"); 
		projectVersion.setText(report.getVersion()); 
		reportElement.add(projectVersion); 

		Element totalViolations = DocumentHelper.createElement("totalViolations"); 
		totalViolations.setText("" + report.getViolations().getValue().size()); 
		reportElement.add(totalViolations); 

		Element violationGeneratedOn = DocumentHelper.createElement("violationsGeneratedOn"); 
		violationGeneratedOn.setText(report.getFormattedDate()); 
		reportElement.add(violationGeneratedOn); 

		Element violationsSeverities = DocumentHelper.createElement("violations"); 
		//violationsSeverities.setAttribute(new Attribute("totalViolations", "" + report.getViolations().getValue().size()));
		violationsSeverities.addAttribute("totalViolations", "" + report.getViolations().getValue().size()); 
		
		for (ViolationsPerSeverity violationPerSeverity : report.getViolationsPerSeverity()) {
			Element violationElement = DocumentHelper.createElement(violationPerSeverity.getSeverity().getSeverityKey()); 
			violationElement.setText("" + violationPerSeverity.getAmount()); 
			violationsSeverities.add(violationElement);  
		}
		reportElement.add(violationsSeverities); 

		Element violations = DocumentHelper.createElement("violations"); 
		reportElement.add(violations); 

		for (Violation violation : report.getViolations().getValue()) {
			Element xmlViolation = DocumentHelper.createElement("violation"); 

			Element source = DocumentHelper.createElement("source"); 
			Element target = DocumentHelper.createElement("target"); 
			Element lineNr = DocumentHelper.createElement("lineNr"); 
			Element severity = DocumentHelper.createElement("severity"); 
			Element ruleType = DocumentHelper.createElement("ruleType"); 
			Element dependencyKind = DocumentHelper.createElement("dependencyKind"); 
			Element isDirect = DocumentHelper.createElement("isDirect"); 

			target.setText(violation.getClassPathTo()); 
			source.setText(violation.getClassPathFrom()); 
			lineNr.setText("" + violation.getLinenumber()); 
			severity.setText(violation.getSeverity().getSeverityName()); 
			if (violation.getLogicalModules() != null) {
				Message messageObject = violation.getMessage();
				String message = new Messagebuilder().createMessage(messageObject,violation);
				ruleType.setText(message); 
			}
			dependencyKind.setText(violation.getViolationTypeKey()); 
			isDirect.setText("" + violation.isIndirect()); 

			xmlViolation.add(source); 
			xmlViolation.add(target); 
			xmlViolation.add(lineNr); 
			xmlViolation.add(severity); 
			xmlViolation.add(ruleType); 
			xmlViolation.add(dependencyKind); 
			xmlViolation.add(isDirect); 

			violations.add(xmlViolation); 
		}
		XMLWriter outputter = new XMLWriter(OutputFormat.createPrettyPrint()); 
		FileWriter fileWriter = new FileWriter(getFileName());
        outputter.setWriter(fileWriter); 
        outputter.write(document);
		fileWriter.close();
	}
}
