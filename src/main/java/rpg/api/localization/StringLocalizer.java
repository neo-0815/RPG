package rpg.api.localization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.IllegalFormatConversionException;
import java.util.LinkedHashMap;
import java.util.MissingFormatArgumentException;

public class StringLocalizer {
	private static final File langDir = getLangDir();
	private static final LinkedHashMap<String, LocalizationTable> tables = new LinkedHashMap<>();
	private static Locale activeLocale = Locale.getDefault();
	
	public static String localize(String path) {
		return format0(path, true);
	}
	
	public static String format(String path, Object... objects) {
		return format0(path, false, objects);
	}
	
	private static String format0(String path, boolean ignoreExceptions, Object... objects) {
		final LocalizationTable table = tables.get(activeLocale.getName());
		
		if(table.pathHasValue(path)) {
			final String value = table.getValue(path);
			
			try {
				return String.format(value, objects);
			}catch(final MissingFormatArgumentException | IllegalFormatConversionException e) {
				if(ignoreExceptions) return value;
				else if(e instanceof MissingFormatArgumentException) throw new LocalizationException(path, activeLocale.getFilename(), ((MissingFormatArgumentException) e).getFormatSpecifier(), table.getLineNumber(path));
				else if(e instanceof IllegalFormatConversionException) throw new LocalizationException(path, activeLocale.getFilename(), "%" + ((IllegalFormatConversionException) e).getConversion(), ((IllegalFormatConversionException) e).getArgumentClass(), table.getLineNumber(path));
			}catch(final ArrayIndexOutOfBoundsException e) {
				return "";
			}
		}
		
		return path;
	}
	
	public static void updateTables() {
		final File[] files = langDir.listFiles(getLangFileFilter());
		
		for(final File file : files)
			try {
				final BufferedReader reader = new BufferedReader(new FileReader(file));
				final LocalizationTable table = new LocalizationTable();
				
				String line = null;
				int lineNumber = 1;
				while((line = reader.readLine()) != null) {
					if(line.split("=").length > 1) table.setValueAndLineNumber(line.split("=")[0], line.split("=")[1], lineNumber);
					
					lineNumber++;
				}
				
				tables.put(file.getName().replace(".lang", ""), table);
				reader.close();
			} catch(final IOException e) {
				e.printStackTrace();
			}
	}
	
	private static File getLangDir() {
		final URL url = StringLocalizer.class.getClassLoader().getResource("lang");
		
		File file = null;
		try {
			file = new File(url.toURI());
		} catch(final URISyntaxException e) {
			file = new File(url.getPath());
		}
		
		return file;
	}
	
	private static FilenameFilter getLangFileFilter() {
		return new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".lang");
			}
		};
	}
	
	public static Locale getActiveLocale() {
		return activeLocale;
	}
	
	public static void setActiveLocale(Locale locale) {
		activeLocale = locale;
	}
	
	public static void resetActiveLocale() {
		activeLocale = Locale.getDefault();
	}
}
