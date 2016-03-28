# Patl4J
Patl4J is the Java implementation of PATL, a rule-based transformation language supporting program adaptation between APIs. Patl4J is developed as an eclipse plugin, so you need to have an Eclipse instance available to transform your projects. (We only tested on Luna and Mars version, so compatibility on other versions remains unknown.)


### Install Patl4J
You can install Patl4J-Eclipse plugin through standard eclipse plugin installation process by visiting Help->Install New Software. Then add the Patl4J update stie  [https://github.com/Mestway/Patl4JUpdateSite/raw/master/](https://github.com/Mestway/Patl4JUpdateSite/raw/master/) and install the Patl4JFeature to acquire the plugin.

When Patl4J Eclipse plugin is successfully installed, you can see Patl4J in your menu bar, in which there will be two menus of 'normalize projects' and 'transform projects'. The 'normalize projects' menu excute the guided normalize procedure and the other excute the transform procedure.

### Configure your project to transform
As our implementation is mainly used for experiment, the design can be user-unfriendly, we highly recommended you to configure your projects to be transformed following our sample projects. The followings are steps to configure your own projects.

Prior using the plugin to transform your projects, you need to configure them so that Patl4J knows which ones you want to transform. The following steps are required:
 * Step 1: Create a new folder and extract referenced libraries inside so that the .class files from these libraries can be visited. 
 * Step 2: Put your PATL rules as a file in the root directory of your project.
 * Step 3: Create an patl.option file in the root directory with the following content:
  * Project name: with a field indicating the project name.
  * Whether normalized: just set the value as false.
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
<project name="EvolutionChamber">
  <platform>windows</platform>
  <output></output>
  <alreadyNormalized>false</alreadyNormalized>
  <mainClassName>com.fray.evo.ui.swingx.EcSwingXMain</mainClassName>
  <classPath>C:\Program Files\Java\jdk1.8.0_60\jre\lib\rt.jar</classPath>
  <classPath>C:\Program Files\Java\jdk1.8.0_60\jre\lib\jce.jar</classPath>
  <classPath>\bin</classPath>
  <classPath>\libsrc\commons-lang-2.6</classPath>
  <classPath>\libsrc\commons-codec-1.10</classPath>
  <classPath>\libsrc\commons-collections-3.2</classPath>
  <classPath>\libsrc\jgap</classPath>
  <classPath>\libsrc\looks-2.1.4</classPath>
  <classPath>\libsrc\swingx-core-1.6.2</classPath>
  <classToLoad>com.fray.evo.ui.swingx.EcSwingXMain</classToLoad>
    <ignore>
    </ignore>
    <libraries>
  </libraries>
</project>
```

### Run the project
When they are configured, you can start the transformation engine by clicking the 'transform projects' item in the Patl4J menu. However, the following steps are neccessary when running the examples:
* Step 1. First, you need click 'normalize projects' button and then your project is normalized but not transformed..
* Step 2. As after normalization, there can be compile errors in the project, you need to fix these compile errors.
* Step 3. Then click 'transform projects' button, and the transformed programs are written in their corresponding directories or output to console based on your option.

### Sample Patl rules and projects
We transformed nine projects in our experiment, and they can be found in the folder [examples](https://github.com/Mestway/Patl4J/tree/master/examples). In this directory, mainly contains four sub-folders: [raw_projects](https://github.com/Mestway/Patl4J/tree/master/examples/raw_projects), [statistics](https://github.com/Mestway/Patl4J/tree/master/examples/statistics), [transformation_rules](https://github.com/Mestway/Patl4J/tree/master/examples/transformation_rules) and [transformed_projects](https://github.com/Mestway/Patl4J/tree/master/examples/transformed_projects).

The folder [raw_projects](https://github.com/Mestway/Patl4J/tree/master/examples/raw_projects) contains the configured Eclipse Java of the six experiment projects. You can download them and import as Eclipse Java projects, and then use the plugin to transform them. (Do not forget revise the class paths in the patl.option file.) 

The folder [statistics](https://github.com/Mestway/Patl4J/tree/master/examples/statistics) contains all the source projects used in experiments, which are labeled by different identifiers at the end of each line of source code.
  * __//Y__: This statement can be transformed by one-to-one transformation rule.
  * __//Ym__: This statement can be transformed by many-to-many transformation rule along with some other statements.
  * __//Ymm__: This statment can be transformed by many-to-many transformation rule, but need to perform our guided-normalization before transformation.
  * __//N__: This statment cannot be transformed by Patl4J.
  * __//W__: This statement can be transformed, but some warnings will be raised.

The folder [transformed_rules](https://github.com/Mestway/Patl4J/tree/master/examples/transformed_rules) contains three configure files: [Google_Calender v2 to v3.patl], [Java Swing to SWT.patl] and [Jdom to Dom4j.patl], which are used in the transformation procedure. [More detail information](#Transformation Rules)

The folder [transformed_projects](https://github.com/Mestway/Patl4J/tree/master/examples/transformed_projects) contains the transformed source code of the projects. These source code are manually revised by human labor after transformation, as our implementation have some simplication on our language design.

The nine projects involved are: 

  * __blasd__: a simple mail system. (Google Calendar v2 -> v3)
  * __clinicaweb__: a web automation system for medical clinic. (Google Calendar v2 -> v3)
  * __goofs__: a file system. (Google Calendar v2 -> v3)
  * __husacct__: a software architecture conformance checking tool. (Jdom -> Dom4j)
  * __openfuxml__: an XML publishing framework. (Jdom -> Dom4j)
  * __serenoa__:  a tool for context-aware adaptation of user interfaces. (Jdom -> Dom4j)
  * __EvolutionChamber__: an application that generates Starcraft 2 build orders. (Swing -> SWT)
  * __Marble__: an simple multi-user game with an friendly UI designed using Swing. (Swing -> SWT)
  * __SwingHeat__: a tool that is used to analyze transfer heat through fins. (Swing -> SWT)

### Transformation Rules
Besides, we also put the transformation rules separated in the [examples](https://github.com/Mestway/Patl4J/tree/master/examples/transformation_rules) folder. There are in total three APIs to transform, one is Google Calendar v2 to v3, one is Jdom to Dom4j and another is Java GUI API Swing to SWT. We listed which classes are cover in the rules below.

##### Google Calender v2 to v3
  This is the PATL program for migrating programs using Google Calendar v2 to v3. Totally 45 methods in the API set in Google Calendar v2 are migrated with 84 transformation rules. Methods in the following API files will be transformed by the PATL program. 
  * CalendarEventEntry
  * CalendarEntry
  * AclEntry
  
##### JDom 1.1.3 to Dom4J 1.6.1
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

#### Java Swing to SWT
  This is the transformation case for a subset of Java GUI designing, Swing to SWT. Totally there are 110 rules involved in the transformation.
  * JFrame -> Shell
  * JPanel -> Composite
  * JButton -> Button
  * ...

### Language specification
The full specification of the language can be referred to [PATL-tr.pdf](https://github.com/Mestway/Patl4J/blob/master/PATL-tr.pdf).

### Questions?
If you have any problems/question about the project, please email mestway AT gmail.com. Besides, we are eagerly seeking more real world transformation cases involved and a better version may be developed if we get more motivation on it. 

