package fr.application.hmi.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.application.hmi.config.Version;

/**
 * @author Philippe BENAOUN
 */
public class AboutButton extends JDialog implements ActionListener {

	/**
	 * obligatoire car test implémente l'interface ActionListener 
	 */
	private static final long serialVersionUID = -6258502928673740291L;


	public void actionPerformed(ActionEvent e) {
		JDialog jdialog = SimpleAboutDialog();
		jdialog.setVisible(true);
	}

	public JDialog SimpleAboutDialog() {
		final JDialog jdialog = new JDialog(this,"A propos de " + Version.getName(), true);
		
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		b.add(new JLabel(" Team Supervisor "));
		b.add(new JLabel(" Johan BASILE "));
		b.add(new JLabel(" Philippe BENAOUN "));
		b.add(new JLabel(" Guillaume FIZET "));
		b.add(Box.createGlue());

		jdialog.getContentPane().add(b, "Center");
		
		JPanel p2 = new JPanel();
		JButton ok = new JButton("Ok");
		p2.add(ok);
		
		jdialog.getContentPane().add(p2, "South");

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jdialog.setVisible(false);
			}
		});

		jdialog.pack();
		jdialog.setSize(300, 150);
		jdialog.setResizable(false);
		jdialog.setLocationRelativeTo(null);

		return jdialog;
	}
}
