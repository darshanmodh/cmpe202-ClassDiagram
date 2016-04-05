package sjsu.cmpe202.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class ConstantCollection {
	
	private HashMap<String, String> collectionType;
	
	public ConstantCollection() {
		
		/* RegEx = [\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*
		 * 
		 * Collection -> arrays, list, map, set, queue
		 * Arrays -> Arrays()
		 * List -> ArrayList, LinkedList
		 * Map -> HashMap, TreeMap, LinkedHashMap
		 * Set -> HashSet, TreeSet, LinkedHashSet
		 * Queue -> PriorityQueue, SynchronousQueue, DelayQueue
		 */
		
		collectionType = new HashMap<String, String>();
		collectionType.put("COLLECTION", "Collection<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		
		collectionType.put("ARRAY", "[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*(\\[\\])+[\\s]*");
		
		collectionType.put("LIST", "List<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("ALIST", "ArrayList<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("LLIST", "LinkedList<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		
		collectionType.put("MAP", "Map<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("HMAP", "HashMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("TMAP", "TreeMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("LHMAP", "LinkedHashMap<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*,[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		
		collectionType.put("SET", "Set<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("HSET", "HashSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("TSET", "TreeSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("LHSET", "LinkedHashSet<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		
		collectionType.put("QUEUE", "Queue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("PQUEUE", "PriorityQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("SQUEUE", "SynchronousQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
		collectionType.put("DQUEUE", "DelayQueue<[\\s]*[a-zA-Z_$][a-zA-Z0-9_$]*[\\s]*>");
	}
	
	public boolean isCollection(String dataType) {
		Iterator<String> collectionTypeIterator = collectionType.keySet().iterator();
		String pattern;
		
		while(collectionTypeIterator.hasNext()) {
			pattern = collectionType.get(collectionTypeIterator.next());
			if(Pattern.matches(pattern, dataType.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getBaseType(String dataType) {
		List<String> baseType = new ArrayList<String>();
		
		if(dataType.contains("[")) { //Array Object
			baseType.add(dataType.substring(0, dataType.indexOf("[")).trim());
		} else if(dataType.contains("<")) { //Collection Object, divide map and others
			if(dataType.contains(",")) { // Map Collection (K, V)
				baseType.add(dataType.substring(dataType.indexOf("<")+1, dataType.indexOf(",")).trim());
				baseType.add(dataType.substring(dataType.indexOf(",")+1, dataType.indexOf(">")).trim());
			} else { //Non-Map Collection
				baseType.add(dataType.substring(dataType.indexOf("<")+1, dataType.indexOf(">")).trim());
			}
		} else { // Simple dataType
			baseType.add(dataType);
		}
		return baseType;
	}
	
}
