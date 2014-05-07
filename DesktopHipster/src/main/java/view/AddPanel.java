package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

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
		setBackground(Constants.BACKGROUNDCOLOR.getColor());
		setToolTipText("Add image to library");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		label = new JLabel(new ImageIcon(getClass().getResource("/AddPanel.png")));
		label.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				JFileChooser chooseFile = new JFileChooser();
				FileNameExtensionFilter allowedSuffix = new FileNameExtensionFilter("JPG, PNG, & GIF", ".jpg", ".png", ".gif");
				chooseFile.setFileFilter(allowedSuffix);
				int file = chooseFile.showOpenDialog(getParent());
				if(file == JFileChooser.APPROVE_OPTION) {
	    	    	firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY,null,chooseFile.getSelectedFiles());
				}
			}
		});
		label.setOpaque(true);
		add(label,BorderLayout.CENTER);
	}



}
