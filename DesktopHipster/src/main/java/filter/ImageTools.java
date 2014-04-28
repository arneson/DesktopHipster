package filter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * This class contains all the different tools that can be used
 * on an image. 
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 * @revised Edvard Hübinette
 *
 */
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
	
	public static BufferedImage applyEnhancedColors(BufferedImage b, final int dark, final int bright) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){

				int newR = (red > 125) ? red + bright : red - dark;
				int newG = (green > 125) ? green + bright : green - dark;
				int newB = (blue > 125) ? blue + bright : blue - dark;
				
				newR = (newR < 0) ? 0 : newR;
				newG = (newG < 0) ? 0 : newG;
				newB = (newB < 0) ? 0 : newB;
				
				newR = (newR > 255) ? 255 : newR;
				newG = (newG > 255) ? 255 : newG;
				newB = (newB > 255) ? 255 : newB;
				
				int rgb = new Color(newR, newG, newB).getRGB();
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
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	//From stackOverflow
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}


}
