
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
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="ofximage" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="wikilink" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "ofximage"
})
@XmlRootElement(name = "ofxgallery")
public class Ofxgallery
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "", required = true)
    protected List<Ofxgallery.Ofximage> ofximage;

    /**
     * Gets the value of the ofximage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ofximage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOfximage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ofxgallery.Ofximage }
     * 
     * 
     */
    public List<Ofxgallery.Ofximage> getOfximage() {
        if (ofximage == null) {
            ofximage = new ArrayList<Ofxgallery.Ofximage>();
        }
        return this.ofximage;
    }

    public boolean isSetOfximage() {
        return ((this.ofximage!= null)&&(!this.ofximage.isEmpty()));
    }

    public void unsetOfximage() {
        this.ofximage = null;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="wikilink" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Ofximage
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "wikilink")
        protected String wikilink;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSetValue() {
            return (this.value!= null);
        }

        /**
         * Gets the value of the wikilink property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWikilink() {
            return wikilink;
        }

        /**
         * Sets the value of the wikilink property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWikilink(String value) {
            this.wikilink = value;
        }

        public boolean isSetWikilink() {
            return (this.wikilink!= null);
        }

    }

}
