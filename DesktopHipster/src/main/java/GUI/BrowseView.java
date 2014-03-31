package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import MVC.PropertyNames;
import MVC.View;

@SuppressWarnings("serial")
public class BrowseView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	
	public BrowseView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		desc = new JLabel("BrowseView");
		
		add(proceedButton);
		add(desc);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, View.SubView.EDIT);
			}
		});
		
		setBackground(java.awt.Color.blue);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
	}
}
