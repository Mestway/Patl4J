package org.openfuxml.producer.preprocessors;

//import java.util.*;

public class ConversionTableEntry implements Comparable {
    private String  from, to;

    public ConversionTableEntry(String from, String to) {
        if (from==null || to==null)
            throw new NullPointerException();
        this.from = from;
        this.to = to;
    }
    public String from()    {return from;}

    public String to()     {return to;}

    public boolean equals(Object o) {
        if (!(o instanceof ConversionTableEntry))
            return false;
        ConversionTableEntry n = (ConversionTableEntry)o;
        return n.from.equals(from) && n.to.equals(to);
    }

    public int hashCode() {
        return from.hashCode();
    }

    public String toString() {return from + " " + to;}

    public int compareTo(Object o) {
        ConversionTableEntry n = (ConversionTableEntry)o;
        int cmp = 1;

        if (n.from().length() > from.length())
          cmp=1;
        else if (n.from().length() < from.length())
          cmp=-1;
        else if ((n.from().length() == from.length()) && n.equals(this))
          cmp=0;

        return cmp;
    }
}
