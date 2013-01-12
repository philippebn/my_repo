package fr.application.hmi.mibGui;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class DSnmpMibTreeBuilder implements DMibParserInterface, Runnable {
	DSnmpMibOutputHandler output = null;
	private DefaultMutableTreeNode rootNode;
	private DefaultMutableTreeNode treeRootNode;
	private DefaultMutableTreeNode rootOrphan;
	private DefaultMutableTreeNode rootVariable;
	private DefaultMutableTreeNode rootVariableTable;

	JTree tree;

	private Vector fileVect;
	private Vector orphanNodes;

	private String errorMsg = "";

	public DSnmpOidSupport oidSupport = new DSnmpOidSupport();
	DSnmpMibTreeHash treeHash;
	DSnmpMibTreeHash variableHash;
	DSnmpMibTreeHash orphanHash;

	public DSnmpMibTreeBuilder() {
		DSnmpMibRecord treeRootRec = new DSnmpMibRecord();
		treeRootRec.name = "MIB Browser";
		treeRootRec.parent = "MIB Browser";
		treeRootRec.number = 0;
		treeRootNode = new DefaultMutableTreeNode(treeRootRec);

		DSnmpMibRecord rootRec = new DSnmpMibRecord();
		rootRec.name = "root";
		rootRec.parent = "MIB Browser";
		rootRec.number = 1;
		rootNode = new DefaultMutableTreeNode(rootRec);

		DSnmpMibRecord rootOrphanRec = new DSnmpMibRecord();
		rootOrphanRec.name = "Orphans";
		rootOrphanRec.parent = "MIB Browser";
		rootOrphanRec.description = "This subtree contains MIB Records whose parent cannot be traced";
		rootOrphanRec.number = 10;
		rootOrphan = new DefaultMutableTreeNode(rootOrphanRec);

		DSnmpMibRecord rootVariableRec = new DSnmpMibRecord();
		rootVariableRec.name = "Variables/Textual Conventions";
		rootVariableRec.parent = "MIB Browser";
		rootVariableRec.description = "This subtree contains all the variables which map to the standard variables.";
		rootVariableRec.number = 11;
		rootVariable = new DefaultMutableTreeNode(rootVariableRec);

		DSnmpMibRecord rootVariableTableRec = new DSnmpMibRecord();
		rootVariableTableRec.name = "Table Entries";
		rootVariableTableRec.parent = "Variables/Textual Conventions";
		rootVariableTableRec.description = "This branch contains a list of sequences for all the tables ";
		rootVariableTableRec.number = 12;
		rootVariableTable = new DefaultMutableTreeNode(rootVariableTableRec);

		treeHash = new DSnmpMibTreeHash();
		treeHash.put(rootRec.name, rootNode);

		variableHash = new DSnmpMibTreeHash();
		orphanHash = new DSnmpMibTreeHash();

		orphanNodes = new Vector();
		fileVect = new Vector();
		clearError();
	}

	public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}

	public boolean addFile(String fName) {
		if (fName == null)
			return false;
		File mibFile = new File(fName);
		if (mibFile.exists() != true)
			return false;
		fileVect.add(fName);
		return true;
	}

	public boolean addDirectory(String dirName) {
		System.out.println("Adding directory : " + dirName);
		File dir = new File(dirName);
		if (dir.isDirectory() != true)
			return false;
		File files[] = dir.listFiles();
		if (files == null)
			return false;
		for (int i = 0; i < files.length; i++) {
			fileVect.add(files[i].getPath());
		}
		return true;
	}

	public String[] getFiles() {

		try {
			Enumeration enu = fileVect.elements();
			String returnStr[] = new String[fileVect.size()];

			int i = 0;
			while (enu.hasMoreElements()) {
				returnStr[i++] = (String) enu.nextElement();
			}
			clearError();
			return returnStr;
		} catch (Exception e) {
			setError("Error in getting filenames..\n" + e.toString());
			return null;
		}
	}

	private void clearError() {
		errorMsg = "";
	}

	private void setError(String err) {
		errorMsg = err;
	}

	public JTree buildTree() {
		// Check if files have been added to list
		if (fileVect.size() == 0) {
			setError("Error : Please add files first");
			return null;
		}

		oidSupport = new DSnmpOidSupport();
		Thread treeThread = new Thread(this);
		treeThread.setPriority(Thread.MAX_PRIORITY - 1);
		treeThread.start();

		treeRootNode.add(rootNode);
		treeRootNode.add(rootOrphan);
		rootVariable.add(rootVariableTable);
		treeRootNode.add(rootVariable);
		tree = new JTree(treeRootNode);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		return (tree);
	}

	public void run() {
		// Get filenames and add nodes to rootnode
		Enumeration enu = fileVect.elements();
		String fileName = "";
		JTree newTree = null;
		while (enu.hasMoreElements()) {
			fileName = (String) enu.nextElement();
			loadFile(fileName);
		}
		updateOrphans();
		output.println("*****COMPLETED******");
	}

	private void loadFile(String fileName) {
		output.print("Adding file " + fileName);
		if (parseFile(fileName) < 0)
			outputError(".. Error");
		else
			output.print("..Done\n");
	}

	public boolean loadNewFile(String fName) {
		if (fName == null)
			return false;
		File mibFile = new File(fName);
		if (mibFile.exists() != true)
			return false;
		if (fileVect.indexOf(fName) == -1) {
			tree.collapsePath(tree.getSelectionPath());
			fileVect.add(fName);
			loadFile(fName);
			updateOrphans();
			return true;
		}
		return false;
	}

	private void updateOrphans() {
		// Check if orphan's parents have arrived. if yes, remove them from
		// orphan list
		// outputText("Scanning orphans..");
		outputText("Updating orphans.");
		DSnmpMibRecord orphanRec = null;
		Enumeration orphanEnu;
		boolean contFlag = true;

		while (contFlag == true) {
			contFlag = false;
			orphanEnu = orphanNodes.elements();
			while (orphanEnu.hasMoreElements()) {
				DefaultMutableTreeNode orphanNode = (DefaultMutableTreeNode) orphanEnu
						.nextElement();

				if (addNode(orphanNode) == true) {
					contFlag = true;
					orphanNodes.remove(orphanNode);
					continue;
				}

			}
			output.print(".");
		}
		output.print("Done");
		output.print("\nBuilding OID Name resolution table...");
		oidSupport.buildOidToNameResolutionTable(rootNode);

		orphanEnu = orphanNodes.elements();
		while (orphanEnu.hasMoreElements()) {
			DefaultMutableTreeNode orphanNode = (DefaultMutableTreeNode) orphanEnu
					.nextElement();
			orphanRec = (DSnmpMibRecord) orphanNode.getUserObject();
			if (orphanRec.recordType == orphanRec.recVariable)
				continue;
			if (orphanRec.recordType == orphanRec.recTable) {
				rootVariable.add(orphanNode);
				continue;
			}
			if (treeHash.containsKey(orphanRec.name) != true)
				rootOrphan.add(orphanNode);
		}

		outputText("Updating variables table..");
		Enumeration enuVar = variableHash.elements();
		DSnmpMibRecord varRec;
		while (enuVar.hasMoreElements()) {
			varRec = (DSnmpMibRecord) enuVar.nextElement();
			rootVariable.add(new DefaultMutableTreeNode(varRec));
		}

		if (tree != null && tree.getModel() != null) {
			((DefaultTreeModel) tree.getModel()).reload();
			tree.revalidate();
			tree.repaint();
		}
		outputText("Done");
	}

	private int parseFile(String fName) {

		DSnmpMibParser fParser = new DSnmpMibParser(fName, this);
		if (output != null)
			fParser.setOutput(output);
		return fParser.parseMibFile();
	}

	private boolean addRecord(DSnmpMibRecord childRec) {
		int parseStatus = 0;
		if (childRec == null)
			return false;
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(childRec);
		if (addNode(newNode) == false) {
			orphanNodes.add(newNode);
			orphanHash.put(childRec.name, newNode);
			return false;
		}
		return true;
	}

	private boolean addNode(DefaultMutableTreeNode newNode) {
		DSnmpMibRecord newRec = (DSnmpMibRecord) newNode.getUserObject();

		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeHash
				.get(newRec.parent);
		if (parentNode == null)
			return false;

		DSnmpMibRecord parentRec = (DSnmpMibRecord) parentNode
				.getUserObject();
		if (parentRec.recordType > 0)
			newRec.recordType = parentRec.recordType + 1;

		DefaultMutableTreeNode dupNode = isChildPresent(newNode);
		if (dupNode == null) { // Add node to its parent
			try {
				parentNode.add(newNode);
				newNode.setParent(parentNode);
				treeHash.put(newRec.name, newNode);
				return true;
			} catch (Exception e) {
				System.out.println("Err in Child : " + newRec.name
						+ "Parent : " + newRec.parent);
				return false;
			}
		} else { // Node already present. add all its children to the existing
					// node
			Enumeration dupChildren = newNode.children();
			while (dupChildren.hasMoreElements()) {
				DefaultMutableTreeNode dupChildNode = (DefaultMutableTreeNode) dupChildren
						.nextElement();
				if (isChildPresent(dupChildNode) == null)
					dupNode.add(dupChildNode);
			}
			return true;
		}
	}

	DefaultMutableTreeNode isChildPresent(DefaultMutableTreeNode childNode) {
		DSnmpMibRecord childRec = (DSnmpMibRecord) childNode.getUserObject();
		return (isChildPresent(childRec));
	}

	DefaultMutableTreeNode isChildPresent(DSnmpMibRecord rec) {
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeHash
				.get(rec.parent);
		if (parentNode == null)
			parentNode = (DefaultMutableTreeNode) orphanHash.get(rec.parent);
		if (parentNode == null)
			return null;
		Enumeration enuChildren = parentNode.children();
		if (enuChildren != null) {
			DefaultMutableTreeNode childNode;
			DSnmpMibRecord childRec;
			while (enuChildren.hasMoreElements()) {
				childNode = (DefaultMutableTreeNode) enuChildren.nextElement();
				childRec = (DSnmpMibRecord) childNode.getUserObject();
				if (childRec.name.equals(rec.name) == true) {
					// outputText("ChildCheck, Rec. Present.. Parent : " +
					// rec.parent + "  Name : " + rec.name);
					return childNode;
				}
			}
		}
		return null; // Child does not exist
	}

	public void setOutput(DSnmpMibOutputHandler output) {
		this.output = output;
	}

	void outputText(String s) {
		try {
			output.println(s);
		} catch (Exception e) {
			System.out.println(s);
		}
	}

	void outputError(String s) {
		try {
			output.printError(s);
		} catch (Exception e) {
			System.out.println(s);
		}
	}

	public void newMibParseToken(DSnmpMibRecord rec) {
		addRecord(rec);

	}

	public void parseMibError(String s) {
		outputError(s);
	}

}// End of class.

