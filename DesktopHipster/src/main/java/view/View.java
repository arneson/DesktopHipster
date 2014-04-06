package view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import General.PropertyNames;

/**
 * The View is part of the MVC. It is the main frame
 * that the application will use. The view will contain the 
 * cards that are the different states of the program. 
 * 
 * @author Robin Sveningson
 *	
 */
@SuppressWarnings("serial")
public class View extends JFrame implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private BrowseView browseView;
	private EditView editView;
	private UploadView uploadView;
	private JPanel cardPanel;
	
	public enum SubView {
		BROWSE,
		EDIT,
		UPLOAD
	}
	
	public View() {
		super();
		pcs = new PropertyChangeSupport(this);
		initialize();
		pcs.addPropertyChangeListener(browseView);
		pcs.addPropertyChangeListener(editView);
		pcs.addPropertyChangeListener(uploadView);
	}
	
	public void initialize() {
		browseView = new BrowseView(pcs);
		editView = new EditView(pcs);
		uploadView = new UploadView(pcs);
		cardPanel = new JPanel();
		
		cardPanel.setLayout(new CardLayout());
		cardPanel.add(browseView, SubView.BROWSE.toString());
		cardPanel.add(editView, SubView.EDIT.toString());
		cardPanel.add(uploadView, SubView.UPLOAD.toString());
		
		updateVisibleCard(SubView.BROWSE.toString());
		
		setLayout(new GridLayout(1,1));
		add(cardPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	public void updateVisibleCard(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel, name);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
		String name = evt.getPropertyName();
		if(name.equals(PropertyNames.MODEL_CARD_CHANGE)) {
			updateVisibleCard(((SubView)evt.getNewValue()).toString());
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
}
