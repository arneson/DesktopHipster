package Filter;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filter {
	public BufferedImage applyGrayscale(BufferedImage b) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				int total = red + green + blue;
				int midvalue = total / 3;
				int rgb = new Color(midvalue, midvalue, midvalue).getRGB();
				return rgb;
			}
		};
		return modifyPixels(b, ef, null);
	}
	
	public BufferedImage applySepia(BufferedImage b) {
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
		return modifyPixels(b, ef, null);
	}
	
	public BufferedImage applyInvertedGrayscale(BufferedImage b) {
		IModify ef = new IModify() {
			public int modify(int red, int green, int blue, int alpha,
					int layerRed, int layerGreen, int layerBlue, int layerAlpha){
				int total = red + green + blue;
				int midvalue = total / 3;
				int rgb = new Color(255-midvalue, 255-midvalue, 255-midvalue).getRGB();
				return rgb;
			}
		};
		return modifyPixels(b, ef, null);
	}
	
	public BufferedImage applyLayer(BufferedImage b, BufferedImage layer) {
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
		return modifyPixels(b, ef, layer);
	}
	
	private BufferedImage modifyPixels(BufferedImage b, IModify ef, BufferedImage layer) {
		int width = b.getWidth();
		int height = b.getHeight();
		BufferedImage modified = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//System.out.println(new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSSS").format(java.util.Calendar.getInstance().getTime()));
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				int rgb = b.getRGB(w, h);
				int alpha = (rgb >> 24) & 0x000000FF;
				int red = (rgb >> 16 ) & 0x000000FF;
				int green = (rgb >> 8 ) & 0x000000FF;
				int blue = (rgb) & 0x000000FF;
				
				int eR = 0, eG = 0, eB = 0, eA = 0;
				if(layer != null) {
					int externalRGB = layer.getRGB(w, h);
					eA = (externalRGB >> 24 ) & 0x000000FF;
					eR = (externalRGB >> 16 ) & 0x000000FF;
					eG = (externalRGB >> 8 ) & 0x000000FF;
					eB = (externalRGB) & 0x000000FF;
				} 
				
				int newColor = ef.modify(red, green, blue, alpha, eR, eG, eB, eA);
				modified.setRGB(w, h, newColor);
			}
		}
		//System.out.println(new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSSS").format(java.util.Calendar.getInstance().getTime()));
		return modified;
	}
}
