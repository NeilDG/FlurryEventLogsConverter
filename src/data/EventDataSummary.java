/**
 * 
 */
package data;

/**
 * Represents an event data
 * @author neil.dg
 *
 */
public class EventDataSummary {
	private String eventName;
	private int totalEventCount = 0;
	private boolean visible = true;
	
	public EventDataSummary(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public void incrementCount() {
		this.totalEventCount++;
	}
	
	public int getTotalEventCount() {
		return this.totalEventCount;
	}
	
	/*
	 * Marks this event as visible when writing as output
	 */
	public void markAsVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
}
