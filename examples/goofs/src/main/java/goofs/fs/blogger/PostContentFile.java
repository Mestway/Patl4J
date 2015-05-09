package goofs.fs.blogger;

import fuse.Errno;
import goofs.blogger.Blog;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.File;

public class PostContentFile extends File {

	protected String postId;

	public PostContentFile(Dir parent, Post post) throws Exception {

		super(parent, post.getEntry().getTitle().getPlainText(), 0777, post
				.getContent());

		setPostId(post.getEntry().getSelfLink().getHref());

	}

	public PostContentFile(Dir parent, String name) throws Exception {

		super(parent, name, 0777, "");
	}

	protected String getPostId() {
		return postId;
	}

	protected void setPostId(String postId) {
		this.postId = postId;
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
	public int save() {

		try {

			getBlogger().updatePost(getPost(), null, new String(getContent()));

			return 0;
		} catch (Exception e) {
			return Errno.ENOENT;
		}
	}

	@Override
	public int delete() {

		try {
			getBlogger().deletePost(getPost());

			getParent().remove();

			return 0;
		} catch (Exception e) {
			return Errno.ENOENT;
		}

	}

	@Override
	public int rename(Dir newParent, String name) {

		if (getParent() == newParent) {

			setName(name);

		}

		return 0;
	}

}
