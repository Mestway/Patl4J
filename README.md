# Patl4J
The PATL language developed for Java program adaptation between APIs with M-to-M mapping features.

### Transformation Examples
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

