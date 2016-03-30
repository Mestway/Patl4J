package goofs.fs.blogger;

import fuse.Errno;
import goofs.EntryContainer;
import goofs.GoofsProperties;
import goofs.ServiceFactory;
import goofs.blogger.Blog;
import goofs.blogger.IBlogger;
import goofs.fs.Dir;
import goofs.fs.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlogsDir extends Dir implements EntryContainer {

	private IBlogger blogger;

	protected Set<String> entryIds = new HashSet<String>();

	public BlogsDir(Dir parent) throws Exception {

		super(parent, GoofsProperties.INSTANCE
				.getProperty("goofs.blogger.blogs"), 0777);

		blogger = (IBlogger) ServiceFactory.getService(IBlogger.class);

		List<Blog> blogs = blogger.getBlogs();

		for (Blog blog : blogs) {

			BlogDir dir = new BlogDir(this, blog);

			add(dir);

			entryIds.add(blog.getEntry().getSelfLink().getHref());
		}

	}

	public void addNewEntryById(String entryId) throws Exception {

		Blog blog = getBlogger().getBlogById(entryId);

		BlogDir dir = new BlogDir(this, blog);

		add(dir);

		entryIds.add(blog.getEntry().getSelfLink().getHref());

	}

	public Set<String> getCurrentEntryIds() throws Exception {

		Set<String> current = new HashSet<String>();

		List<Blog> blogs = blogger.getBlogs();

		for (Blog blog : blogs) {

			current.add(blog.getEntry().getSelfLink().getHref());
		}

		return current;
	}

	public Set<String> getEntryIds() {
		return entryIds;
	}

	protected IBlogger getBlogger() {
		return blogger;
	}

	protected void setBlogger(IBlogger blogger) {
		this.blogger = blogger;
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
		return Errno.EROFS;
	}

	@Override
	public int createTempChild(String name) {

		return Errno.EROFS;
	}

	@Override
	public int createChildFromExisting(String name, Node child) {
		return Errno.EROFS;
	}

}
