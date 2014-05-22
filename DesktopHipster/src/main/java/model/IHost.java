package model;

import java.awt.image.BufferedImage;

/**
 * Interface for host implementations
 * 
 * @author Edvard Hübinette
 * 
 */
public interface IHost {
	public static final String TMPFILE_PATH = "/tmp/DHtmp";

	public void uploadImage(BufferedImage image) throws UploadFailedException;
}
