package goofs.fs.blogger;

import fuse.Errno;
import goofs.blogger.IBlogger;
import goofs.blogger.Post;
import goofs.fs.Dir;
import goofs.fs.SimpleFile;

public class PostContentTempFile extends SimpleFile {

	public PostContentTempFile(Dir parent, String name) throws Exception {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int rename(Dir newParent, String name) {

		int rt = super.rename(newParent, name);

		if (rt == 0) {

			try {

				Post post = ((PostDir) getParent()).getPost();

				IBlogger blogger = ((PostDir) getParent()).getBlogger();

				blogger.updatePost(post, null, new String(getContent()));

				Dir parent = getParent();

				remove();

				parent.add(new PostContentFile(parent, post));

				return 0;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return Errno.EROFS;
			}

		}

		return rt;
	}

}
