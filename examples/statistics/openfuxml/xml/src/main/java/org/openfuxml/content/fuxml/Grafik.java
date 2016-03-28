
package org.openfuxml.content.fuxml;

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
 *         &lt;element ref="{http://www.openfuxml.org/fuxml}grafik" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="align" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fileref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fliessen" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="depth" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "grafik"
})
@XmlRootElement(name = "grafik")
public class Grafik
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Grafik> grafik;
    @XmlAttribute(name = "align")
    protected String align;
    @XmlAttribute(name = "fileref")
    protected String fileref;
    @XmlAttribute(name = "fliessen")
    protected String fliessen;
    @XmlAttribute(name = "width")
    protected Integer width;
    @XmlAttribute(name = "depth")
    protected Integer depth;
    @XmlAttribute(name = "scale")
    protected Double scale;

    /**
     * Gets the value of the grafik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grafik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrafik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Grafik }
     * 
     * 
     */
    public List<Grafik> getGrafik() {
        if (grafik == null) {
            grafik = new ArrayList<Grafik>();
        }
        return this.grafik;
    }

    public boolean isSetGrafik() {
        return ((this.grafik!= null)&&(!this.grafik.isEmpty()));
    }

    public void unsetGrafik() {
        this.grafik = null;
    }

    /**
     * Gets the value of the align property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the value of the align property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlign(String value) {
        this.align = value;
    }

    public boolean isSetAlign() {
        return (this.align!= null);
    }

    /**
     * Gets the value of the fileref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileref() {
        return fileref;
    }

    /**
     * Sets the value of the fileref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileref(String value) {
        this.fileref = value;
    }

    public boolean isSetFileref() {
        return (this.fileref!= null);
    }

    /**
     * Gets the value of the fliessen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFliessen() {
        return fliessen;
    }

    /**
     * Sets the value of the fliessen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFliessen(String value) {
        this.fliessen = value;
    }

    public boolean isSetFliessen() {
        return (this.fliessen!= null);
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
     * Gets the value of the depth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepth(int value) {
        this.depth = value;
    }

    public boolean isSetDepth() {
        return (this.depth!= null);
    }

    public void unsetDepth() {
        this.depth = null;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setScale(double value) {
        this.scale = value;
    }

    public boolean isSetScale() {
        return (this.scale!= null);
    }

    public void unsetScale() {
        this.scale = null;
    }

}
