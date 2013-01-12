package fr.application.hmi.config;

/**
 * Version du programme.
 * @author Philippe BENAOUN
 */
public class Version {
	private static final String NAME = "Team Supervisor";
	private static final String NAMETRIM = "TeamSupervisor";
	private static final String COPYLEFT = "© 2012 BBF Network";
	private static final String VERSION = "1.0";
	private static final String MAILTO = "support@bbfnetwork.com";

	
	public static String getName() {
		return NAME;
	}
	
	public static String getNameTrim() {
		return NAMETRIM;
	}
	
	public static String getVersion() {
		return VERSION;
	}

	public static String getVersionFull() {
		return NAME + " " + VERSION;
	}

	@SuppressWarnings("unused")
	private static String getCopyLeft() {
		return COPYLEFT;
	}

	public static String getMailTo() {
		return MAILTO;
	}
}
