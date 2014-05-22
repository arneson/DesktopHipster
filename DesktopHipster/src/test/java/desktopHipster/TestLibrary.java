package desktopHipster;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.Library;

import org.junit.Test;

public class TestLibrary {
	
	private Library lib;
	private ExtendedImage image;

	public TestLibrary(){
		lib = new Library();
		image = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
	}
	
	@Test
	public void testSaveToHiddenDirectory(){
		//assertTrue();
	}
	
	@Test
	public void testLoadFromHiddenDirectory(){
		//assertTrue();
	}
	
	@Test
	public void testAddToImageArray(){
		int length = lib.getImageList().size();
		lib.addToImageArray(image);
		int newLenght = lib.getImageList().size();
		assertTrue(length + 1 == newLenght);
	}
	
	@Test
	public void testSave(){
		File file = new File(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster/TESTIMAGE.png");
		file.deleteOnExit();
		try {
			lib.save(image.getOriginal(), file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		assertTrue(file.exists());
	}
	
	@Test
	public void testgetImagesWithTagArray(){
		image.addTag("TestTag");
		image.addTag("TestTag2");
		lib.addToImageArray(image);
		List<ExtendedImage> imagesWithTags = lib.getImagesWithTagArray(image.getTags());
		assertTrue(imagesWithTags.contains(image));
	}
}
