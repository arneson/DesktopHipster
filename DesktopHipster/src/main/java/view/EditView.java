package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.swing.*;

import filter.Canvas;
import filter.FiltersEnum;
import model.ExtendedImage;
import General.PropertyNames;

@SuppressWarnings("serial")
public class EditView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private FilterButton blackWhiteFilterButton;
	private JLabel desc;
	private Canvas canvas;
	
	public EditView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		setLayout(new BorderLayout());
		proceedButton = new JButton("proceed");
		blackWhiteFilterButton = new FilterButton(FiltersEnum.BWFILTER);
		desc = new JLabel("EditView");
		canvas = new Canvas();
		
		add(BorderLayout.CENTER,canvas);
		add(BorderLayout.EAST,proceedButton);
		add(BorderLayout.NORTH,desc);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, View.SubView.UPLOAD);
			}
		});
		blackWhiteFilterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.ACTIVE_FILTER_CHANGED_EVENT, null, 
						((FilterButton)e.getSource()).getFilter());
			}
		});
		
		setBackground(java.awt.Color.green);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch(name){
		case PropertyNames.ACTIVE_IMAGE_CHANGED_EVENT:
			canvas.setFilterImage(((ExtendedImage)evt.getNewValue()).getPreview());
			revalidate();
			repaint();
			break;
		}
	}
	
}
