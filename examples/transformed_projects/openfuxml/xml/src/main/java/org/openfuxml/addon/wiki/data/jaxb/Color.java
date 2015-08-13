
package org.openfuxml.addon.wiki.data.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="typ" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="r" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="g" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="a" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "color")
public class Color
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "typ")
    protected String typ;
    @XmlAttribute(name = "r")
    protected Integer r;
    @XmlAttribute(name = "g")
    protected Integer g;
    @XmlAttribute(name = "b")
    protected Integer b;
    @XmlAttribute(name = "a")
    protected Integer a;

    /**
     * Gets the value of the typ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyp() {
        return typ;
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyp(String value) {
        this.typ = value;
    }

    public boolean isSetTyp() {
        return (this.typ!= null);
    }

    /**
     * Gets the value of the r property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getR() {
        return r;
    }

    /**
     * Sets the value of the r property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setR(int value) {
        this.r = value;
    }

    public boolean isSetR() {
        return (this.r!= null);
    }

    public void unsetR() {
        this.r = null;
    }

    /**
     * Gets the value of the g property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setG(int value) {
        this.g = value;
    }

    public boolean isSetG() {
        return (this.g!= null);
    }

    public void unsetG() {
        this.g = null;
    }

    /**
     * Gets the value of the b property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setB(int value) {
        this.b = value;
    }

    public boolean isSetB() {
        return (this.b!= null);
    }

    public void unsetB() {
        this.b = null;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setA(int value) {
        this.a = value;
    }

    public boolean isSetA() {
        return (this.a!= null);
    }

    public void unsetA() {
        this.a = null;
    }

}
