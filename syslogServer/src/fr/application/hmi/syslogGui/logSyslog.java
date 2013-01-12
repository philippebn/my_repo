package fr.application.hmi.syslogGui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class logSyslog implements Runnable {

	private PanelSyslog theUI;
	private int localPort = 8514;
	private boolean stopRequested;
	private DatagramSocket socket;
	private Thread runThread;

	public void run() {
		runThread = Thread.currentThread();
		stopRequested = false;
		try {
			socket = new DatagramSocket(localPort);
			theUI.addLog("Service log started on port " + localPort);
		} catch (SocketException e) {
			theUI.addLog("Can't start listening: " + e.getMessage());
		}
		try {
			while (!stopRequested) {
				DatagramPacket dato = new DatagramPacket(new byte[2048], 2048);
				socket.receive(dato);
				String msg = new String(dato.getData(), 0, dato.getLength());
				theUI.addLog(msg);
			}

		} catch (IOException e) {
			theUI.addLog(e.getMessage());
		}

	}

	public void init_thread(PanelSyslog pUI, int pPort) {
		theUI = pUI;
		localPort = pPort;

	}

	public void stopRequest() {
		stopRequested = true;

		if (runThread != null) {
			socket.close();
			runThread.interrupt();
		}
	}
}
