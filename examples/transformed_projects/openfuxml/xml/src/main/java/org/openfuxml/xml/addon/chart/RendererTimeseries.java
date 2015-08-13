
package org.openfuxml.xml.addon.chart;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="gap" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="from" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="to" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="cumulate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="timePeriod" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orderRecords" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="sumDate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "rendererTimeseries")
public class RendererTimeseries
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "gap")
    protected Boolean gap;
    @XmlAttribute(name = "from")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar from;
    @XmlAttribute(name = "to")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar to;
    @XmlAttribute(name = "cumulate")
    protected Boolean cumulate;
    @XmlAttribute(name = "timePeriod")
    protected String timePeriod;
    @XmlAttribute(name = "orderRecords")
    protected Boolean orderRecords;
    @XmlAttribute(name = "sumDate")
    protected Boolean sumDate;

    /**
     * Gets the value of the gap property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isGap() {
        return gap;
    }

    /**
     * Sets the value of the gap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGap(boolean value) {
        this.gap = value;
    }

    public boolean isSetGap() {
        return (this.gap!= null);
    }

    public void unsetGap() {
        this.gap = null;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    public boolean isSetFrom() {
        return (this.from!= null);
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTo(XMLGregorianCalendar value) {
        this.to = value;
    }

    public boolean isSetTo() {
        return (this.to!= null);
    }

    /**
     * Gets the value of the cumulate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCumulate() {
        return cumulate;
    }

    /**
     * Sets the value of the cumulate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCumulate(boolean value) {
        this.cumulate = value;
    }

    public boolean isSetCumulate() {
        return (this.cumulate!= null);
    }

    public void unsetCumulate() {
        this.cumulate = null;
    }

    /**
     * Gets the value of the timePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimePeriod() {
        return timePeriod;
    }

    /**
     * Sets the value of the timePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimePeriod(String value) {
        this.timePeriod = value;
    }

    public boolean isSetTimePeriod() {
        return (this.timePeriod!= null);
    }

    /**
     * Gets the value of the orderRecords property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOrderRecords() {
        return orderRecords;
    }

    /**
     * Sets the value of the orderRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrderRecords(boolean value) {
        this.orderRecords = value;
    }

    public boolean isSetOrderRecords() {
        return (this.orderRecords!= null);
    }

    public void unsetOrderRecords() {
        this.orderRecords = null;
    }

    /**
     * Gets the value of the sumDate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSumDate() {
        return sumDate;
    }

    /**
     * Sets the value of the sumDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSumDate(boolean value) {
        this.sumDate = value;
    }

    public boolean isSetSumDate() {
        return (this.sumDate!= null);
    }

    public void unsetSumDate() {
        this.sumDate = null;
    }

}
