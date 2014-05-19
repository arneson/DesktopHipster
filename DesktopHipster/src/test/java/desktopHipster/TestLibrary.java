package desktopHipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.Library;

import org.junit.Test;

public class TestLibrary {

	@Test
	public void test() throws FileNotFoundException, IOException {
		Library lib = new Library();
		ExtendedImage image = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		lib.save(image.getOriginal(), new File(System.getProperty("user.home")
				+ "/Pictures/DesktopHipster/TESTIMAGE.png"));

	}
}
