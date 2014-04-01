package filter;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class OldStyleFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
			image = ImageTools.applyEnhancedColors(image,20,5);
			image = ImageTools.applySepia(image);
			//return ImageTools.applyLayer(image, ImageTools.toBufferedImage(new ImageIcon(this.getClass().getResource("/OldImageLayer.png"))));
			return null;
	}

}
