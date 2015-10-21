package rulesEngine.manager;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;

import rulesEngine.listener.FileListener;
import rulesEngine.utils.Logger;



public abstract class AdaptationEngine {
	protected static final String undefinedModelPrefix = "undefined ";
	protected ContextManager cm;
	protected File rulesFile;
	private enum ConditionType{
		STRING,
		BOOLEAN,
		NUMBER
	}
	protected Element rulesRoot; 
	
	public abstract Element getModel(AE_ModelId modelId)  throws Exception, IOException, ContextManagerParserException; 
	public abstract void loadModels(String user) throws Exception, IOException, URISyntaxException, ContextManagerParserException;
	public abstract void launchListeners(String rulesPath, FileListener listener);
	//protected abstract void updateModel(AE_ModelId modelId); 
	
	protected void init(String context_manager_uri, boolean loadModels, boolean executeRules, String currentUser) throws Exception, IOException{
		cm = new ContextManager(context_manager_uri);
		
	    try
	    {   
	       if(loadModels)
	        loadModels(currentUser);
	        
	        
	       if(executeRules)
	    	   executeRules();	       
	      
	       Logger.log("models loaded and rules executed");
	    }
	    catch(Exception e){	    	
	    	e.printStackTrace();	    
	    	Logger.log("problem while loading the model or when executing rules");
	    }	
	}
	
