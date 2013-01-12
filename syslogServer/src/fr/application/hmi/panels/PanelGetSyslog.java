package fr.application.hmi.panels;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.application.common.ping.MyIp;

public class PanelGetSyslog extends JPanel {

	private static final long serialVersionUID = -4591413204644200493L;

	private JPanel panelGetSyslog;
	public JLabel labelportsyslog = new JLabel();
	
	public JPanel getPanelGetNetwork() {

		panelGetSyslog = new JPanel();

		JLabel labelIpv4 = new JLabel(MyIp.getIPAddress());

		
		panelGetSyslog.add("center",new JLabel("Adresse ip:"));
		panelGetSyslog.add("center",labelIpv4);
		
		
		panelGetSyslog.add("center",new JLabel("Port d'écoute:"));
		panelGetSyslog.add("center",labelportsyslog);

		panelGetSyslog.setBorder(BorderFactory.createTitledBorder("Information Serveur Syslog"));
		panelGetSyslog.setLayout(new GridLayout(0,2));

		return panelGetSyslog;
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new PanelGetSyslog().getPanelGetNetwork());
		frame.pack();
		frame.setVisible(true);

	}
}
