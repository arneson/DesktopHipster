package view;

import java.util.List;
import java.awt.image.BufferedImage;

public interface ThumbnailData {
	public List<BufferedImage> getVersions();
	public List<String> getTags();
}
