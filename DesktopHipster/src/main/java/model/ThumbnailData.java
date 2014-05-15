package model;

import java.util.List;
import java.util.TreeSet;
import java.awt.image.BufferedImage;

/**
 * Interface for objects wanting to be represented as
 * 
 * @author Edvard Hübinette
 * 
 */
public interface ThumbnailData {
	public List<BufferedImage> getVersions();

	public TreeSet<String> getTags();

	public BufferedImage getSelectedVersion();
}