package model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.TreeMap;

import filter.FiltersEnum;
import view.View;
import General.PropertyNames;

/**
 * The model class is a part of the MVC. The model
 * contains the application's logic.
 * 
 * @author Robin Sveningson
 *	
 */
public class Model {
	private PropertyChangeSupport pcs;
	//private Library library = new Library();
	//private DdBox ddBox = new DdBox();
	private ExtendedImage activeImage;
	private FiltersEnum activeFilter;
	private IHost[] hosts;
	//private Filter[] filters;
	private TreeMap<String, BufferedImage> filters = new TreeMap<String, BufferedImage>();
	
	public Model() {
		pcs = new PropertyChangeSupport(this);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public void changeCardView(View.SubView sv) {
		pcs.firePropertyChange(PropertyNames.MODEL_CARD_CHANGE, null, sv);
	}
	public void setActiveImage(ExtendedImage newImage){
		activeImage=newImage;
		pcs.firePropertyChange(PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE, null, activeImage);
	}
	public ExtendedImage getActiveImage(){
		return activeImage;
	}

	public FiltersEnum getActiveFilter() {
		return activeFilter;
	}

	public void setActiveFilter(FiltersEnum activeFilter) {
		this.activeFilter = activeFilter;
	}
}
