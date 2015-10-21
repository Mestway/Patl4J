
package org.openfuxml.xml.renderer.cmp;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="writeIntermediate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "merge")
public class Merge
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "writeIntermediate")
    protected Boolean writeIntermediate;

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
     * Gets the value of the writeIntermediate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isWriteIntermediate() {
        return writeIntermediate;
    }

    /**
     * Sets the value of the writeIntermediate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWriteIntermediate(boolean value) {
        this.writeIntermediate = value;
    }

    public boolean isSetWriteIntermediate() {
        return (this.writeIntermediate!= null);
    }

    public void unsetWriteIntermediate() {
        this.writeIntermediate = null;
    }

}
