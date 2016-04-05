package sjsu.cmpe202.model;

import sjsu.cmpe202.constants.ConstantCollection;

public class InstanceVariable {

	String name;
	String dataType;
	char accessType;

	public InstanceVariable() {
		this.name = "";
		this.dataType = "";
		this.accessType = ' ';
	}

	public InstanceVariable(char accessType, String name, String dataType) {
		this.name = name;
		this.dataType = dataType;
		this.accessType = accessType;
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

	public char getAccessType() {
		return accessType;
	}

	public void setAccessType(char accessType) {
		this.accessType = accessType;
	}
	
	@Override
	public String toString() {
		ConstantCollection constants = new ConstantCollection();
		if(constants.isCollection(dataType)) {
			String typeString = "(";
			if(constants.getBaseType(dataType).size() > 1) {
				for(String baseType : constants.getBaseType(dataType)) {
					typeString += baseType + ", ";
				}
				return accessType + "" + name + ":" + typeString.substring(0, typeString.lastIndexOf(",")) + ")(*)";
			} else {
				return accessType + "" +  name + ":" + constants.getBaseType(dataType).get(0) + "(*)";
			}
		} else {
			return accessType + "" + name + ":" + dataType;
		}
	}
	
	public String getFirstCap() {
		return (""+name.charAt(0)).toUpperCase() + name.substring(1, name.length());
	}

}
