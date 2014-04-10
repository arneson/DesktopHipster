package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * One of the cards used in View.java. This jPanel represents
 * the browse view where the user browses all of his/hers 
 * images.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class BrowseView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	private JButton chooseImageButton;
	private JFileChooser jfc;
	
	public BrowseView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		proceedButton.setEnabled(false);
		desc = new JLabel("BrowseView");
		chooseImageButton = new JButton("Choose image");
		jfc = new JFileChooser();
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes()));
		
		addCenter(new JPanel(){{add(chooseImageButton);
			add(proceedButton);
			add(desc);}});
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.EDIT);
			}
		});
		chooseImageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//TODO needs parent later
				int returnVal = jfc.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION){
					pcs.firePropertyChange(PropertyNames.VIEW_OPEN_FILE_CLICKED,null,jfc.getSelectedFile());
				}
			}
		});
		
		setBackground(java.awt.Color.blue);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			proceedButton.setEnabled(true);
			break;
		}
	}
}
