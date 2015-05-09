package goofs.fs;

import goofs.GoofsProperties;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Dir extends Node {

	protected Map<String, Node> files = new LinkedHashMap<String, Node>();

	protected Dir parent;

	protected long lastSynch = System.currentTimeMillis();

	public static final long SYNCH_THRESHOLD = Long
			.parseLong(GoofsProperties.INSTANCE
					.getProperty("goofs.folder.synch.threshold"));

	public Dir(Dir parent, String name, int mode, String... xattrs) {
		super(name, mode, xattrs);

		this.parent = parent;
	}

	public abstract int createChild(String name, boolean isDir);

	public abstract int createTempChild(String name);

	public abstract int createChildFromExisting(String name, Node child);

	public void add(Node n) {

		synchronized (getLock()) {
			files.put(n.name, n);
		}
	}

	public String toString() {
		return super.toString() + " with " + files.size() + " files";
	}

	public Dir getParent() {
		return parent;
	}

	public void setParent(Dir parent) {
		this.parent = parent;
	}

	public Map<String, Node> getFiles() {
		return files;
	}

	public void setFiles(Map<String, Node> files) {
		this.files = files;
	}

	public long getLastSynch() {
		return lastSynch;
	}

	public void setLastSynch(long lastSynch) {
		this.lastSynch = lastSynch;
	}

	public boolean needsSynch() {
		return (System.currentTimeMillis() - getLastSynch() >= SYNCH_THRESHOLD);
	}

	@Override
	public void remove() {

		synchronized (getLock()) {

			Collection<Node> childs = getFiles().values();

			for (Node child : childs) {

				child.remove();
			}

			getParent().getFiles().remove(getName());

			synchronized (getRootLock()) {
				nfiles--;

			}

			setParent(null);
		}

	}

}
