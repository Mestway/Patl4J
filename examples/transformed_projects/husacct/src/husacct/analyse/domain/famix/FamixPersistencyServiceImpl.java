package husacct.analyse.domain.famix;

import husacct.analyse.domain.IModelPersistencyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

import javax.naming.directory.InvalidAttributesException;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

public class FamixPersistencyServiceImpl implements IModelPersistencyService {

    private FamixModel theModel;
    private Element rootNode, packages, libraries, classes, methods, variables, associations; // 1zz
    private HashMap<String, FamixPackage> packagesList;
    private HashMap<String, FamixClass> classList;
    private HashMap<String, FamixBehaviouralEntity> methodsList;
    private HashMap<String, FamixStructuralEntity> variablesList;
    private HashMap<String, FamixLibrary> librarieList;
    private ArrayList<FamixAssociation> associationList;

    @Override
    public Element saveModel() { 
        Element analysedProject = DocumentHelper.createElement("EmptyAnalysedApplication"); 
    	//2014-01-21 Decision: Saving and loading analysed data is not useful
    	boolean saveAnalysedData = false; 
    	if (!saveAnalysedData)
    		return analysedProject;
    	else {
         theModel = FamixModel.getInstance();
        initiateNodes();
        loadObjects();
        return createXml();
    	}
    }

    //Note - Reset was called in saveModel. Change the reset before implementing it, because the
    //in-memory Famix-domain objects are all deleted now. The objects used in this class are not
    //copies, but the real reference to the data in the memory, thus the complete model will be cleared
    //when calling this function. Please fix this.
    private void reset() {
        this.packagesList = null;
        this.librarieList = null;
        this.classList = null;
        this.methodsList = null;
        this.variablesList = null;
        this.associationList = null;
    }

    private void initiateNodes() {
        this.rootNode = DocumentHelper.createElement("Root"); 
        this.packages = DocumentHelper.createElement("Packages"); 
        this.libraries = DocumentHelper.createElement("Libraries"); 
        this.classes = DocumentHelper.createElement("Classes"); 
        this.methods = DocumentHelper.createElement("Methods"); 
        this.variables = DocumentHelper.createElement("Variables"); 
        this.associations = DocumentHelper.createElement("Associations"); 
    }

    private void loadObjects() {
    	Map<String, FamixClass> tempPackageList = (Map) theModel.classes;
        this.packagesList = (HashMap) tempPackageList;
        this.librarieList = theModel.libraries;
        Map<String, FamixClass> tempClassList = (Map) theModel.classes;
        this.classList = (HashMap) tempClassList;
        this.methodsList = theModel.behaviouralEntities;
        this.variablesList = theModel.structuralEntities;
        this.associationList = theModel.associations;
    }

