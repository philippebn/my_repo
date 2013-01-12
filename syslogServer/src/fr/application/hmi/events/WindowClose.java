package fr.application.hmi.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Philippe
 * obligatoire car test implémente l'interface ActionListener
 */
public class WindowClose implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}