
package org.openfuxml.content.ofx;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openfuxml.content.ofx package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openfuxml.content.ofx
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Raw }
     * 
     */
    public Raw createRaw() {
        return new Raw();
    }

    /**
     * Create an instance of {@link Content }
     * 
     */
    public Content createContent() {
        return new Content();
    }

    /**
     * Create an instance of {@link Sections }
     * 
     */
    public Sections createSections() {
        return new Sections();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Section createSection() {
        return new Section();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link Title }
     * 
     */
    public Title createTitle() {
        return new Title();
    }

    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public Paragraph createParagraph() {
        return new Paragraph();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link Listing }
     * 
     */
    public Listing createListing() {
        return new Listing();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link Metadata }
     * 
     */
    public Metadata createMetadata() {
        return new Metadata();
    }

}
