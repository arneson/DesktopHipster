package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import General.PropertyNames;

@SuppressWarnings("serial")
public class UploadView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	
	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		desc = new JLabel("UploadView");
		
		add(proceedButton);
		add(desc);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, View.SubView.BROWSE);
			}
		});
		
		setBackground(java.awt.Color.red);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
}
