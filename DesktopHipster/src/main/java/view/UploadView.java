package view;

import general.PropertyNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import model.Tumblr;

/**
 * The upload view is a card that is used by View.java. It's main
 * purpose is to select which site to upload the image to.
 * 
 * @author Robin Sveningson
 * @revised Lovisa Jäberg
 */

@SuppressWarnings("serial")
public class UploadView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JButton saveToDiscButton;
	private JLabel desc;
	private JButton tumblrButton;
	private JTextField imageName;
	
	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		saveToDiscButton = new JButton("Save to disc");
		desc = new JLabel("UploadView");
		tumblrButton = new JButton("Tumblr");
		imageName = new JTextField("Add name..");
		
		addCenter(new JPanel(){{add(tumblrButton);
			add(proceedButton);
			add(desc);
			add(saveToDiscButton);}});
		
		addSouth(new JPanel(){{add(imageName);}});
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.BROWSE);
			}
		});
		tumblrButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_UPLOAD_ACTIVE_IMAGE, null, new Tumblr());
			}
		});
		//TODO 
		saveToDiscButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pcs.firePropertyChange(PropertyNames.VIEW_SAVE_IMAGE_TO_DISC, null, imageName.getText());
			}
		});
		
		setBackground(java.awt.Color.red);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
}
