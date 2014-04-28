package desktopHipster;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.Flickr;

import org.junit.Test;

/**
 * Basic flickr auth and upload tests
 * @author Edvard
 *
 */
public class FlickrTest {

	@Test
	public void testAuth() {
		Flickr flickr = new Flickr();
	}
	
	@Test
	public void testUpload(){
		Flickr flickr = new Flickr();
	
		ImageIcon icon = new ImageIcon(flickr.getClass().getResource("/robin.jpg"));
		BufferedImage img = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		
		flickr.uploadImage(img);
	}

}
