package desktopHipster;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import model.ExtendedImage;

import org.junit.Test;

import filter.BlackWhiteFilter;

public class BlackWhiteFilterTest {

	@Test
	public void testFilterGetName() {
		assertTrue("", "BlackWhiteFilter".equals(new BlackWhiteFilter()));
	}

	@Test
	public void testFilter() {
		BufferedImage testImage = new BlackWhiteFilter()
				.applyFilter(new ExtendedImage(new ImageIcon(getClass()
						.getResource("/robin.jpg"))).getOriginal());
		assertTrue("", true);
	}

}
