package filter;

import java.awt.image.BufferedImage;

public interface IFilter {
	BufferedImage applyFilter(BufferedImage image);

	String getName();
}