	private String getModelId(Element elt){ 
		String model = elt.attributeValue("externalModelId"); 
		if(model == null || model.length() == 0){
			model = undefinedModelPrefix + "0";
		}
		return model;
	}
	
	
	private boolean testCondition(Element conditionElt) throws ruleModelParserException, Exception, IOException, ContextManagerParserException{ 
		XPath xpa; 
     	List<String> conditionsList = new ArrayList<String>();	 	 	        
     	List<Element> conditionExpressions = conditionElt.elements(); 
     	String type = "";
     	for(Element conditionExpression : conditionExpressions){ 
     		if(conditionExpression.getName().equals("constant")){ 
     			String typeTemp = conditionExpression.attributeValue("type"); 
     			if(typeTemp != null && typeTemp.length() > 0){
     				if(type.length() > 0 && !typeTemp.equals(type)){
     					throw new ruleModelParserException("Incompatibles condition types");
     				}else{
     					type = typeTemp;
     				}
     			}
     			conditionsList.add(conditionExpression.attributeValue("value")); 
     		}
     		else if (conditionExpression.getName().equals("entityReference")){ 
     			String modelId = getModelId(conditionExpression);
     			String xpath = conditionExpression.attributeValue("xPath"); 
     			xpa = new DefaultXPath(xpath);	    
     			Element model = getModel(new AE_ModelId(modelId)); 
     			conditionsList.add(xpa.valueOf(model));     			 
     		}else if (conditionExpression.getName().equals("condition")){ 
     			type = "boolean";     			    
     			conditionsList.add("" + testCondition(conditionExpression));
     		}else{
     			throw new ruleModelParserException(conditionExpression.getName() + " tag isn't supported yet in conditions");
     		}
     	}
     	ConditionType conditionType;
     	//expression evaluation
     	if(type.length() == 0){
     		type = "string";
     	}	 	        	
     	
     	ArrayList<Boolean> booleanConditions = new ArrayList<Boolean>();
		ArrayList<Double> NumberConditions = new ArrayList<Double>();
		if(type.equals("boolean")){
     		conditionType = ConditionType.BOOLEAN;
     	 	        	
	        	
     		for(String  s : conditionsList){
     			booleanConditions.add(Boolean.parseBoolean(s));
     		}
     		
     	}else if(type.equals("string") 
     			|| type.equals("normalizedString")){
     		conditionType = ConditionType.STRING;
     		
     	}else if(type.equals("float") 
     			|| type.equals("int") 
     			|| type.equals("integer") 
     			|| type.equals("decimal")
     			|| type.equals("negativeInteger")
     			|| type.equals("nonPositiveInteger") 
     			|| type.equals("long")
     			|| type.equals("positiveInteger")
     			|| type.equals("short")
     			|| type.equals("unsignedInt")
	 	       		|| type.equals("unsignedLong")
	 	       		|| type.equals("unsignedShort")){
     		conditionType = ConditionType.NUMBER;    		
     		
     		
     		for(String  s : conditionsList){
     			NumberConditions.add(Double.parseDouble(s));
     		}
	        	
     	}else{	
     		throw new ruleModelParserException("Type '" + type + "' isn't supproted yet");
     	}
     	
     	String operator = conditionElt.attributeValue("operator");     	 
     	String incompatibleOperatorMessage = "Operator '" + operator + "' isn't compatible with type '" + type + "'";
     	if(operator == null){
     		if(conditionsList.size() == 1 ){
     			try{
     				return new Boolean (readConstantOrEntity(conditionElt.elements().get(0)));   
     			}catch(Exception e){
     				throw new ruleModelParserException("Content of a condition without operator must be a boolean");
     			}
     		}
     		throw new ruleModelParserException("Condition without operator must have a size of 1");
    	}
     	if((conditionsList.size() > 1 && operator.equals("not"))
     			|| (conditionsList.size() < 2 && !operator.equals("not"))){
     		throw new ruleModelParserException("Condition with operator '" + operator + "' hasn't the appropriate number of arguments");
	 	}
     	
     	boolean conditionOK = true;
     	
     	if(operator.equals("and")){
     		if(!conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);	 		 		 	    	        			
     		}	 	        		
     		for(Boolean b : booleanConditions){
     			conditionOK = conditionOK && b;
     		}     		
     		
     	}else if(operator.equals("or")){
     		if(!conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		conditionOK = false;
     		for(Boolean b : booleanConditions){
     			conditionOK = conditionOK || b;
     		}
     	}else if(operator.equals("xor")){
     		if(!conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		conditionOK = booleanConditions.get(0);
     		for(int i = 1; i < booleanConditions.size(); i++){
     			conditionOK = conditionOK ^ booleanConditions.get(i);
     		}
     	}else if(operator.equals("contains")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		String reference = conditionsList.get(0);
     		for(int i = 1; i < conditionsList.size(); i++){
     			conditionOK = conditionOK && conditionsList.get(i).contains(reference);
     		}
     		
     	}else if(operator.equals("starts")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		String reference = conditionsList.get(0);
     		for(int i = 1; i < conditionsList.size(); i++){
     			conditionOK = conditionOK && conditionsList.get(i).startsWith(reference);
     		}
     	}else if(operator.equals("ends")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		String reference = conditionsList.get(0);
     		for(int i = 1; i < conditionsList.size(); i++){
     			conditionOK = conditionOK && conditionsList.get(i).endsWith(reference);
     		}
     	}else if(operator.equals("gt")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		if(conditionType.name().equals(ConditionType.STRING.name())){
     			String reference = conditionsList.get(0);
	        		for(int i = 1; i < conditionsList.size(); i++){
	        			conditionOK = conditionOK && conditionsList.get(i).compareTo(reference) > 0;
	        		}
     		}else{
     			Double reference = NumberConditions.get(0);
	        		for(int i = 1; i < NumberConditions.size(); i++){
	        			conditionOK = conditionOK && NumberConditions.get(i) > reference;
	        		}
     		}
     	}else if(operator.equals("lt")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		if(conditionType.name().equals(ConditionType.STRING.name())){
     			String reference = conditionsList.get(0);
	        		for(int i = 1; i < conditionsList.size(); i++){
	        			conditionOK = conditionOK && conditionsList.get(i).compareTo(reference) < 0;
	        		}
     		}else{
     			Double reference = NumberConditions.get(0);
	        		for(int i = 1; i < NumberConditions.size(); i++){
	        			conditionOK = conditionOK && NumberConditions.get(i) < reference;
	        		}
     		}
     	}else if(operator.equals("gteq")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		if(conditionType.name().equals(ConditionType.STRING.name())){
     			String reference = conditionsList.get(0);
	        		for(int i = 1; i < conditionsList.size(); i++){
	        			conditionOK = conditionOK && conditionsList.get(i).compareTo(reference) >= 0;
	        		}
     		}else{
     			Double reference = NumberConditions.get(0);
	        		for(int i = 1; i < NumberConditions.size(); i++){
	        			conditionOK = conditionOK && NumberConditions.get(i) >= reference;
	        		}
     		}
     	}else if(operator.equals("lteq")){
     		if(conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}
     		if(conditionType.name().equals(ConditionType.STRING.name())){
     			String reference = conditionsList.get(0);
	        		for(int i = 1; i < conditionsList.size(); i++){
	        			conditionOK = conditionOK && conditionsList.get(i).compareTo(reference) <= 0;
	        		}
     		}else{
     			Double reference = NumberConditions.get(0);
	        		for(int i = 1; i < NumberConditions.size(); i++){
	        			conditionOK = conditionOK && NumberConditions.get(i) <= reference;
	        		}
     		}
     	}else if(operator.equals("eq")){
     		String reference = conditionsList.get(0);
     		for(int i = 1; i < conditionsList.size(); i++){
     			conditionOK = conditionOK && conditionsList.get(i).equals(reference);
     		}
     		
     	}else if(operator.equals("neq")){
     		for(int i = 0; i < conditionsList.size(); i++){
     			String reference = conditionsList.get(i);
	        		for(int j = 0; j < conditionsList.size(); j++){
	        			if(i != j){
	        				conditionOK = conditionOK && !(conditionsList.get(j).equals(reference));
	        			}
	        		}
     		}
     	}else if(operator.equals("not")){
     		if(!conditionType.name().equals(ConditionType.BOOLEAN.name())){
     			throw new ruleModelParserException(incompatibleOperatorMessage);		 		 		 	   
     		}	 
     		conditionOK = !booleanConditions.get(0); 
     	}
     	return conditionOK; 	
    
	}
	
	private String readConstantOrEntity(Element element) throws Exception, ruleModelParserException, IOException, ContextManagerParserException{ 
		String response;
		if(element.getName().equals("constant")){			 
 			response = element.attributeValue("value"); 
 		}
 		else if (element.getName().equals("entityReference")){ 
 			String modelId = getModelId(element);
 			String xpath = element.attributeValue("xPath"); 
 			XPath xpa = new DefaultXPath(xpath);	     
 			response = xpa.valueOf(getModel(new AE_ModelId(modelId))); 
 		}else{
 			throw new ruleModelParserException(element.getName() + " tag isn't supported yet in conditions");
 		}
		return response;
	}
	
	private void executeAction(Element action) throws Exception, ruleModelParserException, IOException, ContextManagerParserException{ 
		AE_Target modelToUpdate = null;
     	List<Element> actionExpressions = action.elements(); 
     	for(Element actionExpression : actionExpressions){ 
     		if(actionExpression.getName().equals("create")){ 
     			modelToUpdate = getTargets(actionExpression, "containingEntityReference");
     			List<?> actionTargets = modelToUpdate.getTargets();
     			Element complexType = actionExpression.element("complexType"); 
     			if(complexType == null){
     				Element actionValueElt = actionExpression.element("value"); 
         			if(actionValueElt == null){         				
         				throw new ruleModelParserException("Create action can't be interpreted yet whitout value");
    	 		 	}
         			actionValueElt = actionValueElt.elements().get(0); 
	 		        String actionValue = readConstantOrEntity(actionValueElt);
	 		        for(Object actionTarget : actionTargets){
	 		        	if(actionTarget instanceof Element){	  
	 		        		Element element = new Element(actionValue); 
	 		        		((Element)actionTarget).add(element); 
	 		        	}else if(actionTarget instanceof Attribute){
	 		        		
	 		        	}else{
	 		        		throw new ruleModelParserException(actionTarget.getClass() + " not handled yet in create action");	 			 		 	        	
	 		        	}	 			 		        	
	 		        }	
     			}else{    				
     				Document model = getModel(new AE_ModelId(getModelId(complexType))).getDocument(); 
     				modelToUpdate = new AE_Target(getModelId(complexType), model, null);
     				XPath xpa = new DefaultXPath(complexType.attributeValue("xPath"));	          
     		        List<Object> elements = (List<Object>) xpa.selectNodes(model.getRootElement());	         
     		        for(Object element : elements){
     		        	 for(Object actionTarget : actionTargets){
     		        		 if(element instanceof Element){ 
     		        			((Element)actionTarget).add(((Element)element).clone());  
     		        		 }else if(element instanceof Attribute){ 
     		        			((Element)actionTarget).addAttribute(((Attribute)element).getName(), ((Attribute)element).getValue());   
     		        		 }else{
     		        			throw new ruleModelParserException(actionTarget.getClass() + " not handled yet in create action");	 			 		 	        	
     		 		        	
     		        		 }
     		        		
     		        	 }
     		        }
     			}
     			
     			
	 		       
     		}else if(actionExpression.getName().equals("delete")){ 
     			modelToUpdate = getTargets(actionExpression, "entityReference");
     			List<?> actionTargets = modelToUpdate.getTargets();
     			for(Object actionTarget : actionTargets){
	 		        	if(actionTarget instanceof Content){	  
	 		        		((Content) actionTarget).detach(); 
	 		        	}else if(actionTarget instanceof Attribute){ 
	 		        		((Attribute) actionTarget).detach(); 
	 		        	}else{
	 		        		throw new ruleModelParserException(actionTarget.getClass() + " not handled yet in delete action");	 			 		 	        	
			 		   }
	 		        	
	 		        }
     			
     		}else if(actionExpression.getName().equals("update")){	     				 		        			
	 		        
	 		       modelToUpdate = getTargets(actionExpression, "entityReference");
	 		       List<?> actionTargets = modelToUpdate.getTargets();
	 		        Element actionValueElt = actionExpression.element("value").elements().get(0);  
	 		        String actionValue = readConstantOrEntity(actionValueElt);
	 		        for(Object actionTarget : actionTargets){
	 		        	if(actionTarget instanceof Element){	 			 		        		 
	 		        		((Element)actionTarget).setText(actionValue); 
	 		        	}
	 		        	else if(actionTarget instanceof Attribute){	 			 		        		 
	 		        		((Attribute)actionTarget).setValue(actionValue); 
	 		        	}else{
	 		        		throw new ruleModelParserException(actionTarget.getClass() + " not handled yet in update action");	 			 		 	        	
	 		        	}	 			 		        	
	 		        }	 			 		        
	 		        
     		}else if(actionExpression.getName().equals("if")){	 		        		 
      			executeIf(actionExpression);
				
     		}else if(actionExpression.getName().equals("block")){	 		        		 
      			executeAction(actionExpression);
      							
     		}else if(actionExpression.getName().equals("for")){	 		        		 
      			int from = Integer.parseInt(actionExpression.attributeValue("from")); 
      			int to = Integer.parseInt(actionExpression.attributeValue("to")); 
      			for(int i = from; i < to ; i++){
      				for(Element doElt : actionExpression.elements()){ 
      					executeAction(doElt);							
      				}
      			}
     		}else{
     			throw new ruleModelParserException(actionExpression.getName() + " tag isn't supported yet in actions"); 
        	}
     		
     		replaceModel(modelToUpdate);
     	}
     
	}
		
	
	protected void replaceModel(AE_Target modelToUpdate) throws Exception, IOException, ContextManagerParserException {}
	
	private  AE_Target getTargets(Element element, String propertyWhithXpath) throws Exception, IOException, ContextManagerParserException{ 
		String xpath = element.element(propertyWhithXpath).attributeValue("xPath"); 
		XPath xpa = new DefaultXPath(xpath);	       
		String model = getModelId(element.element(propertyWhithXpath));	 
		Element modelElt = getModel(new AE_ModelId(model)); 
        return new AE_Target(model, modelElt.getDocument(), xpa.selectNodes(modelElt)); 
	       
	}
	
	private boolean executeIf(Element actionExpression) throws ruleModelParserException, Exception, IOException, ContextManagerParserException{ 
		boolean condition = testCondition(actionExpression.element("condition")); 
		if(condition){					
			executeAction(actionExpression.element("then")); 
			return true;
		}else{
			for(Element elseIf : actionExpression.elements("elseIf")){ 
				condition = executeIf(elseIf);
				if(condition){
					return true;
				}
			}
			//0 or 1  
			for(Element elseElt : actionExpression.elements("else")){ 
				executeAction(elseElt);
				return true;
			}			
		}
		return false;
	}
	
	protected List<Element> getActions() throws Exception, ruleModelParserException, IOException, ContextManagerParserException{ 
		List<Element> actions = new ArrayList<Element>(); 
		//rules
	       XPath xpa = new DeafultXPath("//rule");	         
	       List<Element> rules = (List<Element>) xpa.selectNodes(rulesRoot);  
	       for(Element ruleElt : rules){   
	       	//important 
	       		Element ruleEltCopy = ruleElt.clone(); 
	       		new Document(ruleEltCopy); 
	       	//
	       	List<Element> conditions = ruleEltCopy.elements("condition");       
	       	
		         boolean conditionsOK = true;
		        //load conditions
		        for(Element conditionElt : conditions){   
		        	if(!testCondition(conditionElt)){
		        		conditionsOK = false;
		        		break;
		        	}
		        }
		        if(conditionsOK){
		        	//running actions
		        	xpa = new DefaultXPath("//action");	         
			        actions = (List<Element>) xpa.selectNodes(ruleEltCopy);			       
		        }
	       }
	       return actions;
	}
	
	public void executeRules() throws Exception, ruleModelParserException, IOException, ContextManagerParserException{
		List<Element> actions = getActions(); 
		for(Element action : actions){ 
        	executeAction(action);	 		       
        }     	
	}
	
	public void updateModel(String action) throws Exception, IOException, ruleModelParserException, ContextManagerParserException{
		action = "<rootElement>" + action + "</rootElement>";
		Document rulesModel= new SAXReader().read(new ByteArrayInputStream(action.getBytes()));	  
		executeAction(rulesModel.getRootElement()); 
	}
	
	public ContextManager getCm() {
		return cm;
	}
	
	public void printModel(String modelFilePath, AE_ModelId modelId) throws FileNotFoundException, IOException, Exception, ContextManagerParserException{
		XMLWriter sortie = new XMLWriter(OutputFormat.createPrettyFormat());		 
		sortie.output(getModel(modelId), new FileOutputStream(modelFilePath)); 
	}
	
	public String getModelAsXMLString(AE_ModelId modelId) throws Exception, IOException, ContextManagerParserException {
		XMLWriter sortie = new XMLWriter(OutputFormat.createPrettyFormat());		 
		Element modelElement = new Element("model");		 
		modelElement.addAttribute("id", modelId.getId()); 
		modelElement.add(getModel(modelId).clone()); 
		//String response = sortie.outputString(modelElement);
		String response = sortie.asXML();
		response += "\n";		
		return response;
	}
}
