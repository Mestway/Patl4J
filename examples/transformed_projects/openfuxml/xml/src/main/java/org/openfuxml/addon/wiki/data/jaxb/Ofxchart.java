
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
 *         &lt;element name="title">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="x-axis">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="y-axis">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="colors">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.openfuxml.org/wiki}color" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="size">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="grid">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="range" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}ofxchartcontainer" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="legend" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "xAxis",
    "yAxis",
    "colors",
    "size",
    "grid",
    "ofxchartcontainer"
})
@XmlRootElement(name = "ofxchart")
public class Ofxchart
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Title title;
    @XmlElement(name = "x-axis", namespace = "", required = true)
    protected Ofxchart.XAxis xAxis;
    @XmlElement(name = "y-axis", namespace = "", required = true)
    protected Ofxchart.YAxis yAxis;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Colors colors;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Size size;
    @XmlElement(namespace = "", required = true)
    protected Ofxchart.Grid grid;
    @XmlElement(required = true)
    protected List<Ofxchartcontainer> ofxchartcontainer;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "legend")
    protected Boolean legend;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Title }
     *     
     */
    public Ofxchart.Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Title }
     *     
     */
    public void setTitle(Ofxchart.Title value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return (this.title!= null);
    }

    /**
     * Gets the value of the xAxis property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.XAxis }
     *     
     */
    public Ofxchart.XAxis getXAxis() {
        return xAxis;
    }

    /**
     * Sets the value of the xAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.XAxis }
     *     
     */
    public void setXAxis(Ofxchart.XAxis value) {
        this.xAxis = value;
    }

    public boolean isSetXAxis() {
        return (this.xAxis!= null);
    }

    /**
     * Gets the value of the yAxis property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.YAxis }
     *     
     */
    public Ofxchart.YAxis getYAxis() {
        return yAxis;
    }

    /**
     * Sets the value of the yAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.YAxis }
     *     
     */
    public void setYAxis(Ofxchart.YAxis value) {
        this.yAxis = value;
    }

    public boolean isSetYAxis() {
        return (this.yAxis!= null);
    }

    /**
     * Gets the value of the colors property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Colors }
     *     
     */
    public Ofxchart.Colors getColors() {
        return colors;
    }

    /**
     * Sets the value of the colors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Colors }
     *     
     */
    public void setColors(Ofxchart.Colors value) {
        this.colors = value;
    }

    public boolean isSetColors() {
        return (this.colors!= null);
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Size }
     *     
     */
    public Ofxchart.Size getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Size }
     *     
     */
    public void setSize(Ofxchart.Size value) {
        this.size = value;
    }

    public boolean isSetSize() {
        return (this.size!= null);
    }

    /**
     * Gets the value of the grid property.
     * 
     * @return
     *     possible object is
     *     {@link Ofxchart.Grid }
     *     
     */
    public Ofxchart.Grid getGrid() {
        return grid;
    }

    /**
     * Sets the value of the grid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ofxchart.Grid }
     *     
     */
    public void setGrid(Ofxchart.Grid value) {
        this.grid = value;
    }

    public boolean isSetGrid() {
        return (this.grid!= null);
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

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    /**
     * Gets the value of the legend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLegend() {
        return legend;
    }

    /**
     * Sets the value of the legend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLegend(boolean value) {
        this.legend = value;
    }

    public boolean isSetLegend() {
        return (this.legend!= null);
    }

    public void unsetLegend() {
        this.legend = null;
    }


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
     *         &lt;element ref="{http://www.openfuxml.org/wiki}color" maxOccurs="unbounded"/>
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
        "color"
    })
    public static class Colors
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Color> color;

        /**
         * Gets the value of the color property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the color property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getColor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Color }
         * 
         * 
         */
        public List<Color> getColor() {
            if (color == null) {
                color = new ArrayList<Color>();
            }
            return this.color;
        }

        public boolean isSetColor() {
            return ((this.color!= null)&&(!this.color.isEmpty()));
        }

        public void unsetColor() {
            this.color = null;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="range" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="domain" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Grid
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "range")
        protected Boolean range;
        @XmlAttribute(name = "domain")
        protected Boolean domain;

        /**
         * Gets the value of the range property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isRange() {
            return range;
        }

        /**
         * Sets the value of the range property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRange(boolean value) {
            this.range = value;
        }

        public boolean isSetRange() {
            return (this.range!= null);
        }

        public void unsetRange() {
            this.range = null;
        }

        /**
         * Gets the value of the domain property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isDomain() {
            return domain;
        }

        /**
         * Sets the value of the domain property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDomain(boolean value) {
            this.domain = value;
        }

        public boolean isSetDomain() {
            return (this.domain!= null);
        }

        public void unsetDomain() {
            this.domain = null;
        }

    }


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
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Size
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "height")
        protected Integer height;
        @XmlAttribute(name = "width")
        protected Integer width;

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

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Title
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        public boolean isSetLabel() {
            return (this.label!= null);
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

        public boolean isSetKey() {
            return (this.key!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class XAxis
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;
        @XmlAttribute(name = "dateformat")
        protected String dateformat;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        public boolean isSetLabel() {
            return (this.label!= null);
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

        public boolean isSetKey() {
            return (this.key!= null);
        }

        /**
         * Gets the value of the dateformat property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateformat() {
            return dateformat;
        }

        /**
         * Sets the value of the dateformat property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateformat(String value) {
            this.dateformat = value;
        }

        public boolean isSetDateformat() {
            return (this.dateformat!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class YAxis
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "label")
        protected String label;
        @XmlAttribute(name = "key")
        protected String key;

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLabel(String value) {
            this.label = value;
        }

        public boolean isSetLabel() {
            return (this.label!= null);
        }

        /**
         * Gets the value of the key property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the value of the key property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKey(String value) {
            this.key = value;
        }

        public boolean isSetKey() {
            return (this.key!= null);
        }

    }

}
