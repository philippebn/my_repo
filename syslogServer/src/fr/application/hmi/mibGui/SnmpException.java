package fr.application.hmi.mibGui;

/**
 * Gere les exceptions
 * @author Philippe
 *
 */
public class SnmpException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2582376536185297869L;

	public SnmpException() {
		super("SNMP Exception");
	}

	public SnmpException(String msg) {
		super(msg);
	}

	public SnmpException(Exception exception) {
		super(exception);
	}
}
