package goofs.blogger;

import goofs.BaseTestCase;

import java.util.List;

public class BloggerTestCase extends BaseTestCase {

	protected IBlogger blogger;

	protected void setUp() throws Exception {
		super.setUp();

		blogger = new Blogger(getUsername(), getPassword());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetBlogs() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

	}

	public void testGetPostsBlog() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		List<Post> posts = blogger.getPosts(blogs.get(0));

		assert (posts.size() >= 1);

	}

	public void testGetPostsBlogDateTimeDateTime() {
		fail("Not yet implemented");
	}

	public void testCreatePostBlogStringString() throws Exception {
		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		List<Post> posts = blogger.getPosts(blogs.get(0));

		int sizeBefore = posts.size();

		Post post = blogger.createPost(blogs.get(0), "Test", "Test");

		assert (post.getEntry().getTitle().getPlainText().equals("Test"));

		posts = blogger.getPosts(blogs.get(0));

		assert (posts.size() == sizeBefore + 1);

	}

	public void testCreatePostBlogStringStringBoolean() {
		fail("Not yet implemented");
	}

	public void testUpdatePost() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		List<Post> posts = blogger.getPosts(blogs.get(0));

		int sizeBefore = posts.size();

		Post post = blogger.createPost(blogs.get(0), "Test", "Test");

		assert (post.getEntry().getTitle().getPlainText().equals("Test"));

		posts = blogger.getPosts(blogs.get(0));

		assert (posts.size() == sizeBefore + 1);

		blogger.updatePost(post, "Updated", "Updated");
	}

	public void testDeletePost() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		List<Post> posts = blogger.getPosts(blogs.get(0));

		int sizeBefore = posts.size();

		Post post = blogger.createPost(blogs.get(0), "Test", "Test");

		assert (post.getEntry().getTitle().getPlainText().equals("Test"));

		posts = blogger.getPosts(blogs.get(0));

		int sizeAfterAdd = posts.size();

		assert (sizeAfterAdd == sizeBefore + 1);

		blogger.deletePost(post);

		posts = blogger.getPosts(blogs.get(0));

		assert (posts.size() == sizeBefore);
	}

	public void testCreateComment() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		Post post = blogger.createPost(blogs.get(0), "Test", "Test");

		List<Comment> comments = blogger.getComments(blogs.get(0), post);

		int numCommentsBefore = comments.size();

		blogger.createComment(blogs.get(0), post, "Test");

		comments = blogger.getComments(blogs.get(0), post);

		assert (numCommentsBefore + 1 == comments.size());

	}

	public void testGetComments() throws Exception {

		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		List<Post> posts = blogger.getPosts(blogs.get(0));

		assert (posts.size() >= 1);

		List<Comment> comments = blogger
				.getComments(blogs.get(0), posts.get(0));

		assert (comments.size() >= 1);

	}

	public void testDeleteComment() throws Exception {
		List<Blog> blogs = blogger.getBlogs();

		assert (blogs.size() >= 1);

		Post post = blogger.createPost(blogs.get(0), "Test", "Test");

		List<Comment> comments = blogger.getComments(blogs.get(0), post);

		int numCommentsBefore = comments.size();

		Comment comment = blogger.createComment(blogs.get(0), post, "Test");

		comments = blogger.getComments(blogs.get(0), post);

		assert (numCommentsBefore + 1 == comments.size());

		blogger.deleteComment(comment);

		comments = blogger.getComments(blogs.get(0), post);

		assert (numCommentsBefore == comments.size());
	}

}
