package filter;

import java.awt.image.BufferedImage;

public class Filter {

	public static BufferedImage oldImage(BufferedImage b, BufferedImage oldImageLayer) {
		b = ImageTools.applyEnhancedColors(b,20,5);
		b = ImageTools.applySepia(b);
		return ImageTools.applyLayer(b, oldImageLayer);
	}
}
