package goofs.fs.blogger;

import fuse.Errno;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.blogger.Blog;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.Node;

public class PostDir extends Dir implements Identifiable, Fetchable {

	private String postId;

	public PostDir(Dir parent, Post post) throws Exception {

		super(parent, post.getEntry().getTitle().getPlainText(), 0777);

		setPostId(post.getEntry().getSelfLink().getHref());

		CommentsDir commentsDir = new CommentsDir(this);

		add(commentsDir);

		PostContentFile contentFile = new PostContentFile(this, post);

		add(contentFile);

	}

	public String getId() {

		return getPostId();
	}

	protected String getPostId() {
		return postId;
	}

	protected void setPostId(String postId) {
		this.postId = postId;
	}

	public Post getPost() throws Exception {

		return getBlogger().getPostById(getPostId());

	}

	public Object fetch() throws Exception {

		return getPost();

	}

	protected IBlogger getBlogger() {

		BlogsDir parentDir = (BlogsDir) getParent().getParent();

		return parentDir.getBlogger();
	}

	protected Blog getBlog() throws Exception {
		return ((BlogDir) getParent()).getBlog();

	}

	@Override
	public int delete() {

		try {
			getBlogger().deletePost(getPost());

			remove();

			return 0;
		} catch (Exception e) {
			return Errno.ENOENT;
		}
	}

	@Override
	public int rename(Dir newParent, String name) {

		try {
			getBlogger().updatePost(getPost(), name, null);

			return 0;
		} catch (Exception e) {

			return Errno.ENOENT;
		}

	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (!isDir) {
			try {
				PostContentFile f = new PostContentFile(this, name);

				add(f);

				return 0;

			} catch (Exception e) {
				return Errno.EROFS;
			}
		}

		return Errno.EROFS;

	}

	@Override
	public int createTempChild(String name) {

		try {
			PostContentTempFile f = new PostContentTempFile(this, name);

			add(f);

			return 0;

		} catch (Exception e) {
			return Errno.EROFS;
		}

	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

}
