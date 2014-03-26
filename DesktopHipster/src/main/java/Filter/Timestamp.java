package Filter;

public class Timestamp {
	public static void print() {
		System.out.println(new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSSS").format(java.util.Calendar.getInstance().getTime()));
	}
}
