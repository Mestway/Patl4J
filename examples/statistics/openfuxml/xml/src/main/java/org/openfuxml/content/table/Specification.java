
package org.openfuxml.content.table;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.layout.Float;
import org.openfuxml.content.layout.Width;


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
 *         &lt;element ref="{http://www.openfuxml.org/table}columns" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}alignment" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}width" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}float" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="long" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "columns",
    "alignment",
    "width",
    "_float"
})
@XmlRootElement(name = "specification")
public class Specification
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Columns columns;
    @XmlElement(namespace = "http://www.openfuxml.org/layout")
    protected Alignment alignment;
    @XmlElement(namespace = "http://www.openfuxml.org/layout")
    protected Width width;
    @XmlElement(name = "float", namespace = "http://www.openfuxml.org/layout")
    protected Float _float;
    @XmlAttribute(name = "long")
    protected Boolean _long;

    /**
     * Gets the value of the columns property.
     * 
     * @return
     *     possible object is
     *     {@link Columns }
     *     
     */
    public Columns getColumns() {
        return columns;
    }

    /**
     * Sets the value of the columns property.
     * 
     * @param value
     *     allowed object is
     *     {@link Columns }
     *     
     */
    public void setColumns(Columns value) {
        this.columns = value;
    }

    public boolean isSetColumns() {
        return (this.columns!= null);
    }

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link Alignment }
     *     
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alignment }
     *     
     */
    public void setAlignment(Alignment value) {
        this.alignment = value;
    }

    public boolean isSetAlignment() {
        return (this.alignment!= null);
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Width }
     *     
     */
    public Width getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Width }
     *     
     */
    public void setWidth(Width value) {
        this.width = value;
    }

    public boolean isSetWidth() {
        return (this.width!= null);
    }

    /**
     * Gets the value of the float property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFloat() {
        return _float;
    }

    /**
     * Sets the value of the float property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFloat(Float value) {
        this._float = value;
    }

    public boolean isSetFloat() {
        return (this._float!= null);
    }

    /**
     * Gets the value of the long property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLong() {
        return _long;
    }

    /**
     * Sets the value of the long property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLong(boolean value) {
        this._long = value;
    }

    public boolean isSetLong() {
        return (this._long!= null);
    }

    public void unsetLong() {
        this._long = null;
    }

}
