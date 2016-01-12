/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author neil.dg
 *
 */
public class EventsTableHolder {
	private HashMap<String, EventDataSummary> eventNameTable = new HashMap<String,EventDataSummary>();
	
	private String[] visibleEventNames;
	
	public void addEvent(String event) {
		if(!this.eventNameTable.containsKey(event)) {
			EventDataSummary eventDataSummary = new EventDataSummary(event);
			this.eventNameTable.put(event, eventDataSummary);
		}
	}
	
	public EventDataSummary findEvent(String event) {
		if(this.eventNameTable.containsKey(event)) {
			return this.eventNameTable.get(event);
		}
		else {
			System.out.println("ERROR. EventDataSummary" +event+ " not found.");
			return null;
		}
	}
	
	public void tallyCorrespondingEvent(String event) {
		if(this.eventNameTable.containsKey(event)) {
			this.eventNameTable.get(event).incrementCount();
		}
		else {
			System.out.println("ERROR. EventDataSummary " +event+ " not found.");
		}
	}
	
	public String[] getAllEventNames() {
		String[] eventNames = this.eventNameTable.keySet().toArray(new String[this.eventNameTable.size()]);
		return eventNames;
	}
	
	public String[] getVisibleEventNames() {
		ArrayList<String> eventNames = new ArrayList<String>();
		for(String event : this.eventNameTable.keySet()) {
			if(this.eventNameTable.get(event).isVisible()) {
				eventNames.add(event);
			}
		}
		
		this.visibleEventNames = null;
		this.visibleEventNames = new String[eventNames.size()];
		
		return eventNames.toArray(this.visibleEventNames);
	}
}
