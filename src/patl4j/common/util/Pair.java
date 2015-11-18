package patl4j.common.util;

public class Pair<A, B> {
    private A first;
    private B second;
    // a special flag to indicate whether the pair is valid
    private boolean flag;

    public Pair(A first, B second) {
    	this.first = first;
    	this.second = second;
    	flag = true;
    }
    
    public void setFlag(boolean flag) {
    	this.flag = flag;
    }
    
    public boolean getFlag() {
    	return flag;
    }    

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @SuppressWarnings("rawtypes")
	public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair otherPair = (Pair) other;
    		return 
    		((  this.first == otherPair.first ||
    			( this.first != null && otherPair.first != null &&
    			  this.first.equals(otherPair.first))) &&
    		 (	this.second == otherPair.second ||
    			( this.second != null && otherPair.second != null &&
    			  this.second.equals(otherPair.second))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public A getFirst() {
    	return first;
    }

    public void setFirst(A first) {
    	this.first = first;
    }

    public B getSecond() {
    	return second;
    }

    public void setSecond(B second) {
    	this.second = second;
    }
}