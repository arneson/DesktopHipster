package view;

import general.PropertyNames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private JButton flickrButton;
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
		tumblrButton = new JButton();
		tumblrButton.setIcon(new ImageIcon(getClass().getResource("/tumblr_icon.png")));
		flickrButton = new JButton();
		flickrButton.setIcon(new ImageIcon(getClass().getResource("/flickr_icon.png")));
		imageName = new JTextField("Add name..",50);
		
		imageName.addMouseListener(myMouseListener);
		
		addNorth(new JPanel(){{add(desc);
			add(proceedButton);
			add(saveToDiscButton);
		}});
		JPanel centerPanel = new JPanel(new GridLayout(2,1)){{add(tumblrButton);
			add(flickrButton);}};
			//TODO Change size 
			//centerPanel.setPreferredSize(new Dimension(500,500));
		addCenter(centerPanel);
		
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
		
	}
	
	private MouseListener myMouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(imageName)){
				imageName.setText("");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
}
