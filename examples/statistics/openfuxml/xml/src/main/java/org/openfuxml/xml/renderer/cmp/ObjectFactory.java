
package org.openfuxml.xml.renderer.cmp;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openfuxml.xml.renderer.cmp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openfuxml.xml.renderer.cmp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Preprocessor }
     * 
     */
    public Preprocessor createPreprocessor() {
        return new Preprocessor();
    }

    /**
     * Create an instance of {@link Merge }
     * 
     */
    public Merge createMerge() {
        return new Merge();
    }

    /**
     * Create an instance of {@link Wiki }
     * 
     */
    public Wiki createWiki() {
        return new Wiki();
    }

    /**
     * Create an instance of {@link Toc }
     * 
     */
    public Toc createToc() {
        return new Toc();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link Pdf }
     * 
     */
    public Pdf createPdf() {
        return new Pdf();
    }

    /**
     * Create an instance of {@link Cmp }
     * 
     */
    public Cmp createCmp() {
        return new Cmp();
    }

    /**
     * Create an instance of {@link Targets }
     * 
     */
    public Targets createTargets() {
        return new Targets();
    }

    /**
     * Create an instance of {@link Html }
     * 
     */
    public Html createHtml() {
        return new Html();
    }

}
