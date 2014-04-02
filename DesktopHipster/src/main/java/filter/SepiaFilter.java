package filter;

import java.awt.image.BufferedImage;

public class SepiaFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		return ImageTools.applySepia(image);
	}

}
