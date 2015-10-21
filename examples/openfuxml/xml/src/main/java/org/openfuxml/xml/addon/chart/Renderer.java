
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
 *         &lt;element ref="{http://www.openfuxml.org/chart}rendererTimeseries"/>
 *         &lt;element name="timebar">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="shadow" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="gradient" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="bar">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="vertical" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="gantt">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="timePeriod" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="spline" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
    "rendererTimeseries",
    "timebar",
    "bar",
    "gantt",
    "spline"
})
@XmlRootElement(name = "renderer")
public class Renderer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected RendererTimeseries rendererTimeseries;
    @XmlElement(namespace = "", required = true)
    protected Renderer.Timebar timebar;
    @XmlElement(namespace = "", required = true)
    protected Renderer.Bar bar;
    @XmlElement(namespace = "", required = true)
    protected Renderer.Gantt gantt;
    @XmlElement(namespace = "", required = true)
    protected Object spline;

    /**
     * Gets the value of the rendererTimeseries property.
     * 
     * @return
     *     possible object is
     *     {@link RendererTimeseries }
     *     
     */
    public RendererTimeseries getRendererTimeseries() {
        return rendererTimeseries;
    }

    /**
     * Sets the value of the rendererTimeseries property.
     * 
     * @param value
     *     allowed object is
     *     {@link RendererTimeseries }
     *     
     */
    public void setRendererTimeseries(RendererTimeseries value) {
        this.rendererTimeseries = value;
    }

    public boolean isSetRendererTimeseries() {
        return (this.rendererTimeseries!= null);
    }

    /**
     * Gets the value of the timebar property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer.Timebar }
     *     
     */
    public Renderer.Timebar getTimebar() {
        return timebar;
    }

    /**
     * Sets the value of the timebar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer.Timebar }
     *     
     */
    public void setTimebar(Renderer.Timebar value) {
        this.timebar = value;
    }

    public boolean isSetTimebar() {
        return (this.timebar!= null);
    }

    /**
     * Gets the value of the bar property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer.Bar }
     *     
     */
    public Renderer.Bar getBar() {
        return bar;
    }

    /**
     * Sets the value of the bar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer.Bar }
     *     
     */
    public void setBar(Renderer.Bar value) {
        this.bar = value;
    }

    public boolean isSetBar() {
        return (this.bar!= null);
    }

    /**
     * Gets the value of the gantt property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer.Gantt }
     *     
     */
    public Renderer.Gantt getGantt() {
        return gantt;
    }

    /**
     * Sets the value of the gantt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer.Gantt }
     *     
     */
    public void setGantt(Renderer.Gantt value) {
        this.gantt = value;
    }

    public boolean isSetGantt() {
        return (this.gantt!= null);
    }

    /**
     * Gets the value of the spline property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSpline() {
        return spline;
    }

    /**
     * Sets the value of the spline property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSpline(Object value) {
        this.spline = value;
    }

    public boolean isSetSpline() {
        return (this.spline!= null);
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
     *       &lt;attribute name="vertical" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Bar
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "vertical")
        protected Boolean vertical;

        /**
         * Gets the value of the vertical property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isVertical() {
            return vertical;
        }

        /**
         * Sets the value of the vertical property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setVertical(boolean value) {
            this.vertical = value;
        }

        public boolean isSetVertical() {
            return (this.vertical!= null);
        }

        public void unsetVertical() {
            this.vertical = null;
        }

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
     *       &lt;attribute name="timePeriod" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Gantt
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "timePeriod")
        protected String timePeriod;

        /**
         * Gets the value of the timePeriod property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTimePeriod() {
            return timePeriod;
        }

        /**
         * Sets the value of the timePeriod property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTimePeriod(String value) {
            this.timePeriod = value;
        }

        public boolean isSetTimePeriod() {
            return (this.timePeriod!= null);
        }

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
     *       &lt;attribute name="shadow" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="gradient" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Timebar
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlAttribute(name = "shadow")
        protected Boolean shadow;
        @XmlAttribute(name = "gradient")
        protected Boolean gradient;

        /**
         * Gets the value of the shadow property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isShadow() {
            return shadow;
        }

        /**
         * Sets the value of the shadow property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShadow(boolean value) {
            this.shadow = value;
        }

        public boolean isSetShadow() {
            return (this.shadow!= null);
        }

        public void unsetShadow() {
            this.shadow = null;
        }

        /**
         * Gets the value of the gradient property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isGradient() {
            return gradient;
        }

        /**
         * Sets the value of the gradient property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setGradient(boolean value) {
            this.gradient = value;
        }

        public boolean isSetGradient() {
            return (this.gradient!= null);
        }

        public void unsetGradient() {
            this.gradient = null;
        }

    }

}
