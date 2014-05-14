package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;
import javax.swing.border.LineBorder;

import filter.FiltersEnum;
import general.PropertyNames;
import model.ExtendedImage;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * The edit view is a card that is used by the View.java. The edit
 * view is the stage where the user edits his/hers image.
 * 
 * @author Robin Sveningson
 * @revised Edvard H��binette
 * @revised Lovisa J��berg 
 *	
 */
@SuppressWarnings("serial")
public class EditView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private FilterActionBar filterActionBar = new FilterActionBar();
	private JButton proceedButton, backButton;
	private JLabel canvas;
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

		
		ImageIcon proceedImage = new ImageIcon(getClass().getResource("/right.png"));
		proceedButton = new JButton(proceedImage);
		proceedButton.setBorder(new LineBorder(Color.WHITE,10));
		proceedButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_APPLY_FILTER, null, null);
			}
		});
		
		ImageIcon backImage = new ImageIcon(getClass().getResource("/left.png"));
		backButton = new JButton(backImage);
		backButton.setBorder(new LineBorder(Color.WHITE,10));
		backButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.BROWSE);
			}
		});
		
		canvas = new JLabel();
	
		addEast(new JPanel(new BorderLayout()){{add(proceedButton,BorderLayout.CENTER);}});
		addWest(new JPanel(new BorderLayout()){{add(backButton,BorderLayout.CENTER);}});
		addCenter(new JPanel(){{add(canvas);}});
		addSouth(filterActionBar);
		
		for(FilterButton button : filterActionBar.getFilterButtons()){
			button.addActionListener(filterButtonClick);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch(name){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			if(evt.getNewValue()!=null){
				canvas.setIcon(new ImageIcon(((ExtendedImage)evt.getNewValue()).getPreview()));
				revalidate();
				repaint();
			}
			break;
		}
	}
	
}
