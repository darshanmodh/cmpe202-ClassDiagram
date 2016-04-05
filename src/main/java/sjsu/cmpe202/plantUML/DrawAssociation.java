package sjsu.cmpe202.plantUML;

import java.util.regex.Pattern;

import sjsu.cmpe202.model.InstanceVariable;

public class DrawAssociation {

	public static String handleAssociation(String base, String class1, InstanceVariable instVar, String class2,
			boolean collectionFlag) {

		String newString;

		/*
		 * input1 = -- input2 = --"(* | 1, )" input3 = "(* | 1, )"-- input4 =
		 * "(* | 1, )" -- "(* | 1, )"
		 */
		String input1 = class1 + "\\s--\\s" + class2 + "\n";
		String input2 = class1 + "\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class2 + "\n";
		String input3 = class1 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s" + class2 + "\n";
		String input4 = class1 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class2 + "\n";

		String reverseInput1 = class2 + "\\s--\\s" + class1 + "\n";
		String reverseInput2 = class2 + "\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class1 + "\n";
		String reverseInput3 = class2 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s" + class1 + "\n";
		String reverseInput4 = class2 + "\"((\\*)?|(1)?(\\,\\s)?)+\"\\s--\\s\"((\\*)?|(1)?(\\,\\s)?)+\"" + class1
				+ "\n";

		int startPosition = -1;
		int endPosition;
		String toBeChecked;
		String toBeReplaced;

		// for inputX
		do {
			startPosition = base.indexOf(class1 + " ", startPosition + 1);
			if (startPosition >= 0) {
				endPosition = base.indexOf("\n", startPosition) + 1;

				toBeChecked = base.substring(startPosition, endPosition);
				if (Pattern.matches(input1, toBeChecked)) { // input1
					if (collectionFlag) {
						toBeReplaced = class1 + " -- \"*\"" + class2 + "\n";
					} else {
						toBeReplaced = class1 + " -- \"1\"" + class2 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(input2, toBeChecked)) { // input2
					if (collectionFlag) {
						toBeReplaced = class1 + " -- " + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  *\"" + class2 + "\n";
					} else {
						toBeReplaced = class1 + " -- " + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  1\"" + class2 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(input3, toBeChecked)) { // input3
					if (collectionFlag) {
						toBeReplaced = class1
								+ toBeChecked.substring(toBeChecked.indexOf("\""),
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1) + 1)
								+ " -- \"*\"" + class2 + "\n";
					} else {
						toBeReplaced = class1
								+ toBeChecked.substring(toBeChecked.indexOf("\""),
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1) + 1)
								+ " -- \"1\"" + class2 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(input4, toBeChecked)) { // input4
					if (collectionFlag) {
						toBeReplaced = class1
								+ toBeChecked.substring(toBeChecked.indexOf("\""),
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1) + 1)
								+ " -- \""
								+ toBeChecked.substring(toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1,
										toBeChecked.indexOf("\"",
												toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1))
								+ ",  *\"" + class2 + "\n";
					} else {
						toBeReplaced = class1
								+ toBeChecked.substring(toBeChecked.indexOf("\""),
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1) + 1)
								+ " -- \""
								+ toBeChecked.substring(toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1,
										toBeChecked.indexOf("\"",
												toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1))
								+ ",  1\"" + class2 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				}
			}
		} while (startPosition >= 0);

		// for reverseInputX
		startPosition = -1;
		do {
			startPosition = base.indexOf(class2 + " ", startPosition + 1);
			if (startPosition > 0) {
				endPosition = base.indexOf("\n", startPosition) + 1;
				toBeChecked = base.substring(startPosition, endPosition);

				if (Pattern.matches(reverseInput1, toBeChecked)) { // reverseInput1
					if (collectionFlag) {
						toBeReplaced = class2 + "\"*\" -- " + class1 + "\n";
					} else {
						toBeReplaced = class2 + "\"1\" -- " + class1 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(reverseInput2, toBeChecked)) { // reverseInput2
					if (collectionFlag) {
						toBeReplaced = class2 + "\"*\" -- \""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ "\"" + class1 + "\n";
					} else {
						toBeReplaced = class2 + "\"1\" -- \""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ "\"" + class1 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(reverseInput3, toBeChecked)) { // reverseInput3
					if (collectionFlag) {
						toBeReplaced = class2 + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  *\" -- " + class1 + "\n";
					} else {
						toBeReplaced = class2 + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  1\" -- " + class1 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				} else if (Pattern.matches(reverseInput4, toBeChecked)) { // reverseInput4
					if (collectionFlag) {
						toBeReplaced = class2 + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  *\" -- "
								+ toBeChecked.substring(toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")),
										toBeChecked.indexOf("\"",
												toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1) + 1)
								+ class1 + "\n";
					} else {
						toBeReplaced = class2 + "\""
								+ toBeChecked.substring(toBeChecked.indexOf("\"") + 1,
										toBeChecked.indexOf("\"", toBeChecked.indexOf("\"") + 1))
								+ ",  1\" -- "
								+ toBeChecked.substring(toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")),
										toBeChecked.indexOf("\"",
												toBeChecked.indexOf("\"", toBeChecked.indexOf(" -- ")) + 1) + 1)
								+ class1 + "\n";
					}
					newString = base.replace(toBeChecked, toBeReplaced);
					return newString;
				}
			}
		} while (startPosition >= 0);

		// remove duplicate dependency
		if(collectionFlag) {
			newString = base + class1 + " -- \"*\"" + class2 + "\n";
		} else {
			newString = base + class1 + " -- \"1\"" + class2 + "\n";
		}
		
		return newString;
	}

}
