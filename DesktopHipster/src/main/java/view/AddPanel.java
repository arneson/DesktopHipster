package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		setOpaque(true);

		chooseFile = new JFileChooser();

		label = new JLabel(new ImageIcon(getClass().getResource("/addPanelImage.png")));
		label.setPreferredSize(new Dimension(side,side));
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.setBackground(Constants.CONTRASTCOLORLIGHT.getColor());
		label.setToolTipText("Click here to add an image to your library");
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
