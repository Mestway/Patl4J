package goofs.blogger;

import com.google.gdata.data.Entry;
import com.google.gdata.data.TextContent;

public class Comment {

	protected Entry entry;

	public Comment(Entry entry) {
		this.entry = entry;
	}

	public Entry getEntry() {
		return entry;
	}

	public String getCommentId() {

		// String[] tokens = entry.getSelfLink().getHref().split("/");
		// return tokens[tokens.length - 1];

		return getEntry().getId().split("comment-")[1];
	}

	public String getContent() {

		return ((TextContent) getEntry().getContent()).getContent()
				.getPlainText();
	}
}
