package fr.application.common.log;

/**
 * Classe de log4j
 * @author Philippe
 */
public class LogClass {

	// private static Logger log;

	public static LogClass LogClass(String _name) {
		// log = Logger.getLogger(_name);

		/*
		 * Properties log4jProperties = new Properties();
		 * log4jProperties.setProperty("log4j.rootLogger",
		 * "DEBUG, ConsoleAppender");
		 * log4jProperties.setProperty("log4j.appender.ConsoleAppender",
		 * "org.apache.log4j.ConsoleAppender");
		 * log4jProperties.setProperty("log4j.appender.ConsoleAppender.layout",
		 * "org.apache.log4j.PatternLayout"); log4jProperties.setProperty(
		 * "log4j.appender.ConsoleAppender.layout.ConversionPattern",
		 * "[%d{ISO8601}]%5p%6.6r[%t]%x - %C.%M(%F:%L) - %m%n");//%-5p [%d{dd
		 * MMM yyyy HH:mm:ss}]: %c %x : %m%n
		 */
		// PropertyConfigurator.configure(log4jProperties);*/
		// PropertyConfigurator.configure("...../log4j.properties");
		return null;
	}

	public static void warn(String _message) {
		// log.warn(_message);
	}

	public static void info(String _message) {
		// log.info(_message);
	}

	public static void error(String _message) {
		// log.error(_message);
	}

	public static void error(String _message, Exception e) {
		// log.error(_message,e);
	}

	public static void debug(String _message) {
		// log.debug(_message);
	}

}