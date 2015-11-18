
package org.openfuxml.xml.addon.chart;

import java.io.Serializable;
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
 *         &lt;element ref="{http://www.openfuxml.org/chart}axisType"/>
 *         &lt;element ref="{http://www.openfuxml.org/chart}label"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="autoRangIncludeNull" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "axisType",
    "label"
})
@XmlRootElement(name = "axis")
public class Axis
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected AxisType axisType;
    @XmlElement(required = true)
    protected Label label;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "dateformat")
    protected String dateformat;
    @XmlAttribute(name = "autoRangIncludeNull")
    protected Boolean autoRangIncludeNull;

    /**
     * Gets the value of the axisType property.
     * 
     * @return
     *     possible object is
     *     {@link AxisType }
     *     
     */
    public AxisType getAxisType() {
        return axisType;
    }

    /**
     * Sets the value of the axisType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AxisType }
     *     
     */
    public void setAxisType(AxisType value) {
        this.axisType = value;
    }

    public boolean isSetAxisType() {
        return (this.axisType!= null);
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabel(Label value) {
        this.label = value;
    }

    public boolean isSetLabel() {
        return (this.label!= null);
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

    /**
     * Gets the value of the autoRangIncludeNull property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAutoRangIncludeNull() {
        return autoRangIncludeNull;
    }

    /**
     * Sets the value of the autoRangIncludeNull property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoRangIncludeNull(boolean value) {
        this.autoRangIncludeNull = value;
    }

    public boolean isSetAutoRangIncludeNull() {
        return (this.autoRangIncludeNull!= null);
    }

    public void unsetAutoRangIncludeNull() {
        this.autoRangIncludeNull = null;
    }

}
