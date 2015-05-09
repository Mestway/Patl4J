package goofs.fs;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gdata.util.ResourceNotFoundException;

import fuse.Errno;
import fuse.Filesystem3;
import fuse.FuseDirFiller;
import fuse.FuseException;
import fuse.FuseFtypeConstants;
import fuse.FuseGetattrSetter;
import fuse.FuseMount;
import fuse.FuseOpenSetter;
import fuse.FuseSizeSetter;
import fuse.FuseStatfsSetter;
import fuse.XattrLister;
import fuse.XattrSupport;
import goofs.EntryContainer;
import goofs.Fetchable;
import goofs.Identifiable;

public class GoofsFS implements Filesystem3, XattrSupport {

	protected static final Log log = LogFactory.getLog(GoofsFS.class);

	private static final int BLOCK_SIZE = 512;
	private static final int NAME_LENGTH = 1024;

	protected Dir root;

	public GoofsFS() throws Exception {
		root = new RootDir();
	}

	protected Node lookup(String path) {
		if (path.equals("/"))
			return root;

		java.io.File f = new java.io.File(path);
		Node parent = lookup(f.getParent());
		Node node = (parent instanceof Dir) ? ((Dir) parent).files.get(f
				.getName()) : null;

		return node;
	}

	protected Dir lookupParent(String path) {
		java.io.File f = new java.io.File(path);
		return (Dir) lookup(f.getParent());

	}

	public int chmod(String arg0, int arg1) throws FuseException {
		return 0;
	}

	public int chown(String arg0, int arg1, int arg2) throws FuseException {
		return 0;
	}

	public int flush(String path, Object fh) throws FuseException {

		if (fh instanceof FileHandle)
			return ((FileHandle) fh).flush();

		return Errno.EBADF;
	}

	public int fsync(String path, Object arg1, boolean arg2)
			throws FuseException {

		return 0;
	}

	public int getattr(String path, FuseGetattrSetter getattrSetter)
			throws FuseException {
		Node n = lookup(path);
		// int time = (int) (System.currentTimeMillis() / 1000L);
		if (n instanceof Dir) {
			Dir d = (Dir) n;
			getattrSetter.set(d.hashCode(), FuseFtypeConstants.TYPE_DIR
					| d.mode, 1, 0, 0, 0, d.files.size() * NAME_LENGTH,
					(d.files.size() * NAME_LENGTH + BLOCK_SIZE - 1)
							/ BLOCK_SIZE, n.getAccessTime(), n.getModifyTime(),
					n.getCreateTime());

			return 0;
		} else if (n instanceof File) {
			File f = (File) n;
			getattrSetter.set(f.hashCode(), FuseFtypeConstants.TYPE_FILE
					| f.mode, 1, 0, 0, 0, f.getSize(), (f.getSize()
					+ BLOCK_SIZE - 1)
					/ BLOCK_SIZE, n.getAccessTime(), n.getModifyTime(), n
					.getCreateTime());

			return 0;
		}
		return Errno.ENOENT;
	}

	public int getdir(String path, FuseDirFiller filler) throws FuseException {
		// log.info("in getdir");
		Node n = lookup(path);
		if (n instanceof Dir) {
			if (((Dir) n).needsSynch()) {
				synchDir((Dir) n);
			}
			for (Node child : ((Dir) n).files.values()) {
				int ftype = (child instanceof Dir) ? FuseFtypeConstants.TYPE_DIR
						: ((child instanceof File) ? FuseFtypeConstants.TYPE_FILE
								: 0);
				if (ftype > 0)
					filler
							.add(child.name, child.hashCode(), ftype
									| child.mode);
			}
			return 0;
		}
		return Errno.ENOTDIR;
	}

