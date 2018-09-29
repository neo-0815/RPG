package rpg.api.localization;

import java.util.LinkedHashMap;

public class LocalizationTable {
	private final LinkedHashMap<String, String> values = new LinkedHashMap<>();
	private final LinkedHashMap<String, Integer> lineNumbers = new LinkedHashMap<>();
	
	public String getValue(String path) {
		if(!pathHasValue(path)) return null;
		
		return values.get(path);
	}
	
	public int getLineNumber(String path) {
		if(!pathHasValue(path)) return -1;
		
		return lineNumbers.get(path);
	}
	
	public boolean pathHasValue(String path) {
		return values.containsKey(path);
	}
	
	protected void setValueAndLineNumber(String path, String value, int lineNumber) {
		values.put(path, value);
		lineNumbers.put(path, lineNumber);
	}
}
