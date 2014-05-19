package filter;

import java.awt.image.BufferedImage;

/**
 * This class loops through all the pixels in the given image and applies the
 * given IModify to it.
 * 
 * @author Robin Sveningson
 * 
 */
public class ImageProcessor {

	public static BufferedImage modifyPixels(BufferedImage b, IModify m) {
		return applyPixelChanges(b, m, null);
	}

	public static BufferedImage modifyPixels(BufferedImage b, IModify m,
			BufferedImage layer) {
		return applyPixelChanges(b, m, layer);
	}

	private static BufferedImage applyPixelChanges(BufferedImage b, IModify m,
			BufferedImage layer) {
		int width = b.getWidth();
		int height = b.getHeight();
		BufferedImage modified = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				int rgb = b.getRGB(w, h);
				int alpha = (rgb >> 24) & 0x000000FF;
				int red = (rgb >> 16) & 0x000000FF;
				int green = (rgb >> 8) & 0x000000FF;
				int blue = (rgb) & 0x000000FF;

				int eR = 0, eG = 0, eB = 0, eA = 0;
				if (layer != null) {
					int externalRGB = layer.getRGB(w, h);
					eA = (externalRGB >> 24) & 0x000000FF;
					eR = (externalRGB >> 16) & 0x000000FF;
					eG = (externalRGB >> 8) & 0x000000FF;
					eB = (externalRGB) & 0x000000FF;
				}

				int newColor = m
						.modify(red, green, blue, alpha, eR, eG, eB, eA);
				modified.setRGB(w, h, newColor);
			}
		}
		return modified;
	}
}
