
package org.openfuxml.xml.addon.chart;

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
 *       &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ratio" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "dimension")
public class Dimension
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "height")
    protected Integer height;
    @XmlAttribute(name = "width")
    protected Integer width;
    @XmlAttribute(name = "ratio")
    protected Double ratio;

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeight(int value) {
        this.height = value;
    }

    public boolean isSetHeight() {
        return (this.height!= null);
    }

    public void unsetHeight() {
        this.height = null;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(int value) {
        this.width = value;
    }

    public boolean isSetWidth() {
        return (this.width!= null);
    }

    public void unsetWidth() {
        this.width = null;
    }

    /**
     * Gets the value of the ratio property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * Sets the value of the ratio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatio(double value) {
        this.ratio = value;
    }

    public boolean isSetRatio() {
        return (this.ratio!= null);
    }

    public void unsetRatio() {
        this.ratio = null;
    }

}
