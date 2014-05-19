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
	 */
	public void saveToHiddenDirectory(final ObjectOutputStream stream) {
		try {
			stream.writeInt(imageList.size());
			for (ExtendedImage ei : imageList) {
				stream.writeObject(ei);
				stream.reset();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Loads all data from disk to recreate an old session in this library instance.
	 * @param stream The input stream for reading
	 */
	public void loadFromHiddenDirectory(final ObjectInputStream stream) {
		try {
			final int size = stream.readInt();
			for (int i = 0; i < size; i++) {
				imageList.add((ExtendedImage) stream.readObject());
			}
			stream.close();
		} catch (IOException | ClassNotFoundException e) {
			// Could not restore the user DB, application will start with empty
			// image library
			e.printStackTrace();
		}

	}

	public void updateThumbnailSizes(final int width) {
		for (final ExtendedImage image : imageList) {
			try {
				image.setThumbnailSize(width);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	// What is even this?
	public List<ExtendedImage> getImagesWithTagArray(final Set<String> tags) {
		return new ArrayList<ExtendedImage>(imageList);
	}
}