package sjsu.cmpe202.visitors;

import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

@SuppressWarnings("rawtypes")
public class ClassVisitor extends VoidVisitorAdapter{
	
	private String extendStr;
	private String implementStr;
	private String ballNSocketStr;
	private String classNameStr;
	
	public ClassVisitor() {
		extendStr = "";
		implementStr = "";
		ballNSocketStr = "";
		classNameStr = "";
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object object) {
		
		classNameStr = n.getName() + " : ";
		
		ClassOrInterfaceType extendName;
		ClassOrInterfaceType interfaceName;
		List<ClassOrInterfaceType> extendList = n.getExtends();
		List<ClassOrInterfaceType> implementList = n.getImplements();
		Iterator<ClassOrInterfaceType> extendListIterator = extendList.iterator();
		Iterator<ClassOrInterfaceType> implementListIterator = implementList.iterator();
		
		/*
		 * plantUML
		 * extends className --|> extendedClassName
		 * implements className ..|> interfaceName
		 * interface INTERFACE <<interface>>
		 * ballNSocket className --() interfaceName
		 */
		// extend iterator
		while(extendListIterator.hasNext()){
			extendName = extendListIterator.next();
			extendStr += n.getName() + " --|> " + extendName.getName() + "\n";
		}
		
		// implements iterator
		while(implementListIterator.hasNext()){
			interfaceName = implementListIterator.next();
			implementStr += n.getName() + " ..|> " + interfaceName.getName() + "\n";
			implementStr += "interface " + interfaceName.getName() + " <<interface>> \n";
			ballNSocketStr += n.getName() + " --() " + interfaceName.getName() + "\n";
			ballNSocketStr += "interface " + interfaceName.getName() + "\n";
		}
	}

	public String getExtendStr() {
		return extendStr;
	}

	public String getImplementStr() {
		return implementStr;
	}

	public String getBallNSocketStr() {
		return ballNSocketStr;
	}

	public String getClassNameStr() {
		return classNameStr;
	}

}
