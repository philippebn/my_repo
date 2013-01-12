package fr.application.common.snmp;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpWalk {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Address targetAddress = GenericAddress.parse("udp:192.168.1.15/161");
		TransportMapping<?> transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		/*pour du snmpv3
		 * USM usm = new USM(SecurityProtocols.getInstance(), new
		 * OctetString(MPv3.createLocalEngineID()), 0);
		 * SecurityModels.getInstance().addSecurityModel(usm);
		 */
		
		System.out.println("bonjour");
		transport.listen();
		// setting up target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version1);
		// creating PDU
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(
				new int[] { 1, 3, 6, 1, 2, 1, 1, 1 })));
		pdu.add(new VariableBinding(new OID(
				new int[] { 1, 3, 6, 1, 2, 1, 1, 2 })));
		pdu.setType(PDU.GETNEXT);
		// sending request
		ResponseListener listener = new ResponseListener() {
			public void onResponse(ResponseEvent event) {
				((Snmp) event.getSource()).cancel(event.getRequest(), this);
				System.out.println("Received response PDU is: "
						+ event.getResponse());
			}
		};
		snmp.send(pdu, target, null, listener);
	}

}