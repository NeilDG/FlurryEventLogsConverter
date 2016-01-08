/**
 * 
 */
package logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import data.TransactionTableHolder;
import file.FilePathHolder;
import notifications.NotificationCenter;
import notifications.Notifications;
import ui.ProgressBarHandler;

/**
 * Writes the converted transaction database into the specified output CSV
 * @author neil.dg
 *
 */
public class OutputCSVWriter extends Thread {

	private TransactionTableHolder transactionHolder;
	
	public OutputCSVWriter() {
		// TODO Auto-generated constructor stub
		this.transactionHolder = ConverterHandler.getInstance().getTransactionTableHolder();
		
	}
	@Override
	public void run() {
		ProgressBarHandler.getInstance().setValue(80, "Writing transaction database");
		String outputFilePath = FilePathHolder.getInstance().getOutputPath();
		
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
			ArrayList<String[]> entryList = this.transactionHolder.convertToCSVEntry();
			writer.writeAll(entryList);
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ProgressBarHandler.getInstance().setValue(100, "Finished. Output.csv saved.");
		NotificationCenter.getInstance().postNotification(Notifications.ON_CONVERT_FINISHED);
	}
}
