package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import model.Tumblr;
import General.PropertyNames;

@SuppressWarnings("serial")
public class UploadView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	private JButton tumblrButton;
	
	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		desc = new JLabel("UploadView");
		tumblrButton = new JButton("Tumblr");
		
		add(tumblrButton);
		add(proceedButton);
		add(desc);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, View.SubView.BROWSE);
			}
		});
		tumblrButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.UPLOAD_ACTIVE_IMAGE, null, new Tumblr());
			}
		});
		
		setBackground(java.awt.Color.red);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
}
