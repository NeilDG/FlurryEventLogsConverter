package logic.AWSLogic;

import data.CSVIndices;
import data.CSVLinesHolder;
import data.EventsTableHolder;
import logic.ConverterHandler;
import notifications.NotificationCenter;
import notifications.Notifications;
import ui.ProgressBarHandler;

public class AWSEventNameSearcher extends Thread {
	@Override
	public void run() {
		ProgressBarHandler.getInstance().setValue(30, "Listing all defined events");
		EventsTableHolder eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		
		for(int i = 0; i < CSVLinesHolder.getInstance().getLineCount(); i++) {
			String[] readLine = CSVLinesHolder.getInstance().getLineAt(i);
			
			//this.debugPrintLine(readLine);
			if(readLine.length > CSVIndices.AWSIndices.EVENT_DESCRIPTION) {
				eventsTableHolder.addEvent(readLine[CSVIndices.AWSIndices.EVENT_DESCRIPTION]);
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
