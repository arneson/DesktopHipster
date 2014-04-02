package General;

import controller.Controller;
import view.View;
import model.Model;

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
		View v = new View();
		Model m = new Model();
		Controller c = new Controller(m);
		
		v.addPropertyChangeListener(c);
		m.addPropertyChangeListener(v);
	}
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DesktopHipster dh = new DesktopHipster();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	System.out.println("Shutdown hook!");
		    }
		});
	}
}
