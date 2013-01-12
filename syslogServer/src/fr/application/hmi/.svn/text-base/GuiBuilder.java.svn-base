package fr.application.hmi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import fr.application.common.log.LogClass;
import fr.application.hmi.config.Version;
import fr.application.hmi.events.AboutButton;
import fr.application.hmi.events.WindowClose;
import fr.application.hmi.mibGui.DSnmpMibTreeGUI;
import fr.application.hmi.panels.PanelGetNetwork;
import fr.application.hmi.panels.PanelTableauIP;
import fr.application.hmi.panels.appender.TextAreaAppender;
import fr.application.hmi.syslogGui.PanelSyslog;

/**
 * Classe point d'entre
 * @author Philippe
 *
 */
public class GuiBuilder implements WindowListener {
	
	static Logger log = Logger.getLogger(GuiBuilder.class);
	//objet fenetre
	private JFrame fenetre;
	//thread du syslog
	//private Thread threadSyslog;

	//point d'entre application
	public static void main(String[] args) {
		//creation d'un thread pour l'ihm
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//on creer la fenetre
					GuiBuilder window = new GuiBuilder();
					//on lance un evenement
					log.info("lancement de l'application Network Supervisor");					
					//on l'affiche
					window.fenetre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					LogClass.error(e.toString(), e);
				}
			}
		});
	}

	/**
	 * Creation de l'application.
	 */
	public GuiBuilder() {
		initializeFrame();
		//initializeSyslogSrv();
	}

	/**
	 * Initialisation.
	 */
	private void initializeFrame() {
		
		try {
			//redessine la fenetre avec style windows
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//set de la frame
		fenetre = new JFrame(Version.getVersionFull());
		//fenetre.setLocationRelativeTo(null);
		fenetre.setSize(new Dimension(800, 600));
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.getContentPane().setLayout(new BorderLayout());
		
		//chargement du logo image
		BufferedImage image = null;
		try {
			image = ImageIO.read(
						fenetre.getClass().getResource(
						"/fr/application/ressources/bbf_network_black.png"));
			fenetre.setIconImage(image);
			log.info("chargement de l'image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//Creation du panel general qui cree la fenetre
		JPanel panelFenetre = new JPanel();
		fenetre.getContentPane().add(panelFenetre);
		panelFenetre.setLayout(new BorderLayout());

		
		//Creation du panel Gauche
		JPanel panelGauche = new JPanel();
		//panelGauche.setPreferredSize(new Dimension(300, 300));
		panelGauche.add(new PanelGetNetwork().getPanelGetNetwork());
		//panelGauche.add(new PanelButton().getPanelButton());
		panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
		//panelGauche.setLayout(new BorderLayout());
		panelFenetre.add(panelGauche, BorderLayout.NORTH);
		
		
		
		//Creation de la vue onglet
		JPanel panelOngletGeneral = new JPanel();
		panelFenetre.add(panelOngletGeneral, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		
		//ajout onglet dev network
		tabbedPane.addTab("Découverte réseau", null, new PanelTableauIP(), null);
		
		
		//Creation de l'onglet 1
		Panel panelOnglet1 = new Panel();
		tabbedPane.addTab("Observateur d'évenement", null, panelOnglet1, null);
		panelOnglet1.setLayout(new BorderLayout());
		JTextArea textArea = new JTextArea();
		TextAreaAppender.setTextArea(textArea);
		panelOnglet1.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		
		//Creation de l'onglet 2
		JPanel panelOnglet2 = new JPanel();
		tabbedPane.addTab("Snmp", null, panelOnglet2, null);
		panelOnglet2.setLayout(new GridLayout());
		DSnmpMibTreeGUI tree1 = new DSnmpMibTreeGUI();
		Component comp = tree1.createComponents();
		panelOnglet2.add(comp);
		
		//ajout onglet syslog
		tabbedPane.addTab("Syslog", null, new PanelSyslog().createPanelGeneral(), null);
		

		
		//layout du panel onglet
		panelOngletGeneral.setLayout(new BorderLayout(0, 0));
		panelOngletGeneral.add(tabbedPane);
		
		
		//Creation de la barre de menu
		JMenuBar menuBar = new JMenuBar();
		fenetre.setJMenuBar(menuBar);

		JMenu fichier = new JMenu("Fichier");
		menuBar.add(fichier);

		JMenuItem quitter = new JMenuItem("Quitter");
		fichier.add(quitter);
		quitter.addActionListener(new WindowClose());
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem about = new JMenuItem("A propos de " + Version.getName());
		mnAide.add(about);
		about.addActionListener(new AboutButton());
	}

	
	
	/*private void initializeSyslogSrv() {
		threadSyslog = new Thread(new Runnable() {
			@Override
			public void run() {
				SyslogServeur instLog = new SyslogServeur();
			}
		});
		threadSyslog.start();
	}*/
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		/*try {
			threadSyslog.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

}
