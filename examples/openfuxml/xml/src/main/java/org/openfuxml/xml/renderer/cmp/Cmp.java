
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
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
 *         &lt;element ref="{http://www.openfuxml.org/cmp}targets"/>
 *         &lt;element ref="{http://www.openfuxml.org/cmp}source"/>
 *         &lt;element ref="{http://www.openfuxml.org/cmp}preprocessor"/>
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
    "targets",
    "source",
    "preprocessor"
})
@XmlRootElement(name = "cmp")
public class Cmp
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Targets targets;
    @XmlElement(required = true)
    protected Source source;
    @XmlElement(required = true)
    protected Preprocessor preprocessor;

    /**
     * Gets the value of the targets property.
     * 
     * @return
     *     possible object is
     *     {@link Targets }
     *     
     */
    public Targets getTargets() {
        return targets;
    }

    /**
     * Sets the value of the targets property.
     * 
     * @param value
     *     allowed object is
     *     {@link Targets }
     *     
     */
    public void setTargets(Targets value) {
        this.targets = value;
    }

    public boolean isSetTargets() {
        return (this.targets!= null);
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link Source }
     *     
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link Source }
     *     
     */
    public void setSource(Source value) {
        this.source = value;
    }

    public boolean isSetSource() {
        return (this.source!= null);
    }

    /**
     * Gets the value of the preprocessor property.
     * 
     * @return
     *     possible object is
     *     {@link Preprocessor }
     *     
     */
    public Preprocessor getPreprocessor() {
        return preprocessor;
    }

    /**
     * Sets the value of the preprocessor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Preprocessor }
     *     
     */
    public void setPreprocessor(Preprocessor value) {
        this.preprocessor = value;
    }

    public boolean isSetPreprocessor() {
        return (this.preprocessor!= null);
    }

}
