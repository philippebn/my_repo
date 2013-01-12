package fr.application.common.syslog;

import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.SyslogServerEventHandlerIF;
import org.productivity.java.syslog4j.server.impl.event.printstream.SystemOutSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;

public class SyslogServeur {

	public static void main(String[] args) {

		SyslogServerEventHandlerIF eventHandler = new SystemOutSyslogServerEventHandler();
		TCPNetSyslogServerConfig config = new TCPNetSyslogServerConfig();
		// ou bien UDPNetSyslogServerConfig selon votre besoin

		config.setPort(514);
		config.addEventHandler(eventHandler);

		SyslogServer.createThreadedInstance("audit", config);

		while (true) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
