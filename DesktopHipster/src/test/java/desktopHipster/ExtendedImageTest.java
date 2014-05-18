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

	@Test
	public void testSerializationEquality() throws IOException, ClassNotFoundException {
		ImageIcon icon = new ImageIcon(getClass().getResource("/robin.jpg"));
		ExtendedImage writeImage = new ExtendedImage(icon);

		BufferedImage testImageA = new filter.BlackWhiteFilter()
				.applyFilter(writeImage.getOriginal());
		writeImage.addVersion(FiltersEnum.BWFILTER, testImageA);

		BufferedImage testImageB = new filter.OldStyleFilter()
				.applyFilter(writeImage.getOriginal());
		writeImage.addVersion(FiltersEnum.OLDSTYLEFILTER, testImageB);


		FileOutputStream out = new FileOutputStream(TMP_FILE);
		ObjectOutputStream oOut = new ObjectOutputStream(out);
		oOut.writeObject(writeImage);
		oOut.reset();
		oOut.flush();
		oOut.close();

		ExtendedImage readImage = null;
		FileInputStream in = new FileInputStream(TMP_FILE);
		ObjectInputStream oIn = new ObjectInputStream(in);
		readImage = (ExtendedImage) oIn.readObject();
		oIn.close();

		boolean isEqual = writeImage.equals(readImage);
		System.out.println(writeImage);
		System.out.println(readImage);
		assertTrue("True if the two instances are equal", isEqual);
	}

	@Test
	public void testSerializationHashCodeIdenticality() {
		ImageIcon icon = new ImageIcon(getClass().getResource("/robin.jpg"));
		ExtendedImage writeImage = new ExtendedImage(icon);

		BufferedImage testImageA = new filter.BlackWhiteFilter()
				.applyFilter(writeImage.getOriginal());
		writeImage.addVersion(FiltersEnum.BWFILTER, testImageA);

		BufferedImage testImageB = new filter.OldStyleFilter()
				.applyFilter(writeImage.getOriginal());
		writeImage.addVersion(FiltersEnum.OLDSTYLEFILTER, testImageB);

		try {
			FileOutputStream out = new FileOutputStream(TMP_FILE);
			ObjectOutputStream oOut = new ObjectOutputStream(out);
			oOut.writeObject(writeImage);
			oOut.reset();
			oOut.flush();
			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExtendedImage readImage = null;
		try {
			FileInputStream in = new FileInputStream(TMP_FILE);
			ObjectInputStream oIn = new ObjectInputStream(in);
			readImage = (ExtendedImage) oIn.readObject();
			oIn.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		boolean isEqual = writeImage.hashCode() == readImage.hashCode();

		assertTrue("True if the two instances HashCode is equal", isEqual);
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
