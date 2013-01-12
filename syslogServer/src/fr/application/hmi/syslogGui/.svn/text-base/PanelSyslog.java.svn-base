package fr.application.hmi.syslogGui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

public class PanelSyslog extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3369570923890835756L;
	
	static Logger log4j = Logger.getLogger(PanelSyslog.class);

	private JFileChooser fc = new JFileChooser();
	private JTextArea log = new JTextArea();
	private JToggleButton follow = new JToggleButton("Suivre");
	private logSyslog objSyslog;
	private Thread thread;
	public static int localPort = 514;
	
	private static final String SAVE        = "Sauvegarder";
	private static final String CHANGE_PORT = "Changer port d'écoute";
	private static final String START_LIST  = "Demarrer";
	private static final String STOP_LIST   = "Arrete";	
	private JButton startListener, stopListener;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();		
		frame.add(new PanelSyslog().createPanelGeneral());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500, 600);
	}
	
	
	public JPanel createPanelGeneral() {

		JPanel panelGeneral = new JPanel();
		panelGeneral.setLayout(new BorderLayout());
		panelGeneral.add(createPanelButton(), BorderLayout.PAGE_START);
		
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		log.scrollRectToVisible(getBounds());
		JScrollPane logScrollPane = new JScrollPane(log);
		panelGeneral.add(logScrollPane, BorderLayout.CENTER);
		return panelGeneral;
	}
	
	
	
	public JToolBar createPanelButton() {
		JToolBar panelButton = new JToolBar();
		
		JButton save = new JButton(SAVE);
		save.addActionListener(this);
		save.setActionCommand(SAVE);
		panelButton.add(save);
		
		startListener = new JButton(START_LIST);
		startListener.addActionListener(this);
		startListener.setActionCommand(START_LIST);
		panelButton.add(startListener);
		
		stopListener = new JButton(STOP_LIST);
		stopListener.addActionListener(this);
		stopListener.setActionCommand(STOP_LIST);
		stopListener.setEnabled(false);
		panelButton.add(stopListener);
		
		panelButton.add(follow);
		
		JButton options = new JButton(CHANGE_PORT);
		options.addActionListener(this);
		options.setActionCommand(CHANGE_PORT);
		panelButton.add(options);
		
		return panelButton;
	}
	
	

	public PanelSyslog() {
		// create panel
		JPanel panel = new JPanel();

		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		log.scrollRectToVisible(getBounds());
		JScrollPane logScrollPane = new JScrollPane(log);

		add(panel);
		add(logScrollPane);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ("OpenFile".equals(e.getActionCommand())) {
			int returnVal = fc.showOpenDialog(PanelSyslog.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				OpenFile(file);
				log4j.info("Ouvrir un fichier");
			} else {
				addLog("Ouvrir fichier annulé.");
				log4j.info("Ouvrir fichier annulé.");
			}
		}

		if (SAVE.equals(e.getActionCommand())) {
			int returnVal = fc.showSaveDialog(PanelSyslog.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				FileWriter fw;
				try {
					fw = new FileWriter(file);
					fw.write(log.getText());
				} catch (IOException e1) {
					addLog(e1.getMessage());
				}

			} else {
				log4j.info("Sauvegarde annuler.");
				addLog("Sauvegarde annuler.");
			}
		}

		if (START_LIST.equals(e.getActionCommand())) {
			objSyslog = new logSyslog();
			objSyslog.init_thread(this, localPort);
			thread = new Thread(objSyslog);
			thread.start();
			startListener.setEnabled(false); 
			stopListener.setEnabled(true);
			log4j.info("Serveur Syslog activer...");
		}

		if (STOP_LIST.equals(e.getActionCommand())) {
			objSyslog.stopRequest();
			startListener.setEnabled(true); 
			stopListener.setEnabled(false);
			log4j.info("Serveur Syslog desactiver...");
		}

		if (CHANGE_PORT.equals(e.getActionCommand())) {
			String texte = "Port d'écoute syslog: " + localPort + "\nInsere nouveau port d'écoute";
			String inputValue = JOptionPane.showInputDialog(texte);
			log4j.info(texte);
			
			if (!inputValue.isEmpty())
				localPort = Integer.parseInt(inputValue);
		}
	}
	
	
	//*********************************
	public void addLog(String text) {
		text = text.replaceAll("\\n", "");
		text = text.replaceAll("\\t", "");
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		log.append(format.format(new Date()) + ": " + text + "\n");
		if (follow.isSelected()) {
			String s = log.getText();
			int pos = s.length();
			log.setCaretPosition(pos);
		}
	}

	public void OpenFile(File pfile) {
		if (!pfile.canRead()) {
			addLog("Impossible d'ouvrir le fichier, verifier les permissions");
		}
		addLog("Fichier: " + pfile.toString());
		try {
			BufferedReader in = new BufferedReader(new FileReader(pfile));
			String str;
			while ((str = in.readLine()) != null) {
				log.append(str + "\n");
			}
			in.close();
			addLog("Fichier Fermer: " + pfile.toString());
		} catch (IOException e) {
			addLog(e.getMessage());
		}
	}
	

}
