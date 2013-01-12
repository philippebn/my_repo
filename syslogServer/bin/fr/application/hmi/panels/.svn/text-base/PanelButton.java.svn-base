package fr.application.hmi.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

@SuppressWarnings("unused")
public class PanelButton extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4365622454878726151L;
	
	static Logger log = Logger.getLogger(PanelButton.class);
	
	private JPanel panelButton;

	public JPanel getPanelButton() {
		
		//panel
		panelButton = new JPanel();

        //bouton ici
        JButton button = new JButton("test !");
        
        //ajoute un listener : ici le listener est cette classe
        button.addActionListener(this);

		panelButton.add("center",button);
		panelButton.setBorder(BorderFactory.createTitledBorder("Controles"));
		panelButton.setLayout(new GridLayout(0,1));
		
		return panelButton;
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new PanelButton().getPanelButton());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		log.info("putin");
		
	}
}
