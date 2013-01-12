package fr.application.common.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * 
 * @author Guillaume
 *
 */
public class PingCmd {
	
	// tableau des machines trouvé
	private Vector<String[]> lanIParrList;
	
	// status de la commande
	@SuppressWarnings("unused")
	private boolean status = false;
	
	// cmd de la net view
	private String cmdNetView = "net view";
	
	// cmd du ping
	private String cmdPing = "ping -4 -n 1 ";
	
	@SuppressWarnings("unused")
	private String resultat = null;
	
	public PingCmd() {
		lanIParrList = new Vector<String[]>();

		try {
			// lancement de la requete cmd
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmdNetView);

			BufferedReader r1 = new BufferedReader(
								new InputStreamReader(
										p.getInputStream()));
					String x;
					String z;
					String y;
					
			StringBuffer sb = new StringBuffer();
			while ((x = r1.readLine()) != null) {
				sb.append(x);
				x = x.replace("‚", "é");
				x = x.replace("¥", "Ñ");
				x = x.replace("š", "Ü");
				System.out.println(" ");
				System.out.println(x);

				if (x.startsWith("\\") == true) {
					y = x.substring(2);
					String[] ytab = y.split(" ");
					Runtime rb = Runtime.getRuntime();
					Process pb = rb.exec(cmdPing + ytab[0]);

					BufferedReader r2 = new BufferedReader(
							new InputStreamReader(pb.getInputStream()));
					StringBuffer sb2 = new StringBuffer();
					while ((z = r2.readLine()) != null) {
						sb2.append(z);
						// z = z.substring(2);
						// System.out.println (z);
					}
					String temp = sb2.toString();
					if (!temp.contains("essayez")) {
						temp = temp.substring(31);
						String[] tab = temp.split("]");
						String strtab = tab[0].toString();
						String[] tab1 = strtab.split(" ");
						String ip = tab1[1].substring(1);
						System.out.println(ip);

						/* On place les adresses ip dans un tableau dynamique */
						lanIParrList.add(new String[] {ip, tab1[0]});

					} else
						System.out.println("Résolution IP impossible!");
					r2.close();
					pb.waitFor();

				}
			}
			resultat = sb.toString();
			r1.close();
			p.waitFor();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//String pouet = "correctement";// "us = 4,";
		// System.out.println(pouet);
		//status = resultat.contains(pouet);
		// System.out.println(status);
	}
	
	
	public Vector<String[]> getTabMachine() {
		return lanIParrList;
	}
	
	
	public static void main(final String[] args) {
		PingCmd obj_ping = new PingCmd();
		
		Vector<String[]> machines = obj_ping.getTabMachine();
		
		for (String[] obj : machines) {

				System.out.println(obj.toString());
		
		}

	}
}
