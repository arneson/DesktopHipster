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
	public void run()  {
		upPop.setVisible(true);
		try {
			host.uploadImage(imageToUpload);
		} catch (UploadFailedException e) {
			upPop.setText("Failed. Connected?");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {}
		} finally {
			upPop.setVisible(false);
		}
	}

}
