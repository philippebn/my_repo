package fr.application.common.syslog;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

public class SyslogClient {

	
	
	
	
	
	
	public static void main (String[] args){
		// Set a Specific Host, then Log to It
		SyslogIF syslog = Syslog.getInstance("udp");

		syslog.getConfig().setHost("192.168.2.192");
		syslog.getConfig().setPort(514);

		for (int i = 0; i < 60; i++) {
			System.out.println("test " + i);
			syslog.info("info");
			syslog.warn("warning");
			syslog.alert("alert");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
