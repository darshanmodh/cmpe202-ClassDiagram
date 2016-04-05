package sjsu.cmpe202.model;

import sjsu.cmpe202.constants.ConstantCollection;

public class MethodSignature {
	
	String name;
	String dataType;
	
	public MethodSignature() {
		super();
	}

	public MethodSignature(String name, String dataType) {
		super();
		this.name = name;
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return name + ":" + dataType;
	}
	
	public static String formatDataType(String dataType) {
		ConstantCollection constants = new ConstantCollection();
		if(constants.isCollection(dataType)) {
			String typeString = "(";
			
			if(constants.getBaseType(dataType).size() > 1) { // List with more than 1
				for(String baseType : constants.getBaseType(dataType)) {
					typeString += baseType + ", ";
				}
				return typeString.substring(0, typeString.lastIndexOf(",")) + ")(*)";
			} else { // single element in list
				return constants.getBaseType(dataType).get(0) + "(*)";
			}
		} else {
			return dataType;
		}
	}
	

}
