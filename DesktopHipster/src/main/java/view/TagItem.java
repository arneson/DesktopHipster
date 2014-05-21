package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeSupport;

import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class TagItem extends JPanel{
	private PropertyChangeSupport pcs;
	private JLabel label;
	private boolean selected;
	private boolean odd;
	
	public TagItem(PropertyChangeSupport pcs, String tagName, int height, int width, boolean odd) {
		super();
		this.odd = odd;
		this.pcs = pcs;
		initialize(tagName, height, width);
	}
	
	private void initialize(String tagName, int height, int width) {
		label = new JLabel(tagName);
		label.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
		label.setOpaque(true);
		
		if(odd) {
			setAllBackgrounds(Constants.BACKGROUNDCOLOR_1.getColor());
		} else {
			setAllBackgrounds(Constants.BACKGROUNDCOLOR_2.getColor());
		}
		
		setLayout(new GridLayout(1,1));
		add(label);
		
		setPreferredSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width,height));
	}
	
	public void setSelected(boolean value) {
		selected = value;
		update();
	}
	
	public String getText() {
		return label.getText();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	private void update() {
		if(selected) {
			setBorder(BorderFactory.createEtchedBorder());
		} else {
			setBorder(null);
		}
	}
	
	public void setAllBackgrounds(Color c) {
		setBackground(c);
		label.setBackground(c);
		label.setOpaque(true);
	}
}
