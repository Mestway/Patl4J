package goofs.fs;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class DiskFile extends File {

	protected java.io.File disk;

	public DiskFile(Dir parent, String name, int mode) throws Exception {

		super(parent, name, mode, "");

		disk = java.io.File.createTempFile("goofs", null);

		disk.deleteOnExit();

	}

	public java.io.File getDisk() {
		return disk;
	}

	public int getSize() {

		return (int) getDisk().length();

	}

	public byte[] getContent() {

		byte[] result = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		try {
			FileInputStream fis = new FileInputStream(getDisk());
			while (fis.read(buf) != -1) {
				baos.write(buf);
			}
			result = baos.toByteArray();

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	@Override
	public void truncate(long size) {

		RandomAccessFile raf = null;
		try {

			raf = new RandomAccessFile(getDisk(), "rw");
			raf.setLength(size);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setContent(byte[] content) {

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(getDisk()));
			bos.write(content);
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setContent(InputStream is) throws Exception {

		FileOutputStream fos = new FileOutputStream(getDisk());

		try {

			byte[] buff = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = is.read(buff)) != -1) {
				fos.write(buff, 0, bytesRead);

			}
		} finally {

			fos.close();
			is.close();
		}

	}

	public int read(ByteBuffer buf, long offset) {
		RandomAccessFile raf = null;
		try {

			raf = new RandomAccessFile(getDisk(), "r");
			raf.seek(offset);
			byte[] chunk = new byte[Math.min(buf.remaining(), getSize()
					- (int) offset)];
			raf.read(chunk);
			buf.put(chunk);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	public int write(boolean isWritepage, ByteBuffer buf, long offset) {

		RandomAccessFile raf = null;

		try {
			byte[] chunk = new byte[buf.remaining()];
			raf = new RandomAccessFile(getDisk(), "rw");
			raf.seek(offset);
			buf.get(chunk);
			raf.write(chunk);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	@Override
	public void remove() {

		super.remove();

		getDisk().delete();

	}

}
