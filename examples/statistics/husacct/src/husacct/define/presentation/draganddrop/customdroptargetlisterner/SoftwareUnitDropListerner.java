package husacct.define.presentation.draganddrop.customdroptargetlisterner;

import husacct.define.presentation.moduletree.AnalyzedModuleTree;
import husacct.define.presentation.utils.DragAndDropHelper;
import husacct.define.task.DefinitionController;
import husacct.define.task.components.AbstractDefineComponent;
import husacct.define.task.components.AnalyzedModuleComponent;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class SoftwareUnitDropListerner implements DropTargetListener {
	private AnalyzedModuleTree tree;
	private DropTarget target;

	public SoftwareUnitDropListerner(AnalyzedModuleTree tr) {
		this.tree = tr;
		this.target = new DropTarget(tree, this);
	}

	private AbstractDefineComponent getNodeForEvent(DropTargetDragEvent dtde) {
		Point p = dtde.getLocation();
		DropTargetContext dtc = dtde.getDropTargetContext();
		JTree tree = (JTree) dtc.getComponent();
		TreePath path = tree.getClosestPathForLocation(p.x, p.y);
		return (AbstractDefineComponent) path.getLastPathComponent();
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg) {

		try {
			Point p = arg.getLocation();
			TreePath path = tree.getPathForLocation(p.x, p.y);
			AnalyzedModuleComponent check = (AnalyzedModuleComponent) path
					.getLastPathComponent();
			String type = check.getType().toLowerCase();
			if (type.equals("root") || type.equals("application")
					|| type.equals("externalpackage")) {
				arg.rejectDrag();
			}
		} catch (Exception e) {
			arg.rejectDrag();
		}

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent arg) {
		try {
          
		} catch (Exception e) {
			arg.rejectDrag();
		}

		arg.acceptDrag(1);

	}

	@Override
	public void drop(DropTargetDropEvent arg) {
		try {
			String result = ((String) arg.getTransferable().getTransferData(
					DataFlavor.stringFlavor));
			Object[] namesAndTypes = DragAndDropHelper.interpretObjects(result);
			ArrayList<String> names = (ArrayList<String>) namesAndTypes[0];
			ArrayList<String> types = (ArrayList<String>) namesAndTypes[1];
			
           DefinitionController.getInstance().removeSoftwareUnits(names, types);
		} catch (Exception e) {

		}

	}

	

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {

	}

}
