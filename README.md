# Patl4J
The PATL language developed for Java program adaptation between APIs with M-to-M mapping features.

### Running Patl4J

To run Patl4J as an eclipse plugin, download and install the plugin in eclipse (Download site to be added).

For each projects in the workspace, adding a file named "patl.option" in the root directory of the project with the following format:

  * project: with a field indicating the project name.
  * platform: The platform where you run the plugin, should be one of windows, mac or linux.
  * output: Whether output the transformed file to the project, file for yes, otherwise only output to console.
  * classPath: one or more class paths for .class files used in the project.
  * ignore: containing files and packages to be ignored, one per line
  *   * file: files to be ignored.
  *   * package: packages to be ignored.
  * libraries: libraries to be added in the new API, will be added as "import PACKAGE_NAME;" in the files to be transformed.

###### Example:
```xml
<project name="testAB">
	<platform>windows</platform>
	<output>file</output>
	<classPath>D:\runtime-EclipseApplication\testAB\</classPath>
	<classPath>D:\runtime-EclipseApplication\testAB\bin\</classPath>
 	<classPath>D:\runtime-EclipseApplication\API-A\bin\</classPath>
 	<classPath>D:\runtime-EclipseApplication\API-A\</classPath>
 	<ignore>
 		<file>a.java</file>
 		<file>MyInt.java</file>
 		<package>b.package</package>
 	</ignore>
	 <libraries>
		<lib>api_b.B</lib>
		<lib>api_b.IntPair</lib>
	</libraries>
</project>
```

### Full specification of the language
The full specification of the language can be referred to [PATL-tr.pdf](https://github.com/Mestway/Patl4J/blob/master/PATL-tr.pdf), including the proof of the main theorem.

### Transformed Projects
We use our language to transform the following projects in the [examples](https://github.com/Mestway/Patl4J/tree/master/examples) folder, namely: (We only provide the transformed project java source code here as the original source code with dependencies are oversized for github).

  * blasd: a simple mail system. (Google Calendar v2 -> v3)
  * clinicaweb: a web automation system for medical clinic. (Google Calendar v2 -> v3)
  * goofs: a file system. (Google Calendar v2 -> v3)
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

