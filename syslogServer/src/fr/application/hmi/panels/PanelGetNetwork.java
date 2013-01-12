package fr.application.hmi.panels;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.application.common.ping.MyIp;

public class PanelGetNetwork extends JPanel {

	private static final long serialVersionUID = -4591413204644200493L;

	private JPanel panelGetNetwork;
	
	public JPanel getPanelGetNetwork() {

		panelGetNetwork = new JPanel();

		JLabel labelIpv4 = new JLabel(MyIp.getIPAddress());
		JLabel labelHostname = new JLabel(MyIp.getHostName());
		
		panelGetNetwork.add("center",new JLabel("Adresse ip:"));
		panelGetNetwork.add("center",labelIpv4);
		
		
		
		panelGetNetwork.add("center",new JLabel("Nom machine:"));
		panelGetNetwork.add("center",labelHostname);
		
		panelGetNetwork.add("center",new JLabel("OS name:"));
		panelGetNetwork.add("center",new JLabel(System.getProperty("os.name")));
		
		panelGetNetwork.add("center",new JLabel("Architecture:"));
		panelGetNetwork.add("center",new JLabel(System.getProperty("os.arch")));
		
		panelGetNetwork.add("center",new JLabel("OS version:"));
		panelGetNetwork.add("center",new JLabel(System.getProperty("os.version")));
		
		panelGetNetwork.add("center",new JLabel("Nom utilisateur:"));
		panelGetNetwork.add("center",new JLabel(System.getProperty("user.name")));
		
		panelGetNetwork.add("center",new JLabel("Language:"));
		panelGetNetwork.add("center",new JLabel(System.getProperty("user.language")));
		
		panelGetNetwork.setBorder(BorderFactory.createTitledBorder("Information Générale"));
		panelGetNetwork.setLayout(new GridLayout(0,2));

		return panelGetNetwork;
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.add(new PanelGetNetwork().getPanelGetNetwork());
		frame.pack();
		frame.setVisible(true);
		
		/*Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				SyslogServeur instLog = new SyslogServeur();
			}
		});
		thread.start();*/

	}
}
