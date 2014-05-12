package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import filter.FiltersEnum;
import general.PropertyNames;
import model.ExtendedImage;
import java.awt.Color;

/**
 * The edit view is a card that is used by the View.java. The edit
 * view is the stage where the user edits his/hers image.
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 *	
 */
@SuppressWarnings("serial")
public class EditView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private FilterActionBar filterActionBar = new FilterActionBar();
	private JLabel desc, canvas;
	private ActionListener filterButtonClick = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			pcs.firePropertyChange(PropertyNames.VIEW_ACTIVE_FILTER_CHANGE, null, 
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
		JPanel filterPanel = new JPanel();
		filterPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		proceedButton = new JButton("proceed");
		desc = new JLabel("EditView");
		canvas = new JLabel();
		
		addNorth(new JPanel(){{add(desc);}});
		addEast(new JPanel(){{add(proceedButton);}});
		addCenter(new JPanel(){{add(canvas);}});
		addSouth(filterActionBar);
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_APPLY_FILTER, null, null);
			}
		});
		
		
		for(FilterButton button : filterActionBar.getFilterButtons()){
			button.addActionListener(filterButtonClick);
		}
		
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch(name){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			canvas.setIcon(new ImageIcon(((ExtendedImage)evt.getNewValue()).getPreview()));
			revalidate();
			repaint();
			break;
		}
	}
	
}
