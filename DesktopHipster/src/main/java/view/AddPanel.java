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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @authour Lovisa Jaberg
 * 
 */

public class AddPanel extends JPanel implements PropertyChangeListener {

	private JLabel label;
	private int side;
	private JFileChooser chooseFile;
	private final PropertyChangeSupport pcs;


	/**
	 * Create the panel.
	 */
	public AddPanel(int side, PropertyChangeSupport pcs) {
		
		this.pcs = pcs; 
		this.side = side;
		
		setPreferredSize(new Dimension(side,side));
		setLayout(new BorderLayout());
		setBackground(Constants.BACKGROUNDCOLOR.getColor());
		setToolTipText("Add image to library");
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		chooseFile = new JFileChooser();

		//setOpaque(true);
		label = new JLabel(new ImageIcon(getClass().getResource("/AddPanel.png")));
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.setOpaque(true);
		add(label, BorderLayout.CENTER);
		label.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				openDialog();
			}
		});
	}
	private void openDialog(){
		
		chooseFile.setMultiSelectionEnabled(true);
		int openChoice = chooseFile.showOpenDialog(this);
		List<File> fileList = Arrays.asList(chooseFile.getSelectedFiles());
		logChoice(openChoice, fileList);
	}

	private void logChoice(int openChoice, List<File> fileList){
		
		switch (openChoice)
		{
		case JFileChooser.CANCEL_OPTION:
			System.out.println("Cancelled");
			break;
		case JFileChooser.APPROVE_OPTION:
			System.out.println("Open this file");
			/*System.out.println("This file is: " + file.getName());
			pcs.firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY, null, file);
*/
			for(File file : fileList){
				System.out.println("This file is: " + file.getName());
				pcs.firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY, null, file);
			}
			break;
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
			break;
		}

	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
