package sjsu.cmpe202.visitors;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import sjsu.cmpe202.accessModifier.AccessModifier;
import sjsu.cmpe202.model.InstanceVariable;

@SuppressWarnings("rawtypes")
public class VariableVisitor extends VoidVisitorAdapter {

	InstanceVariable instanceVariable;
	private List<InstanceVariable> instanceVariableList;

	public VariableVisitor() {
		instanceVariableList = new ArrayList<InstanceVariable>();
	}

	@Override
	public void visit(FieldDeclaration n, Object obj) {
		// private and public instance variables
		if (AccessModifier.getAccessModifier(n.getModifiers(), false) == '-'
				|| AccessModifier.getAccessModifier(n.getModifiers(), false) == '+') {
			instanceVariable = new InstanceVariable(AccessModifier.getAccessModifier(n.getModifiers(), false), n.getVariables().get(0).getId().toString(), n.getType().toString());
			instanceVariableList.add(instanceVariable);
		}
	}
	
	public List<InstanceVariable> getInstanceVariableList() {
		return instanceVariableList;
	}

}
