package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * The Library will keep track of the imported images.
 * 
 * @authour Lovisa Jäberg
 * @revised Edvard Hübinette
 */
public class Library {

	/**
	 * This list keeps track of your the ExtendedImages you import to your
	 * library.
	 */
	private final List<ExtendedImage> imageList = new ArrayList<ExtendedImage>();
	protected transient Path path;
	protected transient Path hiddenPath;

	/**
	 * Create a directory where you save your images. If this directory already
	 * exists then it sets the directory where to save your images.
	 */
	public Library() {

		new File(System.getProperty("user.home") + "/Pictures/DesktopHipster")
		.mkdirs();
		path = Paths.get(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster");
		try {
			new File(System.getProperty("user.home")
					+ "/Pictures/DesktopHipster/.backUp").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		hiddenPath = Paths.get(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster/.backUp");

	}

	/**
	 * Saves image to disc in the folder created for this desktop application.
	 * Takes an ExtendedImage and a String (the new filename) as parameters.
	 */
	public void save(final BufferedImage saveImage, final File file)
			throws FileNotFoundException, IOException {
		try {
			ImageIO.write(saveImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * When you import an image to the application by choosing from file system
	 * or drops it in drop down panel this method will put it into the list of
	 * ExtendedImages shown in the library
	 */
	public void load(final File imageFile) {
		addToImageArray(new ExtendedImage(new ImageIcon(
				imageFile.getAbsolutePath())));
	}

	/**
	 * Adds image to the list of images.
	 * 
	 * @param image
	 */

	public void addToImageArray(final ExtendedImage image) {
		imageList.add(image);
	}

	/**
	 * Returns a list of all images in this library
	 * 
	 * @return The list of images
	 */
	public List<ExtendedImage> getImageList() {
		return new ArrayList<ExtendedImage>(imageList);
	}

	/**
	 * Saves all data this library contains to disk. This can be used to restore
	 * the posture of the application on restart.
	 * @param stream The output stream for writing
	 * @throws BackupCorruptException The backup write failed
	 */
	public void saveToHiddenDirectory(final ObjectOutputStream stream) throws BackupCorruptException  {
		try {
			stream.writeInt(imageList.size());
			for (ExtendedImage ei : imageList) {
				stream.writeObject(ei);
				stream.reset();
			}
		} catch (Exception e){
			e.printStackTrace();
			throw new BackupCorruptException("Backup write failed in library");
		}
	}

	/**
	 * Loads all data from disk to recreate an old session in this library instance.
	 * @param stream The input stream for reading
	 * @throws BackupCorruptException The backup read failed
	 */
	public void loadFromHiddenDirectory(final ObjectInputStream stream) throws BackupCorruptException {
		try {
			final int size = stream.readInt();
			for (int i = 0; i < size; i++) {
				imageList.add((ExtendedImage) stream.readObject());
			}
			stream.close();
		} catch (Exception e){
			e.printStackTrace();
			throw new BackupCorruptException("Backup read failed in library");
		}
	}

	public void updateThumbnailSizes(final int width) {
		for (final Object image : imageList.toArray()) {
			try {
				((ExtendedImage)image).setThumbnailSize(width);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<ExtendedImage> getImagesWithTagArray(final Set<String> tags) {

		ArrayList<ExtendedImage> returnList = new ArrayList<ExtendedImage>();

		boolean ok = true;

		//List of all ExtendedImages in library.
		List<ExtendedImage> list = getImageList();

		/*For every ExtendedImage the method gets the tags for this specific image.
		For every tag in the list containing the asked for tags, if any of those doesn't 
		exist, the method breaks. Otherwise, the image will be added to the returnList.
		 */
		for (ExtendedImage image : list){
			TreeSet<String> imageTags = image.getTags();
			for(String tag : tags){
				ok = imageTags.contains(tag);
				if(ok == false){
					break;
				}
			}
			if(ok){
				returnList.add(image);
			}
		}	
	return returnList;
}

	public void remove(ExtendedImage image) {
		for (ExtendedImage exImage : getImageList()) {
			if (image.getImageID() 
					== exImage.getImageID()){
				imageList.remove(image); 
			}
		}
	}

	public void removeTagFromAllImages(String tag) {
		for(ExtendedImage img:getImageList()){
			img.removeTag(tag);
		}
	}
}