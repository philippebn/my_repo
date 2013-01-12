package fr.application.common.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
/**
 * Pinger with thread
 * @author Philippe
 *
 */
public class Pinger extends Thread {
	public boolean pingOk = true;
	public String host;

	public Pinger(String host) {
		this.host = host;
	}

	public void run() {
		try {
			pingOk = InetAddress.getByName(host).isReachable(150000); // 15000ms
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ArrayList<String> machines = new ArrayList<String>();

		String ipRange = "192.168.2.";

		for (int value = 0; value <= 255; value++) {
			//System.out.println(ipRange + value);
			machines.add(ipRange + value);
		}

		ArrayList<Pinger> pingers = new ArrayList<Pinger>();
		for (String host : machines) {
			pingers.add(new Pinger(host));
		}
		// démarrer tous les Thread
		for (Pinger p : pingers)
			p.start();
		// attendre la fin de tous les Thread
		for (Pinger p : pingers)
			try {
				p.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		// afficher le résultat de chaque Thread:
		for (Pinger p : pingers)
			if (p.pingOk) {
				System.out.println(p.host + "->" + p.pingOk);
			} else {
				System.err.println(p.host + "->" + p.pingOk);
			}
	}
}