
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
 *         &lt;element name="objekttitel">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.openfuxml.org/fuxml}absatz-ohne" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="gleiten" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "grafik",
    "objekttitel"
})
@XmlRootElement(name = "medienobjekt")
public class Medienobjekt
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Grafik> grafik;
    @XmlElement(namespace = "", required = true)
    protected Medienobjekt.Objekttitel objekttitel;
    @XmlAttribute(name = "gleiten")
    protected String gleiten;
    @XmlAttribute(name = "id")
    protected String id;

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
     * Gets the value of the objekttitel property.
     * 
     * @return
     *     possible object is
     *     {@link Medienobjekt.Objekttitel }
     *     
     */
    public Medienobjekt.Objekttitel getObjekttitel() {
        return objekttitel;
    }

    /**
     * Sets the value of the objekttitel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Medienobjekt.Objekttitel }
     *     
     */
    public void setObjekttitel(Medienobjekt.Objekttitel value) {
        this.objekttitel = value;
    }

    public boolean isSetObjekttitel() {
        return (this.objekttitel!= null);
    }

    /**
     * Gets the value of the gleiten property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGleiten() {
        return gleiten;
    }

    /**
     * Sets the value of the gleiten property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGleiten(String value) {
        this.gleiten = value;
    }

    public boolean isSetGleiten() {
        return (this.gleiten!= null);
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.openfuxml.org/fuxml}absatz-ohne" maxOccurs="unbounded"/>
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
        "absatzOhne"
    })
    public static class Objekttitel
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(name = "absatz-ohne", required = true)
        protected List<AbsatzOhne> absatzOhne;

        /**
         * Gets the value of the absatzOhne property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the absatzOhne property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAbsatzOhne().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AbsatzOhne }
         * 
         * 
         */
        public List<AbsatzOhne> getAbsatzOhne() {
            if (absatzOhne == null) {
                absatzOhne = new ArrayList<AbsatzOhne>();
            }
            return this.absatzOhne;
        }

        public boolean isSetAbsatzOhne() {
            return ((this.absatzOhne!= null)&&(!this.absatzOhne.isEmpty()));
        }

        public void unsetAbsatzOhne() {
            this.absatzOhne = null;
        }

    }

}
