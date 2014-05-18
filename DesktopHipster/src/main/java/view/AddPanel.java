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
import java.io.File;
import java.nio.file.Files;

/**
 * 
 * @authour Lovisa Jaberg
 * 
 */

public class AddPanel extends JPanel {

	private JLabel label;
	private int side;
	private JFileChooser chooseFile;


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
		label.setOpaque(true);
		add(label, BorderLayout.CENTER);
		label.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				saveDialog();
			}
		});
	}
	private void saveDialog(){
		chooseFile = new JFileChooser();
		logChoice();
	}

	private void logChoice(){
		int openChoice = chooseFile.showOpenDialog(this);
		
		switch (openChoice)
		{
		case JFileChooser.CANCEL_OPTION:
			break;
		case JFileChooser.APPROVE_OPTION:
			for(int i = 0; i < chooseFile.getSelectedFiles().length; i++){
				//File chosenFile = chooseFile.getSelectedFiles().;
				//firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY, null, chosenFile);
			}
			break;
		case JFileChooser.ERROR_OPTION:
			break;
		}

	}
}
