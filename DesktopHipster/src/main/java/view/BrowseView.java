package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import General.PropertyNames;

@SuppressWarnings("serial")
public class BrowseView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	private JButton imageButton;
	private final JFileChooser jfc = new JFileChooser();
	
	public BrowseView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		proceedButton.setEnabled(false);
		desc = new JLabel("BrowseView");
		imageButton = new JButton("Choose image");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes()));
		
		add(imageButton);
		add(proceedButton);
		add(desc);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, View.SubView.EDIT);
			}
		});
		imageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//TODO needs parent later
				int returnVal = jfc.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION){
					pcs.firePropertyChange(PropertyNames.OPEN_FILE_EVENT,null,jfc.getSelectedFile());
					proceedButton.setEnabled(true);
				}
			}
		});
		
		setBackground(java.awt.Color.blue);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
}