    private Element createXml() { 
        Element analysedProject = DocumentHelper.createElement("AnalysedApplication"); 

        Element ElemPackages = DocumentHelper.createElement("Packages"); 
        for (String famPackageKey : packagesList.keySet()) {
            Element ElemPackage = DocumentHelper.createElement("Package"); 
            //ElemPackage.add(DocumentHelper.createElement("UniqueName").setText(packagesList.get(famPackageKey).uniqueName));
            //ElemPackage.add(DocumentHelper.createElement("Name").setText(packagesList.get(famPackageKey).name));
            //ElemPackage.add(DocumentHelper.createElement("BelongsToPackage").setText(packagesList.get(famPackageKey).belongsToPackage));
            Element e1 = DocumentHelper.createElement("UniqueName"); 
            e1.setText(packagesList.get(famPackageKey).uniqueName); 
            ElemPackage.add(e1); 
            Element e2 = DocumentHelper.createElement("Name");  
            e2.setText(packagesList.get(famPackageKey).name); 
            ElemPackage.add(e2); 
            Element e3 = DocumentHelper.createElement("BelongsToPackage"); 
            e3.setText(packagesList.get(famPackageKey).belongsToPackage); 
            ElemPackage.add(e3); 
            ElemPackages.add(ElemPackage); 
        }
        analysedProject.add(ElemPackages); 

        Element ElemLibraries = DocumentHelper.createElement("Libraries"); 
        for (String famLibrarieKey : librarieList.keySet()) {
            Element ElemLibrary = DocumentHelper.createElement("Library"); 
            Element e4 = DocumentHelper.createElement("UniqueName"); 
            e4.setText(packagesList.get(famLibrarieKey).uniqueName); 
            ElemLibrary.add(e4); 
            Element e5 = DocumentHelper.createElement("Name"); 
            e5.setText(packagesList.get(famLibrarieKey).name); 
            ElemLibrary.add(e5); 
            Element e6 = DocumentHelper.createElement("BelongsToPackage"); 
            e6.setText(packagesList.get(famLibrarieKey).belongsToPackage); 
            ElemLibrary.add(e6); 
            ElemLibrary.add(ElemLibrary); 
        }
        analysedProject.add(ElemLibraries); 
 
        Element elemClasses = DocumentHelper.createElement("Classes"); 
        for (String famClassKey : classList.keySet()) {
            Element elemClass = DocumentHelper.createElement("Class"); 
            Element e7 = DocumentHelper.createElement("UniqueName"); 
            e7.setText(classList.get(famClassKey).uniqueName); 
            elemClass.add(e7); 
            Element e8 = DocumentHelper.createElement("Name"); 
            e7.setText(classList.get(famClassKey).name); 
            elemClass.add(e8); 
            Element e9 = DocumentHelper.createElement("BelongsToPackage"); 
            e9.setText(classList.get(famClassKey).belongsToPackage); 
            elemClass.add(e9); 
            Element e10 = DocumentHelper.createElement("IsAbstract"); 
            e10.setText(new Boolean(classList.get(famClassKey).isAbstract).toString()); 
            elemClass.add(e10); 
            Element e11 = DocumentHelper.createElement("IsInnerClass"); 
            e11.setText(new Boolean(classList.get(famClassKey).isInnerClass).toString()); 
            elemClass.add(e11); 
            elemClasses.add(elemClass); 
        }
        analysedProject.add(elemClasses); 

        Element elemMethods = DocumentHelper.createElement("Methods"); 
        for (String famMethKey : methodsList.keySet()) {
            Element elemMethod = DocumentHelper.createElement("Method"); 
            Element e12 = DocumentHelper.createElement("UniqueName"); 
            e12.setText(methodsList.get(famMethKey).uniqueName); 
            elemMethod.add(e12); 
            Element e13 = DocumentHelper.createElement("Name"); 
            e13.setText(methodsList.get(famMethKey).name); 
            elemMethod.add(e13); 
            Element e14 = DocumentHelper.createElement("accessControlQualifier"); 
            e14.setText(methodsList.get(famMethKey).accessControlQualifier); 
            elemMethod.add(e14); 
            Element e15 = DocumentHelper.createElement("signature"); 
            e15.setText(methodsList.get(famMethKey).signature); 
            elemMethod.add(e15); 
            Element e16 = DocumentHelper.createElement("declaredReturnType"); 
            e16.setText(methodsList.get(famMethKey).declaredReturnType); 
            elemMethod.add(e16); 
            elemMethods.add(elemMethod); 
        }
        analysedProject.add(elemMethods); 

        Element elemVariables = DocumentHelper.createElement("Variables"); 
        for (String famVarKey : variablesList.keySet()) {
            Element elemVariable = DocumentHelper.createElement("Variable"); 
            Element e17 = DocumentHelper.createElement("UniqueName"); 
            e17.setText(variablesList.get(famVarKey).uniqueName); 
            elemVariable.add(e17); 
            Element e18 = DocumentHelper.createElement("Name"); 
            e18.setText(variablesList.get(famVarKey).name); 
            elemVariable.add(e18); 
            Element e19 = DocumentHelper.createElement("BelongsToClass"); 
            e19.setText(variablesList.get(famVarKey).belongsToClass); 
            elemVariable.add(e19); 
            Element e20 = DocumentHelper.createElement("declareType"); 
            e20.setText(variablesList.get(famVarKey).declareType); 
            elemVariable.add(e20); 
            elemVariables.add(elemVariable); 
        }
        analysedProject.add(elemVariables); 

        Element elemAssociations = DocumentHelper.createElement("Associations"); 
        for (FamixAssociation famAssoCurrent : associationList) {
            String From = famAssoCurrent.from;
            String To = famAssoCurrent.to;
            String Type = famAssoCurrent.type;
            String LineNr = Integer.toString(famAssoCurrent.lineNumber);
            Element elemAssociation = DocumentHelper.createElement("Association"); 
            Element e21 = DocumentHelper.createElement("From"); 
            e21.setText(From); 
            elemAssociation.add(e21); 
            Element e22 = DocumentHelper.createElement("To"); 
            e22.setText(To); 
            elemAssociation.add(e22); 
            Element e23 = DocumentHelper.createElement("Type"); 
            e23.setText(Type); 
            elemAssociation.add(e23); 
            Element e24 = DocumentHelper.createElement("linenumber"); 
            e24.setText(LineNr); 
            elemAssociation.add(e24); 
            elemAssociations.add(elemAssociation); 
        }
        analysedProject.add(elemAssociations); 
        return analysedProject;
    }

