/**
 * 
 */
package logic;

import data.CSVIndices;
import data.CSVLinesHolder;
import data.EventsTableHolder;
import notifications.NotificationCenter;
import notifications.Notifications;
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
		EventsTableHolder eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		
		for(int i = 1; i < CSVLinesHolder.getInstance().getLineCount(); i++) {
			String[] readLine = CSVLinesHolder.getInstance().getLineAt(i);
			
			//this.debugPrintLine(readLine);
			if(readLine.length > CSVIndices.EVENT_DESCRIPTION) {
				eventsTableHolder.addEvent(readLine[CSVIndices.EVENT_DESCRIPTION]);
			}
			
		}
		
		String[] allEventNames = eventsTableHolder.getAllEventNames();
		System.out.println("Total events detected: " +allEventNames.length);
		
		for(int i = 0; i < allEventNames.length; i++) {
			System.out.println("Found event name: " +allEventNames[i]);
		}
		
		NotificationCenter.getInstance().postNotification(Notifications.ON_START_EVENT_TALLYING);
	}
	
	private void debugPrintLine(String[] readLine) {
		for(int i = 0; i < readLine.length; i++) {
			System.out.println("Index["+i+"]: " +readLine[i]);
		}
	}
}
