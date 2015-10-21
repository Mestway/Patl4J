package goofs.fs;

import java.nio.ByteBuffer;

public abstract class File extends Node {

	protected byte[] content = new byte[256];

	protected Dir parent;

	public File(Dir parent, String name, int mode, String content,
			String... xattrs) throws Exception {
		super(name, mode, xattrs);

		this.content = content.getBytes();

		this.parent = parent;
	}

	public static boolean isTempFile(String name) {

		boolean isTmp = false;

		isTmp = (name.charAt(0) == '.')
				|| (name.charAt(name.length() - 1) == '~');

		if (!isTmp) {
			isTmp = name.matches("\\d{4}");
		}

		return isTmp;

	}

	public void flush() {
		// no need here
	}

	public byte[] getContent() {
		return content;
	}

	public int getSize() {

		return getContent().length;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Dir getParent() {
		return parent;
	}

	public void setParent(Dir parent) {
		this.parent = parent;
	}

	@Override
	public void remove() {

		synchronized (getParent().getLock()) {
			getParent().getFiles().remove(getName());
		}

		synchronized (getRootLock()) {
			nfiles--;
		}

		setParent(null);

	}

	public void truncate(long size) {

		int sizeInt = (int) size;

		if (sizeInt == 0) {
			setContent(new byte[] {});
		}

		else {
			byte[] content = getContent();

			byte[] ncontent = new byte[(int) size];

			System.arraycopy(content, 0, ncontent, 0, (int) size);

			setContent(ncontent);
		}
	}

	@Override
	public void setName(String name) {

		synchronized (getParent().getLock()) {

			if (getParent().getFiles().containsKey(getName())) {

				getParent().getFiles().remove(getName());
				getParent().getFiles().put(name, this);
			}
		}

		super.setName(name);

	}

	public int read(ByteBuffer buf, long offset) {

		buf.put(getContent(), (int) offset, Math.min(buf.remaining(), getSize()
				- (int) offset));

		return 0;
	}

	public int write(boolean isWritepage, ByteBuffer buf, long offset) {

		int length = ((int) offset) + buf.remaining();

		if (content == null) {

			content = new byte[length];

		}

		else if (getSize() < length) {

			byte[] ncontent = new byte[length];

			System.arraycopy(content, 0, ncontent, 0, content.length);

			content = ncontent;
		}

		buf.get(content, (int) offset, buf.remaining());

		return 0;
	}

	public abstract int save();

}
