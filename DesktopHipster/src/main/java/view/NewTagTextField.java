package view;

import general.PropertyNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewTagTextField extends JTextField {
	PropertyChangeSupport pcs;
	public NewTagTextField(PropertyChangeSupport p){
		super();
		pcs = p;
		addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	pcs.firePropertyChange(PropertyNames.VIEW_ADD_NEW_TAG, null, getText());
	        	setText("");
	        }
	    });
	}
}
