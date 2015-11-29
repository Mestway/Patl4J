package com.fray.evo.util.optimization;
import java.io.Serializable;
import java.util.Arrays;
/** 
 * ArrayListInt is an implementation of                                    {@link List}, backed by an array. All optional operations adding, removing, and replacing are supported.
 */
public final class ArrayListInt implements Serializable {
  private static final long serialVersionUID=4944360631178054777L;
  private transient int firstIndex;
  private transient int lastIndex;
  private transient int total;
  private transient int[] array;
  /** 
 * Constructs a new instance of                                    {@code ArrayListInt} with ten capacity.
 */
  public ArrayListInt(){
    this(10);
  }
  /** 
 * Constructs a new instance of                                    {@code ArrayListInt} with the specifiedcapacity.
 * @param capacity the initial capacity of this  {@code ArrayListInt}.
 */
  public ArrayListInt(  int capacity){
    int genVar4683;
    genVar4683=0;
    boolean genVar4684;
    genVar4684=capacity < genVar4683;
    if (genVar4684) {
      java.lang.IllegalArgumentException genVar4685;
      genVar4685=new IllegalArgumentException();
      throw genVar4685;
    }
 else {
      ;
    }
    firstIndex=lastIndex=0;
    array=new int[capacity];
    total=0;
  }
  /** 
 * Inserts the specified object into this                                    {@code ArrayListInt} at the specifiedlocation. The object is inserted before any previous element at the specified location. If the location is equal to the size of this {@code ArrayListInt}, the object is added at the end.
 * @param location the index at which to insert the object.
 * @param object the object to add.
 * @throws IndexOutOfBoundsException when  {@code location < 0 || > size()}
 */
  public void add(  int location,  int object){
    total+=object;
    int size;
    size=lastIndex - firstIndex;
    boolean genVar4686;
    genVar4686=0 < location && location < size;
    if (genVar4686) {
      boolean genVar4687;
      genVar4687=firstIndex == 0 && lastIndex == array.length;
      if (genVar4687) {
        ArrayListInt genVar4688;
        genVar4688=this;
        int genVar4689;
        genVar4689=1;
        genVar4688.growForInsert(location,genVar4689);
      }
 else {
        boolean genVar4690;
        genVar4690=(location < size / 2 && firstIndex > 0) || lastIndex == array.length;
        if (genVar4690) {
          int genVar4691;
          genVar4691=--firstIndex;
          System.arraycopy(array,firstIndex,array,genVar4691,location);
        }
 else {
          int index;
          index=location + firstIndex;
          int genVar4692;
          genVar4692=1;
          int genVar4693;
          genVar4693=index + genVar4692;
          int genVar4694;
          genVar4694=size - location;
          System.arraycopy(array,index,array,genVar4693,genVar4694);
          lastIndex++;
        }
      }
      int genVar4695;
      genVar4695=location + firstIndex;
      array[genVar4695]=object;
    }
 else {
      int genVar4696;
      genVar4696=0;
      boolean genVar4697;
      genVar4697=location == genVar4696;
      if (genVar4697) {
        int genVar4698;
        genVar4698=0;
        boolean genVar4699;
        genVar4699=firstIndex == genVar4698;
        if (genVar4699) {
          ArrayListInt genVar4700;
          genVar4700=this;
          int genVar4701;
          genVar4701=1;
          genVar4700.growAtFront(genVar4701);
        }
 else {
          ;
        }
        int genVar4702;
        genVar4702=--firstIndex;
        array[genVar4702]=object;
      }
 else {
        boolean genVar4703;
        genVar4703=location == size;
        if (genVar4703) {
          boolean genVar4704;
          genVar4704=lastIndex == array.length;
          if (genVar4704) {
            ArrayListInt genVar4705;
            genVar4705=this;
            int genVar4706;
            genVar4706=1;
            genVar4705.growAtEnd(genVar4706);
          }
 else {
            ;
          }
          int genVar4707;
          genVar4707=lastIndex++;
          array[genVar4707]=object;
        }
 else {
          java.lang.IndexOutOfBoundsException genVar4708;
          genVar4708=new IndexOutOfBoundsException();
          throw genVar4708;
        }
      }
    }
  }
  /** 
 * Adds the specified object at the end of this                                    {@code ArrayListInt}.
 * @param object the object to add.
 * @return always true
 */
  public boolean add(  int object){
    boolean genVar4709;
    genVar4709=lastIndex == array.length;
    if (genVar4709) {
      ArrayListInt genVar4710;
      genVar4710=this;
      int genVar4711;
      genVar4711=1;
      genVar4710.growAtEnd(genVar4711);
    }
 else {
      ;
    }
    int genVar4712;
    genVar4712=lastIndex++;
    array[genVar4712]=object;
    total+=object;
    boolean genVar4713;
    genVar4713=true;
    return genVar4713;
  }
  /** 
 * Removes all elements from this                                    {@code ArrayListInt}, leaving it empty.
 * @see #isEmpty
 * @see #size
 */
  public void clear(){
    boolean genVar4714;
    genVar4714=firstIndex != lastIndex;
    if (genVar4714) {
      int genVar4715;
      genVar4715=0;
      Arrays.fill(array,firstIndex,lastIndex,genVar4715);
      firstIndex=lastIndex=0;
      total=0;
    }
 else {
      ;
    }
  }
  /** 
 * Returns a new                                    {@code ArrayListInt} with the same elements, the same size andthe same capacity as this  {@code ArrayListInt}.
 * @return a shallow copy of this {@code ArrayListInt}
 * @see java.lang.Cloneable
 */
  public Object clone(){
    try {
      java.lang.Object genVar4716;
      genVar4716=super.clone();
      ArrayListInt newList;
      newList=(ArrayListInt)genVar4716;
      newList.array=array.clone();
      newList.firstIndex=firstIndex;
      newList.lastIndex=lastIndex;
      newList.total=total;
      return newList;
    }
 catch (    CloneNotSupportedException e) {
      return null;
    }
  }
  /** 
 * Searches this                                    {@code ArrayListInt} for the specified object.
 * @param object the object to search for.
 * @return {@code true} if {@code object} is an element of this{@code ArrayListInt},                                    {@code false} otherwise
 */
  public boolean contains(  int object){
    int i=firstIndex;
    for (; i < lastIndex; i++) {
      int genVar4717;
      genVar4717=array[i];
      boolean genVar4718;
      genVar4718=object == genVar4717;
      if (genVar4718) {
        boolean genVar4719;
        genVar4719=true;
        return genVar4719;
      }
 else {
        ;
      }
    }
    boolean genVar4720;
    genVar4720=false;
    return genVar4720;
  }
  /** 
 * Decrements the element at the specified location in this                                    {@code ArrayListInt}with the specified object.
 * @param location the index at which to put the specified object.
 * @return the previous element at the index.
 * @throws IndexOutOfBoundsException when  {@code location < 0 || >= size()}
 */
  public int decrement(  int location){
    boolean genVar4721;
    genVar4721=0 <= location && location < (lastIndex - firstIndex);
    if (genVar4721) {
      int genVar4722;
      genVar4722=firstIndex + location;
      int result;
      result=array[genVar4722];
      int genVar4723;
      genVar4723=firstIndex + location;
      int genVar4724;
      genVar4724=array[genVar4723];
      genVar4724--;
      total--;
      return result;
    }
 else {
      ;
    }
    java.lang.IndexOutOfBoundsException genVar4725;
    genVar4725=new IndexOutOfBoundsException();
    throw genVar4725;
  }
  /** 
 * Ensures that after this operation the                                    {@code ArrayListInt} can hold thespecified number of elements without further growing.
 * @param minimumCapacity the minimum capacity asked for.
 */
  public void ensureCapacity(  int minimumCapacity){
    boolean genVar4726;
    genVar4726=array.length < minimumCapacity;
    if (genVar4726) {
      int genVar4727;
      genVar4727=0;
      boolean genVar4728;
      genVar4728=firstIndex > genVar4727;
      if (genVar4728) {
        ArrayListInt genVar4729;
        genVar4729=this;
        int genVar4730;
        genVar4730=minimumCapacity - array.length;
        genVar4729.growAtFront(genVar4730);
      }
 else {
        ArrayListInt genVar4731;
        genVar4731=this;
        int genVar4732;
        genVar4732=minimumCapacity - array.length;
        genVar4731.growAtEnd(genVar4732);
      }
    }
 else {
      ;
    }
  }
  public int get(  int location){
    boolean genVar4733;
    genVar4733=0 <= location && location < (lastIndex - firstIndex);
    if (genVar4733) {
      int genVar4734;
      genVar4734=firstIndex + location;
      int genVar4735;
      genVar4735=array[genVar4734];
      return genVar4735;
    }
 else {
      ;
    }
    java.lang.IndexOutOfBoundsException genVar4736;
    genVar4736=new IndexOutOfBoundsException();
    throw genVar4736;
  }
  private void growAtEnd(  int required){
    int size;
    size=lastIndex - firstIndex;
    int genVar4737;
    genVar4737=array.length - lastIndex;
    int genVar4738;
    genVar4738=(genVar4737);
    int genVar4739;
    genVar4739=required - genVar4738;
    boolean genVar4740;
    genVar4740=firstIndex >= genVar4739;
    if (genVar4740) {
      int newLast;
      newLast=lastIndex - firstIndex;
      int genVar4741;
      genVar4741=0;
      boolean genVar4742;
      genVar4742=size > genVar4741;
      if (genVar4742) {
        int genVar4743;
        genVar4743=0;
        System.arraycopy(array,firstIndex,array,genVar4743,size);
        boolean genVar4744;
        genVar4744=newLast < firstIndex;
        int start;
        start=genVar4744 ? firstIndex : newLast;
        int genVar4745;
        genVar4745=0;
        Arrays.fill(array,start,array.length,genVar4745);
      }
 else {
        ;
      }
      firstIndex=0;
      lastIndex=newLast;
    }
 else {
      int genVar4746;
      genVar4746=2;
      int increment;
      increment=size / genVar4746;
      boolean genVar4747;
      genVar4747=required > increment;
      if (genVar4747) {
        increment=required;
      }
 else {
        ;
      }
      int genVar4748;
      genVar4748=12;
      boolean genVar4749;
      genVar4749=increment < genVar4748;
      if (genVar4749) {
        increment=12;
      }
 else {
        ;
      }
      ArrayListInt genVar4750;
      genVar4750=this;
      int genVar4751;
      genVar4751=size + increment;
      int[] newArray;
      newArray=genVar4750.newElementArray(genVar4751);
      int genVar4752;
      genVar4752=0;
      boolean genVar4753;
      genVar4753=size > genVar4752;
      if (genVar4753) {
        int genVar4754;
        genVar4754=0;
        System.arraycopy(array,firstIndex,newArray,genVar4754,size);
        firstIndex=0;
        lastIndex=size;
      }
 else {
        ;
      }
      array=newArray;
    }
  }
  private void growAtFront(  int required){
    int size;
    size=lastIndex - firstIndex;
    int genVar4755;
    genVar4755=array.length - lastIndex;
    int genVar4756;
    genVar4756=genVar4755 + firstIndex;
    boolean genVar4757;
    genVar4757=genVar4756 >= required;
    if (genVar4757) {
      int newFirst;
      newFirst=array.length - size;
      int genVar4758;
      genVar4758=0;
      boolean genVar4759;
      genVar4759=size > genVar4758;
      if (genVar4759) {
        System.arraycopy(array,firstIndex,array,newFirst,size);
        int genVar4760;
        genVar4760=firstIndex + size;
        boolean genVar4761;
        genVar4761=genVar4760 > newFirst;
        int genVar4762;
        genVar4762=firstIndex + size;
        int length;
        length=genVar4761 ? newFirst : genVar4762;
        int genVar4763;
        genVar4763=0;
        Arrays.fill(array,firstIndex,length,genVar4763);
      }
 else {
        ;
      }
      firstIndex=newFirst;
      lastIndex=array.length;
    }
 else {
      int genVar4764;
      genVar4764=2;
      int increment;
      increment=size / genVar4764;
      boolean genVar4765;
      genVar4765=required > increment;
      if (genVar4765) {
        increment=required;
      }
 else {
        ;
      }
      int genVar4766;
      genVar4766=12;
      boolean genVar4767;
      genVar4767=increment < genVar4766;
      if (genVar4767) {
        increment=12;
      }
 else {
        ;
      }
      ArrayListInt genVar4768;
      genVar4768=this;
      int genVar4769;
      genVar4769=size + increment;
      int[] newArray;
      newArray=genVar4768.newElementArray(genVar4769);
      int genVar4770;
      genVar4770=0;
      boolean genVar4771;
      genVar4771=size > genVar4770;
      if (genVar4771) {
        int genVar4772;
        genVar4772=newArray.length - size;
        System.arraycopy(array,firstIndex,newArray,genVar4772,size);
      }
 else {
        ;
      }
      firstIndex=newArray.length - size;
      lastIndex=newArray.length;
      array=newArray;
    }
  }
  private void growForInsert(  int location,  int required){
    int size;
    size=lastIndex - firstIndex;
    int genVar4773;
    genVar4773=2;
    int increment;
    increment=size / genVar4773;
    boolean genVar4774;
    genVar4774=required > increment;
    if (genVar4774) {
      increment=required;
    }
 else {
      ;
    }
    int genVar4775;
    genVar4775=12;
    boolean genVar4776;
    genVar4776=increment < genVar4775;
    if (genVar4776) {
      increment=12;
    }
 else {
      ;
    }
    ArrayListInt genVar4777;
    genVar4777=this;
    int genVar4778;
    genVar4778=size + increment;
    int[] newArray;
    newArray=genVar4777.newElementArray(genVar4778);
    int newFirst;
    newFirst=increment - required;
    int genVar4779;
    genVar4779=location + firstIndex;
    int genVar4780;
    genVar4780=newFirst + location + required;
    int genVar4781;
    genVar4781=size - location;
    System.arraycopy(array,genVar4779,newArray,genVar4780,genVar4781);
    System.arraycopy(array,firstIndex,newArray,newFirst,location);
    firstIndex=newFirst;
    lastIndex=size + increment;
    array=newArray;
  }
  /** 
 * Increments the element at the specified location in this                                    {@code ArrayListInt}with the specified object.
 * @param location the index at which to put the specified object.
 * @return the previous element at the index.
 * @throws IndexOutOfBoundsException when  {@code location < 0 || >= size()}
 */
  public int increment(  int location){
    boolean genVar4782;
    genVar4782=0 <= location && location < (lastIndex - firstIndex);
    if (genVar4782) {
      int genVar4783;
      genVar4783=firstIndex + location;
      int result;
      result=array[genVar4783];
      int genVar4784;
      genVar4784=firstIndex + location;
      int genVar4785;
      genVar4785=array[genVar4784];
      genVar4785++;
      total++;
      return result;
    }
 else {
      ;
    }
    java.lang.IndexOutOfBoundsException genVar4786;
    genVar4786=new IndexOutOfBoundsException();
    throw genVar4786;
  }
  public boolean isEmpty(){
    boolean genVar4787;
    genVar4787=lastIndex == firstIndex;
    return genVar4787;
  }
  /** 
 * Removes the object at the specified location from this list.
 * @param location the index of the object to remove.
 * @return the removed object.
 * @throws IndexOutOfBoundsException when  {@code location < 0 || >= size()}
 */
  public int remove(  int location){
    int result;
    int size;
    size=lastIndex - firstIndex;
    boolean genVar4788;
    genVar4788=0 <= location && location < size;
    if (genVar4788) {
      int genVar4789;
      genVar4789=1;
      int genVar4790;
      genVar4790=size - genVar4789;
      boolean genVar4791;
      genVar4791=location == genVar4790;
      if (genVar4791) {
        int genVar4792;
        genVar4792=--lastIndex;
        result=array[genVar4792];
        array[lastIndex]=0;
      }
 else {
        int genVar4793;
        genVar4793=0;
        boolean genVar4794;
        genVar4794=location == genVar4793;
        if (genVar4794) {
          result=array[firstIndex];
          int genVar4795;
          genVar4795=firstIndex++;
          array[genVar4795]=0;
        }
 else {
          int elementIndex;
          elementIndex=firstIndex + location;
          result=array[elementIndex];
          int genVar4796;
          genVar4796=2;
          int genVar4797;
          genVar4797=size / genVar4796;
          boolean genVar4798;
          genVar4798=location < genVar4797;
          if (genVar4798) {
            int genVar4799;
            genVar4799=1;
            int genVar4800;
            genVar4800=firstIndex + genVar4799;
            System.arraycopy(array,firstIndex,array,genVar4800,location);
            int genVar4801;
            genVar4801=firstIndex++;
            array[genVar4801]=0;
          }
 else {
            int genVar4802;
            genVar4802=1;
            int genVar4803;
            genVar4803=elementIndex + genVar4802;
            int genVar4804;
            genVar4804=1;
            int genVar4805;
            genVar4805=size - location - genVar4804;
            System.arraycopy(array,genVar4803,array,elementIndex,genVar4805);
            int genVar4806;
            genVar4806=--lastIndex;
            array[genVar4806]=0;
          }
        }
      }
      boolean genVar4807;
      genVar4807=firstIndex == lastIndex;
      if (genVar4807) {
        firstIndex=lastIndex=0;
      }
 else {
        ;
      }
    }
 else {
      java.lang.IndexOutOfBoundsException genVar4808;
      genVar4808=new IndexOutOfBoundsException();
      throw genVar4808;
    }
    total-=result;
    return result;
  }
  /** 
 * Replaces the element at the specified location in this                                    {@code ArrayListInt}with the specified object.
 * @param location the index at which to put the specified object.
 * @param object the object to add.
 * @return the previous element at the index.
 * @throws IndexOutOfBoundsException when  {@code location < 0 || >= size()}
 */
  public int set(  int location,  int object){
    boolean genVar4809;
    genVar4809=0 <= location && location < (lastIndex - firstIndex);
    if (genVar4809) {
      int genVar4810;
      genVar4810=firstIndex + location;
      int result;
      result=array[genVar4810];
      int genVar4811;
      genVar4811=firstIndex + location;
      array[genVar4811]=object;
      total+=object - result;
      return result;
    }
 else {
      ;
    }
    java.lang.IndexOutOfBoundsException genVar4812;
    genVar4812=new IndexOutOfBoundsException();
    throw genVar4812;
  }
  /** 
 * Returns the number of elements in this                                    {@code ArrayListInt}.
 * @return the number of elements in this {@code ArrayListInt}.
 */
  public int size(){
    int genVar4813;
    genVar4813=lastIndex - firstIndex;
    return genVar4813;
  }
  /** 
 * Returns a new array containing all elements contained in this                                   {@code ArrayListInt}.
 * @return an array of the elements from this {@code ArrayListInt}
 */
  public Object[] toArray(){
    int size;
    size=lastIndex - firstIndex;
    Object[] result;
    result=new Object[size];
    int genVar4814;
    genVar4814=0;
    System.arraycopy(array,firstIndex,result,genVar4814,size);
    return result;
  }
  /** 
 * Returns a the total of all elements contained in this                                   {@code ArrayListInt}.
 * @return the total of the elements from this {@code ArrayListInt}
 */
  public int total(){
    return total;
  }
  /** 
 * Sets the capacity of this                                    {@code ArrayListInt} to be the same as the currentsize.
 * @see #size
 */
  public void trimToSize(){
    int size;
    size=lastIndex - firstIndex;
    ArrayListInt genVar4815;
    genVar4815=this;
    int[] newArray;
    newArray=genVar4815.newElementArray(size);
    int genVar4816;
    genVar4816=0;
    System.arraycopy(array,firstIndex,newArray,genVar4816,size);
    array=newArray;
    firstIndex=0;
    lastIndex=array.length;
  }
  private int[] newElementArray(  int size){
    int[] a;
    a=new int[size];
    int genVar4817;
    genVar4817=0;
    int genVar4818;
    genVar4818=0;
    Arrays.fill(a,genVar4817,a.length,genVar4818);
    return a;
  }
}
