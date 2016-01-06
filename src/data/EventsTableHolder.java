/**
 * 
 */
package data;

import java.util.HashMap;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @author neil.dg
 *
 */
public class EventsTableHolder {
	private HashMap<String, String> eventNameTable = new HashMap<String,String>();
	
	public void addEvent(String event) {
		if(!this.eventNameTable.containsKey(event)) {
			this.eventNameTable.put(event, event);
		}
	}
	
	public String[] getAllEventNames() {
		String[] eventNames = this.eventNameTable.keySet().toArray(new String[this.eventNameTable.size()]);
		return eventNames;
	}
}
