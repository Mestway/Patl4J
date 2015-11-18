
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.exlp.xml.io.Dir;
import org.openfuxml.xml.renderer.html.Renderer;
import org.openfuxml.xml.renderer.html.Template;


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
 *         &lt;element ref="{http://exlp.sf.net/io}dir" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/renderer/html}renderer" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/renderer/html}template" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dir",
    "renderer",
    "template"
})
@XmlRootElement(name = "html")
public class Html
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://exlp.sf.net/io", required = true)
    protected List<Dir> dir;
    @XmlElement(namespace = "http://www.openfuxml.org/renderer/html", required = true)
    protected List<Renderer> renderer;
    @XmlElement(namespace = "http://www.openfuxml.org/renderer/html", required = true)
    protected List<Template> template;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "active")
    protected Boolean active;

    /**
     * Gets the value of the dir property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dir property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dir }
     * 
     * 
     */
    public List<Dir> getDir() {
        if (dir == null) {
            dir = new ArrayList<Dir>();
        }
        return this.dir;
    }

    public boolean isSetDir() {
        return ((this.dir!= null)&&(!this.dir.isEmpty()));
    }

    public void unsetDir() {
        this.dir = null;
    }

    /**
     * Gets the value of the renderer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the renderer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRenderer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Renderer }
     * 
     * 
     */
    public List<Renderer> getRenderer() {
        if (renderer == null) {
            renderer = new ArrayList<Renderer>();
        }
        return this.renderer;
    }

    public boolean isSetRenderer() {
        return ((this.renderer!= null)&&(!this.renderer.isEmpty()));
    }

    public void unsetRenderer() {
        this.renderer = null;
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
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    public boolean isSetActive() {
        return (this.active!= null);
    }

    public void unsetActive() {
        this.active = null;
    }

}
