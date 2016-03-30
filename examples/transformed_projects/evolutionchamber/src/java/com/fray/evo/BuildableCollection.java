package com.fray.evo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.fray.evo.util.Buildable;
import com.fray.evo.util.Library;
import com.fray.evo.util.Race;


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


    public void put(T building, int num){
        arr[building.getId()] = num;
    }

    public int get(T building){
        return arr[building.getId()];
    }

    public boolean  containsKey(T building){
        return (arr[building.getId()] != 0);
    }

    public int getById(int id){
        return arr[id];
    }
    
    public int getSize(){
        return arr.length;
    }
    
    public int getCount(){
        int result = 0;
        for(int i = 0; i < arr.length; i++){
            result += arr[i];
        }
        return result;
    }
    
    public HashMap<T,Integer> toHashMap(Library<T> library){
    	HashMap<Integer, T> idToItemMap = library.getIdToItemMap();
    	
        HashMap<T, Integer> result = new HashMap<T, Integer>();
        for(int i=0;i < arr.length;i++){
            result.put(idToItemMap.get(i), arr[i]);
        }
        return result;
    }
    
    public void putById(int id, int count){
        arr[id] = count;
    }
    
    @Override
    public BuildableCollection<T> clone(){
    	BuildableCollection<T> result = new BuildableCollection<T>(arr.length, race);
        for(int i = 0; i < arr.length;i++){
            result.putById(i, arr[i]);
        }
        return result;
    }
}
