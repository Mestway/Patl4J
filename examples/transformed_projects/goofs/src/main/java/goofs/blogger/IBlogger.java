package goofs.blogger;

import goofs.GoofsService;

import java.util.List;

import com.google.gdata.client.Query;
import com.google.gdata.data.DateTime;

public interface IBlogger extends GoofsService {

	public abstract List<Blog> getBlogs() throws Exception;

	public abstract Blog getBlogById(String blogId) throws Exception;

	public abstract Post getPostById(String postId) throws Exception;

	public abstract Comment getCommentById(String commentId) throws Exception;

	public abstract List<Post> getPosts(Blog blog) throws Exception;

	public abstract List<Post> getPosts(Blog blog, Query query)
			throws Exception;

	public abstract List<Post> getPosts(Blog blog, DateTime start, DateTime end)
			throws Exception;

	public abstract Post createPost(Blog blog, String title, String content)
			throws Exception;

	public abstract Post createPost(Blog blog, String title, String content,
			boolean isDraft) throws Exception;

	public abstract Post updatePost(Post post, String title, String content)
			throws Exception;

	public abstract void deletePost(Post post) throws Exception;

	public abstract Comment createComment(Blog blog, Post post, String comment)
			throws Exception;

	public abstract List<Comment> getComments(Blog blog, Post post)
			throws Exception;

	public abstract void deleteComment(Comment comment) throws Exception;

	public abstract Comment updateComment(Comment comment, String content)
			throws Exception;

}