	protected void synchDir(Dir dir) {
		dir.setLastSynch(System.currentTimeMillis());
		for (Node next : dir.getFiles().values()) {
			if (next instanceof Fetchable) {
				try {
					((Fetchable) next).fetch();
				} catch (Exception ex) {
					if (ex instanceof ResourceNotFoundException
							|| ex.getCause() instanceof ResourceNotFoundException) {
						if (dir instanceof EntryContainer
								&& next instanceof Identifiable) {
							((EntryContainer) dir).getEntryIds().remove(
									((Identifiable) next).getId());
						}
						next.remove();
					}
				}
			}
		}
		try {
			if (dir instanceof EntryContainer) {

				EntryContainer ec = (EntryContainer) dir;
				Set<String> currIds = ec.getEntryIds();
				Set<String> latestIds = ec.getCurrentEntryIds();
				Set<String> difference = new HashSet<String>(latestIds);
				difference.removeAll(currIds);
				for (String id : difference) {
					ec.addNewEntryById(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int link(String arg0, String arg1) throws FuseException {
		return Errno.EROFS;
	}

	public int mkdir(String path, int mode) throws FuseException {
		Dir parent = lookupParent(path);
		if (parent != null) {
			java.io.File f = new java.io.File(path);
			return parent.createChild(f.getName(), true);
		}
		return Errno.EROFS;
	}

	public int mknod(String path, int mode, int rdev) throws FuseException {
		Dir parent = lookupParent(path);
		if (parent != null) {
			java.io.File f = new java.io.File(path);
			if (File.isTempFile(f.getName())) {
				return parent.createTempChild(f.getName());
			} else {
				return parent.createChild(f.getName(), false);
			}
		}
		return Errno.ENOENT;
	}

	public int open(String path, int flags, FuseOpenSetter openSetter)
			throws FuseException {
		Node n = lookup(path);
		if (n != null) {
			openSetter.setFh(new FileHandle(n));
			return 0;
		}
		return Errno.ENOENT;
	}

	public int read(String path, Object fh, ByteBuffer buf, long offset)
			throws FuseException {
		if (fh instanceof FileHandle) {
			File f = (File) ((FileHandle) fh).getNode();
			return f.read(buf, offset);
		}
		return Errno.EBADF;
	}

	public int readlink(String arg0, CharBuffer arg1) throws FuseException {
		return Errno.ENOENT;
	}

	public int release(String path, Object fh, int flags) throws FuseException {
		if (fh instanceof FileHandle)
			return ((FileHandle) fh).release();
		return 0;
	}

	public int rename(String from, String to) throws FuseException {
		Node n = lookup(from);
		Dir p = lookupParent(to);
		java.io.File f = new java.io.File(to);
		return n.move(p, f.getName());
	}

	public int rmdir(String path) throws FuseException {
		Node n = lookup(path);
		return n.delete();
	}

	public int statfs(FuseStatfsSetter statfsSetter) throws FuseException {
		statfsSetter.set(BLOCK_SIZE, 100000, 20000, 18000, Node.nfiles, 0,
				NAME_LENGTH);
		return 0;
	}

	public int symlink(String arg0, String arg1) throws FuseException {
		return Errno.EROFS;
	}

	public int truncate(String path, long size) throws FuseException {
		Node n = lookup(path);
		if (n instanceof File) {
			((File) n).truncate(size);
			return 0;
		}
		return Errno.ENOENT;
	}

	public int unlink(String path) throws FuseException {
		Node n = lookup(path);
		java.io.File f = new java.io.File(path);
		if (File.isTempFile(f.getName())) {
			n.remove();
			return 0;
		} else {
			return n.delete();
		}
	}

	public int utime(String path, int arg1, int arg2) throws FuseException {
		Node n = lookup(path);
		if (n != null) {
			n.updateAccessTime();
			n.updateModifyTime();
		}
		return 0;
	}

	public int write(String path, Object fh, boolean isWritepage,
			ByteBuffer buf, long offset) throws FuseException {
		if (fh instanceof FileHandle) {
			((FileHandle) fh).setDirty(true);
			File f = (File) ((FileHandle) fh).getNode();
			return f.write(isWritepage, buf, offset);
		}

		return Errno.EBADF;
	}

	public int getxattr(String path, String name, ByteBuffer dst)
			throws FuseException, BufferOverflowException {
		Node n = lookup(path);
		if (n == null)
			return Errno.ENOENT;
		byte[] value = n.xattrs.get(name);
		if (value == null)
			return Errno.ENOATTR;
		dst.put(value);
		return 0;
	}

	public int getxattrsize(String path, String name, FuseSizeSetter sizeSetter)
			throws FuseException {
		Node n = lookup(path);
		if (n == null)
			return Errno.ENOENT;
		byte[] value = n.xattrs.get(name);
		if (value == null)
			return Errno.ENOATTR;
		sizeSetter.setSize(value.length);
		return 0;
	}

	public int listxattr(String path, XattrLister lister) throws FuseException {
		Node n = lookup(path);
		if (n == null)
			return Errno.ENOENT;
		for (String xattrName : n.xattrs.keySet())
			lister.add(xattrName);
		return 0;
	}

	public int removexattr(String path, String name) throws FuseException {
		Node n = lookup(path);
		if (n == null)
			return Errno.ENOENT;
		n.xattrs.remove(name);
		return 0;
	}

	public int setxattr(String path, String name, ByteBuffer value, int flags)
			throws FuseException {
		Node n = lookup(path);
		if (n == null)
			return Errno.ENOENT;
		byte[] dest = new byte[256];
		value.get(dest, 0, value.remaining());
		n.xattrs.put(name, dest);
		return 0;
	}

	public static void main(String[] args) {
		// log.info("entering");
		try {
			FuseMount.mount(args, new GoofsFS(), log);
		} catch (Exception e) {
			log.error("Unable to mount filesystem", e);
		} finally {
			log.info("exiting");
		}
	}
}
