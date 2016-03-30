package com.fray.evo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.fray.evo.util.Buildable;
import com.fray.evo.util.Library;
import com.fray.evo.util.Race;

/**
 * This Class is an alternative to HashMap<T,Integer> and offers better performance than a HashMap, since it uses an int array.
 * It holds an integer for every Buildable <T>.
 * 
 * @author dub
 * @param <T> the Buildable type for this collection. For this class to work, only {@link Buildable#getId()} is required to be set correctly
 */
public class BuildableCollection<T extends Buildable> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final int[] arr;
    protected final Race race;

    public BuildableCollection(Collection<T> buildings, Race race){
       this(buildings.size(),race);
    }
    
    public BuildableCollection(int size, Race race){
        arr = new int[size];
        this.race = race;
    }

    /**
     * set the amount of an Buildable
     * @param buildingB
     * @param num
     */
    public void put(T building, int num){
        arr[building.getId()] = num;
    }

    /**
     * get amount for an Buildable
     * @param building
     * @return
     */
    public int get(T building){
        return arr[building.getId()];
    }
    
    /**
     * check if this Buildable is available in this collection
     * @param building
     * @return
     */
    public boolean  containsKey(T building){
        return (arr[building.getId()] != 0);
    }
    
    /**
     * get amount for the Buildable that corresponds to {@link Buildable#getId()}
     * @param id
     * @return
     */
    public int getById(int id){
        return arr[id];
    }
    
    /**
     * @return the list size
     */
    public int getSize(){
        return arr.length;
    }
    
    /**
     * calculates the total amount over all Buildables
     * @return
     */
    public int getCount(){
        int result = 0;
        for(int i = 0; i < arr.length; i++){
            result += arr[i];
        }
        return result;
    }
    
    /**
     * returns the content as a HashMap. The Library must match the items and race of this collection
     * @param library
     * @return
     */
    public HashMap<T,Integer> toHashMap(Library<T> library){
    	HashMap<Integer, T> idToItemMap = library.getIdToItemMap();
    	
        HashMap<T, Integer> result = new HashMap<T, Integer>();
        for(int i=0;i < arr.length;i++){
            result.put(idToItemMap.get(i), arr[i]);
        }
        return result;
    }
    
    /**
     * set an amount by id. The id must correspond to {@link Buildable#getId()}
     * @param id
     * @param count
     */
    public void putById(int id, int count){
        arr[id] = count;
    }
    
    /**
     * clone this collection.
     */
    @Override
    public BuildableCollection<T> clone(){
    	BuildableCollection<T> result = new BuildableCollection<T>(arr.length, race);
        for(int i = 0; i < arr.length;i++){
            result.putById(i, arr[i]);
        }
        return result;
    }
}
