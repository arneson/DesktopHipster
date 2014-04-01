package filter;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		return ImageTools.applyGrayscale(image);
	}

}
