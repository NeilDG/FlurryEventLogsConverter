/**
 * 
 */
package data;

import java.util.HashMap;

/**
 * Stores the transaction from each user ID.
 * @author neil.dg
 *
 */
public class TransactionTableHolder {
	private HashMap<String, TransactionData> transactionTable = new HashMap<String,TransactionData>();
	
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
}
