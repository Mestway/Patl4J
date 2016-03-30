package goofs;

import java.util.Set;

public interface EntryContainer {

	// what it's holding
	public Set<String> getEntryIds();

	// fresh from the back end
	public Set<String> getCurrentEntryIds() throws Exception;

	// get this entry from the back end and add it to the fs tree
	public void addNewEntryById(String entryId) throws Exception;

}
