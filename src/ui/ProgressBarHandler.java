/**
 * 
 */
package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * UI utility to handle progress bar feedback
 * @author neil.dg
 *
 */
public class ProgressBarHandler implements PropertyChangeListener{
	private static ProgressBarHandler sharedInstance = null;
	public static ProgressBarHandler getInstance() {
		return sharedInstance;
	}
	
	public static void initialize(JProgressBar attachedBar, JLabel progressLabel) {
		sharedInstance = new ProgressBarHandler(attachedBar, progressLabel);
	}
	
	public static void destroy() {
		sharedInstance = null;
	}
	
	private JProgressBar attachedBar;
	private JLabel progressLabel;
	
	private ProgressBarHandler(JProgressBar jProgressBar, JLabel progressLabel) {
		this.attachedBar = jProgressBar;
		this.attachedBar.setValue(0);
		
		this.progressLabel = progressLabel;
	}
	
	public void setValue(int value) {
		this.attachedBar.setValue(value);
	}
	
	public void setValue(int value, String reason) {
		this.setValue(value);
		
		this.progressLabel.setText(reason);
	}
	
	public int getCurrentValue() {
		return this.attachedBar.getValue();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			this.setValue(progress);
		}
	}
}
