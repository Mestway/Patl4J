
package org.openfuxml.addon.wiki.data.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchartdata" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchartcontainer" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ofxchartdata",
    "ofxchartcontainer"
})
@XmlRootElement(name = "ofxchartcontainer")
public class Ofxchartcontainer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Ofxchartdata> ofxchartdata;
    @XmlElement(required = true)
    protected List<Ofxchartcontainer> ofxchartcontainer;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Gets the value of the ofxchartdata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxchartdata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxchartdata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxchartdata }
     * 
     * 
     */
    public List<Ofxchartdata> getOfxchartdata() {
        if (ofxchartdata == null) {
            ofxchartdata = new ArrayList<Ofxchartdata>();
        }
        return this.ofxchartdata;
    }

    public boolean isSetOfxchartdata() {
        return ((this.ofxchartdata!= null)&&(!this.ofxchartdata.isEmpty()));
    }

    public void unsetOfxchartdata() {
        this.ofxchartdata = null;
    }

    /**
     * Gets the value of the ofxchartcontainer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofxchartcontainer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfxchartcontainer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxchartcontainer }
     * 
     * 
     */
    public List<Ofxchartcontainer> getOfxchartcontainer() {
        if (ofxchartcontainer == null) {
            ofxchartcontainer = new ArrayList<Ofxchartcontainer>();
        }
        return this.ofxchartcontainer;
    }

    public boolean isSetOfxchartcontainer() {
        return ((this.ofxchartcontainer!= null)&&(!this.ofxchartcontainer.isEmpty()));
    }

    public void unsetOfxchartcontainer() {
        this.ofxchartcontainer = null;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

}
