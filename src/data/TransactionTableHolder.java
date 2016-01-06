/**
 * 
 */
package data;

import java.util.ArrayList;
import java.util.HashMap;

import logic.ConverterHandler;

/**
 * Stores the transaction from each user ID.
 * @author neil.dg
 *
 */
public class TransactionTableHolder {
	private HashMap<String, TransactionData> transactionTable = new HashMap<String,TransactionData>();
	private HashMap<String, Integer> csvIndexMap = new HashMap<String,Integer>();
	private HashMap<Integer, String> csvEventStringsMap = new HashMap<Integer,String>(); //just inverse of index map
	private EventsTableHolder eventsTableHolder;
	
	public TransactionTableHolder() {
		
	}
	
	
	/*
	 * Retrieves a transaction. Creates a new transaction data if it does not exist.
	 */
	public TransactionData retrieveTransaction(String uniqueID) {
		if(transactionTable.containsKey(uniqueID)) {
			return this.transactionTable.get(uniqueID);
		}
		else {
			TransactionData newData = new TransactionData(uniqueID);
			this.transactionTable.put(uniqueID, newData);
			return newData;
		}
	}
	
	public TransactionData[] getAllTransactionData() {
		TransactionData[] dataList = this.transactionTable.values().toArray(new TransactionData[this.transactionTable.size()]);
		return dataList;
	}
	
	public ArrayList<String[]> convertToCSVEntry() {
		ArrayList<String[]> csvArrayList = new ArrayList<String[]>();
		
		
		eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		String[] eventNames = eventsTableHolder.getAllEventNames();
		
		String[] csvLine = new String[eventNames.length + 1];
		
		//write 1st line as header
		csvLine[0] = "UniqueID";
		for(int i = 1; i <= eventNames.length; i++) {
			csvLine[i] = eventNames[i - 1];
			this.csvIndexMap.put(eventNames[i-1], i);
			this.csvEventStringsMap.put(i, eventNames[i-1]);
		}
		
		csvArrayList.add(csvLine);
		
		TransactionData[] transactionData = this.getAllTransactionData();
		for(int i = 0; i < transactionData.length; i++) {
			csvLine= this.writeDataList(transactionData[i]);
			csvArrayList.add(csvLine);
		}
		
		return csvArrayList;
	}
	
	private String[] writeDataList(TransactionData data) {
		String[] eventNames = this.eventsTableHolder.getAllEventNames();
		String[] csvLines = new String[eventNames.length + 1];
		
		csvLines[0] = data.getUniqueID();
		
		for(int i = 0; i < eventNames.length; i++) {
			int eventTally = data.getEventCount(eventNames[i]);
			csvLines[this.csvIndexMap.get(eventNames[i])] = Integer.toString(eventTally);
		}
		
		return csvLines;
	}
}
