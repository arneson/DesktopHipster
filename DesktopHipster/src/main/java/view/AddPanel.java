package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

/**
 * 
 * @authour Lovisa Jaberg
 * 
 */

public class AddPanel extends JPanel {
	
	private JLabel label;

	/**
	 * Create the panel.
	 */
	public AddPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setToolTipText("Add image to library");
		label = new JLabel(new ImageIcon("/Users/lovis/git/DesktopHipster/DesktopHipster/src/main/java/AddPanel.png"));
		label.setOpaque(true);
		add(label,BorderLayout.CENTER);
	}

}
