package filter;

import java.awt.image.BufferedImage;

/**
 * Simple sepia filter.
 * 
 * @author Edvard
 *
 */
public class SepiaFilter implements IFilter {

	public BufferedImage applyFilter(BufferedImage image) {
		return ImageTools.applySepia(image);
	}
	
	@Override
	public String getName() {
		return "SepiaFilter";
	}


}
