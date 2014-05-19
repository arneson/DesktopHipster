package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.TreeSet;

import view.View;
import filter.FiltersEnum;
import general.PropertyNames;

/**
 * The model class is a part of the MVC. The model contains the application's
 * data.
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 * 
 */

public class Model {
	private final PropertyChangeSupport pcs;
	private final Library library = new Library();
	private ExtendedImage activeImage;
	private FiltersEnum activeFilter;
	private TreeSet<String> tags = new TreeSet<String>();

	public Model() {
		pcs = new PropertyChangeSupport(this);
		startUp();
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

	/**
	 * Sets a new image as active for editing or uploading
	 * 
	 * @param newImage
	 *            The new active image
	 */
	public void setActiveImage(ExtendedImage newImage) {
		activeImage = newImage;
		pcs.firePropertyChange(PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE, null,
				activeImage);
	}

	/**
	 * Returns the image currently set for editing or uploading
	 * 
	 * @return The active image
	 */
	public ExtendedImage getActiveImage() {
		return activeImage;
	}

	/**
	 * Returns the active filter for applying to the active image
	 * 
	 * @return The active filter
	 */
	public FiltersEnum getActiveFilter() {
		return activeFilter;
	}

	/**
	 * Set a new active filter for applying to the active image
	 * 
	 * @param activeFilter
	 *            The new active filter
	 */
	public void setActiveFilter(FiltersEnum activeFilter) {
		this.activeFilter = activeFilter;
	}

	/**
	 * Returns all tags created by the user
	 * 
	 * @return A sorted set of all tags
	 */
	public TreeSet<String> getTags() {
		return new TreeSet<String>(tags);
	}

	/**
	 * Add a tag to the set of tags
	 * 
	 * @param tag
	 *            The tag to add
	 * @return true if the tag did not already exist
	 */
	public boolean addTag(String tag) {
		if (tags.add(tag)) {
			pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null, tags);
			return true;
		} else
			return false;
	}

	/**
	 * Remove a tag from the set of tags
	 * 
	 * @param tag
	 *            The tag to remove
	 * @return true if the tag was removed from the tag list
	 */
	public boolean removeTag(String tag) {
		if (tags.remove(tag)) {
			pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null, tags);
			return true;
		} else
			return false;
	}

	/**
	 * Returns the library associated with this model
	 * 
	 * @return The library
	 */
	public Library getLibrary() {
		return library;
	}

	public void updateGrid(TreeSet<String> tags) {
		if (tags == null || tags.isEmpty())
			pcs.firePropertyChange(PropertyNames.MODEL_GRID_UPDATE, null,
					library.getImageList());
		else
			pcs.firePropertyChange(PropertyNames.MODEL_GRID_UPDATE, null,
					library.getImagesWithTagArray(tags));
	}

	public void gridWidthChanged(int width) {
		library.updateThumbnailSizes(width);
	}

	/**
	 * Adds an image file to the library
	 * 
	 * @param imageFile
	 *            The image file to add
	 * @throws MalformedURLException
	 *             If the URL to the file is malformed
	 */
	public void addFileToLibrary(File imageFile) throws MalformedURLException {
		getLibrary().load(imageFile);
		updateGrid(null);
	}

	/**
	 * Adds a tag to the currently active image
	 * 
	 * @param tag
	 *            The tag to add
	 * @return True if the tag was added
	 */
	public boolean addTagToActiveImage(String tag) {
		return this.getActiveImage().addTag(tag);
	}

	/**
	 * Removes a tag from the currently selected image
	 * 
	 * @param tag
	 *            The tag to remove
	 */
	public void removeTagOnActiveImage(String tag) {
		this.getActiveImage().removeTag(tag);
	}

	/**
	 * Saves the current application state for reading on the next boot
	 */
	public void saveState() {
		ObjectOutputStream stream = null;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(
					library.hiddenPath.toString()));
			stream.writeObject(tags);
		} catch (IOException e) {
			e.printStackTrace();
		}
		library.saveToHiddenDirectory(stream);
		try {
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the previous state written to disk to preserve application state.
	 * Reads in old applied tags and continues to read the library image dump.
	 */
	public void startUp() {
		if (new File(library.hiddenPath.toString()).length() > 0) {
			ObjectInputStream stream = null;
			try {
				FileInputStream fis = new FileInputStream(
						library.hiddenPath.toString());
				stream = new ObjectInputStream(fis);
				tags = (TreeSet<String>) stream.readObject();
				pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null,
						new TreeSet<String>(tags));
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			library.loadFromHiddenDirectory(stream);
		}
	}

}
