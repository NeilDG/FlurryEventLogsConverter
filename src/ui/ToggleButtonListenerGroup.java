/**
 * 
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.ConverterConfig;
import config.ConverterConfig.OriginType;

/**
 * Holder for radio button listeners
 * @author neil.dg
 *
 */
public class ToggleButtonListenerGroup {
	private static ToggleButtonListenerGroup sharedInstance = null;
	public static ToggleButtonListenerGroup getSharedInstance() {
		if(sharedInstance == null) {
			sharedInstance = new ToggleButtonListenerGroup();
		}
		
		return sharedInstance;
	}
	
	private AWSRedshiftToggleListener redshiftToggleListener;
	private FlurryToggleListener flurryToggleListener;
	
	private ToggleButtonListenerGroup() {
		this.redshiftToggleListener = new AWSRedshiftToggleListener();
		this.flurryToggleListener = new FlurryToggleListener();
		
	}
	
	public AWSRedshiftToggleListener getRedShiftToggleListener() {
		return this.redshiftToggleListener;
	}
	
	public FlurryToggleListener getFlurryToggleListener() {
		return this.flurryToggleListener;
	}
	
	public class AWSRedshiftToggleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ConverterConfig.setConfig(OriginType.FROM_AWS);
		}
		
	}
	
	public class FlurryToggleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			ConverterConfig.setConfig(OriginType.FROM_FLURRY);
		}
		
	}
	
}
