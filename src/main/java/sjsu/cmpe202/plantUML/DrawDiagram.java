package sjsu.cmpe202.plantUML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.plantuml.SourceStringReader;
import sjsu.cmpe202.constants.ConstantCollection;
import sjsu.cmpe202.model.InstanceVariable;
import sjsu.cmpe202.model.MethodSignature;

public class DrawDiagram {
	
	ConstantCollection constants = new ConstantCollection();
	
	public void drawClassDiagram(String source, File filePath, String fileName) {
		String appender = "@startuml\n";
		appender += "skinparam classAttributeIconSize 0 \n";
		source = appender + source;
		source += "@enduml\n";
		
		String imagePath = "";
		if(System.getProperty("os.name").equalsIgnoreCase("Windows")) { // Windows
			imagePath = filePath + "\\" + fileName + ".png";
		} else { // Linux and Mac
			imagePath = filePath + "/" + fileName + ".png";
		}
		
		try {
			OutputStream os = new FileOutputStream(imagePath);
			SourceStringReader reader = new SourceStringReader(source);
			reader.generateImage(os);
		} catch (Exception e) {
			System.out.println("Exception while drawing class diagram.");
		}
	}

	public String drawVariable(Map<String, List<InstanceVariable>> instanceVariableMap) {
		String instVarStr = "";
		Set<String> classNameSet = instanceVariableMap.keySet();
		List<InstanceVariable> instVarList;
		
		for(String className : classNameSet) {
			instVarList = instanceVariableMap.get(className);
			for(InstanceVariable inst : instVarList) {
				if(constants.isCollection(inst.getDataType())) { // collection
					List<String> baseTypeList = constants.getBaseType(inst.getDataType().toString());
					boolean retryFlag = false;
					for(String baseType : baseTypeList) {
						if(classNameSet.contains(baseType)) { // association attribute
							instVarStr = DrawAssociation.handleAssociation(instVarStr, className, inst, baseType, true);
						} else {
							if(!retryFlag) {
								instVarStr += className + " : " + inst.toString() + "\n";
								retryFlag = true;
							}
						}
					}
				} else { // variable
					if(classNameSet.contains(inst.getDataType())) {
						instVarStr = DrawAssociation.handleAssociation(instVarStr, className, inst, inst.getDataType(), false);
					} else {
						instVarStr += className + " : " + inst.toString() + "\n";
					}
				}
			}
			instVarStr += "hide " + className + " circle" + "\n";
		}
		return instVarStr;
	}

	public String getDependency(Map<String, List<MethodSignature>> methodParameterMap) {
		String dependencyStr = "";
		List<MethodSignature> methodSignatureList; 
		Set<String> classNameSet = methodParameterMap.keySet();
		
		for(String className : classNameSet) {
			methodSignatureList = methodParameterMap.get(className);
			for(MethodSignature methodParam : methodSignatureList) {
				if(constants.isCollection(methodParam.getDataType())) { //collection
					List<String> baseTypeList = constants.getBaseType(methodParam.getDataType().toString());
					for(String baseType : baseTypeList) {
						if(classNameSet.contains(baseType)) {
							if(!dependencyStr.contains(className + " ..> " + baseType + ":uses" + "\n")) {
								dependencyStr += className + " ..> " + baseType + ":uses" + "\n";
							}
						}
					}
				} else { // variable
					if(classNameSet.contains(methodParam.getDataType())) {
						if(!dependencyStr.contains(className + " ..> " + methodParam.getDataType() + ":uses" + "\n")) {
							dependencyStr += className + " ..> " + methodParam.getDataType() + ":uses" + "\n";
						}
					}
				}
			}
		}
		return dependencyStr;
	}
	
}
