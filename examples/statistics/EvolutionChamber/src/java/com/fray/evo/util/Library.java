/**
 * 
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A Library represents a collection of items. The only requirement is that it can list all elements.
 * Generic for future Races
 * 
 * @author "Beat Durrer"
 * @param <T> the class type of the items in this library (for example Unit,Upgrade or Building)
 */
public abstract class Library<T> {
	
    final protected ArrayList<T> libraryList = new ArrayList<T>();
    final protected HashMap<Integer, T> idToItemMap = new HashMap<Integer, T>();
    
	/**
	 * @return a list containing all elements of this library
	 */
	public ArrayList<T> getList(){
	    return libraryList;
	}
	
	public HashMap<Integer, T> getIdToItemMap(){
	    return idToItemMap;
	}
}
