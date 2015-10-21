package husacct.control.presentation.workspace.savers;

import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.jdom2.Content;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentHelper;

import husacct.ServiceProvider;
import husacct.common.savechain.ISaveable;

public class resourceGatherer implements Runnable {

	private XmlSavePanel saverPanel;
	public resourceGatherer(XmlSavePanel p) {
		this.saverPanel = p;
	}

	@Override
	public void run() {
		Element root = DocumentHelper.createElement("root"); 
		for(ISaveable service : getSaveableServices()){
			String serviceName = service.getClass().getName();

			Element container = DocumentHelper.createElement(serviceName); 
			Element serviceData = service.getWorkspaceData(); 
			container.add(serviceData); 
			root.add(container); 
		}
		saverPanel.setRequiredSpace(calculateNewNodeSize(DocumentHelper.createDocument(root))); 
	}
	private int calculateNewNodeSize(Document d) { 
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		XMLWriter xout = null; 
		if((boolean)saverPanel.getConfig().get("doCompress")) {
			//xout = new XMLWriter(Format.getRawFormat());
            try {
			    xout = new XMLWriter(new OutputFormat()); 
            } catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
            } 
		}
		else {
            try {
			    xout = new XMLWriter(OutputFormat.createPrettyPrint()); 
            } catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
            } 
		}
		try {
			//xout.output(d, os); 
            xout.setOutputStream(os); 
            xout.write(d); 
			os.close();
			return(os.size());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}


	private List<ISaveable> getSaveableServices() {
		List<ISaveable> saveableServices = new ArrayList<ISaveable>();

		if(ServiceProvider.getInstance().getControlService() instanceof ISaveable){
			saveableServices.add((ISaveable) ServiceProvider.getInstance().getControlService());
		}

		if(ServiceProvider.getInstance().getDefineService() instanceof ISaveable){
			saveableServices.add((ISaveable) ServiceProvider.getInstance().getDefineService());
		}

		if(ServiceProvider.getInstance().getAnalyseService() instanceof ISaveable){
			saveableServices.add((ISaveable) ServiceProvider.getInstance().getAnalyseService());
		}

		if(ServiceProvider.getInstance().getValidateService() instanceof ISaveable){
			saveableServices.add((ISaveable) ServiceProvider.getInstance().getValidateService());
		}

		if(ServiceProvider.getInstance().getGraphicsService() instanceof ISaveable){
			saveableServices.add((ISaveable) ServiceProvider.getInstance().getGraphicsService());
		}

		return saveableServices;
	}
}
