package desktopHipster;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.Tumblr;

import org.junit.Test;

public class TumblrTest {

	@Test
	public void testUploadImage() {
		ExtendedImage image = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		Tumblr tumblr = new Tumblr();
		tumblr.uploadImage(image);
	}

}
