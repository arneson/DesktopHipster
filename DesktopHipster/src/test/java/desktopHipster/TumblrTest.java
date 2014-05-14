package desktopHipster;

import javax.swing.ImageIcon;

import model.ExtendedImage;
import model.TumblrHost;

import org.junit.Test;
import org.scribe.exceptions.OAuthConnectionException;

public class TumblrTest {

	@Test
	public boolean testUploadImage() {
		ExtendedImage image = new ExtendedImage(new ImageIcon(getClass().getResource("/robin.jpg")));
		try{
			TumblrHost tumblrHost = new TumblrHost();
			tumblrHost.uploadImage(image);
		}
		catch(OAuthConnectionException e){
			return false;
		}
		return true;
	}

}
