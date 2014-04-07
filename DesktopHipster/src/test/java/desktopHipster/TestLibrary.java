package desktopHipster;

import static org.junit.Assert.*;

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
		lib.save(image,"TESTIMAGE.png");
	}
}
