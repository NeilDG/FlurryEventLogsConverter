/**
 * 
 */
package logic;

import notifications.NotificationCenter;
import notifications.NotificationListener;
import notifications.Notifications;
import notifications.Parameters;
import ui.ProgressBarHandler;

/**
 * Heads the conversion scheme.
 * @author neil.dg
 *
 */
public class ConverterHandler implements NotificationListener {
	private static ConverterHandler sharedInstance = null;
	
	private enum ConversionState {
		NONE,
		INPUT_READ,
		CONVERT,
		OUTPUT_WRITE
	}
	private ConversionState currentState = ConversionState.NONE;
	
	public static void initialize() {
		sharedInstance = new ConverterHandler();
	}
	
	public static ConverterHandler getInstance() {
		return sharedInstance;
	}
	
	private ConverterHandler() {
		NotificationCenter.getInstance().addObserver(Notifications.ON_START_EVENT_NAME_GATHERING, this);
	}
	
	/*
	 * Handles execution of conversion running on a background thread
	 */
	public void execute() {
		InputCSVReader inputCSVReader = new InputCSVReader();
		inputCSVReader.start();
	}
	
	public ConversionState getCurrentState() {
		return this.currentState;
	}

	@Override
	public void onNotify(String notificationString, Parameters params) {
		if(notificationString == Notifications.ON_START_EVENT_NAME_GATHERING) {
			EventNameSearcher eventNameSearcher = new EventNameSearcher();
			eventNameSearcher.start();
		}
	}
	
	
}
