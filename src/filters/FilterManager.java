/**
 * 
 */
package filters;

import java.util.ArrayList;

/**
 * Holds all contained filters
 * @author neil.dg
 *
 */
public class FilterManager {
	private ArrayList<IFilter> filters = new ArrayList<IFilter>();
	
	public void addFilter(IFilter filter) {
		this.filters.add(filter);
	}
	
	public void deleteFilter(IFilter filter) {
		this.filters.remove(filter);
	}
	
	public void removeAlLFilters() {
		this.filters.clear();
	}
	
	/*
	 * Applies all the filters in a sequential matter that it was added
	 */
	public void runFilters() {
		for(IFilter filter : this.filters) {
			filter.applyFilter();
		}
	}
}
