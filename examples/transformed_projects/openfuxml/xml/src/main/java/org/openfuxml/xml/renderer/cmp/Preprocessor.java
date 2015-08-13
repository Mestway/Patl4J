
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.exlp.xml.io.Dir;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://exlp.sf.net/io}dir" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/cmp}merge" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/cmp}wiki"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dir",
    "merge",
    "wiki"
})
@XmlRootElement(name = "preprocessor")
public class Preprocessor
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://exlp.sf.net/io", required = true)
    protected List<Dir> dir;
    @XmlElement(required = true)
    protected List<Merge> merge;
    @XmlElement(required = true)
    protected Wiki wiki;

    /**
     * Gets the value of the dir property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dir property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dir }
     * 
     * 
     */
    public List<Dir> getDir() {
        if (dir == null) {
            dir = new ArrayList<Dir>();
        }
        return this.dir;
    }

    public boolean isSetDir() {
        return ((this.dir!= null)&&(!this.dir.isEmpty()));
    }

    public void unsetDir() {
        this.dir = null;
    }

    /**
     * Gets the value of the merge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the merge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMerge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Merge }
     * 
     * 
     */
    public List<Merge> getMerge() {
        if (merge == null) {
            merge = new ArrayList<Merge>();
        }
        return this.merge;
    }

    public boolean isSetMerge() {
        return ((this.merge!= null)&&(!this.merge.isEmpty()));
    }

    public void unsetMerge() {
        this.merge = null;
    }

    /**
     * Gets the value of the wiki property.
     * 
     * @return
     *     possible object is
     *     {@link Wiki }
     *     
     */
    public Wiki getWiki() {
        return wiki;
    }

    /**
     * Sets the value of the wiki property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wiki }
     *     
     */
    public void setWiki(Wiki value) {
        this.wiki = value;
    }

    public boolean isSetWiki() {
        return (this.wiki!= null);
    }

}
