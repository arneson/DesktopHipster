package desktopHipster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	@Test(expected = IllegalArgumentException.class)
	public void testBadParameterToConstructor() {
		ImageIcon icon = null;
		new ExtendedImage(icon);
	}

	@Test(expected = NoSuchVersionException.class)
	public void testGetVersionNullParameter() throws NoSuchVersionException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		FiltersEnum nullFilter = null;
		testImage.getVersion(nullFilter);
	}

	@Test(expected = NoSuchVersionException.class)
	public void testGetVersionKeyNotPresent() throws NoSuchVersionException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		FiltersEnum bwFilter = FiltersEnum.BWFILTER;
		testImage.getVersion(bwFilter);
	}

	@Test
	public void testGetVersionKeyPresent() throws NoSuchVersionException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		FiltersEnum bwFilter = FiltersEnum.BWFILTER;
		testImage.addVersion(bwFilter,
				new BlackWhiteFilter().applyFilter(testImage.getOriginal()));
		assertTrue("True if returns a bufferedImage)",
				testImage.getVersion(bwFilter) instanceof BufferedImage);
	}

	@Test
	public void testAddVersion() throws NoSuchVersionException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		BufferedImage newVersion = FiltersEnum.BWFILTER.getFilter()
				.applyFilter(testImage.getOriginal());

		testImage.addVersion(FiltersEnum.BWFILTER, newVersion);
		assertEquals(
				"Works if the returned statement is null, since put returns the previous mapping",
				newVersion, testImage.getVersion(FiltersEnum.BWFILTER));
	}

	@Test
	public void testAddTagThatExists() {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		testImage.addTag("TestTag!");
		assertFalse("Works if the addTag method returns false on second add",
				testImage.addTag("TestTag!"));
	}

	@Test
	public void testAddTag() {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		testImage.addTag("TestTag!");
		assertTrue("Works if the addTag method returns true on add",
				testImage.addTag("Another tag!"));
	}

	@Test
	public void testSerializationEquality() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		testImage.addTag("tag!");
		testImage.addVersion(FiltersEnum.BWFILTER, FiltersEnum.BWFILTER
				.getFilter().applyFilter(testImage.getOriginal()));

		File file = new File("/tmp/testFile");
		if (file.exists()) {
			file.delete();
		}
		ObjectOutputStream stream = new ObjectOutputStream(
				new FileOutputStream(file));
		stream.writeObject(testImage);
		stream.reset();
		stream.flush();
		stream.close();

		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(
				file));
		ExtendedImage readImage = (ExtendedImage) inStream.readObject();
		assertTrue("Works if the image is equal to itself after serialization",
				testImage.equals(readImage));

	}
	
	@Test
	public void testSerializationHashCode() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		ExtendedImage testImage = new ExtendedImage(new ImageIcon(getClass()
				.getResource("/robin.jpg")));
		testImage.addTag("tag!");
		testImage.addVersion(FiltersEnum.BWFILTER, FiltersEnum.BWFILTER
				.getFilter().applyFilter(testImage.getOriginal()));

		File file = new File("/tmp/testFile");
		if (file.exists()) {
			file.delete();
		}
		ObjectOutputStream stream = new ObjectOutputStream(
				new FileOutputStream(file));
		stream.writeObject(testImage);
		stream.reset();
		stream.flush();
		stream.close();

		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(
				file));
		ExtendedImage readImage = (ExtendedImage) inStream.readObject();
		inStream.close();
		assertTrue("Works if the hash code is persistent after serialization", testImage.hashCode() == readImage.hashCode());
	}
}
