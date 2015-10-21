package goofs.fs.blogger;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.Fetchable;
import goofs.Identifiable;
import goofs.blogger.Blog;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.Node;
import goofs.fs.SimpleFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlogDir extends Dir implements EntryContainer, Identifiable,
		Fetchable {

	private String blogId;

	protected Set<String> entryIds = new HashSet<String>();

	public BlogDir(Dir parent, Blog blog) throws Exception {

		super(parent, blog.getBlogTitle(), 0777);

		setBlogId(blog.getEntry().getSelfLink().getHref());

		List<Post> posts = getBlogger().getPosts(blog);

		for (Post post : posts) {

			PostDir postDir = new PostDir(this, post);

			add(postDir);

			entryIds.add(post.getEntry().getSelfLink().getHref());
		}

	}

	public void addNewEntryById(String entryId) throws Exception {

		Post post = getBlogger().getPostById(entryId);

		PostDir postDir = new PostDir(this, post);

		add(postDir);

		entryIds.add(post.getEntry().getSelfLink().getHref());

	}

	public Set<String> getCurrentEntryIds() throws Exception {

		Set<String> current = new HashSet<String>();
		List<Post> posts = getBlogger().getPosts(getBlog());

		for (Post post : posts) {

			current.add(post.getEntry().getSelfLink().getHref());
		}

		return current;

	}

	public Set<String> getEntryIds() {

		return entryIds;

	}

	public String getId() {
		return getBlogId();
	}

	protected String getBlogId() {
		return blogId;
	}

	protected void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	protected IBlogger getBlogger() {

		BlogsDir parentDir = (BlogsDir) getParent();

		return parentDir.getBlogger();
	}

	public Blog getBlog() throws Exception {

		return getBlogger().getBlogById(getBlogId());

	}

	public Object fetch() throws Exception {

		return getBlog();
	}

	@Override
	public int delete() {
		return Errno.EROFS;
	}

	@Override
	public int rename(Dir newParent, String name) {
		return Errno.EROFS;
	}

	@Override
	public int createChild(String name, boolean isDir) {

		if (!isDir)
			return Errno.EROFS;

		try {
			Post post = getBlogger().createPost(getBlog(), name, name);

			PostDir postDir = new PostDir(this, post);

			add(postDir);

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

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

}
