package model;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.*;

/**
 * The Library will keep track of the imported images.
 * 
 * @authour Lovisa Jaberg
 * 
 */

//TODO Must save the list with images to disc in hidden directory when you quit the application
//TODO 

public class Library {

	//Array of extended images
	private List<ExtendedImage> imageArray = new ArrayList<ExtendedImage>();

	//Path to the directory where you save the images
	private Path path;

	/**
	 * Create a directory where you save your images. 
	 * If this directory is already created then it sets the directory where to save to the created directory.
	 */
	
	public Library(){
		
		new File(System.getProperty("user.home") + "/Pictures/DesktopHipster").mkdirs();
		path = Paths.get(System.getProperty("user.home") + "/Pictures/DesktopHipster");
	
	}

	/**
	 * Saves image to disc in the folder created for this desktop application. 
	 * Takes an ExtendedImage and  a String (the new filename) as parameters.
	 */

	public void save(BufferedImage saveImage, String name) throws FileNotFoundException, IOException{			    
		try{
			
			File outputfile = new File(path + "/" + name);
			ImageIO.write(saveImage, "png", outputfile);
		} catch(FileNotFoundException fileNotFound){
			//TODO catch exception x2
			System.out.println("File not found");
		} catch(IOException ioExc){
			System.out.println("Error");
		}
	}

	/**
	 * When you import an image to the application by choosing from 
	 * file system or drops it in drop down panel this method will 
	 * put it into the arraylist of images it will show in the library
	 */
	
	
	public void load(BufferedImage image){
		ExtendedImage extImage = (ExtendedImage)image;
		addToImageArray(extImage);
	}
	
	/**
	 * Adds image to the array of images.
	 * @param image
	 */
	
	public void addToImageArray(ExtendedImage image){
		imageArray.add(image);
	}
	

	public List<ExtendedImage> getImageArray() {
		return imageArray;
	}
	
	public void saveToHiddenDirectory(){
		
	}
}
