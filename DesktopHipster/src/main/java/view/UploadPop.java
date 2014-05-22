package view;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Frame displaying status of the upload of an image
 * 
 * @author Simon Arneson
 */

@SuppressWarnings("serial")
public class UploadPop extends JFrame {
	private JProgressBar progBar;
	private JPanel wrapper;
	private JLabel label;

	public UploadPop() {
		super("DesktopHipster");
		progBar = new JProgressBar(0, 20);
		progBar.setIndeterminate(true);

		label = new JLabel();
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);

		wrapper = new JPanel();
		wrapper.setLayout(new GridLayout(2, 1));
		wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		wrapper.add(label);
		wrapper.add(progBar);

		setSize(200, 80);
		setLayout(new GridLayout(1, 1));
		add(wrapper);
		addWindowListener(null);
		setUndecorated(true);
		setLocation(
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().width) * 0.4),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * 0.4));
	}
	
	public void setText(String t) {
		label.setText(t);
	}
}
