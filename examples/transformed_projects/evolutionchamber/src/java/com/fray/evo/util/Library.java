package com.fray.evo.util;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Library<T> {
	
    final protected ArrayList<T> libraryList = new ArrayList<T>();
    final protected HashMap<Integer, T> idToItemMap = new HashMap<Integer, T>();
    
	public ArrayList<T> getList(){
	    return libraryList;
	}
	
	public HashMap<Integer, T> getIdToItemMap(){
	    return idToItemMap;
	}
}
