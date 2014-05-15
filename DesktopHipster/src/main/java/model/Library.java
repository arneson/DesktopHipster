package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * The Library will keep track of the imported images.
 * 
 * @authour Lovisa Jaberg
 * 
 */

public class Library {

	/**
	 * This list keeps track of your the ExtendedImages you import to your library.
	 */
	private List<ExtendedImage> imageList = new ArrayList<ExtendedImage>();

	private Path path;
	private Path hiddenPath;

	/**
	 * Create a directory where you save your images. If this directory already exists 
	 * then it sets the directory where to save your images.
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
			System.out.println("IOException when creating .backUp");
			e.printStackTrace();
		}
		hiddenPath = Paths.get(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster/.backUp");

	}

	/**
	 * Saves image to disc in the folder created for this desktop application.
	 * Takes an ExtendedImage and a String (the new filename) as parameters.
	 */

	public void save(BufferedImage saveImage, File file)
			throws FileNotFoundException, IOException {
		try {
			ImageIO.write(saveImage, "png", file);
		} catch (FileNotFoundException fileNotFound) {
			// TODO catch exception x2
			System.out.println("File not found");
		} catch (IOException ioExc) {
			System.out.println("Error");
		}
	}

	/**
	 * When you import an image to the application by choosing from file system
	 * or drops it in drop down panel this method will put it into the list
	 * of ExtendedImages shown in the library
	 */
	
	public void load(File imageFile) {

		addToImageArray(new ExtendedImage(new ImageIcon(
				imageFile.getAbsolutePath())));
	}

	/**
	 * Adds image to the list of images.
	 * @param image
	 */

	public void addToImageArray(ExtendedImage image) {
		imageList.add(image);
	}
	

	public List<ExtendedImage> getImageArray() {
		return new ArrayList<ExtendedImage>(imageList);
	}

	public void saveToHiddenDirectory() {
		System.out.println("Saving to hidden directory");
		try {

			final ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(hiddenPath.toString()));
			out.writeInt(imageList.size());
			for (ExtendedImage ei : imageList) {
				out.writeObject(ei);
				out.reset();
			}
			out.flush();
			out.close();

		} catch (FileNotFoundException e1) {
			System.out.println("Failed, file not found");
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed, IOException");
			e.printStackTrace();
		}

	}

	// Method to run when program starts
	public void loadFromHiddenDirectory() {
		System.out.println(new File(hiddenPath.toString()).length());
		if (new File(hiddenPath.toString()).length() > 0) {
			System.out.println("Reading in saved library..");
			try {
				FileInputStream fis = new FileInputStream(hiddenPath.toString());
				ObjectInputStream in = new ObjectInputStream(fis);
				final int size = in.readInt();
				for (int i = 0; i < size; i++) {
					imageList.add((ExtendedImage) in.readObject());
					System.out.println(imageList.get(imageList.size() - 1));
				}
				in.close();
				fis.close();
			} catch (FileNotFoundException e) {
				System.out.println("Failed, file not found");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Failed, IOException");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Failed, class not found");
				e.printStackTrace();
			}
		}
		
	}

	// There is an addPanel in the imageList!
	public void updateThumbnailSizes(int width) {
		for (ExtendedImage image : imageList) {
			try {
				image.setThumbnailSize(width);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<ExtendedImage> getImagesWithTagArray(TreeSet<String> tags) {
		return new ArrayList<ExtendedImage>(imageList);
	}
}