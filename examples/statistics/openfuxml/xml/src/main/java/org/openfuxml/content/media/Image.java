
package org.openfuxml.content.media;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.layout.Height;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Title;


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
 *         &lt;element ref="{http://www.openfuxml.org}title" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org}comment" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}alignment" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}width" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/layout}height" minOccurs="0"/>
 *         &lt;element ref="{http://www.openfuxml.org/media}media"/>
 *       &lt;/sequence>
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
    "title",
    "comment",
    "alignment",
    "width",
    "height",
    "media"
})
@XmlRootElement(name = "image")
public class Image implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://www.openfuxml.org")
    protected Title title;
    @XmlElement(namespace = "http://www.openfuxml.org")
    protected Comment comment;
    @XmlElement(namespace = "http://www.openfuxml.org/layout")
    protected Alignment alignment;
    @XmlElement(namespace = "http://www.openfuxml.org/layout")
    protected Width width;
    @XmlElement(namespace = "http://www.openfuxml.org/layout")
    protected Height height;
    @XmlElement(required = true)
    protected Media media;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return (this.title!= null);
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link Comment }
     *     
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comment }
     *     
     */
    public void setComment(Comment value) {
        this.comment = value;
    }

    public boolean isSetComment() {
        return (this.comment!= null);
    }

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link Alignment }
     *     
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alignment }
     *     
     */
    public void setAlignment(Alignment value) {
        this.alignment = value;
    }

    public boolean isSetAlignment() {
        return (this.alignment!= null);
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Width }
     *     
     */
    public Width getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Width }
     *     
     */
    public void setWidth(Width value) {
        this.width = value;
    }

    public boolean isSetWidth() {
        return (this.width!= null);
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Height }
     *     
     */
    public Height getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Height }
     *     
     */
    public void setHeight(Height value) {
        this.height = value;
    }

    public boolean isSetHeight() {
        return (this.height!= null);
    }

    /**
     * Gets the value of the media property.
     * 
     * @return
     *     possible object is
     *     {@link Media }
     *     
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Sets the value of the media property.
     * 
     * @param value
     *     allowed object is
     *     {@link Media }
     *     
     */
    public void setMedia(Media value) {
        this.media = value;
    }

    public boolean isSetMedia() {
        return (this.media!= null);
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

}
