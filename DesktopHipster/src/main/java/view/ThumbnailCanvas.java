package view;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used by the filter test class only. It is
 * made to containt the library logic aswell as display 
 * different images. 
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class ThumbnailCanvas extends JPanel{
	private BufferedImage image;

	public ThumbnailCanvas(BufferedImage image) {
		this.image = image;
		setPreferredSize(new Dimension(400,400));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}	
}
