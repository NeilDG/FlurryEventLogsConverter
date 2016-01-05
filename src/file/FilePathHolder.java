/**
 * 
 */
package file;

/**
 * Holds the input file and the output file and their destination
 * @author neil.dg
 *
 */
public class FilePathHolder {
	private static FilePathHolder sharedInstance = null;
	public static FilePathHolder getInstance() {
		if(sharedInstance == null) {
			sharedInstance = new FilePathHolder();
		}
		
		return sharedInstance;
	}
	
	private String inputFilePath = null;
	private String outputFilePath = null;
	
	public void setInputFilePath(String input) {
		this.inputFilePath = input;
	}
	
	public void setOutputFilePath(String output) {
		this.outputFilePath = output;
	}
	
	public String getInputPath() {
		return this.inputFilePath;
	}
	
	public String getOutputPath() {
		return this.outputFilePath;
	}
}
