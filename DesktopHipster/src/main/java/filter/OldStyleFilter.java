package filter;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class OldStyleFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		image = ImageTools.applyEnhancedColors(image, 20, 5);
		return ImageTools.applySepia(image);
	}

	@Override
	public String getName() {
		return "OldStyleFilter";
	}

}
