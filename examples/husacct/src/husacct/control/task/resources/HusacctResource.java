package husacct.control.task.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentHelper;

public class HusacctResource implements IResource{

	private Logger logger = Logger.getLogger(HusacctResource.class);

	// TODO: Decrypt file
	public Document load(HashMap<String, Object> dataValues) { 
		File file = (File) dataValues.get("file");
		SAXReader sax = new SAXReader(); 
		Document doc = DocumentHelper.createDocument(); 
		try {
			doc = sax.read(file); 
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return doc;
	}
	
	// TODO: Encrypt file
	public boolean save(Document doc, HashMap<String, Object> dataValues) { 
		
		File file = (File) dataValues.get("file");
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			XMLWriter xout = new XMLWriter(OutputFormat.createPrettyPrint()); 
			//xout.output(doc, outputStream);
            xout.setOutputStream(outputStream); 
			xout.write(doc);
			outputStream.close();
			return true;
		} catch (Exception e){
			logger.error(e.getMessage());
			new RuntimeException(e);
		}
		return false;
	}

	@Override
	public boolean save(Document doc, HashMap<String, Object> dataValues, 
			HashMap<String, Object> config) {
		// TODO Auto-generated method stub
		return false;
	}

}
