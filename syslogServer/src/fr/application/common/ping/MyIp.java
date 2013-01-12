package fr.application.common.ping;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.Logger;

public class MyIp {
	
	private static Logger log = Logger.getLogger(MyIp.class);
	
	public static String getHostName() {

			String hostName;
			InetAddress addrs[] = null;
			try {
				hostName = InetAddress.getLocalHost().getHostName();
				addrs = InetAddress.getAllByName(hostName);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			String returnHostName = "UNKNOWN";
			for (InetAddress addr : addrs) {
				if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
					returnHostName = addr.getHostName();
				}
			}
			return returnHostName;
	}
	

	public static String getIpv4() {

		String hostName;
		InetAddress addrs[] = null;

		try {
			hostName = InetAddress.getLocalHost().getHostName();
			addrs = InetAddress.getAllByName(hostName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log.error(e.toString());
		}

		String myIp = "UNKNOWN";
		for (InetAddress addr : addrs) {

			log.debug("addr.getHostAddress() = " + addr.getHostAddress());
			log.debug("addr.getHostName() = " + addr.getHostName());
			System.out.println("addr.getHostAddress() = " + addr.getHostAddress());
			System.out.println("addr.getHostName() = " + addr.getHostName());
			System.out.println("addr.isAnyLocalAddress() = " + addr.isAnyLocalAddress());
			System.out.println("addr.isLinkLocalAddress() = " + addr.isLinkLocalAddress());
			System.out.println("addr.isLoopbackAddress() = " + addr.isLoopbackAddress());
			System.out.println("addr.isMulticastAddress() = " + addr.isMulticastAddress());
			System.out.println("addr.isSiteLocalAddress() = " + addr.isSiteLocalAddress());

			if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
				myIp = addr.getHostAddress();
				log.info(myIp);
			}
		}
		// System.out.println("\nIP = " + myIp);
		return myIp;
	}	

	/**
	 * Recupere adresse ip de la machine hote
	 * @return String ip de la machine hote 
	 */
	public static String getIPAddress() {
		Enumeration<?> nicList;
		NetworkInterface nic;
		Enumeration<?> nicAddrList;
		InetAddress nicAddr;
		try {
			nicList = NetworkInterface.getNetworkInterfaces();
			while (nicList.hasMoreElements()) {
				nic = (NetworkInterface) nicList.nextElement();
				if (!nic.isLoopback() && nic.isUp()) {
					nicAddrList = nic.getInetAddresses();
					while (nicAddrList.hasMoreElements()) {
						nicAddr = (InetAddress) nicAddrList.nextElement();
						try {
							@SuppressWarnings("unused")
							Inet4Address nicAddrIPv4 = (Inet4Address) nicAddr;
							return nicAddr.getHostAddress();
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (SocketException e1) {
			System.out.println("SocketException handled in Networking.getIPAddress!.");
			e1.printStackTrace();
		}
		return "";
	}

	/**
	 * Main de test
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(getIpv4());
		System.out.println(getIPAddress());
		//System.out.println(checkHosts());
	}
	

	/**
	 * Check host
	 * @return ArrayList<String> machines du reseau
	 */
	public static ArrayList<String> checkHosts(){
		ArrayList<String> ips = new ArrayList<String>();
		   int timeout=1000;
		   //for (int i=234;i<254;i++){
		       String host="www.google.fr";//"192.168.2" + "." + i;
		       System.out.println("test " + host);
		       try {
				if (InetAddress.getByName(host).isReachable(timeout)){
				       System.out.println(host + " est connecté");
				       ips.add(host + " est connecté");
				   }
			} catch (UnknownHostException e) {
				 System.out.println("fuck");
			} catch (IOException e) {
				System.out.println("fuck2");
			}
		return ips;
		}

}
