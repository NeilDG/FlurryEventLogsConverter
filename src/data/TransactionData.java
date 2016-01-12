/**
 * 
 */
package data;

import java.util.HashMap;

import logic.ConverterHandler;

/**
 * Represents an individual transaction.
 * @author neil.dg
 *
 */
public class TransactionData {
	private String uniqueID;
	private HashMap<String, Integer> eventTallyTable = new HashMap<String,Integer>();
	
	public TransactionData(String uniqueID) {
		this.uniqueID = uniqueID;
		
		//automatically populate event names from detected events
		EventsTableHolder eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		String[] eventNames = eventsTableHolder.getAllEventNames();
		
		for(int i = 0; i < eventNames.length; i++) {
			this.setupEvent(eventNames[i]);
		}
	}
	
	public String getUniqueID() {
		return this.uniqueID;
	}
	
	private void setupEvent(String eventName) {
		this.eventTallyTable.put(eventName, 0);
	}
	
	/*
	 * Increments an event. Will create a unique event in the table if it does not exist
	 */
	public void incrementEvent(String eventName) {
		if(!this.eventTallyTable.containsKey(eventName)) {
			this.eventTallyTable.put(eventName, 1);
		}
		else {
			int value = this.eventTallyTable.get(eventName).intValue() + 1;
			this.eventTallyTable.put(eventName, value);
		}
		
		EventsTableHolder eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		eventsTableHolder.tallyCorrespondingEvent(eventName);
	}
	
	public int getEventCount(String eventName) {
		if(this.eventTallyTable.containsKey(eventName)) {
			return this.eventTallyTable.get(eventName);
		}
		else {
			System.out.println("ERROR. " +eventName + " was not found.");
			return 0;
		}
	}
	
	public void printDataDebug() {
		System.out.print("Transaction ID: " +this.uniqueID);
		
		for(String eventNames : this.eventTallyTable.keySet()) {
			System.out.print(" Event: " +eventNames+ " Triggers: " +this.eventTallyTable.get(eventNames).intValue());
		}
		
		System.out.println();
		
	}
}
