package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/*
 * @authour Lovisa Jaberg
 * The Library will keep track of the imported images.
 */

//TODO Must save the list with images to disc in hidden directory when you quit the application
//TODO 

public class Library {

	//Array of extended images
	private ArrayList<BufferedImage> imageArray = new ArrayList();
	
	//Path to the directory where you save the images
	private Path path;
	

	public Library(){
		
		/*
		 * Create a directory where you save your images. 
		 * If this directory is already created then it sets the directory where to save to the created directory.
		 */
		
		boolean success = (new File(System.getProperty("user.home") + "/Pictures/DesktopHipster")).mkdirs();
		
		if (!success) {
			path = Paths.get(System.getProperty("user.home") + "/Pictures/DesktopHipster");
			System.out.println("Directory already excists");
		}
	}
	
	/*
	 * Saves image to disc in the application folder
	 */
	
	public void save(BufferedImage saveImage){
		
	}
	
	/*
	 * When you import an image to the application by choosing from 
	 * file system or drops it in drop down panel this method will 
	 * put it into the arraylist of images it will show in the library
	 */
	
	public void load(){
		
	}
}
