/**
 * 
 */
package filters;

import data.EventDataSummary;
import data.EventsTableHolder;
import data.TransactionTableHolder;
import logic.ConverterHandler;

/**
 * Removes events form the table if they do not meet the specified minimum MEAN.
 * @author neil.dg
 *
 */
public class MeanTruncateFilter implements IFilter {

	public final static float MINIMUM_MEAN = 15.0f;	
	
	/* (non-Javadoc)
	 * @see filters.IFilter#applyFilter()
	 */
	@Override
	public void applyFilter() {
		EventsTableHolder eventsTableHolder = ConverterHandler.getInstance().getEventsTableHolder();
		TransactionTableHolder transactionTableHolder = ConverterHandler.getInstance().getTransactionTableHolder();
		String[] eventNames = eventsTableHolder.getAllEventNames();
		
		for(int i = 0; i < eventNames.length; i++) {
			EventDataSummary dataSummary = eventsTableHolder.findEvent(eventNames[i]);
			float eventMean = dataSummary.getTotalEventCount() * 1.0f / transactionTableHolder.getAllTransactionData().length;
			
			if(eventMean <= MINIMUM_MEAN) {
				dataSummary.markAsVisible(false);
				System.out.println(dataSummary.getEventName()+ " has mean of " +eventMean+ " which is less than " +MINIMUM_MEAN+". Removing from CSV.");
			}
		}
	}

}
