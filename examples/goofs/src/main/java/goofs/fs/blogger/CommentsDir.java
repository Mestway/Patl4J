package goofs.fs.blogger;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.GoofsProperties;
import goofs.blogger.Blog;
import goofs.blogger.Comment;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.SimpleDir;
import goofs.fs.SimpleFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentsDir extends SimpleDir implements EntryContainer {

	protected Set<String> entryIds = new HashSet<String>();

	public CommentsDir(Dir parent) {
		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.blogger.comments"));

		try {
			List<Comment> comments = getBlogger().getComments(getBlog(),
					getPost());

			for (Comment comment : comments) {

				CommentFile commentFile = new CommentFile(this, comment);

				add(commentFile);

				entryIds.add(comment.getEntry().getSelfLink().getHref());

			}
		} catch (Exception e) {

		}

	}

	public void addNewEntryById(String entryId) throws Exception {

		Comment comment = getBlogger().getCommentById(entryId);

		CommentFile commentFile = new CommentFile(this, comment);

		add(commentFile);

		entryIds.add(comment.getEntry().getSelfLink().getHref());
	}

	public Set<String> getCurrentEntryIds() throws Exception {

		Set<String> current = new HashSet<String>();

		List<Comment> comments = getBlogger().getComments(getBlog(), getPost());
		for (Comment comment : comments) {

			current.add(comment.getEntry().getSelfLink().getHref());
		}

		return current;

	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	protected IBlogger getBlogger() {

		BlogsDir parentDir = (BlogsDir) getParent().getParent().getParent();

		return parentDir.getBlogger();
	}

	protected Blog getBlog() throws Exception {
		return ((BlogDir) getParent().getParent()).getBlog();

	}

	protected Post getPost() throws Exception {

		return ((PostDir) getParent()).getPost();
	}

	@Override
	public int createChild(String name, boolean isDir) {
		if (isDir)
			return Errno.EROFS;

		try {

			CommentFile commentFile = new CommentFile(this, name);

			add(commentFile);

			return 0;
		} catch (Exception e) {
			return Errno.EROFS;
		}

	}

	@Override
	public int createTempChild(String name) {
		try {
			SimpleFile f = new SimpleFile(this, name);

			add(f);

			return 0;

		} catch (Exception e) {
			return Errno.EROFS;
		}

	}

}
