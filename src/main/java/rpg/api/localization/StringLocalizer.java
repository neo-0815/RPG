package rpg.api.localization;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.IllegalFormatConversionException;
import java.util.MissingFormatArgumentException;

import rpg.api.filereading.RPGFileReader;

/**
 * The class StringLocalizer used to localize {@link String}s.
 *
 * @author Neo Hornberger
 */
public class StringLocalizer {
	private static final File langDir = getLangDir();
	
	private static LocalizationTable activeTable = new LocalizationTable();
	private static Locale activeLocale = Locale.getDefault();
	
	/**
	 * Localizes the path 'path'.
	 *
	 * @param path
	 *            the path which will be localized
	 * @return the localized value of the path 'path'
	 */
	public static String localize(final String path) {
		return format0(path, true);
	}
	
	/**
	 * Localizes the path 'path' and formats its value.
	 *
	 * @param path
	 *            the path which will be localized and formated
	 * @param objects
	 *            the arguments referenced by the format specifiers in the
	 *            format string. If there are more arguments than format
	 *            specifiers, the extra arguments are ignored. The number of
	 *            arguments is variable and may be zero. The behaviour on a
	 *            {@code null} argument depends on the
	 *            <a href="../util/Formatter.html#syntax">conversion</a>
	 * @return the localized and formated value of the path 'path'
	 */
	public static String format(final String path, final Object... objects) {
		return format0(path, false, objects);
	}
	
	/**
	 * <h4>Does the whole magic!!</h4> Localizes the path 'path' and formats its
	 * value. This method is only for internal usage.
	 *
	 * @param path
	 *            the path which will be localized and formated
	 * @param ignoreExceptions
	 *            if {@code true} the method won't throw any
	 *            {@link LocalizationException}
	 * @param objects
	 *            the arguments referenced by the format specifiers in the
	 *            format string. If there are more arguments than format
	 *            specifiers, the extra arguments are ignored. The number of
	 *            arguments is variable and may be zero. The behaviour on a
	 *            {@code null} argument depends on the
	 *            <a href="../util/Formatter.html#syntax">conversion</a>
	 * @return the localized and formated value of the path 'path'
	 */
	private static String format0(final String path, final boolean ignoreExceptions, final Object... objects) {
		if(activeTable.pathHasValue(path)) {
			final String value = activeTable.getValue(path);
			
			try {
				return String.format(value, objects);
			}catch(final MissingFormatArgumentException | IllegalFormatConversionException e) {
				if(ignoreExceptions) return value;
				else if(e instanceof MissingFormatArgumentException) throw new LocalizationException(path, activeLocale.getFilename(), ((MissingFormatArgumentException) e).getFormatSpecifier(), activeTable.getLineNumber(path));
				else if(e instanceof IllegalFormatConversionException) throw new LocalizationException(path, activeLocale.getFilename(), "%" + ((IllegalFormatConversionException) e).getConversion(), ((IllegalFormatConversionException) e).getArgumentClass(), activeTable.getLineNumber(path));
			}catch(final ArrayIndexOutOfBoundsException e) {
				return "";
			}
		}
		
		return path;
	}
	
	/**
	 * Updates the {@link LocalizationTable}.
	 */
	public static void updateTable() {
		RPGFileReader.readLineSplit(new File(langDir, activeLocale.getFilename()), "=", (key, value, lineNumber) -> activeTable.setValueAndLineNumber(key, value, lineNumber));
	}
	
	/**
	 * Gets the directory where the language files are located.
	 *
	 * @return the directory where the language files are located
	 */
	private static File getLangDir() {
		final URL url = StringLocalizer.class.getResource("/assets/lang/");
		
		File file = null;
		try {
			file = new File(url.toURI());
		}catch(final URISyntaxException e) {
			file = new File(url.getPath());
		}
		
		return file;
	}
	
	/**
	 * Gets the currently active {@link Locale}.
	 *
	 * @return the currently active {@link Locale}
	 */
	public static Locale getActiveLocale() {
		return activeLocale;
	}
	
	/**
	 * Sets the currently active {@link Locale}.
	 */
	public static void setActiveLocale(final Locale locale) {
		activeLocale = locale;
		
		updateTable();
	}
	
	/**
	 * Resets the currently active {@link Locale} to the default {@link Locale}.
	 *
	 * @see Locale#getDefault()
	 */
	public static void resetActiveLocale() {
		setActiveLocale(Locale.getDefault());
	}
}
