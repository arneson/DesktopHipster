package view;

import general.PropertyNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewTagTextField extends JTextField {
	PropertyChangeSupport pcs;

	public NewTagTextField(PropertyChangeSupport p) {
		super();
		setText("Add new tag...");
		pcs = p;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_ADD_NEW_TAG, null,
						getText());
				setText("");
			}
		});
		addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				setText("");

			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
}
