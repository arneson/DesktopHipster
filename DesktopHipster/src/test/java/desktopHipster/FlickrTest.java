package desktopHipster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.FlickrHost;

import org.junit.Test;

/**
 * Basic flickr auth and upload tests
 * 
 * @author Edvard
 * 
 */
public class FlickrTest {

	@Test
	public void testAuth() {
		FlickrHost flickrHost = new FlickrHost();
	}

	@Test
	public void testUpload() {
		FlickrHost flickrHost = new FlickrHost();

		ImageIcon icon = new ImageIcon(flickrHost.getClass().getResource(
				"/robin.jpg"));
		BufferedImage img = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		icon.paintIcon(null, g, 0, 0);

		flickrHost.uploadImage(img);
	}

}
