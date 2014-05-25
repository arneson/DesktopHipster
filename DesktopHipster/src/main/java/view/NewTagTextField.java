package view;

import general.PropertyNames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Textfield for adding new tags to library
 * 
 * @authour Simon Arneson
 */


@SuppressWarnings("serial")
public class NewTagTextField extends JPanel {
	PropertyChangeSupport pcs;
	private JTextField tf;
	private Color foreColor = Constants.TEXTCOLOR.getColor();

	public NewTagTextField(PropertyChangeSupport p, int height) {
		super();
		setPreferredSize(new Dimension(0, height));
		
		tf = new JTextField();
		tf.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		setBackground(Constants.BACKGROUNDCOLOR.getColor());
		tf.setBackground(Constants.BACKGROUNDCOLOR_2.getColor());
		tf.setOpaque(true);
		pcs = p;
		
		tf.setForeground(foreColor);
		tf.setText("Add new tag...");
		
		setLayout(new GridLayout(1,1));
		add(tf);
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Tells the model that the user has added a new tag in the view.
				pcs.firePropertyChange(PropertyNames.VIEW_ADD_NEW_TAG, null,
						tf.getText());
				tf.setText("");
			}
		});
		tf.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(tf.getForeground().equals(foreColor)) {
					tf.setForeground(Color.black);
					tf.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(tf.getText().equals("")) {
					tf.setForeground(foreColor);
					tf.setText("Add new tag...");
				}
			}

		});
	}
}
