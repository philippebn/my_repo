package fr.application.hmi.mibGui;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class DSnmpOidSupport
{

	DSnmpMibTreeHash oidResolveHash;
	DSnmpMibOutputHandler output=null;

	DSnmpOidSupport() {
		oidResolveHash=new DSnmpMibTreeHash();
		outputText("OID Support Library initialized");
	}

	public String getNodeOid(DefaultMutableTreeNode node) {
		String strPath="";
                DSnmpMibRecord  nodeInfo = (DSnmpMibRecord )node.getUserObject();
		if(nodeInfo.recordType==nodeInfo.recVariable) return(nodeInfo.name + "-" + nodeInfo.syntax);
		try	{
			TreeNode[] nodePath= node.getPath();
		 	DSnmpMibRecord recTemp;
			if (nodePath.length < 2 ) return(".");
			for(int i=2;i<nodePath.length;i++)	{
				recTemp=(DSnmpMibRecord)(((DefaultMutableTreeNode)nodePath[i]).getUserObject());
	
				strPath=strPath.concat("." + String.valueOf (recTemp.number ));
			}
		}
		catch (Exception e1) {
				return("Error in getting path..\n"+e1.toString());
		}
		// Node OID Obtained, now check if it is in a table
		// For Table Element
		//System.out.println("Getting OID Name...");
		if(nodeInfo.recordType  == 3)
		{
			DSnmpMibRecord  recParTemp=(DSnmpMibRecord) ((DefaultMutableTreeNode)node.getParent()).getUserObject();
			if(recParTemp.tableEntry==-1)	strPath=strPath.concat(".("+1 + " - " + "n"+")");
			else
				strPath=strPath.concat(".(" + 1 + " - " + String.valueOf(recParTemp.tableEntry)+")");
		}
		else
		if(nodeInfo.recordType  == 2)
		{
			if(nodeInfo.tableEntry==-1)	strPath=strPath.concat(".(1-"+node.getChildCount()+")"+ ".(1-" + "n)");
			else
				strPath=strPath.concat(".(1-"+node.getChildCount()+")"+ ".(1-" + String.valueOf(nodeInfo.tableEntry)+")");
		}
		else
		if(node.isLeaf() == true) strPath=strPath.concat(".0");
		else
		strPath=strPath.concat(".*");
		//System.out.println(strPath);
		return strPath;


	}


	public String getNodeOidQuery(DefaultMutableTreeNode node) {

		String strPath="";
        DSnmpMibRecord  nodeInfo = (DSnmpMibRecord )node.getUserObject();
		try	{
			TreeNode[] nodePath= node.getPath();
			DSnmpMibRecord recTemp;
			if (nodePath.length < 2 ) return(".");
			for(int i=2;i<nodePath.length;i++)	{
				recTemp=(DSnmpMibRecord)(((DefaultMutableTreeNode)nodePath[i]).getUserObject());
				//System.out.println(recTemp.name + "  " + recTemp.number);
				strPath=strPath.concat("." + String.valueOf (recTemp.number ));
			}
		}
		catch (Exception e1) {
				outputError("Error in getting path..\n"+e1.toString());
				return "";
		}
		// Node OID Obtained, now check if it is in a table
		// For Table Element
		if(nodeInfo.recordType  == 3)
		{
			DSnmpMibRecord  recParTemp=(DSnmpMibRecord) ((DefaultMutableTreeNode)node.getParent()).getUserObject();
			if(recParTemp.tableEntry==-1)	strPath=strPath.concat(".65535");
			else
				strPath=strPath.concat("."+String.valueOf(recParTemp.tableEntry));
		}
		else
		if(nodeInfo.recordType  == 2)
		{
			if(nodeInfo.tableEntry==-1)	strPath=strPath.concat(".1.1");
			else
				strPath=strPath.concat(".1." + String.valueOf(nodeInfo.tableEntry));
		}
		else
		if(node.isLeaf() == true) strPath=strPath.concat(".0");
		else
		strPath=strPath.concat(".0");
		return strPath;
	}

	public String getNodeOidActual(DefaultMutableTreeNode node) {

		String strPath="";
        DSnmpMibRecord  nodeInfo = (DSnmpMibRecord )node.getUserObject();
		try	{
			TreeNode[] nodePath= node.getPath();
			DSnmpMibRecord recTemp;
			if (nodePath.length < 2 ) return(".");
			for(int i=2;i<nodePath.length;i++)	{
				recTemp=(DSnmpMibRecord)(((DefaultMutableTreeNode)nodePath[i]).getUserObject());
				//System.out.println(recTemp.name + "  " + recTemp.number);
				strPath=strPath.concat("." + String.valueOf (recTemp.number ));
			}
		}
		catch (Exception e1) {
				outputError("Error in getting path..\n"+e1.toString());
				return("Error, cannot resolve OID name");
		}
		return strPath;
	}

	void buildOidToNameResolutionTable(DefaultMutableTreeNode treeRoot)
	{
		try {
			DefaultMutableTreeNode node;
			DSnmpMibRecord nodeRec;
			Enumeration enu=treeRoot.breadthFirstEnumeration();
			while(enu.hasMoreElements()) {
				node=(DefaultMutableTreeNode) enu.nextElement();
				nodeRec=(DSnmpMibRecord) node.getUserObject();
				String sRec=getNodeOidActual(node);
				oidResolveHash.put(sRec,nodeRec.name);
			}
		}
		catch (Exception e) {
			outputError("Error in building OID table..\n"+e.toString());
		}
	}

	public String resolveOidName(String oid)
	{
		String objName=null;
		String oidCopy;

                if(oid.startsWith(".")) oidCopy=oid.toString();
                else oidCopy="." + oid.toString();

		try {
			oidCopy=oidCopy.substring(0,oidCopy.lastIndexOf('.'));

			while(objName==null && oidCopy.length()>2) {
				objName=(String)oidResolveHash.get(oidCopy);
				oidCopy=oidCopy.substring(0,oidCopy.lastIndexOf('.'));
			}
			if(objName==null) return("***");
		}
		catch(Exception e) { System.out.println("Error in Resolving OID Name :\n " + e.toString()); }
		return(objName+oid.substring(oid.indexOf(".",oidCopy.length()+1)));
	}


	public void setOutput(DSnmpMibOutputHandler output) {
		this.output=output;
	}
	void outputText(String s) {
		try {
		output.println(s);
		} catch(Exception e) {
			System.out.println(s);
		}
	}
	void outputError(String s) {
		try {
		output.printError(s);
		} catch(Exception e) {
			System.out.println(s);
		}
	}
}




