/**
 * 
 */
package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.opencsv.CSVReader;

import data.CSVLinesHolder;
import file.FilePathHolder;
import notifications.NotificationCenter;
import notifications.Notifications;
import ui.ProgressBarHandler;

/**
 * Initiates reading of the CSV file
 * @author neil.dg
 *
 */
public class InputCSVReader extends Thread{
	
	private String inputFilePath;
	
	public InputCSVReader() {
		this.inputFilePath = FilePathHolder.getInstance().getInputPath();
	}
	
	@Override
	public void run() {
		File inputCSVFile = new File(this.inputFilePath);
		
		try {
			ProgressBarHandler.getInstance().setValue(15, "Reading CSV file");
			
			CSVReader csvReader = new CSVReader(new FileReader(inputCSVFile));
			
			/*String[] nextLine;
			while((nextLine = csvReader.readNext()) != null) {
				this.processCSVLine(nextLine);
			}*/
			
			ArrayList<String[]> csvLineList = (ArrayList<String[]>) csvReader.readAll();
			CSVLinesHolder.getInstance().assignCSVLineList(csvLineList);
			csvReader.close();
			
			NotificationCenter.getInstance().postNotification(Notifications.ON_START_EVENT_NAME_GATHERING);
			
		} catch (FileNotFoundException e) {
			System.out.println("Error. File " +this.inputFilePath+ " does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading file " + inputCSVFile.getName());
			e.printStackTrace();
		}
	}
	
	private void processCSVLine(String[] nextLine) {
		
		for(int i = 0; i < nextLine.length; i++) {
			System.out.println("Line " +i+ ": " +nextLine[i]);
		}
	}

}