    @Override
    public void loadModel(Element analyseElement) { 
    	//2014-01-21 Decision: Saving and loading analysed data is not useful
    	boolean saveAnalysedData = false; 
    	if (!saveAnalysedData)
    		return;
    	else {
    		theModel = FamixModel.getInstance();
    		
    		for (Element rootElem : analyseElement.elements()) { 
    			for (Element rootChild1Elem : rootElem.elements()) { 
    				if (rootChild1Elem.getName().equalsIgnoreCase("Package")) { 
    					FamixPackage famTempPackage = new FamixPackage();
    					for (Element rootChild1Attrs : rootChild1Elem.elements()) { 
    						if (rootChild1Attrs.getName().equalsIgnoreCase("UniqueName")) { 
    							famTempPackage.uniqueName = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("Name")) { 
    							famTempPackage.name = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("BelongsToPackage")) { 
    							famTempPackage.belongsToPackage = rootChild1Attrs.getText(); 
    						}
    					}
    					try {
    						theModel.addObject(famTempPackage);
    					} catch (InvalidAttributesException e) {
    						e.printStackTrace();
    					}
    				}
    				if (rootChild1Elem.getName().equalsIgnoreCase("Class")) {
    					FamixClass famTempClass = new FamixClass();
    					for (Element rootChild1Attrs : rootChild1Elem.elements()) { 

    						if (rootChild1Attrs.getName().equalsIgnoreCase("UniqueName")) { 
    							famTempClass.uniqueName = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("Name")) { 
    							famTempClass.name = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("BelongsToPackage")) { 
    							famTempClass.belongsToPackage = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("IsAbstract")) { 

    							if (rootChild1Attrs.getText().equalsIgnoreCase("true")) { 
    								famTempClass.isAbstract = true;
    							} else {
    								famTempClass.isAbstract = false;
    							}
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("IsInnerClass")) { 
    							if (rootChild1Attrs.getText().equalsIgnoreCase("true")) { 
    								famTempClass.isInnerClass = true;
    							} else {
    								famTempClass.isInnerClass = false;
    							}
    						}
    					}
    					try {
    						theModel.addObject(famTempClass);
    					} catch (InvalidAttributesException e) {
    						e.printStackTrace();
    					}
    				}
    				if (rootChild1Elem.getName().equalsIgnoreCase("Method")) { 
    					FamixMethod famixMethod = new FamixMethod();
    					for (Element rootChild1Attrs : rootChild1Elem.elements()) { 

    						famixMethod.isPureAccessor = false;
    						famixMethod.belongsToClass = "";
    						famixMethod.isConstructor = false;
    						famixMethod.isAbstract = false;
    						famixMethod.hasClassScope = false;

    						if (rootChild1Attrs.getName().equalsIgnoreCase("Name")) { 
    							famixMethod.name = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("UniqueName")) { 
    							famixMethod.uniqueName = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("accessControlQualifier")) { 
    							famixMethod.accessControlQualifier = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("signature")) { 
    							famixMethod.signature = rootChild1Attrs.getText();    
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("declaredReturnType")) { 
    							famixMethod.declaredReturnType = rootChild1Attrs.getText(); 
    						}
    					}
    					try {
    						theModel.addObject(famixMethod);
    					} catch (InvalidAttributesException e) {
    						e.printStackTrace();
    					}
    				}
    				if (rootChild1Elem.getName().equalsIgnoreCase("Variable")) { 

    					FamixAttribute famTempVariable = new FamixAttribute();
    					for (Element rootChild1Attrs : rootChild1Elem.elements()) { 

    						if (rootChild1Attrs.getName().equalsIgnoreCase("UniqueName")) { 
    							famTempVariable.uniqueName = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("Name")) { 
    							famTempVariable.name = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("BelongsToClass")) { 
    							famTempVariable.belongsToClass = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("declareType")) { 
    							famTempVariable.declareType = rootChild1Attrs.getText(); 
    						}
    					}
    					try {
    						theModel.addObject(famTempVariable);
    					} catch (InvalidAttributesException e) {
    						e.printStackTrace();
    					}

    				}
    				if (rootChild1Elem.getName().equalsIgnoreCase("Association")) {
    					FamixAssociation famTempAssociation = new FamixAssociation();
    					for (Element rootChild1Attrs : rootChild1Elem.elements()) {  
    						if (rootChild1Attrs.getName().equalsIgnoreCase("From")) { 
    							famTempAssociation.from = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("Type")) { 
    							famTempAssociation.type = rootChild1Attrs.getText(); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("linenumber")) { 
    							famTempAssociation.lineNumber = Integer.parseInt(rootChild1Attrs.getText()); 
    						} else if (rootChild1Attrs.getName().equalsIgnoreCase("To")) { 
    							famTempAssociation.to = rootChild1Attrs.getText(); 
    						}
    					}
    					try {
    						theModel.addObject(famTempAssociation);
    					} catch (InvalidAttributesException e) {
    						e.printStackTrace();
    					}
    				}
    			}
    		}
    	}
    }
}
