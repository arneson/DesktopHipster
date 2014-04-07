package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		label = new JLabel(new ImageIcon(getClass().getResource("/AddPanel.png")));
		label.setOpaque(true);
		add(label,BorderLayout.CENTER);
	}

}
