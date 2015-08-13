# Patl4J
Patl4J is the Java implementation of PATL, a rule-based transformation language supporting program adaptation between APIs. Patl4J is developed as an eclipse plugin, so you need to have an Eclipse instance available to transform your projects. (We only tested on Luna and Mars version, so compatibility on other versions remains unknown.)

### Install Patl4J
You can install Patl4J-Eclipse plugin through standard eclipse plugin installation process by visiting Help->Install New Software. Then add the Patl4J update stie  [https://github.com/Mestway/Patl4JUpdateSite/raw/master/](https://github.com/Mestway/Patl4JUpdateSite/raw/master/) and install the Patl4JFeature to acquire the plugin.

When Patl4J Eclipse plugin is successfully installed, you can see Patl4J in your menu bar.

### Configure your project to transform
As our implementation is mainly used for experiment, the design can be user-unfriendly, we highly recommended you to configure your projects to be transformed following our sample projects. The followings are steps to configure your own projects.

Prior using the plugin to transform your projects, you need to configure them so that Patl4J knows which ones you want to transform. The following steps are required:
 * Step 1: Create a new folder and extract referenced libraries inside so that the .class files from these libraries can be visited. 
 * Step 2: Put your PATL rules as a file in the root directory of your project.
 * Step 3: Create an patl.option file in the root directory with the following content:
 	* Project name: with a field indicating the project name.
 	* Whether normalized: do not need in the first run, then after normalization, add this flag in the option file.
 	* Main class: the name of the main class.
 	* Platform: The platform where you run the plugin, should be one of windows, mac or linux.
  	* Output: Whether output the transformed file to the project, file for yes, otherwise only output to console.
  	* Class Path: one or more class paths for .class files used in the project.
  	* Classes to load: which classes are used in the bin directory. (Do not need in most cases, but if it failed without these item, you need to add them in your option file.)
  	* ignore: containing files and packages to be ignored, one per line
   		* file: files to be ignored.
  		* package: packages to be ignored.
  	* libraries: libraries to be added in the new API, will be added as "import PACKAGE_NAME;" in the files to be transformed.

###### Example:
```xml
<project name="Calendar">
	<platform>windows</platform>
	<output>file</output>
	<alreadyNormalized>True</alreadyNormalized>
	<mainClassName>main.Main</mainClassName>
	<classPath>C:\Program Files\Java\jdk1.8.0_11\jre\lib\jce.jar</classPath>
	<classPath>C:\Program Files\Java\jdk1.8.0_11\jre\lib\rt.jar</classPath>
	<classPath>D:\runtime-EclipseApplication\cliniweb\bin</classPath>
	<classPath>D:\runtime-EclipseApplication\cliniweb\libsrc\com.google.guava_1.6.0</classPath>
	<classToLoad>main.Main</classToLoad>
    <ignore>
    </ignore>
    <libraries>
	</libraries>
</project>
```

### Run the project
When they are configured, you can start the transformation engine by clicking the transform item in the Patl4J menu. However, the following steps are neccessary when running the examples:
* Step 1. In your project, remove the alreadyNormalized option. Click 'transform project' button and then your project is normalized but not transformed..
* Step 2. As after normalization, there can be compile errors in the project, you need to fix these compile errors and then add the <alreadyNormalized>Ture</alreadyNormalized> option back to the file patl.option.
* Step 3. Then click 'transformation project' again, and the transformed programs are written in their corresponding directories or output to console based on your option.
* 
### Sample Patl rules and projects
We transformed six projects in our experiment, and they can be found in the folder [examples](https://github.com/Mestway/Patl4J/tree/master/examples). 

The folder [raw_projects](https://github.com/Mestway/Patl4J/tree/master/examples/raw_projects) contains the configured Eclipse Java of the six experiment projects. You can download them and import as Eclipse Java projects, and then use the plugin to transform them. (Do not forget revise the class paths in the patl.option file.) 

The folder [transformed_projects](https://github.com/Mestway/Patl4J/tree/master/examples/transformed_projects) contains the transformed source code of the projects. These source code are manually revised by human labor after transformation, as our implementation have some simplication on our language design.

The six projects involved are: 

  * blasd: a simple mail system. (Google Calendar v2 -> v3)
  * clinicaweb: a web automation system for medical clinic. (Google Calendar v2 -> v3)
  * goofs: a file system. (Google Calendar v2 -> v3)
  * husacct: a software architecture conformance checking tool. (Jdom -> Dom4j)
  * openfuxml: an XML publishing framework. (Jdom -> Dom4j)
  * serenoa:  a tool for context-aware adaptation of user interfaces. (Jdom -> Dom4j)

### Transformation Rules
Besides, we also put the transformation rules separated in the [examples](https://github.com/Mestway/Patl4J/tree/master/examples) folder. There are in total two APIs to transform, one is Google Calendar v2 to v3, and the other is Jdom to Dom4j. We listed which classes are cover in the rules below.

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

### Language specification
The full specification of the language can be referred to [PATL-tr.pdf](https://github.com/Mestway/Patl4J/blob/master/PATL-tr.pdf).

### Questions?
If you have any problems/question about the project, please email mestway AT gmail.com. Besides, we are eagerly seeking more real world transformation cases involved and a better version may be developed if we get more motivation on it. 
