package husacct.control.task.help;

import husacct.common.Resource;
import husacct.control.task.MainController;

import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
//import javax.swing.tree.DefaultMutableTreeNode;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentHelper;

public class HelpTreeModelLoader {

	private Logger logger = Logger.getLogger(MainController.class);
	List<HelpTreeNode> HelpTreeNodeList = new ArrayList<HelpTreeNode>();

	public Document getXmlDocument() { 
		String manualXmlPath = Resource.HELP_PATH + "manuals.xml";
		logger.debug(manualXmlPath);

		Reader reader = new InputStreamReader(Resource.getStream(manualXmlPath)); 
		SAXReader sax = new SAXReader(); 
		Document doc = DocumentHelper.createDocument(); 
		try {			
			doc = sax.read(reader); 
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
		}
       
		return doc;
	}



	public TreeItem getTreeModel() {


		Element root = getXmlDocument().getRootElement(); 

		//DefaultMutableTreeNode parent = new DefaultMutableTreeNode(root.getName());
        Display display = new Display();
        Shell shell = new Shell(display);
        Tree parent = new Tree(shell, SWT.SINGLE | SWT.BORDER);
        TreeItem rootNode = new TreeItem(tree, SWT.NULL);
        rootNode.setText(root.getName()); 
        tree.setTopItem(rootNode);

		for(Element child : root.elements()) { 
			HelpTreeNode HelpNode = new HelpTreeNode(child.attributeValue("filename"), child.attributeValue("viewname"), child.getName(), "html"); 
			//DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(HelpNode);
            TreeItem childNode;
			if(HelpNode.getParent() == null) {
				//parent.add(childNode);
                childNode = new TreeItem(parent, SWT.NULL);
			}
			else {
				HelpTreeNode parentHelpNode = new HelpTreeNode(HelpNode.getParent(),HelpNode.getParent(),HelpNode.getParent(), "folder");
				//DefaultMutableTreeNode parentNode = findNode(parent, parentHelpNode);
				TreeItem parentNode = findNode(parent, parentHelpNode);
                childNode = new TreeItem(parentNode);
				//parentNode.add(childNode);
				//parent.add(parentNode);
			}
			HelpTreeNodeList.add(HelpNode);
		}
		return parent;
	}
	
	private TreeItem findNode(TreeItem root, HelpTreeNode node) {
		List<TreeItem> Children = Collections.list(root.children()); 
		for(int i = 0; i < Children.size(); i++) {
			if(((HelpTreeNode)Children.get(i).getUserObject()).getFilename().equals(node.getFilename())) {
				return Children.get(i);
			}
		}
		return new TreeNode(parent, node);
		
	}

	public String getContent(InputStream stream) {		
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String line;
		String html = "";
		try {
			while((line = br.readLine()) != null) {
				html+=line;
			}	
		}
		catch (Exception ex) {
			
		}
		html = setPath(html);
		return html;
	}
	
	public String setPath(String html) {
		while(html.contains("RESOURCE.GET")) {
			int startIndex = html.indexOf("RESOURCE.GET");
			int startFileNameIndex = startIndex += "RESOURCE.GET".length() + 1;
			int endFileNameIndex = startFileNameIndex;
			if(startIndex > -1) {				
				while(html.charAt(endFileNameIndex) != ')') {
					endFileNameIndex++;
				}
				String filename = html.substring(startFileNameIndex, endFileNameIndex);
				URL pathname = Resource.get(Resource.HELP_IMAGE_PATH + filename);
				html = html.replace("RESOURCE.GET(" + filename + ")", pathname+"");
				
			}
			else {
				break;
			}
		}
		return html;
	}



	public String getContent(Component component) {
		for(HelpTreeNode htn : HelpTreeNodeList) {
			if(htn.getComponentName().equals(component.getClass().getName().substring(component.getClass().getName().lastIndexOf('.')+1))) {
				
				String HelpPagePath = Resource.HELP_PAGES_PATH + htn.getFilename();
				InputStream stream = Resource.getStream(HelpPagePath);

				String html = getContent(stream);
				
				if(html.equals("") || html==null){
					HelpPagePath = Resource.HELP_PAGES_PATH + "home.html";
					stream = Resource.getStream(HelpPagePath);
					return getContent(stream);
				}
				else {
					return html;
					
				}



			}
		}
		String HelpPagePath = Resource.HELP_PAGES_PATH + "home.html";
		InputStream stream = Resource.getStream(HelpPagePath);
		return getContent(stream);
	}
}
