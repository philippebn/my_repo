package fr.application.hmi.mibGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.io.*;

public class DSnmpMibTreeGUI implements ActionListener, MouseListener,
		TreeSelectionListener {

	DSnmpMibTreeBuilder treeSupport;
	DSnmpMibBrowserFunctions snmp;
	DSnmpMibOutputHandler output = new DSnmpMibOutputHandler();
	DSnmpSelectServerDialog m_ipSrv = new DSnmpSelectServerDialog();

	JTree myTree;
	JScrollPane treeScrollPane;
	JPanel treePane;
	// /////////////////////////////////////////////////////
	// CODE BY JOHAN
	JTextArea listMib;
	JButton memoire;
	JButton nbUtilisateur;
	JButton nbProcessus;
	JButton descSys;
	JButton sysup;
	JButton CPUcharge;
	// /////////////////////////////////////////////////////
	JButton btnLoadMib;

	JPanel paneMain = new JPanel(new BorderLayout());
	JTextField selectedTreeOid = new JTextField("Selectionner un oid..");
	JTextArea resultText;
	JButton btnGet = new JButton("Get");
	JButton btnSet = new JButton("Set");
	JButton btnStop = new JButton("Stop");
	JCheckBox chkScroll = new JCheckBox("Scroll Display");
	//JButton btnOidDetails = new JButton("Details");
	JButton btnClear = new JButton("Nettoyer");

	// Tooltips and Toolbars
	JToolBar mainToolbar;
	JButton toolbarBtnIP;

	JToolBar statusToolbar;
	// Initial Vars

	PipedInputStream pin;
	PipedOutputStream pout;
	PrintStream out;
	BufferedReader in;

	public DSnmpMibTreeGUI() {
		output.setLogging(true);
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public DSnmpOidSupport getOidSupport() {
		return treeSupport.oidSupport;
	}

	public JPanel getMainPane() {
		return paneMain;
	}

	public JTree getTree() {
		return myTree;
	}

	public JPanel createComponents() {

		//btnOidDetails.addActionListener(this);
		btnClear.addActionListener(this);

		resultText = new JTextArea();
		JScrollPane resultPane = new JScrollPane(resultText);

		// Set everyone's output to resulttext
		output = new DSnmpMibOutputHandler();
		output.setOutput(resultText);
		output.setOutputError(resultText);
		snmp = new DSnmpMibBrowserFunctions();
		snmp.setOutput(output);
		setIPConfig();

		selectedTreeOid = new JTextField();
		// ///////////////////////////////
		// /CODE BY JOHAN

		memoire = new JButton("Memoire libre");

		nbUtilisateur = new JButton("Nombre d'utilisateur connecté");
		nbProcessus = new JButton("Nombre de processus en cours");
		descSys = new JButton("Description système");
		sysup = new JButton("Uptime du système");
		CPUcharge = new JButton("CPU charge");

		memoire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.25.2.2");
			}
		});

		nbUtilisateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.25.1.5");
			}
		});

		nbProcessus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.25.1.6");
			}
		});

		descSys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.1.1");
			}
		});

		sysup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.1.3");
			}
		});

		CPUcharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedTreeOid.setText("1.3.6.1.2.1.25.3.3.1.2");
			}
		});

		// /////////////////////////////////////
		/*
		 * // Create a tooltip for jlabel, and also add a message handler to it.
		 * selectedTreeOid
		 * .setToolTipText("Click here for more information on this variable");
		 * // selectedTreeOid.setText("Selected Element's OID");
		 * selectedTreeOid.addMouseListener(this);
		 * 
		 * // Create the TREE and Tree pane.
		 * 
		 * outputText("Building tree.."); treeSupport = new
		 * DwSnmpMibTreeBuilder(); treeSupport.setOutput(output); String
		 * projectdir = System.getProperty("ProjectDir"); if (projectdir ==
		 * null) { projectdir = "."; }
		 * 
		 * if (treeSupport.addDirectory(projectdir +
		 * "/src/fr/application/mibs/") == false) { outputError("Directory " +
		 * projectdir + "/mibs/ not found, or it is an empty directory!"); }
		 * 
		 * // treeSupport.addFile("mib_core.txt"); //
		 * treeSupport.addFile("mib_II.txt"); myTree = treeSupport.buildTree();
		 * if (myTree == null || treeSupport.oidSupport == null) {
		 * outputError("Error in loading MIB tree, quitting"); return null; }
		 * snmp.setOidSupport(treeSupport.oidSupport);
		 * myTree.addTreeSelectionListener(this); treeScrollPane = new
		 * JScrollPane(myTree);
		 */
		btnLoadMib = new JButton("Load MIB");
		treePane = new JPanel(new BorderLayout());

		// ///////////////////////////////////////////
		// /CODE BY JOHAN

		// treePane.setLayout(new BoxLayout(treePane, BoxLayout.Y_AXIS));
		treePane.setLayout(new GridLayout(10, 0));
		treePane.add(memoire);
		treePane.add(nbUtilisateur);
		treePane.add(nbProcessus);
		treePane.add(descSys);
		treePane.add(sysup);
		treePane.add(CPUcharge);

		// //////////////////////////////////////////

		statusToolbar = new JToolBar();
		statusToolbar.add(btnClear);
		//statusToolbar.add(btnOidDetails);
		statusToolbar.addSeparator();
		statusToolbar.add(selectedTreeOid);

		JPanel paneBtn = new JPanel(new FlowLayout());
		btnGet.addActionListener(this);
		btnSet.addActionListener(this);
		btnStop.addActionListener(this);
		chkScroll.setSelected(true);
		chkScroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				output.setAutoScroll(chkScroll.isSelected());
			}
		});

		paneBtn.add(btnGet);
		paneBtn.add(btnSet);
		paneBtn.add(btnStop);
		paneBtn.add(chkScroll);

		JPanel paneStatus = new JPanel(new BorderLayout());
		paneStatus.add("South", paneBtn);
		paneStatus.add("North", statusToolbar);
		paneStatus.add("Center", resultPane);

		// Create the Main Toolbar
		mainToolbar = new JToolBar();
		toolbarBtnIP = new JButton("Selectionner Machine(s)");

		toolbarBtnIP.addActionListener(this);
		btnLoadMib.addActionListener(this);
		mainToolbar.add(toolbarBtnIP);
		JSplitPane paneContent = new JSplitPane();
		paneContent.setLeftComponent(treePane);
		paneContent.setRightComponent(paneStatus);
		paneContent.setDividerLocation(250);

		paneMain.add("Center", paneContent);
		paneMain.add("North", mainToolbar);

		m_ipSrv = new DSnmpSelectServerDialog();

		return paneMain;
	}

	/**
	 * Returns the tree pane
	 */
	public JPanel getTreePane() {
		return treePane;
	}

	public void setTreePane(JPanel treePanel) {

	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) myTree
				.getLastSelectedPathComponent();
		if (node == null) {
			selectedTreeOid.setText(" ");
			return;
		}
		// selectedTreeOid.setText(treeSupport.oidSupport.getNodeOid(node));
	}

	public void mouseClicked(MouseEvent evt) {
		Object source = evt.getSource();
		if (source == selectedTreeOid) {
			DSnmpMibRecord node = getSelectedTreeNode();
			// if (node != null)
			// outputText(node.getCompleteString());
		}
	}

	private DSnmpMibRecord getSelectedTreeNode() {
		DSnmpMibRecord ret = null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) myTree
				.getLastSelectedPathComponent();
		if (node != null) {
			ret = (DSnmpMibRecord) node.getUserObject();
		}
		return ret;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		try {
			if (source == btnGet) {
				sendGetRequest(selectedTreeOid.getText());
				return;
			} else if (source == btnSet) {
				DSnmpMibRecord node = getSelectedTreeNode();
				if (!node.isWritable()) {
					JOptionPane.showMessageDialog(getMainPane(),
							"The selected node is not writable.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String oid = selectedTreeOid.getText();
				String oidText = getOidSupport().resolveOidName(oid);
				System.out.println(oidText);
				String setValue = "";
				String message = "Enter new value for " + oidText;
				if (node.getSyntaxID() != DSnmpMibRecord.VALUE_TYPE_NONE) {
					message = message + "\nValue Type: " + node.syntax.trim()
							+ " [" + node.getSyntaxIDString() + "]";
				} else {
					message = message + "\nValue type " + node.syntax.trim()
							+ " unknown, will use STRING.";
				}
				setValue = JOptionPane.showInputDialog(message);
				if (setValue != null && node.checkValidValue(setValue)) {
					outputText("Request : Set  " + oid + "  Value : "
							+ setValue);
					if (snmp.processSetRequest(node, oid, setValue) == null) {
						outputError("Error in processing variable data/set request");
						return;
					}
					// DwSnmpRequestSet(oid,setValue);
					outputText("Set command executed...");
					outputText("Getting new value of " + oid + " ...");
					sendGetRequest(oid);
				}
				return;
			} else if (source == btnStop) {
				snmp.destroySession();
				outputText(" ******** Cancelled *********\n");
				return;
			} else if (source == btnClear) {
				resultText.setText("");
				return;
			} /*else if (source == btnOidDetails) {
				mouseClicked(new MouseEvent(selectedTreeOid, 0, 0, 0, 0, 0, 0,
						true));
				return;
			} */else if (source == toolbarBtnIP) {
				try {
					String newIP = new String(" ");
					if (snmp == null) {
						snmp = new DSnmpMibBrowserFunctions();
						snmp.setOidSupport(treeSupport.oidSupport);
					}
					getNewIPInfo();
				} catch (Exception e) {
					System.err.println("Error in changing IP..\n"
							+ e.toString());
				}
				return;
			} else if (source == btnLoadMib) {
				loadNewMib();
				return;
			}
		} catch (Exception e) {
			outputError("\nError in processing user request : \n"
					+ e.toString());
		}
	}

	void getNewIPInfo() {
		String ipInfo[] = m_ipSrv.show();

		if (ipInfo == null) {
			return;
		}
		setIPConfig();

	}

	void setIPConfig() {
		String ipInfo[] = m_ipSrv.getSelectedConfig();
		snmp.setIP(ipInfo[0]);
		snmp.setPort(Integer.parseInt(ipInfo[1]));
		snmp.setCommunity(ipInfo[2], ipInfo[3]);

	}

	public void loadNewMib() {

		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setVisible(true);
			fileChooser.setDialogTitle("Select the MIB File to Load");
			fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
			fileChooser.setCurrentDirectory(new File("."));
			fileChooser.setMultiSelectionEnabled(true);

			String strFileName = "";
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File[] files = fileChooser.getSelectedFiles();
				if (files != null && files.length > 0) {
					for (int i = 0; i < files.length; i++) {
						try {
							loadSingleFile(files[i]);
						} catch (Exception e) {
							outputError("Error in loading file: "
									+ files[i].getAbsolutePath());
						}
					}
				}
			} else {
				return;
			}

			// treeSupport.addFile(strFileName);
		} catch (Exception e) {
			System.out.println("Error in getting new MIB Filenames.\n"
					+ e.toString());
		}
	}

	private void loadSingleFile(File file) {
		String strFileName = file.getAbsolutePath();
		treeSupport.loadNewFile(strFileName);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(700, 550);
		DSnmpMibTreeGUI tree1 = new DSnmpMibTreeGUI();
		JPanel panel = tree1.createComponents();
		if (panel != null) {
			frame.getContentPane().add(panel);
		} else {
			JOptionPane.showMessageDialog(frame.getContentPane(),
					"Error in loading default MIBs.");
		}
		// Finish setting up the frame, and show it.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// frame.pack();
		frame.setVisible(true);
	}

	void sendGetRequest(String strReq) {

		if (strReq.endsWith("0")) {
			strReq = strReq.substring(0, strReq.lastIndexOf("."));
			outputText("Request : Get  " + strReq + "\n");
		} else if (strReq.endsWith("*")) {
			strReq = strReq.substring(0, strReq.lastIndexOf("*") - 1);
			outputText("Request : Walk " + strReq + "\n");
		} else if (strReq.endsWith(")")) {
			strReq = strReq.substring(0, strReq.indexOf("(") - 1);
			outputText("Request : Walk " + strReq + "\n");
		} else {
			outputError("Error in request. Please check the OID.");
		}

		snmp.snmpRequestGet(strReq);

		// System.out.println(strReq);
		// System.out.println(.toString());

	}

	void outputText(String s) {
		if (output != null) {
			output.println(s);
		} else {
			System.out.println(s);
		}
	}

	void outputError(String e) {
		if (output != null) {
			output.printError(e);
		} else {
			System.out.println(e);

		}
	}

	private void jbInit() throws Exception {
	}
}
