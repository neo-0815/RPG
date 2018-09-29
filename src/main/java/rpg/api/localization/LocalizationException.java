package rpg.api.localization;

import java.util.ArrayList;

public class LocalizationException extends RuntimeException {
	private static final long	serialVersionUID	= 7639671735833895387L;
	private final String		baseMessage			= "Error in path '%path%'";
	
	private final int		line;
	private final String	path,
		file,
		specifier;
	private final Class<?>	specifierClass;
	
	public LocalizationException(String path, String file) {
		this(path, file, -1);
	}
	
	public LocalizationException(String path, String file, int line) {
		this(path, file, null, line);
	}
	
	public LocalizationException(String path, String file, String specifier) {
		this(path, file, specifier, -1);
	}
	
	public LocalizationException(String path, String file, String specifier, int line) {
		this(path, file, specifier, null, line);
	}
	
	public LocalizationException(String path, String file, String specifier, Class<?> specifierClass, int line) {
		this.path = path;
		this.file = file;
		this.specifier = specifier;
		this.specifierClass = specifierClass;
		this.line = line;
	}
	
	@Override
	public String getMessage() {
		String base = baseMessage.replace("%path%", path);
		final ArrayList<String> list = new ArrayList<>();
		
		if(file != null && !file.isEmpty()) list.add(getFieldValue("file"));
		if(line >= 0) list.add(getFieldValue("line"));
		if(specifier != null && !specifier.isEmpty()) if(specifierClass != null) base += " with wrong-matched [" + specifierClass + "]" + " specifier '" + specifier + "'";
		else base += " with unmatched specifier '" + specifier + "'";
		
		if( !list.isEmpty()) base += " (" + list.toString().substring(1).substring(0, list.toString().length() - 2) + ")";
		
		return base;
	}
	
	private String getFieldValue(String field) {
		switch(field.toLowerCase()) {
			case "file":
				return "File: '" + file + "'";
			case "line":
				return "Line: " + line;
			default:
				return "";
		}
	}
}
