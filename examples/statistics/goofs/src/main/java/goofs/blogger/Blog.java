package goofs.blogger;

import com.google.gdata.data.Entry;

public class Blog {

	protected Entry entry;

	public Blog(Entry entry) {
		this.entry = entry;
	}

	public String getBlogId() {

		return entry.getId().split("blog-")[1];
	}

	public String getBlogTitle() {

		return entry.getTitle().getPlainText();
	}

	public Entry getEntry() {
		return entry;
	}
	
	

}
