/**
 * 
 */
package config;

/**
 * Whether data is extracted from Flurry Raw events or AWS RedShift
 * @author neil.dg
 *
 */
public class ConverterConfig {
	private static ConverterConfig sharedInstance = null;
	
	public enum OriginType {
		FROM_FLURRY,
		FROM_AWS
	}
	
	private OriginType originType;
	
	private ConverterConfig() {
		
	}
	
	public static void setConfig(OriginType type) {
		sharedInstance = null;
		sharedInstance = new ConverterConfig();
		sharedInstance.originType = type;
		
		System.out.println("Selected origintype." +type.toString());
	}
	
	public static OriginType getOriginType() {
		return sharedInstance.originType;
	}
}
