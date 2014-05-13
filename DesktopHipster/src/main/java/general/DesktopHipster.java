package general;

import controller.Controller;

/**
 * This class contains the main method that will run the
 * application. The constructor will create all of the important
 * parts of the application and add listeners to the application 
 * events.
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 *
 */
public class DesktopHipster {
	private Controller myController;
	public DesktopHipster() {
		Controller c = new Controller();
		myController = c;
	}
	public static void main(String[] args) {
		if(System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		final DesktopHipster dh = new DesktopHipster();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	//dh.getController().shutDownEverything();
		    }
		});
	}
	public Controller getController() {
		return myController;
	}
}
