package goofs.blogger;

import com.google.gdata.data.Entry;
import com.google.gdata.data.TextContent;

public class Post {
	protected Entry entry;

	public Post(Entry entry) {
		this.entry = entry;
	}

	public Entry getEntry() {
		return entry;
	}

	public String getPostId() {

		// String[] tokens = entry.getSelfLink().getHref().split("/");
		// return tokens[tokens.length - 1];

		return getEntry().getId().split("post-")[1];
	}

	public String getContent() {

		return ((TextContent) getEntry().getContent()).getContent()
				.getPlainText();
	}
}
