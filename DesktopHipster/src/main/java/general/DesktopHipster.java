package general;

import controller.Controller;

/**
 * This class contains the main method that will run the
 * application. The constructor will create all of the important
 * parts of the application and add listeners to the application 
 * events.
 * 
 * @author Robin Sveningson
 *	
 */
public class DesktopHipster {
	public DesktopHipster() {
		@SuppressWarnings("unused")
		Controller c = new Controller();
	}
	public static void main(String[] args) {
		if(System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		@SuppressWarnings("unused")
		DesktopHipster dh = new DesktopHipster();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	System.out.println("Shutdown hook!");
		    }
		});
	}
}
