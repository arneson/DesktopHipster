package General;

import MVC.*;

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
	}
}
