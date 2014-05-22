package filter;

import java.awt.image.BufferedImage;

public class NoFilterFilter implements IFilter {

	@Override
	public BufferedImage applyFilter(BufferedImage image) {
		return image;
	}
}
