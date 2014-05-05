package filter;

/**
 * Simple grayscale filter
 * 
 * @author Edvard Hübinette
 */
import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		return ImageTools.applyGrayscale(image);
	}

	@Override
	public String getName() {
		return "BlackWhiteFilter";
	}

}
