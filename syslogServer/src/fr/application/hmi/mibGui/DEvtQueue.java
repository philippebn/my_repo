package fr.application.hmi.mibGui;

import java.awt.*;

public class DEvtQueue extends EventQueue {

	/**
	 * execute the event queue
	 */
	public void doEvents() {
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		EventQueue evtQueue = toolKit.getSystemEventQueue();

		while (evtQueue.peekEvent() != null) {
			try {
				// if there are then get the event
				AWTEvent evt = evtQueue.getNextEvent();
				super.dispatchEvent(evt);
			} catch (java.lang.InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
