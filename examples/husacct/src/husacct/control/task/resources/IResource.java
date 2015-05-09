package husacct.control.task.resources;

import java.util.HashMap;

import org.dom4j.Document;

public interface IResource {

	public Document load(HashMap<String, Object> dataValues);
	public boolean save(Document doc, HashMap<String, Object> dataValues,HashMap<String, Object> config);

	public boolean save(Document doc, HashMap<String, Object> dataValues);
	
}
