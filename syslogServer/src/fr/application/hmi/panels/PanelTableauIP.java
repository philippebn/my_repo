package fr.application.hmi.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import fr.application.common.ping.PingCmd;

public class PanelTableauIP extends JPanel implements ActionListener, Runnable {

	static Logger log = Logger.getLogger(PanelTableauIP.class);
	
	private static final long serialVersionUID = 579871296745821473L;
	private boolean DEBUG = false;
	
	private DefaultTableModel tmodel = new DefaultTableModel( ) {

		private static final long serialVersionUID = -5617733333009781432L;

		@Override
	    public boolean isCellEditable(int row, int column) {
	       return false;
	    }
	};
	private static JTable jtable;
	private static final String BUTTON_SCAN = "Scan Réseaux";
	private JButton button;

	public PanelTableauIP() {
		setLayout(new BorderLayout());		
		jtable = new JTable(tmodel);
		jtable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		jtable.setFillsViewportHeight(true);
		tmodel.addColumn("IP");
		tmodel.addColumn("Nom Machines");

		if (DEBUG) {
			jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					printDebugData(jtable);
				}
			});
		}

		JScrollPane scrollPane = new JScrollPane(jtable);
		button = new JButton(BUTTON_SCAN);
		button.addActionListener(this);
		button.setActionCommand(BUTTON_SCAN);

		JToolBar toolBar = new JToolBar();
		toolBar.add(button);
		
		
		
		this.add(new JPanel().add(toolBar), BorderLayout.PAGE_START);
		// Add the scroll pane to this panel.
		this.add(scrollPane, BorderLayout.CENTER);
	}

	private void printDebugData(JTable table) {
		int numRows = table.getRowCount();
		int numCols = table.getColumnCount();
		javax.swing.table.TableModel model = table.getModel();

		System.out.println("Value of data: ");
		for (int i = 0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j = 0; j < numCols; j++) {
				System.out.print("  " + model.getValueAt(i, j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("SimpleTableDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		PanelTableauIP newContentPane = new PanelTableauIP();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (BUTTON_SCAN.equals(e.getActionCommand())) {
			Thread t = new Thread(this);
			t.start();
		}

	}

	@Override
	public void run() {
		
		//on balance le thread pour la recherche
		button.setEnabled(false);
		button.setText("Recherche en cours.....");
		while (tmodel.getRowCount() > 0) {
			tmodel.removeRow(0);
		}
		PingCmd liste = new PingCmd();

		Vector<String[]> tableScanNetwork = liste.getTabMachine();
		for (String[] row : tableScanNetwork) {
			tmodel.addRow(new Object[] { row[0].toString(), 
										 row[1].toString() });
			log.debug(row[0].toString());
			log.debug(row[1].toString());
		}
		button.setEnabled(true);
		button.setText(BUTTON_SCAN);
	}

}
