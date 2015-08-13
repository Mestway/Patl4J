
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
 *         &lt;element ref="{http://www.openfuxml.org/wiki}wikiinjection" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}template" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="external" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wikiinjection",
    "template"
})
@XmlRootElement(name = "injections")
public class Injections
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Wikiinjection> wikiinjection;
    @XmlElement(required = true)
    protected List<Template> template;
    @XmlAttribute(name = "external")
    protected Boolean external;
    @XmlAttribute(name = "source")
    protected String source;

    /**
     * Gets the value of the wikiinjection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wikiinjection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWikiinjection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wikiinjection }
     * 
     * 
     */
    public List<Wikiinjection> getWikiinjection() {
        if (wikiinjection == null) {
            wikiinjection = new ArrayList<Wikiinjection>();
        }
        return this.wikiinjection;
    }

    public boolean isSetWikiinjection() {
        return ((this.wikiinjection!= null)&&(!this.wikiinjection.isEmpty()));
    }

    public void unsetWikiinjection() {
        this.wikiinjection = null;
    }

    /**
     * Gets the value of the template property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the template property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Template }
     * 
     * 
     */
    public List<Template> getTemplate() {
        if (template == null) {
            template = new ArrayList<Template>();
        }
        return this.template;
    }

    public boolean isSetTemplate() {
        return ((this.template!= null)&&(!this.template.isEmpty()));
    }

    public void unsetTemplate() {
        this.template = null;
    }

    /**
     * Gets the value of the external property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExternal() {
        return external;
    }

    /**
     * Sets the value of the external property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternal(boolean value) {
        this.external = value;
    }

    public boolean isSetExternal() {
        return (this.external!= null);
    }

    public void unsetExternal() {
        this.external = null;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    public boolean isSetSource() {
        return (this.source!= null);
    }

}
