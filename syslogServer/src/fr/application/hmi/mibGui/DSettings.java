package fr.application.hmi.mibGui;

import java.io.*;
import java.util.*;

import fr.application.hmi.config.Version;

public class DSettings {
	public static PropertyResourceBundle resources;
	DwIPRecord iprecord = null;

	DSettings() {
		try {
			iprecord = new DwIPRecord();
			loadProperties(iprecord);
		} catch (Exception e) {
			System.out.println("Cannot load user settings file.");
			e.printStackTrace();
		}
	}

	public void loadProperties(DwIPRecord iprecord)
			throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(Version.getNameTrim() + ".conf");
		resources = new PropertyResourceBundle(file);
		String parVal;

		parVal = getResourceParameter("lastipaddress");
		iprecord.ipAddress = (parVal != null) ? parVal : "127.0.0.1";

		parVal = getResourceParameter("lastport");
		int parInt;
		try {
			parInt = Integer.parseInt(getResourceParameter("lastport"));
		} catch (Exception e) {
			parInt = 161;
		}
		iprecord.port = parInt;

		parVal = getResourceParameter("lastgetcommunity");
		iprecord.getCommunity = (parVal != null) ? parVal : "public";

		parVal = getResourceParameter("lastsetcommunity");
		iprecord.setCommunity = (parVal != null) ? parVal : "public";

		file.close();
	}

	public void saveProperties(DwIPRecord iprecord)
			throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(Version.getNameTrim() + ".conf", false);

		file.write(("lastipaddress=" + iprecord.ipAddress + "\n").getBytes());
		file.write(("lastport=" + iprecord.port + "\n").getBytes());
		file.write(("lastgetcommunity=" + iprecord.getCommunity + "\n")
				.getBytes());
		file.write(("lastsetcommunity=" + iprecord.setCommunity + "\n")
				.getBytes());
		file.close();

	}

	private static String getResourceParameter(String parmName) {
		String param = null;
		try {
			param = resources.getString(parmName);
		} catch (Exception e) {
			System.out.println("Exception in getting parameter: " + parmName
					+ " " + e);
			param = null;
		}
		return param.trim();
	}

}

class DwIPRecord {
	public String ipAddress = "127.0.0.1";
	public int port = 161;
	public String getCommunity = "public";
	public String setCommunity = "public";
}
