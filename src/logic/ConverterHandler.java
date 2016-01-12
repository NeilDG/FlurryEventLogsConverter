/**
 * 
 */
package logic;

import com.sun.net.httpserver.Filter;

import config.ConverterConfig;
import config.ConverterConfig.OriginType;
import data.EventsTableHolder;
import data.TransactionTableHolder;
import filters.FilterManager;
import logic.AWSLogic.AWSEventNameSearcher;
import logic.AWSLogic.AWSEventTallyWorker;
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
	private FilterManager filterManager;
	
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
		NotificationCenter.getInstance().addObserver(Notifications.ON_WRITE_CONVERTED_CSV, this);
		NotificationCenter.getInstance().addObserver(Notifications.ON_CONVERT_FINISHED, this);
		
		this.eventsTableHolder = new EventsTableHolder();
		this.transactionTableHolder = new TransactionTableHolder();
		this.filterManager = new FilterManager();
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
	
	public FilterManager getFilterManager() {
		return this.filterManager;
	}

	@Override
	public void onNotify(String notificationString, Parameters params) {
		if(notificationString == Notifications.ON_START_EVENT_NAME_GATHERING) {
			
			if(ConverterConfig.getOriginType() == OriginType.FROM_FLURRY) {
				EventNameSearcher eventNameSearcher = new EventNameSearcher();
				eventNameSearcher.start();
			}
			else {
				System.out.print("Using AWS configuration");
				AWSEventNameSearcher searcher = new AWSEventNameSearcher();
				searcher.start();
			}
		}
		else if(notificationString == Notifications.ON_START_EVENT_TALLYING) {
			if(ConverterConfig.getOriginType() == OriginType.FROM_FLURRY) {
				EventTallyWorker tallyWorker = new EventTallyWorker();
				tallyWorker.start();
			}
			else {
				System.out.print("Using AWS configuration");
				AWSEventTallyWorker worker = new AWSEventTallyWorker();
				worker.start();
			}
			
		}
		
		else if(notificationString == Notifications.ON_WRITE_CONVERTED_CSV) {
			OutputCSVWriter writer = new OutputCSVWriter();
			writer.start();
		}
		else if(notificationString == Notifications.ON_CONVERT_FINISHED) {
			this.performCleanup();
		}
	}
	
	private void performCleanup() {
		NotificationCenter.getInstance().clearObservers();
		
		this.eventsTableHolder = null;
		this.transactionTableHolder = null;
		
		destroy();
		initialize();
		
		System.out.println("Conversion finished. Successfully performed cleanup.");
	}
	
	
}
