package model;

import java.awt.image.BufferedImage;

import view.UploadPop;

public class UploadRunnable implements Runnable {

	private BufferedImage imageToUpload;
	private IHost host;
	private UploadPop upPop;

	public UploadRunnable(BufferedImage img, IHost host, UploadPop upPop) {
		this.imageToUpload = img;
		this.host = host;
		this.upPop = upPop;
	}

	@Override
	public void run() {
		upPop.setVisible(true);
		try {
			host.uploadImage(imageToUpload);
		} catch (UploadFailedException e) {
			// TODO Show the user something went wrong, e.getMessage() shows
			// proper messages
		} finally {
			upPop.setVisible(false);
		}
	}

}
