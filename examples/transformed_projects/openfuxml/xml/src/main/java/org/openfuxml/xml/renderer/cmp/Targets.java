
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.openfuxml.org/cmp}pdf" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.openfuxml.org/cmp}html" maxOccurs="unbounded"/>
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
    "pdf",
    "html"
})
@XmlRootElement(name = "targets")
public class Targets
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Pdf> pdf;
    @XmlElement(required = true)
    protected List<Html> html;

    /**
     * Gets the value of the pdf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pdf }
     * 
     * 
     */
    public List<Pdf> getPdf() {
        if (pdf == null) {
            pdf = new ArrayList<Pdf>();
        }
        return this.pdf;
    }

    public boolean isSetPdf() {
        return ((this.pdf!= null)&&(!this.pdf.isEmpty()));
    }

    public void unsetPdf() {
        this.pdf = null;
    }

    /**
     * Gets the value of the html property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the html property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHtml().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Html }
     * 
     * 
     */
    public List<Html> getHtml() {
        if (html == null) {
            html = new ArrayList<Html>();
        }
        return this.html;
    }

    public boolean isSetHtml() {
        return ((this.html!= null)&&(!this.html.isEmpty()));
    }

    public void unsetHtml() {
        this.html = null;
    }

}
