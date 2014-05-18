package desktopHipster;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.Library;

import org.junit.Test;

public class TestLibrary {
	
	private Library lib = new Library();
	private ExtendedImage image = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));

	public TestLibrary(){
		try {
			test();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testAddToImageArray();
	}
	
	
	@Test
	public void test() throws FileNotFoundException, IOException {
		
		lib.save(image.getOriginal(), new File(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster/TESTIMAGE.png"));

	}
	
	public void testSaveToHiddenDirectory(){
		
	}
	
	public void testLoadFromHiddenDirectory(){
		
	}
	
	public boolean testAddToImageArray(){
		int length = lib.getImageArray().size();
		lib.addToImageArray(image);
		int newLenght = lib.getImageArray().size();
		boolean ok = (length + 1 == newLenght); 
		return ok;
	}
	
	public void testSave(){
		
	}
}
