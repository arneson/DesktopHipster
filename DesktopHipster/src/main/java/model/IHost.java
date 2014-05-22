package model;

import java.awt.image.BufferedImage;

/**
 * Interface for host implementations
 * 
 * @author Edvard H��binette
 * 
 */
public interface IHost {
	public static final String TMPFILE_PATH = "/tmp/DHtmp";

	public boolean uploadImage(BufferedImage image);
}
