package view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * Frame displaying status of the upload of an image
 * @author Simon Arneson
 */

@SuppressWarnings("serial")
public class UploadPop extends JFrame {
	private JProgressBar progBar;

	public UploadPop() {
	    super("DesktopHipster");
	    progBar = new JProgressBar(0,20);
	    progBar.setIndeterminate(true);
	    setSize(200,140);
	    add(progBar);
	    addWindowListener(null);
	    setUndecorated(true);
	    setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().width)*0.4),
	    		(int)((Toolkit.getDefaultToolkit().getScreenSize().height)*0.4));
	}
}
