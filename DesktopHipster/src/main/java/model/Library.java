package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Library {

	//Array of extended images
	private ArrayList<BufferedImage> imageArray = new ArrayList();
	
	//Path to the directory where you save the images
	private Path path = Paths.get(System.getProperty("user.home") + "/Pictures/LovisPro");
	

	public Library(){
		
		// Create a directory; all non-existent ancestor directories are
		// automatically created
		boolean success = (new File(System.getProperty("user.home") + "/Pictures/DesktopHipster")).mkdirs();
		
		if (!success) {
			System.out.println("Directory already excists");
			
		}
	}
}
