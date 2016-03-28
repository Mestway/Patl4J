package com.fray.evo.util.optimization;

/*
 *  Adapted by Nafets.st from Apache's work 
 * 
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.Serializable;
import java.util.Arrays;

/**
 * ArrayListInt is an implementation of {@link List}, backed by an array. All
 * optional operations adding, removing, and replacing are supported.
 */
public final class ArrayListInt implements Serializable {

	private static final long serialVersionUID = 4944360631178054777L;

	private transient int firstIndex;

    private transient int lastIndex;

    private transient int total;

    private transient int[] array;

    /**
     * Constructs a new instance of {@code ArrayListInt} with ten capacity.
     */
    public ArrayListInt() {
        this(10);
    }

    /**
     * Constructs a new instance of {@code ArrayListInt} with the specified
     * capacity.
     * 
     * @param capacity
     *            the initial capacity of this {@code ArrayListInt}.
     */
    public ArrayListInt(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        firstIndex = lastIndex = 0;
        array = new int[capacity];
        total = 0;
    }

    /**
     * Inserts the specified object into this {@code ArrayListInt} at the specified
     * location. The object is inserted before any previous element at the
     * specified location. If the location is equal to the size of this
     * {@code ArrayListInt}, the object is added at the end.
     * 
     * @param location
     *            the index at which to insert the object.
     * @param object
     *            the object to add.
     * @throws IndexOutOfBoundsException
     *             when {@code location < 0 || > size()}
     */
    public void add(int location, int object) {
    	total += object;
        int size = lastIndex - firstIndex;
        if (0 < location && location < size) {
            if (firstIndex == 0 && lastIndex == array.length) {
                growForInsert(location, 1);
            } else if ((location < size / 2 && firstIndex > 0)
                    || lastIndex == array.length) {
                System.arraycopy(array, firstIndex, array, --firstIndex,
                        location);
            } else {
                int index = location + firstIndex;
                System.arraycopy(array, index, array, index + 1, size
                        - location);
                lastIndex++;
            }
            array[location + firstIndex] = object;
        } else if (location == 0) {
            if (firstIndex == 0) {
                growAtFront(1);
            }
            array[--firstIndex] = object;
        } else if (location == size) {
            if (lastIndex == array.length) {
                growAtEnd(1);
            }
            array[lastIndex++] = object;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Adds the specified object at the end of this {@code ArrayListInt}.
     * 
     * @param object
     *            the object to add.
     * @return always true
     */
    public boolean add(int object) {
        if (lastIndex == array.length) {
            growAtEnd(1);
        }
        array[lastIndex++] = object;
    	total += object;
        return true;
    }

    /**
     * Removes all elements from this {@code ArrayListInt}, leaving it empty.
     * 
     * @see #isEmpty
     * @see #size
     */
    public void clear() {
        if (firstIndex != lastIndex) {
            Arrays.fill(array, firstIndex, lastIndex, 0);
            firstIndex = lastIndex = 0;
        	total = 0;
        }
    }

    /**
     * Returns a new {@code ArrayListInt} with the same elements, the same size and
     * the same capacity as this {@code ArrayListInt}.
     * 
     * @return a shallow copy of this {@code ArrayListInt}
     * @see java.lang.Cloneable
     */
    public Object clone() {
        try {
            ArrayListInt newList = (ArrayListInt) super.clone();
            newList.array = array.clone();
            newList.firstIndex = firstIndex;
            newList.lastIndex = lastIndex;
            newList.total = total;
            return newList;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Searches this {@code ArrayListInt} for the specified object.
     * 
     * @param object
     *            the object to search for.
     * @return {@code true} if {@code object} is an element of this
     *         {@code ArrayListInt}, {@code false} otherwise
     */
    public boolean contains(int object) {
        for (int i = firstIndex; i < lastIndex; i++) {
            if (object == array[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Decrements the element at the specified location in this {@code ArrayListInt}
     * with the specified object.
     * 
     * @param location
     *            the index at which to put the specified object.
     * @return the previous element at the index.
     * @throws IndexOutOfBoundsException
     *             when {@code location < 0 || >= size()}
     */
    public int decrement(int location) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            int result = array[firstIndex + location];
            array[firstIndex + location]--;
            total--;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Ensures that after this operation the {@code ArrayListInt} can hold the
     * specified number of elements without further growing.
     * 
     * @param minimumCapacity
     *            the minimum capacity asked for.
     */
    public void ensureCapacity(int minimumCapacity) {
        if (array.length < minimumCapacity) {
            if (firstIndex > 0) {
                growAtFront(minimumCapacity - array.length);
            } else {
                growAtEnd(minimumCapacity - array.length);
            }
        }
    }

    public int get(int location) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            return array[firstIndex + location];
        }
        throw new IndexOutOfBoundsException();
    }

    private void growAtEnd(int required) {
        int size = lastIndex - firstIndex;
        if (firstIndex >= required - (array.length - lastIndex)) {
            int newLast = lastIndex - firstIndex;
            if (size > 0) {
                System.arraycopy(array, firstIndex, array, 0, size);
                int start = newLast < firstIndex ? firstIndex : newLast;
                Arrays.fill(array, start, array.length, 0);
            }
            firstIndex = 0;
            lastIndex = newLast;
        } else {
            int increment = size / 2;
            if (required > increment) {
                increment = required;
            }
            if (increment < 12) {
                increment = 12;
            }
            int[] newArray = newElementArray(size + increment);
            if (size > 0) {
                System.arraycopy(array, firstIndex, newArray, 0, size);
                firstIndex = 0;
                lastIndex = size;
            }
            array = newArray;
        }
    }

    private void growAtFront(int required) {
        int size = lastIndex - firstIndex;
        if (array.length - lastIndex + firstIndex >= required) {
            int newFirst = array.length - size;
            if (size > 0) {
                System.arraycopy(array, firstIndex, array, newFirst, size);
                int length = firstIndex + size > newFirst ? newFirst
                        : firstIndex + size;
                Arrays.fill(array, firstIndex, length, 0);
            }
            firstIndex = newFirst;
            lastIndex = array.length;
        } else {
            int increment = size / 2;
            if (required > increment) {
                increment = required;
            }
            if (increment < 12) {
                increment = 12;
            }
            int[] newArray = newElementArray(size + increment);
            if (size > 0) {
                System.arraycopy(array, firstIndex, newArray, newArray.length
                        - size, size);
            }
            firstIndex = newArray.length - size;
            lastIndex = newArray.length;
            array = newArray;
        }
    }

    private void growForInsert(int location, int required) {
        int size = lastIndex - firstIndex;
        int increment = size / 2;
        if (required > increment) {
            increment = required;
        }
        if (increment < 12) {
            increment = 12;
        }
        int[] newArray = newElementArray(size + increment);
        int newFirst = increment - required;
        // Copy elements after location to the new array skipping inserted
        // elements
        System.arraycopy(array, location + firstIndex, newArray, newFirst
                + location + required, size - location);
        // Copy elements before location to the new array from firstIndex
        System.arraycopy(array, firstIndex, newArray, newFirst, location);
        firstIndex = newFirst;
        lastIndex = size + increment;

        array = newArray;
    }

    /**
     * Increments the element at the specified location in this {@code ArrayListInt}
     * with the specified object.
     * 
     * @param location
     *            the index at which to put the specified object.
     * @return the previous element at the index.
     * @throws IndexOutOfBoundsException
     *             when {@code location < 0 || >= size()}
     */
    public int increment(int location) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            int result = array[firstIndex + location];
            array[firstIndex + location]++;
            total++;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }
    
    public boolean isEmpty() {
        return lastIndex == firstIndex;
    }

    /**
     * Removes the object at the specified location from this list.
     * 
     * @param location
     *            the index of the object to remove.
     * @return the removed object.
     * @throws IndexOutOfBoundsException
     *             when {@code location < 0 || >= size()}
     */
    public int remove(int location) {
        int result;
        int size = lastIndex - firstIndex;
        if (0 <= location && location < size) {
            if (location == size - 1) {
                result = array[--lastIndex];
                array[lastIndex] = 0;
            } else if (location == 0) {
                result = array[firstIndex];
                array[firstIndex++] = 0;
            } else {
                int elementIndex = firstIndex + location;
                result = array[elementIndex];
                if (location < size / 2) {
                    System.arraycopy(array, firstIndex, array, firstIndex + 1,
                            location);
                    array[firstIndex++] = 0;
                } else {
                    System.arraycopy(array, elementIndex + 1, array,
                            elementIndex, size - location - 1);
                    array[--lastIndex] = 0;
                }
            }
            if (firstIndex == lastIndex) {
                firstIndex = lastIndex = 0;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
        total -= result;
        return result;
    }

    /**
     * Replaces the element at the specified location in this {@code ArrayListInt}
     * with the specified object.
     * 
     * @param location
     *            the index at which to put the specified object.
     * @param object
     *            the object to add.
     * @return the previous element at the index.
     * @throws IndexOutOfBoundsException
     *             when {@code location < 0 || >= size()}
     */
    public int set(int location, int object) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            int result = array[firstIndex + location];
            array[firstIndex + location] = object;
            total += object - result;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Returns the number of elements in this {@code ArrayListInt}.
     * 
     * @return the number of elements in this {@code ArrayListInt}.
     */
    public int size() {
        return lastIndex - firstIndex;
    }

    /**
     * Returns a new array containing all elements contained in this
     * {@code ArrayListInt}.
     * 
     * @return an array of the elements from this {@code ArrayListInt}
     */
    public Object[] toArray() {
        int size = lastIndex - firstIndex;
        Object[] result = new Object[size];
        System.arraycopy(array, firstIndex, result, 0, size);
        return result;
    }

    /**
     * Returns a the total of all elements contained in this
     * {@code ArrayListInt}.
     * 
     * @return the total of the elements from this {@code ArrayListInt}
     */
    public int total() {
    	return total;
    }

    /**
     * Sets the capacity of this {@code ArrayListInt} to be the same as the current
     * size.
     * 
     * @see #size
     */
    public void trimToSize() {
        int size = lastIndex - firstIndex;
        int[] newArray = newElementArray(size);
        System.arraycopy(array, firstIndex, newArray, 0, size);
        array = newArray;
        firstIndex = 0;
        lastIndex = array.length;
    }
    
    private int[] newElementArray(int size) {
    	int[] a = new int[size];
        Arrays.fill(a, 0, a.length, 0);
        return a;
    }
}
