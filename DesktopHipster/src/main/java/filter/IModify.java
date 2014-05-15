package filter;

/**
 * This interface contains a method head that is being called for each pixel in
 * the specified image. The method should define how the pixels are modified.
 * 
 * @author Robin Sveningson
 * 
 */
public interface IModify {
	public int modify(int red, int green, int blue, int alpha, int layerRed,
			int layerGreen, int layerBlue, int layerAlpha);
}
