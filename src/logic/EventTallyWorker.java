/**
 * 
 */
package logic;

import data.CSVIndices;
import data.CSVLinesHolder;
import data.TransactionData;
import data.TransactionTableHolder;
import ui.ProgressBarHandler;
import org.json.*;

/**
 * Thread that handles tallying of events per each unique ID found
 * @author neil.dg
 *
 */
public class EventTallyWorker extends Thread {
	
	private int parsingErrors = 0;
	
	private final String PLAYLAB_ANALYTICS_KEY = "PlaylabAnalytics";
	private final String SELECTED_ID_KEY = "UnityDeviceId";
	
	private TransactionTableHolder transactionTableHolder;
	
	public EventTallyWorker() {
		// TODO Auto-generated constructor stub
		this.transactionTableHolder = ConverterHandler.getInstance().getTransactionTableHolder();
	}
	
	@Override
	public void run() {
		ProgressBarHandler.getInstance().setValue(60, "Tallying events from unique IDs");
		
		for(int i = 1; i < CSVLinesHolder.getInstance().getLineCount(); i++) {
			String[] readLine = CSVLinesHolder.getInstance().getLineAt(i);
			
			if(readLine.length > CSVIndices.PARAMS_INDEX) {
				this.readAsJSON(readLine);
			}
		}
		
		TransactionData[] dataList = this.transactionTableHolder.getAllTransactionData();
		for(int  i = 0; i < dataList.length; i++) {
			dataList[i].printDataDebug();
		}
		System.out.println("Successfully parsed JSON Params. Data loss due to parse errors: " +this.parsingErrors+ " out of " +CSVLinesHolder.getInstance().getLineCount());
	
	}
	
	private void readAsJSON(String[] readLine) {
		//System.out.println("READING: " +jsonParams);
		
		try {
			JSONObject jsonObject = new JSONObject(readLine[CSVIndices.PARAMS_INDEX]);
			
			String uniqueID = jsonObject.getJSONObject(PLAYLAB_ANALYTICS_KEY).getString(SELECTED_ID_KEY);
			System.out.println("Read USER ID: " +uniqueID);
			
			//tally event
			TransactionData transactionData = this.transactionTableHolder.retrieveTransaction(uniqueID);
			transactionData.incrementEvent(readLine[CSVIndices.EVENT_DESCRIPTION]);
			
		}
		catch(JSONException e) {
			this.parsingErrors++;
			System.out.println(e.getMessage());
		}
		
	}
}
