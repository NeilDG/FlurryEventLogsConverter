/**
 * 
 */
package logic;

import data.EventsTableHolder;
import data.TransactionTableHolder;
import notifications.NotificationCenter;
import notifications.NotificationListener;
import notifications.Notifications;
import notifications.Parameters;

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
	
	private EventsTableHolder eventsTableHolder;
	private TransactionTableHolder transactionTableHolder;
	
	public static void initialize() {
		sharedInstance = new ConverterHandler();
	}
	
	public static void destroy() {
		sharedInstance = null;
	}
	
	public static ConverterHandler getInstance() {
		return sharedInstance;
	}
	
	private ConverterHandler() {
		NotificationCenter.getInstance().addObserver(Notifications.ON_START_EVENT_NAME_GATHERING, this);
		NotificationCenter.getInstance().addObserver(Notifications.ON_START_EVENT_TALLYING, this);
		this.eventsTableHolder = new EventsTableHolder();
		this.transactionTableHolder = new TransactionTableHolder();
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
	
	public EventsTableHolder getEventsTableHolder() {
		return eventsTableHolder;
	}
	
	public TransactionTableHolder getTransactionTableHolder() {
		return transactionTableHolder;
	}

	@Override
	public void onNotify(String notificationString, Parameters params) {
		if(notificationString == Notifications.ON_START_EVENT_NAME_GATHERING) {
			EventNameSearcher eventNameSearcher = new EventNameSearcher();
			eventNameSearcher.start();
		}
		else if(notificationString == Notifications.ON_START_EVENT_TALLYING) {
			EventTallyWorker tallyWorker = new EventTallyWorker();
			tallyWorker.start();
		}
	}
	
	
}
