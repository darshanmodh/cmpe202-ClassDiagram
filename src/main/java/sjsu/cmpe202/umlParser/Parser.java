package sjsu.cmpe202.umlParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Character.Subset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

import sjsu.cmpe202.constants.ConstantCollection;
import sjsu.cmpe202.model.InstanceVariable;
import sjsu.cmpe202.model.MethodSignature;
import sjsu.cmpe202.plantUML.DrawDiagram;
import sjsu.cmpe202.visitors.ClassVisitor;
import sjsu.cmpe202.visitors.ConstructorVisitor;
import sjsu.cmpe202.visitors.MethodVisitor;
import sjsu.cmpe202.visitors.VariableVisitor;

@SuppressWarnings("unused")
public class Parser {
	private static String os;
	private static HashMap<String, List<InstanceVariable>> instanceVariableHashMap;
	private static HashMap<String, List<MethodSignature>> methodSignatureHashMap;
	private String extendStr = "";
	private String implementStr = "";
	private String ballNSocketStr = "";
	ConstantCollection constants = new ConstantCollection();

	public Parser() {
		os = System.getProperty("os.name");
		extendStr = "";
		implementStr = "";
		ballNSocketStr = "";
		instanceVariableHashMap = new HashMap<String, List<InstanceVariable>>();
		methodSignatureHashMap = new HashMap<String, List<MethodSignature>>();
	}

	public static void main(String[] args) {
		Parser parser = new Parser();
		DrawDiagram drawDiagram = new DrawDiagram();
		String source = "";
		boolean javaFilesFound = false;

		if (args.length == 2) {
			File dir = new File(args[0]);
			String[] files = dir.list();
			String basePath = dir.getAbsolutePath();
			if (files.length > 0) {
				System.out.println("*** Parsing started on Java Files ***");
				for (int i = 0; i < files.length; i++) {
					if (files[i].endsWith(".java")) {
						if (os.equalsIgnoreCase("windows")) {
							source += parser.parseJavaFile(basePath + "\\" + files[i], args[1].contains("ballnsocket") ? true : false );
						} else {
							source += parser.parseJavaFile(basePath + "/" + files[i], args[1].contains("ballnsocket") ? true : false );
						}
						javaFilesFound = true;
					}
				}
				System.out.println("*** Java Parsing is done ***");

				// java files found
				if (javaFilesFound) {
					System.out.println("*** Class Diagram is generating ***");
					source += drawDiagram.drawVariable(instanceVariableHashMap);
					source += drawDiagram.getDependency(methodSignatureHashMap);
					drawDiagram.drawClassDiagram(source, dir, args[1]);
					System.out.println("*** Class Diagram is completed at " + dir + "/" + args[1] + " ***");
				} else { // no java files
					System.out.println("*** No Java files found in folder ***");
				}
			} else {
				System.out.println("No files in directory!!!");
			}
		} else {
			System.out.println("Incorrect number of inputs!!!");
		}
	}

	@SuppressWarnings("unchecked")
	private String parseJavaFile(String name, boolean ballNSocketFlag) {
		String classNameStr;
		String methodStr;
		FileInputStream in;
		CompilationUnit cu;

		try {
			in = new FileInputStream(name);
			try {
				cu = JavaParser.parse(in);
			} finally {
				in.close();
			}

			// Original TRY of FileInputStream
			// ClassVisitor -> visit, extend, implement, ballNSocket
			ClassVisitor classVisitor = new ClassVisitor();
			classVisitor.visit(cu, null);
			classNameStr = classVisitor.getClassNameStr();
			extendStr = classVisitor.getExtendStr();
			implementStr = classVisitor.getImplementStr();
			ballNSocketStr = classVisitor.getBallNSocketStr();

			// VariableVisitor -> constructor, getter and visit
			VariableVisitor variableVisitor = new VariableVisitor();
			variableVisitor.visit(cu, null);
			instanceVariableHashMap.put(classNameStr.substring(0, classNameStr.indexOf(" ")),
					variableVisitor.getInstanceVariableList());

			// ConstructorVisitor -> visit(name and param), constructors and
			// getters
			ConstructorVisitor constructorVisitor = new ConstructorVisitor();
			constructorVisitor.visit(cu, null);
			methodStr = getConstructorName(classNameStr, constructorVisitor.getConstructorStrList());

			// MethodVisitor -> visit -> accessModifier, return type, name,
			// arguments
			MethodVisitor methodVisitor = new MethodVisitor();
			methodVisitor.visit(cu, null);
			methodStr += getMethodName(classNameStr, methodVisitor.getMethodStringList());
			
			constructorVisitor.getConstructorParameterList().addAll(methodVisitor.getMethodParamList());
			methodSignatureHashMap.put(classNameStr.substring(0, classNameStr.indexOf(" ")), constructorVisitor.getConstructorParameterList());

			if (ballNSocketFlag)
				return extendStr + ballNSocketStr + methodStr;
			else 
				return extendStr + implementStr + methodStr;

			// End of Original TRY of FileInputStream
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Java parsing errors");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getConstructorName(String classNameStr, List<String> constructorNameList) {
		String constructorName = "";
		for (String constructor : constructorNameList) {
			constructorName += classNameStr + constructor + "\n";
		}
		return constructorName;
	}

	private String getMethodName(String classNameStr, List<String> methodNameList) {
		String methodName = "";
		Set<String> methodNameSet = new HashSet<String>();

		for (String method : methodNameList) {
			methodNameSet.add(method.substring(method.indexOf(" ") + 1, method.indexOf("(")));
		}

		// resolve getter and setter property
		for (InstanceVariable instVar : instanceVariableHashMap
				.get(classNameStr.substring(0, classNameStr.indexOf(" :")))) {
			if (methodNameSet.contains("get" + instVar.getFirstCap())
					&& methodNameSet.contains("set" + instVar.getFirstCap())) {
				instVar.setAccessType('+'); // make variable public
				methodNameSet.remove("get" + instVar.getFirstCap()); // remove
																		// getter
																		// method
				methodNameSet.remove("set" + instVar.getFirstCap()); // remove
																		// setter
																		// method
			}
		}

		for (String method : methodNameList) {
			if (methodNameSet.contains(method.substring(method.indexOf(" ") + 1, method.indexOf("(")))) {
				methodName += classNameStr + method + "\n";
			}
		}
		return methodName;
	}
}
