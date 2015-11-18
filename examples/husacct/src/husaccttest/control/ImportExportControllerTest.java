package husaccttest.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import husacct.ServiceProvider;
import husacct.control.task.ExportController;
import husacct.control.task.ImportController;
import husacct.control.task.MainController;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImportExportControllerTest {
	
	ImportController importController;
	ExportController exportController;
	
	File testFile = new File("importExportTestFile.xml");
	
	@Before
	public void setup(){
		MainController mainController = new MainController();
		importController = mainController.getImportController();
		exportController = mainController.getExportController();
	}
	
	@After
	public void cleanUp(){
		testFile.delete();
	}
	
	@Test
	public void testExport(){
		exportController.exportArchitecture(testFile);
		assertTrue(testFile.exists());
	}
	
	@Test
	public void testImport(){
		exportController.exportArchitecture(testFile);
		importController.importArchitecture(testFile);
		SAXReader sax = new SAXReader();
		Document testDoc = DocumentHelper.createDocument();
		try {
			testDoc = sax.read(testFile);
		} catch (Exception e) {
		}
		Element element = ServiceProvider.getInstance().getDefineService().getLogicalArchitectureData();
		String originalRootElement = testDoc.getRootElement().toString();
		String importedRootElement = element.toString();
		assertEquals(originalRootElement, importedRootElement);
	}
}
