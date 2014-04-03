package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import filter.Canvas;
import filter.FiltersEnum;
import model.ExtendedImage;
import General.PropertyNames;

/**
 * The edit view is a card that is used by the View.java. The edit
 * view is the stage where the user edits his/hers image.
 * 
 * @author Robin Sveningson
 *	
 */
@SuppressWarnings("serial")
public class EditView extends JPanel implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JPanel filterPanel;
	private FilterButton blackWhiteFilterButton;
	private FilterButton sepiaFilterButton;
	private JLabel desc;
	private Canvas canvas;
	private ActionListener filterButtonClick = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			pcs.firePropertyChange(PropertyNames.CONTROLL_ACTIVE_FILTER_CHANGE, null, 
					((FilterButton)e.getSource()).getFilter());
			/*pcs.firePropertyChange(PropertyNames.REQUEST_CHANGE_CARD_VIEW, null, 
					((FilterButton)e.getSource()).getFilter());*/
		}
	};
	
	public EditView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}
	
	public void initialize() {
		setLayout(new BorderLayout());
		filterPanel = new JPanel();
		proceedButton = new JButton("proceed");
		
		blackWhiteFilterButton = new FilterButton(FiltersEnum.BWFILTER);
		sepiaFilterButton = new FilterButton(FiltersEnum.SEPIAFILTER);
		desc = new JLabel("EditView");
		canvas = new Canvas();
		
		filterPanel.add(blackWhiteFilterButton);
		filterPanel.add(sepiaFilterButton);
		
		add(BorderLayout.CENTER,canvas);
		add(BorderLayout.EAST,proceedButton);
		add(BorderLayout.NORTH,desc);
		add(BorderLayout.SOUTH,filterPanel);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.CONTROLL_APPLY_FILTER, null, null);
			}
		});
		blackWhiteFilterButton.addActionListener(filterButtonClick);
		blackWhiteFilterButton.setText("Black/White");
		sepiaFilterButton.addActionListener(filterButtonClick);
		sepiaFilterButton.setText("Sepia");
		
		setBackground(java.awt.Color.green);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch(name){
		case PropertyNames.VIEW_ACTIVE_IMAGE_CHANGE:
			canvas.setFilterImage(((ExtendedImage)evt.getNewValue()).getPreview());
			revalidate();
			repaint();
			break;
		}
	}
	
}
