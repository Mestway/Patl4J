
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Servers;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;


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
 *         &lt;element ref="{http://www.openfuxml.org/wiki}markupProcessor"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}xhtmlProcessor"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}templates"/>
 *         &lt;element ref="{http://www.openfuxml.org/wiki}servers"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fetchArticle" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "markupProcessor",
    "xhtmlProcessor",
    "templates",
    "servers"
})
@XmlRootElement(name = "wiki")
public class Wiki
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://www.openfuxml.org/wiki", required = true)
    protected MarkupProcessor markupProcessor;
    @XmlElement(namespace = "http://www.openfuxml.org/wiki", required = true)
    protected XhtmlProcessor xhtmlProcessor;
    @XmlElement(namespace = "http://www.openfuxml.org/wiki", required = true)
    protected Templates templates;
    @XmlElement(namespace = "http://www.openfuxml.org/wiki", required = true)
    protected Servers servers;
    @XmlAttribute(name = "fetchArticle")
    protected Boolean fetchArticle;

    /**
     * Gets the value of the markupProcessor property.
     * 
     * @return
     *     possible object is
     *     {@link MarkupProcessor }
     *     
     */
    public MarkupProcessor getMarkupProcessor() {
        return markupProcessor;
    }

    /**
     * Sets the value of the markupProcessor property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkupProcessor }
     *     
     */
    public void setMarkupProcessor(MarkupProcessor value) {
        this.markupProcessor = value;
    }

    public boolean isSetMarkupProcessor() {
        return (this.markupProcessor!= null);
    }

    /**
     * Gets the value of the xhtmlProcessor property.
     * 
     * @return
     *     possible object is
     *     {@link XhtmlProcessor }
     *     
     */
    public XhtmlProcessor getXhtmlProcessor() {
        return xhtmlProcessor;
    }

    /**
     * Sets the value of the xhtmlProcessor property.
     * 
     * @param value
     *     allowed object is
     *     {@link XhtmlProcessor }
     *     
     */
    public void setXhtmlProcessor(XhtmlProcessor value) {
        this.xhtmlProcessor = value;
    }

    public boolean isSetXhtmlProcessor() {
        return (this.xhtmlProcessor!= null);
    }

    /**
     * Gets the value of the templates property.
     * 
     * @return
     *     possible object is
     *     {@link Templates }
     *     
     */
    public Templates getTemplates() {
        return templates;
    }

    /**
     * Sets the value of the templates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Templates }
     *     
     */
    public void setTemplates(Templates value) {
        this.templates = value;
    }

    public boolean isSetTemplates() {
        return (this.templates!= null);
    }

    /**
     * Gets the value of the servers property.
     * 
     * @return
     *     possible object is
     *     {@link Servers }
     *     
     */
    public Servers getServers() {
        return servers;
    }

    /**
     * Sets the value of the servers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Servers }
     *     
     */
    public void setServers(Servers value) {
        this.servers = value;
    }

    public boolean isSetServers() {
        return (this.servers!= null);
    }

    /**
     * Gets the value of the fetchArticle property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isFetchArticle() {
        return fetchArticle;
    }

    /**
     * Sets the value of the fetchArticle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFetchArticle(boolean value) {
        this.fetchArticle = value;
    }

    public boolean isSetFetchArticle() {
        return (this.fetchArticle!= null);
    }

    public void unsetFetchArticle() {
        this.fetchArticle = null;
    }

}
