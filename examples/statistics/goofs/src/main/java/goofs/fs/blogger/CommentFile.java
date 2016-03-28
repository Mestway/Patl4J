package goofs.fs.blogger;

import fuse.Errno;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.blogger.Blog;
import goofs.blogger.Comment;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.File;

public class CommentFile extends File implements Identifiable, Fetchable {

	private String commentId;

	public CommentFile(Dir parent, Comment comment) throws Exception {

		super(parent, comment.getEntry().getTitle().getPlainText(), 0777,
				comment.getContent());

		setCommentId(comment.getEntry().getSelfLink().getHref());
	}

	public CommentFile(Dir parent, String name) throws Exception {

		super(parent, name, 0777, "");

	}

	public String getId() {
		return getCommentId();
	}

	protected String getCommentId() {
		return commentId;
	}

	protected void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	protected IBlogger getBlogger() {

		BlogsDir parentDir = (BlogsDir) getParent().getParent().getParent()
				.getParent();

		return parentDir.getBlogger();
	}

	protected Blog getBlog() throws Exception {

		return ((BlogDir) getParent().getParent().getParent()).getBlog();

	}

	protected Post getPost() throws Exception {

		return ((CommentsDir) getParent()).getPost();

	}

	public Comment getComment() throws Exception {

		return getBlogger().getCommentById(getCommentId());

	}

	public Object fetch() throws Exception {

		return getComment();

	}

	@Override
	public int delete() {

		try {
			getBlogger().deleteComment(getComment());

			remove();

			return 0;
		} catch (Exception e) {

			return Errno.EROFS;
		}
	}

	@Override
	public int rename(Dir newParent, String name) {

		return Errno.EROFS;
	}

	@Override
	public int save() {

		try {

			if (getCommentId() == null) {

				Comment comment = getBlogger().createComment(getBlog(),
						getPost(), new String(getContent()));

				setCommentId(comment.getEntry().getSelfLink().getHref());
			}

			else {
				getBlogger().updateComment(getComment(),
						new String(getContent()));
			}

			return 0;
		} catch (Exception e) {
			return Errno.ENOENT;
		}

	}
}
