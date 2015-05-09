# Patl4J
The PATL language developed for Java program adaptation between APIs with M-to-M mapping features.

### Full specification of the language
The full specification of the language can be referred to [PATL-tr.pdf](https://github.com/Mestway/Patl4J/blob/master/PATL-tr.pdf), including the proof of the main theorem.

### Transformed Projects
We use our language to transform the following projects in the [examples](https://github.com/Mestway/Patl4J/tree/master/examples) folder, namely: (We only provide the transformed project java source code here as the original source code with dependencies are oversized for github).

  * blasd: a simple mail system. (Google Calendar v2->v3)
  * clinicaweb: a web automation system for medical clinic. (Google Calendar v2->v3)
  * goofs: a file system. (Google Calendar v2->v3)
  * husacct: a software architecture conformance checking tool. (Jdom -> Dom4j)
  * openfuxml: an XML publishing framework. (Jdom -> Dom4j)
  * serenoa:  a tool for context-aware adaptation of user interfaces. (Jdom -> Dom4j)

### Transformation Program Examples
Several transformation examples are provided in the [examples](https://github.com/Mestway/Patl4J/tree/master/examples) folder, including the following several test cases.
#### Google Calender v2 to v3
  
  This is the PATL program for migrating programs using Google Calendar v2 to v3. Totally 45 methods in the API set in Google Calendar v2 are migrated with 84 transformation rules. Methods in the following API files will be transformed by the PATL program. 
  * CalendarEventEntry
  * CalendarEntry
  * AclEntry
  
#### JDom 1.1.3 to Dom4J 1.6.1
  
  This is the transformation case for a subset of JDom 1.1.3 to a subset of Dom4J 1.6.1. Toally 84 rules are used in the transformation.
  (Classes on the left hand side of the arrow are classes in JDom 1.1.3 and those on the right are their corresponding classes in Dom4J 1.6.1)
  * Element -> Element
  * Document -> Document
  * Attribute -> Attribute
  * SAXBuilder -> SAXReader
  * DOMBuilder -> DOMReader
  * Format -> OutputFormat
  * XMLOutputter -> XMLWriter
  * XPath -> XPath
  * Namespace -> Namespace

### Patl4J Eclipse Plug-in Development

The public eclipse plug-in version of the tool is under development, and will be released soon.

