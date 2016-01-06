/**
 * 
 */
package data;

import java.util.ArrayList;

/**
 * Holder of the read input CSV file
 * @author neil.dg
 *
 */
public class CSVLinesHolder {
	private static CSVLinesHolder sharedInstance = null;
	public static CSVLinesHolder getInstance() {
		if(sharedInstance == null) {
			sharedInstance = new CSVLinesHolder();
		}
		
		return sharedInstance;
	}
	
	private ArrayList<String[]> csvLineList = null;
	
	private CSVLinesHolder() {
		
	}
	
	public void assignCSVLineList(ArrayList<String[]> csvLineList) {
		this.csvLineList = csvLineList;
	}
	
	public String[] getLineAt(int index) {
		if(index < this.csvLineList.size()) {
			return this.csvLineList.get(index);
		}
		else {
			System.out.println("CSV line index out of bounds. " +index+ " > " +this.csvLineList.size());
			return null;
		}
	}
	
	public int getLineCount() {
		return this.csvLineList.size();
	}
}
