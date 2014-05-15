package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * 
 * @authour Lovisa Jaberg
 * 
 */

public class AddPanel extends JPanel {

	private JLabel label;
	private int side;


	/**
	 * Create the panel.
	 */
	public AddPanel(int side) {
		this.side = side;
		setPreferredSize(new Dimension(side,side));
		setLayout(new BorderLayout());
		setBackground(Constants.BACKGROUNDCOLOR.getColor());
		setToolTipText("Add image to library");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setOpaque(true);
		label = new JLabel(new ImageIcon(getClass().getResource("/AddPanel.png")));
		label.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {

				JFileChooser chooseFile = new JFileChooser();
				//FileNameExtensionFilter allowedSuffix = new FileNameExtensionFilter("JPG, PNG, & GIF", ".jpg", ".png", ".gif");
				//chooseFile.setFileFilter(allowedSuffix);
				int file = chooseFile.showOpenDialog(getParent());
				if(file == JFileChooser.APPROVE_OPTION) {
					firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY, null, chooseFile.getSelectedFiles());
				}
			}

		});


		/*label.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				JFileChooser chooseFile = new JFileChooser();
				FileNameExtensionFilter allowedSuffix = new FileNameExtensionFilter("JPG, PNG, & GIF", ".jpg", ".png", ".gif");
				chooseFile.setFileFilter(allowedSuffix);
				int file = chooseFile.showOpenDialog(getParent());
				if(file == JFileChooser.APPROVE_OPTION) {
	    	    	firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY,null,chooseFile.getSelectedFiles());
				}
			}
		});*/
		label.setOpaque(true);
		add(label,BorderLayout.CENTER);
	}



}
