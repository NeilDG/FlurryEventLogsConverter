package logic.AWSLogic;

import data.CSVIndices;
import data.CSVLinesHolder;
import data.TransactionData;
import data.TransactionTableHolder;
import logic.ConverterHandler;
import notifications.NotificationCenter;
import notifications.Notifications;
import ui.ProgressBarHandler;

public class AWSEventTallyWorker extends Thread {

	private TransactionTableHolder transactionTableHolder;
	
	public AWSEventTallyWorker() {
		// TODO Auto-generated constructor stub
		this.transactionTableHolder = ConverterHandler.getInstance().getTransactionTableHolder();
	}
	
	@Override
	public void run() {
		ProgressBarHandler.getInstance().setValue(60, "Tallying events from unique IDs");
		
		for(int i = 0; i < CSVLinesHolder.getInstance().getLineCount(); i++) {
			String[] readLine = CSVLinesHolder.getInstance().getLineAt(i);
			
			String uniqueID = readLine[CSVIndices.AWSIndices.UNIQUE_ID];
			
			//tally event
			TransactionData transactionData = this.transactionTableHolder.retrieveTransaction(uniqueID);
			transactionData.incrementEvent(readLine[CSVIndices.AWSIndices.EVENT_DESCRIPTION]);
		}
		
		TransactionData[] dataList = this.transactionTableHolder.getAllTransactionData();
		for(int  i = 0; i < dataList.length; i++) {
			dataList[i].printDataDebug();
		}
		
		NotificationCenter.getInstance().postNotification(Notifications.ON_WRITE_CONVERTED_CSV);
	}
}
