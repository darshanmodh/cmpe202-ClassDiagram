package sjsu.cmpe202.visitors;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import sjsu.cmpe202.accessModifier.AccessModifier;
import sjsu.cmpe202.model.MethodSignature;

@SuppressWarnings("rawtypes")
public class ConstructorVisitor extends VoidVisitorAdapter {

	private MethodSignature constructorParameter;
	private List<MethodSignature> constructorParameterList;
	private List<String> constructorStrList;

	public ConstructorVisitor() {
		constructorParameterList = new ArrayList<MethodSignature>();
		constructorStrList = new ArrayList<String>();
	}

	public void visit(ConstructorDeclaration n, Object object) {
		String constructorName = "";
		constructorName += AccessModifier.getAccessModifier(n.getModifiers(), true) + " " + n.getName() + "(";
		List<Parameter> params = n.getParameters();
		for (Parameter param : params) {
			constructorName += param.getId() + ":" + MethodSignature.formatDataType(param.getType().toString()) + ",";
			constructorParameter = new MethodSignature(param.getId().toString(), param.getType().toString());
			constructorParameterList.add(constructorParameter);
		}

		if (constructorName.lastIndexOf(",") > 0) { // handle last param
			constructorName = constructorName.substring(0, constructorName.lastIndexOf(","));
			constructorName += ")";
		} else {
			constructorName += ")";
		}
		constructorStrList.add(constructorName);
	}

	public List<MethodSignature> getConstructorParameterList() {
		return constructorParameterList;
	}

	public List<String> getConstructorStrList() {
		return constructorStrList;
	}

}
