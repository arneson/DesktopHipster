package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Transport;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.uploader.UploadMetaData;
import com.aetrion.flickr.uploader.Uploader;

public class FlickrHost implements IHost {

	// Hardcoded to specific user in this implementation
	private static final String API_KEY = "c06a492a32f8896e4c9c9598c8d17cbb";
	private static final String API_SECRET = "32ac7ec38b1df570";
	private Flickr flickr;

	public FlickrHost() {
		auth();
	}

	private void auth() {
		Transport transport = flickr.getTransport();
		flickr = new Flickr(API_KEY);
	}

	@Override
	public boolean uploadImage(BufferedImage image) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] asBytes = out.toByteArray();

		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Uploader uploader = flickr.getUploader();
		UploadMetaData metaData = new UploadMetaData();
		metaData.setContentType("photo");
		metaData.setTitle("DesktopHipster");
		try {
			uploader.upload(asBytes, metaData);
		} catch (FlickrException | IOException | SAXException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}