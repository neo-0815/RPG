package rpg.api.localization;

public class Locale {
	public static final Locale NONE = createLocale("", "");
	
	public static final Locale	AMERICAN_ENGLISH	= createLocale("en", "US");
	public static final Locale	BRITISH_ENGLISH		= createLocale("en", "GB");
	public static final Locale	GERMAN				= createLocale("de", "DE");
	
	private final String language,
		country;
	
	private Locale(String language, String country) {
		this.language = language.toLowerCase();
		this.country = country.toUpperCase();
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getName() {
		if(this == NONE) return null;
		
		return language + "_" + country;
	}
	
	public String getFilename() {
		if(this == NONE) return null;
		
		return getName() + ".lang";
	}
	
	public static Locale getDefault() {
		return AMERICAN_ENGLISH;
	}
	
	private static Locale createLocale(String language, String country) {
		return new Locale(language, country);
	}
}
