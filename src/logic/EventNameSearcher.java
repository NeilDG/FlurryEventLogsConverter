/**
 * 
 */
package logic;

import ui.ProgressBarHandler;

/**
 * Worker thread that searches for all events in the CSV and stores it in a hashmap.
 * @author neil.dg
 *
 */
public class EventNameSearcher extends Thread {

	@Override
	public void run() {
		ProgressBarHandler.getInstance().setValue(30, "Listing all defined events");
	}
}
