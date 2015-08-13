package husacct.control.task.resources;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentHelper;

public class XmlResource implements IResource{

	private boolean doCompress = false;
	private boolean doPasswordProtection = false;
	private Logger logger = Logger.getLogger(XmlResource.class);

	public Document load(HashMap<String, Object> dataValues) { 

		File file = (File) dataValues.get("file");

		SAXReader sax = new SAXReader(); 
		Document doc = DocumentHelper.createDocument(); 
		try {

			doc = sax.read(file); 
		}
		catch (Exception ex) {
			Cryptographer crypto = new Cryptographer();
			String password = "";
			while(password.equals("")) {
				password = promptUserForPassword();
				
				if(password.equals("")) {
					break;
				}
				try {
					doc = sax.read(crypto.decrypt(password, file)); 

				}
				catch (Exception e) {
					password = "";
				}
			}					
		}
		return doc;
	}

	public boolean save(Document doc, HashMap<String, Object> dataValues, HashMap<String, Object> config) { 

		//this.doEncrypt = (boolean)config.get("doEncrypt");
		this.doCompress = (boolean)config.get("doCompress");
		this.doPasswordProtection = (boolean)config.get("doPasswordProtection");
	
		File file = (File) dataValues.get("file");
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			XMLWriter xout; 
			if(doCompress) {
				System.out.println("compress");
				//xout = new XMLOutputter(Format.getRawFormat());
				xout = new XMLWriter(new OutputFormat()); 
			}
			else {
				//xout = new XMLOutputter(OutputFormat.createPrettyPrint());
				xout = new XMLWriter(new OutputFormat()); 
			}
            xout.setOutputStream(outputStream); 
            xout.write(doc);
	
			outputStream.close();
	
			//if(doEncrypt) {
			//	Cryptographer crypto = new Cryptographer();
			//	crypto.encrypt(doc, file);
			//}
			if(doPasswordProtection) {
				Cryptographer crypto = new Cryptographer();
				crypto.encrypt((String)config.get("password"), file);
			}
	
	
			return true;
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			new RuntimeException(e);
		}
		return false;
	}
	
	public String promptUserForPassword() {
        /*
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(150,75));
		JLabel label = new JLabel("Enter a password to open the workspace:");
		JPasswordField pass = new JPasswordField(20);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "The file is locked.",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[1]);
                */
        Display display = new Display();
        final Shell shell = new Shell(display);
        InputDialog id = new InputDialog(shell, "This file is locked.", 
            "Enter a password to open the workspace:", null, null);
        /*
		if(option == 0) // pressing OK button
		{
			char[] password = pass.getPassword();
			return new String(password);
		}
		else {
			return "";
		}
        */
        if (id.value != null) {
            return id.value();
        } else {
            return "";
        }
	}
	
	@Override
	public boolean save(Document doc, HashMap<String, Object> dataValues) { 
		HashMap<String,Object> config = new HashMap<String, Object>();
		config.put("doCompress", false);
		config.put("doPasswordProtection", false);
		save(doc, dataValues, config);
		return false;
	}
}
