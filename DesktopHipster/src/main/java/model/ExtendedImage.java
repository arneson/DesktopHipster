package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import view.ThumbnailData;
import filter.*;

/**
 * Image class extending BufferedImage for having preview and thumbnail sized versions.
 * 
 * @author Edvard Hubinette
 * @revised by Simon Arneson
 */

public class ExtendedImage extends BufferedImage implements ThumbnailData {

	private BufferedImage preview;
	private BufferedImage thumbnail;
	
	// Map for managing image edit versions
	private TreeMap<FiltersEnum, BufferedImage> versions = new TreeMap<FiltersEnum, BufferedImage>();
	// Needed?
	// private Path pathToFile;
	
	public ExtendedImage(ImageIcon image){
		super(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = this.createGraphics();
		image.paintIcon(null, g, 0, 0);
		g.dispose();
				
		preview =  filter.ImageTools.toBufferedImage(getScaledInstance(600, -1, Image.SCALE_SMOOTH));
		
		thumbnail = filter.ImageTools.toBufferedImage(getScaledInstance(100, -1, Image.SCALE_FAST));
		
	}
	
	/**
	 * Adds an edited version of this image to a list of versions
	 * 
	 * @param filterName The name of the filter used
	 * @param image The edited image to stash
	 */
	public void addVersion(FiltersEnum filterName, BufferedImage image){
		versions.put(filterName, image);
	}

	/**
	 * Gets a version of the image with a specific filter
	 * 
	 * @param filterName The name of the filter used
	 * @return The edited image to stash
	 * @throws NoSuchVersionException Image version with that filter does not exist
	 */
	public BufferedImage getVersion(FiltersEnum filterName) throws NoSuchVersionException{
		if(versions.containsKey(filterName)){
			return versions.get(filterName);
		}else{
			throw new NoSuchVersionException();
		}
	}

	/**
	 * Returns a smaller version of the image
	 * 
	 * @return Preview-sized version of this image
	 */
	public BufferedImage getPreview(){
		return preview;
	}
	public void setPreview(BufferedImage newPreview){
		preview=newPreview;
	}

	/**
	 * Returns a small version of the image
	 * 
	 * @return Thumbnail-sized version of this image
	 */
	public BufferedImage getThumbnail(){
		return thumbnail;
	}

	@Override
	public List<BufferedImage> getVersions() {
		return (List<BufferedImage>)versions.values();
	}

	@Override
	public List<String> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getSelectedVersion() {
		return thumbnail;
	}

	public void setThumbnailSize(int width) {
		thumbnail = filter.ImageTools.toBufferedImage(getScaledInstance(width, -1, Image.SCALE_FAST));

		
	}
}
