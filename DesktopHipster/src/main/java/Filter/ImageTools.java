package Filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTools {
	public static BufferedImage applyGrayscale(BufferedImage b) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				int total = red + green + blue;
				int midvalue = total / 3;
				int rgb = new Color(midvalue, midvalue, midvalue).getRGB();
				return rgb;
			}
		};
		return ImageProcessor.modifyPixels(b, ef);
	}
	
	public static BufferedImage applySepia(BufferedImage b) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				int newR = Math.round((float)((red * 0.393) + (green * 0.769) + (blue * 0.189)));
				int newG = Math.round((float)((red * 0.349) + (green * 0.686) + (blue * 0.168)));
				int newB = Math.round((float)((red * 0.272) + (green * 0.534) + (blue * 0.131)));
				
				newR = (newR > 255) ? 255 : newR;
				newG = (newG > 255) ? 255 : newG;
				newB = (newB > 255) ? 255 : newB;
				
				int rgb = new Color(newR, newG, newB).getRGB();
				return rgb;
			}
		};
		return ImageProcessor.modifyPixels(b, ef);
	}
	
	public static BufferedImage applyInvertedGrayscale(BufferedImage b) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				int total = red + green + blue;
				int midvalue = total / 3;
				int rgb = new Color(255-midvalue, 255-midvalue, 255-midvalue).getRGB();
				return rgb;
			}
		};
		return ImageProcessor.modifyPixels(b, ef);
	}
	
	public static BufferedImage applyLayer(BufferedImage b, BufferedImage layer) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				//Apply transparency to original color
				red = red * alpha / 255;
				green = green * alpha / 255;
				blue = blue * alpha / 255;
				
				//Calculate layer transparency in percent
				double percent = (double)layerAlpha / (double)255;
				
				//Calculate distance from original color value to layer color value
				int dr = Math.abs(red - layerRed);
				int dg = Math.abs(green - layerGreen);
				int db = Math.abs(blue - layerBlue);
				
				//Calculate distance to move based on layer transparency percent
				dr = (int)Math.round(dr * percent);
				dg = (int)Math.round(dg * percent);
				db = (int)Math.round(db * percent);
				
				//Calculate new color values
				int newR = (red > layerRed) ? red - dr : red + dr;
				int newG = (green > layerGreen) ? green - dg : green + dg;
				int newB = (blue > layerBlue) ? blue - db : blue + db;
				
				return new Color(newR, newG, newB).getRGB();
			}
		};
		return ImageProcessor.modifyPixels(b, ef, layer);
	}
}
