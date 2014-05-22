package model;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.awt.image.BufferedImage;

import filter.FiltersEnum;

/**
 * Interface for objects wanting to be represented as
 * 
 * @author Edvard Hübinette
 * 
 */
public interface ThumbnailData {
	public Map<FiltersEnum, BufferedImage> getVersions();

	public TreeSet<String> getTags();

	public BufferedImage getSelectedVersion();
}