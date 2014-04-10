package model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import view.View;
import filter.FiltersEnum;
import general.PropertyNames;

/**
 * The model class is a part of the MVC. The model
 * contains the application's data.
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 *	
 */
public class Model {
	private PropertyChangeSupport pcs;
	private Library library = new Library();
	//private DdBox ddBox = new DdBox();
	private ExtendedImage activeImage;
	private FiltersEnum activeFilter;
	private ArrayList<IHost> hosts = new ArrayList<IHost>();
	private TreeMap<String, BufferedImage> filterExamples = new TreeMap<String, BufferedImage>();
	private TreeSet<String> tags = new TreeSet<String>();
	
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
	
	public TreeSet<String> getTags(){
		return new TreeSet<String>(tags);
	}
	
	/**
	 * @param tag The tag to add
	 * @return true if the tag did not already exist
	 */
	public boolean addTag(String tag){
		return tags.add(tag);
	}
	
	/**
	 * @param tag The tag to remove
	 * @return true if the tag existed in the tag list
	 */
	public boolean removeTag(String tag){
		return tags.remove(tag);
	}
}
