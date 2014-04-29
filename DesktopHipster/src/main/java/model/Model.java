package model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.MalformedURLException;
import java.util.TreeMap;
import java.util.TreeSet;

import view.View;
import filter.FiltersEnum;
import filter.IFilter;
import general.PropertyNames;

/**
 * The model class is a part of the MVC. The model
 * contains the application's data.
 * 
 * @author Robin Sveningson
 * @revised Edvard H��binette
 *	
 */

public class Model {
	private PropertyChangeSupport pcs;
	private Library library = new Library();
	private ExtendedImage activeImage;
	private FiltersEnum activeFilter;
	private TreeSet<IFilter> allFilters = new TreeSet<IFilter>();
	private TreeSet<IHost> allHosts = new TreeSet<IHost>();
	private TreeMap<String, BufferedImage> filterExamples = new TreeMap<String, BufferedImage>();
	private TreeSet<String> tags = new TreeSet<String>();
	
	public Model() {
		pcs = new PropertyChangeSupport(this);
		
		for(int i = 0; i < FiltersEnum.values().length; i++){
			allFilters.add(FiltersEnum.values()[i].getFilter());
		}
		
		for(int i = 0; i < HostsEnum.values().length; i++){
			allHosts.add(HostsEnum.values()[i].getHost());
		}
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
		
	public Library getLibrary() {
		return library;
	}
	
	/**
	 * Returns all filters
	 * @return A sorted set of all filters
	 */
	public TreeSet<IFilter> getAllFilters(){
		return allFilters;
	}
	
	/**
	 * Returns all hosts
	 * @return A sorted set of all hosts
	 */
	public TreeSet<IHost> getAllHosts(){
		return allHosts;
	}
	
	public void updateGrid() {
		pcs.firePropertyChange(PropertyNames.MODEL_GRID_UPDATE, null, library.getImageArray());
	}
	
	public void gridWidthChanged(int width) {
		library.updateThumbnailSizes(width);
	}

	public void addFileToLibrary(File imageFile) throws MalformedURLException {
		getLibrary().load(imageFile);
		updateGrid();
	}
}
