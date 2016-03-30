package com.fray.evo.util.optimization;

import java.io.Serializable;
import java.util.Arrays;

public final class ArrayListInt implements Serializable {

	private static final long serialVersionUID = 4944360631178054777L;

	private transient int firstIndex;

    private transient int lastIndex;

    private transient int total;

    private transient int[] array;

    public ArrayListInt() {
        this(10);
    }

    public ArrayListInt(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        firstIndex = lastIndex = 0;
        array = new int[capacity];
        total = 0;
    }

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

    public boolean add(int object) {
        if (lastIndex == array.length) {
            growAtEnd(1);
        }
        array[lastIndex++] = object;
    	total += object;
        return true;
    }

    public void clear() {
        if (firstIndex != lastIndex) {
            Arrays.fill(array, firstIndex, lastIndex, 0);
            firstIndex = lastIndex = 0;
        	total = 0;
        }
    }

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

    public boolean contains(int object) {
        for (int i = firstIndex; i < lastIndex; i++) {
            if (object == array[i]) {
                return true;
            }
        }
        return false;
    }

    public int decrement(int location) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            int result = array[firstIndex + location];
            array[firstIndex + location]--;
            total--;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

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
        System.arraycopy(array, location + firstIndex, newArray, newFirst
                + location + required, size - location);
        System.arraycopy(array, firstIndex, newArray, newFirst, location);
        firstIndex = newFirst;
        lastIndex = size + increment;

        array = newArray;
    }

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

    public int set(int location, int object) {
        if (0 <= location && location < (lastIndex - firstIndex)) {
            int result = array[firstIndex + location];
            array[firstIndex + location] = object;
            total += object - result;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return lastIndex - firstIndex;
    }

    public Object[] toArray() {
        int size = lastIndex - firstIndex;
        Object[] result = new Object[size];
        System.arraycopy(array, firstIndex, result, 0, size);
        return result;
    }

    public int total() {
    	return total;
    }

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
