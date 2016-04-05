package sjsu.cmpe202.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import sjsu.cmpe202.accessModifier.AccessModifier;
import sjsu.cmpe202.model.MethodSignature;

@SuppressWarnings("rawtypes")
public class MethodVisitor extends VoidVisitorAdapter {

	private MethodSignature methodParam;
	private List<String> methodStringList;
	private List<MethodSignature> methodParamList;

	public MethodVisitor() {
		methodStringList = new ArrayList<String>();
		methodParamList = new ArrayList<MethodSignature>();
	}

	public void visit(MethodDeclaration n, Object obj) {

		String methodString = "";
		boolean publicMethodFlag = false;

		// only public method
		if (AccessModifier.getAccessModifier(n.getModifiers(), true) == '+') {
			if (ModifierSet.isStatic(n.getModifiers())) {
				methodString += "{static}";
			}
			if (ModifierSet.isAbstract(n.getModifiers())) {
				methodString += "{abstract}";
			}
			methodString += AccessModifier.getAccessModifier(n.getModifiers(), true) + " " + n.getName() + "(";

			publicMethodFlag = true;
			if (n.getBody() != null) {
				methodParamList.addAll(getClassUsedInBody(n.getBody().toString()));
			}
		}

		List<Parameter> params = n.getParameters();
		for (Parameter param : params) {
			if (publicMethodFlag) {
				methodString += param.getId() + ":" + MethodSignature.formatDataType(param.getType().toString()) + ",";
			}
			methodParam = new MethodSignature(param.getId().toString(), param.getType().toString());
			methodParamList.add(methodParam);
		}

		if (publicMethodFlag) {
			if (methodString.lastIndexOf(",") > 0) {
				methodString = methodString.substring(0, methodString.lastIndexOf(","));
				methodString += "):" + MethodSignature.formatDataType(n.getType().toString());
			} else {
				methodString += "):" + MethodSignature.formatDataType(n.getType().toString());
			}
			methodStringList.add(methodString);
		}
	}

	public List<MethodSignature> getClassUsedInBody(String methodBody) {
		String pattern = "[a-zA-Z_$][a-zA-Z0-9_$]*(\\[\\])?\\s(([a-zA-Z_$][a-zA-Z0-9_$]*)(\\,)?)+(=|\\s=|;)";
		String statement = "";
		boolean assignmentFlag;
		MethodSignature methodLocalParam;
		List<MethodSignature> methodLocalParamList = new ArrayList<MethodSignature>();
		int startPosition = 1;
		int endPosition;
		do {
			endPosition = methodBody.indexOf(";", startPosition + 1) + 1;

			if (endPosition > startPosition) {
				assignmentFlag = false;
				statement = methodBody.substring(startPosition, endPosition);
				if (statement != null && statement.length() > 2) {
					if (statement.contains("=")) {
						statement = methodBody.substring(startPosition, methodBody.indexOf("=", startPosition + 1) + 1);
						assignmentFlag = true;
					}
					statement = statement.trim();
					if (Pattern.matches(pattern, statement)) {
						if (assignmentFlag) {
							methodLocalParam = new MethodSignature(
									statement.substring(statement.indexOf(" ") + 1, statement.indexOf("=")).trim(),
									statement.substring(0, statement.indexOf(" ")));
						} else {
							methodLocalParam = new MethodSignature(
									statement.substring(statement.indexOf(" ") + 1, statement.indexOf(";")).trim(),
									statement.substring(0, statement.indexOf(" ")));
						}
						methodParamList.add(methodLocalParam);
					}
				}
				startPosition = endPosition + 1;
			} else { // startPosition reached endPosition
				break;
			}
		} while (startPosition >= 0 && startPosition < methodBody.length() - 1);
		return methodLocalParamList;
	}

	public List<String> getMethodStringList() {
		return methodStringList;
	}

	public List<MethodSignature> getMethodParamList() {
		return methodParamList;
	}

}
