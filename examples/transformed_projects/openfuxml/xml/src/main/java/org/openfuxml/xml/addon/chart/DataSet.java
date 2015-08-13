
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
 *         &lt;element ref="{http://www.openfuxml.org/chart}data" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}dataSet" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rangeIndex" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="colorIndex" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "data",
    "dataSet"
})
@XmlRootElement(name = "dataSet")
public class DataSet
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Data> data;
    @XmlElement(required = true)
    protected List<DataSet> dataSet;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "rangeIndex")
    protected Integer rangeIndex;
    @XmlAttribute(name = "colorIndex")
    protected Integer colorIndex;

    /**
     * Gets the value of the data property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the data property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Data }
     * 
     * 
     */
    public List<Data> getData() {
        if (data == null) {
            data = new ArrayList<Data>();
        }
        return this.data;
    }

    public boolean isSetData() {
        return ((this.data!= null)&&(!this.data.isEmpty()));
    }

    public void unsetData() {
        this.data = null;
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
     * Gets the value of the rangeIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRangeIndex() {
        return rangeIndex;
    }

    /**
     * Sets the value of the rangeIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRangeIndex(int value) {
        this.rangeIndex = value;
    }

    public boolean isSetRangeIndex() {
        return (this.rangeIndex!= null);
    }

    public void unsetRangeIndex() {
        this.rangeIndex = null;
    }

    /**
     * Gets the value of the colorIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getColorIndex() {
        return colorIndex;
    }

    /**
     * Sets the value of the colorIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setColorIndex(int value) {
        this.colorIndex = value;
    }

    public boolean isSetColorIndex() {
        return (this.colorIndex!= null);
    }

    public void unsetColorIndex() {
        this.colorIndex = null;
    }

}
