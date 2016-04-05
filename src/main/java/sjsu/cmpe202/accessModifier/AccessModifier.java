package sjsu.cmpe202.accessModifier;

import com.github.javaparser.ast.body.ModifierSet;

public class AccessModifier {

	public static char getAccessModifier(int code, boolean isMethod) {

		switch (code) {
			case ModifierSet.PRIVATE:
				return '-';
			case ModifierSet.PUBLIC:
				return '+';
			case ModifierSet.PROTECTED:
				return '#';
			default:
				if(isMethod) {
					if(ModifierSet.isPublic(code)) {
						return '+';
					} else {
						return '~';
					}
				} else {
					return '~';
				}
		} // end of switch

	}

}
