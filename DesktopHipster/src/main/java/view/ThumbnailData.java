package view;

import java.util.List;
import java.util.TreeSet;
import java.awt.image.BufferedImage;

public interface ThumbnailData {
	public List<BufferedImage> getVersions();
	public TreeSet<String> getTags();
	public BufferedImage getSelectedVersion();
}
