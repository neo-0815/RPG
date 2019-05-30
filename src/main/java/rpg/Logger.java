package rpg;

import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public static boolean isActive = true;
	
	private static void log(final PrintStream out, final String prefix, final String msg) {
		if(isActive) new SecurityManager() {
			{
				out.println("[" + OffsetDateTime.now().format(FORMATTER) + "] [" + getClassContext()[3].getCanonicalName() + "/" + prefix + "]: " + msg);
			}
		};
	}
	
	public static void info(final String msg) {
		log(System.out, "INFO", msg);
	}
	
	public static void debug(final String msg) {
		log(System.out, "DEBUG", msg);
	}
	
	public static void error(final String msg) {
		log(System.err, "ERROR", msg);
	}
	
	public static void error(final Exception e) {
		final String eMsg = e.getMessage();
		String msg = e.getClass().getCanonicalName() + (eMsg != null ? ": " + eMsg : "");
		
		for(final StackTraceElement st : e.getStackTrace())
			msg += "\n\t\tat " + st;
		
		log(System.err, "ERROR", msg);
	}
}