package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Items displaying tags in TagPanel
 * 
 * @authour Simon Arneson
 * @revised Robin Sveningson
 */

@SuppressWarnings("serial")
public class TagItem extends JPanel{
	private PropertyChangeSupport pcs;
	private JLabel label, remove;
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
		label.setPreferredSize(new Dimension(width - 15 - 20, height));
		
		remove = new JLabel("x");
		remove.setPreferredSize(new Dimension(15,15));
		
		if(odd) {
			setAllBackgrounds(Constants.BACKGROUNDCOLOR_1.getColor());
		} else {
			setAllBackgrounds(Constants.BACKGROUNDCOLOR_2.getColor());
		}
		
		remove.setVisible(false);
		
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		add(remove, BorderLayout.EAST);
		
		remove.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REMOVE_TAG, null, label.getText());
			}
		});
		
		MouseListener hoverAdapter = new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				remove.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				remove.setVisible(false);
 			}
		};
		
		addMouseListener(hoverAdapter);
		label.addMouseListener(hoverAdapter);
		remove.addMouseListener(hoverAdapter);
		
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
