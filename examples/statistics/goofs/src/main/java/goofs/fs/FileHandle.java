package goofs.fs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileHandle {

	protected static final Log log = LogFactory.getLog(FileHandle.class);

	protected Node node;

	protected boolean dirty = false;

	public FileHandle(Node node) {
		this.node = node;
		log.debug("  " + this + " created");
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean isTempFile() {

		return File.isTempFile(getNode().getName());
	}

	public int release() {
		
		int result = isTempFile() ? 0 : isDirty() ? ((File) getNode()).save()
				: 0;

		setDirty(false);

		log.debug("  " + this + " released");

		return result;
	}

	public int flush() {

		((File) getNode()).flush();

		return 0;
	}

	protected void finalize() {
		log.debug("  " + this + " finalized");
	}

	public String toString() {
		return "FH[" + getNode() + ", hashCode=" + hashCode() + "]";
	}

}
