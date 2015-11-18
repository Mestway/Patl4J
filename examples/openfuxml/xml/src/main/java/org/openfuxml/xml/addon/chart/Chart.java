
package org.openfuxml.xml.addon.chart;

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
 *         &lt;element ref="{http://www.openfuxml.org/chart}renderer"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}axis" maxOccurs="2"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}title"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}dimension"/>
 *         &lt;element name="colors">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.openfuxml.org/chart}color" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.openfuxml.org/chart}grid"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}dataSet" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "renderer",
    "axis",
    "title",
    "dimension",
    "colors",
    "grid",
    "dataSet"
})
@XmlRootElement(name = "chart")
public class Chart
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Renderer renderer;
    @XmlElement(required = true)
    protected List<Axis> axis;
    @XmlElement(required = true)
    protected Title title;
    @XmlElement(required = true)
    protected Dimension dimension;
    @XmlElement(namespace = "", required = true)
    protected Chart.Colors colors;
    @XmlElement(required = true)
    protected Grid grid;
    @XmlElement(required = true)
    protected List<DataSet> dataSet;
    @XmlAttribute(name = "id")
    protected Integer id;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "legend")
    protected Boolean legend;

    /**
     * Gets the value of the renderer property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer }
     *     
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the value of the renderer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer }
     *     
     */
    public void setRenderer(Renderer value) {
        this.renderer = value;
    }

    public boolean isSetRenderer() {
        return (this.renderer!= null);
    }

    /**
     * Gets the value of the axis property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the axis property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAxis().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Axis }
     * 
     * 
     */
    public List<Axis> getAxis() {
        if (axis == null) {
            axis = new ArrayList<Axis>();
        }
        return this.axis;
    }

    public boolean isSetAxis() {
        return ((this.axis!= null)&&(!this.axis.isEmpty()));
    }

    public void unsetAxis() {
        this.axis = null;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return (this.title!= null);
    }

    /**
     * Gets the value of the dimension property.
     * 
     * @return
     *     possible object is
     *     {@link Dimension }
     *     
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Sets the value of the dimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimension }
     *     
     */
    public void setDimension(Dimension value) {
        this.dimension = value;
    }

    public boolean isSetDimension() {
        return (this.dimension!= null);
    }

    /**
     * Gets the value of the colors property.
     * 
     * @return
     *     possible object is
     *     {@link Chart.Colors }
     *     
     */
    public Chart.Colors getColors() {
        return colors;
    }

    /**
     * Sets the value of the colors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Chart.Colors }
     *     
     */
    public void setColors(Chart.Colors value) {
        this.colors = value;
    }

    public boolean isSetColors() {
        return (this.colors!= null);
    }

    /**
     * Gets the value of the grid property.
     * 
     * @return
     *     possible object is
     *     {@link Grid }
     *     
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Sets the value of the grid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Grid }
     *     
     */
    public void setGrid(Grid value) {
        this.grid = value;
    }

    public boolean isSetGrid() {
        return (this.grid!= null);
    }

    /**
     * Gets the value of the dataSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataSet }
     * 
     * 
     */
    public List<DataSet> getDataSet() {
        if (dataSet == null) {
            dataSet = new ArrayList<DataSet>();
        }
        return this.dataSet;
    }

    public boolean isSetDataSet() {
        return ((this.dataSet!= null)&&(!this.dataSet.isEmpty()));
    }

    public void unsetDataSet() {
        this.dataSet = null;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(int value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    public boolean isSetCode() {
        return (this.code!= null);
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
     *         &lt;element ref="{http://www.openfuxml.org/chart}color" maxOccurs="unbounded"/>
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

}
