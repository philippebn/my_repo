package fr.application.hmi.mibGui;

import org.apache.log4j.Logger;

import fr.application.hmi.panels.PanelTableauIP;


public class DSnmpMibBrowserFunctions {
	private DSnmpMibOutputHandler output = null;
	private DSnmpOidSupport oidSupport = null;

//static Logger log = Logger.getLogger(PanelTableauIP.class);
	
	private String agentIP = "192.168.2.9";
	private int agentPort = 161;
	private String setCommunity = "public";
	private String getCommunity = "public";

	private SnmpLib m_snmpLib;

	public DSnmpMibBrowserFunctions() {
	}

	public void setOutput(DSnmpMibOutputHandler output) {
		this.output = output;
	}

	public void setOidSupport(DSnmpOidSupport oidSupport) {
		this.oidSupport = oidSupport;
	}

	public void setIP(String s) {
		m_snmpLib = null;
		this.agentIP = s;
	}

	public void setPort(int p) {
		m_snmpLib = null;
		this.agentPort = p;
	}

	public void setCommunity(String get, String set) {
		m_snmpLib = null;
		this.getCommunity = get;
		this.setCommunity = set;
	}

	public String getIP() {
		return agentIP;
	}

	public int getPort() {
		return agentPort;
	}

	public String getReadCommunity() {
		return getCommunity;
	}

	public String getWriteCommunity() {
		return setCommunity;
	}

	void destroySession() {
		getSnmpLib().destroySession();
	}

	public void outputRecord(SnmpOidValuePair oidval) {
		try {
			if (oidSupport != null) {
				//outputText("Oid : " + oidval.oid + " ("
					//	+ oidSupport.resolveOidName(oidval.oid) + " )");
			} else {
				//outputText("Oid : " + oidval.oid);
			}
			
			
		} catch (Exception e) {
			outputError("Cannot resolve Name from OID..\n" + e.toString());
		}
		
		////////////////////////////////
		////CODE BY JOHAN
		
		//System.out.println(oidval.oid.toString());
		if(oidval.oid.toString().equals("1.3.6.1.2.1.25.2.2.0"))
		{
			//System.out.println("boucle");
			// On traite alors la mémoire.
			double giga = Integer.parseInt(oidval.value_str);
			giga = ((giga / 1024)/1024);
			
			oidval.value_str = String.valueOf(giga);
			//System.out.println("oidval.value_str");
			outputText("Value: " + oidval.value_str + " GO de mémoire disponible");
		}
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.1.5.0"))
		{
			outputText("Value: " + oidval.value_str + " utilisateur(s) connecté(s)");
		}
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.1.6.0"))
		{
			outputText("Value: " + oidval.value_str + " processus en cours");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.5"))
		{
			outputText("Coeur 1 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.6"))
		{
			outputText("Coeur 2 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.7"))
		{
			outputText("Coeur 3 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.8"))
		{
			outputText("Coeur 4 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.9"))
		{
			outputText("Coeur 5 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.10"))
		{
			outputText("Coeur 6 : " + oidval.value_str + " %");
		}
		
		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.11"))
		{
			outputText("Coeur 7 : " + oidval.value_str + " %");
		}

		else if(oidval.oid.toString().equals("1.3.6.1.2.1.25.3.3.1.2.12"))
		{
			outputText("Coeur 8 : " + oidval.value_str + " %");
		}

		else
		{
			outputText("OID: " + oidval.oid);
			outputText("Value: " + oidval.value_str);
		}
		/////////////////////////////////
	}

	void outputError(String texte) {
		try {
			output.printError(texte);
			//log.error(texte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void outputText(String texte) {
		try {
			output.println(texte);
			//log.info(texte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void snmpRequestGet(String strVar, String strTo) {
		try {
			getSnmpLib().snmpWalk(strVar);
		} catch (Exception e) {
			outputError("\nError in executing GET request : \n" + e.toString());
			e.printStackTrace();
		}
	}

	public DSnmpRequest snmpRequestGet(String strVar) {
		snmpRequestGet(strVar, null);
		return null;
	}

	String processSetRequest(DSnmpMibRecord setRec, String oid, String setVal) {
		try {
			getSnmpLib().snmpSetValue(oid, setRec.getSyntaxID(), setVal);
		} catch (Exception e) {
			outputError("\nError in executing SET request : \n" + e.toString());
			e.printStackTrace();
		}
		return "";
	}

	private SnmpLib getSnmpLib() {
		if (m_snmpLib == null) {
			m_snmpLib = new SnmpLib();
			m_snmpLib.setHost(getIP());
			m_snmpLib.setPort(getPort());
			m_snmpLib.setCommunity(getReadCommunity(), getWriteCommunity());

			m_snmpLib.setSnmpResponseHandler(new ISnmpResponseHandler() {
				public void responseReceived(SnmpOidValuePair resp_values) {
					outputRecord(resp_values);
				}

				public void requestStats(int totalRequests, int totalResponses,
						long timeInMillis) {
					/*if (totalResponses == 0)
						totalResponses = 1;
					outputText("\nReceived " + (totalResponses - 1)
							+ " record(s) in " + timeInMillis
							+ " milliseconds.");*/
					outputText("***********************************");
				}
			});
		}
		return m_snmpLib;
	}
}
