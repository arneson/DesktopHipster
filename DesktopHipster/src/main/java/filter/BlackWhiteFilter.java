package filter;

/**
 * A simple grayscale filter.
 * 
 * @author Edvard Hübinette
 */
import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		return ImageTools.applyGrayscale(image);
	}

}
