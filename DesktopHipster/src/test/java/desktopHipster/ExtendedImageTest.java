package desktopHipster;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.NoSuchVersionException;

import org.junit.Test;

import filter.BlackWhiteFilter;
import filter.FiltersEnum;

public class ExtendedImageTest {
	private static final File TMP_FILE = new File("/tmp/test");

	@Test(expected = IllegalArgumentException.class)
	public void testBadParameterToConstructor() {
		ImageIcon icon = null;
		new ExtendedImage(icon);
	}

	
	@Test (expected = NoSuchVersionException.class)
	public void testGetVersionNullParameter() throws NoSuchVersionException{
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		FiltersEnum nullFilter = null;
		testImage.getVersion(nullFilter);
	}
	
	@Test (expected = NoSuchVersionException.class)
	public void testGetVersionKeyNotPresent() throws NoSuchVersionException{
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		FiltersEnum bwFilter = FiltersEnum.BWFILTER;
		testImage.getVersion(bwFilter);
	}
	
	@Test
	public void testGetVersionKeyPresent() throws NoSuchVersionException{
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		FiltersEnum bwFilter = FiltersEnum.BWFILTER;
		testImage.addVersion(bwFilter, new BlackWhiteFilter().applyFilter(testImage.getOriginal()));
		assertTrue("True if returns a bufferedImage)", testImage.getVersion(bwFilter)instanceof BufferedImage);
	}

}
