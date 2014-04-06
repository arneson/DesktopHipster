package desktopHipster;

public class Timestamp {
	/**
	 * This class can be used to print out the current time in a
	 * readable format to the console. If we want to calculate the
	 * approximate time it takes to run methods we can use this class
	 * before and after, then we can compare the timestamp values
	 * to calculate the time.
	 * 
	 * @author Robin Sveningson
	 *	
	 */
	public static void print() {
		System.out.println(new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSSS").format(java.util.Calendar.getInstance().getTime()));
	}
}
